import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Simulace {
	public static void main(String args[]) throws FileNotFoundException {
		File file = new File("/Users/adambardzak/Desktop/tutorial.txt");
		ArrayList<String> listDat = new ArrayList<String>(); 
		listDat = Parser.parse(file);
		for(int i = 0; i < listDat.size(); i++) {
			System.out.println(listDat.get(i));
		}
		
	}
	
	public void split(ArrayList<String> list) {
		ArrayList<String> velbloudi, cesty, oazy, pozadavky, sklady;
	}
	
	
}