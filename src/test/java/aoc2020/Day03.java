package aoc2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day03 {

  @Test
  public void testPuzzle2() throws URISyntaxException, IOException {
    List<String> input = Files.readAllLines(Path.of(Day2.class.getResource("day03.txt").toURI()));
    log.info("Count = {}", getCount(input, 1, 3));
  }


  @Test
  public void testPuzzle1() throws URISyntaxException, IOException {
    List<String> input = Files.readAllLines(Path.of(Day2.class.getResource("day03.txt").toURI()));
    long totalCount = 1;
    int count;
    totalCount = totalCount * getCount(input, 1, 1) *
        getCount(input, 1, 3) *
        getCount(input, 1, 5) *
        getCount(input, 1, 7) *
        getCount(input, 2, 1);
    log.info("Count = {}", totalCount);
  }

  private int getCount(List<String> input, int down, int right) {
    int count=0;
    int pos=right;
    for(int i=down;i<input.size();i+=down) {
      if(input.get(i).toCharArray()[pos] == '#') {
        count++;
      }
      pos=(pos+right) % input.get(1).length();
    }
    return count;
  }
}
