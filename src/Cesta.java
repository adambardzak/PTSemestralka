

public class Cesta {
	Sklad sklad;
	Oaza oaza;
	double vzdalenost;
	
	public Cesta(Sklad sklad, Oaza oaza) {
		this.oaza = oaza;
		this.sklad = sklad;
		vzdalenost = Math.sqrt(Math.pow((oaza.xo - sklad.xs), 2) + Math.pow((oaza.yo - sklad.ys), 2));
	}
	
	public Cesta(Oaza oaza, Oaza oaza1, int vzdalenost) {
		
	}
	
	public Cesta(Sklad sklad, Sklad sklad1, int vzdalenost) {
		
	}
	
}