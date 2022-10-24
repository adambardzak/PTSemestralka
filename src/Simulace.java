
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Simulace {
	public static void main(String args[]) throws FileNotFoundException {
		File file = new File("/Users/adambardzak/Desktop/tutorial.txt");
		ArrayList<Character> listDat = new ArrayList<Character>(); 
		
		//vytvorEntity(toStringList(Parser.parse(file)));
		
		Graf graf = new Graf(6);
		graf.pridejHranu(1, 4);
		graf.pridejHranu(1, 3);
		graf.pridejHranu(4, 1);
		graf.pridejHranu(1, 5);
		graf.pridejHranu(6, 3);
		graf.vypisMatici();
		
		
		
	}
	
	//prebira list s informacemi o entitach
	public static void vytvorEntity(ArrayList<String> list) {
		int pointer = 0; //pomocne ukazovatko, ukazuje kde se zrovna nachazime v listu
		
		
		//--------------------------SKLADY--------------------------
		int pocetSkladu = Integer.parseInt(list.get(pointer));
		pointer++;
		ArrayList<Sklad> sklady = new ArrayList<Sklad>();
		for(int i = 1; i < pocetSkladu + 1; i++) {
			Sklad sklad = new Sklad(Integer.parseInt(list.get(pointer)), Integer.parseInt(list.get(pointer + 2)), Integer.parseInt(list.get(pointer + 3)), Integer.parseInt(list.get(pointer + 4)), Integer.parseInt(list.get(pointer + 5)), i);
			sklady.add(sklad);
			pointer = pointer + 5;
		}
		//zkusebni vypis
		System.out.println("Sklady:");
		System.out.println(sklady.get(0).vypis());
		System.out.println("pointer se nachazi na pozici: "+pointer);
		
		
		//--------------------------OAZY--------------------------
		int pocetOaz = Integer.parseInt(list.get(pointer));
		pointer++;
		ArrayList<Oaza> oazy = new ArrayList<Oaza>();
		System.out.println("Oazy: ");
		for(int i = 1; i < pocetOaz + 1; i++) {
			Oaza oaza = new Oaza(Integer.parseInt(list.get(pointer)), Integer.parseInt(list.get(pointer + 1)), pocetOaz, i);
			oazy.add(oaza);
			System.out.println(oaza.vypis());
			pointer = pointer + 2;
		}
		System.out.println("pointer se nachazi na pozici: "+pointer);
		
	}
	/*
	 * rozdeli list charu na Stringy podle mezer
	 */
	public static ArrayList<String> toStringList(ArrayList<Character> list)  {
		ArrayList<String> prevedeny = new ArrayList<String>();
		
		for(int i = 0; i < list.size(); i++) {
			
			if(list.get(i) != ' ') {
				
				StringBuilder sb = new StringBuilder();
				while(list.get(i) != ' ') {
					
					sb.append(list.get(i));
					i++;
				}
				
				prevedeny.add(sb.toString());
					
			}
		}
		
//		for(int i = 0; i < prevedeny.size(); i++) {
//			System.out.println(prevedeny.get(i));
//		}
		
		return prevedeny;
	}
}
