package pt_semestralka;


public class Cesta {
	Sklad sklad, sklad1;
	Oaza oaza, oaza1;
	double vzdalenost;
	
	public Cesta(Sklad sklad, Oaza oaza) {
		this.oaza = oaza;
		this.sklad = sklad;
		vzdalenost = Math.sqrt(Math.pow((oaza.xo - sklad.xs), 2) + Math.pow((oaza.yo - sklad.ys), 2));
	}
	
	public Cesta(Oaza oaza, Oaza oaza1, int vzdalenost) {
		this.oaza = oaza;
		this.oaza1 = oaza1;
		//doplnit vypocet vzdalenosti
	}
	
	public Cesta(Sklad sklad, Sklad sklad1, int vzdalenost) {
		this.sklad = sklad;
		this.sklad1 = sklad1;
		//doplnit vzdalenost
	}
	
}