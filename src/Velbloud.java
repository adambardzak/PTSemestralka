import java.util.ArrayList;
import java.util.Random;

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
	double maxNaJednoNapiti;
	double napityNaDelku;
	
	
	public Velbloud(Stav stav, Stav dalsiStav, int casPosledniZmenyStavu, int casDalsiZmenyStavu, Sklad materskySklad,
			DruhVelblouda druh, Random random) {
		this.stav = stav;
		this.dalsiStav = dalsiStav;
		this.casDalsiZmenyStavu = casDalsiZmenyStavu;
		this.casPosledniZmenyStavu = casPosledniZmenyStavu;
		this.materskySklad = materskySklad;
		this.druh = druh;
		this.maxNaJednoNapiti = (this.druh.dmin + this.druh.dmax)/2 + Math.pow(((this.druh.dmax + this.druh.dmin)/4) , 2) * random.nextGaussian(); //stredni hodnota = dmin + dmax)/2, rozptyl = smerodatna odchylka na druhou, smerodatna odchylka =(dmax-dmin)/4
		this.napityNaDelku = maxNaJednoNapiti;
	}
	
	public void vypis() {
		System.out.println("Stav: "+stav+", Nasledujici stav: "+dalsiStav+", Stav naposledy zmenen: "+casPosledniZmenyStavu+", Stav bude zmenen v: "+casDalsiZmenyStavu+", druh velblouda: "+ druh.jmeno);
	}
	
	//tohle doplnit!
	public boolean zvladneCestu(Pozadavek pozadavek, int casSimulace, ArrayList<Oaza> oazy) {
		Cesta cesta = new Cesta(this.materskySklad, oazy.get(pozadavek.op));
		if(napityNaDelku >= cesta.vzdalenost) return true;
		else {
			pij(casSimulace);
			return false;
		}
	}
	
	//dodelat!
	public boolean jeNapity() {
		return piti;
	}
	
	public void pij(int casSimulace) {
		this.stav = Stav.PIJE;
		this.dalsiStav = Stav.CEKA;
		this.casDalsiZmenyStavu = casSimulace + this.druh.td;
		
	}
	
	public boolean zvladneNaklad(Pozadavek predavanyPozadavek) {
		if(predavanyPozadavek.kp <= this.druh.kd) return true;
		else return false;
	}

	
	public void vemPozadavek(Pozadavek predavanyPozadavek, int casSimulace) {
		this.pozadavek = predavanyPozadavek;
		this.stav = Stav.NAKLADA;
		this.dalsiStav = Stav.JDE;
		this.casDalsiZmenyStavu = casSimulace + (predavanyPozadavek.kp * this.materskySklad.tn); //pocet kosu z pozadavku krat doba nalozeni jednoho kose
		//vypis
	}
	

	
	//zmenStav parametr kdy stav skonci a co bude delat pak, 
	
}