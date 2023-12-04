package aoc2022;

import utils.Utils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day06 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2022,6);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPart1() {

    List<String> lines = readFile();
    int markerLength = 4;
    int count = getMarkerCount(lines, markerLength);
    log.info("Part 1 - {}", count);

  }

  @Test
  public void testPart2() {

    List<String> lines = readFile();
    int markerLength = 14;
    int count = getMarkerCount(lines, markerLength);
    log.info("Part 2 - {}", count);

  }

  private int getMarkerCount(List<String> lines, int markerLength) {
    int count = 0;
    for (String line : lines) {
      for(int i = markerLength; i<line.length(); i++) {
        Set<Character> x = new HashSet<>();
        for(int j = i; j > i - markerLength; j--) {
          x.add(line.toCharArray()[j]);
        }
        if(x.size() == markerLength) {
          count += i+1;
          break;
        }
      }
    }
    return count;
  }

}
