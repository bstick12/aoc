package aoc2023;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Point;
import utils.Utils;

@Slf4j
public class Day16 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 16);
    //return Files.readAllLines(Path.of(Day15.class.getResource("day16_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static Map<Map.Entry<String, List<Integer>>, Long> CACHE = new HashMap();

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();

    Character[][] grid = Utils.getGrid(lines);
    Utils.printGridXY(grid);

    Point start = new Point(-1,0);

    Map<Point, Integer> energized = new HashMap<>();

    Queue<Map.Entry<Point, Point>> beam = new LinkedList<>();
    Set<Map.Entry<Point, Point>> visited = new HashSet<>();
    beam.add(Map.entry(start, Point.EAST));
    while(!beam.isEmpty()) {
      Map.Entry<Point, Point> step = beam.remove();
      Point current = step.getKey().add(step.getValue());

      Map.Entry<Point, Point> visit = Map.entry(current, step.getValue());
      if(visited.contains(visit)) {
        continue;
      } else {
        visited.add(visit);
      }
      Character x = Utils.getSafeXY(current, grid, 'X');
      if (x != 'X') {
        energized.compute(current, (k, v) -> v == null ? 1 : v + 1);
        switch (x) {
          case '.':
            beam.add(Map.entry(current, step.getValue()));
            break;
          case '|':
            if (step.getValue().equals(Point.EAST) || step.getValue().equals(Point.WEST)) {
              beam.add(Map.entry(current, Point.NORTH));
              beam.add(Map.entry(current, Point.SOUTH));
            } else {
              beam.add(Map.entry(current, step.getValue()));
            }
            break;
          case '-':
            if (step.getValue().equals(Point.NORTH) || step.getValue().equals(Point.SOUTH)) {
              beam.add(Map.entry(current, Point.EAST));
              beam.add(Map.entry(current, Point.WEST));
            } else {
              beam.add(Map.entry(current, step.getValue()));
            }
            break;
          case '/':
            if (step.getValue().equals(Point.NORTH)) {
              beam.add(Map.entry(current, Point.EAST));
            } else if (step.getValue().equals(Point.SOUTH)) {
              beam.add(Map.entry(current, Point.WEST));
            } else if (step.getValue().equals(Point.EAST)) {
              beam.add(Map.entry(current, Point.NORTH));
            } else if (step.getValue().equals(Point.WEST)) {
              beam.add(Map.entry(current, Point.SOUTH));
            }
            break;
          case '\\':
            if (step.getValue().equals(Point.NORTH)) {
              beam.add(Map.entry(current, Point.WEST));
            } else if (step.getValue().equals(Point.SOUTH)) {
              beam.add(Map.entry(current, Point.EAST));
            } else if (step.getValue().equals(Point.EAST)) {
              beam.add(Map.entry(current, Point.SOUTH));
            } else if (step.getValue().equals(Point.WEST)) {
              beam.add(Map.entry(current, Point.NORTH));
            }
            break;
          default:
            break;
        }
      }
    }

    log.info("Answer 1 - {}", energized.size());
  }

  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();

    Character[][] grid = Utils.getGrid(lines);

    int answer = 0;

    Point start = new Point(-1,0);

    Queue<Map.Entry<Point, Point>> starts = new LinkedList<>();

    for(int x=0;x<grid.length;x++) {
      starts.add(Map.entry(new Point(-1, x), Point.EAST));
      starts.add(Map.entry(new Point(x, -1), Point.SOUTH));
      starts.add(Map.entry(new Point(grid.length, x), Point.WEST));
      starts.add(Map.entry(new Point(x, grid.length), Point.NORTH));
    }

    while(!starts.isEmpty()) {
      Map<Point, Integer> energized = new HashMap<>();
      Queue<Map.Entry<Point, Point>> beam = new LinkedList<>();
      Set<Map.Entry<Point, Point>> visited = new HashSet<>();
      beam.add(starts.remove());
      while (!beam.isEmpty()) {
        Map.Entry<Point, Point> step = beam.remove();
        Point current = step.getKey().add(step.getValue());

        Map.Entry<Point, Point> visit = Map.entry(current, step.getValue());
        if (visited.contains(visit)) {
          continue;
        } else {
          visited.add(visit);
        }
        Character x = Utils.getSafeXY(current, grid, 'X');
        if (x != 'X') {
          energized.compute(current, (k, v) -> v == null ? 1 : v + 1);
          switch (x) {
            case '.':
              beam.add(Map.entry(current, step.getValue()));
              break;
            case '|':
              if (step.getValue().equals(Point.EAST) || step.getValue().equals(Point.WEST)) {
                beam.add(Map.entry(current, Point.NORTH));
                beam.add(Map.entry(current, Point.SOUTH));
              } else {
                beam.add(Map.entry(current, step.getValue()));
              }
              break;
            case '-':
              if (step.getValue().equals(Point.NORTH) || step.getValue().equals(Point.SOUTH)) {
                beam.add(Map.entry(current, Point.EAST));
                beam.add(Map.entry(current, Point.WEST));
              } else {
                beam.add(Map.entry(current, step.getValue()));
              }
              break;
            case '/':
              if (step.getValue().equals(Point.NORTH)) {
                beam.add(Map.entry(current, Point.EAST));
              } else if (step.getValue().equals(Point.SOUTH)) {
                beam.add(Map.entry(current, Point.WEST));
              } else if (step.getValue().equals(Point.EAST)) {
                beam.add(Map.entry(current, Point.NORTH));
              } else if (step.getValue().equals(Point.WEST)) {
                beam.add(Map.entry(current, Point.SOUTH));
              }
              break;
            case '\\':
              if (step.getValue().equals(Point.NORTH)) {
                beam.add(Map.entry(current, Point.WEST));
              } else if (step.getValue().equals(Point.SOUTH)) {
                beam.add(Map.entry(current, Point.EAST));
              } else if (step.getValue().equals(Point.EAST)) {
                beam.add(Map.entry(current, Point.SOUTH));
              } else if (step.getValue().equals(Point.WEST)) {
                beam.add(Map.entry(current, Point.NORTH));
              }
              break;
            default:
              break;
          }
        }
      }
      answer = Math.max(answer, energized.size());
    }

    log.info("Answer 2 - {}", answer);
  }

}

