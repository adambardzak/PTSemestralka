package pt_semestralka;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Simulace {
	//seznam vsech existujicich velbloudu
	static int casSimulace = 0;
	static ArrayList<Pozadavek> pozadavky = new ArrayList<Pozadavek>();
	static ArrayList<Sklad> sklady = new ArrayList<Sklad>();
	static ArrayList<DruhVelblouda> druhyVelbloudu = new ArrayList<DruhVelblouda>();
	static ArrayList<Cesta> cesty = new ArrayList<Cesta>();
	static ArrayList<Oaza> oazy = new ArrayList<Oaza>();
	public static int pocetOaz, pocetSkladu, pocetCest, pocetVrcholu, pocetVelbloudu, pocetPozadavku;
	public static void main(String args[]) throws FileNotFoundException {
		File file = new File("/Users/adambardzak/Desktop/tutorial.txt");
		vytvorEntity(toStringList(Parser.parse(file)));
		//cyklus co bude spoustet nekolik simulaci
		spustSimulaci();
	}
	
//	public void nastavCas(int kolikcasu) {
//		this.simulacniCas = simulacniCas + kolikcasu;
//	}
	/*
	 * @param treba kolik vygenerovanych velbloudu, nebo treba generovat kdyz budou velbloudi moc douho pryc
	 * , bylo by vhodne generovat alespon jednoho s nejnizsim zastoupenim
	 * 
	 * @param nasobek minimalniho mnozstvi velbloudu, divat se jak bude nejvyhodnejsi generovat velbloudy
	 * aby dosli v rozumnem case
	 * 
	 * 
	 * budem muset jeste pridat volbu ze ktereho skladu bude pozadavek vyrizen
	 * 
	 */
	public static void spustSimulaci() {
		ArrayList<Pozadavek> serazenePozadavky = new ArrayList<Pozadavek>();
		while(pozadavky.size() > 0) {
			//beru ty pozadavky co maji aktualni cas simulace
			for(int i = 0; i < pozadavky.size(); i++) {
				if(pozadavky.get(i).tp == casSimulace) serazenePozadavky.add(pozadavky.get(i));
			}
			//sem chci dat serazeni pozadavku aby se bral jako prvni ten s nejnizsim casem na splneni
			
		}
		
		
		//vzit pozadavky a ty s aktualnim casem simulace
		//nejaky cyklus pres vsechny sklady a v nem cyklus pres vsechny pozadavky pro kazdy sklad a  kdy se vzdy na konci zvysi cas
		//az se projdou vsechny sklady tak zvysit cas o 1
		//metoda posun cas
		//brat pozadavky dokud nejaky budou v listu
		//nekolik simulaci
		//ma kdo obslouzit pozadavky? pripadne vygenerovat
		//na konci kazdeho simulacniho cyklu velbloudy seradit podle napitosti
		
		/* 
		 * projdu vsechny sklady a dokud tam budou pozadavky tak si je budu zpracovavat podle toho jak je budu mit
		 * serazeny ve fronte, pokud nebudu mit velbloudy tak si je vygeneruju podle toho jaky druh generovani jsem 
		 * zrovna zvolil, na pozadavek vzdy idealne davam napiteho velblouda, pokud ho nemam, necham nejakeho napit (zas je
		 * roztridit podle nejvyssi mozny vzdalenosti) 
		 */
		
	}
	
	//metoda co bude kopirovat pocatecni stav entit, abychom nemuseli porad nacitat soubor dokola

	//prebira list s informacemi o entitach
	public static void vytvorEntity(ArrayList<String> list) {
		int pointer = 0; //pomocne ukazovatko, ukazuje kde se zrovna nachazime v listu


		//--------------------------SKLADY--------------------------
		pocetSkladu = Integer.parseInt(list.get(pointer));
		pointer++;
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
		for(int i = 1; i < pocetVelbloudu + 1; i++) {
			DruhVelblouda druhVelblouda = new DruhVelblouda(list.get(pointer), Double.parseDouble(list.get(pointer + 1)), Double.parseDouble(list.get(pointer + 2)), Integer.parseInt(list.get(pointer + 3)),
					Integer.parseInt(list.get(pointer + 4)), Integer.parseInt(list.get(pointer + 5)), Integer.parseInt(list.get(pointer + 6)), Double.parseDouble(list.get(pointer + 7)));
			druhyVelbloudu.add(druhVelblouda);
			druhVelblouda.vypis();
			pointer = pointer + 8;
		}
		System.out.println("pointer se nachazi na pozici: "+pointer);
		//--------------------------POZADAVKY--------------------------
		pocetPozadavku = Integer.parseInt(list.get(pointer));
		pointer++;
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
