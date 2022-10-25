
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Simulace {
	public static int pocetOaz, pocetSkladu, pocetCest, pocetVrcholu, pocetVelbloudu, pocetPozadavku;
	public static void main(String args[]) throws FileNotFoundException {
		File file = new File("/Users/adambardzak/Desktop/tutorial.txt");
		vytvorEntity(toStringList(Parser.parse(file)));

	}

	//prebira list s informacemi o entitach
	public static void vytvorEntity(ArrayList<String> list) {
		int pointer = 0; //pomocne ukazovatko, ukazuje kde se zrovna nachazime v listu


		//--------------------------SKLADY--------------------------
		pocetSkladu = Integer.parseInt(list.get(pointer));
		pointer++;
		ArrayList<Sklad> sklady = new ArrayList<Sklad>();
		for(int i = 1; i < pocetSkladu + 1; i++) {
			Sklad sklad = new Sklad(Integer.parseInt(list.get(pointer)), Integer.parseInt(list.get(pointer + 1)),
					Integer.parseInt(list.get(pointer + 2)), Integer.parseInt(list.get(pointer + 3)), Integer.parseInt(list.get(pointer + 4)), i);
			sklady.add(sklad);
			pointer = pointer + 5;
		}
		//zkusebni vypis
		System.out.println("Sklady:");
		System.out.println(sklady.get(0).vypis());
		System.out.println("pointer se nachazi na pozici: "+pointer);


		//--------------------------OAZY--------------------------
		pocetOaz = Integer.parseInt(list.get(pointer));
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


		//--------------------------CESTY--------------------------
		pocetVrcholu = pocetOaz + pocetSkladu;
		pocetCest = Integer.parseInt(list.get(pointer));
		pointer++;
		ArrayList<Cesta> cesty = new ArrayList<Cesta>();
		for(int i = 1; i < pocetCest + 1; i++) {
			if(Integer.parseInt(list.get(pointer)) <= pocetSkladu && Integer.parseInt(list.get(pointer + 1)) > pocetSkladu) {
				Cesta cesta = new Cesta(sklady.get(Integer.parseInt(list.get(pointer)) - 1), oazy.get(Integer.parseInt(list.get(pointer + 1)) - pocetSkladu - 1));
				cesty.add(cesta);
				pointer = pointer + 2;
				//dodelat zbyle vetve!!!!
			} 
		}
		Cesty neco = new Cesty(cesty);
		neco.vypis();
		System.out.println("pointer se nachazi na pozici: "+pointer);
		//--------------------------VELBLOUDI--------------------------
		pocetVelbloudu = Integer.parseInt(list.get(pointer));
		pointer++;
		ArrayList<Velbloud> velbloudi = new ArrayList<Velbloud>();
		for(int i = 1; i < pocetVelbloudu + 1; i++) {
			Velbloud velbloud = new Velbloud(list.get(pointer), Double.parseDouble(list.get(pointer + 1)), Double.parseDouble(list.get(pointer + 2)), Integer.parseInt(list.get(pointer + 3)),
					Integer.parseInt(list.get(pointer + 4)), Integer.parseInt(list.get(pointer + 5)), Integer.parseInt(list.get(pointer + 6)), Double.parseDouble(list.get(pointer + 7)));
			velbloudi.add(velbloud);
			velbloud.vypis();
			pointer = pointer + 8;
		}
		System.out.println("pointer se nachazi na pozici: "+pointer);
		//--------------------------POZADAVKY--------------------------
		pocetPozadavku = Integer.parseInt(list.get(pointer));
		pointer++;
		ArrayList<Pozadavek> pozadavky = new ArrayList<Pozadavek>();
		for(int i = 1; i < pocetPozadavku + 1; i++) {
			Pozadavek pozadavek = new Pozadavek(Integer.parseInt(list.get(pointer)), Integer.parseInt(list.get(pointer + 1)),
					Integer.parseInt(list.get(pointer + 2)), Integer.parseInt(list.get(pointer + 3)), i);
			pozadavky.add(pozadavek);
			pozadavek.vypis();
			pointer = pointer + 4;
		}	
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
		return prevedeny;
	}
}
