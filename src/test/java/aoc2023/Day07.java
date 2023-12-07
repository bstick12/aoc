package aoc2023;

import static java.util.Map.entry;

import com.google.common.base.Objects;
import com.sun.source.tree.Tree;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day07 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 7);
    //return Files.readAllLines(Path.of(Day07.class.getResource("day07_1.txt").toURI()));

  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    long answer = 0;

    List<String> lines = readFile();

    TreeSet<Hand> hands = new TreeSet<>(new HandCompare(false));

    for (String line : lines) {
      String[] s = line.split(" ");
      Hand hand = new Hand(s[0], Integer.valueOf(s[1]), false);
      hands.add(hand);
    }

    int x = 1;
    for (Hand hand : hands) {
      answer += hand.getBid() * x;
      x++;
    }

    log.info("Answer 1 - {}", answer);

  }

  @Test
  public void testPuzzle2() throws Exception {

    long answer = 0;

    List<String> lines = readFile();

    TreeSet<Hand> hands = new TreeSet<>(new HandCompare(true));

    for (String line : lines) {
      String[] s = line.split(" ");
      Hand hand = new Hand(s[0], Integer.valueOf(s[1]), true);
      hands.add(hand);
    }

    log.info("{}", hands.size());
    int x = 1;
    for (Hand hand : hands) {
      answer += hand.getBid() * x;
      x++;
    }

    log.info("Answer 2 - {}", answer);

  }

  public static class HandCompare implements Comparator<Hand> {

    private static final Map<Character, Integer> DEFAULTS = Map.ofEntries(
        entry('1', 1),
        entry('2', 2),
        entry('3', 3),
        entry('4', 4),
        entry('5', 5),
        entry('6', 6),
        entry('7', 7),
        entry('8', 8),
        entry('9', 9),
        entry('T', 10),
        entry('Q', 12),
        entry('K', 13),
        entry('A', 14)
    );

    private Map<Character, Integer> order = new HashMap<>();

    public HandCompare(boolean jokersWild) {
      order.putAll(DEFAULTS);
      if(jokersWild) {
        order.put('J', 0);
      } else {
        order.put('J', 11);
      }
    }

    public int compare(Hand o1, Hand o2) {
      if (o2.getScore() == o1.getScore()) {
        for (int i=0;i<o2.getHand().length();i++) {
          int i1 = order.get(o1.getHand().toCharArray()[i])
              .compareTo(order.get(o2.getHand().toCharArray()[i]));
          if(i1 != 0) {
            return i1;
          }
        }
        return 0;
      } else {
        return Integer.compare(o1.getScore(), o2.getScore());
      }
    }

  }

  @Data
  @EqualsAndHashCode
  public static class Hand {

    private final String hand;

    private final int bid;
    private final boolean jokersWild;

    private int score;

    public Hand(String hand, int bid, boolean jokersWild) {
      this.hand = hand;
      this.bid = bid;
      this.jokersWild = jokersWild;
      this.score = calculateScore(hand);
    }

    private int calculateScore(String hand) {

      Map<Character, Integer> handCount = new HashMap<>();
      for (char c : hand.toCharArray()) {
        handCount.compute(c, (k,v) -> v == null ? 1 : v + 1);
      }

      if(jokersWild) {
        Integer jokerCount = handCount.get('J');
        if (jokerCount != null && handCount.size() > 1) {
          handCount.remove('J');
          Map.Entry<Character, Integer> wildEntry = null;
          for (Map.Entry<Character, Integer> handCountEntry : handCount.entrySet()) {
            if (wildEntry == null) {
              wildEntry = handCountEntry;
            } else {
              if (handCountEntry.getValue() > wildEntry.getValue()) {
                wildEntry = handCountEntry;
              }
            }
          }
          Integer integer = handCount.get(wildEntry.getKey());
          handCount.put(wildEntry.getKey(), integer + jokerCount);
        }
      }

      return handCount.values().stream().map(v -> (int) Math.pow(v, 2)).mapToInt(i -> i).sum();
    }

  }

}