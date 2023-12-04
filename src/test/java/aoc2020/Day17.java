package aoc2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day17 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if(!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day17.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {
    List<String> input = readInput(17, true);
    Iterator<String> iterator = input.iterator();


    long answer=0;

    int length = input.get(0).length();

    int cycles=6;
    int x = length + (cycles * 2);

    char[][][] zone = new char[x][x][x];

    for(int i=0;i<zone[0].length;i++) {
      for(int j=0;j<zone[i][0].length;j++) {
        for(int k=0;k<zone[i][j].length;k++) {
          zone[i][j][k] = '.';
        }
      }
    }

    for(int j=cycles;j<cycles + length;j++) {
      for(int k=cycles;k<cycles + length;k++) {
        zone[x/2][j][k] = input.get(j-cycles).toCharArray()[k-cycles];
      }
    }

    for(int boot=0;boot<cycles;boot++) {
      char[][][] nzone = new char[x][x][x];

      for(int i=0;i<zone[0].length;i++) {
        for(int j=0;j<zone[i][0].length;j++) {
          for(int k=0;k<zone[i][j].length;k++) {
            nzone[i][j][k] = newstate(zone,i,j,k);
          }
        }
      }
      zone=nzone;
    }

    int count=0;
    for(int i=0;i<zone[0].length;i++) {
      for(int j=0;j<zone[i][0].length;j++) {
        for(int k=0;k<zone[i][j].length;k++) {
          if(zone[i][j][k] == '#') {
            count++;
          }
        }
      }
    }

    log.info("Ans P1 {}", count);

  }

  private char newstate(char[][][] zone, int i, int j, int k) {
    List<Character> neighbours = new ArrayList<Character>();

    for(int a=-1;a<=1;a++) {
      for(int b=-1;b<=1;b++) {
        for(int c=-1;c<=1;c++) {
          if(!(a==0 && b==0 && c==0)) {
            checkNeighbour(neighbours, zone, i+a, j+b, k+c);
          }
        }
      }
    }

    long count = neighbours.stream().filter(x -> x == '#').count();
    if(zone[i][j][k] == '#') {
      if(count == 2 || count ==3) {
        return '#';
      }
    } else {
      if(count == 3) {
        return '#';
      }
    }
    return '.';
  }

  private void checkNeighbour(List<Character> neighbours, char[][][] zone, int i, int j, int k) {
    try {
      neighbours.add(zone[i][j][k]);
    } catch (Exception e) {
    }
  }

  private void checkNeighbour2(List<Character> neighbours, char[][][][] zone, int i, int j, int k,int l) {
    try {
      neighbours.add(zone[i][j][k][l]);
    } catch (Exception e) {
    }
  }


  private char newstate2(char[][][][] zone, int i, int j, int k, int l) {
    List<Character> neighbours = new ArrayList<Character>();

    for(int a=-1;a<=1;a++) {
      for(int b=-1;b<=1;b++) {
        for(int c=-1;c<=1;c++) {
          for(int d=-1;d<=1;d++) {
            if(!(a==0 && b==0 && c==0 && d==0)) {
              checkNeighbour2(neighbours, zone, i+a, j+b, k+c, l+d);
            }
          }
        }
      }
    }

    long count = neighbours.stream().filter(x -> x == '#').count();
    if(zone[i][j][k][l] == '#') {
      if(count == 2 || count ==3) {
        return '#';
      }
    } else {
      if(count == 3) {
        return '#';
      }
    }
    return '.';
  }

  @Test
  public void testPuzzle2() {
    List<String> input = readInput(17, false);
    Iterator<String> iterator = input.iterator();

    int length = input.get(0).length();

    int cycles=10;
    int x = length + (cycles * 2);
    char[][][][] zone = new char[x][x][x][x];

    for(int i=0;i<zone[0].length;i++) {
      for(int j=0;j<zone[i][0].length;j++) {
        for(int k=0;k<zone[i][j][0].length;k++) {
          for(int l=0;l<zone[i][j][k].length;l++) {
              zone[i][j][k][l] = '.';
          }
        }
      }
    }

    for(int j=cycles;j<cycles + length;j++) {
      for(int k=cycles;k<cycles + length;k++) {
        zone[x/2][x/2][j][k] = input.get(j-cycles).toCharArray()[k-cycles];
      }
    }

    for(int boot=0;boot<cycles;boot++) {
      char[][][][] nzone = new char[x][x][x][x];

      for(int i=0;i<zone[0].length;i++) {
        for(int j=0;j<zone[i][0].length;j++) {
          for(int k=0;k<zone[i][j][0].length;k++) {
            for(int l=0;l<zone[i][j][k].length;l++) {
              nzone[i][j][k][l] = newstate2(zone, i, j, k, l);
            }
          }
        }
      }
      zone=nzone;
    }

    int count=0;
    for(int i=0;i<zone[0].length;i++) {
      for(int j=0;j<zone[i][0].length;j++) {
        for(int k=0;k<zone[i][j][0].length;k++) {
          for(int l=0;l<zone[i][j][k].length;l++) {
            if (zone[i][j][k][l] == '#') {
              count++;
            }
          }
        }
      }
    }

    log.info("Ans P1 {}", count);

  }

}

