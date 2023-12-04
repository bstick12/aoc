package aoc2022;

import com.google.common.collect.Range;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day15 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day15.class.getResource("day15.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Data
  @AllArgsConstructor
  public static class Point implements Comparable<Point> {
    int x;
    int y;

    @Override
    public int compareTo(Point o) {
      return Integer.compare(x, o.x);
    }
  }

  private Map<Point, Integer> sensonManhattanDistance;

  @Test
  public void day15_part1() {

    Pattern compile = Pattern.compile("x=(-?\\d+), y=(-?\\d+)*");

    List<String> lines = readFile();

    sensonManhattanDistance = new HashMap<>();
    for (String line : lines) {
      Matcher matcher = compile.matcher(line);
      matcher.find();
      Point sensor = new Point(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));
      matcher.find();
      Point beacon = new Point(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));
      sensonManhattanDistance.put(sensor, Math.abs(sensor.x - beacon.x) + Math.abs(sensor.y - beacon.y));
    }

    Set<Point> points = new HashSet<>();
    int yDist=2000000;
    for (Map.Entry<Point, Integer> pointIntegerEntry : sensonManhattanDistance.entrySet()) {
      Point sensor = pointIntegerEntry.getKey();
      int distance = pointIntegerEntry.getValue();
      if(Range.open(sensor.y-distance, sensor.y+distance).contains(yDist)) {
        int b = Math.abs(yDist - sensor.y);
        for(int x=sensor.x - (distance - b);x<sensor.x + (distance - b);x++) {
          points.add(new Point(x,yDist));
        }
      }
    }

    log.info("Ans - {}", points.size());

    for (Map.Entry<Point, Integer> pointIntegerEntry : sensonManhattanDistance.entrySet()) {
      checkPerimeter(pointIntegerEntry.getKey(), pointIntegerEntry.getValue(), 4_000_000);
    }

  }

  private void extracted(Point point) {
    boolean contained = false;
    for (Map.Entry<Point, Integer> pointIntegerEntry : sensonManhattanDistance.entrySet()) {
      Point sensor = pointIntegerEntry.getKey();
      int distance = pointIntegerEntry.getValue();
      contained |= isContained(sensor, point, distance);
    }
    if(!contained) {
      log.info("Ans - {}", point);
      log.info("Ans - {}", (long) 4_000_000 * point.x + point.y);
      System.exit(0);
    }
  }

  private boolean isContained(Point sensor, Point point, int distance) {
    return Math.abs(sensor.x - point.x) + Math.abs(sensor.y - point.y) <= distance;
  }


  private void checkPerimeter(Point sensor, int distance, int square) {
    int boundary = distance + 1;

    Range zone = Range.closed(0, square);
    for(int x=0;x<boundary;x++) {
      Point point = new Point(sensor.x + x, sensor.y + boundary - x);
      if(zone.contains(point.x) && zone.contains(point.y)) {
        extracted(point);
      }
      point = new Point(sensor.x - boundary + x, sensor.y + x);
      if(zone.contains(point.x) && zone.contains(point.y)) {
        extracted(point);
      }
      point = new Point(sensor.x + boundary - x, sensor.y - x);
      if(zone.contains(point.x) && zone.contains(point.y)) {
        extracted(point);
      }
      point = new Point(sensor.x - x, sensor.y - boundary + x);
      if(zone.contains(point.x) && zone.contains(point.y)) {
        extracted(point);
      }
    }
  }

}
