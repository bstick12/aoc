package aoc2021;

import static utils.Utils.getSafe;
import static utils.Utils.initGrid;
import static utils.Utils.setSafe;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day15 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(15);
    } else {
      return Files.readAllLines(Path.of(Day15.class.getResource("day15-test.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(true);

    int maxX = input.get(0).length();
    int maxY = input.size();

    Integer[][] grid = new Integer[maxY][maxX];

    int y=0;
    for (String s : input) {
      int x=0;
      for (char c : s.toCharArray()) {
        grid[y][x] = Integer.valueOf(String.valueOf(c));
        x++;
      }
      y++;
    }


    Node[][] nodes = new Node[maxY][maxX];
    Graph graph = new Graph();
    for(int x=0;x<maxX;x++) {
      for(y=0;y<maxY;y++) {
        Node node = new Node(String.format("%s,%s", x, y));
        setSafe(x,y,nodes,node);
      }
    }

    for(int x=0;x<maxX;x++) {
      for(y=0;y<maxY;y++) {
        Node safe = getSafe(x, y, nodes, null);
        safe.addDestination(getSafe(x,y+1, nodes, null), getSafe(x,y,grid,Integer.MAX_VALUE));
        safe.addDestination(getSafe(x,y-1, nodes, null), getSafe(x,y,grid,Integer.MAX_VALUE));
        safe.addDestination(getSafe(x+1,y, nodes, null), getSafe(x,y,grid,Integer.MAX_VALUE));
        safe.addDestination(getSafe(x-1 ,y, nodes, null), getSafe(x,y,grid,Integer.MAX_VALUE));
        graph.addNode(safe);
      }
    }

    graph = calculateShortestPathFromSource(graph, nodes[maxX-1][maxY-1]);

    log.info("Ans 1 - {}", nodes[0][0].getDistance());
    log.info("Ans 1 - {}", nodes[maxX-1][maxY-1].getDistance());

  }


  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(false);

    long answer = 0;

    int maxX = input.get(0).length();
    int maxY = input.size();

    Integer[][] grid = new Integer[maxY][maxX];

    int y=0;
    for (String s : input) {
      int x=0;
      for (char c : s.toCharArray()) {
        grid[y][x] = Integer.valueOf(String.valueOf(c));
        x++;
      }
      y++;
    }

    Integer[][] bigGrid = new Integer[maxY*5][maxX*5];
    initGrid(bigGrid,0);

    for(y=0;y<maxX;y++) {
      for(int x=0;x<maxX;x++) {
        int init = grid[x][y];
        bigGrid[y][x] = init;
        for(int i=1;i<5;i++) {
          bigGrid[y][x+(i*maxX)] = getVal(init + i);
          bigGrid[y+(i*maxY)][x] = getVal(init + i);
          for(int j=1;j<5;j++) {
            bigGrid[y+(j*maxX)][x+(i*maxY)] = getVal(init + i + j);
          }
        }
      }
    }


    Queue<Costing> queue = new PriorityQueue<Costing>(Comparator.comparing(Costing::getCost));


    Costing costing = new Costing(0, 0, 0);
    queue.add(costing);
    globalCostings = new HashSet<>();
    globalCostings.add(costing);
    routes = new TreeSet<>(Comparator.comparing(Costing::getCost));
    Map<Map.Entry<Integer, Integer>, Integer> costs = new HashMap<>();
    while(true) {
      Costing cost = queue.remove();
      globalCostings.remove(cost);
      if(cost.x == bigGrid.length - 1 && cost.y == bigGrid[0].length - 1) {
        log.info("Ans 2 - {}", cost.cost);
        break;
      }
      next(cost.x+1, cost.y, cost.cost, bigGrid, costs, queue);
      next(cost.x-1, cost.y, cost.cost, bigGrid, costs, queue);
      next(cost.x, cost.y+1, cost.cost, bigGrid, costs, queue);
      next(cost.x, cost.y-1, cost.cost, bigGrid, costs, queue);

    }

  }

  @Test
  public void testPuzzle3() throws Exception {

    List<String> input = readFile(false);

    long answer = 0;

    int maxX = input.get(0).length();
    int maxY = input.size();

    Integer[][] grid = new Integer[maxY][maxX];

    int y=0;
    for (String s : input) {
      int x=0;
      for (char c : s.toCharArray()) {
        grid[y][x] = Integer.valueOf(String.valueOf(c));
        x++;
      }
      y++;
    }

    Integer[][] bigGrid = new Integer[maxY*5][maxX*5];
    initGrid(bigGrid,0);

    for(y=0;y<maxX;y++) {
      for(int x=0;x<maxX;x++) {
        int init = grid[x][y];
        bigGrid[y][x] = init;
        for(int i=1;i<5;i++) {
          bigGrid[y][x+(i*maxX)] = getVal(init + i);
          bigGrid[y+(i*maxY)][x] = getVal(init + i);
          for(int j=1;j<5;j++) {
            bigGrid[y+(j*maxX)][x+(i*maxY)] = getVal(init + i + j);
          }
        }
      }
    }

    Costing costing = new Costing(0, 0, 0);
    TreeSet<Costing> routes = new TreeSet<>(new Comparator<>() {
      @Override
      public int compare(Costing o1, Costing o2) {
        int compare = Integer.compare(o1.cost, o2.cost);
        if (compare == 0) {
          compare = Integer.compare(o2.x, o1.x);
          if (compare == 0) {
            compare = Integer.compare(o2.y, o1.y);
          }
        }
        return compare;
      }
    });
    routes.add(costing);
    routes.add(new Costing(0,1,1));
    Map<Map.Entry<Integer, Integer>, Integer> costs = new HashMap<>();
    while(!routes.isEmpty()) {
      Costing cost = routes.pollFirst();
      if(cost.x == bigGrid.length - 1 && cost.y == bigGrid[0].length - 1) {
        log.info("Ans 2 - {}", cost.cost);
        break;
      }
      nexts(cost.x+1, cost.y, cost.cost, bigGrid, costs, routes);
      nexts(cost.x-1, cost.y, cost.cost, bigGrid, costs, routes);
      nexts(cost.x, cost.y+1, cost.cost, bigGrid, costs, routes);
      nexts(cost.x, cost.y-1, cost.cost, bigGrid, costs, routes);

    }

  }

  private void nexts(int x, int y, int cost, Integer[][] grid, Map<Map.Entry<Integer, Integer>, Integer> costs, TreeSet<Costing> routes) {
    Integer safe = getSafe(x, y, grid, 10);
    if(safe < 10) {
      int newCost = cost + safe;
      AbstractMap.SimpleEntry key = new AbstractMap.SimpleEntry(x, y);
      if (costs.containsKey(key)) {
        if (costs.get(key) < newCost) {
          return;
        }
      }
      costs.put(key, newCost);
      Costing costing = new Costing(newCost, x, y);
      routes.add(costing);
    }
  }


  private static Set<Costing> globalCostings;

  private static Set<Costing> routes;

  private void next(int x, int y, int cost, Integer[][] grid, Map<Map.Entry<Integer, Integer>, Integer> costs, Queue<Costing> queue) {
    Integer safe = getSafe(x, y, grid, 10);
    if(safe < 10) {
      int newCost = cost + safe;
      AbstractMap.SimpleEntry key = new AbstractMap.SimpleEntry(x, y);
      if (costs.containsKey(key)) {
        if (costs.get(key) < newCost) {
          return;
        }
      }
      costs.put(key, newCost);
      Costing costing = new Costing(newCost, x, y);
      routes.add(costing);
      if(!globalCostings.contains(costing)) {
        queue.add(costing);
        globalCostings.add(costing);
      }
    }
  }


  @Data
  @AllArgsConstructor
  public static class Costing {
    int cost;
    int x;
    int y;
  }

  public int getVal(int x) {
    if (x > 9) {
      return x - 9;
    } else {
      return x;
    }
  }

  public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
    source.setDistance(0);

    Set<Node> settledNodes = new HashSet<>();
    Set<Node> unsettledNodes = new HashSet<>();

    unsettledNodes.add(source);

    while (unsettledNodes.size() != 0) {
      Node currentNode = getLowestDistanceNode(unsettledNodes);
      unsettledNodes.remove(currentNode);
      for (Map.Entry< Node, Integer> adjacencyPair:
          currentNode.getAdjacentNodes().entrySet()) {
        Node adjacentNode = adjacencyPair.getKey();
        Integer edgeWeight = adjacencyPair.getValue();
        if (!settledNodes.contains(adjacentNode)) {
          calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
          unsettledNodes.add(adjacentNode);
        }
      }
      settledNodes.add(currentNode);
    }
    return graph;
  }

  public static class Graph {

    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
      nodes.add(nodeA);
    }

    public void addNodes(Collection<Node> nodes) {
      this.nodes.addAll(nodes);
    }

  }

  public class Node {

    private String name;

    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
      if(destination != null)
        adjacentNodes.put(destination, distance);
    }

    public Node(String name) {
      this.name = name;
    }

    public Integer getDistance() {
      return distance;

    }

    public void setDistance(Integer distance) {
      this.distance = distance;
    }

    public List<Node> getShortestPath() {
      return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
      this.shortestPath = shortestPath;
    }

    public Map<Node, Integer> getAdjacentNodes() {
      return adjacentNodes;
    }
  }

  private static Node getLowestDistanceNode(Set <Node> unsettledNodes) {
    Node lowestDistanceNode = null;
    int lowestDistance = Integer.MAX_VALUE;
    for (Node node: unsettledNodes) {
      int nodeDistance = node.getDistance();
      if (nodeDistance < lowestDistance) {
        lowestDistance = nodeDistance;
        lowestDistanceNode = node;
      }
    }
    return lowestDistanceNode;

  }

  private static void calculateMinimumDistance(Node evaluationNode,
                                               Integer edgeWeigh, Node sourceNode) {
    Integer sourceDistance = sourceNode.getDistance();
    if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
      evaluationNode.setDistance(sourceDistance + edgeWeigh);
      LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
      shortestPath.add(sourceNode);
      evaluationNode.setShortestPath(shortestPath);
    }
  }

}
