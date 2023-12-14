package aoc2023;

import static utils.Utils.getGrid;

import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;
import utils.Point;

@Slf4j
public class Day14 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 14);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static Map<Map.Entry<String, List<Integer>>, Long> CACHE = new HashMap();

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();
    Character[][] grid = getGrid(lines);
    spinGridLR(Point.NORTH, grid);
    log.info("Answer 1 - {}", getLoad(grid));
  }

  private static int getLoad(Character[][] grid) {
    int answer = 0;
    for (int y = 0; y < grid[0].length; y++) {
      for (int x = 0; x < grid.length; x++) {
        if(grid[x][y] == 'O') {
          answer += grid[0].length - y;
        }
      }
    }
    return answer;
  }

  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();

    Character[][] grid = getGrid(lines);

    List<Point> nw = List.of(Point.NORTH, Point.WEST);
    List<Point> se = List.of(Point.SOUTH, Point.EAST);

    Map<String, Map.Entry<Long, Integer>> gridHash = new HashMap<>();

    long loop = 1000000000;
    long i = 0;
    while(i<loop) {

      String key = Arrays.deepToString(grid);
      if(gridHash.containsKey(key)) {
        Map.Entry<Long, Integer> entry = gridHash.get(key);
        long cycle = i - entry.getKey();
        long answerIdx = entry.getKey() + (loop - entry.getKey()) % cycle;
        for (Map.Entry<Long, Integer> value : gridHash.values()) {
          if(value.getKey() == answerIdx) {
            log.info("Answer 2 - {}", value.getValue());
            return;
          }
        }
      } else {
        gridHash.put(key, Map.entry(i, getLoad(grid)));
      }

      for (Point spin : nw) {
        spinGridLR(spin, grid);
      }

      for (Point spin : se) {
        spinGridRL(spin, grid);
      }
      i++;
    }
  }

  private void spinGridRL(Point spin, Character[][] grid) {
    for (int y = grid[0].length - 1; y >= 0; y--) {
      for (int x = grid.length - 1; x >= 0; x--) {
        Point current = new Point(x, y);
        Character currentType = Utils.getSafeXY(current, grid, 'O');
        if (currentType == 'O') {
          Point direction = current.add(spin);
          Character directionType = Utils.getSafeXY(direction, grid, 'O');
          while (directionType == '.') {
            Utils.setSafeXY(direction, grid, 'O');
            Utils.setSafeXY(current, grid, '.');
            current = direction;
            direction = current.add(spin);
            directionType = Utils.getSafeXY(direction, grid, 'O');
          }
        }
      }
    }
  }

  private void spinGridLR(Point spin, Character[][] grid) {
    for (int y = 0; y < grid[0].length; y++) {
      for (int x = 0; x < grid.length; x++) {
        Point current = new Point(x, y);
        Character currentType = Utils.getSafeXY(current, grid, 'O');
        if (currentType == 'O') {
          Point direction = current.add(spin);
          Character directionType = Utils.getSafeXY(direction, grid, 'O');
          while (directionType == '.') {
            Utils.setSafeXY(direction, grid, 'O');
            Utils.setSafeXY(current, grid, '.');
            current = direction;
            direction = current.add(spin);
            directionType = Utils.getSafeXY(direction, grid, 'O');
          }
        }
      }
    }
  }

}

