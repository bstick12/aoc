package aoc2021;

import utils.Utils;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day10 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(10);
    } else {
      return Files.readAllLines(Path.of(Day10.class.getResource("day10.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(true);
    long answer = 0;
    List<Long> answers = new ArrayList<>();

    Map<Character, Integer> scoreIllegal = Map.of(')', 3, ']', 57, '}', 1197, '>', 25137);
    Map<Character, Integer> scoreLegal = Map.of('(', 1, '[', 2, '{', 3, '<', 4);
    BiMap<Character, Character> brackets = HashBiMap.create(Map.of('{', '}', '(', ')', '[', ']', '<', '>'));

    for (String s : input) {
      boolean illegal = false;
      Stack<Character> syntax = new Stack<>();
      for (char c : s.toCharArray()) {
        if(brackets.containsKey(c))
          syntax.add(c);
        else
          illegal = syntax.pop() != brackets.inverse().get(c);

        if (illegal) {
          answer += scoreIllegal.get(c);
          break;
        }
      }

      if(!illegal) {
        long answer2 = 0;
        while(!syntax.isEmpty()) {
          answer2 = answer2 * 5;
          answer2 += scoreLegal.get(syntax.pop());
        }
        answers.add(answer2);
      }
    }

    log.info("Ans 1 - {}", answer);

    Collections.sort(answers);
    log.info("Ans 2 - {}", answers.get(answers.size() / 2));


  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(false);
    long answer = 0;
    log.info("Ans 2 - {}", answer);

  }

}
