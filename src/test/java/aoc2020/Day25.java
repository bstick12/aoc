package aoc2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day25 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if (!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day25.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {

    int card = 15733400;
    int door = 6408062;

    long cardLoop = findLoop(card, 7);
    long doorLoop = findLoop(door, 7);
    log.info("{}", cardLoop);
    log.info("{}", doorLoop);

    log.info("{}", encryptionKey(8, 17807724));

    log.info("{}", encryptionKey(cardLoop, door));


  }

  long encryptionKey(long loop, long publicKey) {
    long value = 1;
    for(int i=0;i<loop;i++) {
      value *= publicKey;
      value = value % 20201227;
    }
    return value;
  }

  long findLoop(long target, int subject) {
    long value = 1;
    long i=0;
    while(value != target) {
      i++;
      value *= subject;
      value = value % 20201227;
    }
    return i;
  }

}


