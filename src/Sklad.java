
public class Sklad {
	int xs,ys,ts,ks,tn,cs;
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
}