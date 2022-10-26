
public class Velbloud {
	
	public enum Poloha { CESTA, SKLAD, OAZA }
	public enum Stav { CEKA, PIJE, JDE, NAKLADA, VYKLADA }
	
	Poloha poloha = Poloha.SKLAD;
	Stav stav;
	Stav dalsiStav;
	int casDalsiZmenyStavu;
	int casPosledniZmenyStavu;
	int pocetKosu = 0;
	Sklad materskySklad;
	DruhVelblouda druh;
	Pozadavek pozadavek;
	boolean piti = true;
	
	
	public Velbloud(Stav stav, Stav dalsiStav, int casPosledniZmenyStavu, int casDalsiZmenyStavu, Sklad materskySklad,
			DruhVelblouda druh) {
		this.stav = stav;
		this.dalsiStav = dalsiStav;
		this.casDalsiZmenyStavu = casDalsiZmenyStavu;
		this.casPosledniZmenyStavu = casPosledniZmenyStavu;
		this.materskySklad = materskySklad;
		this.druh = druh;
	}
	
	public void vypis() {
		System.out.println("Stav: "+stav+", Nasledujici stav: "+dalsiStav+", Stav naposledy zmenen: "+casPosledniZmenyStavu+", Stav bude zmenen v: "+casDalsiZmenyStavu+", druh velblouda: "+ druh.jmeno);
	}
	
	//tohle doplnit!
	public boolean zvladneCestu(Double vzdalenost) {
		if(jeNapity()) return true;
		else return false;
	}
	
	//dodelat!
	public boolean jeNapity() {
		return piti;
	}

	
	public void vemPozadavek(Pozadavek predavanyPozadavek) {
		this.pozadavek = predavanyPozadavek;
		this.stav = dalsiStav;
		this.dalsiStav = Stav.JDE;
		//vypis
	}
	

	
	//zmenStav parametr kdy stav skonci a co bude delat pak, 
	
}