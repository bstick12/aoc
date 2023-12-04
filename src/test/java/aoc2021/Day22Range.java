package aoc2021;

import utils.Utils;
import com.google.common.collect.Range;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day22Range {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(22);
    } else {
      return Files.readAllLines(Path.of(Day22Range.class.getResource("day22.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());

  }


  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);

    Pattern compile = Pattern.compile("(on|off)\\ x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");

    List<Step> steps = new ArrayList<>();

    for (String s : input) {
      Matcher matcher = compile.matcher(s);
      if (matcher.matches()) {
        String toggle = matcher.group(1);
        long x1 = Long.valueOf(matcher.group(2));
        long x2 = Long.valueOf(matcher.group(3));
        long y1 = Long.valueOf(matcher.group(4));
        long y2 = Long.valueOf(matcher.group(5));
        long z1 = Long.valueOf(matcher.group(6));
        long z2 = Long.valueOf(matcher.group(7));
        steps.add(new Step(toggle.equals("on"),
            new Bounds(Range.closed(x1,x2), Range.closed(y1,y2),Range.closed(z1,z2))));
      }
    }

    Bounds bounds = steps.get(0).getBounds();
    for (Step step : steps) {
      bounds = bounds.union(step.getBounds());
    }

    GridNode gridNode = new GridNode(bounds, false, new ArrayList<>());
    for (Step step : steps) {
      log.info("{}", gridNode.numOn());
      gridNode.runStep(step);
    }

    long answer = gridNode.numOn();

    log.info("{}", answer);

  }


  @Data
  @AllArgsConstructor
  static class GridNode {
    Bounds bounds;
    boolean on;
    List<GridNode> children;

    public void runStep(Step step) {
      if (!bounds.intersects(step.getBounds())) {
        return;
      }
      if (children.isEmpty()) {
        if (isOn() == step.isOn()) {
          return;
        }

        if
        (bounds.x.lowerEndpoint() < step.getBounds().x.lowerEndpoint()
          && bounds.x.upperEndpoint() >= step.getBounds().x.upperEndpoint()) {
          createChildren(step.getBounds().x.lowerEndpoint(), "X");
        } else if
        (bounds.x.lowerEndpoint() <= step.getBounds().x.upperEndpoint()
                && bounds.x.upperEndpoint() > step.getBounds().x.upperEndpoint()) {
          createChildren(step.getBounds().x.upperEndpoint() + 1, "X");
        } else if
        (bounds.y.lowerEndpoint() < step.getBounds().y.lowerEndpoint()
                && bounds.y.upperEndpoint() >= step.getBounds().y.upperEndpoint()) {
          createChildren(step.getBounds().y.lowerEndpoint(), "Y");
        } else if
        (bounds.y.lowerEndpoint() <= step.getBounds().y.upperEndpoint()
                && bounds.y.upperEndpoint() > step.getBounds().y.upperEndpoint()) {
          createChildren(step.getBounds().y.upperEndpoint() + 1, "Y");
        } else if
        (bounds.z.lowerEndpoint() < step.getBounds().z.lowerEndpoint()
                && bounds.z.upperEndpoint() >= step.getBounds().z.upperEndpoint()) {
          createChildren(step.getBounds().z.lowerEndpoint(), "Z");
        } else if
        (bounds.z.lowerEndpoint() <= step.getBounds().z.upperEndpoint()
                && bounds.z.upperEndpoint() > step.getBounds().z.upperEndpoint()) {
          createChildren(step.getBounds().z.upperEndpoint() + 1, "Z");
        }
        else {
          this.on = step.isOn();
        }


      } else if (step.getBounds().contains(bounds)) {
        on = step.isOn();
        children = new ArrayList<>();
      }

      for (GridNode child : children) {
        child.runStep(step);
      }
    
  }

    private void createChildren(long split, String axis) {
      Bounds one = SerializationUtils.clone(bounds);
      Bounds two = SerializationUtils.clone(bounds);

      switch (axis) {
        case "X":
          one.x = Range.closed(one.x.lowerEndpoint(), split-1);
          two.x = Range.closed(split, two.x.upperEndpoint());
          break;
        case "Y":
          one.y = Range.closed(one.y.lowerEndpoint(), split-1);
          two.y = Range.closed(split, two.y.upperEndpoint());
          break;
        case "Z":
          one.z = Range.closed(one.z.lowerEndpoint(), split-1);
          two.z = Range.closed(split, two.z.upperEndpoint());
          break;
        default:
          throw new RuntimeException("Unexpected axis");
      }

      children = List.of(new GridNode(one, isOn(), new ArrayList<>()),  new GridNode(two, isOn(), new ArrayList<>()));

    }

    public long numOn() {
      if(!children.isEmpty()) {
        return children.stream().mapToLong(x -> x.numOn()).sum();
      } else if (isOn()) {
        return bounds.volume();
      } else {
        return 0;
      }
    }
  }

  @Data
  @AllArgsConstructor
  static class Step {
    boolean on;
    Bounds bounds;
  }

  @Data
  @AllArgsConstructor
  static class Point implements Serializable {

    long x,y,z;

  }

  @Data
  @AllArgsConstructor
  static class Bounds implements Serializable {

    Range<Long> x;
    Range<Long> y;
    Range<Long> z;

    public Bounds union(Bounds bounds) {
      return new Bounds(x.span(bounds.x), y.span(bounds.y), z.span(bounds.z));
    }

    public boolean intersects(Bounds bounds) {
      return true
          && x.isConnected(bounds.x)
          && y.isConnected(bounds.y)
          && z.isConnected(bounds.z);
    }

    public boolean contains(Bounds bounds) {
      return true
          && x.encloses(bounds.x)
          && y.encloses(bounds.y)
          && z.encloses(bounds.z);
    }


    public long volume() {
      return 1
          * (x.upperEndpoint() - x.lowerEndpoint() + 1)
          * (y.upperEndpoint() - y.lowerEndpoint() + 1)
          * (z.upperEndpoint() - z.lowerEndpoint() + 1);
    }
  }


}
