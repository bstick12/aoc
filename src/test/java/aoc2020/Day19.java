package aoc2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day19 {

  public static boolean part2 = false;

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if (!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day19.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {
    List<String> input = readInput(19, true);
    Iterator<String> iterator = input.iterator();
    long answer = 0;

    Pattern compile = Pattern.compile("(\\d+): (.*)");

    Map<String, String> ruleMap = new HashMap<>();
    while(iterator.hasNext()) {
      String next = iterator.next();
      if(next.isEmpty()) {
        break;
      }
      Matcher matcher = compile.matcher(next);
      if(matcher.find()) {
        ruleMap.put(matcher.group(1),matcher.group(2));
        if(matcher.group(2).equals("\"a\"")) {
          ruleMap.put(matcher.group(1),"a");
        }
        if(matcher.group(2).equals("\"b\"")) {
          ruleMap.put(matcher.group(1),"b");
        }
      }
    }

    Pattern compile1 = Pattern.compile("^" + getRegex("0", ruleMap) + "$");
    while(iterator.hasNext()) {
      String next = iterator.next();
      if(compile1.matcher(next).find()) {
        answer++;
      }
    }

    log.info("Ans P1 {}", answer);
  }

  @Test
  public void testPuzzle2() {
    List<String> input = readInput(19, true);
    Iterator<String> iterator = input.iterator();
    long answer = 0;

    Pattern compile = Pattern.compile("(\\d+):(.*)");

    Map<String, String> ruleMap = new HashMap<>();
    while(iterator.hasNext()) {
      String next = iterator.next();
      if(next.isEmpty()) {
        break;
      }
      Matcher matcher = compile.matcher(next);
      if(matcher.find()) {
        ruleMap.put(matcher.group(1),StringUtils.trim(matcher.group(2)));
        if(matcher.group(2).equals(" \"a\"")) {
          ruleMap.put(matcher.group(1),"a");
        }
        if(matcher.group(2).equals(" \"b\"")) {
          ruleMap.put(matcher.group(1),"b");
        }
      }
    }

    part2 = true;
    Pattern compile1 = Pattern.compile("^" + getRegex("0", ruleMap) + "$");
    while(iterator.hasNext()) {
      String next = iterator.next();
      if(compile1.matcher(next).find()) {
        answer++;
      }
    }

    log.info("Ans P2 {}", answer);
  }

  private String getRegex(String s, Map<String, String> ruleMap) {

    String rule = ruleMap.get(s);
    StringBuilder sb = new StringBuilder();
    sb.append("(");
    if(rule.equals("a") || rule.equals("b")) {
      return rule;
    } else {
      processRule(ruleMap, rule, sb);
    }
    sb.append(")");
    return sb.toString();

  }

  private void processRule(Map<String, String> ruleMap, String rule, StringBuilder sb) {
    String[] parts = rule.split(" ");
    for(String part : parts) {
      if(part.equals("|")) {
        sb.append("|");
      } else {
        if(part2) {
          if (part.equals("8")) {
            sb.append("(").append(getRegex("42", ruleMap)).append("+)");
          } else if (part.equals("11")) {
            sb.append("(");
            StringBuilder rule11 = new StringBuilder();
            int iterations = 5;
            for (int i = 1; i < iterations; i++) {
              for (int j = 0; j < i; j++) {
                rule11.append("42 ");
              }
              for (int j = 0; j < i; j++) {
                rule11.append("31 ");
              }
              if (i < iterations - 1) {
                rule11.append("| ");
              }
            }
            processRule(ruleMap, rule11.toString(), sb);
            sb.append(")");
          } else {
            sb.append(getRegex(part, ruleMap));
          }
        } else {
          sb.append(getRegex(part, ruleMap));
        }
      }
    }
  }

}