import java.io.File;
import java.io.IOException;

import com.pwhiting.util.Command;

public class Runner {

	private static final ClassLoader THE_CLASSLOADER = Runner.class.getClassLoader();
	private static final Command JAVA_COMMAND = new Command("java").setFinal();
	
	public static void runOddities() {
		final String ODD_IN = THE_CLASSLOADER.getResource("input/Oddities.in").getFile();
		final File ODDITIES_OUT = new File("output/Oddities.out");
		
		if (!ODDITIES_OUT.exists()) {
			try {
				ODDITIES_OUT.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			System.out.println(JAVA_COMMAND.execute("Oddities", "<", ODD_IN/*, ">", ODDITIES_OUT.getPath()*/));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		
		runOddities();
		
	}
	
}
