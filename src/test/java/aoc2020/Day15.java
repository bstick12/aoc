package aoc2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day15 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if(!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day15.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  @Test
  public void testPuzzle2() {
    List<String> input = readInput(15, true);
    Iterator<String> iterator = input.iterator();

    List<Integer> collect = Arrays.stream(input.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());

    Map<Integer, Integer> locations = new HashMap<>();
    for(int i=0;i<collect.size()-1;i++) {
      locations.put(collect.get(i),i);
    }

    int next=collect.get(collect.size() - 1);
    for(int i=collect.size()-1;i<29999999;i++) {
      if(locations.containsKey(next)) {
        int previousLocation = locations.get(next);
        int nNext = i - previousLocation;
        locations.put(next,i);
        next = nNext;
      } else {
        locations.put(next,i);
        next=0;
      }
    }

    log.info("Ans P2 {}", next);

  }

  @Test
  public void testPuzzle1() {
    List<String> input = readInput(15, true);
    Iterator<String> iterator = input.iterator();

    List<Integer> collect = Arrays.stream(input.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());
    Map<Integer, Integer> locations = new HashMap<>();

    for(int i=collect.size()-1;i<2020;i++) {
      int integer = collect.get(i);
      int ans=0;
      for(int j=collect.size()-2;j>=0;j--) {
        if(collect.get(j) == (integer)) {
          ans=i-j;
          break;
        }
      }
      collect.add(ans);
    }

    log.info("Ans P1 {}", collect.get(2019));


  }



}

