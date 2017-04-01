import java.util.Arrays;
import java.util.Random;

public class Main {
	static double[][][] weights;
	static int[] size = { 2, 10, 1 };
	static double[][] activation;
	static int printD = 500;
	static double learningRate = 0.005;
	static double maxError = 0.01;

	private static double sigmoid(double in) {
		return (double) (1 / Math.exp(-in) + 1);
	}

	private static double dSigmoid(double in) {
		double t = sigmoid(in);
		return t * (1 - t);
	}

	private static double[][] forwardProp() {

		for (int i = 0; i < size.length - 1; i++) {
			activation[i + 1] = new double[size[i + 1]];
			for (int j = 0; j < weights[i].length; j++) {
				for (int k = 0; k < weights[i][j].length; k++) {
					activation[i + 1][j] += activation[i][k] * weights[i][j][k];
				}
				if (i < size.length - 2) {
					activation[i + 1][j] = sigmoid(activation[i + 1][j]);
				}
			}
		}

		return activation;
	}

	private static void arrayAdd(double[] A, double[] B) {
		if (A.length != B.length)
			System.err.print("Arrays need to be off equals size to substract");

		for (int i = 0; i < A.length; i++)
			A[i] = A[i] + B[i];
	}

	private static double[] arraySub(double[] A, double[] B) {
		if (A.length != B.length)
			System.err.print("Arrays need to be off equals size to substract");
		double[] C = new double[A.length];
		for (int i = 0; i < A.length; i++)
			C[i] = A[i] - B[i];
		return C;
	}

	private static void backProp(double[][] activation, double[] expectedOutput) {
		double[][] error = new double[activation.length][];

		for (int i = 0; i < activation.length; i++) {
			error[i] = new double[activation[i].length];
		}

		double[][] delta = new double[size.length][];
		for (int i = 0; i < delta.length; i++) {
			delta[i] = new double[size[i]];
		}

		error[error.length - 1] = arraySub(expectedOutput, activation[activation.length - 1]);
		double[][] localGradient = new double[error.length][];
		for (int i = 0; i < error.length; i++) {
			localGradient[i] = Arrays.copyOf(error[i], error[i].length);
		}

		for (int k = weights.length - 1; k > 0; k--) {
			if (k > 0) {
				error[k] = new double[error[k].length];
				localGradient[k] = new double[localGradient[k].length];
			}
			for (int i = 0; i < delta[k + 1].length; i++) {
				if (k != weights.length - 1) {
					delta[k + 1][i] = error[k + 1][i] * dSigmoid(activation[k + 1][i]);
				} else {
					delta[k + 1][i] = error[k + 1][i];
				}
				for (int j = 0; j < weights[k][i].length; j++) {
					error[k][j] += delta[k + 1][i] * weights[k][i][j];
				}
			}
		}

		for (int k = weights.length - 1; k > 0; k--) {
			for (int i = 0; i < weights[k].length; i++) {
				for (int j = 0; j < weights[k][i].length; j++) {
					weights[k][i][j] += learningRate * delta[k + 1][i] * activation[k][j];
				}
			}
		}

	}

	private static double error(double[] output, double[] expectedOutput) {
		double error = 0;
		double t;
		if (output.length != expectedOutput.length)
			System.err.println("Cannot calculate error if arrays not same size");

		for (int i = 0; i < output.length; i++) {
			t = (output[i] - expectedOutput[i]);
			error += t * t;
		}
		return error;
	}

	public static void main(String[] args) {
		double[][] input = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
		double[][] expectedOutput = { { 0 }, { 1 }, { 1 }, { 0 } };

		weights = new double[size.length - 1][][];

		for (int i = 0; i < weights.length; i++) {
			weights[i] = new double[size[i + 1]][size[i]];
		}

		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				for (int k = 0; k < weights[i][j].length; k++) {
					weights[i][j][k] = r.nextDouble() * 2 - 1;
				}
			}
		}

		double error;
		int i = -1;
		boolean flag = true;
		while (flag) {
			i++;
			flag = false;
			if (i % printD == 0)
				System.out.println("Epoch " + i);
			for (int k = 0; k < input.length; k++) {
				activation = new double[size.length][];
				activation[0] = Arrays.copyOf(input[k], input[k].length);
				activation = forwardProp();
				backProp(activation, expectedOutput[k]);
				error = error(activation[activation.length - 1], expectedOutput[k]);
				if (Math.floorMod(i, printD) == 0) {
					System.out.print("Pattern " + k + ": " + activation[activation.length - 1][0]);
					System.out.println(" Squared Error: " + error);
				}
				if (error > maxError && i < 20000)
					flag = true;
			}
			if (i % printD == 0)
				System.out.println();
		}

		System.out.println("Epoch " + i);
		for (int k = 0; k < input.length; k++) {
			activation = new double[size.length][];
			activation[0] = Arrays.copyOf(input[k], input[k].length);
			activation = forwardProp();
			error = error(activation[activation.length - 1], expectedOutput[k]);
			System.out.print("Pattern " + k + ": " + activation[activation.length - 1][0]);
			System.out.println(" Squared Error: " + error);

		}
	}

}
