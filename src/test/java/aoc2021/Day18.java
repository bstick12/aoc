package aoc2021;

import utils.Utils;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day18 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(18);
    } else {
      return Files.readAllLines(Path.of(Day18.class.getResource("day18.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }


  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);
    long answer = 0;

    List<ArrayList<Pair>> blah = getLists(input);

    while(blah.size() > 1) {
      List<Pair> addend = blah.remove(1);
      List<Pair> augend = blah.get(0);
      add(augend, addend);
      reduce(augend, 0);
    }

    log.info("Ans 1 = {}", mag(new AtomicInteger(0), 1, blah.get(0)));

  }

  private List<ArrayList<Pair>> getLists(List<String> input) {
    List<ArrayList<Pair>> blah = new ArrayList<>();
    input.stream().forEach(
        line -> {
          ArrayList<Pair> numbers = new ArrayList<>();
          AtomicInteger d = new AtomicInteger();
          line.chars().mapToObj(x -> String.valueOf((char) x))
            .forEach(s -> {
              switch (s) {
                case "[":
                  d.getAndIncrement();
                  break;
                case "]":
                  d.getAndDecrement();
                  break;
                case ",":
                  break;
                default:
                  numbers.add(new Pair(d.get(), Integer.valueOf(s)));
              }
            });
          blah.add(numbers);
        }
    );
    return blah;
  }


  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(false);
    long answer = 0;

    List<ArrayList<Pair>> blah = getLists(input);

    List<Long> mags = new ArrayList<>();

    for (int i = 0; i < blah.size(); i++) {
      for (int j = 0; j < blah.size(); j++) {
        if(i != j) {
          List<Pair> pairs = addNewList(SerializationUtils.clone(blah.get(i)), SerializationUtils.clone(blah.get(j)));
          reduce(pairs,0);
          mags.add(mag(new AtomicInteger(0), 1, pairs));

          pairs = addNewList(SerializationUtils.clone(blah.get(j)), SerializationUtils.clone(blah.get(i)));
          reduce(pairs,0);
          mags.add(mag(new AtomicInteger(0), 1, pairs));
        }
      }
    }

    log.info("{}", mags.stream().max(Long::compareTo).get());

  }

    private long mag(AtomicInteger i, int depth, List<Pair> pairs) {
    long answer = 0;
    if(pairs.get(i.get()).depth == depth) {
      answer += 3 * pairs.get(i.getAndIncrement()).value;
    } else {
      answer += 3 *  mag(i, depth+1,pairs);
    }
    if(pairs.get(i.get()).depth == depth) {
      answer += 2 * pairs.get(i.getAndIncrement()).value;
    } else {
      answer += 2 * mag(i, depth + 1, pairs);
    }
    return answer;
  }

  @AllArgsConstructor
  @ToString
  public static class Pair implements Serializable {
    int depth;
    int value;
  }

  private void reduce(List<Pair> augend, int i) {
    for (;i < augend.size() - 1;i++) {
      Pair pair = augend.get(i);
      if (pair.depth == 5) {
        int l = pair.value;
        int r =  augend.get(i + 1).value;
        augend.set(i, new Pair(4,0));
        augend.remove(i+1);
        try {
          augend.get(i - 1).value += l;
        } catch (IndexOutOfBoundsException e) {
        }
        try {
          augend.get(i + 1).value += r;
        } catch (IndexOutOfBoundsException e) {
        }
        reduce(augend, i);
      }
    }
    for (int i1 = 0; i1 < augend.size(); i1++) {
      Pair pair = augend.get(i1);
      if (pair.value >= 10) {
        augend.set(i1, new Pair(pair.depth + 1, pair.value / 2));
        augend.add(i1 + 1, new Pair(pair.depth + 1, (pair.value + 1)/2));
        reduce(augend, i1);
      }
    }

  }

  private void add(List<Pair> augend, List<Pair> addend) {
    augend.addAll(addend);
    augend.forEach(x -> x.depth += 1 );
  }

  private List<Pair> addNewList(List<Pair> augend, List<Pair> addend) {
    ArrayList<Pair> pairs = new ArrayList<>(augend);
    pairs.addAll(addend);
    pairs.forEach(x -> x.depth += 1 );
    return pairs;
  }

}
