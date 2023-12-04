package aoc2022;

import utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day14 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day14.class.getResource("day14.txt").toURI()));
 //   return Utils.getInputData(2022,9);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Data
  @AllArgsConstructor
  public static class Point {
    int x;
    int y;
  }

  @Test
  public void day14_part1() {

    int ans = 0;

    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;

    Pattern compile = Pattern.compile("(\\d+,\\d+)( -> )*");

    List<String> lines = readFile();
    List<List<Point>> walls = new ArrayList<>();

    for (String line : lines) {
      Matcher matcher = compile.matcher(line);
      int find = 0;
      List<Point> points = new ArrayList<>();
      while(matcher.find()) {
        String group = matcher.group(1);
        String[] split = group.split(",");
        Point p = new Point(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
        minX = Math.min(minX, p.x);
        minY = Math.min(minY, p.y);
        maxX = Math.max(maxX, p.x);
        maxY = Math.max(maxY, p.y);
        points.add(p);
      }
      walls.add(points);
    }

    log.info("Part 1 - {} {} {} {}", minX, minY, maxX, maxY);


    String[][] grid = new String[maxX - minX + 1 * (maxY *2)][maxY + 1 + 2];
    Utils.initGrid(grid, ".");

    for(int i=0;i<grid.length;i++) {
      grid[i][maxY + 1 + 1] = "#";
    }

    for (List<Point> wall : walls) {
      for (int i = 0; i < wall.size() - 1; i++) {
        Point one = wall.get(i);
        Point two = wall.get(i+1);
        if(one.x == two.x) {
          for(int j=Math.min(one.y, two.y);j<=Math.max(one.y, two.y);j++) {
            grid[one.x - minX + maxY][j] = "#";
          }
        } else {
          for(int j=Math.min(one.x, two.x);j<=Math.max(one.x, two.x);j++) {
            grid[j - minX + maxY][one.y] = "#";
          }
        }

      }
    }

    int x = 500 - minX + maxY;
    int y = 0;
    while(true) {
      // Move Down
      String val = Utils.getSafeXY(x, y + 1, grid, "?");
      if(val == "?") {
        break;
      }
      if(val == ".") {
        y++;
      } else {
        val = Utils.getSafeXY(x - 1, y + 1, grid, "?");
        if(val == "?") {
          break;
        }
        if(val == ".") {
          y++;
          x--;
        } else {
          val = Utils.getSafeXY(x + 1, y + 1, grid, "?");
          if(val == "?") {
            break;
          }
          if(val == ".") {
            y++;
            x++;
          } else {
            if(grid[x][y] == "0") {
              break;
            }
            grid[x][y] = "0";
            ans++;
            x = 500 - minX + maxY;
            y = 0;
          }
        }
      }
    }

    Utils.printGridXY(grid);
    log.info("Answer {}", ans);

  }

  private boolean outOfBounds(int x, int y, int maxX, int maxY) {
    return x < 0 || y > maxY || x > maxX;
  }


}
