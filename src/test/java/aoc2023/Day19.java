package aoc2023;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import utils.Utils;


@Slf4j
public class Day19 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 19);
    //return Files.readAllLines(Path.of(Day15.class.getResource("day19_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static Map<Map.Entry<String, List<Integer>>, Long> CACHE = new HashMap();

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();

    Map<String, String> rules = new HashMap<>();
    int i;
    for (i = 0; i < lines.size(); i++) {
      if(lines.get(i).isBlank()) {
        break;
      } else {
        String[] split = lines.get(i).split("\\{");
        String ruleName = split[0];
        String ruleText = StringUtils.removeEnd(split[1], "}");
        StringBuilder rule = new StringBuilder();
        while(!ruleText.isBlank()) {
          String[] s = ruleText.split(":", 2);
          rule.append(s[0]).append(" ? ");
          ruleText = s[1];
          s = ruleText.split(",", 2);
          if(!s[0].contains(":")) {
            rule.append("'").append(s[0]).append("' : ");
            ruleText = s[1];
          }
          if (!s[1].contains(":")) {
            rule.append("'").append(s[1]).append("'");
            ruleText = "";
          }
        }
        rules.put(ruleName, rule.toString());
      }
    }
    i++;

    Pattern pattern = Pattern.compile("x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)");

    List<XMAS> xmases = new ArrayList<>();
    for (;i < lines.size(); i++) {
      Matcher matcher = pattern.matcher(lines.get(i));
      if(matcher.find()) {
        XMAS xmas = new XMAS(
            Integer.valueOf(matcher.group(1)),
            Integer.valueOf(matcher.group(2)),
            Integer.valueOf(matcher.group(3)),
            Integer.valueOf(matcher.group(4))
        );
        xmases.add(xmas);
      }
    }

    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine js = sem.getEngineFactories().get(0).getScriptEngine();

    List<XMAS> accepted = new ArrayList<>();
    for (XMAS xmas : xmases) {
      js.put("x", xmas.x());
      js.put("m", xmas.m());
      js.put("a", xmas.a());
      js.put("s", xmas.s());

      String rule = "in";
      boolean processing = true;
      while(processing) {
        String result = (String) js.eval(rules.get(rule));
        switch (result) {
          case "A":
            accepted.add(xmas);
            processing = false;
            break;
          case "R":
            processing = false;
            break;
          default:
            rule = result;
            break;
        }
      }

    }

    log.info("Answer 1 - {}", accepted.stream().map(x -> x.sum()).mapToInt(x -> x).sum());

  }

  @Test
  public void testPuzzle2() {

    List<String> lines = readFile();

    Map<String, Rule> rules = new HashMap<>();
    int i;
    for (i = 0; i < lines.size(); i++) {
      if(lines.get(i).isBlank()) {
        break;
      } else {
        String[] split = lines.get(i).split("\\{");
        String ruleName = split[0];
        String ruleText = StringUtils.removeEnd(split[1], "}");
        processRule(ruleName, ruleText, rules);
      }
    }

    for (Map.Entry<String, Rule> stringRuleEntry : rules.entrySet()) {
      log.info("{}", stringRuleEntry);
    }

    Queue<Map.Entry<String, XMASRange>> ranges = new LinkedList<>();

    ranges.add(Map.entry("in", new XMASRange(
        new Range(1, 4000),
        new Range(1, 4000),
        new Range(1, 4000),
        new Range(1, 4000)
    )));

    long answer = 0L;
    while(!ranges.isEmpty()) {
      Map.Entry<String, XMASRange> flow = ranges.remove();
      switch (flow.getKey()) {
        case "A":
          log.info("Accepted - {}", flow.getValue());
          answer += flow.getValue().product();
          break;
        case "R":
          break;
        default:
          Rule rule = rules.get(flow.getKey());
          Map.Entry<XMASRange, XMASRange> split = flow.getValue().split(rule);
          ranges.add(Map.entry(rule.left(), split.getKey()));
          ranges.add(Map.entry(rule.right(), split.getValue()));
      }
    }

    log.info("Answer 2 - {}", answer);

  }

  public void processRule(String ruleName, String ruleText, Map<String, Rule> rules) {
    if(ruleText.contains(":")) {
      String[] s = ruleText.split(":", 2);
      String[] r = s[1].split(",", 2);
      String range = s[0].substring(0, 1);
      String op = s[0].substring(1, 2);
      int value = Integer.valueOf(s[0].substring(2));
      Rule rule = new Rule(range, op, value, r[0], r[1]);
      rules.put(ruleName, rule);
      processRule(r[0], r[0], rules);
      processRule(r[1], r[1], rules);
    }
  }

  public record Rule(String range, String op, int value, String left, String right) {}

  public record Range(long min, long max) {

    Range copy() {
      return new Range(min, max);
    }

  }

  public record XMASRange (Range x, Range m, Range a, Range s) {

    public Range getRange(String name) {
      switch (name) {
        case "x":
          return x;
        case "m":
          return m;
        case "a":
          return a;
        case "s":
          return s;
        default:
          throw new RuntimeException();
      }
    }

    public XMASRange copy(String name, Range range) {
      switch (name) {
        case "x":
          return new XMASRange(range, m.copy(), a.copy(), s.copy());
        case "m":
          return new XMASRange(x.copy(), range, a.copy(), s.copy());
        case "a":
          return new XMASRange(x.copy(), m.copy(), range, s.copy());
        case "s":
          return new XMASRange(x.copy(), m.copy(), a.copy(), range);
        default:
          throw new RuntimeException();
      }
    }

    public Map.Entry<XMASRange, XMASRange> split(Rule rule) {
      Range range = getRange(rule.range);
      if(rule.op().equals("<")) {
        return Map.entry(
            copy(rule.range,new Range(range.min(), rule.value() - 1)),
            copy(rule.range,new Range(rule.value, range.max))
        );
      } else {
        return Map.entry(
            copy(rule.range,new Range(rule.value + 1, range.max())),
            copy(rule.range,new Range(range.min(), rule.value))
        );
      }
    }

    public long product() {
      return
          (x().max() -x().min() + 1) *
              (m().max() -m().min() + 1) *
              (a().max() -a().min() + 1) *
              (s().max() -s().min() + 1);
    }
  }

  public record XMAS (int x, int m, int a, int s) {

    public int sum() {
      return x + m + a + s;
    }

  };

}

