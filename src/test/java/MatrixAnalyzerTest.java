public class MatrixAnalyzerTest {
    public static void main(String[] args) {
        String[] matrixAndWord = new ConsoleReader().requestDataFromUser();
        System.out.println(new MatrixAnalyzer().findPath(matrixAndWord[0], matrixAndWord[1]));
    }
}
