package aoc2021;

import utils.Utils;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day14 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(14);
    } else {
      return Files.readAllLines(Path.of(Day14.class.getResource("day14.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);

    String starting = input.get(0);

    Map<String, String> mapping = new HashMap<>();
    for (String s : input.subList(2, input.size())) {
      String key = s.split("->")[0].trim();
      String value = s.split("->")[1].trim();
      mapping.put(key, value);
    }

    for(int i=0;i<10;i++) {
      StringBuilder sb = new StringBuilder();
      for(int j=0;j<starting.length()-1;j++) {
        String substring = starting.substring(j, j + 2);
        sb.append(substring.charAt(0));
        sb.append(mapping.getOrDefault(substring, ""));
      }
      sb.append(starting.charAt(starting.length()-1));
      starting = sb.toString();
    }

    Map<Character, Long> counts = new HashMap<>();
    String finalStarting = starting;
    starting.chars().distinct().forEach(
        i -> {
          char c = (char) i;
          counts.put(c, (long) StringUtils.countMatches(finalStarting, c));
        }
    );
    Long min = counts.values().stream().min(Long::compare).get();
    Long max = counts.values().stream().max(Long::compare).get();

    long answer = max - min;

    log.info("Ans 1 - {}", answer);

  }


  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(false);

    String starting = input.get(0);

    Map<String, String> mapping = new HashMap<>();
    for (String s : input.subList(2, input.size())) {
      String key = s.split("->")[0].trim();
      String value = s.split("->")[1].trim();
      mapping.put(key, value);
    }

    Map<String, BigInteger> counts = new HashMap<>();
    for(int j=0;j<starting.length()-1;j++) {
      String substring = starting.substring(j, j + 2);
      BigInteger orDefault = counts.getOrDefault(substring, new BigInteger("0"));
      counts.put(substring, orDefault.add(new BigInteger("1")));
    }


    for(int i=0;i<40;i++) {
      Map<String, BigInteger> newCounts = new HashMap<>();

      for (Map.Entry<String, BigInteger> stringLongEntry : counts.entrySet()) {
        String s = mapping.get(stringLongEntry.getKey());
        if(s!=null) {
          String one = stringLongEntry.getKey().substring(0,1).concat(s);
          String two = s.concat(stringLongEntry.getKey().substring(1,2));
          newCounts.put(one, newCounts.getOrDefault(one,new BigInteger("0")).add(stringLongEntry.getValue()));
          newCounts.put(two, newCounts.getOrDefault(two,new BigInteger("0")).add(stringLongEntry.getValue()));
        } else {
          newCounts.put(stringLongEntry.getKey(), stringLongEntry.getValue());
        }

      }
      counts = newCounts;
    }

    Map<Character, BigInteger> countChars = new HashMap<>();
    countChars.put(starting.charAt(0), new BigInteger("1"));
    countChars.put(starting.charAt(starting.length()-1), new BigInteger("1"));
    for (Map.Entry<String, BigInteger> stringLongEntry : counts.entrySet()) {
      countChars.put(stringLongEntry.getKey().charAt(0), countChars.getOrDefault(stringLongEntry.getKey().charAt(0), new BigInteger("0")).add(stringLongEntry.getValue()));
      countChars.put(stringLongEntry.getKey().charAt(1), countChars.getOrDefault(stringLongEntry.getKey().charAt(1), new BigInteger("0")).add(stringLongEntry.getValue()));
    }


    BigInteger min = countChars.values().stream().min(BigInteger::compareTo).get();
    BigInteger max = countChars.values().stream().max(BigInteger::compareTo).get();

    BigInteger answer = max.subtract(min);
    log.info("Ans 2 - {}", answer.divide(new BigInteger("2")));

  }

}
