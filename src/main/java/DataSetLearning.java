import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Artemij Chugreev
 * Date: 29.03.12
 * Time: 1:10
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class DataSetLearning {
    public static final int LINES_NUMBER = 97290;
    private static final int FEATURES_NUMBER = 256;
    private Query [] queries = new Query[LINES_NUMBER];
    private double [] coefficients = new double[FEATURES_NUMBER];
    private static final int ITERATIONS_NUMBER = 20000;
    private static final double STEP=0.5;

    public DataSetLearning(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        for(int i=0; i<queries.length; i++){
            queries[i] = new Query(scanner);
        }
        double prevMse;
        double currMse = Double.MAX_VALUE;
        double bestMse = Double.MAX_VALUE;
        int featureIndex;
        double prevCoefficient;
        double machineRelevance;
        double relevanceSummDiff=0;
        double [] relevanceDiff = new double[ITERATIONS_NUMBER];
        for(int i=0; i<ITERATIONS_NUMBER; i++){
            relevanceSummDiff = 0;
            Random random = new Random();
            featureIndex = Math.abs(random.nextInt() % FEATURES_NUMBER);
            prevCoefficient = coefficients[featureIndex];
            coefficients[featureIndex] += random.nextDouble()%(STEP*500/ITERATIONS_NUMBER);
            for (Query query : queries) {
                machineRelevance = query.evaluateRelevance(coefficients);
//                System.out.println("machine rel: " + machineRelevance + "real rel: "+query.getRelevance());
                relevanceSummDiff += Math.abs(machineRelevance-query.getRelevance());
            }
            relevanceDiff[i] = relevanceSummDiff;
            prevMse = currMse;
            currMse = evaluateMse();
            System.out.println("prev mse: " + prevMse + "curr mse: " + currMse);
            if(currMse > prevMse){
                coefficients[featureIndex] = prevCoefficient;
            }
            bestMse = Math.min(currMse, prevMse);
        }
        System.out.println(Arrays.toString(coefficients));
        System.out.println(Arrays.toString(relevanceDiff));
        System.out.println("best mse: " + bestMse);
    }

    public double [] coefficients(){
        return coefficients;
    }

    public void updateCoefficients(){
        Random random = new Random();
        int featureIndex = random.nextInt() % FEATURES_NUMBER;
        coefficients[featureIndex] = random.nextDouble();
    }
    
    public double evaluateMse(){
        double mse =0.0;
        for(Query query : queries){
            mse+=Math.pow(query.getRelevance()-query.getMachineRelevance(), 2);
        }
        return Math.sqrt(mse/LINES_NUMBER);
    }
    
    public Query[] getQueries() {
        return queries;
    }
}
