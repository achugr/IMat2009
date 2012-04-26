import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Artemij Chugreev
 * Date: 29.03.12
 * Time: 0:48
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class Query {
    private double[] features = new double[256];
    private double relevance;
    private double machineRelevance;
    private int id;
    private static int counter = 0;

    public Query(double[] features, int relevance) {
        this.features = features;
        this.relevance = relevance;
    }

    public Query(Scanner scanner, PrintWriter printWriter) {
        parseQueryByScanner(scanner, printWriter);
    }

    private void parseQueryByScanner(Scanner scanner, PrintWriter printWriter) {
        if (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(s.substring(0, s.indexOf('#')), ": ", false);
            relevance = Double.parseDouble(tokenizer.nextToken());
            while (tokenizer.hasMoreTokens()) {
                Integer j = Integer.parseInt((String) tokenizer.nextElement());
                double val = Double.parseDouble((String) tokenizer.nextElement());
                features[j] = val;
            }
        }
        SimpleTargetFunction simpleTargetFunction = new SimpleTargetFunction();
        machineRelevance = simpleTargetFunction.evaluateRelevance(features);
        System.out.println(machineRelevance);
        printWriter.println(machineRelevance);
    }

    public int getCounter() {
        return counter;
    }

    public double[] getFeatures() {
        return features;
    }

    public double getRelevance() {
        return relevance;
    }

    public double getMachineRelevance() {
        return machineRelevance;
    }
}

