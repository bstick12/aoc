package aoc2022;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day08 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day08.class.getResource("day08.txt").toURI()));

    //return Utils.getInputData(2022,8);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }


//  Pattern compile = Pattern.compile("(on|off)\\ x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");
//
//  List<Day22.Step> steps = new ArrayList<>();
//
//    for (String s : input) {
//    Matcher matcher = compile.matcher(s);
//    if (matcher.matches()) {
//      String toggle = matcher.group(1);
//      int x1 = Integer.valueOf(matcher.group(2));
//      int x2 = Integer.valueOf(matcher.group(3));


  @Test
  public void testPuzzle1() throws Exception {

    Pattern compile = Pattern.compile("");

    List<String> lines = readFile();

    int maxX = lines.size();
    int maxY = lines.get(0).length();
    int count = (maxX * maxY) - (maxX - 2) * (maxY - 2);
    Integer[][] grid = new Integer[maxX][maxY];
    Utils.initGrid(grid, 0);

    int x = 0;
    for (String line : lines) {
      int y = 0;
      for (char c : line.toCharArray()) {
        grid[x][y] = Integer.valueOf(String.valueOf(c));
        y++;
      }
      x++;
    }

    for(x=1;x<maxX-1;x++) {
      for(int y=1;y<maxY-1;y++) {
        count += isVisibleX(x, y, grid) || isVisibleY(x, y, grid) ? 1 : 0;
      }
    }

    log.info("Ans 1 - {}", count);

    int max = 0;
    for(x=1;x<maxX-1;x++) {
      for(int y=1;y<maxY-1;y++) {
        max = Math.max(max, score(x,y,grid));
      }
    }

    log.info("Ans 1 - {}", max);


  }

  private int score(int x, int y, Integer[][] grid) {

    int value = grid[x][y];

    int topX = 0;
    for(int vX = x-1; vX>=0; vX--) {
      topX++;
      if(value <= grid[vX][y])
        break;
    }

    int botX = 0;
    for(int vX = x + 1; vX<grid[x].length; vX++) {
      botX++;
      if(value <= grid[vX][y] )
        break;
    }

    int topY = 0;
    for(int vY = y-1; vY>=0; vY--) {
      topY++;
      if(value <= grid[x][vY])
        break;
    }

    int botY = 0;
    for(int vY = y + 1; vY<grid[x].length; vY++) {
      botY++;
      if(value <= grid[x][vY])
        break;
    }

    return topX * topY * botX * botY;

  }

  private boolean isVisibleX(int x, int y, Integer[][] grid) {
    int value = grid[x][y];

    boolean visible = true;
    for(int vX = x-1; vX>=0; vX--) {
      if(grid[vX][y] >= value) {
        visible = false;
      }
    }
    if(visible) return true;

    visible = true;
    for(int vX = x + 1; vX<grid[x].length; vX++) {
      if(grid[vX][y] >= value) {
        visible = false;
      }
    }
    if(visible) return true;

    return false;
  }

  private boolean isVisibleY(int x, int y, Integer[][] grid) {
    int value = grid[x][y];
    // top
    boolean visible = true;
    for(int vY = y - 1; vY>=0; vY--) {
      if(grid[x][vY] >= value) {
        visible = false;
      }
    }
    if(visible) return true;

    //bottom
    visible = true;
    for(int vY = y + 1; vY<grid[x].length; vY++) {
      if(grid[x][vY] >= value) {
        visible = false;
      }
    }
    if(visible) return true;

    return false;
  }

}
