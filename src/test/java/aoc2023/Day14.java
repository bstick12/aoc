package aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class Day14 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 14);
    //return Files.readAllLines(Path.of(Day07.class.getResource("day14_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static Map<Map.Entry<String, List<Integer>>, Long> CACHE = new HashMap();

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();

    Character[][] grid = new Character[lines.get(0).length()][lines.size()];

    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.toCharArray().length; x++) {
        grid[x][y] = line.toCharArray()[x];
      }
    }

    for (int y = 0; y < grid[0].length; y++) {
      for (int x = 0; x < grid.length; x++) {
        Point current = new Point(x, y);
        Character currentType = Utils.getSafeXY(current.x, current.y, grid, 'O');
        if(currentType == 'O') {
          Point north = current.add(Point.NORTH);
          Character northType = Utils.getSafeXY(north.x, north.y, grid, 'O');
          while(northType == '.') {
            Utils.setSafeXY(north.x, north.y, grid, 'O');
            Utils.setSafeXY(current.x, current.y, grid, '.');
            current = north;
            north = current.add(Point.NORTH);
            northType = Utils.getSafeXY(north.x, north.y, grid, 'O');
          }
        }
      }
    }

    int answer = 0;

    for (int y = 0; y < grid[0].length; y++) {
      for (int x = 0; x < grid.length; x++) {
        if(grid[x][y] == 'O') {
          answer += grid[0].length - y;
        }
      }
    }

    log.info("{}", answer);

  }

  public record Point(int x, int y) {

    public static final Point NORTH = new Point(0,-1);
    public static final Point SOUTH = new Point(0,1);
    public static final Point EAST = new Point(1,0);
    public static final Point WEST = new Point(-1,0);

    Point add(Point point) {
      return new Point(point.x + x, point.y + y);
    }

  }

  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();

    Character[][] grid = new Character[lines.get(0).length()][lines.size()];

    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.toCharArray().length; x++) {
        grid[x][y] = line.toCharArray()[x];
      }
    }

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
            log.info("Answer {}", value.getValue());
            return;
          }
        }
      } else {
        int answer = 0;
        for (int y = 0; y < grid[0].length; y++) {
          for (int x = 0; x < grid.length; x++) {
            if(grid[x][y] == 'O') {
              answer += grid[0].length - y;
            }
          }
        }
        gridHash.put(key, Map.entry(i, answer));
      }

      for (Point spin : nw) {
        for (int y = 0; y < grid[0].length; y++) {
          for (int x = 0; x < grid.length; x++) {
            Point current = new Point(x, y);
            Character currentType = Utils.getSafeXY(current.x, current.y, grid, 'O');
            if (currentType == 'O') {
              Point direction = current.add(spin);
              Character directionType = Utils.getSafeXY(direction.x, direction.y, grid, 'O');
              while (directionType == '.') {
                Utils.setSafeXY(direction.x, direction.y, grid, 'O');
                Utils.setSafeXY(current.x, current.y, grid, '.');
                current = direction;
                direction = current.add(spin);
                directionType = Utils.getSafeXY(direction.x, direction.y, grid, 'O');
              }
            }
          }
        }
      }
      for (Point spin : se) {
        for (int y = grid[0].length - 1; y >= 0; y--) {
          for (int x = grid.length - 1; x >= 0; x--) {
            Point current = new Point(x, y);
            Character currentType = Utils.getSafeXY(current.x, current.y, grid, 'O');
            if (currentType == 'O') {
              Point direction = current.add(spin);
              Character directionType = Utils.getSafeXY(direction.x, direction.y, grid, 'O');
              while (directionType == '.') {
                Utils.setSafeXY(direction.x, direction.y, grid, 'O');
                Utils.setSafeXY(current.x, current.y, grid, '.');
                current = direction;
                direction = current.add(spin);
                directionType = Utils.getSafeXY(direction.x, direction.y, grid, 'O');
              }
            }
          }
        }
      }
      i++;
    }
  }

}

