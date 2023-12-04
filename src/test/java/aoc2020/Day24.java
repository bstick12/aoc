package aoc2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day24 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if (!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day24.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  private static int ITERATIONS = 10_000_000;

  private static int SIZE = 1_000_000;

  @Data
  @AllArgsConstructor
  public static class Hex {
    private int x;
    private int y;
    private int z;

    public Hex move(String direction) {
      switch (direction) {
        case "e":
          x++;
          y--;
          break;
        case "w":
          x--;
          y++;
          break;
        case "ne":
          x++;
          z--;
          break;
        case "sw":
          x--;
          z++;
          break;
        case "se":
          y--;
          z++;
          break;
        case "nw":
          y++;
          z--;
          break;
        default:
          throw new RuntimeException();
      }
      return new Hex(x, y, z);
    }

    public Hex adjacent(String direction) {
      switch (direction) {
        case "e":
          return new Hex(x + 1, y - 1, z);
        case "w":
          return new Hex(x - 1, y + 1, z);
        case "ne":
          return new Hex(x + 1, y, z - 1);
        case "sw":
          return new Hex(x - 1, y, z + 1);
        case "se":
          return new Hex(x, y - 1, z + 1);
        case "nw":
          return new Hex(x, y + 1, z - 1);
        default:
          throw new RuntimeException();
      }
    }

  }

  public Set<Hex> adjacents(Hex hex) {
    Set<Hex> ret = new HashSet<>();
    ret.add(hex.adjacent("e"));
    ret.add(hex.adjacent("w"));
    ret.add(hex.adjacent("ne"));
    ret.add(hex.adjacent("sw"));
    ret.add(hex.adjacent("nw"));
    ret.add(hex.adjacent("se"));
    return ret;
  }

  @Test
  public void testPuzzle1() {

    List<String> input = readInput(24, true);
    Iterator<String> iterator = input.iterator();

    String regex = "(e|se|sw|w|nw|ne)";
    Pattern compile = Pattern.compile(regex);

    Map<Hex, Boolean> tiles = new HashMap<>();

    for (String s : input) {
      Matcher matcher = compile.matcher(s);
      Hex root = new Hex(0, 0, 0);
      while (matcher.find()) {
        root = root.move(matcher.group());
      }
      tiles.put(root, !tiles.getOrDefault(root, false));
      log.info("{}", root);
    }
    log.info("{}", tiles.values().stream().filter(x -> x == true).count());
    log.info("{}", tiles.values().stream().filter(x -> x == false).count());


    for (int day = 1; day <= 100; day++) {

      Map<Hex, Boolean> newTiles = new HashMap<>();
      final Map<Hex, Boolean> tilesCopy = new HashMap<>(tiles);
      for (Hex entry : tiles.keySet()) {
        adjacents(entry).forEach(x -> tilesCopy.putIfAbsent(x, false));
      }

      for (Map.Entry<Hex, Boolean> tileEntry : tilesCopy.entrySet()) {
        newTiles.put(tileEntry.getKey(), tileEntry.getValue());
//        Set<Hex> adjacents = adjacents(tileEntry.getKey());
//        for (Hex adjacent : adjacents) {
//          newTiles.putIfAbsent(adjacent, false);
//        }
        long count = adjacents(tileEntry.getKey())
            .stream().filter(x -> tilesCopy.getOrDefault(x, false)).count();
        if (tileEntry.getValue()) {
          if (count == 0 || count > 2)
            newTiles.put(tileEntry.getKey(), false);
        }
        else
          if (count == 2)
            newTiles.put(tileEntry.getKey(), true);
      }

      tiles = newTiles;
      log.info("Day {} {}", day, tiles.values().stream().filter(x -> x == true).count());
    }
  }
}


