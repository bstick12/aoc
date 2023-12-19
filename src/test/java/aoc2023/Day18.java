package aoc2023;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Point;
import utils.Utils;

@Slf4j
public class Day18 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 18);
    //return Files.readAllLines(Path.of(Day15.class.getResource("day18_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static Map<Map.Entry<String, List<Integer>>, Long> CACHE = new HashMap();

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();

    Map<String, Point> directions = Map.of(
        "R", Point.EAST,
        "L", Point.WEST,
        "U", Point.NORTH,
        "D", Point.SOUTH
    );

    int down = 1;
    int right = 1;
    for (String line : lines) {
      String[] s = line.split(" ");
      String direction = s[0];
      int moves = Integer.valueOf(s[1]);
      if(direction.equals("R"))
        right += moves;
      if(direction.equals("D"))
        down += moves;
    }

    Character[][] grid = new Character[right*2][down*2];
    Utils.initGrid(grid, '.');

    Point start = new Point(right,down);
    Utils.setSafeXY(start, grid, '#');
    for (String line : lines) {
      String[] s = line.split(" ");
      String direction = s[0];
      int moves = Integer.valueOf(s[1]);
      for (int i = 0; i < moves; i++) {
        start = start.add(directions.get(direction));
        Utils.setSafeXY(start, grid, '#');
      }
    }

    fill(grid, new Point(0,0));

    log.info("Answer 1 - {}", countLava(grid));
  }

  @Test
  public void testPuzzle1Shoelace() throws Exception {
    List<String> lines = readFile();

    Map<String, Point> directions = Map.of(
        "R", Point.EAST,
        "L", Point.WEST,
        "U", Point.NORTH,
        "D", Point.SOUTH
    );

    Point current = new Point(0,0);
    long area = 0;
    long perimeter = 0;
    for (String line : lines) {
      String[] s = line.split(" ");
      Point direction = directions.get(s[0]);
      int moves = Integer.valueOf(s[1]);
      perimeter += moves;
      Point next = current.add(new Point(direction.x() * moves, direction.y() * moves));
      long i = (current.x() * next.y()) - (current.y() * next.x());
      area += i;
      current = next;
    }
    log.info("Answer 1 - {}", (area / 2) + (perimeter / 2) + 1);
  }

  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();

    Map<String, Point> directions = Map.of(
        "0", Point.EAST,
        "1", Point.SOUTH,
        "2", Point.WEST,
        "3", Point.NORTH
    );

    Point current = new Point(0,0);
    long area = 0;
    long perimeter = 0;
    for (String line : lines) {
      String[] s = line.split(" ");
      Point direction = directions.get(s[2].substring(7,8));
      long moves = Integer.valueOf(s[2].substring(2,7), 16);
      perimeter += moves;
      Point next = current.add(new Point(direction.x() * (int) moves, direction.y() * (int) moves));
      BigInteger a = BigInteger.valueOf(current.x()).multiply(BigInteger.valueOf(next.y()));
      BigInteger b = BigInteger.valueOf(current.y()).multiply(BigInteger.valueOf(next.x()));
      BigInteger c = a.subtract(b);
      area += c.longValue();
      current = next;
    }

    log.info("Answer 2 - {}", (area / 2) + (perimeter / 2) + 1);

  }

  public int countLava(Character[][] grid) {
    int lava = 0;
    for(int i=0;i < grid[0].length;i++) {
      for(int j=0;j<grid.length;j++) {
        if(grid[j][i] != 'O') {
          lava++;
        }
      }
    }
    return lava;
  }

  public void fill(Character[][] grid, Point point) {
    Queue<Point> toFill = new ConcurrentLinkedQueue<>();
    toFill.add(point);
    int j=0;
    while(!toFill.isEmpty()) {
      Point remove = toFill.remove();
      if(Utils.getSafeXY(remove, grid, '*') == '.') {
        Utils.setSafeXY(remove, grid,'O');
        toFill.add(remove.add(Point.EAST));
        toFill.add(remove.add(Point.SOUTH));
        toFill.add(remove.add(Point.WEST));
        toFill.add(remove.add(Point.NORTH));
      }
    }
  }



}

