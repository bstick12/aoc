package aoc2021;

import utils.Utils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day1 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(1);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {
    int count=0;
    List<Integer> collect = readFileAsInts();
    for(int x=0;x<collect.size()-1;x++) {
      count += collect.get(x+1) > collect.get(x) ? 1 : 0;
    }
    log.info("Ans 1 - {}", count);
  }


  @Test
  public void testPuzzle2() throws Exception {
    int count=0;
    List<Integer> collect = readFileAsInts();
    for(int x=0;x<collect.size()-3;x++) {
      count += collect.get(x+3) > collect.get(x) ? 1 : 0;
    }
    log.info("Ans 2 - {}", count);
  }

}
