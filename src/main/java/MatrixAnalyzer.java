import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MatrixAnalyzer {
    public static String run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the string that will form the matrix:");
        String matrixString;
        while (true) {
            matrixString = scanner.next().toUpperCase();
            if (Math.sqrt(matrixString.length()) % 1 == 0) {
                MatrixData.n = (int) Math.sqrt(matrixString.length());
                break;
            }
            System.out.println("Sorry, the string of length " + matrixString.length()
                    + " can not be transformed to matrix! Try again:");
        }
        MatrixData.setMatrix(matrixString);
        System.out.println("Please enter the word you want to search for:");
        MatrixData.word = scanner.next().toUpperCase();
        return findResultString();
    }

    private static String findResultString() {
        for (int currentIndex = 0; currentIndex < MatrixData.matrix.size(); currentIndex++) {
            if (MatrixData.matrix.get(currentIndex) == MatrixData.word.charAt(0)) {
                List<Integer> initialIndex = new ArrayList<>();
                initialIndex.add(currentIndex);
                List<Integer> result = getIndices(initialIndex, currentIndex, 1);
                if (!result.isEmpty()) {
                    return buildResult(result);
                }
            }
        }
        return "The matrix does not contain this word!";
    }

    private static List<Integer> getIndices(
            List<Integer> indices, int currentIndex, int searchPoint) {
        if (indices.size() == MatrixData.word.length()) {
            return indices;
        }
        int n = MatrixData.n;
        if (currentIndex >= n
                && isValid(indices,currentIndex - n, searchPoint)) {
            indices.add(currentIndex - n);
            return getIndices(indices, currentIndex - n, searchPoint + 1);
        }
        if (currentIndex < (MatrixData.matrix.size() - n)
                && isValid(indices, currentIndex + n, searchPoint)) {
            indices.add(currentIndex + n);
            return getIndices(indices, currentIndex + n, searchPoint + 1);
        }
        if (currentIndex % n > 0
                && isValid(indices, currentIndex - 1, searchPoint)) {
            indices.add(currentIndex - 1);
            return getIndices(indices, currentIndex - 1, searchPoint + 1);
        }
        if (currentIndex % n < (n - 1)
                && isValid(indices, currentIndex + 1, searchPoint)) {
            indices.add(currentIndex + 1);
            return getIndices(indices, currentIndex + 1, searchPoint + 1);
        }
        return Collections.emptyList();
    }

    private static boolean isValid(List<Integer> indices, int indexToCheck, int searchPoint) {
        return MatrixData.word.charAt(searchPoint) == MatrixData.matrix.get(indexToCheck)
                && !indices.contains(indexToCheck);
    }

    private static String buildResult(List<Integer> indices) {
        int n = MatrixData.n;
        return indices.stream()
                .map(index -> "[" + (index / n) + "," + (index % n) + "]")
                .collect(Collectors.joining("->"));
    }

    private static class MatrixData {
        static int n;
        static List<Character> matrix;
        static String word;

        static void setMatrix(String matrixString) {
            matrix = matrixString.chars()
                    .mapToObj(character -> (char) character)
                    .collect(Collectors.toList());
        }
    }
}
