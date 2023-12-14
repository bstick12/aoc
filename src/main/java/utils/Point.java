package utils;

public record Point(int x, int y) {

  public static final Point NORTH = new Point(0,-1);
  public static final Point SOUTH = new Point(0,1);
  public static final Point EAST = new Point(1,0);
  public static final Point WEST = new Point(-1,0);

  public Point add(Point point) {
    return new Point(point.x + x, point.y + y);
  }

}