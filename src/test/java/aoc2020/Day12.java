package aoc2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day12 {

  @SneakyThrows
  public static List<String> readFile() {
    boolean test = false;
    if(!test) {
      return Utils.getInputData(12);
    } else {
      return Files.readAllLines(Path.of(Day12.class.getResource("day12.txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {
    List<String> input = readFile();
    Iterator<String> iterator = input.iterator();

    int x=0,y=0;

    String[] compass = {"E", "S", "W", "N"};
    int direction = 0;
    int rotations=0;
    for (String s : input) {
      String action = s.substring(0, 1);
      Integer arg = Integer.valueOf(s.substring(1,s.length()));
      if(action.equals("F")) {
        action=compass[direction];
      }
      switch (action) {
        case "N":
          x+=arg;
          break;
        case "S":
          x-=arg;
          break;
        case "E":
          y+=arg;
          break;
        case "W":
          y-=arg;
          break;
        case "L":
          rotations = (arg / 90) % 4;
          if(direction - rotations < 0) {
            direction = (4 - rotations) + direction;
          } else {
            direction = (direction - rotations) % 4;
          }
          break;
        case "R":
          rotations = (arg / 90) % 4;
          direction = (direction + rotations) % 4;
          break;
      }

    }

    log.info("Final Position {} {}", x, y);
    log.info("Ans P2 {}", Math.abs(x) + Math.abs(y));

  }

  @Test
  public void testPuzzle2() {
    List<String> input = readFile();
    Iterator<String> iterator = input.iterator();

    int x=0,y=0;
    int xway=10, yway=1;

    int rotations=0;
    for (String s : input) {
      String action = s.substring(0, 1);
      Integer arg = Integer.valueOf(s.substring(1,s.length()));

      switch (action) {
        case "N":
          yway+=arg;
          break;
        case "S":
          yway-=arg;
          break;
        case "E":
          xway+=arg;
          break;
        case "W":
          xway-=arg;
          break;
        case "L":
          rotations = (arg / 90) % 4;
          for(int i=0;i<rotations;i++) {
            int tmp=xway;
            xway=yway*-1;
            yway=tmp;
          }
          break;
        case "R":
          rotations = (arg / 90) % 4;
          for(int i=0;i<rotations;i++) {
            int tmp=yway;
            yway=xway*-1;
            xway=tmp;
          }
          break;
        case "F":
          x+=(xway*arg);
          y+=(yway*arg);
      }

    }

    log.info("Final Position {} {}", x, y);
    log.info("Ans P2 {}", Math.abs(x) + Math.abs(y));

  }

}
