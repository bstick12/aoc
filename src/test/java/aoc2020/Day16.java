package aoc2020;

import com.google.common.collect.Range;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day16 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if(!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day16.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {
    List<String> input = readInput(16, false);
    Iterator<String> iterator = input.iterator();

    Pattern pattern = Pattern.compile("(\\w+):\\s(\\d+)-(\\d+)\\sor\\s(\\d+)-(\\d+)");

    Set<Range> range = new HashSet<>();
    boolean nbt = false;
    Long errorCount = (long) 0;

    List<String> valid= new ArrayList<String>();
    for(String s: input) {
      Matcher m = pattern.matcher(s);
      if(m.find()) {
        range.add(Range.closed(Long.valueOf(m.group(2)), Long.valueOf(m.group(3))));
        range.add(Range.closed(Long.valueOf(m.group(4)), Long.valueOf(m.group(5))));
      }

      if(s.startsWith("nearby tickets")) {
        nbt = true;
      }

      if(nbt && !s.startsWith("nearby")) {
        List<Long> collect = Arrays.stream(s.split(",")).map(Long::valueOf).collect(Collectors.toList());
        for (Long aLong : collect) {
          boolean found =false;
          for (Range range1 : range) {
             if(range1.contains(aLong)) {
               found=true;
               break;
             }
          }
          if(!found) {
            errorCount += aLong;
          }
        }
      }

    }

    log.info("Ans P1 {}", errorCount);

  }

  @Test
  public void testPuzzle2() {
    List<String> input = readInput(16, false);
    Iterator<String> iterator = input.iterator();

    Pattern pattern = Pattern.compile("(.+):\\s(\\d+)-(\\d+)\\sor\\s(\\d+)-(\\d+)");

    Map<Range, String> range = new HashMap<Range, String>();
    boolean nbt = false;
    Long errorCount = (long) 0;

    List<String> validT= new ArrayList<String>();
    for(String s: input) {
      Matcher m = pattern.matcher(s);
      if(m.find()) {
        range.put(Range.closed(Long.valueOf(m.group(2)), Long.valueOf(m.group(3))), m.group(1));
        range.put(Range.closed(Long.valueOf(m.group(4)), Long.valueOf(m.group(5))), m.group(1));
      }


      if(s.startsWith("nearby tickets")) {
        nbt = true;
      }

      if(nbt && !s.startsWith("nearby")) {
        List<Long> collect = Arrays.stream(s.split(",")).map(Long::valueOf).collect(Collectors.toList());
        boolean valid = true;
        for (Long aLong : collect) {
          boolean found =false;
          for (Map.Entry<Range, String> rangeStringEntry : range.entrySet()) {
            if(rangeStringEntry.getKey().contains(aLong)) {
              found=true;
              break;
            }
          }
          if(!found) {
            valid=false;
            break;
          }
        }
        if(valid) {
          validT.add(s);
        }
      }

    }

    String[] split = "103,197,83,101,109,181,61,157,199,137,97,179,151,89,211,59,139,149,53,107".split(",");

    Set<String> found = new HashSet<>();
    long answer = 1;
    for(int i=0;i<split.length;i++) {
      Set<String> x = new HashSet<>();
      for (String s : validT) {
        Set<String> xy = new HashSet<>();
        List<Long> collect = Arrays.stream(s.split(",")).map(Long::valueOf).collect(Collectors.toList());
        Long aLong = collect.get(i);
        for (Map.Entry<Range, String> rangeStringEntry : range.entrySet()) {
          if (rangeStringEntry.getKey().contains(aLong)) {
            xy.add(rangeStringEntry.getValue());
          }
        }
        if (x.isEmpty()) {
          x.addAll(xy);
        } else {
          x.retainAll(xy);
        }
      }
      x.removeAll(found);
      if (x.size() == 1) {
        found.addAll(x);
        if(x.iterator().next().startsWith("departure")) {
          answer *= Long.valueOf(split[i]);
        }
        i = 0;
      }
    }

    log.info("Ans P2 {}", answer);

  }



}

