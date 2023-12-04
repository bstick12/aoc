package aoc2019;

import com.sun.jdi.connect.Connector;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day2 {

  @Test
  public void puzzle1() throws URISyntaxException, IOException {
    List<String> input = Files.readAllLines(Path.of(Day2.class.getResource("day02.txt").toURI()));
    Integer[] split = Arrays.stream(input.get(0).split(",")).map(Integer::valueOf).toArray(Integer[]::new);
    split[1] = 12;
    split[2] = 2;

    outer:
    for(int i=0;i<split.length;i+=4) {
      int opCode = split[i];
      log.info("Op code = {}", opCode);
      if(opCode == 99 ) {
        break;
      }
      int op1 = split[i+1];
      int op2 = split[i+2];
      int res = split[i+3];
      switch (opCode) {
        case 1:
          split[res] = split[op1] + split[op2];
          break;
        case 2:
          split[res] = split[op1] * split[op2];
          break;
        default:
          throw new RuntimeException("Unknown Opcode");
      }
    }
  log.info("Result {}", Arrays.toString(split));
  }

}
