package aoc2021;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day19 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(19);
    } else {
      return Files.readAllLines(Path.of(Day19.class.getResource("day19.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());

  }


  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);

    Pattern patH = Pattern.compile("--- scanner (\\d+) ---");
    Pattern patL = Pattern.compile("(-?\\d+),(-?\\d+),(-?\\d+)");

    LinkedList<Set<Beacon>> scanners = new LinkedList<>();

    for (String s : input) {
      if (patH.matcher(s).find()) {
        scanners.add(new HashSet<>());
        continue;
      }
      var m = patL.matcher(s);
      if (m.find()) {
        scanners.getLast().add(
            new Beacon(Integer.parseInt(m.group(1)),
                Integer.parseInt(m.group(2)),
                Integer.parseInt(m.group(3))));
      }
    }

    String rotations = "xxxxyxxxxyxxxxyxxxxzxxxxzzxxxx";

    Set<Beacon> scannerSet = new HashSet<Beacon>();
    scannerSet.add(new Beacon(0,0,0));
    Set<Beacon> answerSet = scanners.pop();
    int loop=0;
    outer:
    while (!scanners.isEmpty()) {
      loop++;
      Set<Beacon> nextSet = scanners.pop();
      for (char c : rotations.toCharArray()) {
            for (Beacon next : nextSet) {
              for (Beacon base : answerSet) {
                Beacon shift = new Beacon(base.x - next.x, base.y - next.y, base.z - next.z);
                Set<Beacon> nextShifted = nextSet.stream().map(x -> x.shift(shift)).collect(Collectors.toSet());
                int count = 0;
                for (Beacon shifted : nextShifted) {
                  if (answerSet.contains(shifted)) {
                    count++;
                  }
                  if (count >= 12) {
                    answerSet.addAll(nextShifted);
                    scannerSet.add(shift);
                    continue outer;
                  }
                }
              }
            }
          nextSet = nextSet.stream().map(x -> x.rotate(c)).collect(Collectors.toSet());
      }
      scanners.add(nextSet);
    }
    long answer = 0;
    log.info("Answer 1 - {}",answerSet.size());

    for (Beacon b1 : scannerSet) {
      for (Beacon b2 : scannerSet) {
        answer = Math.max(answer, b1.distance(b2));
      }
    }
    log.info("Answer 2 - {}",answer);

  }


  @Data
  @AllArgsConstructor
  public class Beacon {

    int x;
    int y;
    int z;

    public Beacon shift(Beacon b) {
      return new Beacon(this.x + b.x, this.y + b.y, this.z + b.z);
    }

    public Beacon rotate(Character axis) {
      switch (axis) {
        case 'x':
          return new Beacon(x, z, -y);
        case 'y':
          return new Beacon(-z, y, x);
        case 'z':
          return new Beacon(y, -x, z);
        default:
          return null;
      }
    }

    public int distance(Beacon b){
      return Math.abs(this.x-b.x)+Math.abs(this.y-b.y)+Math.abs(this.z-b.z);
    }
  }

}
