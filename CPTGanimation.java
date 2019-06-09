import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import javax.imageio.*;
import java.awt.image.*;

//testing 123

public class CPTGanimation extends JPanel {
	int intXBoard[][]=new int[8][9]; //for board
	int intYBoard[][]=new int[8][9]; 
	int intX=0; //for board
	int intY=0; //for board 

	
	int intDeltaPiece=83; //change this value to make board bigger
	int intDeltaBoard=intDeltaPiece+2;
	Pieces[] Piece = new Pieces[28];
	Pieces[] EnPiece = new Pieces[28];
	boolean blnFirstTime=true;
	boolean blnReady=false;
	boolean blnEnReady=false;
	boolean blnDead = false; 
	int intLength;
	int intCounter;
	int intDeadX = 890; 
	int intDeadY = 110;
	
	boolean blnMenu = true;
	boolean blnConnection = false; 
	boolean blnClient = false; 
	boolean blnGameboard = false;
	boolean blnHelp = false; 
	boolean blnConnection2 = false;
	
	BufferedImage imgTitle = null; 
	BufferedImage imgConnection = null; 
	BufferedImage imgClient = null; 
	BufferedImage imgHelp = null;
	
	
	public void paintComponent(Graphics g) {
		g.fillRect(0,0,2040,900);
		g.fillRect(0,0,2000,1000);
		g.setColor(Color.WHITE);
		
		for (int i=0; i<8; i++) {
			for (int j=0; j<9; j++) {
				intXBoard[i][j]=intX;
				intYBoard[i][j]=intY;
				intX=intX+intDeltaBoard;
			}
			intY=intY+intDeltaBoard;
			intX=0;
		}
		intY=0;
		for (int i=0; i<8; i++) {
			for (int j=0; j<9; j++) {
				g.fillRect(intXBoard[i][j],intYBoard[i][j],intDeltaPiece,intDeltaPiece);
			}
		}
		g.setColor(Color.BLACK);
		if(blnFirstTime==true) {
			intCounter=0;
			Piece[intCounter]=new Pieces("5G",intCounter,0,0); //all pieces, highest to lowest rank, spy and priv. outside of order
			intCounter++;
			Piece[intCounter]=new Pieces("4G",intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("3G",intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("2G",intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("1G",intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Col",intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("L. Col",intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Maj",intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Cap",intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("1 L.",intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("2 L.",intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Serg",intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Spy",-1,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Spy",-1,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Pri1",14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Pri2",14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Pri3",14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Pri4",14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Pri5",14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Pri6",14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces("Flag",15,0,0);
			intCounter++;
			for (int i=intCounter; i<28; i++) {
				Piece[i]=new Pieces("",16,0,0);
			}	
			intCounter=0;
			EnPiece[intCounter]=new Pieces("En5G",intCounter,0,0); //all pieces, highest to lowest rank, spy and priv. outside of order
			intCounter++;
			EnPiece[intCounter]=new Pieces("En4G",intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("En3G",intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("En2G",intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("En1G",intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnCol",intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnL. Col",intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnMaj",intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnCap",intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("En1 L.",intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("En2 L.",intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnSerg",intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnSpy",-1,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnSpy",-1,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnPri1",14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnPri2",14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnPri3",14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnPri4",14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnPri5",14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnPri6",14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces("EnFlag",15,0,0);
			intCounter++;
			for (int i=intCounter; i<28; i++) {
				EnPiece[i]=new Pieces("",16,0,0);
			}	
			intCounter=0;
			for (int i=1; i<28; i++) { //i variable decides which piece and where it is                                                                                                              b
				if (i==1) {
					Piece[0].setIntY(intDeltaBoard*5);
					Piece[0].setIntX(0);
					g.drawString(Piece[0].getStrPiece(),Piece[0].getIntX(),Piece[0].getIntY());
				}
				Piece[i].setIntX(Piece[i-1].getIntX()+intDeltaBoard);
				if(i==9 || i==18){
					Piece[i].setIntX(0);
				}	
				if (i<9) {
					Piece[i].setIntY(intDeltaBoard*5);
				}else if (i<18){
					Piece[i].setIntY(intDeltaBoard*6);
				}else {
					Piece[i].setIntY(intDeltaBoard*7);
				}
				g.drawString(Piece[i].getStrPiece(),Piece[i].getIntX(),Piece[i].getIntY());
			}
			blnFirstTime=false;
		}else if (blnFirstTime==false) {//fix identation if it works
			for (int i=0; i<21; i++) {
				g.setColor(Color.BLUE);
				g.drawString(Piece[i].getStrPiece(),Piece[i].getIntX(),Piece[i].getIntY());
				g.drawString(EnPiece[i].getStrPiece(),EnPiece[i].getIntX(),EnPiece[i].getIntY());
			}
		} 
		if(blnDead == true){ 
			g.setColor(Color.RED);
			g.drawRect(intDeadX, intDeadY, 30, 30); 
			
		}
		if (blnMenu == true){ 
			g.drawImage(imgTitle,0,0,null); 
		}
		if(blnConnection == true){ 
			g.drawImage(imgConnection,0,0,null); 
		}
		if(blnConnection2 == true){ 
			g.drawImage(imgClient,0,0,null);
		}
		if (blnClient == true){ 
			g.drawImage(imgClient,0,0,null); 
		}
		if(blnHelp == true){ 
			g.drawImage(imgHelp,0,0,null);
		}
	}
	public CPTGanimation() {
		super();
			try{ 
				imgTitle = ImageIO.read(new File("title.png")); 
			}catch(IOException e){ 
			}
			try{ 
				imgConnection = ImageIO.read(new File("Connection.png")); 
			}catch(IOException e){ 
			}
			try{ 
				imgClient = ImageIO.read(new File("Client.png")); 
			}catch(IOException e){ 
			}
			try{ 
				imgHelp = ImageIO.read(new File("HelpScreen.png")); 
			}catch(IOException e){ 
				
			}
	}
}


























