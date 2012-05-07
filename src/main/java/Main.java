import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Artemij Chugreev
 * Date: 28.03.12
 * Time: 23:48
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        DataSetLearning dataSetLearning = new DataSetLearning("imat2009_learning.txt");
        DataSetTesting dataSetTesting = new DataSetTesting("imat2009_test.txt", dataSetLearning.coefficients());
    }
}
