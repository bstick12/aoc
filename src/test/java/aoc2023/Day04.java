package aoc2023;

import utils.Utils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day04 {

  @SneakyThrows
  public static List<String> readFile() {
    //return Files.readAllLines(Path.of(Day03.class.getResource("day04_1.txt").toURI()));
    return Utils.getInputData(2023,4);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();
    int answer = 0;

    for (String line : lines) {
      String[] split = line.split(":");
      String[] split1 = split[1].split("\\|");
      Set<Integer> numbers = Arrays.stream(split1[0].trim().split("\\s+")).map(v -> Integer.valueOf(v)).collect(Collectors.toSet());
      Set<Integer> winning = Arrays.stream(split1[1].trim().split("\\s+")).map(v -> Integer.valueOf(v)).collect(Collectors.toSet());
      numbers.retainAll(winning);
      if(numbers.size() > 0) {
        answer += Math.pow(2, numbers.size() -1);
      }
    }

    log.info("Answer 1 - {}", answer);

  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> lines = readFile();
    Map<Integer, Integer> wins = new HashMap<>();
    int x = 1;
    for (String line : lines) {
      String[] split = line.split(":");
      String[] split1 = split[1].split("\\|");
      Set<Integer> numbers = Arrays.stream(split1[0].trim().split("\\s+")).map(v -> Integer.valueOf(v)).collect(Collectors.toSet());
      Set<Integer> winning = Arrays.stream(split1[1].trim().split("\\s+")).map(v -> Integer.valueOf(v)).collect(Collectors.toSet());
      numbers.retainAll(winning);
      wins.put(x,numbers.size());
      x++;
    }

    Map<Integer, Integer> card = new HashMap<>();
    for(int i=1;i<wins.size()+1;i++) {
      card.compute(i, (k, v) -> (v == null) ? 1 : v + 1);
      for(int j=i+1;j<=i+wins.get(i);j++) {
        int finalI = i;
        card.compute(j, (k, v) -> (v == null) ? card.get(finalI) : v + card.get(finalI));
      }
    }

    log.info("Answer 2 - {}", card.values().stream().mapToInt(Integer::intValue).sum());

  }

}
