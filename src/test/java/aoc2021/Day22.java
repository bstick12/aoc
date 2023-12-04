package aoc2021;

import utils.Utils;
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
public class Day22 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(22);
    } else {
      return Files.readAllLines(Path.of(Day22.class.getResource("day22.txt").toURI()));
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
        int x1 = Integer.valueOf(matcher.group(2));
        int x2 = Integer.valueOf(matcher.group(3));
        int y1 = Integer.valueOf(matcher.group(4));
        int y2 = Integer.valueOf(matcher.group(5));
        int z1 = Integer.valueOf(matcher.group(6));
        int z2 = Integer.valueOf(matcher.group(7));
        steps.add(new Step(toggle.equals("on"),
            new Bounds(new Point(x1,y1,z1),
                new Point(x2,y2,z2))));
      }
    }

    Bounds bounds = steps.get(0).getBounds();
    for (Step step : steps) {
      bounds = bounds.union(step.getBounds());
    }

    GridNode gridNode = new GridNode(bounds, false, new ArrayList<>());
    for (Step step : steps) {
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
        (bounds.getMin().getX() < step.getBounds().getMin().getX()
            && bounds.getMax().getX() >= step.getBounds().getMin().getX()) {
          createChildren(step.getBounds().getMin().getX(), "X");
        } else if
        (bounds.getMin().getX() <= step.getBounds().getMax().getX()
                && bounds.getMax().getX() > step.getBounds().getMax().getX()) {
          createChildren(step.getBounds().getMax().getX() + 1, "X");
        } else if
        (bounds.getMin().getY() < step.getBounds().getMin().getY()
                && bounds.getMax().getY() >= step.getBounds().getMin().getY()) {
          createChildren(step.getBounds().getMin().getY(), "Y");
        } else if
        (bounds.getMin().getY() <= step.getBounds().getMax().getY()
                && bounds.getMax().getY() > step.getBounds().getMax().getY()) {
          createChildren(step.getBounds().getMax().getY() + 1, "Y");
        } else if
        (bounds.getMin().getZ() < step.getBounds().getMin().getZ()
                && bounds.getMax().getZ() >= step.getBounds().getMin().getZ()) {
          createChildren(step.getBounds().getMin().getZ(), "Z");
        } else if
        (bounds.getMin().getZ() <= step.getBounds().getMax().getZ()
                && bounds.getMax().getZ() > step.getBounds().getMax().getZ()) {
          createChildren(step.getBounds().getMax().getZ() + 1, "Z");
        } else {
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

    private void createChildren(long x, String x1) {
      Bounds one = SerializationUtils.clone(bounds);
      Bounds two = SerializationUtils.clone(bounds);

      switch (x1) {
        case "X":
          one.getMax().setX(x - 1);
          two.getMin().setX(x);
          break;
        case "Y":
          one.getMax().setY(x - 1);
          two.getMin().setY(x);
          break;
        case "Z":
          one.getMax().setZ(x - 1);
          two.getMin().setZ(x);
          break;
        default:
          throw new RuntimeException("Unexpeced axis");
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

    public Point max(Point max) {
      return new Point(Math.max(x, max.getX()), Math.max(y, max.getY()), Math.max(z, max.getZ()));
    }

    public Point min(Point min) {
      return new Point(Math.min(x, min.getX()), Math.min(y, min.getY()), Math.min(z, min.getZ()));
    }

  }

  @Data
  @AllArgsConstructor
  static class Bounds implements Serializable {
    Point min;
    Point max;

    public Bounds union(Bounds bounds) {
      return new Bounds(min.min(bounds.min), max.max(bounds.max));
    }

    public boolean intersects(Bounds bounds) {
      return true
          && min.getX() <= bounds.max.getX()
          && bounds.min.getX() <= max.getX()
          && min.getY() <= bounds.max.getY()
          && bounds.min.getY() <= max.getY()
          && min.getZ() <= bounds.max.getZ()
          && bounds.min.getZ() <= max.getZ();

    }

    public boolean contains(Bounds bounds) {
      return true
          && min.getX() <= bounds.min.getX()
          && max.getX() >= bounds.max.getX()
          && min.getY() <= bounds.min.getY()
          && max.getY() >= bounds.max.getY()
          && min.getZ() <= bounds.min.getZ()
          && max.getZ() >= bounds.max.getZ();
    }


    public long volume() {
      return 1
          * (max.getX() - min.getX() + 1)
          * (max.getY() - min.getY() + 1)
          * (max.getZ() - min.getZ() + 1);

    }
  }


}
