package aoc2023;

import utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day03 {

  @SneakyThrows
  public static List<String> readFile() {
    //return Files.readAllLines(Path.of(Day03.class.getResource("day03_1.txt").toURI()));
    return Utils.getInputData(2023,3);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();
    int answer = 0;

    Pattern number = Pattern.compile("(\\d+)");
    Pattern symbol = Pattern.compile("([^0-9.])");

    Set<Point> symbols = new HashSet<>();
    int x = 0;
    for (String line : lines) {
      Matcher matcher = symbol.matcher(line);
      while(matcher.find()) {
        symbols.add(new Point(x, matcher.start()));
      }
      x++;
    }

    x = 0;
    for (String line : lines) {
      Matcher matcher = number.matcher(line);
      while(matcher.find()) {
        if(isAdjacent(symbols, x, matcher.start(), matcher.end() - matcher.start() - 1)) {
          answer += Integer.valueOf(matcher.group());
        }
      }
      x++;
    }

    log.info("Answer 1 - {}", answer);

  }

  private boolean isAdjacent(Set<Point> symbols, int x, int y, int i) {

    for(int j=x-1;j<=x+1;j++) {
      for(int k=y-1;k<=y+i+1;k++) {
        if(symbols.contains(new Point(j,k))) {
          return true;
        }
      }
    }
    return false;
  }

  private void isAdjacentCapture(Set<Point> symbols, int x, int y, int i, Map<Point, List<Integer>> points, int value) {
    for(int j=x-1;j<=x+1;j++) {
      for(int k=y-1;k<=y+i+1;k++) {
        if(symbols.contains(new Point(j,k))) {
          List<Integer> orDefault = points.getOrDefault(new Point(j, k), new ArrayList<>());
          orDefault.add(value);
          points.put(new Point(j,k), orDefault);
          return;
        }
      }
    }
  }

  public record Point (int x, int y) {}


  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();
    int answer = 0;

    Pattern number = Pattern.compile("(\\d+)");
    Pattern symbol = Pattern.compile("([^0-9.])");

    Set<Point> symbols = new HashSet<>();
    int x = 0;
    for (String line : lines) {
      Matcher matcher = symbol.matcher(line);
      while(matcher.find()) {
        if(matcher.group().equals("*")) {
          symbols.add(new Point(x, matcher.start()));
        }
      }
      x++;
    }


    Map<Point, List<Integer>> points = new HashMap<>();
    x = 0;
    for (String line : lines) {
      Matcher matcher = number.matcher(line);
      while(matcher.find()) {
        isAdjacentCapture(symbols, x, matcher.start(), matcher.end() - matcher.start() - 1, points, Integer.valueOf(matcher.group()));
      }
      x++;
    }

    for (List<Integer> value : points.values()) {
      if(value.size() > 1) {
        answer += value.stream().reduce(1, (a, b) -> a * b);
      }
    }

    log.info("Answer 1 - {}", answer);

  }

}
