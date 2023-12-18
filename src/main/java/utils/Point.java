package utils;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public record Point(int x, int y) implements Comparable<Point> {

  public static final Point NORTH = new Point(0,-1); // 1, -1
  public static final Point EAST = new Point(1,0); //   1,  1
  public static final Point SOUTH = new Point(0,1); // -1,  1
  public static final Point WEST = new Point(-1,0); // -1, -1

  public static final List<Point> CARDINALS = List.of(EAST, SOUTH, NORTH, WEST);

  public static final Map<Point, Point> OPPOSITE = Map.of(
      NORTH, SOUTH,
      EAST, WEST,
      WEST, EAST,
      SOUTH, NORTH
  );

  public static final Point NORTHEAST = new Point(1,-1);
  public static final Point NORTHWEST = new Point(-1,-1);
  public static final Point SOUTHEAST = new Point(1,1);
  public static final Point SOUTHWEST = new Point(-1,1);

  public Point add(Point point) {
    return new Point(point.x + x, point.y + y);
  }

  @Override
  public int compareTo(Point o) {
    if(this.x() == o.x()) {
      return Integer.compare(this.y(), o.y());
    } else {
      return Integer.compare(this.x(), o.x());
    }
  }
}