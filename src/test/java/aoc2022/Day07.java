package aoc2022;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day07 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day07.class.getResource("day07.txt").toURI()));

 //   return Utils.getInputData(2022,7);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void day07() {

    List<String> lines = readFile();

    Map<String, Integer> directories = new HashMap<>();
    String directory = "./";
    lines.remove(0);
    for (String line : lines) {
      if (line.startsWith("$")) {
        if (!line.startsWith("$ ls")) {
          String[] s = line.split(" ");
          if (s[2].equals("..")) {
            directory = StringUtils.substringBeforeLast(directory, "/");
          } else {
            directory += "/" + s[2];
          }
        }
      } else if (!line.startsWith("dir")) {
        String[] s = line.split(" ");
        String tmp = directory;
        while (!tmp.equals(".")) {
          directories.compute(tmp, (k,v) -> v == null ? Integer.valueOf(s[0]) : v + Integer.valueOf(s[0]));
          tmp = StringUtils.substringBeforeLast(tmp, "/");
        }
      }
    }

    log.info("Part 1 - {}", directories.values().stream()
        .filter(v -> v <= 100000)
        .reduce(0, Integer::sum));

    int space = directories.get("./") - 40000000;
    log.info("Part 2 - {}", directories.values().stream()
        .filter(v -> v >= space)
        .min(Integer::compareTo).get());

    directories.keySet().stream().sorted().forEach(k -> log.info("{} {}", k, directories.get(k)));

  }

  @Test
  public void testPuzzle1() throws Exception {


    int count = 0;
    List<String> lines = readFile();

    Set<String> x = new HashSet<>();

    Map<String, Integer> directories = new HashMap<>();

    String directory = "";
    lines.remove(0);
    for (String line : lines) {
      if (line.startsWith("$")) {
        if (line.startsWith("$ ls")) {

        } else {
          String[] s = line.split(" ");
          if (s[2].equals("..")) {
            directory = StringUtils.substringBeforeLast(directory, "/");
          } else {
            if(directory.endsWith("/")) {
              directory += s[2];
            } else {
              directory += "/" + s[2];
            }
          }
        }
      } else if (line.startsWith("dir")) {

      } else {
        String[] s = line.split(" ");
        String tmp = directory;
        while (!tmp.equals("")) {
          Integer orDefault = directories.getOrDefault(tmp, 0);
          orDefault += Integer.valueOf(s[0]);
          directories.put(tmp, orDefault);
          tmp = StringUtils.substringBeforeLast(tmp, "/");
          if(tmp.equals("") && !directory.equals("/")) {
            orDefault = directories.getOrDefault("/", 0);
            orDefault += Integer.valueOf(s[0]);
            directories.put("/", orDefault);
          }
        }
      }
    }

    for (Integer value : directories.values()) {
      if(value <= 100000) {
        count += value;
      }
    }
    log.info("Part 1 - {}", count);

    int i = directories.get("/") - 40000000;
    List<Integer> values = new ArrayList<>();
    for (Integer value : directories.values()) {
      if(value >= i) {
        values.add(value);
      }
    }
    Collections.sort(values);
    log.info("Part 2 - {}", values.get(0));


  }

}
