package aoc2023;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import utils.Utils;


@Slf4j
public class Day20 {

  @SneakyThrows
  public static List<String> readFile() {
    return Utils.getInputData(2023, 20);
    //return Files.readAllLines(Path.of(Day15.class.getResource("day20_1.txt").toURI()));
  }

  public static List<Integer> readFileAsInts() {
    return readFile().stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static Map<Map.Entry<String, List<Integer>>, Long> CACHE = new HashMap();


  @Test
  public void testPuzzle1() throws Exception {
    List<String> lines = readFile();

    Map<String, Module> flips = new HashMap<>();

    Map<String, Boolean> states = new HashMap<>();

    for (String line : lines) {
      String[] split = line.split(" -> ");
      List<String> list = Arrays.stream(split[1].split(", ")).toList();
      if(split[0].startsWith("b")) {
        flips.put(split[0], new Module('-', list));
      } else {
        String module = split[0].substring(1);
        flips.put(module, new Module(split[0].toCharArray()[0], list));
        states.put(module, false);
      }
    }

    Map<String, Set<String>> inputMap = new HashMap<>();
    for (Map.Entry<String, Module> flip : flips.entrySet()) {
      if(flip.getValue().type == '&') {
        Set<String> inputs = new HashSet<>();
        flips.entrySet().forEach(e -> {
          if(e.getValue().targets.contains(flip.getKey())) {
            inputs.add(e.getKey());
          }
        });
        inputMap.put(flip.getKey(), inputs);
      }
    }


    log.info("{}", inputMap);

    int high = 0;
    int low = 0;
    for(int i=0;i<1000;i++) {
      Queue<Pulse> pulses = new LinkedList<>();
      pulses.add(new Pulse(false, "button","broadcaster"));
      while(!pulses.isEmpty()) {
        Pulse pulse = pulses.remove();
        if(pulse.target().equals("rx") && !pulse.high()) {
          log.info("{} {}", pulse, i);
        }
        if(pulse.high()) {
          high++;
        } else {
          low++;
        }
        Module module = flips.getOrDefault(pulse.target(), new Module('+', List.of()));
        //log.info("{}", pulse);
        List<Map.Entry<String, Boolean>> stateUpdate = new ArrayList<>();
        for (String target : module.targets) {
          switch (module.type) {
            case '-':
              pulses.add(new Pulse(false, pulse.target(), target));
              break;
            case '%':
              boolean state = states.get(pulse.target());
              if(!pulse.high()) {
                state = !state;
                pulses.add(new Pulse(state, pulse.target(), target));
                stateUpdate.add(Map.entry(pulse.target, state));
              }
              break;
            case '&':
              Set<String> inputs = inputMap.get(pulse.target());
              boolean state2 = true;
              for (String input : inputs) {
                state2 &= states.get(input);
              }
              pulses.add(new Pulse(!state2, pulse.target(), target));
              stateUpdate.add(Map.entry(pulse.target, !state2));
              break;
          }
        }
        stateUpdate.forEach(e -> states.put(e.getKey(), e.getValue()));
      }
    }

    log.info("Answer 1 - {} {} {}", low, high, low * high);

  }

  public record Module(char type, List<String> targets) {}

  public record Pulse(boolean high, String source, String target) {}

  @Test
  public void testPuzzle2() throws Exception {
    List<String> lines = readFile();

    Map<String, Module> flips = new HashMap<>();

    Map<String, Boolean> states = new HashMap<>();

    for (String line : lines) {
      String[] split = line.split(" -> ");
      List<String> list = Arrays.stream(split[1].split(", ")).toList();
      if(split[0].startsWith("b")) {
        flips.put(split[0], new Module('-', list));
      } else {
        String module = split[0].substring(1);
        flips.put(module, new Module(split[0].toCharArray()[0], list));
        states.put(module, false);
      }
    }

    Map<String, Set<String>> inputMap = new HashMap<>();
    for (Map.Entry<String, Module> flip : flips.entrySet()) {
      if(flip.getValue().type == '&') {
        Set<String> inputs = new HashSet<>();
        flips.entrySet().forEach(e -> {
          if(e.getValue().targets.contains(flip.getKey())) {
            inputs.add(e.getKey());
          }
        });
        inputMap.put(flip.getKey(), inputs);
      }
    }

    String endTarget = "rx";
    for (Map.Entry<String, Module> flip : flips.entrySet()) {
      if(flip.getValue().targets().contains(endTarget)) {
        endTarget = flip.getKey();
        break;
      }
    }

    Set<String> sources = new HashSet<>();
    for (Map.Entry<String, Module> flip : flips.entrySet()) {
      if(flip.getValue().targets().contains(endTarget)) {
        sources.add(flip.getKey());
      }
    }

    log.info("{}", sources);

    Map<String, Long> lcms = new HashMap<>();

    for(int i=0;i<10000;i++) {
      Queue<Pulse> pulses = new LinkedList<>();
      pulses.add(new Pulse(false, "button","broadcaster"));
      while(!pulses.isEmpty()) {
        Pulse pulse = pulses.remove();
        if(sources.contains(pulse.source()) && pulse.high()) {
          if(!lcms.containsKey(pulse.source())) {
            // That +1 is important ;(
            lcms.put(pulse.source(), (long) i + 1);
          }
          if(lcms.size() == 4) break;
        }

        Module module = flips.getOrDefault(pulse.target(), new Module('+', List.of()));
        //log.info("{}", pulse);
        List<Map.Entry<String, Boolean>> stateUpdate = new ArrayList<>();
        for (String target : module.targets) {
          switch (module.type) {
            case '-':
              pulses.add(new Pulse(false, pulse.target(), target));
              break;
            case '%':
              boolean state = states.get(pulse.target());
              if(!pulse.high()) {
                state = !state;
                pulses.add(new Pulse(state, pulse.target(), target));
                stateUpdate.add(Map.entry(pulse.target, state));
              }
              break;
            case '&':
              Set<String> inputs = inputMap.get(pulse.target());
              boolean state2 = true;
              for (String input : inputs) {
                state2 &= states.get(input);
              }
              pulses.add(new Pulse(!state2, pulse.target(), target));
              stateUpdate.add(Map.entry(pulse.target, !state2));
              break;
          }
        }
        stateUpdate.forEach(e -> states.put(e.getKey(), e.getValue()));
      }
    }

    log.info("{}", lcms);
    log.info("Answer 2 - {}", Utils.lcm(lcms.values().stream().toList()));

  }
}

