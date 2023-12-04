package aoc2020;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day23 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if (!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day23.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  private static int ITERATIONS=10_000_000;

  private static int SIZE=1_000_000;

  @Test
  public void testPuzzle1() {

    LinkedList<Integer> cups = new LinkedList<>();
    int input = 158937462;
    while(input > 0) {
      cups.addFirst(input % 10);
      input /= 10;
    }

    for(int i=10;i<=SIZE;i++) {
      cups.addFirst(i);
    }

    Optional<Integer> max = cups.stream().max(Integer::compareTo);
    log.info("P1 endstate {}", cups);


    for(int i=0;i<ITERATIONS;i++) {

      int poll = cups.poll();
      int one = cups.poll();
      int two = cups.poll();
      int three = cups.poll();

      int target = poll;
      if(target - 1 == 0) {
        target = max.get();
      } else {
        target--;
      }

      int destination = 0;

      outer:
      while(true) {
        for (int j = 0; j < cups.size(); j++) {
          if(cups.get(j) == target) {
            destination = j;
            break outer;
          }
        }
        if(target - 1 == 0) {
          target = max.get();
        } else {
          target--;
        }
      }

      cups.add(destination + 1, three);
      cups.add(destination + 1, two);
      cups.add(destination + 1, one);
      cups.addLast(poll);
      log.info("P1 endstate {}", cups);


    }

    log.info("P1 endstate {}", cups, max);

    BigInteger answer = BigInteger.valueOf(0);
    int start=1;
    outer:
    while(true) {
      int poll = cups.poll();
      if(poll == 1) {
        while(!cups.isEmpty()) {
          answer = answer.multiply(BigInteger.valueOf(10)).add(BigInteger.valueOf(cups.poll()));
        }
        break outer;
      }
      cups.addLast(poll);
    }

    log.info("Ans P1 {}", answer);

  }

  @Test
  public void testPuzzle2() {

    int input = 158937462;
    int size = 1_000_000;
    int iterations = 10_000_000;

    Map<Integer, Integer> cups = new HashMap<>(size);

    for(int i=size; i>10; i--) {
      cups.put(i-1, i);
    }
    int prev = 10;
    while(input > 0) {
      int value = input % 10;
      cups.put(value, prev);
      input /= 10;
      prev = value;
    }
    cups.put(size, prev);

    // Start with first number of input
    int current=1;

    for(int i=0;i<iterations;i++) {

      int one = cups.get(current);
      int two = cups.get(one);
      int three = cups.get(two);

      int target = current - 1;
      while(target == one || target == two || target == three || target == current || target == 0) {
        if(target == 0) {
          target = size;
        } else {
          target --;
        }
      }

      int whattargetpointedat = cups.get(target);
      int whatthreepointedat = cups.get(three);
      cups.put(current, whatthreepointedat);
      cups.put(target, one);
      cups.put(three, whattargetpointedat);
      current = whatthreepointedat;

    }

    log.info("{}", cups.get(1));
    log.info("{}", cups.get(cups.get(1)));
    log.info("{}", (long) cups.get(1) * (long) cups.get(cups.get(1)));

  }

  private String print(Map<Integer, Integer> cups, int next) {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(next);
    sb.append(", ");
    int start = next;
    while(true) {
      start = cups.get(start);
      if(start == next) {
        break;
      } else {
        sb.append(start);
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }

}

