package optimizer;

import lombok.AllArgsConstructor;

public class TestFunctionBenchmark {

    /**
     * Shifted Bent Cigar D 10
     * Rotated Bent Cigar D 10
     * Rosenbrock D 10
     * Rastrigin D 10
     * Zakharov D 10
     * <p>
     * Obszar przeszukiwania [-100, 100] ^ D
     */

    public static class Levy20 implements TestFunction {

        public double functionToProcess(double[] args) {
            int n = args.length;
            double z[] = new double[n];
            for (int i = 0; i < n; i++) {
                z[i] = 1.0 + ((args[i] - 1.0) / 4.0);
            }
            double s = Math.pow(Math.sin(3.1415 * z[0]), 2.0);
            for (int i = 0; i < n - 1; i++) {
                s += Math.pow((z[i] - 1.0), 2.0) * (1.0 + 10.0 * Math.pow(Math.sin(3.1415 * z[i] + 1.0), 2.0));
            }
            return s + Math.pow(z[n - 1] - 1.0, 2.0) * (Math.pow(Math.sin(2.0 * 3.1415 * z[n - 1]), 2.0) + 1.0);
        }
    }

    public static class Zakharov implements TestFunction {
        private boolean isShifted;
        private boolean isRotated;

        private double shiftedRate;

        private double[] shiftedArray;
        private double[] rotatedArray;

        public Zakharov(boolean isShifted, boolean isRotated, double shiftedRate, double[] shiftedArray, double[] rotatedArray) {
            this.isShifted = isShifted;
            this.isRotated = isRotated;
            this.shiftedRate = shiftedRate;
            this.shiftedArray = shiftedArray;
            this.rotatedArray = rotatedArray;
        }

        public Zakharov() {
            this.isRotated = false;
            this.isShifted = false;

            this.rotatedArray = null;
            this.shiftedArray = null;

            this.shiftedRate = 1.0;
        }

        public double functionToProcess(double[] args) {
            int nx = args.length;

            double[] z = new double[nx];
            double result = 0.0;

            sr_func(args, z, nx, shiftedArray, rotatedArray, shiftedRate, isShifted, isRotated); /* shift and rotate */

            int n = z.length;
            double s1 = 0.0;
            double s2 = 0.0;
            double s3 = 0.0;
            for (double aX : z) {
                s1 += aX * aX;
            }
            for (int i = 0; i < n; i++) {
                s2 += 0.5 * (double) i * z[i];
            }
            return s1 + Math.pow(s2, 2.0) + Math.pow(s2, 4.0);
        }
    }

    public static class Rosenbrock implements TestFunction {
        private boolean isShifted;
        private boolean isRotated;

        private double shiftedRate;

        private double[] shiftedArray;
        private double[] rotatedArray;

        public Rosenbrock(boolean isShifted, boolean isRotated, double shiftedRate, double[] shiftedArray, double[] rotatedArray) {
            this.isShifted = isShifted;
            this.isRotated = isRotated;
            this.shiftedRate = shiftedRate;
            this.shiftedArray = shiftedArray;
            this.rotatedArray = rotatedArray;
        }

        public Rosenbrock() {
            this.isRotated = false;
            this.isShifted = false;

            this.rotatedArray = null;
            this.shiftedArray = null;

            this.shiftedRate = 1.0;
        }

        public double functionToProcess(double[] args) {
            int nx = args.length;

            double[] z = new double[nx];
            double result = 0.0;

            sr_func(args, z, nx, shiftedArray, rotatedArray, shiftedRate, isShifted, isRotated); /* shift and rotate */

            int n = z.length;
            double ff = 0.0;
            for (int i = 0; i < n - 1; i++) {
                ff += (100.0 * (z[i + 1] - z[i] * z[i]) * (z[i + 1] - z[i] * z[i]) + (1.0 - z[i]) * (1.0 - z[i]));
            }
            return ff;
        }
    }

    public static class Rastrigin implements TestFunction {
        private boolean isShifted;
        private boolean isRotated;

