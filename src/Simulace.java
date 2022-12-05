
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Simulace {
	public static final double ZMENA_CASU = 0.001;
	public static final double EPSILON = 0.01;
	static double casSimulace = 0;
	static List<Pozadavek> pozadavky = new ArrayList<Pozadavek>();
	static List<Sklad> sklady = new ArrayList<Sklad>();
	static List<DruhVelblouda> druhyVelbloudu = new ArrayList<DruhVelblouda>();
	static List<Cesta> cesty = new ArrayList<Cesta>();
	static List<Oaza> oazy = new ArrayList<Oaza>();
	public static int pocetOaz, pocetSkladu, pocetCest, pocetVrcholu, pocetVelbloudu, pocetPozadavku;
	static EntityGraphMap entityGraphMap;
	public static void main(String args[]) throws FileNotFoundException {
		//File file = new File("./src/parser.txt");
		//File file = new File("./src/tutorial.txt");
		//File file = new File("./src/weird_small.txt");
		//File file = new File("./src/weird_medium.txt");
		//File file = new File("./src/weird_large.txt");
		//File file = new File("./src/centre_small.txt");
		File file = new File("./src/dense_small.txt");
		ArrayList<Character> parsedFile = Parser.parse(file);
		if (parsedFile.size() > 0) {
			vytvorEntity(toStringList(parsedFile));		
			
			//Dijkstra.test();	
			//entityGraphMap.getShortedPathTest();	
			//System.out.println(entityGraphMap);
			
			spustSimulaci();
		} else {
			System.out.println("Soubor "+ file.getName() +" se neda naparsovat");
		}
		
	}	

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

	public static int nejblizsiSklad(int indexOazy) {
		if (indexOazy == 2) {
			//System.out.println("aaaa");
		}
		double nejkratsiCesta = Double.MAX_VALUE;
		int indexNejblizsiSklad = -1;
		for(int i = 0; i < sklady.size(); i++) {
			for(int j = 0; j < cesty.size(); j++) {
				if(cesty.get(j).misto1 instanceof Sklad) {
					if(cesty.get(j).misto2 instanceof Oaza && cesty.get(j).misto2.equals(oazy.get(indexOazy-1))) {
						if(nejkratsiCesta > cesty.get(j).vzdalenost) {
							nejkratsiCesta = cesty.get(j).vzdalenost;
							indexNejblizsiSklad = i;
						}
					}
				}
				if(cesty.get(j).misto1 instanceof Oaza && cesty.get(j).misto1.equals(oazy.get(indexOazy-1))) {
					if(cesty.get(j).misto2 instanceof Sklad) {
						if(nejkratsiCesta > cesty.get(j).vzdalenost) {
							nejkratsiCesta = cesty.get(j).vzdalenost;
							indexNejblizsiSklad = i;
						}
					}
				}
			}
		}
		return indexNejblizsiSklad;
	}
	public static void spustSimulaci() {

		int pocetVyrizenychKosu = 0;
		int pocetObslouzenychPozadavku = 0;
		boolean simulaceBezi = true;
		while(pozadavky.size() > 0 && simulaceBezi) {			
			ArrayList<Pozadavek> pozadavkyTed = new ArrayList<Pozadavek>();
			for(int i = 0; i < pozadavky.size(); i++) {
				if(pozadavky.get(i).tz <= casSimulace && pozadavky.get(i).vyrizeny == false) pozadavkyTed.add(pozadavky.get(i)); // musi se zpracovat i pozadavky starsi, ktere tam visi
			}
			int indexPozadavku = 0;
			while(pozadavkyTed.size() > indexPozadavku && simulaceBezi) {
				//beru ty pozadavky co maji aktualni cas simulace
				
				Sklad nejblizsiSklad = entityGraphMap.getSkladToOaza(oazy.get(pozadavkyTed.get(indexPozadavku).op-1));
				if(nejblizsiSklad == null) {
					System.out.println("Cas: "+casSimulace+", nelze obslouzit Oazu: "+ pozadavkyTed.get(indexPozadavku).op +", neexistuje cesta");
					pozadavkyTed.get(indexPozadavku).vyrizeny = true;
					indexPozadavku++;
					continue;
				}
				int indexNejblizsiSklad = nejblizsiSklad.INDEX - 1;
				if (sklady.get(indexNejblizsiSklad).velbloudi.size() == 0) {
					sklady.get(indexNejblizsiSklad).generujVelbloudy(oazy, entityGraphMap);
				}				
				if(sklady.get(indexNejblizsiSklad).velbloudi.size() > 0) { //pokud jsou ve skladu nejaci velbloudi, vem toho prvniho
					for(int i = 0; i < sklady.get(indexNejblizsiSklad).velbloudi.size(); i++) {
						if((sklady.get(indexNejblizsiSklad).velbloudi.get(i).zvladneNaklad(pozadavkyTed.get(indexPozadavku)) &&
								sklady.get(indexNejblizsiSklad).velbloudi.get(i).zvladneCestu(pozadavkyTed.get(indexPozadavku),casSimulace)) &&
								sklady.get(indexNejblizsiSklad).velbloudi.get(i).stav == Velbloud.Stav.CEKA) {
							sklady.get(indexNejblizsiSklad).velbloudi.get(i).nakladej(pozadavkyTed.get(indexPozadavku),casSimulace);
							pozadavkyTed.get(indexPozadavku).vyrizeny = true;

						}
					}
				}
				//nakonec kontrola co se bude se vsema velbloudama dit v dalsim case
				for(int i = 0; i < sklady.get(indexNejblizsiSklad).velbloudi.size(); i++) {
					sklady.get(indexNejblizsiSklad).velbloudi.get(i).zkontrolujSvujStav(casSimulace);
				}
				indexPozadavku++;
			}
			for(int i = 0; i < pozadavkyTed.size(); i++) {
				if(casSimulace > (pozadavkyTed.get(i).tp + pozadavkyTed.get(i).tz)) {
					System.out.println("Cas: "+casSimulace+", Oaza: "+pozadavkyTed.get(i).op+", Vsichni vymreli, Harpagon zkrachoval, Konec simulace\n"
							+ "");
					simulaceBezi = false;
					break;
				}
			}
			boolean jeTamNevyrizeny = false;
			for(int i = 0; i < pozadavky.size(); i++) {
				if(pozadavky.get(i).vyrizeny == false) {
					jeTamNevyrizeny = true;
					break;
				}
			}
			if(jeTamNevyrizeny) {
				casSimulace = casSimulace + ZMENA_CASU;
				//System.out.println("Cas: " + casSimulace);
				sklady.get(0).doplnSklad(casSimulace);
				for(int j = 0; j < sklady.size(); j++) {
					for(int i = 0; i < sklady.get(j).velbloudi.size(); i++) {
						sklady.get(j).velbloudi.get(i).zkontrolujSvujStav(casSimulace);
					}
				}
			} else {
				System.out.println("Simulace skoncila uspesne");
				break;
			}

		}
		for(int i = 0; i < pozadavky.size(); i++) {
			if(pozadavky.get(i).vyrizeny == true) {
				pocetObslouzenychPozadavku++;
				pocetVyrizenychKosu = pocetVyrizenychKosu + pozadavky.get(i).kp;
			}
		}

		System.out.println("Pocet vyrizenych pozadavku: "+pocetObslouzenychPozadavku+", pocet donesenych kosu: "+pocetVyrizenychKosu);
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
			Sklad sklad = new Sklad(i, Double.parseDouble(list.get(pointer)), Double.parseDouble(list.get(pointer + 1)),
					Integer.parseInt(list.get(pointer + 2)), Integer.parseInt(list.get(pointer + 3)), Integer.parseInt(list.get(pointer + 4)), i);
			sklady.add(sklad);
			pointer = pointer + 5;
		}
		//zkusebni vypis
		//		System.out.println("Sklady:");
		//		System.out.println(sklady.get(0).vypis());
		//		System.out.println("pointer se nachazi na pozici: "+pointer);

		//--------------------------OAZY--------------------------
		pocetOaz = Integer.parseInt(list.get(pointer));
		pointer++;
		//System.out.println("Oazy: ");
		for(int i = 1; i < pocetOaz + 1; i++) {
			Oaza oaza = new Oaza(i + pocetSkladu,Double.parseDouble(list.get(pointer)), Double.parseDouble(list.get(pointer + 1)), pocetOaz, i);
			oazy.add(oaza);
			//System.out.println(oaza.vypis());
			pointer = pointer + 2;
		}
		//		System.out.println("pointer se nachazi na pozici: "+pointer);

		//--------------------------CESTY--------------------------
		pocetVrcholu = pocetOaz + pocetSkladu;
		pocetCest = Integer.parseInt(list.get(pointer));
		pointer++;
		int pocetVseho = pocetSkladu + pocetOaz;
		for(int i = 1; i < pocetCest + 1; i++) {
			try {
		//	if(Integer.parseInt(list.get(pointer)) <= pocetVseho && Integer.parseInt(list.get(pointer + 1)) > pocetSkladu) {
				Oaza oaza1 = null;
				Oaza oaza2 = null;
				Sklad sklad1 = null;
				Sklad sklad2 = null;
				if(sklady.size() > (Integer.parseInt(list.get(pointer)) - 1)) {
					sklad1 = sklady.get(Integer.parseInt(list.get(pointer)) - 1);
				} else {
					oaza1 = oazy.get(Integer.parseInt(list.get(pointer)) - pocetSkladu - 1);
				}
				if(sklady.size() <= (Integer.parseInt(list.get(pointer + 1)) - 1)) {
					oaza2 = oazy.get(Integer.parseInt(list.get(pointer + 1)) - pocetSkladu - 1);
				} else {
					sklad2 = sklady.get(Integer.parseInt(list.get(pointer + 1)) - 1);
				}
				Cesta cesta = null;
				if(sklad1 != null && sklad2 != null) {
					cesta = new Cesta(sklad1, sklad2);
				} else if(sklad1 != null && oaza2 != null) {
					cesta = new Cesta(sklad1, oaza2);
				} else if(oaza1 != null && oaza2 != null) {
					cesta = new Cesta(oaza1, oaza2);
				} else if(oaza1 != null && sklad2 != null){
					cesta = new Cesta(oaza1, sklad2);
				}
				cesty.add(cesta);
				pointer = pointer + 2;
			//}
			} catch (Exception e) {
				System.out.println("CHyba");
			} 			
		}
		
		entityGraphMap = new EntityGraphMap(sklady,oazy,cesty); //vytvoreni grafu cest

		//--------------------------VELBLOUDI--------------------------
		pocetVelbloudu = Integer.parseInt(list.get(pointer));
		pointer++;
		for(int i = 1; i < pocetVelbloudu + 1; i++) {
			DruhVelblouda druhVelblouda = new DruhVelblouda(list.get(pointer), Double.parseDouble(list.get(pointer + 1)), Double.parseDouble(list.get(pointer + 2)), Double.parseDouble(list.get(pointer + 3)),
					Double.parseDouble(list.get(pointer + 4)), Double.parseDouble(list.get(pointer + 5)), Double.parseDouble(list.get(pointer + 6)), Double.parseDouble(list.get(pointer + 7)));
			druhyVelbloudu.add(druhVelblouda);
			druhVelblouda.vypis();
			pointer = pointer + 8;
		}
		//System.out.println("pointer se nachazi na pozici: "+pointer);

		//--------------------------POZADAVKY--------------------------
		pocetPozadavku = Integer.parseInt(list.get(pointer));
		pointer++;
		for(int i = 1; i < pocetPozadavku + 1; i++) {
			Pozadavek pozadavek = new Pozadavek(Double.parseDouble(list.get(pointer)), Integer.parseInt(list.get(pointer + 1)),
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

		int i = 0;
		while(list.size() > i) {

			if(!Character.isWhitespace(list.get(i)) && list.get(i) != ' ' && !Character.isSpaceChar(list.get(i))) {

				StringBuilder sb = new StringBuilder();
				while((list.size() > i) && (!(Character.isWhitespace(list.get(i)) || list.get(i) == ' ' || Character.isSpaceChar(list.get(i))))) {

					sb.append(list.get(i));
					i++;
				}

				prevedeny.add(sb.toString());
			}
			i++;
		}
		return prevedeny;
	}	
	
}
