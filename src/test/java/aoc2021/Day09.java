package aoc2021;

import static utils.Utils.getSafe;
import static utils.Utils.initGrid;
import static utils.Utils.setSafe;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day09 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(9);
    } else {
      return Files.readAllLines(Path.of(Day09.class.getResource("day09.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);

    long answer = 0;
    int maxX = input.get(0).length();
    int maxY = input.size();

    Integer[][] grid = new Integer[maxY][maxX];
    initGrid(grid, 0);
    Boolean[][] low = new Boolean[maxY][maxX];
    initGrid(low, false);

    int y = 0;
    for (String s : input) {
      int x = 0;
      for (char c : s.toCharArray()) {
        grid[y][x] = Integer.valueOf(String.valueOf((char) c));
        x++;
      }
      y++;
    }

    for(y=0;y<maxY;y++) {
      for(int x=0;x<maxX;x++) {
        if(isLow(x,y,grid)) {
          mark(x,y,low);
        }
      }
    }

    for(y=0;y<maxY;y++) {
      for(int x=0;x<maxX;x++) {
        if(low[y][x]) {
          answer += (grid[y][x] + 1);
        }
      }
    }

    log.info("Ans 1 - {}", answer);

    List<Integer> basinSizes = new ArrayList<>();

    for(y=0;y<maxY;y++) {
      for(int x=0;x<maxX;x++) {
        if(low[y][x]) {
          basinSizes.add(findBasinSize(x,y,grid));
        }
      }
    }

    Collections.sort(basinSizes);
    Collections.reverse(basinSizes);

    log.info("Ans 2 - {}", basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2));

  }

  private Integer findBasinSize(int x, int y, Integer[][] grid) {
    Boolean[][] basin = new Boolean[grid.length][grid[0].length];
    initGrid(basin, false);
    spreadBasin(x,y,basin,grid);
    int answer = 0;
    for(int i=0;i<basin.length;i++) {
      for(int j=0;j<basin[0].length;j++) {
        if(basin[i][j]) {
          answer++;
        }
      }
    }
    return answer;
  }

  private void spreadBasin(int x, int y, Boolean[][] basin, Integer[][] grid) {

    if(getSafe(x, y, grid, 9) < 9 && !getSafe(x, y, basin, true)) {
      setSafe(x,y,basin, true);
      spreadBasin(x+1,y,basin,grid);
      spreadBasin(x-1,y,basin,grid);
      spreadBasin(x,y+1,basin,grid);
      spreadBasin(x,y-1,basin,grid);
    }

  }

  private void mark(int x, int y, Boolean[][] low) {

    setSafe(x,y,low,true);
    setSafe(x+1,y,low,false);
    setSafe(x-1,y,low,false);
    setSafe(x,y+1,low,false);
    setSafe(x,y-1,low,false);

  }

  private boolean isLow(int x, int y, Integer[][] map) {
    int value = getSafe(x,y,map,9);
    boolean lowest = true;
    lowest &= value < getSafe(x+1,y,map,9);
    lowest &= value < getSafe(x-1,y,map,9);
    lowest &= value < getSafe(x,y+1,map,9);
    lowest &= value < getSafe(x,y-1,map,9);
    return lowest;
  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(true);
    int answer = 0;
    log.info("Ans 2 - {}", answer);

  }

}
