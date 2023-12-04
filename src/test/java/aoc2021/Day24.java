package aoc2021;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day24 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(24);
    } else {
      return Files.readAllLines(Path.of(Day24.class.getResource("day24.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());

  }


  @Test
  public void testPuzzle1() throws Exception {

    List<String> instructions = readFile(false);



    Map<String, Long> vals = new HashMap<>();
    vals.put("w", 0L);
    vals.put("x", 0L);
    vals.put("y", 0L);
    vals.put("z", 0L);

    for (int i = 0; i < instructions.size(); i+=18) {

      log.info("{} {}", instructions.get(i + 5), instructions.get(i+15));

    }


      String monad =
                      "92915979999498";
    monad = "21611513911181";

      int input=0;
      for (int i = 0; i < instructions.size(); i++) {
        if (i % 18 == 0) {
          vals.put("w", Long.valueOf(String.valueOf(monad.charAt(input))));
          input++;
        } else {
          String[] instruction = instructions.get(i).split(" ");
          switch (instruction[0]) {
            case "add":
              vals.put(instruction[1], vals.get(instruction[1]) + value(instruction[2], vals));
              break;
            case "mul":
              vals.put(instruction[1], vals.get(instruction[1]) * value(instruction[2], vals));
              break;
            case "div":
              vals.put(instruction[1], vals.get(instruction[1]) / value(instruction[2], vals));
              break;
            case "mod":
              vals.put(instruction[1], vals.get(instruction[1]) % value(instruction[2], vals));
              break;
            case "eql":
              vals.put(instruction[1], vals.get(instruction[1]) == value(instruction[2], vals) ? 1L : 0L);
              break;
            default:
              throw new RuntimeException("Invalid instruction" + Arrays.toString(instruction));
          }
        }
      }

      log.info("{}", vals);


  }

  private long value(String s, Map<String, Long> vals) {
    if(NumberUtils.isParsable(s)) {
      return Long.valueOf(s);
    } else {
      return vals.get(s);
    }
  }


}
