package aoc2021;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day20 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(20);
    } else {
      return Files.readAllLines(Path.of(Day20.class.getResource("day20.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());

  }


  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);

    String iea = input.get(0);

    Map<Map.Entry<Integer, Integer>, Integer> map = new HashMap<>();

    int y = 0;
    for (int i = 2; i < input.size(); i++) {
      String s = input.get(i);
      int x = 0;
      for (char c : s.toCharArray()) {
        if(c == '#') {
          map.put(new AbstractMap.SimpleEntry<>(x,y), 1);
        }
        x++;
      }
      y++;
    }

    for(int i=0;i<50;i++) {

      Map<Map.Entry<Integer, Integer>, Integer> resultMap = new HashMap<>();

      int maxX = map.keySet().stream().mapToInt(k -> k.getKey()).max().getAsInt();
      int minX = map.keySet().stream().mapToInt(k -> k.getKey()).min().getAsInt();
      int maxY = map.keySet().stream().mapToInt(k -> k.getValue()).max().getAsInt();
      int minY = map.keySet().stream().mapToInt(k -> k.getValue()).min().getAsInt();


      for(y = minY-1;y <= maxY+1; y++) {
        for (int x = minX-1; x <= maxX+1; x++) {
          StringBuilder sb = new StringBuilder();
          for(int yy = y-1;yy<=y+1;yy++) {
            for(int xx = x-1;xx<=x+1;xx++) {
              sb.append(map.getOrDefault(new AbstractMap.SimpleEntry<>(xx, yy), getDefault(xx,yy,minX, maxX, minY,maxY, i)));
            }
          }
          int decimal = Integer.valueOf(sb.toString(), 2);
          char c = iea.toCharArray()[decimal];
          if(c == '#') {
            resultMap.put(new AbstractMap.SimpleEntry<>(x,y), 1);
          }
        }
      }
      map = resultMap;

      log.info("Answer 1 - {} - {}",i, map.keySet().size());

    }

  }

  private Integer getDefault(int x, int y, int minX, int maxX, int minY, int maxY, int i) {
    if(x > maxX || x < minX || y > maxY || y < minY) {
      return i % 2 == 0 ? 0 : 1;
    } else {
      return 0;
    }
  }

}
