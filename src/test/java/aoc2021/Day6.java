package aoc2021;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day6 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(6);
    } else {
      return Files.readAllLines(Path.of(Day7.class.getResource("day06.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }
  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);
    List<Integer> fish = Arrays.stream(input.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());

    for (int i = 0; i < 80; i++) {
      int born = 0;
      for (int x = 0; x < fish.size(); x++) {
        int integer = fish.get(x);
        integer--;
        if (integer < 0) {
          born++;
          fish.set(x, 6);
        } else {
          fish.set(x, integer);
        }
      }
      for (int x = 0; x < born; x++) {
        fish.add(8);
      }
    }
    long answer = fish.size();
    log.info("Ans 1 - {}", answer);

  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(false);
    List<Integer> fishes = Arrays.stream(input.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());

    long spawnCycle[] = new long[9];
    for (int cycleDay : fishes) {
      spawnCycle[cycleDay]++;
    }

    for(int x=0;x<256;x++) {
      long spawning = spawnCycle[0];
      for(int i=0;i<spawnCycle.length-1;i++) {
        spawnCycle[i] = spawnCycle[i+1];
      }
      spawnCycle[8] = spawning;
      spawnCycle[6] += spawning;
    }

    log.info("Ans 2 - {}", Arrays.stream(spawnCycle).sum());

  }








}
