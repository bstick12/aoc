package aoc2020;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day09 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(9);
    //return Files.readAllLines(Path.of(Day07.class.getResource("day09.txt").toURI()));

  }

  @Test
  public void testPuzzle1() {
    List<String> input = readFile();

    List<Long> numbers = input.stream().map(Long::valueOf).collect(Collectors.toList());

    int preamble = 25;

    done:
    for(int i=preamble; i<input.size();i++) {

      Set<Long> candidates = new HashSet<>(numbers.subList(i - preamble, i));
      boolean found = false;

      Long answer = numbers.get(i);
      for (Long candidate : candidates) {
        if (candidates.contains(answer - candidate)) {
          found=true;
          break;
        }
      }
      if(!found) {
        log.info("Ans P1 {}", answer);
        for (int j = 0; j < numbers.size(); j++) {
          long total = numbers.get(j);
          for (int k = j + 1; k < numbers.size() && total < answer; k++) {
            total += numbers.get(k);
            if (total == answer) {
              TreeSet<Long> values = new TreeSet<>(numbers.subList(j, k));
              log.info("Ans P2 {}", values.first() + values.last());
              break done;
            }
          }
        }
      }
    }
  }



}
