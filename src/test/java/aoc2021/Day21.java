package aoc2021;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day21 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(20);
    } else {
      return Files.readAllLines(Path.of(Day21.class.getResource("day20.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());

  }


  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);

    int p1 = 1;
    int p2 = 6;

    int p1score = 0;
    int p2score = 0;

    int dice = 1;
    while (true) {
      p1 += add(dice);
      p1 = (p1 % 10) != 0 ? (p1 % 10) : 10;
      p1score += p1;
      if (p1score >= 1000) {
        break;
      }
      p2 += add(dice + 3);
      p2 = (p2 % 10) != 0 ? (p2 % 10) : 10;
      p2score += p2;
      if (p2score >= 1000) {
        break;
      }
      dice += 6;
    }

    log.info("{} {} {}", dice, p1score, p2score);

    if(p1score >= 1000) {
      log.info("{}", p2score * (dice + 2));
    } else {
      log.info("{}", p1score * (dice + 5));
    }

  }

  private int add(int dice) {
    int addition=0;
    for(int i=0;i<3;i++) {
      int x = (dice + i % 100) != 0 ? (dice + i % 100) : 100;
      addition += x;
    }
    return addition;
  }


  private Map<Integer, Integer> universes = new HashMap<>();

  long p1universes = 0;
  long p2universes = 0;

  Map<State, long[]> cache = new HashMap<>();

  List<Integer> rolls = List.of(3, 4, 5, 4, 5, 6, 5, 6, 7, 4, 5, 6, 5, 6, 7, 6, 7, 8, 5, 6, 7, 6, 7, 8, 7, 8, 9);

  @Data
  @AllArgsConstructor
  public class State {
    int p1,p2,s1,s2;
    int player;
  }

  @Test
  public void testPuzzle3() throws Exception {

    long[] result = playGame(1,6,0,0,0);
    log.info("{} {} - {}", result[0], result[1], Math.max(result[0], result[1]));


  }

  private long[] playGame(int p1, int p2, int s1, int s2, int player) {
    State state = new State(p1,p2,s1,s2,player);
    if(cache.containsKey(state))
      return cache.get(state);

    long wins[] = {0,0};

    for (int roll : rolls) {
      int[] pos = {p1,p2};
      int[] score = {s1,s2};

      pos[player] = (pos[player] + roll - 1)  % 10 + 1;
      score[player] += pos[player];

      if(score[player] >= 21) {
        wins[player] += 1;
      } else {
        long[] result = playGame(pos[0], pos[1], score[0], score[1], player == 0 ? 1 : 0);
        wins[0] += result[0];
        wins[1] += result[1];
      }

    }

    cache.put(state, wins);
    return wins;

  }


  @Test
  public void testPuzzle2() throws Exception {

    p1universes = 0;
    p2universes = 0;

    universes.put(3,1);
    universes.put(4,3);
    universes.put(5,6);
    universes.put(6,7);
    universes.put(7,6);
    universes.put(8,3);
    universes.put(9,1);

    List<String> input = readFile(false);

    int p1 = 1;
    int p2 = 6;

    int p1score = 0;
    int p2score = 0;

    startGame(p1,p2,p1score,p2score);

    log.info("{} {}", p1universes, p2universes);

  }

  private void startGame(int p1, int p2, int p1score, int p2score) {
    for (Map.Entry<Integer, Integer> a : universes.entrySet()) {
      for (Map.Entry<Integer, Integer> b : universes.entrySet()) {
        playRound(p1,p2,p1score, p2score, a.getKey(), b.getKey(),a.getValue(), b.getValue());
      }
    }
  }

  private void playRound(int p1, int p2, int p1score, int p2score, int p1roll, int p2roll, long p1acc, long p2acc) {

    p1 += p1roll;
    p1 = (p1 % 10) != 0 ? (p1 % 10) : 10;
    p1score += p1;
    if (p1score >= 21) {
      p1universes += p1acc;
      return;
    }
    p2 += p2roll;
    p2 = (p2 % 10) != 0 ? (p2 % 10) : 10;
    p2score += p2;
    if (p2score >= 21) {
      p2universes += p2acc;
      return;
    }

    for (Map.Entry<Integer, Integer> a : universes.entrySet()) {
      for (Map.Entry<Integer, Integer> b : universes.entrySet()) {
        playRound(p1,p2,p1score, p2score, a.getKey(), b.getKey(), p1acc * a.getValue(), p2acc * b.getValue());
      }
    }

  }

}
