
public class Pieces {
	//properties
	public String strPiece;
	public int intRank;
	public int intX;
	public int intY;
	
	//methods
	public void setStrPiece(String strPiece) {
		this.strPiece=strPiece;
	}
	public String getStrPiece() {
		return strPiece;
	}
	
	public void setIntRank(int intRank) {
		this.intRank=intRank;
	}
	public int getIntRank() {
		return intRank;
	}
	
	//constructor
	public Pieces(String strPiece,int intRank, int intX, int intY) {
		this.strPiece = strPiece;
		this.intRank = intRank;
		this.intX=intX;
		this.intY=intY;
	}
}
	
