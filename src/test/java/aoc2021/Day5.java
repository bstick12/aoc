package aoc2021;

import com.google.common.collect.Maps;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day5 {

  @SneakyThrows
  public static List<String> readFile() {
    //return Utils.getInputData(5);
    return Files.readAllLines(Path.of(Day5.class.getResource("day05.txt").toURI()));
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile();
    long answer = 0;

    Pattern pattern = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

    Map<String, Integer> lines = Maps.newHashMap();

    for(String s: input) {
      Matcher m = pattern.matcher(s);

      if(m.find()) {
        int x1=Integer.valueOf(m.group(1));
        int y1=Integer.valueOf(m.group(2));
        int x2=Integer.valueOf(m.group(3));
        int y2=Integer.valueOf(m.group(4));

        if(x1 == x2) {
          lines.put(x1 + "-" + y1, lines.getOrDefault(x1 + "-" + y1, 0) + 1);
          lines.put(x2 + "-" + y2, lines.getOrDefault(x2 + "-" + y2, 0) + 1);
          for(int a=Math.min(y1,y2)+1;a<Math.max(y1,y2);a++) {
            lines.put(x1 + "-" + a, lines.getOrDefault(x1 + "-" + a, 0) + 1);
          }
        }if (y1 == y2) {
          lines.put(x1 + "-" + y1, lines.getOrDefault(x1 + "-" + y1, 0) + 1);
          lines.put(x2 + "-" + y2, lines.getOrDefault(x2 + "-" + y2, 0) + 1);
          for(int a=Math.min(x1,x2)+1;a<Math.max(x1,x2);a++) {
            lines.put(a + "-" + y1, lines.getOrDefault(a + "-" + y1, 0) + 1);
          }
        }
      }
    }


    answer = lines.entrySet().stream().filter(x -> x.getValue() > 1).count();

    log.info("Ans 1 - {}", answer);

  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile();
    long answer = 0;

    Pattern pattern = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

    Map<String, Integer> lines = Maps.newHashMap();

    for(String s: input) {
      Matcher m = pattern.matcher(s);

      if(m.find()) {
        int x1=Integer.valueOf(m.group(1));
        int y1=Integer.valueOf(m.group(2));
        int x2=Integer.valueOf(m.group(3));
        int y2=Integer.valueOf(m.group(4));

        if(x1 == x2) {
          lines.put(x1 + "-" + y1, lines.getOrDefault(x1 + "-" + y1, 0) + 1);
          lines.put(x2 + "-" + y2, lines.getOrDefault(x2 + "-" + y2, 0) + 1);
          for(int a=Math.min(y1,y2)+1;a<Math.max(y1,y2);a++) {
            lines.put(x1 + "-" + a, lines.getOrDefault(x1 + "-" + a, 0) + 1);
          }
        } else if (y1 == y2) {
          lines.put(x1 + "-" + y1, lines.getOrDefault(x1 + "-" + y1, 0) + 1);
          lines.put(x2 + "-" + y2, lines.getOrDefault(x2 + "-" + y2, 0) + 1);
          for(int a=Math.min(x1,x2)+1;a<Math.max(x1,x2);a++) {
            lines.put(a + "-" + y1, lines.getOrDefault(a + "-" + y1, 0) + 1);
          }
        } else if (Math.abs(x1 - x2) == Math.abs(y1 - y2)) {
          lines.put(x1 + "-" + y1, lines.getOrDefault(x1 + "-" + y1, 0) + 1);
          lines.put(x2 + "-" + y2, lines.getOrDefault(x2 + "-" + y2, 0) + 1);
          if(x1 < x2) {
            for(int a=x1+1;a<x2;a++) {
              if(y1 < y2) {
                lines.put(a + "-" + (y1 + (a-x1)), lines.getOrDefault(a + "-" + (y1 + (a-x1)), 0) + 1);
              } else {
                lines.put(a + "-" + (y1 - (a-x1)), lines.getOrDefault(a + "-" + (y1 - (a-x1)), 0) + 1);
              }
            }
          } else {

            for(int a=x2+1;a<x1;a++) {
              if(y1 < y2) {
                lines.put(a + "-" + (y2 - (a-x2)), lines.getOrDefault(a + "-" + (y2 - (a-x2)), 0) + 1);
              } else {
                 lines.put(a + "-" + (y2 + (a-x2)), lines.getOrDefault(a + "-" + (y2 + (a-x2)), 0) + 1);
              }
            }
          }

        } else {
          throw new RuntimeException("Blah blah blah");
        }
      }
    }


    answer = lines.entrySet().stream().filter(x -> x.getValue() > 1).count();

    log.info("Ans 1 - {}", answer);


  }

  @Data
  private static class Point {
    int x;
    int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

  }

  static Map<Point, Integer> ventCoverage = new HashMap<>();

  private static class Vent {
    public Vent(Point a, Point b) {
      int deltaX = Math.abs(a.x - b.x);
      int deltaY = Math.abs(a.y - b.y);
      Point start = a.x <= b.x ? a : b;
      Point end = start == a ? b : a;
      int run = Math.max(deltaX, deltaY);
      for (int i = 0; i <= run; i++) {
        Point p;
        if (end.y > start.y) {
          p = new Point(start.x + deltaX, start.y + deltaY);
        } else {
          p = new Point(start.x + deltaX, start.y - deltaY);
        }
        ventCoverage.put(p, ventCoverage.getOrDefault(p,0) + 1);
        if (deltaX > 0) deltaX--;
        if (deltaY > 0) deltaY--;
      }
    }
  }

  @Test
  public void blahBlah() {

    Pattern pattern = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

    Map<Point, Integer> points = new ConcurrentHashMap<>();

    readFile().stream()
    .map(x -> {
      Matcher m = pattern.matcher(x);
      if (m.find()) {
        Point a = new Point(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)));
        Point b = new Point(Integer.valueOf(m.group(3)), Integer.valueOf(m.group(4)));
        return new AbstractMap.SimpleImmutableEntry(a, b);
      }
      throw new RuntimeException("Invalid Input");
    }
    ).forEach(x -> walkPoints(x, points));

    long answer = points.entrySet().stream().filter(x -> x.getValue() > 1).count();
    log.info("Ans 1 - {}", answer);

  }

  private void walkPoints(Map.Entry<Point,Point> line, Map<Point, Integer> points) {

    Point a = line.getKey();
    Point b = line.getValue();

    int deltaX = Math.abs(a.x - b.x);
    int deltaY = Math.abs(a.y - b.y);
    Point start = a.x <= b.x ? a : b;
    Point end = start == a ? b : a;
    int run = Math.max(deltaX, deltaY);
    for (int i = 0; i <= run; i++) {
      Point p;
      if (end.y > start.y) {
        p = new Point(start.x + deltaX, start.y + deltaY);
      } else {
        p = new Point(start.x + deltaX, start.y - deltaY);
      }
      points.put(p, points.getOrDefault(p,0) + 1);
      if (deltaX > 0) deltaX--;
      if (deltaY > 0) deltaY--;
    }

  }



}