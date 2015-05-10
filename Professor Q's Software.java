import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
	
public class Main {
	public static void main(String[] args) {
    	try {
			BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
			
			// read number of test cases
			int t = Integer.parseInt(strin.readLine());
			
			for (int i = 0; i < t; i++) {
				// read number of modules and number of initial signals
				String[] tmp = strin.readLine().split(" ");
				int n = Integer.parseInt(tmp[0]);
				
				// read initial signals and put them into the signal queue
				tmp = strin.readLine().split(" ");
				LinkedList<Integer> queue = new LinkedList<Integer>();
				for (String str : tmp) {
					queue.add(Integer.parseInt(str));
				}
				
				// use a list to record started times of each module (represented by index)
				int[] startedTimes = new int[n];
				// use a map to map the input signals to modules (represented by index)
				HashMap<Integer, List<Integer>> sigToMod = new HashMap<Integer, List<Integer>>();
				// use a map to map the module to output signals
				HashMap<Integer, List<Integer>> modToSig = new HashMap<Integer, List<Integer>>();
				
				// read each module
				for (int j = 0; j < n; j++) {
					// module j's started times is 0 initially
					startedTimes[j] = 0;
					
					// read the module input signal
					tmp = strin.readLine().split(" ");
					int inSig = Integer.parseInt(tmp[0]);
					
					// put input signal and module to map sigToMod
					if (!sigToMod.containsKey(inSig)) {
						List<Integer> modules = new ArrayList<Integer>();
						modules.add(j);
						sigToMod.put(inSig, modules);
					} else {
						List<Integer> modules = sigToMod.get(inSig);
						modules.add(j);
					}
					
					// read the module output signals
					int k = Integer.parseInt(tmp[1]);
					List<Integer> outSigs = new ArrayList<Integer>();
					int cnt = 0;
					while (cnt < k) {
						outSigs.add(Integer.parseInt(tmp[cnt + 2]));
						cnt++;
					}
					
					// put module to output signals to map modToSigs
					modToSig.put(j, outSigs);
				}
				
				// run the software
				while (!queue.isEmpty()) {
					int inSig = queue.pollFirst();
					
					// if this signal can start some modules
					if (sigToMod.containsKey(inSig)) {
						List<Integer> modules = sigToMod.get(inSig);
						
						for (int mod : modules) {
							// add started times of each module that can be started by this signal
							startedTimes[mod] = (startedTimes[mod] + 1) % 142857;
							
							// meanwhile put the output signals of each module into the queue
							List<Integer> outSigs = modToSig.get(mod);
							for (int sig : outSigs) {
								queue.add(sig);
							}
						}
					}
				}
				
				// output the started times of each module
				for (int j = 0; j < n; j++) {
					System.out.print(startedTimes[j] + " ");
				}
				System.out.println();
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}
