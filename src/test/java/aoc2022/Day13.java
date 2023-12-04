package aoc2022;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day13 {

  @SneakyThrows
  public static List<String> readFile() {
    return Files.readAllLines(Path.of(Day13.class.getResource("day13-test.txt").toURI()));
 //   return Utils.getInputData(2022,9);
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void day13_part1() throws IOException {

    int ans = 0;

    List<String> lines = readFile();

    for(int i=0;i<lines.size();i+=3) {
      List<Object> left = readLine(lines.get(i));
      List<Object> right = readLine(lines.get(i+1));
      ans += comparePacketLists(left, right,0) ? (i / 3) + 1 : 0;
    }

    log.info("Part 1 - {}", ans);

  }

  private boolean comparePacketLists(List<Object> left, List<Object> right, int index) {
    while(true) {

      Object leftS = getSafeLeft(left, index);
      Object rightS = getSafeLeft(right, index);


    }
  }

  private Object getSafeLeft(List<Object> left, int index) {
    if(left.size() < index) {
      return List.of(Integer.MAX_VALUE);
    } else {
      return left.get(index);
    }
  }

  private Object getSafeRight(List<Object> right, int index) {
    if(right.size() < index) {
      return List.of(Integer.MIN_VALUE);
    } else {
      return right.get(index);
    }
  }

  private boolean isInteger(Object object) {
    return object instanceof Integer;
  }


  public List<Object> readLine(String line) throws IOException {
    return OBJECT_MAPPER.readValue(line.getBytes(StandardCharsets.UTF_8), new TypeReference<List<Object>>(){});
  }

}
