import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.pwhiting.util.CommandLineUtils;

public class Runner {

	private static final ClassLoader THE_CLASSLOADER = Runner.class.getClassLoader();
	private static final InputStream ODD_IN = THE_CLASSLOADER.getResourceAsStream("input/Oddities.out.txt");
	private static final File ODDITIES_OUT = new File("output/Oddities.out.txt");
	
	public static void runOddities() {
		
		if (!ODDITIES_OUT.exists()) {
			ODDITIES_OUT.mkdirs();
			try {
				ODDITIES_OUT.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ODD_IN.toString();
		
		String[] lines = ODD_IN.toString().replace("\r", "").split("\n");
		int[] values = null;
		int current = 0;
		
		for (String line : lines) {
			
			try {
				int value = Integer.valueOf(line);
				if (values != null && value >= 0) {
					values = new int[value];
				} else {
					values[current++] = value;
				}
				
			} catch (NumberFormatException nfe) {
				// Our work here is done
				break;
			}
			
		}
		
		CommandLineUtils.
		
	}
	
	
	public static void main(String[] args) {
		
		
		
	}
	
}
