package pt_semestralka;
public class Oaza {
	int xo,yo,po,co;
	public Oaza(int xo,int yo,int po,int co) {
		this.xo = xo;
		this.yo = yo;
		this.po = po;
		this.co = co;
	}
	
	public String vypis() {
		String vypis = "souradnice: ["+xo+","+yo+"], oaza cislo: "+co;
		return vypis;
	}
}