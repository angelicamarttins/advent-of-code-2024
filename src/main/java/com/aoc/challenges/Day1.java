package com.aoc.challenges;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day1 {

  public static void main(String[] args) {
    System.out.println(firstChallengePart());
    System.out.println(secondChallengePart());
  }

  private static int firstChallengePart() {
    int result = 0;

    List<List<Integer>> lists = readFile();
    List<Integer> leftList = lists.getFirst();
    List<Integer> rightList = lists.getLast();

    leftList.sort(Integer::compareTo);
    rightList.sort(Integer::compareTo);

    for (int i = 0; i < leftList.size(); i++) {
      result += Math.abs(leftList.get(i) - rightList.get(i));
    }

    return result;
  }

  private static Integer secondChallengePart() {
    List<List<Integer>> lists = readFile();
    List<Integer> leftList = lists.getFirst();
    List<Integer> rightList = lists.getLast();

    return Arrays.stream(leftList.stream()
            .map(leftId -> leftId * (int) rightList
                .stream()
                .filter(rightId -> rightId.equals(leftId))
                .count()
            )
            .toArray(Integer[]::new))
        .reduce(0, Integer::sum);
  }

  private static List<List<Integer>> readFile() {
    String filePath = "/home/angelica-martins/estudos/advent-of-code-2024/src/main/resources/files/day1.txt";
    List<Integer> leftList = new ArrayList<>();
    List<Integer> rightList = new ArrayList<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      String line = bufferedReader.readLine();

      while (Objects.nonNull(line)) {
        Integer[] row = Arrays.stream(line.split(" {3}"))
            .map(Integer::parseInt)
            .toArray(Integer[]::new);

        leftList.add(row[0]);
        rightList.add(row[1]);

        line = bufferedReader.readLine();
      }
    } catch (IOException exception) {
      System.out.println("Error while reading file. " + exception.getMessage());
    }

    return List.of(leftList, rightList);
  }

}