package aoc2021;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day8 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(8);
    } else {
      return Files.readAllLines(Path.of(Day8.class.getResource("day08.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);
    int answer = 0;

    for (String s : input) {
      Map<Character, Character> mapping = new HashMap<>();
      String results = s.split("\\|")[1].trim();
      Set<Integer> known = Set.of(2, 4, 7, 3);
      for(String result : results.split(" ")) {
        if(known.contains(result.length())) {
          answer++;
        }
      }
    }

    log.info("Ans 1 - {}", answer);

  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(false);
    int answer = 0;

    for (String s : input) {

      Map<Integer, String> mapping = new HashMap<>();

      String dials = s.split("\\|")[0].trim();
      String results = s.split("\\|")[1].trim();

      List<String> collect = Arrays.stream(dials.split(" "))
          .sorted(Comparator.comparing(String::length)).collect(Collectors.toList());

      mapping.put(1, collect.get(0));
      mapping.put(7, collect.get(1));
      mapping.put(4, collect.get(2));
      mapping.put(8, collect.get(9));
      mapping.put(6, disjoint(mapping.get(1), collect.subList(6,9)));
      List<String> strings = new ArrayList<>(collect.subList(6, 9));
      strings.remove(mapping.get(6));
      mapping.put(0, disjoint(mapping.get(4), strings));
      strings.remove(mapping.get(0));
      mapping.put(9, strings.get(0));
      mapping.put(3, intersection(mapping.get(1), collect.subList(3,6)));
      mapping.put(5, disjoint(diff(mapping.get(6), mapping.get(1)), collect.subList(3,6)));
      strings = new ArrayList<>(collect.subList(3, 6));
      strings.remove(mapping.get(5));
      strings.remove(mapping.get(3));
      mapping.put(2, strings.get(0));

      String[] s1 = results.split(" ");

      Map<String, Integer> mappingA = new HashMap<>();

      for (Integer integer : mapping.keySet()) {
        mappingA.put(sort(mapping.get(integer)), integer);
      }

      long x = mappingA.get(sort(s1[0])) * 1000 +
          mappingA.get(sort(s1[1])) * 100 +
          mappingA.get(sort(s1[2])) * 10 +
          mappingA.get(sort(s1[3])) * 1;
      answer += x;

    }

    log.info("Ans 2 - {}", answer);

  }

  private String sort(String x) {
    char[] chars = x.toCharArray();
    Arrays.sort(chars);
    return String.valueOf(chars);
  }

  private String intersection(String x, List<String> y) {

    return y.stream().filter(r -> {
      boolean contains = true;
      for (char c : x.toCharArray()) {
        contains &= r.contains(String.valueOf(c));
      }
      return contains;
    }).findFirst().get();

  }

  private String disjoint(String x, List<String> y) {
    return y.stream().filter(r -> {
      boolean contains = true;
      for (char c : x.toCharArray()) {
        contains &= r.contains(String.valueOf(c));
      }
      return !contains;
    }).findFirst().get();
  }

  private String diff(String a, String b) {
    for (char c : a.toCharArray()) {
      b = b.replace(String.valueOf(c), "");
    }
    return b;
  }

}

