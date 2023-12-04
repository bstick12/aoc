package aoc2022;

import com.google.common.base.Splitter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day18 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day18.class.getResource("day18.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }


  @Data
  @AllArgsConstructor
  private static class Vertex {
    int x,y,z;
  }

  @Test
  public void day18_part1() {

    int count=0;

    List<String> lines = readFile();

    Set<Vertex> vertices = new HashSet<>();

    int maxX = 0;
    int maxY = 0;
    int maxZ = 0;
    for (String line : lines) {
      String[] split = line.split(",");
      Vertex vertex = new Vertex(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]));
      vertices.add(vertex);
      maxX = Math.max(maxX, vertex.getX());
      maxY = Math.max(maxY, vertex.getY());
      maxZ = Math.max(maxZ, vertex.getZ());
    }

    log.info("{} {} {}", maxX, maxY, maxZ);

    int grid[][][] = new int[maxX+3][maxY+3][maxZ+3];

    for (Vertex vertex : vertices) {
      grid[vertex.x+1][vertex.y+1][vertex.z+1] = 1;
    }

    for(int x = 1;x <= maxX + 1; x++) {
      for (int y = 1; y <= maxY + 1; y++) {
        for (int z = 1; z <= maxZ + 1; z++) {
          if (grid[x][y][z] == 1) {
            count += grid[x + 1][y][z] == 0 ? 1 : 0;
            count += grid[x - 1][y][z] == 0 ? 1 : 0;
            count += grid[x][y + 1][z] == 0 ? 1 : 0;
            count += grid[x][y - 1][z] == 0 ? 1 : 0;
            count += grid[x][y][z + 1] == 0 ? 1 : 0;
            count += grid[x][y][z - 1] == 0 ? 1 : 0;
          }
        }
      }
    }

    log.info("Answer {}", count);

    for(int y=0;y<maxY+3;y++) {
      for (int z = 0; z < maxZ+3;z++) {
        grid[0][y][z]=-1;
        grid[maxX+2][y][z]=-1;
      }
    }

    for(int x=0;x<maxX+3;x++) {
      for (int z = 0; z < maxZ+3;z++) {
        grid[x][0][z]=-1;
        grid[x][maxY+2][z]=-1;
      }
    }

    for(int x=0;x<maxX+3;x++) {
      for (int y = 0; y < maxY+3;y++) {
        grid[x][y][0]=-1;
        grid[x][y][maxZ+2]=-1;
      }
    }

    for(int x=1;x<=maxX+1;x++) {
      for(int y=1;y<=maxY+1;y++) {
        for(int z=1;z<=maxZ+1;z++) {
          if (grid[x][y][z] == 0) {
            if(grid[x + 1][y][z] == -1 ||
                grid[x - 1][y][z] == -1 ||
                grid[x][y + 1][z] == -1 ||
                grid[x][y - 1][z] == -1 ||
                grid[x][y][z + 1] == -1 ||
                grid[x][y][z - 1] == -1) {
              grid[x][y][z] = -1;
            }
          }
        }
      }
    }

    for(int x=maxX+1;x>=1;x--) {
      for(int y=maxY+1;y>=1;y--) {
        for(int z=maxZ+1;z>=1;z--) {
          if (grid[x][y][z] == 0) {
            if(grid[x + 1][y][z] == -1 ||
                grid[x - 1][y][z] == -1 ||
                grid[x][y + 1][z] == -1 ||
                grid[x][y - 1][z] == -1 ||
                grid[x][y][z + 1] == -1 ||
                grid[x][y][z - 1] == -1) {
              grid[x][y][z] = -1;
            }
          }
        }
      }
    }

    for(int x=1;x<=maxX+1;x++) {
      for(int y=1;y<=maxY+1;y++) {
        for(int z=maxZ+1;z>=1;z--) {
          if (grid[x][y][z] == 0) {
            if(grid[x + 1][y][z] == -1 ||
                grid[x - 1][y][z] == -1 ||
                grid[x][y + 1][z] == -1 ||
                grid[x][y - 1][z] == -1 ||
                grid[x][y][z + 1] == -1 ||
                grid[x][y][z - 1] == -1) {
              grid[x][y][z] = -1;
            }
          }
        }
      }
    }

    for(int x=maxX+1;x>=1;x--) {
      for(int y=1;y<=maxY+1;y++) {
        for(int z=maxZ+1;z>=1;z--) {
          if (grid[x][y][z] == 0) {
            if(grid[x + 1][y][z] == -1 ||
                grid[x - 1][y][z] == -1 ||
                grid[x][y + 1][z] == -1 ||
                grid[x][y - 1][z] == -1 ||
                grid[x][y][z + 1] == -1 ||
                grid[x][y][z - 1] == -1) {
              grid[x][y][z] = -1;
            }
          }
        }
      }
    }

    for(int x = 1;x <= maxX + 1; x++) {
      for (int y = 1; y <= maxY + 1; y++) {
        for (int z = 1; z <= maxZ + 1; z++) {
          if (grid[x][y][z] == 0) {
            count += grid[x + 1][y][z] == 1 ? -1 : 0;
            count += grid[x - 1][y][z] == 1 ? -1 : 0;
            count += grid[x][y + 1][z] == 1 ? -1 : 0;
            count += grid[x][y - 1][z] == 1 ? -1 : 0;
            count += grid[x][y][z + 1] == 1 ? -1 : 0;
            count += grid[x][y][z - 1] == 1 ? -1 : 0;
          }
        }
      }
    }
    log.info("Answer {}", count);
  }



}
