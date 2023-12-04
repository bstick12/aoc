package aoc2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day1 {

  @Test
  public void testPuzzle1() throws URISyntaxException, IOException {

    List<String> input = Files.readAllLines(Path.of(Day1.class.getResource("day01.txt").toURI()));
    Set<Integer> collect = input.stream().map(Integer::valueOf).collect(Collectors.toSet());

    for(int i : collect) {
      if (collect.contains(2020 - i)) {
        log.info("P1 = {} {} = {}", i, (2020 - i),  (2020 - i) * i);
        break;
      }
    }

  }


  @Test
  public void testPuzzle2() throws URISyntaxException, IOException {

    List<String> input = Files.readAllLines(Path.of(Day1.class.getResource("day01.txt").toURI()));
    Set<Integer> collect = input.stream().map(Integer::valueOf).collect(Collectors.toSet());

    outer:
    for(int i : collect) {
      int remainder = 2020 - i;
      for(int j : collect) {
        if (i != j) {
          if (collect.contains(remainder - j)) {
            log.info("P2 = {} {} {} = {}", i, j, (remainder - j),  (remainder - j) * i * j );
            break outer;
          }
        }
      }
    }

  }


}
