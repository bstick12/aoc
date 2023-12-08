package aoc2023;

import static java.util.Map.entry;
import static utils.Utils.lcm;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day08 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 8);
    //return Files.readAllLines(Path.of(Day07.class.getResource("day08_1.txt").toURI()));

  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public record Pair (String left , String right) {}

  @Test
  public void testPuzzle1() throws Exception {

    long answer = 0;

    List<String> lines = readFile();

    char[] instructions = lines.get(0).toCharArray();

    Map<String, Pair> map = new HashMap<>();
    Pattern symbol = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");

    for(int i=2;i<lines.size();i++) {
      Matcher matcher = symbol.matcher(lines.get(i));
      while(matcher.find()) {
            map.put(matcher.group(1), new Pair(matcher.group(2), matcher.group(3)));
      }
    }

    String currentPosition = "AAA";
    int instruction = 0;
    while(!currentPosition.equals("ZZZ")) {
      answer++;
      Pair pair = map.get(currentPosition);
      char instruction1 = instructions[instruction];
      currentPosition = instruction1 == 'R' ? pair.right : pair.left;
      instruction++;
      if(instruction == instructions.length) {
        instruction = 0;
      }
    }


    log.info("Answer 1 - {}", answer);

  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> lines = readFile();

    char[] instructions = lines.get(0).toCharArray();

    Map<String, Pair> map = new HashMap<>();
    Pattern symbol = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");

    Set<String> startingSteps = new HashSet<>();

    for(int i=2;i<lines.size();i++) {
      Matcher matcher = symbol.matcher(lines.get(i));
      while(matcher.find()) {
        map.put(matcher.group(1), new Pair(matcher.group(2), matcher.group(3)));
        if(matcher.group(1).endsWith("A")) {
          startingSteps.add(matcher.group(1));
        }
      }
    }

    List<Long> steps = new ArrayList<>();
    for(String start : startingSteps) {
      String currentPosition = start;
      long stepCount = 0;
      int instruction = 0;
      while(!currentPosition.endsWith("Z")) {
        stepCount++;
        Pair pair = map.get(currentPosition);
        char instruction1 = instructions[instruction];
        currentPosition = instruction1 == 'R' ? pair.right : pair.left;
        instruction++;
        if(instruction == instructions.length) {
          instruction = 0;
        }
      }
      steps.add(stepCount);
    }
    log.info("Answer 2 - {}", lcm(steps));

  }

}