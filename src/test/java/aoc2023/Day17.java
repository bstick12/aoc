package aoc2023;

import com.google.common.collect.Comparators;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Point;
import utils.Utils;

@Slf4j
public class Day17 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 17);
    //return Files.readAllLines(Path.of(Day15.class.getResource("day17_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static Map<Map.Entry<String, List<Integer>>, Long> CACHE = new HashMap();

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();

    Character[][] grid = Utils.getGrid(lines);

    Point start = new Point(0,0);
    Point end = new Point(grid.length - 1, grid.length - 1);

    Queue<Travel> journey = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
    journey.add(new Travel(start, Point.EAST, 1, 0 ));
    journey.add(new Travel(start, Point.SOUTH, 1, 0 ));

    Set<Seen> seen = new HashSet<>();

    while(!journey.isEmpty()) {

      Travel travel = journey.remove();

      if(seen.contains(travel.seen())) {
        continue;
      }
      seen.add(travel.seen());

      Point last = travel.direction;

      for (Point cardinal : Point.CARDINALS) {
        Point nextPoint = travel.point.add(cardinal);

        if(!(Utils.getSafeXY(nextPoint, grid, '#') == '#'
            || Point.OPPOSITE.get(last).equals(cardinal)
            || (cardinal.equals(last) && travel.distance == 3))) {

          Travel nextTravel;
          int nextCost = travel.cost + Integer.valueOf(String.valueOf(Utils.getSafeXY(nextPoint, grid, '#')));
          if (last.equals(cardinal)) {
            nextTravel = new Travel(nextPoint, cardinal, travel.distance + 1, nextCost);
          } else {
            nextTravel = new Travel(nextPoint, cardinal, 1, nextCost);
          }

          if (seen.contains(nextTravel.seen())) {
            continue;
          }

          if (nextTravel.point.equals(end)) {
            log.info("Answer 1 - {}", nextTravel.cost);
            return;
          }

          journey.add(nextTravel);

        }
      }
    }
  }

  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();

    Character[][] grid = Utils.getGrid(lines);

    Point start = new Point(0,0);
    Point end = new Point(grid.length - 1, grid.length - 1);

    Queue<Travel> journey = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));

    journey.add(new Travel(start, Point.EAST, 1, 0 ));
    journey.add(new Travel(start, Point.SOUTH, 1, 0 ));

    Set<Seen> seen = new HashSet<>();

    while(!journey.isEmpty()) {

      Travel travel = journey.remove();
      if(seen.contains(travel.seen())) {
        continue;
      }
      seen.add(travel.seen());

      Point last = travel.direction;

      for (Point cardinal : Point.CARDINALS) {
        Point nextPoint = travel.point.add(cardinal);

        if(!(Utils.getSafeXY(nextPoint, grid, '#') == '#'
            || Point.OPPOSITE.get(last).equals(cardinal)
            || (cardinal.equals(last) && travel.distance == 10)
            || (!cardinal.equals(last) && travel.distance < 4))
        ) {

          Travel nextTravel;
          int nextCost = travel.cost + Integer.valueOf(String.valueOf(Utils.getSafeXY(nextPoint, grid, '#')));
          if (last.equals(cardinal)) {
            nextTravel = new Travel(nextPoint, cardinal, travel.distance + 1, nextCost);
          } else {
            nextTravel = new Travel(nextPoint, cardinal, 1, nextCost);
          }

          if (seen.contains(nextTravel.seen())) {
            continue;
          }

          if (nextTravel.point.equals(end) && nextTravel.distance() >= 4) {
            log.info("Answer 2 - {} {}", nextTravel.cost, nextTravel);
            return;
          }

          journey.add(nextTravel);

        }
      }
    }
  }

  public record Travel(Point point, Point direction, int distance, int cost) {

    Seen seen() {
      return new Seen(point, direction, distance);
    }

  }

  public record Seen(Point point, Point direction, int distance) {}

}

