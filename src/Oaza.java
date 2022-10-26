public class Oaza {
	int xo,yo,po,co;
	private static int pocet = 0;
	public final int INDEX = ++pocet;
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