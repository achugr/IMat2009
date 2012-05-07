/**
 * Artemij Chugreev
 * Date: 29.03.12
 * Time: 1:35
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class SimpleTargetFunction implements TargetFunction{

    public double evaluateRelevance(double [] features){
        double sum = 0.0;
        for(int i=0; i<features.length; i++){
            sum+=features[i];
        }
        sum = 4 * sum/features.length;
        return sum;
    }
    
    public double evaluateRelevance(double [] features, double coeff[]){
        double sum = 0.0;
        for(int i=0; i<features.length; i++){
            sum += coeff[i] * features[i];
        }
        sum = 4 * sum/features.length;
        return sum;
    }

}
