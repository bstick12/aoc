package aoc2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day22 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if (!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day22.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {

    List<String> input = readInput(22, true);
    Iterator<String> iterator = input.iterator();
    Queue<Integer> player1 = new LinkedList<>();
    Queue<Integer> player2 = new LinkedList<>();
    boolean isplayer2 = false;
    for (String s : input) {
      if(s.startsWith("Player 1:")) {
        continue;
      }
      if(s.startsWith("Player 2:")) {
        isplayer2 = true;
        continue;
      }
      if(s.isEmpty()) continue;
      if(isplayer2) {
        player2.add(Integer.valueOf(s));
      } else {
        player1.add(Integer.valueOf(s));
      }
    }


    while(!player1.isEmpty() && !player2.isEmpty()) {
      int p1 = player1.poll();
      int p2 = player2.poll();
      if(p1 > p2) {
        player1.offer(p1);
        player1.offer(p2);
      } else {
        player2.offer(p2);
        player2.offer(p1);
      }
    }

    Queue<Integer> winner = player1.isEmpty() ? player2 : player1;
    log.info("Player 1 {}", player1);
    log.info("Player 2 {}", player2);
    log.info("Ans P1 {}", score(winner));

  }

  AtomicLong game = new AtomicLong(0);
  @Test
  public void testPuzzle2() {

    List<String> input = readInput(22, true);
    Iterator<String> iterator = input.iterator();
    LinkedList<Integer> player1 = new LinkedList<>();
    LinkedList<Integer> player2 = new LinkedList<>();
    boolean isplayer2 = false;
    for (String s : input) {
      if(s.startsWith("Player 1:")) {
        continue;
      }
      if(s.startsWith("Player 2:")) {
        isplayer2 = true;
        continue;
      }
      if(s.isEmpty()) continue;
      if(isplayer2) {
        player2.add(Integer.valueOf(s));
      } else {
        player1.add(Integer.valueOf(s));
      }
    }

    long answer = 0;

    log.info("Player 1 {}", player1);
    log.info("Player 2 {}", player2);

    playGame(player1, player2);

    LinkedList<Integer> winner = player1.isEmpty() ? player2 : player1;

    log.info("Player 1 {}", player1);
    log.info("Player 2 {}", player2);
    log.info("Ans P2 {}", score(winner));

  }

  private long score(Queue<Integer> hand) {
    // Slower
    //return IntStream.range(0, hand.size()).mapToLong(i -> (hand.size() - i ) * hand.get(i)).sum();
    int size = hand.size();
    long answer=0;
    for (Integer integer : hand) {
      answer += integer * size;
      size--;
    }
    return answer;
  }

  private boolean playGame(LinkedList<Integer> player1, LinkedList<Integer> player2) {

    Set<Map.Entry<Long, Long>> previousRounds = new HashSet<>();

    while(!player1.isEmpty() && !player2.isEmpty()) {

      Map.Entry<Long,Long> round = new AbstractMap.SimpleEntry<>(score(player1), score(player2));

      if(previousRounds.contains(round)) {
        return true;
      }
      previousRounds.add(round);

      int p1 = player1.poll();
      int p2 = player2.poll();

      boolean p1w;
      if(player1.size() >= p1 && player2.size() >= p2) {
        p1w = playGame(new LinkedList<>(player1.subList(0,p1)), new LinkedList<>(player2.subList(0, p2)));
      } else {
        p1w = p1 > p2;
      }

      if(p1w) {
        player1.offer(p1);
        player1.offer(p2);
      } else {
        player2.offer(p2);
        player2.offer(p1);
      }

    }
    return !player1.isEmpty();
  }

}

