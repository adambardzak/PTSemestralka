package pt_semestralka;
import java.util.ArrayList;

public class Sklad {
	int xs,ys,ts,ks,tn,cs;
	ArrayList<Velbloud> velbloudi = new ArrayList<Velbloud>();
	//nejakje parametr jak dlouho uz jsou velbloudi ptyc
	int pocetKosu = ks;
	public Sklad(int xs,int ys,int ks,int ts,int tn,int cs) {
		this.xs = xs;
		this.ys = ys;
		this.ts = ts;
		this.ks = ks;
		this.tn = tn;
		this.cs = cs;
				
	}
	
	public String vypis() {
		String vypis = "souradnice: ["+xs+","+ys+"], pocet kosu: "+ks+", doba doplneni: "+ts+", doba nalozeni: "+tn+", sklad cislo: "+cs;
		return vypis;
	}
	
	/*
	 * pokud ma sklad dostatek kosu na splneni pozadavku, nalozi je na predaneho velblouda a snizi si zustatek a vrati 1
	 * pokud ma sklad malo kosu, vrati 0
	 */
	public int nalozVelblouda(DruhVelblouda velbloud, int pozadovaneKose, int dobaN) {
		//cas??
		if(this.pocetKosu < pozadovaneKose) {
			return 0;
		} else {
			velbloud.pocetKosu = pozadovaneKose;
			this.pocetKosu = this.pocetKosu - pozadovaneKose;
			return 1;
		}
	}
	
	//gettery nejaky na velbloudy a generator velbloudu, pokud tam nejsou, tak nejaky vygenerovat
}