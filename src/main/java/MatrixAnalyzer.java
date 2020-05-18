import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MatrixAnalyzer {
    public String run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the string that will form the matrix:");
        String matrixString;
        while (true) {
            matrixString = scanner.next().toUpperCase();
            if (Math.sqrt(matrixString.length()) % 1 == 0) {
                MatrixData.dimension = (int) Math.sqrt(matrixString.length());
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

    private String findResultString() {
        for (int matrixIndex = 0; matrixIndex < MatrixData.matrix.size(); matrixIndex++) {
            if (MatrixData.matrix.get(matrixIndex) == MatrixData.word.charAt(0)) {
                List<Integer> result = getResultList(new ArrayList<>(), matrixIndex, 1);
                if (!result.isEmpty()) {
                    return buildResult(result);
                }
            }
        }
        return "The matrix does not contain this word!";
    }

    private List<Integer> getResultList(
            List<Integer> oldIndices, int matrixIndex, int searchPoint) {
        List<Integer> newIndices = new ArrayList<>(oldIndices);
        newIndices.add(matrixIndex);
        return checkNeighbourIndices(newIndices, matrixIndex, searchPoint);
    }

    private List<Integer> checkNeighbourIndices(
            List<Integer> indices, int currentIndex, int searchPoint) {
        if (indices.size() == MatrixData.word.length()) {
            return indices;
        }
        int dim = MatrixData.dimension;
        if (currentIndex >= dim
                && isValid(indices,currentIndex - dim, searchPoint)) {
            List<Integer> copyList
                    = getResultList(indices, currentIndex - dim, searchPoint + 1);
            if (!copyList.isEmpty()) {
                return copyList;
            }
        }
        if (currentIndex < (MatrixData.matrix.size() - dim)
                && isValid(indices, currentIndex + dim, searchPoint)) {
            List<Integer> copyList
                    = getResultList(indices, currentIndex + dim, searchPoint + 1);
            if (!copyList.isEmpty()) {
                return copyList;
            }
        }
        if (currentIndex % dim > 0
                && isValid(indices, currentIndex - 1, searchPoint)) {
            List<Integer> copyList
                    = getResultList(indices, currentIndex - 1, searchPoint + 1);
            if (!copyList.isEmpty()) {
                return copyList;
            }
        }
        if (currentIndex % dim < (dim - 1)
                && isValid(indices, currentIndex + 1, searchPoint)) {
            List<Integer> copyList
                    = getResultList(indices, currentIndex + 1, searchPoint + 1);
            if (!copyList.isEmpty()) {
                return copyList;
            }
        }
        return Collections.emptyList();
    }

    private boolean isValid(List<Integer> indices, int indexToCheck, int searchPoint) {
        return MatrixData.word.charAt(searchPoint) == MatrixData.matrix.get(indexToCheck)
                && !indices.contains(indexToCheck);
    }

    private String buildResult(List<Integer> indices) {
        int dimension = MatrixData.dimension;
        return indices.stream()
                .map(index -> "[" + (index / dimension) + "," + (index % dimension) + "]")
                .collect(Collectors.joining("->"));
    }

    private static class MatrixData {
        static int dimension;
        static List<Character> matrix;
        static String word;

        static void setMatrix(String matrixString) {
            matrix = matrixString.chars()
                    .mapToObj(character -> (char) character)
                    .collect(Collectors.toList());
        }
    }
}
