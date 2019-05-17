import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

//testing 123

public class CPTGanimation extends JPanel {
	int intXBoard[][]=new int[8][8]; //for board
	int intYBoard[][]=new int[8][8]; 
	int intX=0; //for board
	int intY=0; //for board
	int n =21;
	int intDeltaPiece=83; //change this value to make board bigger
	int intDeltaBoard=intDeltaPiece+2;
	Pieces[] Piece = new Pieces[24];
	int intXPiece[] = new int[24]; 
	int intYPiece[] = new int[24];
	Pieces[] EnPiece = new Pieces[24];
	int intEnXPiece[] = new int[24];
	int intEnYPiece[] = new int[24];
	boolean blnFirstTime=true;
	boolean blnReady=false;
	int intLength;
	int intCounter;
	
	public void paintComponent(Graphics g) {
		g.fillRect(0,0,2040,900);
		if(blnFirstTime==true) {
			for (int i=0; i<24; i++) {
				intXPiece[i]=0;
				intYPiece[i]=0;
			}
		}
		intCounter=0;
		Piece[intCounter]=new Pieces("5G",intCounter,intXPiece[intCounter],intYPiece[intCounter]); //all pieces, highest to lowest rank, spy and priv. outside of order
		intCounter++;
		Piece[intCounter]=new Pieces("4G",intCounter,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("3G",intCounter,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("2G",intCounter,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("1G",intCounter,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Col",intCounter,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("L. Col",intCounter,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Maj",intCounter,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Cap",intCounter,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("1 L.",intCounter,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("2 L.",intCounter,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Serg",intCounter,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Spy",-1,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Spy",-1,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Pri1",14,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Pri2",14,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Pri3",14,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Pri4",14,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Pri5",14,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Pri6",14,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		Piece[intCounter]=new Pieces("Flag",15,intXPiece[intCounter],intYPiece[intCounter]);
		intCounter++;
		for (int i=intCounter; i<24; i++) {
			Piece[i]=new Pieces("",16,intXPiece[i],intYPiece[i]);
		}
		intCounter=0;
		EnPiece[intCounter]=new Pieces("5G",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]); //all pieces, highest to lowest rank, spy and priv. outside of order
		intCounter++;
		EnPiece[intCounter]=new Pieces("4G",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("3G",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("2G",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("1G",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Col",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("L. Col",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Maj",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Cap",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("1 L.",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("2 L.",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Serg",intCounter,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Spy",-1,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Spy",-1,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Pri1",14,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Pri2",14,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Pri3",14,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Pri4",14,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Pri5",14,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Pri6",14,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		EnPiece[intCounter]=new Pieces("Flag",15,intEnXPiece[intCounter],intEnYPiece[intCounter]);
		intCounter++;
		for (int i=intCounter; i<24; i++) {
			EnPiece[i]=new Pieces("",16,intEnXPiece[i],intEnYPiece[i]);
		}
		intCounter=0;
		g.fillRect(0,0,2000,1000);
		g.setColor(Color.WHITE);
		
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				intXBoard[i][j]=intX;
				intYBoard[i][j]=intY;
				intX=intX+intDeltaBoard;
			}
			intY=intY+intDeltaBoard;
			intX=0;
		}
		intY=0;
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				g.fillRect(intXBoard[i][j],intYBoard[i][j],intDeltaPiece,intDeltaPiece);
			}
		}
		g.setColor(Color.BLACK);
		if (blnFirstTime==true) {
			for (int i=1; i<24; i++) { //i variable decides which piece and where it is                                                                                                              b
				if (i==1) {
					intYPiece[0]=intDeltaBoard*5;
					intXPiece[0]=0;
					g.drawString(Piece[0].getStrPiece(),intXPiece[0],intYPiece[0]);
				}
			
				intXPiece[i]=intXPiece[i-1]+intDeltaBoard;
				if(i==8 || i==16){
					intXPiece[i]=0;
				}	
				if (i<8) {
					intYPiece[i]=intDeltaBoard*5;
				}else if (i>=16){
					intYPiece[i]=intDeltaBoard*6;
				}else {
					intYPiece[i]=intDeltaBoard*7;
				}
				g.drawString(Piece[i].getStrPiece(),intXPiece[i],intYPiece[i]);
			}
			
			
		}else if (blnFirstTime==false) {//fix identation if it works
			for (int i=0; i<24; i++) {
				g.drawString(Piece[i].getStrPiece(),intXPiece[i],intYPiece[i]);
				g.drawString(EnPiece[i].getStrPiece(),intEnXPiece[i],intEnYPiece[i]);
			}
			
			
		}
			
	}
	public CPTGanimation() {
		super();
	}
}
