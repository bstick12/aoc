package aoc2023;

import utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day02 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023,2);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();

    Pattern pattern = Pattern.compile("Game (\\d+):");
    Pattern pattern2 = Pattern.compile("(\\d+) (blue|red|green)");


    Map<String, Integer> cubes = Map.of(
        "red",12,
        "green", 13,
        "blue", 14
    );

    int answer = 0;
    for (String line : lines) {
      Matcher matcher = pattern.matcher(line);
      if(matcher.find()) {
        int gameId = Integer.valueOf(matcher.group(1));
        String[] games = line.split(":");
        Matcher matcher2 = pattern2.matcher(games[1]);
        boolean busted = false;
        while(matcher2.find()) {
          String colour = matcher2.group(2);
          int count = Integer.valueOf(matcher2.group(1));
          if(cubes.get(colour) < count) {
            busted = true;
            break;
          }
        }
        answer += busted ? 0 : gameId;
      }

    }

    log.info("Answer 1 - {}", answer);

  }

  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();

    Pattern pattern = Pattern.compile("Game (\\d+):");
    Pattern pattern2 = Pattern.compile("(\\d+) (blue|red|green)");

    int answer = 0;
    for (String line : lines) {
      Matcher matcher = pattern.matcher(line);
      if(matcher.find()) {
        Map<String, Integer> counts = new HashMap<>();
        String[] games = line.split(":");
        Matcher matcher2 = pattern2.matcher(games[1]);
        while(matcher2.find()) {
          String colour = matcher2.group(2);
          int count = Integer.valueOf(matcher2.group(1));
          counts.compute(colour, (k, v) -> (v == null) ? count : Math.max(v, count));
        }
        answer += counts.values().stream().reduce(1, (a, b) -> a * b);
      }

    }

    log.info("Answer 2 - {}", answer);

  }

}
