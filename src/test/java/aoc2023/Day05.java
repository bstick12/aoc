package aoc2023;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day05 {

  @SneakyThrows
  public static List<String> readFile() {
    //return Files.readAllLines(Path.of(Day03.class.getResource("day05_1.txt").toURI()));
    return Utils.getInputData(2023,5);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> lines = readFile();

    List<Long> seeds =
        Arrays.stream(lines.get(0).split(":")[1].trim().split(" ")).map(v -> Long.valueOf(v)).collect(Collectors.toList());
    log.info("{}",seeds);

    List<TreeMap<Long, Range>> maps = new ArrayList<>(6);

    int x = 0;


    TreeMap<Long, Range> map = new TreeMap<>();
    for(int i=3;i<lines.size();i++) {
      if(lines.get(i).equals("")) {
        maps.add(map);
        i+=1;
        x++;
        map = new TreeMap<>();
      } else {
        List<Long> collect1 = Arrays.stream(lines.get(i).split(" ")).map(v -> Long.valueOf(v)).collect(Collectors.toList());
        map.put(collect1.get(1), new Range(collect1.get(0), collect1.get(2)));
      }
    }
    maps.add(x++, map);

    TreeSet<Long> locations = new TreeSet<>();
    for (Long seed : seeds) {
      long lookup = seed;
      for (int i = 0; i < maps.size(); i++) {
        Map.Entry<Long, Range> integerRangeEntry = maps.get(i).floorEntry(lookup);
        if(integerRangeEntry != null) {
          if(integerRangeEntry.getKey() + integerRangeEntry.getValue().range >= lookup) {
            lookup = (lookup - integerRangeEntry.getKey()) + integerRangeEntry.getValue().destination;
          }
        }
      }
      locations.add(lookup);
    }

    log.info("Answer 1 - {}", locations.first());

  }

  public record Range (long destination , long range) {}


  @Test
  public void testPuzzle2() throws Exception {

    List<String> lines = readFile();

    List<Long> seeds =
        Arrays.stream(lines.get(0).split(":")[1].trim().split(" ")).map(v -> Long.valueOf(v)).collect(Collectors.toList());

    List<TreeMap<Long, Range>> maps = new ArrayList<>(6);

    int x = 0;


    TreeMap<Long, Range> map = new TreeMap<>();
    for(int i=3;i<lines.size();i++) {
      if(lines.get(i).equals("")) {
        maps.add(map);
        i+=1;
        x++;
        map = new TreeMap<>();
      } else {
        List<Long> collect1 = Arrays.stream(lines.get(i).split(" ")).map(v -> Long.valueOf(v)).collect(Collectors.toList());
        map.put(collect1.get(1), new Range(collect1.get(0), collect1.get(2)));
      }
    }
    maps.add(x++, map);

    long lowestLocation = Long.MAX_VALUE;
    for(int j=0;j<seeds.size();j+=2) {
      for(long seed=seeds.get(j);seed<seeds.get(j)+seeds.get(j+1);seed++) {
        long lookup = seed;
        for (int i = 0; i < maps.size(); i++) {
          Map.Entry<Long, Range> integerRangeEntry = maps.get(i).floorEntry(lookup);
          if (integerRangeEntry != null) {
            if (integerRangeEntry.getKey() + integerRangeEntry.getValue().range >= lookup) {
              lookup = (lookup - integerRangeEntry.getKey()) + integerRangeEntry.getValue().destination;
            }
          }
        }
        lowestLocation = Math.min(lowestLocation, lookup);
      }
    }
    log.info("Answer 2 - {}", lowestLocation);

  }

}
