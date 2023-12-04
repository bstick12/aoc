package aoc2022;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day25 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day25.class.getResource("day25.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }


  @Test
  public void day25_part1() {

    List<String> lines = readFile();

    long answer = 0;

    for (String line : lines) {

      long number = 0;

      char[] chars = line.toCharArray();

      for (int i = 0; i < chars.length; i++) {
        int j = chars.length - 1 - i;
        if (StringUtils.isNumeric(String.valueOf(chars[i]))) {
          number += Integer.valueOf(String.valueOf(chars[i])) * Math.pow(5, j);
        } else if (chars[i] == '-') {
          number -= Math.pow(5, j);
        } else if (chars[i] == '=') {
          number -= 2 * Math.pow(5, j);
        } else {
          throw new RuntimeException("Failure"  + chars[i]);
        }
      }

      answer += number;

    }

    log.info("{}",answer);

    char[] snafu = "012=-".toCharArray();

    StringBuilder sb = new StringBuilder();
    while(answer>0) {
      sb.append(snafu[(int) (answer % 5)]);
      answer = (answer + 2) / 5;

    }
    log.info("{}", sb.reverse());



  }


}
