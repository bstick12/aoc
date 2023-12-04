package aoc2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day06 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(6);
    //return Files.readAllLines(Path.of(Day06.class.getResource("day06.txt").toURI()));
  }

  @Test
  public void testPuzzle2() {

    List<String> input = readFile();
    input.add("");
    Iterator<String> iterator = input.iterator();

    HashMap<Character, Integer> count = new HashMap<>();

    int total = 0;
    int members = 0;

    while (iterator.hasNext()) {

      String next = iterator.next();
      if (next.equals("")) {
        final int counter = members;
        total = total + count.values().stream().filter((x) -> x == counter).collect(Collectors.toList()).size();
        count.clear();
        members = 0;
      } else {
        for (char c : next.toCharArray()) {
          count.put(c, count.getOrDefault(c,0)+1);
        }
        members++;
      }
    }
    log.info("Total = {}", total);
  }

  @Test
  public void testPuzzle1() throws URISyntaxException, IOException {
    List<String> input = readFile();
    input.add("");
    input.add("");

    Iterator<String> iterator = input.iterator();
    HashMap<Character, Integer> count = new HashMap<>();

    int total = 0;

    while (iterator.hasNext()) {

      String next = iterator.next();
      if (next.equals("")) {
        total = total + count.size();
        count.clear();
      } else {
        for (char c : next.toCharArray()) {
          count.putIfAbsent(c, 0);
        }
      }
    }
  log.info("Total = {}", total);

  }

}
