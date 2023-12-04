package aoc2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day08 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(8);
    //return Files.readAllLines(Path.of(Day07.class.getResource("day08.txt").toURI()));
  }

  @Test
  public void testPuzzle1() {
    List<String> input = readFile();

    int acc = 0;
    int idx=0;
    Set<Integer> lines = new HashSet<>();
    while (true) {

      if(!lines.add(idx))
        break;
      String s = input.get(idx);
      String[] s1 = s.split(" ");
      switch (s1[0]) {
        case "nop":
          idx++;
          break;
        case "jmp":
          idx += NumberUtils.toInt(s1[1]);
          break;
        case "acc":
          acc += NumberUtils.toInt(s1[1]);
          idx++;
          break;
      }

    }
    log.info("Total = {}", acc);
  }

  @Test
  public void testPuzzle2() {
    List<String> input = readFile();

    boolean found = false;
    for(int i=0;i<input.size();i++) {

      List<String> copy = new ArrayList<>(input);
      copy.set(i,copy.get(i).replace("jmp", "nop"));

      Set<Integer> lines = new HashSet<>();
      int acc = 0;
      int idx=0;

      while (true) {
        if (idx == copy.size()) {
          found = true;
          break;
        }

        if (!lines.add(idx))
          break;

        String s = copy.get(idx);
        String[] s1 = s.split(" ");
        switch (s1[0]) {
          case "nop":
            idx++;
            break;
          case "jmp":
            idx += NumberUtils.toInt(s1[1]);
            break;
          case "acc":
            acc += NumberUtils.toInt(s1[1]);
            idx++;
            break;
        }
      }

      if(found) {
        log.info("Found = {}", acc);
        break;
      }
    }

  }

}
