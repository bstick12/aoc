package aoc2022;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day20 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day20.class.getResource("day20-test.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }


  @Test
  public void day20_part1() {

    int count=0;

    List<Integer> lines = readFileAsInts();

    int size = lines.size();
    int j=0;
    for(int i=0; i<size; i++) {
      int value = lines.remove(j);
      if (value == 0) {

      } else if(value > 0) {
        lines.add(j + value, value);
      }
      log.info("{} {}", value, lines);
    }


  }



}
