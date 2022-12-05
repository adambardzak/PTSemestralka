import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Sklad extends Misto{
//	double xs,ys;
	int ts,ks,tn,cs;
	ArrayList<Velbloud> velbloudi = new ArrayList<Velbloud>();
	//nejakje parametr jak dlouho uz jsou velbloudi ptyc
	int pocetKosu = ks;
	private static int pocet = 0;
	public final int INDEX = ++pocet;
	public Sklad(int ID, double xs,double ys,int ks,int ts,int tn,int cs) {
		super(ID ,xs, ys);
		this.ts = ts;
		this.ks = ks;
		this.tn = tn;
		this.cs = cs;
				
	}
	
	public String vypis() {
		String vypis = "souradnice: ["+sx+","+sy+"], pocet kosu: "+ks+", doba doplneni: "+ts+", doba nalozeni: "+tn+", sklad cislo: "+cs;
		return vypis;
	}

	public void doplnSklad(double casSimulace) {
		if(casSimulace > 0 && Math.abs(casSimulace % ts) < Simulace.EPSILON) {
			pocetKosu = pocetKosu + ts;
		}
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
	
	public void generujVelbloudy(List<Oaza> oazy, EntityGraphMap entityGraphMap) {
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
			pocetGenerovanych.add(Simulace.druhyVelbloudu.get(i).pd * Math.pow(10, pocetNasobeni));
		}
		long[] pole = new long[pocetGenerovanych.size()];
		for(int i = 0; i < pole.length; i++) {
			pole[i] = Double.valueOf(pocetGenerovanych.get(i)).longValue();
		}
		
		//je funkcni zatim jen pro  2 druhy, musime dodelat
		double delitel = gcd(pole);
		
		
		for(int i = 0; i < pocetGenerovanych.size(); i++) {
			int pocetVelbloudu = (int) (pocetGenerovanych.get(i) / delitel);
			for(int j = 0; j < pocetVelbloudu; j++) {
				Velbloud velbloud = new Velbloud(this, Simulace.druhyVelbloudu.get(i), oazy, entityGraphMap);
				this.velbloudi.add(velbloud);
			}
		}
		
		
	}

	private static long gcd(long a, long b)
	{
		while (b > 0)
		{
			long temp = b;
			b = a % b; // % is remainder
			a = temp;
		}
		return a;
	}

	private static long gcd(long[] input)
	{
		long result = input[0];
		for(int i = 1; i < input.length; i++) result = gcd(result, input[i]);
		return result;
	}
	//gettery nejaky na velbloudy a generator velbloudu, pokud tam nejsou, tak nejaky vygenerovat

	@Override
	public String toString() {
		return "Sklad [ts=" + ts + ", ks=" + ks + ", tn=" + tn + ", cs=" + cs + ", velbloudi=" + velbloudi
				+ ", pocetKosu=" + pocetKosu + ", INDEX=" + INDEX + ", sx=" + sx + ", sy=" + sy + ", ID=" + ID + "]";
	}
	
	
}