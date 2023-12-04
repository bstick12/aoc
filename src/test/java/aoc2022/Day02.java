package aoc2022;

import utils.Utils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day02 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2022,2);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {
    List<String> collect = readFile();


    int score = 0;
    for (String s : collect) {
      String[] s1 = s.split(" ");
      switch (s1[1]) {
        case "X":
          score+=1;
          switch (s1[0]) {
            case "A":
              score+=3;
              break;
            case "B":
              score+=0;
              break;
            case "C":
              score+=6;
              break;
            default:
              break;
          }
          break;
        case "Y":
          score+=2;
          switch (s1[0]) {
            case "A":
              score+=6;
              break;
            case "B":
              score+=3;
              break;
            case "C":
              score+=0;
              break;
            default:
              break;
          }
          break;
        case "Z":
          score+=3;
          switch (s1[0]) {
            case "A":
              score+=0;
              break;
            case "B":
              score+=6;
              break;
            case "C":
              score+=3;
              break;
            default:
              break;
          }
          break;
        default:
          break;

      }
    }
    log.info("Ans 1 - {}", score);
  }


  @Test
  public void testPuzzle2() throws Exception {
    List<String> collect = readFile();
    int score = 0;
    for (String s : collect) {
      String[] s1 = s.split(" ");
      switch (s1[1]) {
        case "X":
          score+=0;
          switch (s1[0]) {
            case "A":
              score+=3;
              break;
            case "B":
              score+=1;
              break;
            case "C":
              score+=2;
              break;
            default:
              break;
          }
          break;
        case "Y":
          score+=3;
          switch (s1[0]) {
            case "A":
              score+=1;
              break;
            case "B":
              score+=2;
              break;
            case "C":
              score+=3;
              break;
            default:
              break;
          }
          break;
        case "Z":
          score+=6;
          switch (s1[0]) {
            case "A":
              score+=2;
              break;
            case "B":
              score+=3;
              break;
            case "C":
              score+=1;
              break;
            default:
              break;
          }
          break;
        default:
          break;

      }
    }
    log.info("Ans 1 - {}", score);
  }

  @Test
  public void testPuzzle1Improved() throws Exception {
    List<String> collect = readFile();

    char x = 'A';
    log.info("{}", (int) x - 64);

    int score = 0;
    for (String s : collect) {
      String[] s1 = s.split(" ");
      switch (s1[1]) {
        case "X":
          score+=1;
          switch (s1[0]) {
            case "A":
              score+=3;
              break;
            case "B":
              score+=0;
              break;
            case "C":
              score+=6;
              break;
            default:
              break;
          }
          break;
        case "Y":
          score+=2;
          switch (s1[0]) {
            case "A":
              score+=6;
              break;
            case "B":
              score+=3;
              break;
            case "C":
              score+=0;
              break;
            default:
              break;
          }
          break;
        case "Z":
          score+=3;
          switch (s1[0]) {
            case "A":
              score+=0;
              break;
            case "B":
              score+=6;
              break;
            case "C":
              score+=3;
              break;
            default:
              break;
          }
          break;
        default:
          break;

      }
    }
    log.info("Ans 1 - {}", score);
  }



}
