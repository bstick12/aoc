package aoc2022;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day09 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day09.class.getResource("day09.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }


  @Test
  public void day09() {

    int followCount = 9;
    List<String> lines = readFile();

    Set<Point> visited = new HashSet<>();

    Point head = new Point(0,0);
    Map<Integer, Point> followers = new HashMap<>();
    for(int i=1;i<=followCount;i++) {
      followers.put(i, new Point(0,0));
    }
    visited.add(followers.get(followCount));

    for (String line : lines) {
      String[] s = line.split(" ");
      String action = s[0];
      int moves = Integer.valueOf(s[1]);

      for(int i=0;i<moves;i++) {
        switch (action) {
          case "U":
            head.y++;
            break;
          case "D":
            head.y--;
            break;
          case "R":
            head.x++;
            break;
          case "L":
            head.x--;
            break;
        }


        Point tmpHead = new Point(head.x, head.y);

        for(int j=1;j<=followCount;j++) {
          Point tail = followers.get(j);
          if(tmpHead.x == tail.x) {
            int dy = tmpHead.y - tail.y;
            if (Math.abs(dy) > 1) {
              tail.y += Math.signum(dy);
            }
          } else if (tmpHead.y == tail.y) {
            int dx = tmpHead.x - tail.x;
            if (Math.abs(dx) > 1) {
              tail.x += Math.signum(dx);
            }
          } else {
            int dx = tmpHead.x - tail.x;
            int dy = tmpHead.y - tail.y;
            if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
              tail.x += (int) Math.signum(dx);
              tail.y += (int) Math.signum(dy);
            }
          }
          tmpHead = tail;
        }

        visited.add(new Point(followers.get(followCount).x, followers.get(followCount).y));

      }

    }

    log.info("Part 1 - {}", visited.size());

  }

  @Data
  @AllArgsConstructor
  @EqualsAndHashCode
  public static class Point {
    public int x;
    public int y;
  }


  @Test
  public void part2() {

    List<String> lines = readFile();

    Map<Point, Integer> visited = new HashMap<>();

    Point[] nodes = new Point[10];
    for(int i=0;i<10;i++) {
      nodes[i] = new Point(0,0);
    }

    Point head = nodes[0];

    for (String line : lines) {
      String[] s = line.split(" ");
      String action = s[0];
      int moves = Integer.valueOf(s[1]);

      for(int i=0;i<moves;i++) {
        switch (action) {
          case "U":
            head.y++;
            break;
          case "D":
            head.y--;
            break;
          case "R":
            head.x++;
            break;
          case "L":
            head.x--;
            break;
        }

        for (int j = 1; j < 10; j++) {
          follow(nodes[j], nodes[j - 1]);

        }
        Point tmpNode = new Point(nodes[9].x, nodes[9].y);
        visited.compute(tmpNode, (k, v) -> (v == null) ? 1 : v++);

      }

    }
    log.info("Ans {}", visited.values().stream().reduce(0, Integer::sum));

}

  private void follow(Point node, Point parent) {

    if (node.x == parent.x) {
      int dy = parent.y - node.y;
      if (Math.abs(dy) > 1) {
        node.y += Math.signum(dy);
      }
    }
    else if (node.y == parent.y) {
      int dx = parent.x - node.x;
      if (Math.abs(dx) > 1) {
        node.x += Math.signum(dx);
      }
    } else {
      int dx = parent.x - node.x;
      int dy = parent.y - node.y;
      if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
        node.x += Math.signum(dx);
        node.y += Math.signum(dy);
      }
    }

  }

}