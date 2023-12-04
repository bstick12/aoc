package aoc2022;

import com.google.common.base.Splitter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day21 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day21.class.getResource("day21.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }


  @Test
  public void day21_part1() {

    int count=0;

    List<String> lines = readFile();

    Map<String, String> map = new HashMap<>();

    for (String line : lines) {
      String[] s = line.split(":");
      map.put(s[0].trim(), s[1].trim());
    }

    log.info("{}", evaluate("root", map));
    log.info("{} = {}", evaluate("rvrh", map) , evaluate("hzgl", map));

    for(long i=3453748220115L; i<3453748221115L; i++) {
      map.put("humn", String.valueOf(i));
//      if(evaluate("rvrh", map) > evaluate("hzgl", map)) {
        log.info("{} - {} = {}", i, evaluate("rvrh", map), evaluate("hzgl", map));
//      }
    }
  }

  private long evaluate(String key, Map<String, String> map) {

    String s = map.get(key);

    if(StringUtils.isNumeric(s)) {
      return Long.valueOf(s);
    } else {
      String[] s1 = s.split(" ");
      String op = s1[1];
      long a = evaluate(s1[0], map);
      long b = evaluate(s1[2], map);
      switch (op) {
        case "+":
          return a + b;
        case "/":
          return a / b;
        case "*":
          return a * b;
        case "-":
          return a - b;
        default:
          throw new RuntimeException("not here");
      }
    }

  }


//  3453730000000 - 638116961 = 586809113
//  3453740000000 - 609956964 = 586809113
//  3453750000000 - 581796968 = 586809113
//  3453760000000 - 553636962 = 586809113
//  3453770000000 - 525476966 = 586809113

}
