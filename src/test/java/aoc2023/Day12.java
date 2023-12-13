package aoc2023;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day12 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 12);
    //return Files.readAllLines(Path.of(Day07.class.getResource("day12_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static Map<Map.Entry<String, List<Integer>>, Long> CACHE = new HashMap();

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();

    long answer = 0;
    for (String line : lines) {
      String[] s = line.split(" ");
      String pattern = s[0];
      List<Integer> blocks = Arrays.stream(s[1].split(",")).map(Integer::valueOf).collect(Collectors.toList());
      answer += findMappings(pattern, blocks);
    }
    log.info("Answer {}", answer);

  }

  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();

    long answer = 0;
    for (String line : lines) {
      String[] s = line.split(" ");
      String pattern = s[0];
      List<Integer> blocks = Arrays.stream(s[1].split(",")).map(Integer::valueOf).collect(Collectors.toList());
      String expandedPattern = expand(pattern + "?", 5);
      expandedPattern = expandedPattern.substring(0, expandedPattern.length() - 1);
      final List<Integer> expandedBlocks = new ArrayList<>();
      for(int i=0;i<5;i++) {
        expandedBlocks.addAll(blocks);
      }
      long mappingCount = findMappings(expandedPattern, expandedBlocks);
      // log.info("{} {} {}", expandedPattern, expandedBlocks, mappingCount);
      answer += mappingCount;
    }
    log.info("Answer {}", answer);

  }

  public long findMappings(String pattern, List<Integer> blocks) {

    Map.Entry<String, List<Integer>> entry = Map.entry(pattern, new ArrayList(blocks));

    if (CACHE.containsKey(entry)) {
      return CACHE.get(entry);
    }

    if(blocks.isEmpty()) {
      if(!pattern.contains("#")) {
        return 1;
      }
      return 0;
    }

    long mappings = 0;

    int loopSize = pattern.length() + blocks.size() - blocks.stream().mapToInt(Integer::valueOf).sum();

    for(int i=0;i<loopSize;i++) {
      String mapping = expand(".", i) + expand("#", blocks.get(0)) + ".";

      for(int j=0;j<=pattern.length();j++) {
        if(j<mapping.length() && j<pattern.length()) {
          if (pattern.toCharArray()[j] != mapping.toCharArray()[j] && pattern.toCharArray()[j] != '?') {
            break;
          }
        } else {
          String substring = "";
          if(mapping.length() < pattern.length()) {
            substring = pattern.substring(mapping.length());
          }
          mappings += findMappings(substring, blocks.subList(1, blocks.size()));
          break;
        }
      }
    }

    CACHE.put(entry, mappings);
    return mappings;
  }

  public String expand(String input, int multiplier) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < multiplier; i++) {
      result.append(input);
    }
    return result.toString();
  }

}

