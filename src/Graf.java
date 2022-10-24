
/*
 * implementace neorientovaneho grafu, reprezentovan matici sousednosti
 */
public class Graf {
	private boolean maticeSousednosti[][];
	private int pocetVrcholu;
	
	public Graf(int pocetVrcholu) {
		this.pocetVrcholu = pocetVrcholu;
		maticeSousednosti = new boolean[pocetVrcholu][pocetVrcholu];
	}
	
	//prida hranu mezi vrcholy i a j
	public void pridejHranu(int i, int j) {
		if(i >= 0 && i < pocetVrcholu && j > 0 && j < pocetVrcholu) {
			maticeSousednosti[i - 1][j - 1] = true;
			maticeSousednosti[j - 1][i - 1] = true;
		} else System.out.println("Snazis se pridat hranu a nektery z vrcholu neexistuje");
	}
	
	//odstrani hranu
	public void odstranHranu(int i, int j) {
		if(i >= 0 && i < pocetVrcholu && j > 0 && j < pocetVrcholu) {
			maticeSousednosti[i - 1][j - 1] = false;
			maticeSousednosti[j - 1][i - 1] = false;
		}
	}
	
	//zjisti, zda je hrana mezi vrcholy i a j
	public boolean jeHranaMezi(int i, int j) {
		if(i >= 0 && i < pocetVrcholu && j > 0 && j < pocetVrcholu) {
			return maticeSousednosti[i - 1][j - 1];
		} 
		else return false;
	}
	
	//vypise matici sousednosti prislusneho grafu
	public void vypisMatici() {
		System.out.println("Matice sousednosti: ");
		System.out.print("  ");
		for(int i = 0; i < pocetVrcholu; i++) {
			System.out.print( i + 1 + " ");
		}
		System.out.println();
		for(int i = 0; i < pocetVrcholu; i++) {
			System.out.print(i + 1 + " ");
			for(int j = 0; j < pocetVrcholu; j++) {
				if(maticeSousednosti[i][j] == true) System.out.print("1 ");
				else System.out.print("0 ");
			}
			System.out.println();
		}
	}
	
}
