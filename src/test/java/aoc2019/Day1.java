package aoc2019;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.IntSupplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day1 {

  @Test
  public void testPuzzle1() throws URISyntaxException, IOException {

    List<String> input = Files.readAllLines(Path.of(Day1.class.getResource("day01.txt").toURI()));
    List<Integer> collect = input.stream().map(Integer::valueOf).collect(Collectors.toList());

    int total=0;

    log.info("Total " + collect.stream().mapToInt(new ToIntFunction<Integer>() {
      @Override
      public int applyAsInt(Integer value) {
        return (value / 3) - 2;
      }
    }).sum());

  }

  @Test
  public void testPuzzle2() throws URISyntaxException, IOException {

    List<String> input = Files.readAllLines(Path.of(Day1.class.getResource("day01.txt").toURI()));
    final List<Integer> collect = input.stream().map(Integer::valueOf).collect(Collectors.toCollection(CopyOnWriteArrayList::new));

    int total=0;

    log.info("Total " + collect.stream().mapToInt(new Mass()).sum());

  }

  public static class Mass implements ToIntFunction<Integer> {
    @Override
    public int applyAsInt(Integer value) {
      int result = (value / 3) - 2;
      if(result > 0) {
        return result + new Mass().applyAsInt(result);
      } else {
        return 0;
      }
    }
  };

}
