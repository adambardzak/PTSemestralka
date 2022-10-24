
public class Cesta {
	//
	Graf graf = new Graf(Simulace.pocetVrcholu);
	int cisloSkladu;
	int cisloOazy;
	
	public Cesta(int cisloSkladu, int cisloOazy) {
		this.cisloOazy = cisloOazy;
		this.cisloSkladu = cisloSkladu;
		graf.pridejHranu(cisloSkladu, cisloOazy);
		
	}
	
	public void vypis() {
		graf.vypisMatici();
	}
}