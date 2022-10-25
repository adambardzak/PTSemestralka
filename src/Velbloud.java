
public class Velbloud {
	int dmin,dmax,td,kd;
	double vmin,vmax, pd;
	String jmeno;
	public Velbloud(String jmeno,double vmin, double vmax, int dmin,int dmax,int td,int kd,double pd) {
		this.jmeno = jmeno;
		this.vmin = vmin;
		this.vmax = vmax;
		this.dmin = dmin;
		this.dmax = dmax;
		this.td = td; //doba piti
		this.kd = kd; //maximalni zatizeni
		this.pd = pd; //procentualni zastoupeni druhu
	}
	
	public void vypis() {
		System.out.println("Druh: "+jmeno+", min rychlost: "+vmin+", max rychlost: "+vmax+", min vzdalenost: "+dmin+", max vzdalenost: "+dmax);
	}
}
