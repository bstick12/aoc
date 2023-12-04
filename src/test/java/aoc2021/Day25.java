package aoc2021;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day25 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(25);
    } else {
      return Files.readAllLines(Path.of(Day25.class.getResource("day25.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());

  }



  @Test
  public void testSwap() {

    int a = 5;
    int b = 10;
    log.info("{} {}", a, b);
    a=a | b;
    log.info("{} {}", a, b);
    b=a ^ b;
    log.info("{} {}", a, b);
    a=a ^ b;
    log.info("{} {}", a, b);
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);

    Character[][] seabed = new Character[input.size()][input.get(0).length()];

    int maxY = input.size();
    int maxX = input.get(0).length();

    for(int y=0;y<input.size();y++) {
      char[] chars = input.get(y).toCharArray();
      for (int x =0;x<chars.length; x++) {
        seabed[y][x] = chars[x];
      }
    }


    long answer = 0;

    Utils.printGrid(seabed);
    while(true) {
      int moves = 0;

      moves += moveEast(seabed, maxX);
      moves += moveSouth(seabed, maxY);
      answer++;

      if(moves == 0) {
        break;
      }

    }


    log.info("{}", answer);


  }

  private int moveEast(Character[][] seabed, int maxX) {

    int moves =0;

    Set<Map.Entry<Integer,Integer>> moved = new HashSet<>();
    Set<Map.Entry<Integer,Integer>> vacated = new HashSet<>();

    for(int y=seabed.length-1;y>=0;y--) {
      for(int x=seabed[y].length-1;x>=0;x--) {
        if(seabed[y][x] == '>' && !moved.contains(new AbstractMap.SimpleImmutableEntry<>(y,x))) {
          int newX = x + 1 < maxX ? x + 1 : 0;
          if(seabed[y][newX] == '.' && !vacated.contains(new AbstractMap.SimpleImmutableEntry<>(y, newX))) {
            moved.add(new AbstractMap.SimpleImmutableEntry<>(y,newX));
            vacated.add(new AbstractMap.SimpleImmutableEntry<>(y,x));
            seabed[y][newX] = '>';
            seabed[y][x] = '.';
            moves++;
          }
        }
      }

    }

    return moves;
  }

  private int moveSouth(Character[][] seabed, int maxY) {

    int moves =0;

    Set<Map.Entry<Integer,Integer>> moved = new HashSet<>();
    Set<Map.Entry<Integer,Integer>> vacated = new HashSet<>();

    for(int y=seabed.length-1;y>=0;y--) {
      for(int x=seabed[y].length-1;x>=0;x--) {
        if(seabed[y][x] == 'v' && !moved.contains(new AbstractMap.SimpleImmutableEntry<>(y,x))) {
          int newY = y + 1 < maxY ? y + 1 : 0;
          if(seabed[newY][x] == '.' && !vacated.contains(new AbstractMap.SimpleImmutableEntry<>(newY, x))) {
            moved.add(new AbstractMap.SimpleImmutableEntry<>(newY,x));
            vacated.add(new AbstractMap.SimpleImmutableEntry<>(y,x));
            seabed[newY][x] = 'v';
            seabed[y][x] = '.';
            moves++;
          }
        }
      }

    }

    return moves;
  }

  private long value(String s, Map<String, Long> vals) {
    if(NumberUtils.isParsable(s)) {
      return Long.valueOf(s);
    } else {
      return vals.get(s);
    }
  }


}
