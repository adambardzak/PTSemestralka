import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Parser {
	
	static char v1 = '\uD83D'; //kody emojis
	static char v2 =  '\uDC2A';
	static char p1 = '\uD83C';
	static char p2 = '\uDFDC';
	
	
	public static ArrayList<Character> parse(File file) throws FileNotFoundException {
		//nacitani vstupniho souboru do pole Stringu
		ArrayList<String> radky = new ArrayList<String>();
		ArrayList<Character> znaky = new ArrayList<Character>();
		Scanner s = new Scanner(file);
		
		while(s.hasNextLine()) {
			radky.add(s.nextLine());
		}
		s.close();
		
		char[] temp;
		for(int i = 0; i < radky.size(); i++) {
			temp = radky.get(i).toCharArray();
			znaky.add(' ');
			for(int j = 0; j < temp.length; j++) {
				znaky.add(temp[j]);
			}
		}
		
		

		
		
		ArrayList<Character> parsed = new ArrayList<Character>();
		
		parsed = odstranKomentare(znaky);
		
		return parsed;
		
		
		}
	public static ArrayList<Character> odstranKomentare(ArrayList<Character> list) {
		int indexp = 0;
		int indexk = 0;
		int pocetVelbloudu = 0;
		int pocetPousti = 0;
	if(list.size() > 2) {
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i) == v1 && list.get(i + 1) == v2) {
				pocetVelbloudu++;
				if(indexp == 0) indexp = i;
			}
			if (list.get(i) == p1 && list.get(i + 1) == p2) {
				pocetPousti++;
				indexk = i;
			}
			if (pocetPousti == pocetVelbloudu && pocetVelbloudu > 0) {
				try {
					list.subList(indexp, indexk + 2).clear();
					list.add(indexp,' ');

				} catch (Exception e ) {
					System.out.println("Chyba");
				}
				list = odstranKomentare(list);
				break;
			}
		}
	}
		return list;
//		for(int i = 0; i < list.size(); i++) {
//			if(list.get(i) == v1 && list.get(i + 1) == v2) {
//				do {
//					list.remove(i);
//					odstranKomentare(list);
//				} while ((list.get(i) != p1 && list.get(i + 1) != p2));
//			}
//		}
	}
	
	public static ArrayList<Character> odstranPouste(ArrayList<Character> list) {
		ArrayList<Character> bezPousti = new ArrayList<Character>();
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) != p1 && list.get(i) != p2) {
				bezPousti.add(list.get(i));
			}
		}
		return bezPousti;
	}
	
}