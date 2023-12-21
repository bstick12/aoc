package aoc2023;


import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Point;
import utils.Utils;


@Slf4j
public class Day21 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 21);
    //return Files.readAllLines(Path.of(Day15.class.getResource("day21_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static Map<Map.Entry<String, List<Integer>>, Long> CACHE = new HashMap();


  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();


    Character[][] grid = Utils.getGrid(lines);
    Point start = null;
    for(int y=0;y < grid[0].length;y++) {
      for(int x=0;x<grid.length;x++) {
        if(grid[x][y] == 'S') {
          start = new Point(x,y);
        }
      }
    }

    assert start != null;
    Set<Point> plots = Set.of(start);
    for(int i=0;i<256;i++) {
      Set<Point> newSlots = new HashSet<>();
      for (Point plot : plots) {
        for (Point cardinal : Point.CARDINALS) {
          Point add = plot.add(cardinal);
          if(Utils.getSafeXY(add, grid, '#') != '#') {
            newSlots.add(add);
          }
        }
      }
      plots = newSlots;
    }

    log.info("Answer - {}", plots.size());

  }


  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();


    Character[][] grid = Utils.getGrid(lines);
    Point start = null;
    for(int y=0;y < grid[0].length;y++) {
      for(int x=0;x<grid.length;x++) {
        if(grid[x][y] == 'S') {
          start = new Point(x,y);
        }
      }
    }

    int y = grid[0].length;
    int x = grid.length;
    int steps = 26501365;

    assert start != null;
    Set<Point> plots = Set.of(start);

    List<Integer> terms = new ArrayList<>();
    for(int i=0;i<steps && terms.size() < 3;i++) {
      Set<Point> newSlots = new HashSet<>();
      for (Point plot : plots) {
        for (Point cardinal : Point.CARDINALS) {
          Point add = plot.add(cardinal);
          Point check = new Point(add.x() % x, add.y() % y);
          check = check.add(new Point(x,y));
          check = new Point(check.x() % x, check.y() % y);
          if(Utils.getSafeXY(check, grid, '#') != '#') {
            newSlots.add(add);
          }
        }
      }

      if (i % x == steps % x) {
        terms.add(plots.size());
      }

      plots = newSlots;

    }

    log.info("Answer - {}", f(steps/x, terms));

  }

  public long f(long n, List<Integer> terms) {

    long b0 = terms.get(0);
    long b1 = terms.get(1) - terms.get(0);
    long b2 = terms.get(2) - terms.get(1);

    return b0 + (b1 * n) + (n * (n - 1) / 2) * (b2 - b1);

  }

}

