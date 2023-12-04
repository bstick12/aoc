package aoc2020;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day13 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if(!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day13.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {
    List<String> input = readInput(13, false);
    Integer time = Integer.valueOf(input.get(0));
    String[] buses = input.get(1).split(",");

    int busId = 0;
    int wait = Integer.MAX_VALUE;

    for (String bus : buses) {
      if(!bus.equals("x")) {
        int i = Integer.valueOf(bus) - (time % Integer.valueOf(bus));
        if(i<wait) {
          wait = i;
          busId = Integer.valueOf(bus);
        }
      }

    }

    log.info("Ans P1 {}", busId * wait);

  }

  @Test
  public void testPuzzle2() {

    List<String> input = readInput(13, false);
    String[] buses = input.get(1).split(",");
    List<Map.Entry<BigInteger, BigInteger>> pairs = new ArrayList<>();
    for(int i=0;i<buses.length;i++) {
      if(!buses[i].equals("x")) {
        pairs.add(new AbstractMap.SimpleImmutableEntry(new BigInteger(buses[i]), BigInteger.valueOf(i)));
      }
    }

    BigInteger answer = new BigInteger("0");
    BigInteger increment = pairs.get(0).getKey();
    int startPos = 1;

    done:
    while(true) {
      for(int i=startPos;i<pairs.size();i++) {
        Map.Entry<BigInteger, BigInteger> pair = pairs.get(i);
        BigInteger posMod =
            answer.add(pair.getValue()).mod(pair.getKey());
        if(posMod.longValue() != 0) {
          break;
        } else {
          startPos++;
          increment = increment.multiply(pair.getKey());
        }
        if(i==pairs.size() - 1) {
          break done;
        }
      }
      answer = answer.add(increment);
    }
    log.info("Ans P2 {}", answer);
  }

}

