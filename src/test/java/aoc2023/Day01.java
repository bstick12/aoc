package aoc2023;

import utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day01 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023,1);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();

    Pattern pattern = Pattern.compile("\\d");

    int answer = 0;
    for (String line : lines) {
      Matcher matcher = pattern.matcher(line);
      List<Integer> ints= new ArrayList<>();
      while(matcher.find()) {
        ints.add(Integer.valueOf(matcher.group()));
      }
      answer += ints.get(0) * 10 + ints.get(ints.size()-1);
    }

    log.info("Answer 1 - {}", answer);

  }


  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();

    Map<String, Integer> numberMap = new HashMap<>();
    numberMap.put("one", 1);
    numberMap.put("two", 2);
    numberMap.put("three", 3);
    numberMap.put("four", 4);
    numberMap.put("five", 5);
    numberMap.put("six", 6);
    numberMap.put("seven", 7);
    numberMap.put("eight", 8);
    numberMap.put("nine", 9);
    numberMap.put("1", 1);
    numberMap.put("2", 2);
    numberMap.put("3", 3);
    numberMap.put("4", 4);
    numberMap.put("5", 5);
    numberMap.put("6", 6);
    numberMap.put("7", 7);
    numberMap.put("8", 8);
    numberMap.put("9", 9);

    int answer = 0;
    for (String line : lines) {
      List<Integer> ints = new ArrayList<>();
      for(int i =0; i < line.length(); i++) {
        for (String s : numberMap.keySet()) {
          if(line.substring(i, line.length()).startsWith(s)) {
            ints.add(numberMap.get(s));
            break;
          }
        }
      }
      answer += ints.get(0) * 10 + ints.get(ints.size() - 1);
    }
    log.info("Answer 1 - {}", answer);
  }

}
