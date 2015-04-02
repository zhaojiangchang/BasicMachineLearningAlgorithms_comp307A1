package part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * @author JackyChang
 * ID:300282984
 *
 */
public class Main {

	public static void main(String[] args) {
		ReadFile rf = new ReadFile();
		rf.setMatched(0);
		System.out.print("Enter fileName(Format: hepatitis-training.dat hepatitis-test.dat) \n"
				+ "OR \nInput 'yes' to get average accuracy of the classifiers over the 10 trials\n \nInput here: ");

				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				String argu;
				String trainingAdd = null;
				String testAdd = null;
						try {
							argu = br.readLine();
							String[] tokens = argu.split(" ");

							if(tokens[0].equalsIgnoreCase("yes")){
								rf.runTrails(new ReadFile());
							}
							else{
								trainingAdd = "./data/"+tokens[0];
								testAdd = "data/"+tokens[1];
								rf.runTrainingTestData(new ReadFile(), trainingAdd, testAdd);
							}

						} catch (IOException ioe) {
							System.exit(1);
						}

	}


}
