import java.util.ArrayList;
import java.util.Random;


public class Sklad {
	int xs,ys,ts,ks,tn,cs;
	ArrayList<Velbloud> velbloudi = new ArrayList<Velbloud>();
	//nejakje parametr jak dlouho uz jsou velbloudi ptyc
	int pocetKosu = ks;
	private static int pocet = 0;
	public final int INDEX = ++pocet;
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
	public int nalozVelblouda(Velbloud velbloud, int pozadovaneKose, int dobaN) {
		//cas??
		if(this.pocetKosu < pozadovaneKose) {
			return 0;
		} else {
			velbloud.pocetKosu = pozadovaneKose;
			this.pocetKosu = this.pocetKosu - pozadovaneKose;
			return 1;
		}
	}
	
	public void generujVelbloudy(ArrayList<Oaza> oazy) {
		double nejmensiZastoupeni = 2.0; //protoze to bude vzdycky desetinne cislo
		
		/*
		 * tady se zjistuje druh velblouda ktery se ma generovat nejminkrat
		 */
		for(int i = 0; i < Simulace.druhyVelbloudu.size(); i++) {
			if(Simulace.druhyVelbloudu.get(i).pd < nejmensiZastoupeni) {
				nejmensiZastoupeni = Simulace.druhyVelbloudu.get(i).pd;
			}
		}
		
		/*
		 * nejdriv se zjisti, kolikrat se nejmensi zastoupeni musi vynasobit, aby to nebylo des. cislo,
		 * pak se stejnekrat vynasobi zastoupeni vsech ostatnich druhu a najde se jejich nejmensi spolecny delitel,
		 * abychom jich generovali zatim co nejmene
		 */
		int pocetNasobeni = 0;
		while((nejmensiZastoupeni) < 1) {
			nejmensiZastoupeni = nejmensiZastoupeni * 10;
			pocetNasobeni++;
		}
		ArrayList<Double> pocetGenerovanych = new ArrayList<Double>();
		for(int i = 0; i < Simulace.druhyVelbloudu.size(); i++) {
			pocetGenerovanych.add(Simulace.druhyVelbloudu.get(i).pd * 10 * pocetNasobeni);
		}
		
		//je funkcni zatim jen pro  2 druhy, musime dodelat
		double delitel = nejmensiSpolecnyDelitel(pocetGenerovanych.get(0), pocetGenerovanych.get(1));
		
		
		for(int i = 0; i < pocetGenerovanych.size(); i++) {
			int pocetVelbloudu = (int) (pocetGenerovanych.get(i) / delitel);
			for(int j = 0; j < pocetVelbloudu; j++) {
				Velbloud velbloud = new Velbloud(this, Simulace.druhyVelbloudu.get(i), oazy);
				this.velbloudi.add(velbloud);
			}
		}
		
		
	}
	
	static double nejmensiSpolecnyDelitel(double a, double b)
    {
        if (a == 0)
            return b;
        
        
        return nejmensiSpolecnyDelitel(b % a, a);
    }
    // method to calculate all common divisors
    // of two given numbers
    // a, b --> input integer numbers
    static double spolecnyDelitel(double a, double b)
    {
        // find gcd of a, b
        double n = nejmensiSpolecnyDelitel(a, b);
 
        // Count divisors of n.
        double result = 0;
        for (int i = 1; i <= Math.sqrt(n); i++) {
            // if 'i' is factor of n
            if (n % i == 0) {
                // check if divisors are equal
                if (n / i == i)
                    result += 1;
                else
                    result += 2;
            }
        }
        return result;
    }
	//gettery nejaky na velbloudy a generator velbloudu, pokud tam nejsou, tak nejaky vygenerovat
}