package aoc2023;

import static utils.Utils.getGrid;

import com.sun.source.tree.Tree;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import utils.Point;
import utils.Utils;

@Slf4j
public class Day15 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 15);
    //return Files.readAllLines(Path.of(Day15.class.getResource("day15_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static Map<Map.Entry<String, List<Integer>>, Long> CACHE = new HashMap();

  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();

    long answer = 0;

    for (String cmd : lines.get(0).split(",")) {
      int command = getHash(cmd);
      answer += command;
    }
    log.info("Answer 1 - {}", answer);
  }

  private static int getHash(String cmd) {
    int command = 0;
    for (char c : cmd.toCharArray()) {
      command += c;
      command *= 17;
      command %= 256;
    }
    return command;
  }

  public record Lens(String label, int focal) { }

  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();

    long answer = 0;

    TreeMap<Integer, List<Lens>> boxes = new TreeMap<>();

    for (String cmd : lines.get(0).split(",")) {
      String[] split = cmd.split("[=-]");
      final String label = split[0];
      final Lens lens;
      List<Lens> list = boxes.getOrDefault(getHash(label),  new ArrayList<>());
      if(split.length == 1) {
        lens = new Lens(label, -1);
        for (int i = 0; i < list.size(); i++) {
          Lens existing = list.get(i);
          if(existing.label.equals(lens.label)) {
            list.remove(i);
            break;
          }
        }
      } else {
        lens = new Lens(label, Integer.valueOf(split[1]));
        boolean replaced = false;
        for (int i = 0; i < list.size(); i++) {
          Lens existing = list.get(i);
          if(existing.label.equals(lens.label)) {
            list.remove(i);
            list.add(i, lens);
            replaced = true;
            break;
          }
        }
        if (!replaced) {
          list.add(lens);
        }
      }
      boxes.put(getHash(label), list);
    }

    for (Map.Entry<Integer, List<Lens>> entry : boxes.entrySet()) {
      int box = 0;
      for (int i = 0; i < entry.getValue().size(); i++) {
        Lens lens = entry.getValue().get(i);
        box += (entry.getKey() + 1) * (i+1) * lens.focal;
      }
      answer += box;
    }

    log.info("Answer 2 - {}", answer);

  }
}

