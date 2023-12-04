package aoc2020;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import utils.Utils;

@Slf4j
public class Day21 {

  @SneakyThrows
  public static List<String> readInput(int day, boolean test) {
    if (!test) {
      return Utils.getInputData(day);
    } else {
      return Files.readAllLines(Path.of(Day21.class.getResource("day" + day + ".txt").toURI()));
    }
  }

  @Test
  public void testPuzzle1() {

    List<String> input = readInput(21, true);
    Iterator<String> iterator = input.iterator();

    Map<String, Integer> ingredientCount = new HashMap<>();
    Set<String> allAllergens = new HashSet<>();

    for (String s : input) {
      String[] split = s.split("\\(");
      Set<String> ingredients = Sets.newHashSet(split[0].split(" "));
      List<String> allergens = Arrays.asList(split[1].substring(0, split[1].length() - 1).replace("contains ", "").split(", "));
      for (String ingredient : ingredients) {
        ingredientCount.put(ingredient, ingredientCount.getOrDefault(ingredient, 0) + 1);
      }
      allAllergens.addAll(allergens);
    }

    Map<String, Set<String>> candidates = new HashMap<>();
    for (String allergen : allAllergens) {
      candidates.put(allergen, new HashSet<>(ingredientCount.keySet()));
    }

    for (String s : input) {
      String[] split = s.split("\\(");
      Set<String> ingredients = Sets.newHashSet(split[0].split(" "));
      List<String> allergens = Arrays.asList(split[1].substring(0, split[1].length() - 1).replace("contains ", "").split(", "));
      for (String allergen : allergens) {
        for (String ingredient : ingredientCount.keySet()) {
          if (!ingredients.contains(ingredient)) {
            candidates.get(allergen).remove(ingredient);
          }
        }
      }
    }

    for (Set<String> ingredients : candidates.values()) {
      for (String ingredient : ingredients) {
        ingredientCount.remove(ingredient);
      }
    }

    log.info("Ans P1 {}",ingredientCount.values().stream().reduce(0, Integer::sum));

   final Map<String, String> allergenToIngredient = new HashMap<>();

    while(!candidates.isEmpty()) {
      for (Map.Entry<String, Set<String>> canidateEntry : candidates.entrySet()) {
        if(canidateEntry.getValue().size() == 1) {
          String ingredient = canidateEntry.getValue().iterator().next();
          allergenToIngredient.put(canidateEntry.getKey(), ingredient);
          for (String s : candidates.keySet()) {
            candidates.get(s).remove(ingredient);
          }
          candidates.remove(canidateEntry.getKey());
          break;
        }
      }
    }

    log.info("Ans P2 {}", Joiner.on(",")
        .join(new TreeSet<>(allergenToIngredient.keySet()).stream()
            .map(allergenToIngredient::get).collect(Collectors.toList())));
  }

  @Test
  public void testPuzzle2() {

    List<String> input = readInput(20, false);
    Iterator<String> iterator = input.iterator();

  }

}

