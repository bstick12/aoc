package aoc2022;

import utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day01 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2022,1);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {
    List<String> collect = readFile();
    int x = 1;
    int elf = 1;
    int max = 0;
    int calories = 0;
    List<Integer> y = new ArrayList<>();
    for (String s : collect) {
      if(s.equals("")) {
        if(calories > max) {
          elf = x;
          max = calories;
        }
        y.add(calories);
        x++;
        calories=0;
      } else {
        calories += Integer.valueOf(s);
      }
    }
    log.info("Ans 1 - {} {}", elf, max);
    Collections.sort(y);
    Collections.reverse(y);
    int r = y.get(1) + y.get(2) + y.get(0);
    log.info("Ans 1 - {} {}", r, y.get(0));

  }


  @Test
  public void testPuzzle2() throws Exception {
    List<String> collect = readFile();
    int calories = 0;
    List<Integer> y = new ArrayList<>();
    for (String s : collect) {
      if(s.isEmpty()) {
        y.add(calories);
        calories=0;
      } else {
        calories += Integer.valueOf(s);
      }
    }
    Collections.sort(y);
    Collections.reverse(y);
    log.info("Ans 1 - {} ", y.get(0));
    log.info("Ans 2 - {}", y.subList(0,3).stream().collect(Collectors.summingInt(Integer::intValue)));
  }

}
