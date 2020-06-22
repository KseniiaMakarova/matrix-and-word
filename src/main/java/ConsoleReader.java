import java.util.Scanner;

public class ConsoleReader {
    public String[] requestDataFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the string that will form the matrix:");
        String matrixString;
        while (true) {
            matrixString = scanner.next().toUpperCase();
            if (Math.sqrt(matrixString.length()) % 1 == 0) {
                break;
            }
            System.out.println("Sorry, the string of length " + matrixString.length()
                    + " can not be transformed to matrix! Try again:");
        }
        System.out.println("Please enter the word you want to search for:");
        String word = scanner.next().toUpperCase();
        return new String[] {matrixString, word};
    }
}
