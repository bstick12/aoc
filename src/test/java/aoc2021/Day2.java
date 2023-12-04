package aoc2021;

import utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day2 {


  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2);
  }


  @SneakyThrows
  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }


  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile();
    log.info("{}", input);
    int answer=0;

    int forward = 0;
    int depth = 0;

    for (String s : input) {
      String[] s1 = s.split(" ");
      String direction = s1[0];
      int value = Integer.valueOf(s1[1]);

      switch (direction) {
        case "forward":
         forward += value;
          break;
        case "down":
         depth += value;
        break;
        case "up":
        depth -= value;
        break;
        default:
          break;
      }

    }

    answer = forward * depth;

    log.info("Ans 1 - {}", answer);

  }


  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile();

    int answer=0;
    int horizontal = 0;
    int depth = 0;
    int aim=0;

    for (String s : input) {
      String[] s1 = s.split(" ");
      String direction = s1[0];
      int value = Integer.valueOf(s1[1]);
      switch (direction) {
        case "forward":
          horizontal += value;
          depth += aim * value;
          break;
        case "down":
          aim += value;
          break;
        case "up":
          aim -= value;
          break;
        default:
          break;
      }

    }

    answer = horizontal * depth;
    log.info("Ans 2 - {}", answer);
  }

  static class Submarine {
    int horizontal = 0;
    int depth = 0;
    int aim = 0;
  }

  interface Action {
    void perform(Submarine submarine, Integer value);
  }

  @Test
  public void testPuzzle2Complicated() throws Exception {

    Map<String, Action> actions = new HashMap<>();
    actions.put("forward", (submarine, value) -> {
      submarine.horizontal += value;
      submarine.depth += submarine.aim * value;
    });
    actions.put("down", (submarine, value) -> {
      submarine.aim += value;
    });
    actions.put("up", (submarine, value) -> {
      submarine.aim -= value;
    });

    List<String> input = readFile();
    int answer=0;
    Submarine submarine = new Submarine();
    for (String s : input) {
      String[] s1 = s.split(" ");
      String direction = s1[0];
      int value = Integer.valueOf(s1[1]);
      actions.get(direction).perform(submarine,value);
    }
    answer = submarine.horizontal * submarine.depth;
    log.info("Ans 2 - {}", answer);
  }


}
