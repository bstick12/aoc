package aoc2022;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day12 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day12.class.getResource("day12.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Data
  @AllArgsConstructor
  @EqualsAndHashCode
  public static class Point {
    int x;
    int y;
  }

  public static Character[][] grid;
  public static Integer[][] gridCosts;
  Map<Point, Integer> visited = new HashMap<>();

  @Test
  public void day12_breath() {

    int ans = 0;

    List<String> lines = readFile();

    int maxX = lines.size();
    int maxY = lines.get(0).length();
    grid = new Character[maxY][maxX];
    gridCosts = new Integer[maxY][maxX];
    Utils.initGrid(gridCosts, 0);

    Point start = new Point(0,0);

    for(int x=0;x<lines.size();x++) {
      char[] chars = lines.get(x).toCharArray();
      for (int y = 0; y < chars.length; y++) {
        grid[y][x] = chars[y];
        if(chars[y] == 'E') {
          grid[y][x] = 'z' + 1;
        }
      }
    }

    Utils.printGridPretty(grid);

    TreeSet<Integer> costs = new TreeSet<>();
    Queue<Point> current = new ArrayDeque<>();
    current.add(start);
    int step=0;
    visited.put(start,step);
    find(current, step, costs);

    log.info("Answer {}", costs);


    Utils.printGridPretty(gridCosts);


    log.info("Answer {}", costs.first());

  }

  private void find(Queue<Point> current, int step, TreeSet<Integer> costs) {
    Queue<Point> next = new ArrayDeque<>();
    step++;
    while(!current.isEmpty()) {
      Point point = current.poll();
      char val = Utils.getSafe(point.x, point.y, grid, 'X');

      if(val == '{') {
        costs.add(step-1);
        return;
      } else {
        extracted(step, next, val, point.x + 1, point.y);
        extracted(step, next, val, point.x - 1, point.y);
        extracted(step, next, val, point.x , point.y + 1);
        extracted(step, next, val, point.x , point.y - 1);
        find(next, step, costs);
      }
    }
  }

  private void extracted(int step, Queue<Point> next, char val, int x, int y) {
    Point point = new Point(x, y);
    char up = Utils.getSafe(point.x , point.y, grid, 'X');
    if(up != 'X' && (val >= up || val + 1 == up)) {
      if(visited.getOrDefault(point,Integer.MAX_VALUE) > step) {
        visited.put(point, step);
        next.add(point);
        gridCosts[point.y][point.x] = step;
      }
    }
  }

}
