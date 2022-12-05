/*
 * konstruktor druhu velblouda
 */
public class DruhVelblouda {
	double dmin, dmax, td, kd;
	double vmin, vmax, pd;
	String jmeno;
	public DruhVelblouda(String jmeno,double vmin, double vmax, double dmin,double dmax,double td,double kd,double pd) {
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

	@Override
	public String toString() {
		return "DruhVelblouda [dmin=" + dmin + ", dmax=" + dmax + ", td=" + td + ", kd=" + kd + ", vmin=" + vmin
				+ ", vmax=" + vmax + ", pd=" + pd + ", jmeno=" + jmeno + "]";
	}
	
	
	
}
