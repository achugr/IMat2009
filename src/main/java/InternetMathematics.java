import java.io.FileNotFoundException;

/**
 * Artemij Chugreev
 * Date: 28.03.12
 * Time: 23:48
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class InternetMathematics {

    public static void main(String[] args) throws FileNotFoundException {
        DataSet dataSet = new DataSet("imat2009_test.txt");
        System.out.println("mse = " + dataSet.evaluateMse());
    }
}
