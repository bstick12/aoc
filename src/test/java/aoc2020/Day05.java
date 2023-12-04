package aoc2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day05 {

  @Test
  public void testPuzzle2() throws URISyntaxException, IOException {
    List<String> input = Files.readAllLines(Path.of(Day2.class.getResource("day05.txt").toURI()));

    TreeSet<Integer> ids = new TreeSet<>();

    int max = 0;
    for (String s : input) {

      int upper = 127;
      int lower = 0;

      int seatLower = 0;
      int seatUpper = 7;

      s = s.replace('F', '0');
      s = s.replace('B', '1');
      s = s.replace('L', '0');
      s = s.replace('R', '1');

      Integer integer = Integer.valueOf(s, 2);

//      for (char c : s.toCharArray()) {
//
//        switch (c) {
//          case 'F':
//            upper = upper - ((upper - lower) / 2) - 1;
//            break;
//          case 'B':
//            lower = lower + ((upper - lower) / 2) + 1;
//            break;
//          case 'L':
//            seatUpper = seatUpper  - ((seatUpper - seatLower) / 2) - 1;
//            break;
//          case 'R':
//            seatLower = seatLower + ((seatUpper - seatLower) / 2) + 1;
//            break;
//          default:
//            break;
//        }
//
//      }

      if (integer > max) {
        max = integer;
      }

      ids.add(integer);
   }
    log.info("Row {}",max);


    int next=ids.first();
    for (Integer id : ids) {
        if(id != next) {
          log.info("Missing {}", next);
          break;
        }
        next++;
    }
  }
}
