package aoc2021;

import static utils.Utils.getSafe;
import static utils.Utils.initGrid;
import static utils.Utils.setSafe;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day11 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(11);
    } else {
      return Files.readAllLines(Path.of(Day11.class.getResource("day11.txt").toURI()));
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
    log.info("Ans 1 - {}", answer);

    int maxX = input.get(0).length();
    int maxY = input.size();

    Integer[][] grid = new Integer[maxY][maxX];
    initGrid(grid, 0);

    int y = 0;
    for (String s : input) {
      int x = 0;
      for (char c : s.toCharArray()) {
        grid[y][x] = Integer.valueOf(String.valueOf((char) c));
        x++;
      }
      y++;
    }

    int steps = 100;

    printGrid(grid);

    for(int s=0;s<steps;s++) {
      Boolean[][] flashed = new Boolean[maxY][maxX];
      initGrid(flashed, false);

      for (y = 0; y < maxY; y++) {
        for (int x = 0; x < maxX; x++) {
          step(x,y,grid,flashed);
        }
      }
      for (y = 0; y < maxY; y++) {
        for (int x = 0; x < maxX; x++) {
          if(getSafe(x,y,flashed, false)) {
            answer++;
          }
        }
      }
      printGrid(grid);
    }

    log.info("Ans 1 - {}", answer);


  }

  private void printGrid(Integer[][] grid) {
    for (Integer[] integers : grid) {
  //    log.info("{}", Arrays.toString(integers));
    }

  }

  private void step(int x, int y, Integer[][] grid, Boolean[][] flashed) {
    if(!getSafe(x,y,flashed,true)) {
      setSafe(x,y,grid,getSafe(x,y,grid,null) + 1);
      if(getSafe(x,y,grid,null) > 9) {
        setSafe(x,y,flashed, true);
        setSafe(x,y,grid,0);
        step(x,y+1, grid, flashed);
        step(x,y-1, grid, flashed);
        step(x+1,y, grid, flashed);
        step(x-1,y, grid, flashed);
        step(x+1,y+1, grid, flashed);
        step(x-1,y+1, grid, flashed);
        step(x+1,y-1, grid, flashed);
        step(x-1,y-1, grid, flashed);
      }
    }
  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(false);
    long answer = 0;

    int maxX = input.get(0).length();
    int maxY = input.size();

    Integer[][] grid = new Integer[maxY][maxX];
    initGrid(grid, 0);

    int y = 0;
    for (String s : input) {
      int x = 0;
      for (char c : s.toCharArray()) {
        grid[y][x] = Integer.valueOf(String.valueOf(c));
        x++;
      }
      y++;
    }

    int step = 0;

    while(true) {
      step++;
      long count=0;
      Boolean[][] flashed = new Boolean[maxY][maxX];
      initGrid(flashed, false);

      for (y = 0; y < maxY; y++) {
        for (int x = 0; x < maxX; x++) {
          step(x,y,grid,flashed);
        }
      }
      for (y = 0; y < maxY; y++) {
        for (int x = 0; x < maxX; x++) {
          if(getSafe(x,y,flashed, false)) {
            count++;
          }
        }
      }
      if(count == maxX * maxY) {
        break;
      }
    }

    log.info("Ans 2 - {}", step);

  }

}
