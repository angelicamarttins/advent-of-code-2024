package com.aoc.challenges;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Day2 {

  public static void main(String[] args) {
//    System.out.println(firstChallengePart());
    System.out.println(secondChallengePart());
  }

  private static int secondChallengePart() {


    return readFile()
        .stream()
        .mapToInt(report -> Arrays
            .stream(report)
            .mapToInt(reportLevels -> {
                  System.out.println(Arrays.toString(reportLevels));
                  List<Integer> unsafetyLevel = new ArrayList<>();
                  int safetyReport = IntStream
                      .range(0, reportLevels.length - 1)
                      .map(indexLevel -> {
                        Integer level = reportLevels[indexLevel];
                        Integer previousLevel = indexLevel == 0 ? level : reportLevels[indexLevel - 1];
                        Integer nextLevel = reportLevels[indexLevel + 1];

                        boolean differenceBetweenLevels = Math.abs(level - nextLevel) <= 3;
                        boolean isIncreasing = previousLevel <= level && nextLevel > level;
                        boolean isDecreasing = previousLevel >= level && nextLevel < level;

                        if ((isIncreasing || isDecreasing) && differenceBetweenLevels) {
                          System.out.println("level = " + level);
                          System.out.println("oi");
                          return 1;
                        }

                        if (unsafetyLevel.isEmpty() || unsafetyLevel.size() == 1) {
                          unsafetyLevel.add(level);
                        }

                        System.out.println("level = " + level);
                        System.out.println("unsafety = " + unsafetyLevel);

                        if (unsafetyLevel.size() == 1) {
                          boolean differenceBetweenLevels1 = Math.abs(previousLevel - nextLevel) <= 3;
                          System.out.println(previousLevel + " - " + nextLevel + " = " + differenceBetweenLevels1);
                          if (differenceBetweenLevels1 && !nextLevel.equals(previousLevel)) {
                            System.out.println("oi");
                            return 1;
                          }

                        }
                        System.out.println("tchau");
                        return 0;
                      })
                      .sum();

                  return unsafetyLevel.isEmpty() && safetyReport == reportLevels.length - 1 ? 1 : 0;
                }
            ).sum()
        ).sum();
  }

  private static int firstChallengePart() {
    return readFile()
        .stream()
        .mapToInt(report -> Arrays
            .stream(report)
            .mapToInt(reportLevels -> {
                  int safetyReport = IntStream
                      .range(0, reportLevels.length - 1)
                      .map(indexLevel -> {
                        Integer level = reportLevels[indexLevel];
                        Integer previousLevel = indexLevel == 0 ? level : reportLevels[indexLevel - 1];
                        Integer nextLevel = reportLevels[indexLevel + 1];

                        boolean differenceBetweenLevels = Math.abs(level - nextLevel) <= 3;
                        boolean isIncreasing = previousLevel <= level && nextLevel > level;
                        boolean isDecreasing = previousLevel >= level && nextLevel < level;

                        return (isIncreasing || isDecreasing) && differenceBetweenLevels ? 1 : 0;
                      })
                      .sum();

                  return safetyReport == reportLevels.length - 1 ? 1 : 0;
                }
            ).sum()
        ).sum();
  }

  private static List<Integer[][]> readFile() {
    String filePath = "/home/angelica-martins/estudos/advent-of-code-2024/src/main/resources/files/day2.txt";
    List<Integer[][]> list = new ArrayList<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      String line = bufferedReader.readLine();

      while (Objects.nonNull(line)) {
        Integer[][] row = Arrays
            .stream(line.split(System.lineSeparator()))
            .map(separatedRow -> Arrays
                .stream(separatedRow.split("\\s+"))
                .map(Integer::parseInt)
                .toArray(Integer[]::new))
            .toArray(Integer[][]::new);

        list.add(row);

        line = bufferedReader.readLine();
      }
    } catch (IOException exception) {
      System.out.println("Error while reading file. " + exception.getMessage());
    }

    return list;
  }

}
