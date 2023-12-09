package aoc2023;

import static utils.Utils.lcm;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day09 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 9);
    //return Files.readAllLines(Path.of(Day07.class.getResource("day09_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public record Pair (String left , String right) {}

  @Test
  public void testPuzzle1() throws Exception {

    long answer = 0;

    List<String> lines = readFile();
    for (String line : lines) {
      Stack<List<Long>> longs = new Stack<>();
      List<Long> collect = Arrays.stream(line.split(" ")).map(v -> Long.valueOf(v)).collect(Collectors.toList());
      boolean zeros = false;
      while(!zeros) {
        longs.push(collect);
        List<Long> newCollection = new ArrayList<>();
        zeros = true;
        for (int i = 0; i < collect.size() - 1; i++) {
          long l = collect.get(i + 1) - collect.get(i);
          newCollection.add(l);
          zeros &= l == 0;
        }
        collect = newCollection;
      }
      long lastValue = collect.get(collect.size() - 1);
      while(!longs.isEmpty()) {
        collect = longs.pop();
        lastValue = collect.get(collect.size() -1) + lastValue;
      }
      answer += lastValue;
    }

    log.info("Answer 1 - {}", answer);

  }

  @Test
  public void testPuzzle2() throws Exception {

    long answer = 0;

    List<String> lines = readFile();
    for (String line : lines) {
      Stack<List<Long>> longs = new Stack<>();
      List<Long> collect = Arrays.stream(line.split(" ")).map(v -> Long.valueOf(v)).collect(Collectors.toList());
      boolean zeros = false;
      while(!zeros) {
        longs.push(collect);
        List<Long> newCollection = new ArrayList<>();
        zeros = true;
        for (int i = 0; i < collect.size() - 1; i++) {
          long l = collect.get(i + 1) - collect.get(i);
          newCollection.add(l);
          zeros &= l == 0;
        }
        collect = newCollection;
      }
      long firstValue = collect.get(0);
      while(!longs.isEmpty()) {
        collect = longs.pop();
        firstValue = collect.get(0) - firstValue;
      }
      answer += firstValue;
    }

    log.info("Answer 2 - {}", answer);

  }

}