/*
 *  Date: 4/5/12
 *   Time: 15:57
 *   Author: 
 *      Alexander Marchuk
 *      aamarchuk@gmail.com
 */

import java.util.*;
import java.io.*;

public class Imat {

    static double learn[][];
    private static final int LEARN_SET_SIZE = 97290;
    private static final int FEATURES_NUMBER = 246;

    public static class F {
        double weight[];
        double target;
        double[] partSum = new double[LEARN_SET_SIZE];

        double calcTarget() {
            double MSE = 0;
            for (int i = 0; i < LEARN_SET_SIZE; i++) {
                double request = 0;
                for (int j = 1; j < FEATURES_NUMBER; j++) {
                    request += weight[j - 1] * learn[i][j];
                    //    request += weight[j - 1 + 245] * learn[i][j] * learn[i][j];
                }
                partSum[i] = request;
                MSE += (request - learn[i][0]) * (request - learn[i][0]);
            }
            return MSE;// / 97290;
        }

        double calcTarget(int index) {
            double MSE = 0;
            for (int i = 0; i < LEARN_SET_SIZE; i++) {
                double request = 0;
                for (int j = 1; j < FEATURES_NUMBER; j++) {
                    if (j != index) {
                        request += weight[j - 1] * learn[i][j];
                        //   request += weight[j - 1 + 245] * learn[i][j] * learn[i][j];
                    }
                }
                MSE += (request - learn[i][0]) * (request - learn[i][0]);
            }
            return MSE;// / 97290;
        }

        double gradient(int index) {
            double gradient = 0;
            for (int i = 0; i < LEARN_SET_SIZE - 1; i++) {
                if (index < FEATURES_NUMBER - 1) {
                    gradient += (partSum[i] - learn[i][0]) * learn[i][index + 1];
                } else {
                    gradient += (partSum[i] - learn[i][0]) * learn[i][index - FEATURES_NUMBER + 1] * learn[i][index - FEATURES_NUMBER + 1];
                }
            }
            return gradient / LEARN_SET_SIZE;
        }

        int maxDradient() {
            double[] gradients = new double[FEATURES_NUMBER];
            for (int index = 0; index < FEATURES_NUMBER; index++) {
                for (int i = 0; i < LEARN_SET_SIZE; i++) {
                    if (index < FEATURES_NUMBER) {
                        gradients[index] += (partSum[i] - learn[i][0]) * learn[i][index];
                    } else {
                        gradients[index] += (partSum[i] - learn[i][0]) * learn[i][index - FEATURES_NUMBER] * learn[i][index - FEATURES_NUMBER];
                    }
                }
                gradients[index] /= LEARN_SET_SIZE;
            }
            int max = -1;
            double maxVal = 0.0;
            for (int i = 0; i < FEATURES_NUMBER; i++) {
                if (maxVal < gradients[i]) {
                    max = i;
                    maxVal = gradients[i];
                }
            }
            System.out.println("Max grad " + maxVal + " index " + max);
            return max;
        }

        void setTarget(double t) {
            target = t;
        }

        void setWeight(double[] lambda) {
            for (int i = 0; i < FEATURES_NUMBER; i++) {
                weight[i] = lambda[i];
            }
        }

        void normalize() {
            double sum = 0;
            for (int i = 0; i < FEATURES_NUMBER; i++) {
                sum += weight[i];
            }

            for (int i = 0; i < FEATURES_NUMBER; i++) {
                weight[i] /= sum;
            }
        }

        void print() {
            double sum = 0;
            for (int i = 0; i < FEATURES_NUMBER; i++) {
                sum += weight[i];
                System.out.print(weight[i] + " ");
            }
            System.out.println("\n sum is " + sum);
            System.out.println("target is " + target + "\n");
        }

        F() {
            weight = new double[FEATURES_NUMBER];
            for (int i = 0; i < FEATURES_NUMBER; i++) {
                weight[i] = 1.0 / FEATURES_NUMBER;//Math.random();
                //System.out.println(weight[i]);
            }
            // normalize();
            target = -1;
        }

