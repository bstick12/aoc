package aoc2021;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day16 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(16);
    } else {
      return Files.readAllLines(Path.of(Day15.class.getResource("day16.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  List<Integer> versions;
  List<Integer> types;
  List<Integer> values;

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(true);
    long answer = 0;

    StringBuilder sb = new StringBuilder();

    for (char c : input.get(0).toCharArray()) {
      String s = String.valueOf(c);
      Integer integer = Integer.valueOf(s, 16);
      String padded = StringUtils.leftPad(Integer.toBinaryString(integer), 4, "0");
      sb.append(padded);
    }

    versions = new ArrayList<>();
    types = new ArrayList<>();
    values = new ArrayList<>();

    parse(sb.toString());

    answer  = versions.stream().mapToInt(Integer::valueOf).sum();

    log.info("Ans 1 - {}", answer);

  }

  private void parse(String toString) {

    if(StringUtils.isEmpty(toString)) {
      return;
    }

    Integer version = Integer.valueOf(toString.substring(0, 3), 2);
    versions.add(version);
    Integer type = Integer.valueOf(toString.substring(3, 6), 2);

    log.info("V={}, T={}", version, type);

    types.add(type);
    String substring = toString.substring(6);
    switch (type) {
      case 4:
        log.info("literal {}", substring);
        String s = readLiteral(substring);
        parse(s);
        break;
      default:
        log.info("operator {}", substring);
        String s1 = readOperator(substring);
        parse(s1);
        break;
    }

  }

  private String readOperator(String substring) {

    int count = 1;
    String type =  String.valueOf(substring.charAt(0));
    if(type.equals("0")) {
      count += 15;
      int l = Integer.valueOf(substring.substring(1,16), 2);
      count += l;
      parse(substring.substring(16,27));
      parse(substring.substring(27,27+l-11));
    } else {
      count += 11;
      int l = Integer.valueOf(substring.substring(1,12), 2);
      count += 11 * l;
      for(int i=0;i<l;i++) {
        parse(substring.substring(12 + (i*11), 23 + (i*11)));
      }
    }

    int remaining = substring.length() - count;
    String substring1 = substring.substring(count + remaining % 4);
    while(substring1.startsWith("0000")) {
      substring1 = substring1.substring(4);
    }
    return substring1;

  }

  private String readLiteral(String substring) {

    StringBuilder sb = new StringBuilder();

    int count = 0;

    while(true) {
      String substring1 = substring.substring(0, 5);
      substring = substring.substring(5);
      if(substring1.charAt(0) == '1') {
        sb.append(substring1.substring(1));
      } else {
        sb.append(substring1.substring(1));
        break;
      }
    }
    values.add(Integer.valueOf(sb.toString(), 2));
    log.info("{}", Integer.valueOf(sb.toString(), 2));

    int remaining = substring.length() % 4;
    substring = substring.substring(remaining);
    while(substring.startsWith("0000")) {
      substring = substring.substring(4);
    }
    return substring;
  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(true);
    long answer = 0;


    log.info("Ans 1 - {}", answer);


  }

}