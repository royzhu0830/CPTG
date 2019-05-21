//testing 123
public class Pieces {
	//properties
	/**name of the piece*/
	public String strPiece;
	/**number corresponding to rank of piece; lower rank=stronger*/
	public int intRank;
	/**X-coordinate of the piece*/
	public int intX;
	/**Y-coordinate of the piece*/
	public int intY;
	
	//methods
	/**gets the name of the piece*/
	public String getStrPiece() {
		return strPiece;
	}
	/**gets the rank of the piece*/
	public int getIntRank() {
		return intRank;
	}
	/**gets the X-coordinate of the piece*/
	public int getIntX() {
		return intX;
	}
	/**sets the X-coordinate of the Piece*/
	public void setIntX(int intX) {
		this.intX=intX;
	}
	/**gets the Y-coordinate of the piece*/
	public int getIntY(){
		return intY;
	}
	/**sets the y-coordinate of the piece*/
	public void setIntY(int intY) {
		this.intY=intY;
	}
	/**determines outcome of conflict*/
	public void kill(int intRank){
		
	}
	
	//constructor
	public Pieces(String strPiece,int intRank, int intX, int intY) {
		this.strPiece = strPiece;
		this.intRank = intRank;
		//this.intX=intX;
		//this.intY=intY;
	}
}
	
