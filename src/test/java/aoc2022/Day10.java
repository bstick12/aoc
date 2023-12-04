package aoc2022;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day10 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day10.class.getResource("day10.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void day09() {

    int ans = 0;

    Set<Integer> cycles = Set.of(20, 60, 100, 140, 180, 220);

    List<String> lines = readFile();

    int cycle=0;
    int register = 1;
    for (String line : lines) {
      if(!line.startsWith("noop")) {
        String[] s = line.split(" ");
        Integer add = Integer.valueOf(s[1]);
        cycle++;
        if(cycles.contains(cycle)) {
          ans += register * cycle;
        }
        cycle++;
        if(cycles.contains(cycle)) {
          ans += register * cycle;
        }
        register += add;
      } else {
        cycle++;
        if(cycles.contains(cycle)) {
          ans += register * cycle;
        }
      }
    }

    log.info("Part 1 - {}", ans);

  }

  @Test
  public void day09_p2() {

    List<String> lines = readFile();

    int cycle=0;
    int register = 1;
    char[] crt = new char[240];
    for (String line : lines) {
      if(!line.startsWith("noop")) {
        String[] s = line.split(" ");
        Integer add = Integer.valueOf(s[1]);
        cycle++;
        populateCrt(crt, cycle, register);
        cycle++;
        populateCrt(crt, cycle, register);
        register += add;
      } else {
        cycle++;
        populateCrt(crt, cycle, register);
      }
    }

    for(int i=0;i<crt.length;i+=40) {
      StringBuilder sb = new StringBuilder();
      for(int j=0;j<40;j++) {
        sb.append(crt[i+j]);
      }
      log.info("{}", sb);
    }

  }

  private void populateCrt(char[] crt, int cycle, int register) {
    int b = cycle % 40;
    if(b >= register && b <= register + 2) {
      crt[cycle-1] = '#';
    } else {
      crt[cycle-1] = '.';
    }
  }

}
