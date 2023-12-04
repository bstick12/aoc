package aoc2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day18 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if (!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day18.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {
    List<String> input = readInput(18, true);
    Iterator<String> iterator = input.iterator();
    long answer = 0;
    for (String s : input) {
      answer += evaluate(s);
    }
    log.info("Ans P1 {}", answer);
  }

  @Test
  public void testPuzzle2() {
    List<String> input = readInput(18, true);
    Iterator<String> iterator = input.iterator();
    long answer = 0;
    for (String s : input) {
      String brackets = brackets(s);
      answer += evaluate(brackets);
    }
    log.info("Ans P2 {}", answer);
  }

  private String brackets(String s) {

    char[] x = s.toCharArray();

    for (int i=0; i<x.length; i++) {
      if (x[i] == '+') {
        int open=0;
        for (int j=i+1;j<x.length;j++) {
          if(x[j] != ' ') {
            if(x[j] == '(')
              open++;
            if(x[j] == ')')
              open--;
            if(open == 0) {
              s = addChar(s, ')', j + 1);
              for(int k=i-2;k>=0;k--) {
                if(x[k] == '(')
                  open--;
                if(x[k] == ')')
                  open++;
                if(open == 0) {
                  s = addChar(s, '(', k);
                  break;
                }
              }
              i++;
              x=s.toCharArray();
              break;
            }
          }
        }
      }
    }
    return String.valueOf(x);
  }

  public String addChar(String str, char ch, int position) {
    int len = str.length();
    char[] updatedArr = new char[len + 1];
    str.getChars(0, position, updatedArr, 0);
    updatedArr[position] = ch;
    str.getChars(position, len, updatedArr, position + 1);
    return new String(updatedArr);
  }

  private long evaluate(String s) {

    char operation = '0';

    long value=0;
    char[] x = s.toCharArray();
    for (int i = 0; i < x.length; i++) {
      if (x[i] == '(') {
        int open=1;
        for(int j=i+1;j<x.length;j++) {
          if(x[j] == ')') {
            open--;
          }
          if(x[j] == '(') {
            open++;
          }
          if(open == 0) {
            value = doMath(value, operation,
                evaluate(String.valueOf(Arrays.copyOfRange(x, i+1, j))));
            i=j;
            break;
          }
        }
      } else if (x[i] == '*' || x[i] == '+') {
        operation = x[i];
      } else if (x[i] == ' ') {
        // skip
      } else {
        value = doMath(value, operation, Long.valueOf(String.valueOf(x[i])));
      }
    }

    return value;
  }

  public long doMath(long value, char operation, long newValue) {
    if(value == 0) {
      value = newValue;
    } else {
      if(operation == '*') {
        value = value * newValue;
      } else {
        value = value + newValue;
      }
    }
    return value;
  }

}

