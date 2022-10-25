import java.util.ArrayList;

public class Cesty {
	Graf graf;
	ArrayList<Cesta> cesty;
	
	public Cesty(ArrayList<Cesta> cesty) {
		this.cesty = cesty;
		this.graf = new Graf(Simulace.pocetVrcholu);
		for(int i = 0; i < cesty.size(); i++) {
			graf.pridejHranu(cesty.get(i).sklad.cs, cesty.get(i).oaza.co + Simulace.pocetSkladu, cesty.get(i).vzdalenost);
		}
	}
	
	public void vypis() {
		this.graf.vypisMatici();
	}
}
