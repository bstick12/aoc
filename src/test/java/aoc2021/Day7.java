package aoc2021;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day7 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(7);
    } else {
      return Files.readAllLines(Path.of(Day7.class.getResource("day07.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);
    List<Integer> collect = Arrays.stream(input.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());
    Collections.sort(collect);

    int start = collect.get(0);
    int end = collect.get(collect.size() -1);

    Map<Integer, Long> usages = new HashMap<>();

    for(int i=start;i<end;i++) {
      int finalI = i;
      usages.put(i, collect.stream().mapToLong(x -> Math.abs(finalI - x)).sum());
    }

    Map.Entry<Integer, Long> leastUsage = new AbstractMap.SimpleEntry<>(-1, Long.MAX_VALUE);
    for (Map.Entry<Integer, Long> integerLongEntry : usages.entrySet()) {
      if(integerLongEntry.getValue() < leastUsage.getValue()) {
        leastUsage = integerLongEntry;
      }
    }

    log.info("Ans 2 - {}", leastUsage);

  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(false);
    List<Integer> collect = Arrays.stream(input.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());
    Collections.sort(collect);

    int start = collect.get(0);
    int end = collect.get(collect.size() -1);

    Map<Integer, Long> usages = new HashMap<>();

    for(int i=start;i<end;i++) {
      int finalI = i;
      usages.put(i, collect.stream().mapToLong(x -> {
        int moves = Math.abs(finalI - x);
        return (moves * (moves + 1)) / 2;
      }).sum());
    }

    Map.Entry<Integer, Long> leastUsage = new AbstractMap.SimpleEntry<>(-1, Long.MAX_VALUE);
    for (Map.Entry<Integer, Long> integerLongEntry : usages.entrySet()) {
      if(integerLongEntry.getValue() < leastUsage.getValue()) {
        leastUsage = integerLongEntry;
      }
    }

    log.info("Ans 2 - {}", leastUsage);

  }

}