        private double shiftedRate;

        private double[] shiftedArray;
        private double[] rotatedArray;

        public Rastrigin(boolean isShifted, boolean isRotated, double shiftedRate, double[] shiftedArray, double[] rotatedArray) {
            this.isShifted = isShifted;
            this.isRotated = isRotated;
            this.shiftedRate = shiftedRate;
            this.shiftedArray = shiftedArray;
            this.rotatedArray = rotatedArray;
        }

        public Rastrigin() {
            this.isRotated = false;
            this.isShifted = false;

            this.rotatedArray = null;
            this.shiftedArray = null;

            this.shiftedRate = 1.0;
        }

        public double functionToProcess(double[] args) {
            int nx = args.length;

            double[] z = new double[nx];
            double result = 0.0;

            sr_func(args, z, nx, shiftedArray, rotatedArray, shiftedRate, isShifted, isRotated); /* shift and rotate */

            double ff = 0;
            int n = z.length;
            for (double aX : z) {
                ff += aX * aX - 10 * Math.cos(2.0 * Math.PI * aX);
            }
            return ff + 10 * n;
        }
    }

    public static class BentCigar implements TestFunction {
        private boolean isShifted;
        private boolean isRotated;

        private double shiftedRate;

        private double[] shiftedArray;
        private double[] rotatedArray;

        public BentCigar(boolean isShifted, boolean isRotated, double shiftedRate, double[] shiftedArray, double[] rotatedArray) {
            this.isShifted = isShifted;
            this.isRotated = isRotated;
            this.shiftedRate = shiftedRate;
            this.shiftedArray = shiftedArray;
            this.rotatedArray = rotatedArray;
        }

        public BentCigar() {
            this.isRotated = false;
            this.isShifted = false;

            this.rotatedArray = null;
            this.shiftedArray = null;

            this.shiftedRate = 1.0;
        }

        public double functionToProcess(double[] args) {
            int i;
            int nx = args.length;

            double[] z = new double[nx];
            double result = 0.0;

            sr_func(args, z, nx, shiftedArray, rotatedArray, shiftedRate, isShifted, isRotated); /* shift and rotate */

            result = z[0] * z[0];
            for (i = 1; i < nx; i++) {
                result += Math.pow(10.0, 6.0) * z[i] * z[i];
            }
            return result;
        }
    }

    private static void sr_func(double[] x, double[] sr_x, int nx, double[] Os, double[] Mr, double sh_rate, boolean isShifted, boolean isRotated) /* shift and rotate */ {
        int i;
        double[] y = new double[x.length];
        if (isShifted) {
            if (isRotated) {
                shiftfunc(x, y, nx, Os);
                for (i = 0; i < nx; i++)//shrink to the original search range
                {
                    y[i] = y[i] * sh_rate;
                }
                rotatefunc(y, sr_x, nx, Mr);
            } else {
                shiftfunc(x, sr_x, nx, Os);
                for (i = 0; i < nx; i++)//shrink to the original search range
                {
                    sr_x[i] = sr_x[i] * sh_rate;
                }
            }
        } else {

            if (isRotated) {
                for (i = 0; i < nx; i++)//shrink to the original search range
                {
                    y[i] = x[i] * sh_rate;
                }
                rotatefunc(y, sr_x, nx, Mr);
            } else
                for (i = 0; i < nx; i++)//shrink to the original search range
                {
                    sr_x[i] = x[i] * sh_rate;
                }
        }
    }

    private static void shiftfunc(double[] x, double[] xshift, int nx, double[] Os) {
        int i;
        for (i = 0; i < nx; i++) {
            xshift[i] = x[i] - Os[i];
        }
    }

    private static void rotatefunc(double[] x, double[] xrot, int nx, double[] Mr) {
        int i, j;
        for (i = 0; i < nx; i++) {
            xrot[i] = 0;
            for (j = 0; j < nx; j++) {
                xrot[i] = xrot[i] + x[j] * Mr[i * nx + j];
            }
        }
    }
}
