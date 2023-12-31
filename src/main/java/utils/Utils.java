package utils;

import com.google.common.base.Joiner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
  public static final String AOC_SESSION = "";

  @SneakyThrows
  public static List<String> getInputData(int day) {
    return getInputData(2021, day);
  }

  @SneakyThrows
  public static List<String> getInputData(int year, int day) {

    URL url = new URL("https://adventofcode.com/" + year + "/day/"+ day +"/input");
    URLConnection urlConnection = url.openConnection();
    urlConnection.setRequestProperty("Cookie", "session=" + AOC_SESSION);
    urlConnection.connect();
    try (InputStream input = urlConnection.getInputStream()) {
      return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.toList());
    }

  }

  public static Character[][] getGrid(List<String> lines) {
    Character[][] grid = new Character[lines.get(0).length()][lines.size()];

    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.toCharArray().length; x++) {
        grid[x][y] = line.toCharArray()[x];
      }
    }
    return grid;
  }

  public static <T> T getSafe(int x, int y, T[][] grid, T defaultValue) {
    try {
      return grid[y][x];
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public static <T> T getSafeXY(int x, int y, T[][] grid, T defaultValue) {
    try {
      return grid[x][y];
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public static <T> T getSafeXY(Point p, T[][] grid, T defaultValue) {
    try {
      return grid[p.x()][p.y()];
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public static void printGrid(Object[][] grid) {
    for(int i=0;i<grid.length;i++) {
      log.info("{}", Joiner.on("").join(grid[i]));
    }
  }

  public static void printGridXY(Object[][] grid) {
    for(int i=0;i < grid[0].length;i++) {
      StringBuilder sb = new StringBuilder();
      for(int j=0;j<grid.length;j++) {
        sb.append(grid[j][i]);
      }
      log.info("{}", sb);
    }
  }

  public static void printGridPretty(Object[][] grid) {
    for(int i=0;i<grid[0].length;i++) {
      StringBuilder sb = new StringBuilder();
      for(int j=0;j<grid.length;j++) {
        sb.append(String.format("%4s", grid[j][i]));
      }
      log.info("{}", sb);
    }
  }

  public static <T> long countGrid(T[][] grid, T count) {

    long answer =0;

    for(int i=0;i<grid.length;i++) {
      for(int j=0;j<grid[0].length;j++) {
        if(grid[i][j].equals(count)) {
          answer++;
        }
      }
    }
    return answer;

  }


  public static <T> void setSafe(int x, int y, T[][] grid, T value) {
    try {
      grid[y][x] = value;
    } catch (Exception e) {
    }
  }

  public static <T> void setSafeXY(int x, int y, T[][] grid, T value) {
    try {
      grid[x][y] = value;
    } catch (Exception e) {
    }
  }

  public static <T> void setSafeXY(Point p, T[][] grid, T value) {
    try {
      grid[p.x()][p.y()] = value;
    } catch (Exception e) {
    }
  }

  public static <T> void initGrid(T[][] grid, T value) {
    for(int i=0;i<grid.length;i++) {
      for(int j=0;j<grid[0].length;j++) {
        grid[i][j] = value;
      }
    }
  }


  public static long gcd(long a, long b)
  {
    while (b > 0)
    {
      long temp = b;
      b = a % b; // % is remainder
      a = temp;
    }
    return a;
  }

  public static long gcd(List<Long> input)
  {
    long result = input.get(0);
    for(int i = 1; i < input.size(); i++) {
      result = gcd(result, input.get(i));
    }
    return result;
  }

  public static long lcm(long a, long b)
  {
    return a * (b / gcd(a, b));
  }

  public static long lcm(List<Long> input)
  {
    long result = input.get(0);
    for(int i = 1; i < input.size(); i++) {
      result = lcm(result, input.get(i));
    }
    return result;
  }


}
