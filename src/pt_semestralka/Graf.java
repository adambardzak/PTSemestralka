package pt_semestralka;

/*
 * implementace neorientovaneho ohodnoceneho grafu, reprezentovan matici sousednosti
 */
public class Graf {
	private double maticeSousednosti[][];
	private int pocetVrcholu;
	
	public Graf(int pocetVrcholu) {
		this.pocetVrcholu = pocetVrcholu;
		maticeSousednosti = new double[pocetVrcholu][pocetVrcholu];
	}
	
	//prida hranu mezi vrcholy i a j
	public void pridejHranu(int i, int j, double vzdalenost) {
		if(i >= 0 && i <= pocetVrcholu && j > 0 && j <= pocetVrcholu) {
			maticeSousednosti[i - 1][j - 1] = vzdalenost;
			maticeSousednosti[j - 1][i - 1] = vzdalenost;
		} else System.out.println("Chyba: Snazis se pridat hranu a nektery z vrcholu neexistuje!");
	}
	
	//odstrani hranu
	public void odstranHranu(int i, int j) {
		if(i >= 0 && i <= pocetVrcholu && j > 0 && j <= pocetVrcholu) {
			maticeSousednosti[i - 1][j - 1] = 0;
			maticeSousednosti[j - 1][i - 1] = 0;
		}
	}
	
	//zjisti, zda je hrana mezi vrcholy i a j
	public boolean jeHranaMezi(int i, int j) {
		if(i >= 0 && i < pocetVrcholu && j > 0 && j < pocetVrcholu) {
			return maticeSousednosti[i - 1][j - 1] > 0;
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
				if(maticeSousednosti[i][j] != 0) System.out.print(maticeSousednosti[i][j]+" ");
				else System.out.print("0 ");
			}
			System.out.println();
		}
	}
	
//	public int getMinimumVertex(boolean [] mst, int [] key){
//		 int minKey = Integer.MAX_VALUE;
//		 int vertex = –1;
//		 for (int i = 0; i <vertices ; i++) {
//		 if(mst[i]==false && minKey>key[i]){
//		 minKey = key[i];
//		 vertex = i;
//		 }
//		 }
//		 return vertex;
//		 }
	
}
