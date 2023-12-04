package aoc2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day17 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day17.class.getResource("day18.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }



  @Test
  public void day18_part1() {

    int count=0;

    List<Integer> chamber = new ArrayList<>();
    chamber.add(Integer.parseInt("0000000", 2));
    chamber.add(Integer.parseInt("0000000", 2));
    chamber.add(Integer.parseInt("0000000", 2));

    int x = Integer.parseInt("0011110", 2);
    int y = Integer.parseInt("0000000", 2);
    int z = Integer.parseInt("0010000", 2);
    int a = Integer.parseInt("1000000", 2);

    assertEquals(x+y, x | y);
    assertEquals(x+z, x | z);


    List<String> lines = readFile();

    for (String line : lines) {

    }

    log.info("Answer {}", count);
  }



}
