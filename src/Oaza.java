public class Oaza extends Misto{
//	double xo,yo;
	int po,co;
	private static int pocet = 0;
	public final int INDEX = ++pocet;
	public Oaza(int ID,double xo,double yo,int po,int co) {
		super(ID,xo, yo);
//		this.xo = xo;
//		this.yo = yo;
		this.po = po;
		this.co = co;
	}
	
	public String vypis() {
		String vypis = "souradnice: ["+sx+","+sy+"], oaza cislo: "+co;
		return vypis;
	}

	@Override
	public String toString() {
		return "Oaza [po=" + po + ", co=" + co + ", INDEX=" + INDEX + ", sx=" + sx + ", sy=" + sy + ", ID=" + ID + "]";
	}
	
	
}