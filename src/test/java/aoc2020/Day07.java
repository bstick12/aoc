package aoc2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day07 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(7);
    //return Files.readAllLines(Path.of(Day07.class.getResource("day07.txt").toURI()));

  }

  @Test
  public void testPuzzle1() {
    List<String> input = readFile();

    Iterator<String> iterator = input.iterator();

    Map<String, Set<String>> bagMap = new HashMap<>();

    for (String s : input) {

      if(!s.contains("contain no other bag")) {
        String[] s1 = s.split(" ");
        String bag = s1[0] + " " + s1[1];
        for(int i=5;i<s1.length;i+=4) {
          String innerBag =  s1[i] + " " + s1[i+1];
          Set<String> orDefault = bagMap.getOrDefault(innerBag, new HashSet<String>());
          orDefault.add(bag);
          bagMap.put(innerBag,orDefault);
        }
      }
    }

    Set<String> bags = new HashSet<>();
    findHoldingBags("shiny gold", bagMap, bags);
    log.info("Ans {}", bags.size() );

  }

  public void findHoldingBags(String colour, Map<String, Set<String>> bags, Set<String> goldHoldingBags) {
    Set<String> strings = bags.getOrDefault(colour, new HashSet<>());
    if(goldHoldingBags.containsAll(strings)) {
      return;
    } else {
      goldHoldingBags.addAll(strings);
      for (String string : strings) {
        findHoldingBags(string, bags, goldHoldingBags);
      }
    }
  }

  @Test
  public void testPuzzle2() throws URISyntaxException, IOException {

    List<String> input = readFile();

    Map<String, Set<String>> bagMap = new HashMap<>();

    for (String s : input) {
      String[] s1 = s.split(" ");
      String bag = s1[0] + " " + s1[1];
      if (s.contains("contain no other bag")) {
        bagMap.put(bag, new HashSet<>());
      } else {
        for (int i = 4; i < s1.length; i += 4) {
          String innerBag = s1[i] + " " + s1[i + 1] + " " + s1[i + 2];
          Set<String> orDefault = bagMap.getOrDefault(bag, new HashSet<String>());
          orDefault.add(innerBag);
          bagMap.put(bag, orDefault);
        }
      }
    }

    log.info("Ans {}", counter("shiny gold", bagMap));
  }

  private int counter(String colour, Map<String, Set<String>> count) {
    Set<String> strings = count.get(colour);
    if(strings.isEmpty()) {
      return 0;
    } else {
      int total=0;
      for (String string : strings) {
        String[] s = string.split(" ");
        int bagCount = Integer.valueOf(s[0]);
        String bag = s[1] + " " + s[2];
        total += bagCount + bagCount * counter(bag, count);
      }
      return total;
    }

  }

}
