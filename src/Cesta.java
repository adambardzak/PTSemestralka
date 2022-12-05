

public class Cesta {
	Misto misto1, misto2;
	double vzdalenost;
	
	public Cesta(Misto misto1, Misto misto2) {
		this.misto1 = misto1;
		this.misto2 = misto2;
		vzdalenost = Math.sqrt(Math.pow((misto1.sx - misto2.sx), 2) + Math.pow((misto1.sy - misto2.sy), 2));
	}

	@Override
	public String toString() {
		return "Cesta [misto1=" + misto1 + ", misto2=" + misto2 + ", vzdalenost=" + vzdalenost + "]";
	}
	
	
	
}