package aoc2021;

import static utils.Utils.countGrid;
import static utils.Utils.initGrid;
import static utils.Utils.printGrid;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day13 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(13);
    } else {
      return Files.readAllLines(Path.of(Day13.class.getResource("day13.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);

    List<Integer> xFolds = List.of(Integer.valueOf(input.get(input.size() - 12).split("=")[1]));
    List<Integer> yFolds = List.of(Integer.valueOf(input.get(input.size() - 11).split("=")[1]) * 2 + 1);

    int maxX = xFolds.get(xFolds.size()-1);
    int maxY = yFolds.get(yFolds.size()-1);

    String[][] grid = new String[maxY][maxX];
    initGrid(grid, " ");

    for(int i=0;i<input.size()-13;i++) {
      int x = Integer.valueOf(input.get(i).trim().split(",")[0]);
      int y = Integer.valueOf(input.get(i).trim().split(",")[1]);
      x = getPoint(x, xFolds);
      y = getPoint(y, yFolds);
      grid[y][x] = "#";
    }

    log.info("Ans 1 - {}", countGrid(grid,"#" ));

  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(false);

    List<Integer> xFolds = getFoldFor("x",input);
    List<Integer> yFolds = getFoldFor("y",input);

    int maxX = xFolds.get(xFolds.size()-1);
    int maxY = yFolds.get(yFolds.size()-1);

    String[][] grid = new String[maxY][maxX];
    initGrid(grid, " ");

    for(int i=0;i<input.size()-13;i++) {
      int x = Integer.valueOf(input.get(i).trim().split(",")[0]);
      int y = Integer.valueOf(input.get(i).trim().split(",")[1]);
      x = getPoint(x, xFolds);
      y = getPoint(y, yFolds);
      grid[y][x] = "#";
    }

    printGrid(grid);
    log.info("Ans 2 - {}", countGrid(grid, "#"));

  }

  private int getPoint(int point, List<Integer> folds) {
    for (int fold : folds) {
      if (point > fold) {
        point = fold - (point - fold);
      }
    }
    return point;
  }

  private List<Integer> getFoldFor(String fold, List<String> input) {
    List<Integer> folds = new ArrayList<>();
    for (int i = 12; i > 0; i--) {
      if (input.get(input.size() - i).split("=")[0].endsWith(fold)) {
        folds.add(Integer.valueOf(input.get(input.size() - i).split("=")[1]));
      }
    }
    return folds;
  }

}
