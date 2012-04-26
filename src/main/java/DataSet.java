import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.io.File;

/**
 * Artemij Chugreev
 * Date: 29.03.12
 * Time: 1:10
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class DataSet {
    private static final int LINES_NUMBER = 115643;
    private Query [] queries = new Query[LINES_NUMBER];

    public DataSet(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        PrintWriter printWriter = new PrintWriter(new File("test.txt"));
        for(int i=0; i<queries.length; i++){
            queries[i] = new Query(scanner, printWriter);
        }
        printWriter.close();
    }

    public double evaluateMse(){
        double mse =0.0;
        for(Query query : queries){
            mse+=Math.pow(query.getRelevance()-query.getMachineRelevance(), 2);
        }
        return mse/queries.length;
    }
    
    public Query[] getQueries() {
        return queries;
    }
}
