
public class DruhVelblouda {
	int x, y;
	int dmin,dmax,td,kd;
	int pocetKosu = 0;
	double vmin,vmax, pd;
	String jmeno;
	public DruhVelblouda(String jmeno,double vmin, double vmax, int dmin,int dmax,int td,int kd,double pd) {
		this.jmeno = jmeno;
		this.vmin = vmin; //minimalni rychlost - rychlost bude konstantni a bude generovana pomoci rovnomerneho rozdeleni
		this.vmax = vmax; //maximalni rychlost
		this.dmin = dmin; //minimalni vzdalenost na jedno napiti
		this.dmax = dmax;
		this.td = td; //doba piti
		this.kd = kd; //maximalni zatizeni
		this.pd = pd; //procentualni zastoupeni druhu
	}
	
	public void vypis() {
		System.out.println("Druh: "+jmeno+", min rychlost: "+vmin+", max rychlost: "+vmax+", min vzdalenost: "+dmin+", max vzdalenost: "+dmax);
	}
	
	public int[] getSouradnice() {
		int[] pole = new int[2];
		pole[0] = this.x;
		pole[1] = this.y;
		return pole;		
	}
	
	public void setSouradnice(int a, int b) {
		this.x = a;
		this.y = b;
	}
}
