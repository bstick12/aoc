package aoc2022;

import utils.Utils;
import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day04 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2022,4);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }


//  Pattern compile = Pattern.compile("(on|off)\\ x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");
//
//  List<Day22.Step> steps = new ArrayList<>();
//
//    for (String s : input) {
//    Matcher matcher = compile.matcher(s);
//    if (matcher.matches()) {
//      String toggle = matcher.group(1);
//      int x1 = Integer.valueOf(matcher.group(2));
//      int x2 = Integer.valueOf(matcher.group(3));


  @Test
  public void testPuzzle1() throws Exception {

    Pattern compile = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");

    int connected = 0;
    int enclosed = 0;
    List<String> lines = readFile();

    for (String line : lines) {
      Matcher m = compile.matcher(line);
      if(m.matches()) {
        Range<Integer> range1;
        range1 = Range.range(Integer.valueOf(m.group(1)), BoundType.CLOSED, Integer.valueOf(m.group(2)), BoundType.CLOSED);

        Range<Integer> range2;
        range2 = Range.range(Integer.valueOf(m.group(3)), BoundType.CLOSED, Integer.valueOf(m.group(4)), BoundType.CLOSED);

        if(range1.isConnected(range2)) {
          connected++;
        }
        if(range1.encloses(range2) || range2.encloses(range1)) {
          enclosed++;
        }
      }
    }
    log.info("Ans 1 - {}", enclosed);
    log.info("Ans 1 - {}", connected);

  }

  @Test
  public void testPuzzle2() throws Exception {

    int count = 0;
    List<String> lines = readFile();

    for (String line : lines) {

    }
    log.info("Ans 2 - {}", count);

  }

}
