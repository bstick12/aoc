package aoc2023;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day13 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 13);
    //return Files.readAllLines(Path.of(Day07.class.getResource("day13_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static Map<Map.Entry<String, List<Integer>>, Long> CACHE = new HashMap();

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();

    long answer = 0;

    List<String> grid = new ArrayList<>();
    for (String line : lines) {
      if(line.isEmpty()) {
        answer += mirrorValue(grid, -1);
        grid = new ArrayList<>();
      } else {
        grid.add(line);
      }
    }
    answer += mirrorValue(grid, -1);

    log.info("Answer {}", answer);

  }

  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();

    long answer = 0;

    List<String> grid = new ArrayList<>();
    for (String line : lines) {
      if(line.isEmpty()) {
        answer += findNew(grid, mirrorValue(grid, -1));
        grid = new ArrayList<>();
      } else {
        grid.add(line);
      }
    }

    answer += findNew(grid, mirrorValue(grid, -1));

    log.info("Answer {}", answer);

  }

  private long findNew(List<String> grid, int originalValue) {
    for (int i = 0; i < grid.size(); i++) {
      for (int j = 0; j < grid.get(i).length(); j++) {
        char[] tmp = grid.get(i).toCharArray();
        tmp[j] = tmp[j] == '#' ? '.' : '#';
        grid.set(i, new String(tmp));
        long l = mirrorValue(grid, originalValue);
        if(l != 0)
          return l;
        tmp[j] = tmp[j] == '#' ? '.' : '#';
        grid.set(i, new String(tmp));
      }
    }
    return 0;
  }

  private int mirrorValue(List<String> grid, int exclude) {

    // Check vertical
    Set<Integer> possibles = IntStream.range(0, grid.get(0).length()-1).boxed().collect(Collectors.toSet());
    possibles.remove(exclude - 1);
    int lineSize = grid.get(0).length();
    for (String line : grid) {
      for(int i=0;i<lineSize;i++) {
        if(!possibles.contains(i)) {
          continue;
        }
        int j=i;
        int k=j+1;
        for(;j>=0 && k < lineSize;j--,k++) {
          if(line.charAt(j) != line.charAt(k)) {
            possibles.remove(i);
            break;
          }
        }
      }
    }

    if(!possibles.isEmpty()) {
      return possibles.stream().findFirst().get() + 1;
    }

    // Check horizontal
    possibles = IntStream.range(0, grid.size() - 1).boxed().collect(Collectors.toSet());
    possibles.remove((exclude/100) - 1);
    lineSize = grid.size();
    for (int x = 0; x < grid.get(0).length(); x++) {
      for(int i=0;i<lineSize;i++) {
        if(!possibles.contains(i)) {
          continue;
        }
        int j=i;
        int k=j+1;
        for(;j>=0 && k < lineSize;j--,k++) {
          if(grid.get(j).charAt(x) != grid.get(k).charAt(x)) {
            possibles.remove(i);
            break;
          }
        }
      }
    }

    if(!possibles.isEmpty()) {
      return (possibles.stream().findFirst().get() + 1) * 100;
    }

    return 0;
  }

}

