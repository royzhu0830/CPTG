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
	boolean blnSettings = false; 
	boolean blnWaiting = false; 
	
	BufferedImage imgTitle = null; 
	BufferedImage imgConnection = null; 
	BufferedImage imgClient = null; 
	BufferedImage imgHelp = null;
	BufferedImage imgSettings = null; 
	BufferedImage imgWaiting = null;
	
	BufferedImage img5G = null; 
	BufferedImage img4G = null; 
	BufferedImage img3G = null; 
	BufferedImage img2G = null; 
	BufferedImage img1G = null; 
	BufferedImage imgCol = null; 
	BufferedImage imgLt = null; 
	BufferedImage imgMaj = null; 
	BufferedImage imgCap = null; 
	BufferedImage img1L = null; 
	BufferedImage img2L = null; 
	BufferedImage imgSerg = null; 
	BufferedImage imgPrivate = null; 
	BufferedImage imgSpy = null; 
	BufferedImage imgFlag = null; 
	BufferedImage imgEnemy = null; 
	BufferedImage imgWhite = null; 
	
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
			Piece[intCounter]=new Pieces(img5G,intCounter,0,0); //all pieces, highest to lowest rank, spy and priv. outside of order
			intCounter++;
			Piece[intCounter]=new Pieces(img4G,intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(img3G,intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(img2G,intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(img1G,intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgCol,intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgLt,intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgMaj,intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgCap,intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(img1L,intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(img2L,intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgSerg,intCounter,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgPrivate,-1,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgPrivate,-1,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgPrivate,14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgPrivate,14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgPrivate,14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgPrivate,14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgSpy,14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgSpy,14,0,0);
			intCounter++;
			Piece[intCounter]=new Pieces(imgFlag,15,0,0);
			intCounter++;
			for (int i=intCounter; i<28; i++) {
				Piece[i]=new Pieces(imgWhite,16,0,0);
			}	
			intCounter=0;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0); //all pieces, highest to lowest rank, spy and priv. outside of order
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,intCounter,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,-1,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,-1,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,14,0,0);
			intCounter++;
			EnPiece[intCounter]=new Pieces(imgEnemy,15,0,0);
			intCounter++;
			for (int i=intCounter; i<28; i++) {
			EnPiece[i]=new Pieces(imgWhite,16,0,0);
			}	
			intCounter=0;
			for (int i=1; i<28; i++) { //i variable decides which piece and where it is                                                                                                              b
				if (i==1) {
					Piece[0].setIntY(intDeltaBoard*5);
					Piece[0].setIntX(0);
					g.drawImage(Piece[0].getimgPiece(),Piece[0].getIntX(),Piece[0].getIntY(),null);
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
				g.drawImage(Piece[i].getimgPiece(),Piece[i].getIntX(),Piece[i].getIntY(),null);
			}
			blnFirstTime=false;
		}else if (blnFirstTime==false) {//fix identation if it works
				for (int i=0; i<21; i++) {
					g.drawImage(Piece[i].getimgPiece(),Piece[i].getIntX(),Piece[i].getIntY(),null);
					g.drawImage(EnPiece[i].getimgPiece(),EnPiece[i].getIntX(),EnPiece[i].getIntY(),null);
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
		if(blnSettings == true){ 
			g.drawImage(imgSettings,0,0,null);
		}
		if(blnWaiting == true){ 
			g.drawImage(imgWaiting,0,0,null); 
		
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
			try{ 
				imgSettings = ImageIO.read(new File("Settings.png")); 
			}catch(IOException e){ 
			}
			try{
				imgWaiting = ImageIO.read(new File("WaitingScreen.png")); 
			}catch(IOException e){ 
			}
			try{ 
				img5G = ImageIO.read(new File("5G.png")); 
			}catch (IOException e){ 
			}
			try{ 
				img4G = ImageIO.read(new File("4G.png")); 
			}catch (IOException e){ 
			}
			try{ 
				img3G = ImageIO.read(new File("3G.png")); 
			}catch (IOException e){ 
			}
			try{ 
				img2G = ImageIO.read(new File("2G.png")); 
			}catch (IOException e){ 
			}
			try{ 
				img1G = ImageIO.read(new File("1G.png")); 
			}catch (IOException e){ 
			}
			try{ 
				imgCol = ImageIO.read(new File("Col.png")); 
			}catch (IOException e){ 
			}
			try{ 
				imgLt = ImageIO.read(new File("Lt.png")); 
			}catch (IOException e){ 
			}
			try{ 
				imgMaj = ImageIO.read(new File("Maj.png")); 
			}catch (IOException e){ 
			}
			try{ 
				imgCap = ImageIO.read(new File("Cap.png")); 
			}catch (IOException e){ 
			}
			try{ 
				img1L = ImageIO.read(new File("1L.png")); 
			}catch (IOException e){ 
			}
			try{ 
				img2L = ImageIO.read(new File("2L.png")); 
			}catch (IOException e){ 
			}
			try{ 
				imgEnemy = ImageIO.read(new File("Enemy.png")); 
			}catch (IOException e){ 
			}
			try{ 
				imgWhite = ImageIO.read(new File("White.png")); 
			}catch (IOException e){ 
			}
			try{ 
				imgSerg = ImageIO.read(new File("Serg.png")); 
			}catch (IOException e){ 
			}
			try{ 
				imgPrivate = ImageIO.read(new File("Private.png")); 
			}catch (IOException e){ 
			}
			try{ 
				imgSpy = ImageIO.read(new File("Spy.png")); 
			}catch (IOException e){ 
			}
			try{ 
				imgFlag = ImageIO.read(new File("Flag.png")); 
			}catch (IOException e){ 
			}
	}
}


























