import java.util.Random;


public class Main {
	static float[][][] weights;
	static int[] size= {2,2,1};
	static float[][] activation;
	static float learningRate = 0.2f;
	
	private static float sigmoid(float in){
		return (float) (1 / Math.exp(-in)+1);
	}
	private static float dSigmoid(float in){
		float t=sigmoid(in);
		return t * (1-t);
	}
//	private static float dOutput(float in) {
//		
//	}
	
	private static float[] forwardProp(float[] input){
		float[] out=new float[size[size.length-1]];
		float[] cIn=input.clone();
		
		for(int i=0;i<size.length-1;i++){
			out=new float[size[i+1]];
			for(int j=0;j<weights[i].length;j++){
				for(int k=0;k<weights[i][j].length;k++){
					out[j] +=cIn[k]*weights[i][j][k];	//input activation * weights
				}
				//?doesnt cloning to activation go here?
				out[j]=sigmoid(out[j]);					//hidden output
				//out[j]= out[j] > 2 ? 1 : 0;
			}
			for(int j=0;j<out.length;j++) System.out.print(out[j]+" ");
			System.out.println();
			activation[i]=out.clone();
			cIn=out.clone();
		}
		
		return out;
	}
	
	private static void backProp(float[][] activation,float[] input,float[] output,float[] expectedOutput){
		
		//Walk backwards through layers
		
		//determine error and gradient for every neuron in layer
		//error output= output-expected output
		//local_gradient_output = d_output_function(output_activation)*output_error;
		//delta_output = learn_rate*hidden_output.*local_gradient_output;
		//delta_hidden = [examples(pattern,:) bias_value].’*local_gradient_hidden*learn_rate;
		//% Update the weight matrices
		//w_hidden = w_hidden+delta_hidden;
		//w_output = w_output+delta_output.’;
		System.out.println("output = " + output[0] + " last in activation = " + activation[1][0]);
		
		//float[]errorOutput = new float[expectedOutput.length];
		//float[]gradientOutput = new float[expectedOutput.length];
		//for(int i=0; i<expectedOutput.length-1;i++) {
		//	errorOutput[i] = expectedOutput[i] - outputFunction(output[i]); ? outputFunction is sigmoid or just output[i]?
		//	gradientOutput[i] = dOutputfunction(output[i]) * errorOutput[i];
		//}
		
		
	}
	
	
	public static void main(String[] args) {
		//input=new float[size[0]];
		float[] input= {0,1};
		float[] expectedOutput={1};
		float[] output;
		weights=new float[size.length-1][][];
		activation=new float[size.length-1][];
		
		for(int i=0;i<weights.length;i++){
			weights[i]=new float[size[i+1]][size[i]];
		}
		Random r=new Random();
		for(int i=0;i<weights.length;i++){
			for(int j=0;j<weights[i].length;j++){
				for(int k=0;k<weights[i][j].length;k++){
					weights[i][j][k]=r.nextFloat()*2-1;
					System.out.print(weights[i][j][k]+" ");
				}
				System.out.println();
			}
			//System.out.println();
		}
		//System.out.println();
		float[] test=forwardProp(input); //is test final output really output according to "output = output_function(output_activation);"?
		
		backProp(activation, input, test, expectedOutput);

		
		
	}
	
}
