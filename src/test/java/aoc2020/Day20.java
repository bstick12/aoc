package aoc2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day20 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if (!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day20.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {

    List<String> input = readInput(20, false);
    Iterator<String> iterator = input.iterator();
    Pattern compile = Pattern.compile("Tile (\\d+):");
    Map<Integer, Tile> tiles = new HashMap<>();

    while (iterator.hasNext()) {
      int tileId = 0;
      List<String> tile = new ArrayList<>();
      for (int i = 0; i < 12; i++) {
        String line = iterator.next();
        if (i == 0) {
          Matcher matcher = compile.matcher(line);
          if(matcher.find()) {
            tileId = Integer.valueOf(matcher.group(1));
          }
          continue;
        }
        if (i == 11) {
          continue;
        }
        tile.add(line);
      }
      tiles.put(tileId, new Tile(tileId, tile));
    }

    int dimension = (int) Math.sqrt(tiles.size());

    Map<Map.Entry<Tile, Rotation>, Map.Entry<Map.Entry<Tile, Rotation>,Map.Entry<Tile, Rotation>>> possible = new HashMap<>();

    for(Tile t : tiles.values()) {
      for (Rotation or : Rotation.values()) {
        Set<Map.Entry<Tile, Rotation>> right = new HashSet<>();
        Set<Map.Entry<Tile, Rotation>> bottom = new HashSet<>();
        for(Tile t1 : tiles.values()) {
          if(t.getId() != t1.getId()) {
            for (Rotation ir : Rotation.values()) {
              if(t.getRight(or).equals(t1.getLeft(ir))) {
                right.add(new AbstractMap.SimpleEntry<>(t1, ir));
              }
              if(t.getBottom(or).equals(t1.getTop(ir))) {
                bottom.add(new AbstractMap.SimpleEntry<>(t1, ir));
              }
            }
          }
        }
        possible.put(new AbstractMap.SimpleEntry<>(t, or), new AbstractMap.SimpleEntry<>(right.iterator().hasNext() ? right.iterator().next() : null ,bottom.iterator().hasNext() ? bottom.iterator().next(): null));
      }

    }

    Map.Entry<Tile,Rotation>[][] grid = new Map.Entry[dimension][dimension];


    for (Map.Entry<Tile, Rotation> tileRotationEntry : possible.keySet()) {
      int i = 0;
      int j = 0;
      Set<Map.Entry<Tile, Rotation>> used = new HashSet<>();
      grid = new Map.Entry[dimension][dimension];
      grid[0][0] = tileRotationEntry;
      used.add(tileRotationEntry);
      findNext(possible, tileRotationEntry, used, grid, i, j, dimension);
      if(grid[0][0] != null && grid[dimension-1][0] != null && grid[0][dimension-1] != null && grid[dimension-1][dimension-1] != null) {
        break;
      }
    }

    log.info("Ans P1 - {}", grid[0][0].getKey().getId() * grid[dimension-1][0].getKey().getId() * grid[0][dimension-1].getKey().getId() * grid[dimension-1][dimension-1].getKey().getId());

    char map[][] = new char[8*dimension][8*dimension];

    for(int i=0;i<dimension;i++) {
      for(int j=0;j<dimension;j++) {
        Map.Entry<Tile, Rotation> tileRotationEntry = grid[i][j];
        char[][] chars = toCharMap(tileRotationEntry.getKey().getData());
        chars = manipulate(chars, tileRotationEntry.getValue());
        for(int k=0;k<chars.length;k++) {
          for(int l=0;l<chars.length;l++) {
            map[i*8+k][j*8+l] = chars[k][l];
          }
        }
      }
    }

    for (char[] chars : map) {
      log.info("{}", String.valueOf(chars));
    }

  }

  private char[][] manipulate(char[][] chars, Rotation rotation) {
    switch (rotation) {
      case NONE:
        return chars;
      case R90:
        return rotate(chars);
      case R180:
        return rotate(rotate(chars));
      case R270:
        return rotate(rotate(rotate(chars)));
      case FLIP:
        return flip(chars);
      case FLIP90:
        return rotate(flip(chars));
      case FLIP180:
        return rotate(rotate(flip(chars)));
      case FLIP270:
        return rotate(rotate(rotate(flip(chars))));
      default:
        throw new RuntimeException();
    }
  }

  private char[][] flip(char[][] chars) {
    char[][] flipped = new char[chars.length][chars.length];
    for(int i=0;i<chars.length;i++) {
      flipped[i] = StringUtils.reverse(String.valueOf(chars[i])).toCharArray();
    }
    return flipped;
  }

  private char[][] rotate(char[][] chars) {
    char[][] rotated = new char[chars.length][chars.length];
    for (int i = 0; i < chars.length; ++i)
      for (int j = 0; j < chars.length; ++j)
        rotated[i][j] = chars[chars.length - j - 1][i];
    return rotated;
  }

  private char[][] toCharMap(List<String> data) {
    char[][] x = new char[8][8];
    for(int i=1;i<data.size()-1;i++) {
      x[i-1] = data.get(i).substring(1,9).toCharArray();
    }
    return x;
  }

  private void findNext(
      Map<Map.Entry<Tile, Rotation>, Map.Entry<Map.Entry<Tile, Rotation>, Map.Entry<Tile, Rotation>>> possible,
      Map.Entry<Tile, Rotation> parent, Set<Map.Entry<Tile, Rotation>> used, Map.Entry<Tile, Rotation>[][] grid, int i, int j,
      int dimension) {
    if(i >= dimension || j >= dimension) {
      return;
    }
    Map.Entry<Map.Entry<Tile, Rotation>, Map.Entry<Tile, Rotation>> parentLinks = possible.get(parent);

    if(parentLinks.getKey() == null || parentLinks.getValue() == null) {
      return;
    }

    Map.Entry<Map.Entry<Tile, Rotation>, Map.Entry<Tile, Rotation>> right = possible.get(parentLinks.getKey());
    Map.Entry<Map.Entry<Tile, Rotation>, Map.Entry<Tile, Rotation>> bottom = possible.get(parentLinks.getValue());
    if(right.getValue() != null && right.getValue().equals(bottom.getKey())) {
      grid[i][j+1] = parentLinks.getKey();
      grid[i+1][j] = parentLinks.getValue();
      grid[i+1][j+1] = right.getValue();
      findNext(possible, grid[i+1][j], used, grid, i+1, j, dimension);
      findNext(possible, grid[i][j+1], used, grid, i, j+1, dimension);
      findNext(possible, grid[i+1][j+1], used, grid, i+1, j+1, dimension);
    }

  }


  @Test
  public void testPuzzle2() throws URISyntaxException, IOException {

    List<String> strings = Files.readAllLines(Path.of(Day20.class.getResource("day20.part2.txt").toURI()));
    int total = count(strings);
    int count=0;
    count += getCount(strings);
    log.info("Ans P2 - {} {}", count, total - (count * 15));

  }

  private int getCount(List<String> strings) {
    int count=0;
    Pattern body = Pattern.compile("(#.{4}##.{4}##.{4}###)");
    Pattern tail = Pattern.compile("(#.{2}#.{2}#.{2}#.{2}#.{2}#)");

    for(int i=0;i<strings.size();i++) {
      Matcher matcher = body.matcher(strings.get(i));
      while(matcher.find()) {
        Matcher matcher1 = tail.matcher(strings.get(i + 1));
        while(matcher1.find()) {
          if(strings.get(i-1).toCharArray()[matcher.end(1) - 2] == '#') {
            count++;
          }
        }
      }
    }
    return count;
  }

  private int count(List<String> strings) {
    int count  = 0;
    for (String string : strings) {
      count += StringUtils.countMatches(string, '#');
    }
    return count;
  }

  public List<String> rotate(List<String> lines) {
    List<String> y = new ArrayList<>();
    char[][] x = new char[lines.size()][lines.size()];
    for (int i = 0; i < lines.size(); i++) {
      x[i] = lines.get(i).toCharArray();
    }
    x = rotate(x);
    for (char[] chars : x) {
      y.add(String.valueOf(chars));
    }
    return y;
  }

  public List<String> flip(List<String> lines) {
    List<String> x = new ArrayList<>();
    for (String line : lines) {
      x.add(StringUtils.reverse(line));
    }
    return x;
  }

  public enum Rotation {
    NONE,
    R90,
    R180,
    R270,
    FLIP,
    FLIP90,
    FLIP180,
    FLIP270,

  }

  @ToString
  public static class Tile {

    @Getter
    private final long id;

    @Getter
    private final List<String> data;

    String top;
    String bottom;
    String left;
    String right;

    String rtop;
    String rbottom;
    String rleft;
    String rright;

    public Tile(Integer id, List<String> tile) {
      this.id = id;
      data = new ArrayList(tile);

      top = tile.get(0);
      rtop = StringUtils.reverse(tile.get(0));

      bottom = tile.get(tile.size() - 1);
      rbottom = StringUtils.reverse(tile.get(tile.size() - 1));

      StringBuilder sb1 = new StringBuilder();
      StringBuilder sb2 = new StringBuilder();
      for (String s : tile) {
        char[] chars = s.toCharArray();
        sb1.append(chars[0]);
        sb2.append(chars[9]);
      }
      left = sb1.toString();
      right = sb2.toString();

      rleft = sb1.reverse().toString();
      rright = sb2.reverse().toString();

    }

    public String getTop(Rotation rotation) {
      switch (rotation) {
        case NONE:
          return top;
        case R90:
          return rleft;
        case R180:
          return rbottom;
        case R270:
          return right;
        case FLIP:
          return rtop;
        case FLIP90:
          return rright;
        case FLIP180:
          return bottom;
        case FLIP270:
          return left;
        default:
          throw new RuntimeException();
      }
    }

    public String getBottom(Rotation rotation) {
      switch (rotation) {
        case NONE:
          return bottom;
        case R90:
          return rright;
        case R180:
          return rtop;
        case R270:
          return left;
        case FLIP:
          return rbottom;
        case FLIP90:
          return rleft;
        case FLIP180:
          return top;
        case FLIP270:
          return right;
        default:
          throw new RuntimeException();
      }

    }

    public String getLeft(Rotation rotation) {
      switch (rotation) {
        case NONE:
          return left;
        case R90:
          return bottom;
        case R180:
          return rright;
        case R270:
          return rtop;
        case FLIP:
          return right;
        case FLIP90:
          return rbottom;
        case FLIP180:
          return rleft;
        case FLIP270:
          return top;
        default:
          throw new RuntimeException();
      }
    }

    public String getRight(Rotation rotation) {
      switch (rotation) {
        case NONE:
          return right;
        case R90:
          return top;
        case R180:
          return rleft;
        case R270:
          return rbottom;
        case FLIP:
          return left;
        case FLIP90:
          return rtop;
        case FLIP180:
          return rright;
        case FLIP270:
          return bottom;
        default:
          throw new RuntimeException();
      }
    }

  }

}

