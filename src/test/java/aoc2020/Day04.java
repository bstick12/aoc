package aoc2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class Day04 {

  @Test
  public void testPuzzle1() throws URISyntaxException, IOException {
    List<String> input = Files.readAllLines(Path.of(Day2.class.getResource("day04.txt").toURI()));



    Iterator<String> iterator = input.iterator();
    HashMap<String,String> passportData = new HashMap<>();
    int valid=0;
    while(iterator.hasNext()) {

      String next = iterator.next();

      if(next.equals("")) {
        if(passportData.values().size() == 8 || (passportData.values().size() == 7 && !passportData.containsKey("cid"))) {
          valid++;
        }
        passportData.clear();
      } else {
        String[] values = next.split(" ");
        for (String value : values) {
          String[] elems = value.split(":");
          passportData.put(elems[0], elems[1]);
        }
      }

    }
    log.info("Valid = {}", valid);
  }


  @Test
  public void testPuzzle2() throws URISyntaxException, IOException {
    List<String> input = Files.readAllLines(Path.of(Day2.class.getResource("day04.txt").toURI()));

    Set<String> eye = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

    Pattern p = Pattern.compile("#[a-z0-9]{6}");

    Iterator<String> iterator = input.iterator();
    HashMap<String,String> passportData = new HashMap<>();
    int valid=0;
    while(iterator.hasNext()) {

      String next = iterator.next();

      if(next.equals("")) {
        if(passportData.values().size() == 8 || (passportData.values().size() == 7 && !passportData.containsKey("cid"))) {
          if(
              (Integer.valueOf(passportData.get("byr")) >= 1920 && Integer.valueOf(passportData.get("byr")) <= 2002)
                  && (Integer.valueOf(passportData.get("iyr")) >= 2010 && Integer.valueOf(passportData.get("iyr")) <= 2020)
                  && (Integer.valueOf(passportData.get("eyr")) >= 2020 && Integer.valueOf(passportData.get("eyr")) <= 2030)
                  && eye.contains(passportData.get("ecl"))
              && (passportData.get("pid").length() == 9 && NumberUtils.isDigits(passportData.get("pid")))
              && (p.matcher(passportData.get("hcl")).matches())
          ) {
            String height = passportData.get("hgt");
            if(height.endsWith("cm")) {
              int val = Integer.valueOf(height.substring(0, height.length() - 2));
              if(val >= 150 && val <= 193) {
                valid++;
              }
            }
            if(height.endsWith("in")) {
              int val = Integer.valueOf(height.substring(0, height.length() - 2));
              if(val >= 59 && val <= 76) {
                valid++;
              }
            }
          }
        }
        passportData.clear();
      } else {
        String[] values = next.split(" ");
        for (String value : values) {
          String[] elems = value.split(":");
          passportData.put(elems[0], elems[1]);
        }
      }

    }
    log.info("Valid = {}", valid);
  }

}
