package aoc2023;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day10 {

  @SneakyThrows
  public static List<String> readFile() {
    //return Utils.getInputData(2023, 10);
    return Files.readAllLines(Path.of(Day07.class.getResource("day10_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    long answer = 0;

    List<String> lines = readFile();

    Character[][] grid = new Character[lines.get(0).length()][lines.size()];
    Point start = null;
    int y = 0;
    for (String line : lines) {
      int x = 0;
      for (char c : line.toCharArray()) {
        grid[x][y] = c;
        if (c == 'S') {
          start = new Point(x,y);
          grid[x][y] = '|';
        }
        x++;
      }
      y++;
    }

    char direction = 'N';
    int steps = 0;

    int x = start.x;
    y = start.y;
    while(true) {
      steps++;
      char currentPipe = grid[x][y];
      switch (currentPipe) {
        case '|':
          if(direction == 'N') {
            y--;
          } else {
            y++;
          }
          break;
        case '-':
          if(direction == 'W') {
            x--;
          } else {
            x++;
          }
          break;
        case 'F':
          if(direction == 'N') {
            direction = 'E';
            x++;
          } else {
            direction = 'S';
            y++;
          }
          break;
        case 'J':
          if(direction == 'S') {
            direction = 'W';
            x--;
          } else {
            direction = 'N';
            y--;
          }
          break;
        case 'L':
          if(direction == 'S') {
            direction = 'E';
            x++;
          } else {
            direction = 'N';
            y--;
          }
          break;
        case '7':
          if(direction == 'E') {
            direction = 'S';
            y++;
          } else {
            direction = 'W';
            x--;
          }
          break;
        default:
          throw new RuntimeException("Invalid character");
      }
      if(start.x == x && start.y == y && steps != 0) {
        break;
      }
    }

    log.info("Answer 1 - {}", steps / 2);

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

    long answer = 0;

    List<String> lines = readFile();

    Character[][] grid = new Character[lines.get(0).length()][lines.size()];
    Point start = null;
    int y = 0;
    for (String line : lines) {
      int x = 0;
      for (char c : line.toCharArray()) {
        grid[x][y] = c;
        if (c == 'S') {
          start = new Point(x,y);
          grid[x][y] = '|';
        }
        x++;
      }
      y++;
    }

    char direction = 'N';
    int steps = 0;

    int x = start.x;
    y = start.y;
    Set<Point> loop = new HashSet<>();
    Point current = start;
    while(true) {
      steps++;
      loop.add(current);
      char currentPipe = grid[current.x][current.y];
      switch (currentPipe) {
        case '|':
          if(direction == 'N') {
            current = current.add(Point.NORTH);
          } else {
            current = current.add(Point.SOUTH);
          }
          break;
        case '-':
          if(direction == 'W') {
            current = current.add(Point.WEST);
          } else {
            current = current.add(Point.EAST);
          }
          break;
        case 'F':
          if(direction == 'N') {
            direction = 'E';
            current = current.add(Point.EAST);
          } else {
            direction = 'S';
            current = current.add(Point.SOUTH);
          }
          break;
        case 'J':
          if(direction == 'S') {
            direction = 'W';
            current = current.add(Point.WEST);
          } else {
            direction = 'N';
            current = current.add(Point.NORTH);
          }
          break;
        case 'L':
          if(direction == 'S') {
            direction = 'E';
            current = current.add(Point.EAST);
          } else {
            direction = 'N';
            current = current.add(Point.NORTH);
          }
          break;
        case '7':
          if(direction == 'E') {
            direction = 'S';
            current = current.add(Point.SOUTH);
          } else {
            direction = 'W';
            current = current.add(Point.WEST);
          }
          break;
        default:
          throw new RuntimeException("Invalid character");
      }
      if(start.equals(current) && steps != 0) {
        break;
      }
    }

    for (x=0;x< grid.length;x++) {
      for (y=0;y<grid[x].length;y++) {
        if(!loop.contains(new Point(x,y))) {
          grid[x][y] = 'O';
        }
      }
    }

    Character[][] bigGrid = new Character[lines.get(0).length()*3][lines.size()*3];

    for (x=0;x< grid.length;x++) {
      for (y=0;y<grid[x].length;y++) {
        char type = grid[x][y];
        int bigX = x * 3;
        int bigY = y * 3;
        bigGrid[bigX + 0][bigY + 0] = '.';
        bigGrid[bigX + 0][bigY + 1] = '.';
        bigGrid[bigX + 0][bigY + 2] = '.';
        bigGrid[bigX + 1][bigY + 0] = '.';
        bigGrid[bigX + 1][bigY + 1] = '.';
        bigGrid[bigX + 1][bigY + 2] = '.';
        bigGrid[bigX + 2][bigY + 0] = '.';
        bigGrid[bigX + 2][bigY + 1] = '.';
        bigGrid[bigX + 2][bigY + 2] = '.';
        switch (type) {
          case 'O':
            break;
          case 'F':
            bigGrid[bigX + 1][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 2] = '*';
            bigGrid[bigX + 2][bigY + 1] = '*';
            break;
          case 'L':
            bigGrid[bigX + 1][bigY + 0] = '*';
            bigGrid[bigX + 1][bigY + 1] = '*';
            bigGrid[bigX + 2][bigY + 1] = '*';
            break;
          case 'J':
            bigGrid[bigX + 0][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 0] = '*';
            bigGrid[bigX + 1][bigY + 1] = '*';
            break;
          case '7':
            bigGrid[bigX + 0][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 2] = '*';
            break;
          case '|':
            bigGrid[bigX + 1][bigY + 0] = '*';
            bigGrid[bigX + 1][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 2] = '*';
            break;
          case '-':
            bigGrid[bigX + 0][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 1] = '*';
            bigGrid[bigX + 2][bigY + 1] = '*';
            break;
        }
      }
    }

    fill(bigGrid, new Point(0,0));

    int area = 0;
    for (x=0;x< grid.length;x++) {
      for (y=0;y < grid[x].length; y++) {
        if(bigGrid[(x*3) + 1][(y*3) + 1] == '.') {
          area++;
        }
      }
    }

    log.info("Answer 2 - {}", area);

  }

  public void fill(Character[][] grid, Point point) {
    Queue<Point> toFill = new ConcurrentLinkedQueue<>();
    toFill.add(point);
    int j=0;
    while(!toFill.isEmpty()) {
      Point remove = toFill.remove();
      if(Utils.getSafeXY(remove.x,remove.y, grid, '*') == '.') {
        Utils.setSafeXY(remove.x,remove.y, grid,'O');
        toFill.add(remove.add(Point.EAST));
        toFill.add(remove.add(Point.SOUTH));
        toFill.add(remove.add(Point.WEST));
        toFill.add(remove.add(Point.NORTH));
      }
    }
  }

}

