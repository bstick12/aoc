package aoc2023;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day11 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 11);
    //return Files.readAllLines(Path.of(Day07.class.getResource("day11_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    long answer = 0;

    List<String> lines = readFile();
    List<Point> galaxies = new ArrayList<>();
    TreeSet<Integer> emptyRows = IntStream.range(0, lines.size()).boxed().collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
    TreeSet<Integer> emptyCols = IntStream.range(0, lines.size()).boxed().collect(TreeSet::new, TreeSet::add, TreeSet::addAll);

    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          emptyRows.remove(y);
          emptyCols.remove(x);
          galaxies.add(new Point(x, y));
        }
      }
    }

    List<Point> spacedGalaxies =
        galaxies.stream().map(p -> p.add(new Point(emptyCols.headSet(p.x).size(), emptyRows.headSet(p.y).size()))).collect(
            Collectors.toList());

    for(int i=0;i<spacedGalaxies.size();i++) {
      Point start = spacedGalaxies.get(i);
      for(int j=i+1;j<spacedGalaxies.size();j++) {
          Point destination = spacedGalaxies.get(j);
          int distance = Math.abs(start.x - destination.x) + Math.abs(start.y - destination.y);
          answer += distance;
      }
    }

    log.info("Answer 1 - {}", answer);

  }


  @Test
  public void testPuzzle2() throws Exception {

    long answer = 0;

    List<String> lines = readFile();
    List<Point> galaxies = new ArrayList<>();
    TreeSet<Integer> emptyRows = IntStream.range(0, lines.size()).boxed().collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
    TreeSet<Integer> emptyCols = IntStream.range(0, lines.size()).boxed().collect(TreeSet::new, TreeSet::add, TreeSet::addAll);

    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          emptyRows.remove(y);
          emptyCols.remove(x);
          galaxies.add(new Point(x, y));
        }
      }
    }

    List<Point> spacedGalaxies =
        galaxies.stream().map(p -> p.add(
            new Point(
                (emptyCols.headSet(p.x).size() * 1000000) - emptyCols.headSet(p.x).size(),
                (emptyRows.headSet(p.y).size() * 1000000) - emptyRows.headSet(p.y).size()
              )
            )).collect(Collectors.toList());


    for(int i=0;i<spacedGalaxies.size();i++) {
      Point start = spacedGalaxies.get(i);
      for(int j=i+1;j<spacedGalaxies.size();j++) {
        Point destination = spacedGalaxies.get(j);
        int distance = Math.abs(start.x - destination.x) + Math.abs(start.y - destination.y);
        answer += distance;
      }
    }

    log.info("Answer 2 - {}", answer);

  }

  public record Point(int x, int y) {
    Point add(Point point) {
      return new Point(point.x + x, point.y + y);
    }

  }

}

