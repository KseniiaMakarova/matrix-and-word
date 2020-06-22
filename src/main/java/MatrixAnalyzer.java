import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatrixAnalyzer {
    private static final int[] ROWS_INDICES_HELPER = new int[] {-1, 1, 0, 0};
    private static final int[] COLS_INDICES_HELPER = new int[] {0, 0, -1, 1};

    public String findPath(String matrixString, String word) {
        int dimension = (int) Math.sqrt(matrixString.length());
        char[][] matrix = getMatrix(matrixString, dimension);
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                Optional<List<String>> optionalPath 
                        = testAndAddNode(row, col, matrix, word, 0, new ArrayList<>());
                if (optionalPath.isPresent()) {
                    return String.join("->", optionalPath.get());
                }
            }
        }
        return "The matrix does not contain this word!";
    }

    private char[][] getMatrix(String matrixString, int dimension) {
        char[][] matrix = new char[dimension][dimension];
        String[] rows = matrixString.split("(?<=\\G.{" + dimension + "})");
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = rows[i].toCharArray();
        }
        return matrix;
    }

    private Optional<List<String>> testAndAddNode(
            int row, int col, char[][] matrix, String word, int charIndex, List<String> path) {
        String node = "[" + row + "," + col + "]";
        if (matrix[row][col] != word.charAt(charIndex) || path.contains(node)) {
            return Optional.empty();
        }
        List<String> localPath = new ArrayList<>(path);
        localPath.add(node);
        if (localPath.size() == word.length()) {
            return Optional.of(localPath);
        }
        for (int i = 0; i < ROWS_INDICES_HELPER.length; i++) {
            int newRow = row + ROWS_INDICES_HELPER[i];
            int newCol = col + COLS_INDICES_HELPER[i];
            if (isNodeValid(newRow, newCol, matrix.length)) {
                Optional<List<String>> optionalPath
                        = testAndAddNode(newRow, newCol, matrix, word, charIndex + 1, localPath);
                if (optionalPath.isPresent()) {
                    return optionalPath;
                }
            }
        }
        return Optional.empty();
    }

    private boolean isNodeValid(int newRow, int newCol, int dimension) {
        return newRow >= 0 && newCol >= 0 && newRow < dimension && newCol < dimension;
    }
}
