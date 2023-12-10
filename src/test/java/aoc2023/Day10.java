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
    return Utils.getInputData(2023, 10);
    //return Files.readAllLines(Path.of(Day07.class.getResource("day10_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public record Pair (String left , String right) {}

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

  public record Point(int x, int y) {}

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
    while(true) {
      steps++;
      loop.add(new Point(x,y));
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

    y = 0;
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
        switch (type) {
          case 'O':
            bigGrid[bigX + 0][bigY + 0] = '.';
            bigGrid[bigX + 0][bigY + 1] = '.';
            bigGrid[bigX + 0][bigY + 2] = '.';
            bigGrid[bigX + 1][bigY + 0] = '.';
            bigGrid[bigX + 1][bigY + 1] = '.';
            bigGrid[bigX + 1][bigY + 2] = '.';
            bigGrid[bigX + 2][bigY + 0] = '.';
            bigGrid[bigX + 2][bigY + 1] = '.';
            bigGrid[bigX + 2][bigY + 2] = '.';
            break;
          case 'F':
            bigGrid[bigX + 0][bigY + 0] = '.';
            bigGrid[bigX + 0][bigY + 1] = '.';
            bigGrid[bigX + 0][bigY + 2] = '.';
            bigGrid[bigX + 1][bigY + 0] = '.';
            bigGrid[bigX + 1][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 2] = '*';
            bigGrid[bigX + 2][bigY + 0] = '.';
            bigGrid[bigX + 2][bigY + 1] = '*';
            bigGrid[bigX + 2][bigY + 2] = '.';
            break;
          case 'L':
            bigGrid[bigX + 0][bigY + 0] = '.';
            bigGrid[bigX + 0][bigY + 1] = '.';
            bigGrid[bigX + 0][bigY + 2] = '.';
            bigGrid[bigX + 1][bigY + 0] = '*';
            bigGrid[bigX + 1][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 2] = '.';
            bigGrid[bigX + 2][bigY + 0] = '.';
            bigGrid[bigX + 2][bigY + 1] = '*';
            bigGrid[bigX + 2][bigY + 2] = '.';
            break;
          case 'J':
            bigGrid[bigX + 0][bigY + 0] = '.';
            bigGrid[bigX + 0][bigY + 1] = '*';
            bigGrid[bigX + 0][bigY + 2] = '.';
            bigGrid[bigX + 1][bigY + 0] = '*';
            bigGrid[bigX + 1][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 2] = '.';
            bigGrid[bigX + 2][bigY + 0] = '.';
            bigGrid[bigX + 2][bigY + 1] = '.';
            bigGrid[bigX + 2][bigY + 2] = '.';
            break;
          case '7':
            bigGrid[bigX + 0][bigY + 0] = '.';
            bigGrid[bigX + 0][bigY + 1] = '*';
            bigGrid[bigX + 0][bigY + 2] = '.';
            bigGrid[bigX + 1][bigY + 0] = '.';
            bigGrid[bigX + 1][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 2] = '*';
            bigGrid[bigX + 2][bigY + 0] = '.';
            bigGrid[bigX + 2][bigY + 1] = '.';
            bigGrid[bigX + 2][bigY + 2] = '.';
            break;
          case '|':
            bigGrid[bigX + 0][bigY + 0] = '.';
            bigGrid[bigX + 0][bigY + 1] = '.';
            bigGrid[bigX + 0][bigY + 2] = '.';
            bigGrid[bigX + 1][bigY + 0] = '*';
            bigGrid[bigX + 1][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 2] = '*';
            bigGrid[bigX + 2][bigY + 0] = '.';
            bigGrid[bigX + 2][bigY + 1] = '.';
            bigGrid[bigX + 2][bigY + 2] = '.';
            break;
          case '-':
            bigGrid[bigX + 0][bigY + 0] = '.';
            bigGrid[bigX + 0][bigY + 1] = '*';
            bigGrid[bigX + 0][bigY + 2] = '.';
            bigGrid[bigX + 1][bigY + 0] = '.';
            bigGrid[bigX + 1][bigY + 1] = '*';
            bigGrid[bigX + 1][bigY + 2] = '.';
            bigGrid[bigX + 2][bigY + 0] = '.';
            bigGrid[bigX + 2][bigY + 1] = '*';
            bigGrid[bigX + 2][bigY + 2] = '.';
            break;
        }
      }
    }

    Utils.printGridXY(bigGrid);
    fill(bigGrid, 0,0);
    Utils.printGridXY(bigGrid);

    int area = 0;
    for (x=0;x< grid.length;x++) {
      for (y=0;y < grid[x].length; y++) {
        if(bigGrid[(x*3) + 1][(y*3) + 1] == '.') {
          area++;
        }
      }
    }

    log.info("Answer 1 - {}", area);

  }

  public void fill(Character[][] grid, int x, int y) {
    Queue<Point> toFill = new ConcurrentLinkedQueue<Point>();
    toFill.add(new Point(x,y));
    while(!toFill.isEmpty()) {
      Point remove = toFill.remove();
      if(Utils.getSafeXY(remove.x,remove.y, grid, '*') == '.') {
        Utils.setSafeXY(remove.x,remove.y, grid,'O');
        if(Utils.getSafeXY(remove.x+1,remove.y, grid, '*') == '.') {
          toFill.add(new Point(remove.x + 1, remove.y));
        }
        if(Utils.getSafeXY(remove.x - 1,remove.y, grid, '*') == '.') {
          toFill.add(new Point(remove.x - 1, remove.y));
        }
        if(Utils.getSafeXY(remove.x + 1,remove.y, grid, '*') == '.') {
          toFill.add(new Point(remove.x + 1, remove.y));
        }
        if(Utils.getSafeXY(remove.x,remove.y + 1, grid, '*') == '.') {
          toFill.add(new Point(remove.x, remove.y + 1));
        }
        if(Utils.getSafeXY(remove.x,remove.y - 1, grid, '*') == '.') {
          toFill.add(new Point(remove.x, remove.y - 1));
        }
      }
    }

    Utils.printGridXY(grid);

  }

}

