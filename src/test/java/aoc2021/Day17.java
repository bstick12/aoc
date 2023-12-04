package aoc2021;

import utils.Utils;
import com.google.common.collect.Range;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day17 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(17);
    } else {
      return Files.readAllLines(Path.of(Day17.class.getResource("day17.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);
    Pattern compile = Pattern.compile(".*x=(-?\\d+)..(-?\\d+)..y=(-?\\d+)..(-?\\d+)");
    Matcher matcher = compile.matcher(input.get(0));
    if(matcher.matches()) {
      int min_x=Integer.valueOf(matcher.group(1));
      int max_x=Integer.valueOf(matcher.group(2));
      int min_y=Integer.valueOf(matcher.group(3));
      int max_y=Integer.valueOf(matcher.group(4));

      log.info("Ans 1 - {}", min_y*(min_y+1)/2);

      long answer=0;

      Range<Integer> rX = Range.closed(min_x, max_x);
      Range<Integer> rY = Range.closed(min_y, max_y);

      check(6,7,rX,rY);
      for(int x=0;x<=max_x;x++) {
        for(int y=min_y;y<=min_y*-1;y++) {
          answer+=check(x,y,rX,rY);
        }
      }

      log.info("Ans 2 - {}",answer);


    }


  }

  private long check(int dx, int dy, Range<Integer> rX, Range<Integer> rY) {
    int x=0,y=0;
    int origX=dx;
    int origY=dy;
    while(y>=rY.lowerEndpoint()) {
      x += dx;
      y += dy;
      dx += Integer.compare(0,dx);
      dy -= 1;
//      log.info("{} {}", x, y);
      if(rX.contains(x)) {
        if(rY.contains(y)) {
          log.info("{} {} - {}", origX, origY, origX * origY);
          return 1;
        }
      } else if (dx==0) {
        break;
      }
    }
    return 0;
  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(false);
    int answer = 0;
    log.info("Ans 2 - {}", answer);

  }


}

