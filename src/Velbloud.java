import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Velbloud {

	public enum Stav { VRATIL_SE_DO_SKLADU, CEKA, PIJE_V_OAZE, PIJE_VE_SKLADU, JDE_DO_SKLADU, JDE_DO_OAZY, NAKLADA, VYKLADA }

	private static int pocet = 0;
	public final int INDEX = ++pocet;
	List<Oaza> oazy;
	Stav stav = Stav.CEKA;
	Stav dalsiStav;
	double casDalsiZmenyStavu;
	double casPosledniZmenyStavu;
	int pocetKosu = 0;
	Sklad materskySklad;
	DruhVelblouda druh;
	Pozadavek pozadavek;
	boolean piti = true;
	double maxNaJednoNapiti;
	double napityNaDelku;
	double rychlost;
	EntityGraphMap entityGraphMap;


	public Velbloud(Sklad materskySklad,
			DruhVelblouda druh, List<Oaza> oazy2, EntityGraphMap entityGraphMap) {
		Random random = new Random(System.currentTimeMillis());
		this.materskySklad = materskySklad;
		this.druh = druh;
		double a = random.nextGaussian();
		while(Math.abs(a) > 1) {
			a = random.nextGaussian();
		}
		this.maxNaJednoNapiti = (this.druh.dmin + this.druh.dmax)/2 + ((this.druh.dmax + this.druh.dmin)/4) * a;
		this.napityNaDelku = maxNaJednoNapiti;
		this.rychlost = (random.nextDouble() * (this.druh.vmax - this.druh.vmin)) + this.druh.vmin;
		this.oazy = oazy2;
		this.entityGraphMap = entityGraphMap;

	}

	//tohle doplnit, pocita se s tim, ze je napity na celou cestu i pres vice oaz!
	public boolean zvladneCestu(Pozadavek pozadavek, double casSimulace) {
		Node shorted = entityGraphMap.getShortedPathToOaza(oazy.get(pozadavek.op - 1));	
		if(napityNaDelku >= shorted.getDistance()) return true;
		//Cesta cesta = new Cesta(this.materskySklad, oazy.get(pozadavek.op - 1));
		//if(napityNaDelku >= cesta.vzdalenost) return true;
		else if(napityNaDelku < maxNaJednoNapiti){
			pijVeSkladu(casSimulace);
		} 
		return false;
	}

	//dodelat!
	public boolean jeNapity() {
		return piti;
	}

	public void pijVeSkladu(double casSimulace) {
		this.stav = Stav.PIJE_VE_SKLADU;
		this.dalsiStav = Stav.CEKA;
		this.casDalsiZmenyStavu = casSimulace + this.druh.td;
		System.out.println("Cas: "+String.format("%.2f",casSimulace)+", Velbloud: "+INDEX+", Druh: "+this.druh.jmeno+", Sklad: "+materskySklad.INDEX+", Ziznivy "+druh.jmeno+", Pokracovani mozne v: "+this.casDalsiZmenyStavu);

	}

	public void pijVOaze(double casSimulace) {
		this.stav = Stav.PIJE_V_OAZE;
		this.dalsiStav = Stav.JDE_DO_SKLADU;
		this.casDalsiZmenyStavu = casSimulace + this.druh.td;
		System.out.println("Cas: "+String.format("%.2f",casSimulace)+", Velbloud: "+INDEX+", Druh: "+this.druh.jmeno+", Oaza: "+pozadavek.op+", Ziznivy "+druh.jmeno+", Pokracovani mozne v: "+this.casDalsiZmenyStavu);
	}


	public void jdi(double casSimulace) {
		this.stav = Stav.JDE_DO_OAZY;
		this.dalsiStav = Stav.VYKLADA;
		Node shorted = entityGraphMap.getShortedPathToOaza(oazy.get(pozadavek.op - 1));			
		this.casDalsiZmenyStavu = casSimulace + (int)(shorted.getDistance()/rychlost);		
		System.out.println("Cas: "+String.format("%.2f",casSimulace)+", Velbloud: "+INDEX+", Druh: "+this.druh.jmeno+", Sklad: "+materskySklad.INDEX+", Nalozeno kosu: "+pocetKosu+
				", Odchazi cestou: " + shorted.shortestPathInfo());

	}

	public boolean zvladneNaklad(Pozadavek predavanyPozadavek) {
		if(predavanyPozadavek.kp <= this.druh.kd) return true;
		else return false;
	}


	public void nakladej(Pozadavek predavanyPozadavek, double casSimulace) {
		this.pozadavek = predavanyPozadavek;
		this.stav = Stav.NAKLADA;
		this.dalsiStav = Stav.JDE_DO_OAZY;
		this.pocetKosu = predavanyPozadavek.kp;
		this.casDalsiZmenyStavu = casSimulace + (predavanyPozadavek.kp * this.materskySklad.tn); //pocet kosu z pozadavku krat doba nalozeni jednoho kose
		System.out.println("Cas: "+String.format("%.2f",casSimulace)+", Velbloud: "+INDEX+", Druh: "+this.druh.jmeno+", Sklad: "+materskySklad.INDEX+", Nakladano kosu: "+pocetKosu+
				", do Oazy "+ predavanyPozadavek.op +", Odchod v: "+String.format("%.2f",this.casDalsiZmenyStavu));
	}

	public void vykladej(double casSimulace) {
		this.stav = Stav.VYKLADA;
		if(zvladneCestu(pozadavek, casSimulace)) { //zatim je cesta stejna, pak by mohl zvladnout obslouzit vic oaz a vracet se jinudy
			this.dalsiStav = Stav.JDE_DO_SKLADU;
		} else {
			this.dalsiStav = Stav.PIJE_V_OAZE;	
		}
		casDalsiZmenyStavu = casSimulace + (pozadavek.kp * this.materskySklad.tn);
		System.out.println("Cas: "+String.format("%.2f",casSimulace)+", Velbloud: "+INDEX+", Druh: "+this.druh.jmeno+", Oaza: "+pozadavek.op+", Vylozeno kosu: "+pozadavek.kp+""
				+ ", Vylozeno v: "+String.format("%.2f",this.casDalsiZmenyStavu)+", Casova rezerva: "+(pozadavek.tp - casSimulace + pozadavek.tz));
	}

	public void jdiDoSkladu(double casSimulace) {
		this.stav = Stav.JDE_DO_SKLADU;
		this.dalsiStav = Stav.VRATIL_SE_DO_SKLADU;
		Node shorted = entityGraphMap.getShortedPathToOaza(oazy.get(pozadavek.op - 1));	
		//Cesta cesta = new Cesta(this.materskySklad, oazy.get(pozadavek.op - 1));
		//this.casDalsiZmenyStavu = casSimulace + (int)(cesta.vzdalenost/rychlost);
		this.casDalsiZmenyStavu = casSimulace + (int)(shorted.getDistance()/rychlost);
	}

	public void cekej(double casSimulace) {
		this.casDalsiZmenyStavu++;
		this.stav = Stav.CEKA;
		//System.out.println();
	}

	public void vratilJsemSeDoSkladu(double casSimulace) {
		System.out.println("Cas: "+String.format("%.2f",casSimulace)+", Velbloud: "+INDEX+", Druh: "+this.druh.jmeno+", Navrat do skladu: "+materskySklad.INDEX);
		cekej(casSimulace);
	}

	public void zkontrolujSvujStav(double casSimulace) {
		if(casSimulace == 0) return;
		if(Math.abs(this.casDalsiZmenyStavu - casSimulace) < (Simulace.EPSILON*10)) {
			if(dalsiStav != null) {
			switch(dalsiStav) {
				case JDE_DO_OAZY:
					jdi(casSimulace);
					break;
				case VYKLADA:
					vykladej(casSimulace);
					break;
				case NAKLADA:
					nakladej(pozadavek, casSimulace);
					break;
				case JDE_DO_SKLADU:
					jdiDoSkladu(casSimulace);
					break;
				case CEKA:
					cekej(casSimulace);
					break;
				case PIJE_V_OAZE:
					pijVOaze(casSimulace);
					break;
				case PIJE_VE_SKLADU:
					pijVeSkladu(casSimulace);
					break;
				case VRATIL_SE_DO_SKLADU:
					vratilJsemSeDoSkladu(casSimulace);
					break;
			}
			}
		}
	}

	@Override
	public String toString() {
		return "Velbloud [INDEX=" + INDEX + ", oazy=" + oazy + ", stav=" + stav + ", dalsiStav=" + dalsiStav
				+ ", casDalsiZmenyStavu=" + casDalsiZmenyStavu + ", casPosledniZmenyStavu=" + casPosledniZmenyStavu
				+ ", pocetKosu=" + pocetKosu + ", materskySklad=" + materskySklad + ", druh=" + druh + ", pozadavek="
				+ pozadavek + ", piti=" + piti + ", maxNaJednoNapiti=" + maxNaJednoNapiti + ", napityNaDelku="
				+ napityNaDelku + ", rychlost=" + rychlost + "]";
	}



	//zmenStav parametr kdy stav skonci a co bude delat pak, 

	
}