package aoc2022;

import utils.Utils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day03 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2022,3);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }


//  Pattern compile = Pattern.compile("(on|off)\\ x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");
//
//  List<Day22.Step> steps = new ArrayList<>();
//
//    for (String s : input) {
//    Matcher matcher = compile.matcher(s);
//    if (matcher.matches()) {
//      String toggle = matcher.group(1);
//      int x1 = Integer.valueOf(matcher.group(2));
//      int x2 = Integer.valueOf(matcher.group(3));


  @Test
  public void testPuzzle1() throws Exception {

    Pattern compile = Pattern.compile("");

    int count = 0;
    List<String> lines = readFile();

    int x = (int) 'a';
    int y = (int) 'A';

//    lines.clear();
//    lines.add("vJrwpWtwJgWrhcsFMMfFFhFp");
//    lines.add("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL");

    for (String line : lines) {
      int len = line.length();
      Set<Character> set = new HashSet<>();
      for(int i = 0;i < len / 2;i++) {
        set.add(line.charAt(i));
      }
      for(int i=len/2;i<len;i++) {
        if (set.contains(line.charAt(i))) {
          if((int) line.charAt(i) > 96) {
            count += (int) line.charAt(i) - 96;
          } else {
            count += (int) line.charAt(i) - 38;
          }
          break;
        }
      }
    }
    log.info("Ans 1 - {}", count);

  }

  @Test
  public void testPuzzle2() throws Exception {

    int count = 0;
    List<String> lines = readFile();

    for(int x=0;x<lines.size();x+=3) {
      Set<Character> intersection = new HashSet<>();
      for(int i=0;i<3;i++) {
        Set<Character> set = new HashSet<>();
        for(int j = 0;j < lines.get(x+i).length();j++) {
          set.add(lines.get(x+i).charAt(j));
        }
        if(i>0) {
          intersection.retainAll(set);
        } else {
          intersection.addAll(set);
        }
      }
      intersection.stream().findFirst().get();
      if((int) intersection.stream().findFirst().get() > 96) {
        count += intersection.stream().findFirst().get() - 96;
      } else {
        count += intersection.stream().findFirst().get() - 38;
      }

    }
    log.info("Ans 2 - {}", count);

  }

  public Character intersections(String ...args) {

    Set<Character> intersection =
        args[0].chars().mapToObj(c -> (char)c).collect(Collectors.toSet());

    for(int i=1;i< args.length;i++) {
      intersection.retainAll(args[i].chars().mapToObj(c -> (char)c).collect(Collectors.toSet()));
    }
    return intersection.stream().findFirst().get();

  }

  @Test
  public void testPuzzle1Clean() throws Exception {

    int count = 0;

    List<String> lines = readFile();
    for (String line : lines) {

      Character intersection = intersections(line.substring(0, line.length()/2), line.substring(line.length()/2));
      if((int) intersection > 96) {
        count += intersection - 96;
      } else {
        count += intersection - 38;
      }

    }
    log.info("Ans 1 - {}", count);

  }

  @Test
  public void testPuzzle2Clean() throws Exception {

    int count = 0;

    List<String> lines = readFile();

    for(int x=0;x<lines.size();x+=3) {
      Character intersection = intersections(lines.get(x), lines.get(x+1), lines.get(x+2));
      if((int) intersection > 96) {
        count += intersection - 96;
      } else {
        count += intersection - 38;
      }

    }
    log.info("Ans 1 - {}", count);

  }

}
