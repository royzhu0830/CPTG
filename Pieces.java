//testing 123
public class Pieces {
	//properties
	public String strPiece;
	public int intRank;
	public int intX;
	public int intY;
	
	//methods
	public String getStrPiece() {
		return strPiece;
	}
	
	public int getIntRank() {
		return intRank;
	}
	public int getIntX() {
		return intX;
	}
	public void setIntX(int intX) {
		this.intX=intX;
	}
	public int getIntY(){
		return intY;
	}
	public void setIntY(int intY) {
		this.intY=intY;
	}
	public void move(){
		
	}
	public void kill(){
		
	}
	
	//constructor
	public Pieces(String strPiece,int intRank, int intX, int intY) {
		this.strPiece = strPiece;
		this.intRank = intRank;
		//this.intX=intX;
		//this.intY=intY;
	}
}
	
