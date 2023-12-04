package aoc2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day11 {

  @SneakyThrows
  public static List<String> readFile() {
    boolean test = false;
    if(!test) {
      return Utils.getInputData(11);
    } else {
      return Files.readAllLines(Path.of(Day11.class.getResource("day11.txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {
    List<String> input = readFile();
    Iterator<String> iterator = input.iterator();
    int answer = 0;
    char[][] plan = new char[input.size()][input.get(0).length()];

    for (int i=0;i<input.size();i++) {
      char[] chars = input.get(i).toCharArray();
      for (int j=0;j<chars.length;j++) {
        plan[i][j] = chars[j];
      }
    }

    int count = 0;
    while(true) {
      count++;
      char[][] newplan = new char[input.size()][input.get(0).length()];
      for (int i = 0; i < plan.length; i++) {
        for (int j = 0; j < plan[i].length; j++) {
          List<Character> adjacents = new ArrayList<>();
          getValue(plan, i - 1, j - 1, adjacents);
          getValue(plan, i - 1, j, adjacents);
          getValue(plan, i - 1, j + 1, adjacents);
          getValue(plan, i, j - 1, adjacents);
          getValue(plan, i, j + 1, adjacents);
          getValue(plan, i + 1, j - 1, adjacents);
          getValue(plan, i + 1, j, adjacents);
          getValue(plan, i + 1, j + 1, adjacents);
          if (plan[i][j] == 'L') {
            if (adjacents.stream().allMatch(x -> x == 'L' || x == '.')) {
              newplan[i][j] = '#';
            } else {
              newplan[i][j] = 'L';
            }
          } else if (plan[i][j] == '#') {
            if (adjacents.stream().filter(x -> x == '#').count() >= 4) {
              newplan[i][j] = 'L';
            } else {
              newplan[i][j] = '#';
            }
          } else {
            newplan[i][j] = plan[i][j];
          }
        }
      }
      if(Arrays.deepEquals(plan, newplan)) {
        log.info("Ans {} {}", count(newplan), count);
        break;
      }
      plan = newplan;
    }

  }

  public void getValue(char[][] plan, int i, int j, List<Character> adjacents) {
    try {
      adjacents.add(plan[i][j]);
    } catch (Exception e) {
    }
  }

  public int count(char[][] plan) {

    int count=0;
    for(int i=0;i<plan.length;i++) {
      for(int j=0;j<plan[i].length;j++) {
        if(plan[i][j] == '#') {
          count++;
        }
      }
    }
    return count;
  }

  @Test
  public void testPuzzle2() {
    List<String> input = readFile();
    Iterator<String> iterator = input.iterator();
    int answer = 0;
    char[][] plan = new char[input.size()][input.get(0).length()];

    for (int i=0;i<input.size();i++) {
      char[] chars = input.get(i).toCharArray();
      for (int j=0;j<chars.length;j++) {
        plan[i][j] = chars[j];
      }
    }

    int count = 0;
    while(true) {
      count++;
      char[][] newplan = new char[input.size()][input.get(0).length()];
      for (int i = 0; i < plan.length; i++) {
        for (int j = 0; j < plan[i].length; j++) {
          List<Character> adjacents = new ArrayList<>();
          getVisible(plan, i,j, - 1, -1, adjacents);
          getVisible(plan, i,j, - 1, 0, adjacents);
          getVisible(plan, i,j, - 1, 1, adjacents);
          getVisible(plan, i,j, 0, -1, adjacents);
          getVisible(plan, i,j, 0, 1, adjacents);
          getVisible(plan, i,j, 1, -1, adjacents);
          getVisible(plan, i,j, 1, 0, adjacents);
          getVisible(plan, i,j, 1, 1, adjacents);

          if (plan[i][j] == 'L') {
            if (adjacents.stream().allMatch(x -> x == 'L' || x == '.')) {
              newplan[i][j] = '#';
            } else {
              newplan[i][j] = 'L';
            }
          } else if (plan[i][j] == '#') {
            if (adjacents.stream().filter(x -> x == '#').count() >= 5) {
              newplan[i][j] = 'L';
            } else {
              newplan[i][j] = '#';
            }
          } else {
            newplan[i][j] = plan[i][j];
          }
        }
      }

      if(Arrays.deepEquals(plan, newplan)) {
        log.info("Ans {} {}", count(newplan), count);
        break;
      }
      plan = newplan;
    }

  }

  public void getVisible(char[][] plan, int i, int j, int ioff, int joff, List<Character> adjacents) {
    try {
      i+=ioff;
      j+=joff;
      if(plan[i][j] == '.') {
        getVisible(plan, i, j, ioff, joff, adjacents);
      } else {
        adjacents.add(plan[i][j]);
      }
    } catch (Exception e) {
    }
  }

}
