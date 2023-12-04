package aoc2021;

import utils.Utils;
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
public class Day23 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(22);
    } else {
      return Files.readAllLines(Path.of(Day23.class.getResource("day22.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());

  }


  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);

    Pattern compile = Pattern.compile("(on|off)\\ x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");

    List<Step> steps = new ArrayList<>();

    for (String s : input) {
      Matcher matcher = compile.matcher(s);
      if (matcher.matches()) {
        String toggle = matcher.group(1);
        int x1 = Integer.valueOf(matcher.group(2));
        int x2 = Integer.valueOf(matcher.group(3));
        int y1 = Integer.valueOf(matcher.group(4));
        int y2 = Integer.valueOf(matcher.group(5));
        int z1 = Integer.valueOf(matcher.group(6));
        int z2 = Integer.valueOf(matcher.group(7));
        steps.add(new Step(toggle.equals("on"),
            new Bounds(new Point(x1,y1,z1),
                new Point(x2,y2,z2))));
      }
    }

    Bounds bounds = steps.get(0).getBounds();
    for (Step step : steps) {
      bounds = bounds.union(step.getBounds());
    }


  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(true);

    Pattern compile = Pattern.compile("(on|off)\\ x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");


  }


  @Data
  @AllArgsConstructor
  static class Step {
    boolean on;
    Bounds bounds;
  }

  @Data
  @AllArgsConstructor
  static class Point {

    int x,y,z;

    public Point max(Point max) {
      return new Point(Math.max(x, max.getX()), Math.max(y, max.getY()), Math.max(z, max.getZ()));
    }

    public Point min(Point min) {
      return new Point(Math.min(x, min.getX()), Math.min(y, min.getY()), Math.min(z, min.getZ()));
    }

  }

  @Data
  @AllArgsConstructor
  static class Bounds {
    Point min;
    Point max;

    public Bounds union(Bounds bounds) {
      return new Bounds(min.min(bounds.min), max.max(bounds.max));
    }

  }


}
