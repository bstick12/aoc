package aoc2021;

import utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day4 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(4);
    //return Files.readAllLines(Path.of(Day3.class.getResource("day04.txt").toURI()));
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  class Board {
    List<List<Integer>> rows = new ArrayList<>();
    List<List<Integer>> columns = new ArrayList<>();
  }

  @Test
  public void testPuzzle1() throws Exception {
    List<String> input = readFile();
    List<Integer> bingos = Arrays.stream(input.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());
    List<Integer> scores = scoreBoards(bingos, generateBoards(input));
    log.info("Ans 1 - {}", scores.get(0));
  }

  @Test
  public void testPuzzle2() throws Exception {
    List<String> input = readFile();
    List<Integer> bingos = Arrays.stream(input.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());
    List<Integer> scores = scoreBoards(bingos, generateBoards(input));
    log.info("Ans 2 - {}", scores.get(scores.size()-1));
  }

  private List<Board> generateBoards(List<String> input) {

    List<Board> boards = new ArrayList<>();

    for(int x = 2;x<input.size();x+=6) {
      Board board = new Board();

      for(int y=0;y<5;y++) {
        String row = input.get(x+y);
        String[] split = row.trim().split("\\s+");
        board.rows.add(Arrays.stream(split).map(Integer::valueOf).collect(Collectors.toList()));
      }

      for(int y=0;y<5;y++) {
        List<Integer> col = new ArrayList<>();
        for (List<Integer> row : board.rows) {
          col.add(row.get(y));
        }
        board.columns.add(col);
      }

      boards.add(board);

    }

    return boards;
  }

  public int scoreBoard(int last, List<List<Integer>> uncalledNumbers) {
    int reduce = uncalledNumbers.stream().mapToInt(x -> x.stream().reduce(0, Integer::sum)).reduce(0, Integer::sum);
    return last * reduce;
  }

  public List<Integer> scoreBoards(List<Integer> bingos, List<Board> boards) {

    List<Integer> scores = new ArrayList<>();

    for (Integer bingo : bingos) {
      Iterator<Board> boardIterator = boards.iterator();
      outer:
      while(boardIterator.hasNext()) {
        Board board = boardIterator.next();
        for (List<Integer> row : board.rows) {
          row.remove(bingo);
          if(row.isEmpty()) {
            scores.add(scoreBoard(bingo, board.rows));
            boardIterator.remove();
            continue outer;
          }
        }

        for (List<Integer> col : board.columns) {
          col.remove(bingo);
          if(col.isEmpty()) {
            scores.add(scoreBoard(bingo, board.columns));
            boardIterator.remove();
            continue outer;
          }
        }
      }
    }
    return scores;

  }






}
