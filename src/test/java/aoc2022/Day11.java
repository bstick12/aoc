package aoc2022;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day11 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day11.class.getResource("day11.txt").toURI()));
  }

  @Data
  @EqualsAndHashCode
  public static class Monkey {
    public Queue<Long> items = new ArrayDeque<>();
    public String operation;
    public long divisor;
    public int truety;
    public int falsy;
    public long inspect = 0;
  }

  @Test
  public void day11_part1() {
    log.info("Part 1 - {}", monkeyPlay(20));
  }

  @Test
  public void day11_part2() {
    log.info("Part 2 - {}", monkeyPlay(10000));

  }

  private long monkeyPlay(int boundary) {

    List<String> lines = readFile();

    Pattern compile = Pattern.compile("old (.) (.*)");

    Map<Integer, Monkey> monkeys = new HashMap<>();

    for(int i=0;i<lines.size();i+=7) {
      Monkey monkey = new Monkey();
      String[] split = lines.get(i + 1).split(":");
      Arrays.stream(split[1].split(",")).forEach(s -> monkey.items.add(Long.valueOf(Long.valueOf(s.trim()))));
      monkey.operation =  lines.get(i +2).split("= ")[1];
      monkey.divisor =  Long.valueOf(lines.get(i + 3).split("by ")[1]);
      monkey.truety =  Integer.valueOf(lines.get(i + 4).split("monkey ")[1]);
      monkey.falsy =  Integer.valueOf(lines.get(i + 5).split("monkey ")[1]);
      monkeys.put(i/7, monkey);
    }

    long lcm = monkeys.values().stream()
        .mapToLong(m -> m.divisor)
        .reduce((a,b) -> a*b).getAsLong();

    for(int i=0;i<boundary;i++) {
      for(int j=0;j<monkeys.size();j++) {
        Monkey monkey = monkeys.get(j);
        while(!monkey.items.isEmpty()) {
          Long item = monkey.items.poll();
          Matcher matcher = compile.matcher(monkey.operation);
          if(matcher.matches()) {
            String group = matcher.group(2);
            long augend = group.equals("old") ? item : Long.valueOf(group);
            switch (matcher.group(1)) {
              case "+":
                item += augend;
                break;
              case "*":
                item *= augend;
              break;
            }
          }
          item = boundary == 20 ? item / 3 : item;
          if(item % monkey.divisor == 0) {
            monkeys.get(monkey.truety).items.add(boundary == 20 ? item : item % lcm);
          } else {
            monkeys.get(monkey.falsy).items.add(boundary == 20 ? item : item % lcm);
          }
          monkey.inspect++;

        }
      }
    }

    return monkeys.values().stream().map(m -> m.inspect)
        .sorted(Comparator.reverseOrder()).collect(Collectors.toList())
        .subList(0,2).stream()
        .reduce((a,b) -> a*b).get();

  }

}
