package aoc2023;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day06 {

  @SneakyThrows
  public static List<String> readFile() {
    //return Files.readAllLines(Path.of(Day03.class.getResource("day06_1.txt").toURI()));
    return Utils.getInputData(2023,6);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> lines = readFile();
    List<Long> times =
        Arrays.stream(lines.get(0).split(":")[1].trim().split("\s+")).map(v -> Long.valueOf(v)).collect(Collectors.toList());

    List<Long> distances =
        Arrays.stream(lines.get(1).split(":")[1].trim().split("\s+")).map(v -> Long.valueOf(v)).collect(Collectors.toList());

    long answer = 1;

    for(int i=0;i<times.size();i++) {
      long wins = 0;
      for(int j=1;j<times.get(i);j++) {
        if((times.get(i) - j) * j > distances.get(i)) {
          wins++;
        }
      }
      answer *= wins != 0 ? wins : 1;
    }
    log.info("{} {}", times, distances);

    log.info("Answer 1 - {}", answer);

  }

  public record Range (long destination , long range) {}

  @Test
  public void testPuzzle2() throws Exception {

    List<String> lines = readFile();
    List<Long> times =
        List.of(Long.valueOf(lines.get(0).split(":")[1].trim().replaceAll("\s+ ", "")));

    List<Long> distances =
        List.of(Long.valueOf(lines.get(1).split(":")[1].trim().replaceAll("\s+ ", "")));
    long answer = 1;

    for(int i=0;i<times.size();i++) {
      long wins = 0;
      for(int j=1;j<times.get(i);j++) {
        if((times.get(i) - j) * j > distances.get(i)) {
          wins++;
        }
      }
      answer *= wins != 0 ? wins : 1;
    }
    log.info("{} {}", times, distances);

    log.info("Answer 1 - {}", answer);

  }

}
