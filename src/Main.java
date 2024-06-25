import java.util.Random;

public class Main {
    public static void main(String[] args) {
        controller control = new controller();
        control.run();
    }
    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public static double unsigmoid(double x) {
        if (x < 0 || x > 1) {
            throw new IllegalArgumentException("The input to the unsigmoid function must be in (0, 1)");
        } else if (x == 0) {
            return -15;
        } else if (x == 1) {
            return 15;
        }
        return -Math.log((1 / x) - 1);
    }
    public static boolean randomChance(float chance) {
        return new Random().nextDouble() < chance;
    }
    public static float randomFloat(float x) {return (new Random().nextFloat() - 0.5f) * 2 * x;}
    public static float difference(float a, float b) {
        if (a > b) {
            return a - b;
        } else {
            return b - a;
        }
    }
    public static int difference(int a, int b) {return (int) difference((float) a, (float) b);}

    public static float colourCutOff(float input, float target, float standardDeviation) {
        float exponent = (float) (-Math.pow(input - target, 2) / (2 * Math.pow(standardDeviation, 2)));
        return 1 - (float) Math.exp(exponent);
    }
    public static float speciationMultiplier(float input) {return (float) (1 / (1 + Math.exp(-0.02F * (-input+382.5F))));}
}