        F(double w[]) {
            if (w.length == FEATURES_NUMBER) {
                weight = new double[FEATURES_NUMBER];
                for (int i = 0; i < FEATURES_NUMBER; i++) {
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

    static double standartGauss(double mean, double deviation) {
        Random r = new Random();
        return (r.nextGaussian() * deviation + mean);
    }

    public static void main(String[] args) {
        long heapMaxSize = Runtime.getRuntime().maxMemory();
        System.out.println(heapMaxSize);
        learn = new double[LEARN_SET_SIZE][FEATURES_NUMBER];
        System.out.println(Runtime.getRuntime().freeMemory());

        for (int i = 0; i < LEARN_SET_SIZE; i++) {
            for (int j = 0; j < FEATURES_NUMBER; j++) {
                learn[i][j] = 0;
            }
        }

        double[] ww = new double[FEATURES_NUMBER];
        BufferedReader in;
        try {

            in = new BufferedReader(new InputStreamReader(new FileInputStream("imat2009_learning.txt")));
            int i = 0;
            while (in.ready()) {
                String s = in.readLine();

                StringTokenizer tokenizer = new StringTokenizer(s.substring(0, s.indexOf('#')), ": ", false);
                learn[i][0] = Double.parseDouble(tokenizer.nextToken());
                //System.out.println(learn[i][0]+" fdfd "+i);
                while (tokenizer.hasMoreTokens()) {
                    Integer j = Integer.parseInt((String) tokenizer.nextElement());
                    double val = Double.parseDouble((String) tokenizer.nextElement());
                    learn[i][j.intValue()] = val;
                    // System.out.println(learn[i][j.intValue()]+" "+j.intValue() + " " + val);
                }
                i += 1;
            }

            in = new BufferedReader(new InputStreamReader(new FileInputStream("output")));
            int jj = 0;
            String ss = in.readLine();
            StringTokenizer tokenz = new StringTokenizer(ss, " ", false);
            while (jj < FEATURES_NUMBER && tokenz.hasMoreElements()) {
                ww[jj] = Double.parseDouble((String) tokenz.nextElement());
                // ww[jj] = Math.random() / 100;
                // ww[jj+245] = 0.0;
                jj += 1;
            }

        } catch (IOException e) {
            System.out.println("Learn not load!");
        }


        F f = new F(ww);
        double t = f.calcTarget();
        f.setTarget(t);

        double[] lambda = new double[FEATURES_NUMBER];
        int nostep = 0;
        int step = 0;

        F fL = new F(f.weight);
        fL.setTarget(t);
        double accept = 0;

        double bestTarget = t;
        System.out.println("Best target is " + bestTarget + " witj coeff");
        double[] bestW = new double[FEATURES_NUMBER];
        for (int j = 0; j < FEATURES_NUMBER; j++) {
            bestW[j] = f.weight[j];
            System.out.print(bestW[j] + " ");
        }
        System.out.println();

        double tau = 0.001;
        double alpha = 0.01;
        double lambdaTarget = 1000000.0;
        double[] grad = new double[FEATURES_NUMBER];

        while (bestTarget > 0.1) {
            System.out.println();
            step++;

            for (int i = 0; i < FEATURES_NUMBER; i++) {
                grad[i] = f.gradient(i);
            }

            for (int i = 0; i < FEATURES_NUMBER; i++) {
                lambda[i] = f.weight[i] - alpha * grad[i];
            }
            fL.setWeight(lambda);
            lambdaTarget = fL.calcTarget();

            while (lambdaTarget > f.target) {
                alpha /= 2;


                for (int i = 0; i < FEATURES_NUMBER; i++) {
                    lambda[i] = f.weight[i] - alpha * grad[i];//standartGauss(f.weight[i], 0.01);
                }

                fL.setWeight(lambda);
                lambdaTarget = fL.calcTarget();

            }
            alpha *= 10;
            System.out.println("Alpha is " + alpha);
            System.out.println("Improve on " + (f.target - lambdaTarget));

            f.setWeight(fL.weight);
            double u = f.calcTarget();
            f.setTarget(u);

            nostep++;

            if (lambdaTarget < bestTarget) {
                // System.out.println("Improve on "+ (bestTarget-lambdaTarget));
                bestTarget = lambdaTarget;
                for (int j = 0; j < FEATURES_NUMBER; j++) {
                    bestW[j] = f.weight[j];
                }
                nostep = 0;
            }
            System.out.println("New MSE is " + f.target + " BestTarget " + bestTarget + " LambdaTarget " + lambdaTarget + " reached in " + step + " step. Nostep is:" + nostep);
            for (int j = 0; j < FEATURES_NUMBER; j++) {
                System.out.print(bestW[j] + " ");
            }
            System.out.println();

        }

    }
}