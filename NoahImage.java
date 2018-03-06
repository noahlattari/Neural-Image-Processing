import java.util.Scanner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class NoahImage {

	public static double[][] output = new double[10][301];
	public static double[][] hidden = new double[300][785];

	public static void main(String[] args) throws IOException {
		Scanner hiddenscan = new Scanner(new File("hidden-weights.txt"));
		Scanner outputscan = new Scanner(new File("output-weights.txt"));

		BufferedImage img = ImageIO.read(new File("src/number.png"));
		// get pixel data
		double[] dummy = null;
		double[] X = img.getData().getPixels(0, 0, img.getWidth(), img.getHeight(), dummy);
		for (int a = 0; a < X.length; a++) {
			X[a] = X[a] / 255;
		}

		for (int i = 0; i < hidden.length; i++) {
			for (int j = 0; j < hidden[0].length; j++) {
				hidden[i][j] = hiddenscan.nextDouble();

			}
		}

		for (int k = 0; k < output.length; k++) {
			for (int l = 0; l < output[0].length; l++) {

				output[k][l] = outputscan.nextDouble();

			}
		}

		double[] hiddenFirst = new double[hidden[0].length];
		double[] hiddenSecond = new double[hidden.length];
		double[] hiddenFinal = new double[hidden.length];
		double bias = 0;

		for (int m = 0; m < hidden.length; m++) {
			for (int n = 0; n < hidden[m].length - 1; n++) {

				hiddenFirst[n] = X[n] * hidden[m][n];
				hiddenSecond[m] += hiddenFirst[n];
			}

			bias = hidden[m][784];
			hiddenSecond[m] = hiddenSecond[m] + bias;
			hiddenFinal[m] = 1 / (1 + Math.pow(Math.E, (hiddenSecond[m] * -1)));

		}

		double[] outputFirst = new double[output[0].length];
		double[] outputSecond = new double[output.length];
		double[] outputFinal = new double[output.length];

		for (int o = 0; o < output.length; o++) {
			for (int p = 0; p < output[o].length - 1; p++) {
				outputFirst[p] = hiddenFinal[p] * output[o][p];
				outputSecond[o] += outputFirst[p];
			}

			bias = output[o][300];
			outputSecond[o] = outputSecond[o] + bias;
			outputFinal[o] = 1 / (1 + Math.pow(Math.E, (outputSecond[o] * -1)));
		}

		double result = outputFinal[0];
		int position = 0;

		for (int q = 0; q < outputFinal.length; q++) {
			if (outputFinal[q] > result) {
				result = outputFinal[q];
				position = q;
			}
		}

		System.out.println("The network prediction is " + position + ".");

	}
}