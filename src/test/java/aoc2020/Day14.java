package aoc2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day14 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if(!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day14.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {
    List<String> input = readInput(14, false);
    Iterator<String> iterator = input.iterator();

    String pattern = "mem\\[(\\d+)\\]\\ =\\ (\\d+)";
    Pattern r = Pattern.compile(pattern);

    Map<Long, Long> blah = new HashMap<>();

    long lo=0;
    long hi=0;
    String mask = "";
    for (String s : input) {
      if(s.startsWith("mask")) {
        lo = Long.valueOf(s.split(" = ")[1].replace('X', '1'),2);
        hi = Long.valueOf(s.split(" = ")[1].replace('X', '0'),2);
        mask = s.split(" = ")[1];
      } else {
        Matcher m = r.matcher(s);
        if(m.find()) {
          String add = Long.toBinaryString(Long.valueOf(m.group(1)));
          long l = Long.valueOf(m.group(1)) | hi & lo;
          char[] s1 = StringUtils.leftPad(Long.toBinaryString(l), 36, '0').toCharArray();
          char[] am = mask.toCharArray();
          for(int i=0;i<s1.length;i++) {
            if(am[i] == 'X') {
              s1[i] = 'X';
            }
          }
          List<String> addresses = new ArrayList<String>();
          iterate(String.valueOf(s1), addresses, 0);
          for (String address : addresses) {
            blah.put(Long.valueOf(address, 2), Long.valueOf(m.group(2)));
          }
        }
      }
    }
    log.info("Ans P1 {}", blah.values().stream().mapToLong(Long::valueOf).sum());
  }

  private void iterate(String mask, List<String> addresses, int i) {
    if(mask.contains("X") || i > mask.length()) {
      if(mask.toCharArray()[i] == 'X') {
        char[] c = mask.toCharArray();
        c[i] = '1';
        iterate(String.valueOf(c), addresses,i+1);
        c[i] = '0';
        iterate(String.valueOf(c), addresses,i+1);
      } else {
        iterate(mask, addresses, i+1);
      }
    } else {
      addresses.add(mask);
    }
  }


  @Test
  public void testPuzzle2() {
    List<String> input = readInput(14, true);
    Iterator<String> iterator = input.iterator();

    String pattern = "mem\\[(\\d+)\\]\\ =\\ (\\d+)";
    Pattern r = Pattern.compile(pattern);

    Map<String, Long> blah = new HashMap<>();

    long lo=0;
    long hi=0;
    for (String s : input) {
      if(s.startsWith("mask")) {
        lo = Integer.valueOf(s.split(" = ")[1].replace('X', '1'),2);
        hi = Integer.valueOf(s.split(" = ")[1].replace('X', '0'),2);
      } else {
        Matcher m = r.matcher(s);
        if(m.find()) {
          blah.put(m.group(1), Long.valueOf(m.group(2)) | hi & lo);
          log.info("Ans P1 {} {}", m.group(1), Long.valueOf(m.group(2)) | hi & lo);
        }
      }
    }
    log.info("Ans P1 {}", blah.values().stream().mapToLong(Long::valueOf).sum());
  }


}

