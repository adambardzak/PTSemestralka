public class Pozadavek implements Comparable<Pozadavek>{
	int tz, op, kp, tp, poradi;
	boolean vyrizeny = false;
	
	public Pozadavek(int tz, int op,int kp,int tp, int poradi) {
		this.tz = tz; //cas prichodu pozadavku
		this.op = op; //index oazy kam ma byt pozadavek dorucen
		this.kp = kp; //mnozstvi pozadovanych kosu
		this.tp = tp; //doba na zpracovani pozadavku
		this.poradi = poradi; //jako kolikaty pozadavek prisel
	}
	
	public void vypis() {
		System.out.println("cas prichodu: "+tz+", oaza c.: "+op+", pocet kosu: "+kp+", cas na pozadavek: "+tp+", poradi: "+poradi);
	}

	@Override
	public int compareTo(Pozadavek o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//sorted listx s pozadavky serazenymi podle neceho
	//pridavat pozadavky do fronty rovnou serazeny

}