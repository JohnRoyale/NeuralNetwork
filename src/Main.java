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
	private static float[][] arrayTranspose(float [][] A) {
		float[][] B = new float[][];
        // transpose
        if (A.length > 0) {
            for (int i = 0; i < A[0].length; i++) {
                for (int j = 0; j < A.length; j++) {
                    System.out.print(A[j][i] + " ");
                }
                System.out.print("\n");
            }
        }
		return B;
	}
	
	private static float[][] arrayMultiplication(float[][] A, float[][] B) {
		
		return C;
	}
	
	private static float[] forwardProp(float[] input){
		float[] out=new float[size[size.length-1]];
		float[] cIn=input.clone();
		
		for(int i=0;i<size.length-1;i++){
			out=new float[size[i+1]];
			for(int j=0;j<weights[i].length;j++){
				for(int k=0;k<weights[i][j].length;k++){
					out[j] +=cIn[k]*weights[i][j][k];	//hidden activation = input activation * hidden weights 
				}
				//?doesnt cloning to activation go here?
				out[j]=sigmoid(out[j]);					//hidden output = sigmoid hidden_activation
				//out[j]= out[j] > 2 ? 1 : 0;
			}
//			for(int j=0;j<out.length;j++) System.out.print(out[j]+" ");
//			System.out.println();
			activation[i]=out.clone();					//hidden output aswell??
			cIn=out.clone();
		}
		
		return out;
	}
	
	private static void backProp(float[][] activation,float[] input,float[] output,float[] expectedOutput){
		
		//Walk backwards through layers
		
		//hidden activation -> original out[j]
		//hidden output -> new out[j], now cloned to activation
		//output activation -> hidden output * weights[1][0][0,1]
		//output -> outputFunction(output activation) (returns output activation)
		//output error -> expected output - output
		//local gradient output -> dsigmoid(output activation) * output error
		//hidden error -> (local gradient output * weights[1][0][0,1]) and arraytranspose
		//local gradient hidden -> dsigmoid(original out[j]).*output error (arraymultiplication)
		//delta output -> learningRate * (hidden output (new out[j]).* local gradient output) (arraymultiplication)
		//delta hidden -> learningRate * (input .* local gradient hidden) (arraymultiplication)
		//weights hidden -> weights[0][0,1][0,1] * delta hidden
		//weight output -> weights[1][0][0,1] * delta output
		
		//determine error and gradient for every neuron in layer
		//error output= output-expected output
		//local_gradient_output = d_output_function(output_activation)*output_error;
		//delta_output = learn_rate*hidden_output.*local_gradient_output;
		//delta_hidden = [examples(pattern,:) bias_value].’*local_gradient_hidden*learn_rate;
		//% Update the weight matrices
		//w_hidden = w_hidden+delta_hidden;
		//w_output = w_output+delta_output.’;
		System.out.println("output = " + output[0] + " last in activation = " + activation[1][0]);
		
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
					System.out.print("x");
				}
				System.out.print("y");
				System.out.println();
			}
			System.out.print("z");
			System.out.println();
		}
		System.out.println(weights[1][0][1]);
		float[] test=forwardProp(input); //is test final output really output according to "output = output_function(output_activation);"?
		
		backProp(activation, input, test, expectedOutput);

		
		
	}
	
}
