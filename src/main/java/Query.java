import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Artemij Chugreev
 * Date: 29.03.12
 * Time: 0:48
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class Query {
    private static final int FEATURES_NUMBER = 256;
    private double[] features = new double[FEATURES_NUMBER];
    private double relevance;
    private double machineRelevance;
    private int id;
    private static int counter = 0;
//    private static final double [] coefficients;

//    public Query(double[] features, int relevance) {
//        this.features = features;
//        this.relevance = relevance;
//    }

    public Query(Scanner scanner) {
        features = getFeatures(scanner);
//        this.coefficients = coefficients;
//        printWriter.println(evaluateRelevance(coefficients));
    }

    private double [] getFeatures(Scanner scanner) {
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
        return features;    
    }
    
    public double evaluateRelevance(double [] coefficients){
        SimpleTargetFunction simpleTargetFunction = new SimpleTargetFunction();
        machineRelevance = simpleTargetFunction.evaluateRelevance(features, coefficients);
//        System.out.println(machineRelevance);
        return machineRelevance;
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

