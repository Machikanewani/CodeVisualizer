package code;

public class drawPyramid {
	double nop;
	double noc;
	double nom;
	double loc;
	double cyclo;
	double calls;
	double fanout;
	double andc;
	double ahh;
	
	public drawPyramid(double nop, double noc, double nom, double loc, double cyclo, double calls, double fanout, double andc, double ahh) {
		this.nop = nop;
		this.noc = noc;
		this.nom = nom;
		this.loc = loc;
		this.cyclo = cyclo;
		this.calls = calls;
		this.fanout = fanout;
		this.andc = andc;
		this.ahh = ahh;
	}

	public void drawpyramid(String packageName) {
		pyFrame pyframe  = new pyFrame(this, packageName);
	}

	
}


