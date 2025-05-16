import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();

        System.out.print("Enter number of points: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 0; i < n; i++) {
            System.out.print("Enter x and y separated by a comma: ");
            String[] parts = scanner.nextLine().split(",");
            x.add(Double.parseDouble(parts[0].trim()));
            y.add(Double.parseDouble(parts[1].trim()));
        }

        List<List<Double>> table = computeDividedDifferences(x, y);
        printDividedDifferenceTable(table);

        // Evaluate at a specific x
        System.out.print("\nEnter value of x to evaluate the polynomial: ");
        double xVal = scanner.nextDouble();
        double result = evaluateNewtonPolynomial(table, x, xVal);
        System.out.printf("P(%.4f) = %.4f%n", xVal, result);

        scanner.close();
    }

    public static List<List<Double>> computeDividedDifferences(List<Double> x, List<Double> y) {
        int n = x.size();
        List<List<Double>> table = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>(Collections.nCopies(n, null));
            row.set(0, y.get(i));
            table.add(row);
        }

        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                double numerator = table.get(i + 1).get(j - 1) - table.get(i).get(j - 1);
                double denominator = x.get(i + j) - x.get(i);
                table.get(i).set(j, numerator / denominator);
            }
        }

        return table;
    }

    public static double evaluateNewtonPolynomial(List<List<Double>> table, List<Double> x, double xVal) {
        double result = 0.0;
        double term = 1.0;

        for (int i = 0; i < x.size(); i++) {
            result += table.get(0).get(i) * term;
            term *= (xVal - x.get(i));
        }

        return result;
    }

    public static void printDividedDifferenceTable(List<List<Double>> table) {
        System.out.println("\nDivided Differences Table:");
        for (List<Double> row : table) {
            for (Double val : row) {
                if (val != null) {
                    System.out.printf("%15.4f", val);
                } else {
                    System.out.printf("%15s", "");
                }
            }
            System.out.println();
        }
    }
}
