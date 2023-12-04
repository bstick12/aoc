package aoc2021;

import utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day3 {


  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(3);
    //return Files.readAllLines(Path.of(Day3.class.getResource("day03.txt").toURI()));

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

    int[] pos = new int[input.get(0).length()];

    for (String s : input) {
      for (int x=0;x<s.length();x++) {
        if (s.toCharArray()[x] == '1') {
          pos[x] = pos[x] + 1;
        }
      }

    }

    String gamma = "";
    String epsilon = "";
    for (int x=0;x<pos.length;x++) {
      if(pos[x] > input.size() / 2) {
        gamma = gamma + "1";
        epsilon = epsilon + "0";
      } else {
        gamma = gamma + "0";
        epsilon = epsilon + "1";
      }
    }


    log.info("Ans 1 - {}", Integer.valueOf(gamma, 2));
    log.info("Ans 1 - {}", Integer.valueOf(epsilon, 2));

    log.info("Ans 1 - {}", Integer.valueOf(epsilon, 2) * Integer.valueOf(gamma, 2));

  }


  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile();
    log.info("{}", input);
    int answer=0;

    String or = "";
    for(int pos=0;pos<input.get(0).length();pos++) {
      AtomicInteger count = new AtomicInteger();
      AtomicInteger ones = new AtomicInteger();
      String finalOr = or;
      int finalPos = pos;
      input.stream().filter(x -> x.startsWith(finalOr)).forEach(x -> {
            if (x.toCharArray()[finalPos] == '1') {
              ones.getAndIncrement();
            }
            count.getAndIncrement();
          }
      );
      if(count.get() == 1) {
        or = input.stream().filter(x -> x.startsWith(finalOr)).findFirst().get();
        break;
      } else {
        if (ones.get() * 2 >= count.get()) {
          or = or + "1";
        } else {
          or = or + "0";
        }
      }
    }
    int a = Integer.valueOf(or, 2);
    log.info("Ans 1 - {}", or);

    or = "";
    for(int pos=0;pos<input.get(0).length();pos++) {
      AtomicInteger count = new AtomicInteger();
      AtomicInteger ones = new AtomicInteger();
      String finalOr = or;
      int finalPos = pos;
      input.stream().filter(x -> x.startsWith(finalOr)).forEach(x -> {
            if (x.toCharArray()[finalPos] == '1') {
              ones.getAndIncrement();
            }
            count.getAndIncrement();
          }
      );
      if(count.get() == 1) {
          or = input.stream().filter(x -> x.startsWith(finalOr)).findFirst().get();
          break;
      } else {
        if (ones.get() * 2 >= count.get()) {
          or = or + "0";
        } else {
          or = or + "1";
        }
      }
    }
    int b = Integer.valueOf(or, 2);
    log.info("Ans 1 - {}", or);
    log.info("Ans 1 - {}", a * b);

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
