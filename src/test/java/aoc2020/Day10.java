package aoc2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day10 {

  @SneakyThrows
  public static List<String> readFile() {
    //return Utils.getInputData(10);
    return Files.readAllLines(Path.of(Day07.class.getResource("day10.txt").toURI()));

  }

  @Test
  public void testPuzzle1() {
    List<String> input = readFile();

    List<Long> numbers = input.stream().map(Long::valueOf).collect(Collectors.toList());

    TreeSet<Long> x = new TreeSet<>(numbers);

    long device = x.last() + 3;
    x.add(device);
    long adapter=0;
    int ones = 0;
    int threes = 0;
    for (Long aLong : x) {
      if(aLong - adapter == 1) {
        ones++;
      }
      if(aLong - adapter == 3) {
        threes++;
      }
      adapter = aLong;
    }
    log.info("Ans {}", ones * threes);
  }

  @Test
  public void testPuzzle2() {
    List<String> input = readFile();

    List<Long> numbers = input.stream().map(Long::valueOf).collect(Collectors.toList());
    TreeSet<Long> sorted = new TreeSet<>(numbers);
    Map<Long, Long> counts = new HashMap<>();
    counts.put((long)0, (long)1);

    for (Long x : sorted) {
      counts.put(x, counts.getOrDefault(x-1,(long) 0)
          + counts.getOrDefault(x-2,(long) 0)
          + counts.getOrDefault(x-3,(long) 0));
    }

    log.info("Ans {}", counts.get(sorted.last()));

  }

}
