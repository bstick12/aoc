package aoc2022;

import utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day05 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2022,5);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> lines = readFile();

    Map<Integer, Stack<String>> stacks = new HashMap<>();
    for(int l=7;l>=0;l--) {
      String line = lines.get(l);
      for(int x=0;x<9;x++) {
        int pos = 1 + (x*4);
        if(line.length() > pos && line.toCharArray()[pos] != ' ') {
          Stack<String> stack = stacks.getOrDefault(x + 1, new Stack<>());
          stack.push(String.valueOf(line.toCharArray()[pos]));
          stacks.put(x+1, stack);
        }
      }
    }

    Pattern compile = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    for (int i = 10; i < lines.size(); i++) {
      String line = lines.get(i);
      Matcher m = compile.matcher(line);
      if(m.matches()) {
        int move = Integer.valueOf(m.group(1));
        int from = Integer.valueOf(m.group(2));
        int to = Integer.valueOf(m.group(3));
        for(int j=0;j<move;j++) {
          stacks.get(to).push(stacks.get(from).pop());
        }
      }
    }

    StringBuilder sb = new StringBuilder();
    for(int i=1;i<10;i++) {
      sb.append(stacks.get(i).peek());
    }

    log.info("Ans 1 - {}", sb);

  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> lines = readFile();

    List<Integer> stackPosition = List.of(1,5,9,13,17,21,25,29,33);

    Map<Integer, Stack<String>> stacks = new HashMap<>();
    for(int l=7;l>=0;l--) {
      String line = lines.get(l);
      for(int x=0;x<stackPosition.size();x++) {
        int pos = stackPosition.get(x);
        if(line.length() > pos && line.toCharArray()[pos] != ' ') {
          Stack<String> orDefault = stacks.getOrDefault(x + 1, new Stack<>());
          orDefault.push(String.valueOf(line.toCharArray()[pos]));
          stacks.put(x+1, orDefault);
        }
      }
    }

    Pattern compile = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    for (int i = 10; i < lines.size(); i++) {
      String line = lines.get(i);
      Matcher m = compile.matcher(line);
      if(m.matches()) {
        int move = Integer.valueOf(m.group(1));
        int from = Integer.valueOf(m.group(2));
        int to = Integer.valueOf(m.group(3));

        Stack<String> tmp = new Stack<>();
        for(int j=0;j<move;j++) {
          tmp.push(stacks.get(from).pop());
        }
        while(!tmp.isEmpty()) {
          stacks.get(to).push(tmp.pop());
        }

      }
    }

    StringBuilder sb = new StringBuilder();
    for(int i=1;i<10;i++) {
      sb.append(stacks.get(i).peek());
    }

    log.info("Ans 2 - {}", sb);

  }

}
