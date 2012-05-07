import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: achugr
 * Date: 07.05.12
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
public class DataSetTesting {
    private double [] coefficients;
    private static final int LINES_NUMBER = 115643;
    private Query [] queries = new Query[LINES_NUMBER];


    public DataSetTesting(String filePath, double [] coefficients) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(filePath));
        PrintWriter printWriter = new PrintWriter(new File("test.txt"));

        for(int i=0; i<queries.length; i++){
            queries[i] = new Query(scanner);
        }
        for(Query query : queries){
            printWriter.println(query.evaluateRelevance(coefficients));
        }
        printWriter.close();
        scanner.close();

    }
}
