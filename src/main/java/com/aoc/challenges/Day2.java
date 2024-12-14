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
                      .range(0, reportLevels.length)
                      .map(indexLevel -> {
                        System.out.println("--------------INICIO-------------------");
                        Integer firstLevel = reportLevels[0];
                        Integer level = reportLevels[indexLevel];
                        Integer previousLevel = indexLevel == 0 ? level : reportLevels[indexLevel - 1];
                        Integer nextLevel = indexLevel == reportLevels.length - 1 ? 0 : reportLevels[indexLevel + 1];
                        System.out.println("level: " + level);

                        if (unsafetyLevel.size() < 2) {
                          System.out.println("menos de dois erros");

                          if (indexLevel == 0) {
                            System.out.println("primeiro level");
                            if (differenceBetweenLevels(level, nextLevel) && !level.equals(nextLevel)) {
                              return 1;
                            }

                            unsafetyLevel.add(level);
                          }

                          if (!unsafetyLevel.isEmpty() && firstLevel.equals(unsafetyLevel.getFirst())) {
                            System.out.println("segundo level com erro no primeiro level");
                            if (differenceBetweenLevels(level, nextLevel)) {
                              return 1;
                            }

                            unsafetyLevel.add(level);
                            return 0;
                          }

                          if (unsafetyLevel.contains(previousLevel)) {
                            System.out.println("level anterior com erro");
                            Integer prePreviousLevel = reportLevels[indexLevel - 2];

                            if (indexLevel == reportLevels.length - 1 && differenceBetweenLevels(level, prePreviousLevel) && isIncreasingOrDecreasingPreviousLevel(level, prePreviousLevel)) {
                              System.out.println("último level");
                              return 1;
                            }

                            if (differenceBetweenLevels(level, nextLevel) && isIncreasingOrDecreasingBothLevels(prePreviousLevel, level, nextLevel)) {
                              return 1;
                            }

                            unsafetyLevel.add(level);
                            return 0;
                          }

                          if (differenceBetweenLevels(level, nextLevel) && isIncreasingOrDecreasingBothLevels(previousLevel, level, nextLevel)) {
                            System.out.println("level normal");
                            return 1;
                          }

                          System.out.println("level com erro");
                          unsafetyLevel.add(level);
                          return 0;
                        }

                        System.out.println(unsafetyLevel);
                        return 0;
                      })
                      .sum();
                  System.out.println(safetyReport);
                  if (safetyReport != reportLevels.length - 1) {
                    System.out.println("report com insucesso = " + Arrays.toString(reportLevels));
                  }
                  return safetyReport == reportLevels.length - 1 ? 1 : 0;
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

  private static boolean differenceBetweenLevels(Integer level, Integer nextLevel) {
    return Math.abs(level - nextLevel) <= 3;
  }

  private static boolean isIncreasingOrDecreasingBothLevels(Integer previousLevel, Integer level, Integer nextLevel) {
    boolean isIncreasing = previousLevel < level && nextLevel > level;
    boolean isDecreasing = previousLevel > level && nextLevel < level;

    System.out.println("previousLevel = " + previousLevel);
    System.out.println("level = " + level);
    System.out.println("nextLevel = " + nextLevel);

    return isIncreasing || isDecreasing;
  }

  private static boolean isIncreasingOrDecreasingNextLevel(Integer level, Integer nextLevel) {
    boolean isIncreasing = nextLevel > level;
    boolean isDecreasing = nextLevel < level;

    return isIncreasing || isDecreasing;
  }

  private static boolean isIncreasingOrDecreasingPreviousLevel(Integer level, Integer previousLevel) {
    System.out.println("previousLevel = " + previousLevel);
    System.out.println("level = " + level);

    boolean isIncreasing = previousLevel < level;
    boolean isDecreasing = previousLevel > level;

    return isIncreasing || isDecreasing;
  }

}
