import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Parser {
	
	
	public static ArrayList<String> parse(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		int pocetSkladu = 0;
		int pocetOaz = 0;
		int pocetCest = 0;
		int pocetDVelblouda = 0;
		int pocetPozadavku = 0;
		int[] sklady = new int[pocetSkladu * 5];
		int[] oazy = new int[pocetOaz * 2];
		int[] cesty = new int[pocetCest * 2];
		String[] velbloudi = new String[pocetDVelblouda * 8];
		int[] pozadavky = new int[ pocetPozadavku * 4]; 

		
		
		//predelat na arraylist
		//zjistovani velikosti vstupniho souboru
		int count = 0;
		while (sc.hasNext()) {
			count++;
			sc.next();
		}
		sc.close();
		
		//nacitani vstupniho souboru do pole Stringu
		String[] info = new String[count];
		Scanner s = new Scanner(file);
		for (int i = 0; i < count; i++) {
			info[i] = s.next();
		}
		
		ArrayList<String> parsed = new ArrayList<String>();
		//odstranovani komentaru
		for(int i = 0; i < info.length; i++) {
			if(info[i].equals("🐪")) {
				while (!(info[i].equals("🏜") && i < info.length)) {
					i++;
				} continue;
			} 
			//System.out.println(info[i]);
			parsed.add(info[i]);
		}
		
//		for(int i = 0; i < parsed.size(); i++) {
//			System.out.println(parsed.get(i));
//		}
		
		return parsed;
		
		
		}
	
}