/*
 *  Date: 4/6/12
 *   Time: 08:26
 *   Author: 
 *      Alexander Marchuk
 *      aamarchuk@gmail.com
 */

import java.io.*;
import java.util.StringTokenizer;

public class OutputYandexMath {
    static double test[][];
    static double w[];

    public static class F {
        double weight[];
        double target;

        double calcTarget() {
            double MSE = 0;
            for (int i = 0; i < 115643; i++) {
                double request = 0;
                for (int j = 1; j <= 490; j++) {
                    request += weight[j - 1] * test[i][j];
                }
                MSE += (request - test[i][0]) * (request - test[i][0]);
            }
            return MSE / 115643;
        }

        double calcRange(int n) {
            double range = 0;
            double round = 0;
            for (int i = 1; i < 245; i++) {
                range += weight[i - 1] * test[n][i];
                range += weight[i - 1 + 245] * test[n][i] * test[n][i];
            }
           /*if (Math.abs(Math.round(range) - range) < 0.25) {
                round = Math.round(range);
            } else  if (Math.abs(Math.round(range)+0.5 - range) < 0.25){
                round = Math.round(range)+0.5;
            } else  if (Math.abs(Math.round(range)-0.5 - range) < 0.25){
                round = Math.round(range)-0.5;
            }
            System.out.println(range + " " + round);*/
            return range;
        }

        void setTarget(double t) {
            target = t;
        }

        void setWeight(double[] lambda) {
            for (int i = 0; i < 490; i++) {
                weight[i] = lambda[i];
            }
        }

        void normalize() {
            double sum = 0;
            for (int i = 0; i < 490; i++) {
                sum += weight[i];
            }
            for (int i = 0; i < 490; i++) {
                weight[i] /= sum;
            }
        }

        void print() {
            double sum = 0;
            for (int i = 0; i < 490; i++) {
                sum += weight[i];
                System.out.print(weight[i] + " ");
            }
            System.out.println("\n sum is " + sum);
            System.out.println("target is " + target + "\n");
        }

        F() {
            weight = new double[490];
            for (int i = 0; i < 490; i++) {
                weight[i] = Math.random();
                //System.out.println(weight[i]);
            }
            normalize();
            target = -1;
        }

        F(double w[]) {
            if (w.length == 490) {
                weight = new double[490];
                for (int i = 0; i < 490; i++) {
                    weight[i] = w[i];
                    // System.out.println(weight[i]);
                    //normalize();
                }
            } else {
                System.out.println("Cant create new - invalid lenght of array!");
            }

            target = -1;
        }
    }


    public static void main(String[] args) {
        test = new double[115643][246];
        w = new double[490];

        for (int i = 0; i < 115643; i++) {
            for (int j = 0; j < 246; j++) {
                test[i][j] = 0;
            }
        }


        BufferedReader in;
        try {

            in = new BufferedReader(new InputStreamReader(new FileInputStream("imat2009_test.txt")));
            int i = 0;
            while (in.ready()) {
                String s = in.readLine();
                StringTokenizer tokenizer = new StringTokenizer(s.substring(0, s.indexOf('#')), ": ", false);
                test[i][0] = Double.parseDouble(tokenizer.nextToken());
                while (tokenizer.hasMoreTokens()) {
                    Integer j = Integer.parseInt((String) tokenizer.nextElement());
                    double val = Double.parseDouble((String) tokenizer.nextElement());
                    test[i][j.intValue()] = val;
                    // System.out.println(j.intValue() + " " + val);
                }
                i += 1;
            }
        } catch (IOException e) {
            System.out.println("Test not load!");
        }

        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("output")));
            int i = 0;
            String s = in.readLine();
            StringTokenizer tokenizer = new StringTokenizer(s, " ", false);
            while (i < 490 && tokenizer.hasMoreElements()) {
                w[i] = Double.parseDouble((String) tokenizer.nextElement());
                i += 1;
            }
        } catch (IOException e) {
            System.out.println("Output file not load!");
        }

        F f = new F(w);


        PrintWriter range;

        try {
            range = new PrintWriter(new OutputStreamWriter(new FileOutputStream("rangeOut.txt")), true);

            for (int i = 0; i < 115643; i++) {
                double newRange = f.calcRange(i);
                range.print(newRange + "\n");
                //System.out.println(i + " : " + newRange + " : " + test[i][0]);
                range.flush();
            }
        } catch (IOException e) {
            System.out.println("PrintWriter file not load!");
        }

    }
}