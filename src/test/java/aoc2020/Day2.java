
package aoc2020;

    import java.io.IOException;
    import java.net.URISyntaxException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.util.List;
    import java.util.Set;
    import java.util.stream.Collectors;
    import lombok.extern.slf4j.Slf4j;
    import org.junit.jupiter.api.Test;

@Slf4j
public class Day2 {

  @Test
  public void testPuzzle1() throws URISyntaxException, IOException {
    List<String> input = Files.readAllLines(Path.of(Day2.class.getResource("day02.txt").toURI()));

    int valid=0;
    for (String s : input) {
      String[] s1 = s.split(" ");
      String[] s2 = s1[0].split("-");
      int min = Integer.valueOf(s2[0]);
      int max = Integer.valueOf(s2[1]);
      char c = s1[1].charAt(0);
      int count=0;
      for (char c1 : s1[2].toCharArray()) {
        if(c == c1) {
          count++;
        }
      }
      if(count >= min && count <= max) {
        valid++;
      }
    }

    log.info("Valid = {}", valid);


  }

  @Test
  public void testPuzzle2() throws URISyntaxException, IOException {
    List<String> input = Files.readAllLines(Path.of(Day2.class.getResource("day02.txt").toURI()));

    int valid=0;
    for (String s : input) {
      String[] s1 = s.split(" ");
      String[] s2 = s1[0].split("-");
      int min = Integer.valueOf(s2[0]);
      int max = Integer.valueOf(s2[1]);
      char c = s1[1].charAt(0);
      if(c == s1[2].toCharArray()[min-1] || c == s1[2].toCharArray()[max-1]) {
        if(!(c == s1[2].toCharArray()[min-1] && c == s1[2].toCharArray()[max-1])) {
          valid++;
        }
      }
    }
    log.info("Valid = {}", valid);
  }

}
