
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

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
		Random random = new Random();
		File file = new File("./tutorial.txt");
		vytvorEntity(toStringList(Parser.parse(file)));
		//cyklus co bude spoustet nekolik simulaci
		spustSimulaci(random);
		sklady.get(0).generujVelbloudy(random);
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
	public static void spustSimulaci(Random random) {
		
		
		
		ArrayList<Pozadavek> pozadavkyTed = new ArrayList<Pozadavek>();
		// v tomhle whliu se vezme jeden velbloud a zada se mu jeden pozadavek
		while(pozadavky.size() > 0) {
			for(int i = 0; i < pozadavky.size(); i++) {
				if(pozadavky.get(i).tp <= casSimulace) pozadavkyTed.add(pozadavky.get(i)); // musi se zpracovat i pozadavky starsi, ktere tam visi
			}
			while(pozadavkyTed.size() > 0) {
				
			Pozadavek pozadavek = null;
			Velbloud velbloud = null;
			Sklad sklad = null;
			//beru ty pozadavky co maji aktualni cas simulace
	
			//sem chci dat serazeni pozadavku aby se bral jako prvni ten s nejnizsim casem na splneni
			//tady bude hledani nejblizsiho skladu z te oazy sklad = pozadavek.oaza.getNejblizsiSklad()
			if(sklady.get(0).velbloudi.size() > 0) { //pokud jsou ve skladu nejaci velbloudi, vem toho prvniho
				for(int i = 0; i < sklady.get(0).velbloudi.size(); i++) {
					if((sklady.get(0).velbloudi.get(i).zvladneNaklad(pozadavek) && sklady.get(0).velbloudi.get(i).zvladneCestu(pozadavek,casSimulace,oazy))) {
						velbloud.vemPozadavek(pozadavek,casSimulace);
					}
				}
			} else { //pokud ne, tak !ZJISTI, JESTLI VUBEC MUZES VYGENEROVAT NEJAKYHO CO TO ZVLADNE! a vygeneruj JE a pak vem toho prvniho, protoze ten urcite bude napity
				sklady.get(0).generujVelbloudy(random);
				velbloud = sklady.get(0).velbloudi.get(0);
				velbloud.vemPozadavek(pozadavek,casSimulace);
			}
			
			
			//nakonec kontrola co se bude se vsema velbloudama dit v dalsim case
			
			}
			casSimulace++;
			
		}

		//brat pozadavky dokud nejaky budou v listu
		//nekolik simulaci
		//na konci kazdeho simulacniho cyklu velbloudy seradit podle napitosti

		/* 
		 * projdu vsechny sklady a dokud tam budou pozadavky tak si je budu zpracovavat podle toho jak je budu mit
		 * serazeny ve fronte, pokud nebudu mit velbloudy tak si je vygeneruju podle toho jaky druh generovani jsem 
		 * zrovna zvolil, na pozadavek vzdy idealne davam napiteho velblouda, pokud ho nemam, necham nejakeho napit (zas je
		 * roztridit podle nejvyssi mozny vzdalenosti) 
		 */

	}

	//	public void generujKose() {
	//		for(int i = 0; i < sklady.size(); i++) {
	//			sklady.get(i).pocetKosu = sklady.get(i).pocetKosu + sklady.get(i).ks;
	//		}
	//	}


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
