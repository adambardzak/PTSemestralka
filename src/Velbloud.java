import java.util.ArrayList;
import java.util.Random;

public class Velbloud {

	public enum Stav { VRATIL_SE_DO_SKLADU, CEKA, PIJE_V_OAZE, PIJE_VE_SKLADU, JDE_DO_SKLADU, JDE_DO_OAZY, NAKLADA, VYKLADA }

	private static int pocet = 0;
	public final int INDEX = ++pocet;
	ArrayList<Oaza> oazy;
	Stav stav = Stav.CEKA;
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
	double rychlost;


	public Velbloud(Sklad materskySklad,
			DruhVelblouda druh, ArrayList<Oaza> oazy) {
		Random random = new Random(System.currentTimeMillis());
		this.materskySklad = materskySklad;
		this.druh = druh;
		double a = random.nextGaussian();
		while(Math.abs(a) > 1) {
			a = random.nextGaussian();
		}
		this.maxNaJednoNapiti = (this.druh.dmin + this.druh.dmax)/2 + ((this.druh.dmax + this.druh.dmin)/4) * a;
		//stredni hodnota = dmin + dmax)/2, rozptyl = smerodatna odchylka na druhou, smerodatna odchylka =(dmax-dmin)/4
		this.napityNaDelku = maxNaJednoNapiti;
		this.rychlost = (random.nextDouble() * (this.druh.vmax - this.druh.vmin)) + this.druh.vmin;
		this.oazy = oazy;
		System.out.println("Velbloud cislo :"+INDEX+", Rychlost: "+rychlost+", Na jedno napiti ujde: "+maxNaJednoNapiti);

	}

	//tohle doplnit!
	public boolean zvladneCestu(Pozadavek pozadavek, int casSimulace) {
		Cesta cesta = new Cesta(this.materskySklad, oazy.get(pozadavek.op - 1));
		if(napityNaDelku >= cesta.vzdalenost) return true;
		else if(napityNaDelku < maxNaJednoNapiti){
			pijVeSkladu(casSimulace);
		} 
		return false;
	}

	//dodelat!
	public boolean jeNapity() {
		return piti;
	}

	public void pijVeSkladu(int casSimulace) {
		this.stav = Stav.PIJE_VE_SKLADU;
		this.dalsiStav = Stav.CEKA;
		this.casDalsiZmenyStavu = casSimulace + this.druh.td;
		System.out.println("Cas: "+casSimulace+", Velbloud: "+INDEX+", Sklad: "+materskySklad.INDEX+", Ziznivy "+druh.jmeno+", Pokracovani mozne v: "+this.casDalsiZmenyStavu);

	}

	public void pijVOaze(int casSimulace) {
		this.stav = Stav.PIJE_V_OAZE;
		this.dalsiStav = Stav.JDE_DO_SKLADU;
		this.casDalsiZmenyStavu = casSimulace + this.druh.td;
		System.out.println("Cas: "+casSimulace+", Velbloud: "+INDEX+", Oaza: "+pozadavek.op+", Ziznivy "+druh.jmeno+", Pokracovani mozne v: "+this.casDalsiZmenyStavu);
	}


	public void jdi(int casSimulace) {
		this.stav = Stav.JDE_DO_OAZY;
		this.dalsiStav = Stav.VYKLADA;
		Cesta cesta = new Cesta(this.materskySklad, oazy.get(pozadavek.op - 1));
		this.casDalsiZmenyStavu = casSimulace + (int)(cesta.vzdalenost/rychlost);

	}

	public boolean zvladneNaklad(Pozadavek predavanyPozadavek) {
		if(predavanyPozadavek.kp <= this.druh.kd) return true;
		else return false;
	}


	public void nakladej(Pozadavek predavanyPozadavek, int casSimulace) {
		this.pozadavek = predavanyPozadavek;
		this.stav = Stav.NAKLADA;
		this.dalsiStav = Stav.JDE_DO_OAZY;
		this.pocetKosu = predavanyPozadavek.kp;
		this.casDalsiZmenyStavu = casSimulace + (predavanyPozadavek.kp * this.materskySklad.tn); //pocet kosu z pozadavku krat doba nalozeni jednoho kose
		System.out.println("Cas: "+casSimulace+", Velbloud: "+INDEX+", Sklad: "+materskySklad.INDEX+", Nalozeno kosu: "+pocetKosu+
				", Odchod v: "+this.casDalsiZmenyStavu);
		//vypis
	}

	public void vykladej(int casSimulace) {
		this.stav = Stav.VYKLADA;
		if(zvladneCestu(pozadavek, casSimulace)) { //zatim je cesta stejna, pak by mohl zvladnout obslouzit vic oaz a vracet se jinudy
			this.dalsiStav = Stav.JDE_DO_SKLADU;
		} else {
			this.dalsiStav = Stav.PIJE_V_OAZE;	
		}
		casDalsiZmenyStavu = casSimulace + (pozadavek.kp * this.materskySklad.tn);
		System.out.println("Cas: "+casSimulace+", Velbloud: "+INDEX+", Oaza: "+pozadavek.op+", Vylozeno kosu: "+pozadavek.kp+""
				+ ", Vylozeno v: "+this.casDalsiZmenyStavu+", Casova rezerva: "+(pozadavek.tp - casSimulace + pozadavek.tz));
	}

	public void jdiDoSkladu(int casSimulace) {
		this.stav = Stav.JDE_DO_SKLADU;
		this.dalsiStav = Stav.VRATIL_SE_DO_SKLADU;
		Cesta cesta = new Cesta(this.materskySklad, oazy.get(pozadavek.op - 1));
		this.casDalsiZmenyStavu = casSimulace + (int)(cesta.vzdalenost/rychlost);
	}

	public void cekej(int casSimulace) {
		this.casDalsiZmenyStavu++;
		this.stav = Stav.CEKA;
		System.out.println();
	}

	public void vratilJsemSeDoSkladu(int casSimulace) {
		System.out.println("Cas: "+casSimulace+", Velbloud: "+INDEX+", Navrat do skladu: "+materskySklad.INDEX);
		cekej(casSimulace);
	}

	public void zkontrolujSvujStav(int casSimulace) {
		if(casSimulace == 0) return;
		if(this.casDalsiZmenyStavu == casSimulace) {
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



	//zmenStav parametr kdy stav skonci a co bude delat pak, 

}