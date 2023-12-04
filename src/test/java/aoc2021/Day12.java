package aoc2021;

import utils.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day12 {

  @SneakyThrows
  public static List<String> readFile(boolean example) {
    if(!example) {
      return Utils.getInputData(12);
    } else {
      return Files.readAllLines(Path.of(Day12.class.getResource("day12.txt").toURI()));
    }
  }

  @SneakyThrows
  public static List<Integer> readFileAsInts(boolean example) {
    return readFile(example).stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  @Data
  public static class Graph {

    private Map<String, Set<String>> vertices = new HashMap<>();

    public Graph() {

    }

    public void addEdge(String a, String b) {
      Set<String> a2b = vertices.getOrDefault(a, new HashSet<>());
      a2b.add(b);
      vertices.put(a,a2b);
      if(!a.equals("start") && !b.equals("end")) {
        Set<String> b2a = vertices.getOrDefault(b, new HashSet<>());
        b2a.add(a);
        vertices.put(b,b2a);
      }

    }

  }

  @Test
  public void testPuzzle1() throws Exception {

    List<String> input = readFile(false);
    long answer = 0;
    Graph g = new Graph();
    for (String s : input) {
      String[] split = s.split("-");
      g.addEdge(split[0], split[1]);
    }

    HashSet<List<String>> allPaths = new HashSet<>();
    Set<List<String>> traverse = traverse(g, allPaths);

    log.info("Ans 1 - {}", traverse.size());

  }

  private Set<List<String>> traverse(Graph g, HashSet<List<String>> allPaths) {

    Set<String> start = g.vertices.get("start");
    List<String> path = new ArrayList<>();
    path.add("start");
    Map<String, Integer> visited = new HashMap<>();
    visited.put("start", 1);
    for (String s : start) {
      traverse(g, s, new ArrayList<>(path), new HashMap<>(visited), allPaths);
    }
    return allPaths;
  }

  private void traverse(Graph g, String vertex, List<String> path, Map<String, Integer> visited, Set<List<String>> allPaths) {
    path.add(vertex);
    int visitCount = visited.getOrDefault(vertex, 0) + 1;
    visited.put(vertex, visitCount);
    if(vertex.equals("end")) {
      allPaths.add(path);
    } else {
      Set<String> current = g.vertices.getOrDefault(vertex, new HashSet<>());
      for (String s : current) {
        boolean small = s.toLowerCase().equals(s);
        if(small) {
          if(visited.getOrDefault(s, 0) < 1) {
            traverse(g, s, new ArrayList<>(path), new HashMap<>(visited), allPaths);
          }
        } else {
          traverse(g, s, new ArrayList<>(path), new HashMap<>(visited), allPaths);
        }
      }
    }
  }

  @Test
  public void testPuzzle2() throws Exception {

    List<String> input = readFile(false);
    long answer = 0;

    Graph g = new Graph();
    for (String s : input) {
      String[] split = s.split("-");
      g.addEdge(split[0], split[1]);
    }

    HashSet<List<String>> allPaths = new HashSet<>();

    for (String s : g.vertices.keySet()) {
      if(s.toLowerCase().equals(s) && !(s.equals("start") || s.equals("end"))) {
        traverseTwice(g, allPaths, s);
      }
    }
    log.info("Ans 2 - {}", allPaths.size());

  }


  private Set<List<String>> traverseTwice(Graph g, HashSet<List<String>> allPaths, String special) {

    Set<String> start = g.vertices.get("start");
    List<String> path = new ArrayList<>();
    path.add("start");
    Map<String, Integer> visited = new HashMap<>();
    visited.put("start", 1);
    visited.put(special, -1);
    for (String s : start) {
      traverse(g, s, new ArrayList<>(path), new HashMap<>(visited), allPaths);
    }
    return allPaths;
  }

}
