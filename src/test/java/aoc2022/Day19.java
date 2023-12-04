package aoc2022;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day19 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day19.class.getResource("day19.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Data
  @AllArgsConstructor
  public static class Blueprint {

    int id;
    int oreore;
    int clayore;
    int obsidianore;
    int obsidiaclay;
    int geodeore;
    int geodeobsidian;

  }


  @Data
  @AllArgsConstructor
  public static class State {

    int ore_robot;
    int clay_robot;
    int obsidian_robot;
    int geode_robot;
    int ore;
    int clay;
    int obsidian;
    int geode;

    public State tick(Blueprint bp) {
      return new State(ore_robot, clay_robot, obsidian_robot, geode_robot,
          ore + (ore_robot * 1), clay + (clay_robot * 1),
          obsidian + (obsidian_robot * 1), geode + (geode_robot * 1));
    };

  }

  @Test
  public void day18_part1() {

    int count=0;

    Pattern pattern = Pattern.compile("Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian");

    List<String> lines = readFile();

    Map<Integer, Blueprint> blueprints = new HashMap<>();
    for (String line : lines) {

      Matcher matcher = pattern.matcher(line);
      matcher.find();
      Blueprint bp = new Blueprint(
          Integer.valueOf(matcher.group(1)),
          Integer.valueOf(matcher.group(2)),
          Integer.valueOf(matcher.group(3)),
          Integer.valueOf(matcher.group(4)),
          Integer.valueOf(matcher.group(5)),
          Integer.valueOf(matcher.group(6)),
          Integer.valueOf(matcher.group(7))
          );
      log.info("{}", bp);
      blueprints.put(bp.id, bp);
    }

    State start = new State(1,0,0,0,0,0,0,0);

  }



}
