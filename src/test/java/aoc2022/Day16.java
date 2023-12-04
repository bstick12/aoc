package aoc2022;

import aoc2021.Day18;
import com.google.common.base.Splitter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day16 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day16.class.getResource("day16-test.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Data
  @AllArgsConstructor
  public static class Valve {
    String id;
    int rate;
    Set<String> valves;
    Set<String> visitable;
  }

  @Data
  @AllArgsConstructor
  public static class State {
    String id;
    boolean opened;
    int pressure;

    Map.Entry<String, Boolean> getKey() {
      return new AbstractMap.SimpleImmutableEntry<>(id, opened);
    }
  }

  Map<String, Valve> valves;

  @Test
  public void day15_part1() {

    Pattern compile = Pattern.compile("Valve (\\w+) has flow rate=(\\d+); .* valves? (.*)");

    List<String> lines = readFile();

    valves = new HashMap<>();

    for (String line : lines) {
      Matcher matcher = compile.matcher(line);
      matcher.find();
      Valve valve = new Valve(matcher.group(1), Integer.valueOf(matcher.group(2)),
          new HashSet(Splitter.on(",").trimResults().splitToList(matcher.group(3))), new HashSet<>());
      log.info("{} {} {}", valve);
      valves.put(valve.getId(), valve);

    }
    log.info("Ans - {}", 0);

    int minutes = 30;

    Map<Map.Entry<String, Boolean>, Integer> best = new HashMap<>();
    List<State> states = new ArrayList<>();
    states.add(new State("AA", false, 0));

    for(int i=1;i<minutes+1;i++) {
      log.info("{} {}", i, states.size());
      log.info("{}", states);

      List<State> newStates = new ArrayList<>();

      for (State state : states) {
        if(best.containsKey(state.getKey()) && state.getPressure() <= best.get(state.getKey())) {
          continue;
        }
        best.put(state.getKey(), state.getPressure());

        if(!state.isOpened() && valves.get(state.getId()).getRate() != 0) {
          newStates.add(new State(state.getId(), true, state.getPressure() + valves.get(state.getId()).getRate() * (minutes - i)));
        }

        for (String valve : valves.get(state.getId()).getValves()) {
          newStates.add(new State(valve, false, state.getPressure()));
        }

      }
      states = newStates;

    }
    log.info("{}", states.stream().map(s -> s.getPressure()).max(Integer::compareTo));


  }



}
