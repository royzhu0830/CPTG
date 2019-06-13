import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.IOException;  
import java.io.FileWriter; 

public class CPTG implements ActionListener, MouseMotionListener, MouseListener, KeyListener { //no timer has been set to repaint, so do that
	//variables
	JFrame theframe;
	CPTGanimation thepanel;
	Timer thetimer;
	JButton thebutton;
	JButton thesendbutton; 
	JButton helpquitbutton; 
	JButton mainmenubutton; 
	JButton settingsbackbutton;
	JButton clientbackbutton;  
	JButton theclientbutton;
	JButton restartbutton; 
	JButton quitbutton; 
	JScrollPane thescroll; 
	JTextField thetextfield;
	JTextField theclientfield; 
	JTextField thename; 
	JTextField portnumber;
	JTextField portnumber2; 
	JLabel theIP;
	JLabel reminder; 
	JLabel portwarning; 
	JLabel deadpiecelabel;
	JTextArea thearea;
	int intTemp=-1;
	int intTemp2;
	int intTemp3;
	int intTempX;
	int intTempY;
	int i=0;
	int j=0;
	int intSplitX=0;
	int intSplitY=0;
	int intEX;
	int intEY;
	int intEnI;
	int intDeadX = 800; 
	int intDeadY = 10;
	int intEnDead=0;
	int intPortNumber=6; 
	int intPortNumber2=9; 
	int intScore = 0; 
	boolean blnStart = false;
	boolean blnTurn=true;
	boolean blnWin[] = new boolean[5];
	boolean blnActivate = false;
	boolean blnSwap=true;
	boolean blnHelp;
	boolean blnSettings = false;
	String strTempPiece;
	String strEnemyX="";
	String strEnemyY="";
	String strName="bob"; 
	String strText; 
	String strSplit[]=new String [48];
	StringBuilder strCoordinates = new StringBuilder(21);
	SuperSocketMaster ssm;
	SuperSocketMaster ssm2;
	boolean blnGame = false; 
	boolean blnMenu = true; 
	boolean blnConnection = false; 
	boolean blnClient = false; 
	String strClientAddress;
	boolean blnServer = false;
	char chrQuit;


	
	public void actionPerformed(ActionEvent e) {

		//Get username
		strName = thename.getText();
		//port number for game
		try{ 
			intPortNumber = Integer.parseInt(portnumber.getText());
		}catch(NumberFormatException q){ 
			intPortNumber = 0; 
		} 
		if(intPortNumber > 10000 || intPortNumber < 0){ 
			intPortNumber = 0; 
		} 
		//port number for noob chat
		try{ 
			intPortNumber2 = Integer.parseInt(portnumber2.getText());
		}catch(NumberFormatException q){ 
			intPortNumber2 = 0; 
		} 
		if(intPortNumber2 > 10000 || intPortNumber2 < 0){ 
			intPortNumber2 = 0; 
		} 
		if(intPortNumber2 == 0 || intPortNumber == 0){ 
			thepanel.add(portwarning); 
		}else{ 
			thepanel.remove(portwarning); 
		}
		//quit button
		if(e.getSource() == quitbutton){ 
			theframe.dispatchEvent(new WindowEvent(theframe, WindowEvent.WINDOW_CLOSING));
		}
		//restart button
		if(e.getSource() == restartbutton){ 
			thepanel.blnFirstTime = true; 
			blnSwap = true;
			blnActivate = true;
			thepanel.blnWin = false;
			thepanel.blnLose = false; 
			thepanel.remove(quitbutton);
			thepanel.remove(restartbutton);
			thepanel.add(thebutton);
			thepanel.blnReady = false;
		}
		//repaint
		if (e.getSource()==thetimer) {
			thepanel.repaint();
		}  
		//back button on help screen
		if(e.getSource() == helpquitbutton){ 
			thepanel.remove(helpquitbutton); 
			thepanel.blnHelp = false; 
			blnMenu = true;
		} 
		//back button on play screen
		if(e.getSource() == mainmenubutton){
			thepanel.remove(mainmenubutton);
			thepanel.remove(thename);
			blnMenu = true;
			blnConnection = false;
			thepanel.blnMenu = true; 
			thepanel.blnConnection = false;
			thepanel.blnFirstTime = true; 
			blnSwap = true;
			blnActivate = true;
			thepanel.blnWin = false;
			thepanel.blnLose = false; 
			thepanel.blnReady = false; 
			thepanel.remove(quitbutton);
			thepanel.remove(restartbutton);
			thepanel.remove(thesendbutton);
			thepanel.remove(thetextfield); 
			thepanel.remove(thescroll);
			blnServer = false; 
			blnClient = false; 
			thepanel.blnConnection = false; 
			thepanel.blnConnection2 = false;  
			thepanel.remove(theclientfield); 
			thepanel.remove(theclientbutton); 	
			blnConnection = false;
		}
		//back button on settings screen
		if(e.getSource() == settingsbackbutton){ 
			thepanel.blnSettings = false; 
			thepanel.remove(settingsbackbutton);
			thepanel.remove(portnumber); 
			thepanel.remove(portnumber2);
			blnMenu = true;
		}
		//client connect button
		if (e.getSource()==theclientbutton){
			strClientAddress = theclientfield.getText();
			thepanel.remove(thename); 
			thepanel.remove(mainmenubutton);
			if (blnClient == true){ 
				ssm = new SuperSocketMaster(strClientAddress,intPortNumber,this);
				ssm2 = new SuperSocketMaster(strClientAddress,intPortNumber2,this); 
				ssm.connect();
				ssm2.connect();
				ssm2.sendText("Start");
			}
			//Txt File Name// 
			String fileName = "Score.txt"; 
			BufferedWriter score; 
			try{ 
				score = new BufferedWriter(new FileWriter("Score.txt",true)); 
				strText = thename.getText() + ": " +intScore+ "\n";
				score.append(strText);
				score.close(); 
			}catch(IOException p){ 
				p.printStackTrace(); 
			}
		}
		//ready button
		if (e.getSource()==thebutton) {
			thepanel.remove(thebutton);
			thepanel.add(thesendbutton);
			thepanel.add(thetextfield); 
			thepanel.add(thescroll); 
			thepanel.remove(theIP);
			blnActivate = true;
			blnSwap=false;
			if (blnClient==true) {
				blnTurn=false;
			}
			if(blnServer == true){
				blnTurn = true;
			}
			i=0;
			//send coordinates of pieces
			while (i<21) {
				strCoordinates.append(String.valueOf(thepanel.Piece[i].getIntX()) + "," + String.valueOf(thepanel.Piece[i].getIntY())+",");
				i++;
			}
			ssm.sendText(strCoordinates.toString());
			i=0;
			//send button
		}else if (e.getSource() == thesendbutton){
			ssm2.sendText(strName+": "+thetextfield.getText()); 
			thearea.append(strName+": "+thetextfield.getText() + "\n");
			//chat ssm
		}else if (e.getSource()==ssm2) {
			thearea.append(ssm2.readText() + "\n");
			//waits for both server and client to ready up to run
			if (blnServer==true && ssm2.readText().equals("Start") && blnStart==false) {
				thepanel.blnWaiting = false;
				thepanel.blnConnection = false; 
				thepanel.blnGameboard = true;  
				blnGame = true; 
				thepanel.add(thebutton);
				thepanel.remove(thename); 
				thepanel.remove(mainmenubutton); 
				blnConnection = false; 
			//	thepanel.blnWaiting = true;
				ssm2.sendText("Ready");
				blnStart=true;
			}else if(blnClient==true && ssm2.readText().equals("Ready")) {
				thepanel.blnWaiting = false;
				thepanel.blnConnection2 = false; 
				thepanel.blnGameboard = true;  
				blnGame = true; 
				thepanel.add(thebutton);
				thepanel.remove(theclientfield); 
				thepanel.remove(theclientbutton); 
				blnConnection = false;
			}
			//send command of movement of pieces
		}else if (e.getSource()==ssm) {
			
			i=0;
			j=0;
			if (thepanel.blnReady==false) {
				strSplit=ssm.readText().split(",");
				while (i<21) {
					try{
						intEX=Integer.parseInt(strSplit[j]);
						intEY=Integer.parseInt(strSplit[j+1]);
					}catch(NumberFormatException n) {
						thepanel.EnPiece[i].setIntX(0);
						thepanel.EnPiece[i].setIntY(0);
					}
					// determine location of enemy pieces.
					if (intEY==thepanel.intDeltaBoard*5) {
						thepanel.EnPiece[i].setIntY(thepanel.intDeltaBoard*2);
					}else if (intEY==thepanel.intDeltaBoard*6) {
						thepanel.EnPiece[i].setIntY(thepanel.intDeltaBoard*1);
					}else if (intEY==thepanel.intDeltaBoard*7) {
						thepanel.EnPiece[i].setIntY(thepanel.intDeltaBoard*0);
					}
					if(intEX==thepanel.intDeltaBoard*0) {
						thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*8);
					}else if(intEX==thepanel.intDeltaBoard*1) {
						thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*7);
					}else if(intEX==thepanel.intDeltaBoard*2) {
						thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*6);
					}else if(intEX==thepanel.intDeltaBoard*3) {
						thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*5);
					}else if(intEX==thepanel.intDeltaBoard*4) {
						thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*4);
					}else if(intEX==thepanel.intDeltaBoard*5) {
						thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*3);
					}else if(intEX==thepanel.intDeltaBoard*6) {
						thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*2);
					}else if(intEX==thepanel.intDeltaBoard*7) {
						thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*1);
					}else if(intEX==thepanel.intDeltaBoard*8) {
						thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*0);
					}
					i++;
					j=j+2;
				}
				
				j=0;
				thepanel.blnReady=true;
			}else{
				strSplit=ssm.readText().split(",");
				try{
					intEnI = Integer.parseInt(strSplit[1]);
				}catch(NumberFormatException w) {
					intEnI=0;
				}
				
				if(strSplit[0].equals("L")) {
					thepanel.EnPiece[intEnI].setIntX(thepanel.EnPiece[intEnI].getIntX()+thepanel.intDeltaBoard);
				}else if(strSplit[0].equals("R")) {
					thepanel.EnPiece[intEnI].setIntX(thepanel.EnPiece[intEnI].getIntX()-thepanel.intDeltaBoard);
				}else if(strSplit[0].equals("U")) {
					thepanel.EnPiece[intEnI].setIntY(thepanel.EnPiece[intEnI].getIntY()+thepanel.intDeltaBoard);
				}else if(strSplit[0].equals("D")) {
					thepanel.EnPiece[intEnI].setIntY(thepanel.EnPiece[intEnI].getIntY()-thepanel.intDeltaBoard);
				}
				if (thepanel.Piece[20].getIntY()==0) {
					thepanel.blnWin = true; 
					thepanel.add(restartbutton); 
					thepanel.add(quitbutton);
					blnActivate = false;
					intEnDead=0;
					intScore=intScore+1;
					intDeadX=800;
					intDeadY=10;
					thepanel.blnReady=false;
				}else if (thepanel.EnPiece[20].getIntY()==595){
					thepanel.blnLose = true; 
					thepanel.add(restartbutton); 
					thepanel.add(quitbutton);
					blnActivate = false;
					intEnDead=0;
					intDeadX=800;
					intDeadY=10;
					thepanel.blnReady=false;
				}
				blnTurn=true;
				i=0;
				blnWin=null;
				while(i<21) {
					//battle conditions
					if(thepanel.Piece[i].getIntX()==thepanel.EnPiece[intEnI].getIntX() && thepanel.Piece[i].getIntY()==thepanel.EnPiece[intEnI].getIntY()) {
						thepanel.add(deadpiecelabel);
						blnWin=thepanel.Piece[i].battle(thepanel.Piece[i].getIntRank(),thepanel.EnPiece[intEnI].getIntRank()); 
						if (blnWin[4]==true) {
							thepanel.Piece[i].setIntX(intDeadX);
							thepanel.Piece[i].setIntY(intDeadY);
						}else if (blnWin[2]==true) {
							thepanel.Piece[i].setIntX(intDeadX); 
							thepanel.Piece[i].setIntY(intDeadY);
							thepanel.EnPiece[intEnI].setIntX(3000);
							thepanel.EnPiece[intEnI].setIntY(3000);
							intDeadX=intDeadX+90;
							intEnDead++;
							deadpiecelabel.setText("Enemy Pieces Defeated: " + intEnDead);
							if (intDeadX>1195) {
								intDeadY=intDeadY+90;
								intDeadX=800;
							}
						}else if(blnWin[0] == false){
							thepanel.Piece[i].setIntX(intDeadX); 
							thepanel.Piece[i].setIntY(intDeadY);
							intDeadX = intDeadX + 90; 
							if (intDeadX>1195) {
								intDeadY=intDeadY+90;
								intDeadX=800;
							}
						}else if (blnWin[0] == true){ 
							intEnDead++;
							deadpiecelabel.setText("Enemy Pieces Defeated: " + intEnDead);
							thepanel.EnPiece[intEnI].setIntX(3000);
							thepanel.EnPiece[intEnI].setIntY(3000);
						} 
						//win/lose condition
						if(blnWin[1] == true){ 
							//Win Screen 
							thepanel.blnWin = true; 
							thepanel.add(restartbutton); 
							thepanel.add(quitbutton);
							thepanel.remove(deadpiecelabel);
							intEnDead=0;
							intScore=intScore+1;
							blnActivate = false;
							intDeadX=800;
							intDeadY=10;
							thepanel.blnReady=false;
							//Lose Screen
						}else if (blnWin[3] == true){ 
							thepanel.blnLose = true; 
							thepanel.add(restartbutton); 
							thepanel.add(quitbutton);
							thepanel.remove(deadpiecelabel);
							blnActivate = false;
							intEnDead=0; 
							intDeadX=800;
							thepanel.blnReady=false;
							intDeadY=10;
						}
					}
					i++;
				}
			}
		}
	}
	public void mouseDragged(MouseEvent e) {
		//movement of pieces
		if(intTemp!=-1) {
			thepanel.Piece[intTemp].setIntX(e.getX());
			thepanel.Piece[intTemp].setIntY(e.getY());
		}
	}
	public void mouseMoved (MouseEvent e) {
		
	}
	public void mouseClicked(MouseEvent e) {
		//main menu
		if (blnMenu == true){
			//play button 
			if(e.getX() >= 30 && e.getX() <= 597 && e.getY() >= 205 && e.getY() <= 282){ 
				thepanel.blnMenu = false; 
				thepanel.blnConnection = true;
				blnMenu = false; 
				blnConnection = true; 
				thepanel.add(thename);
				thepanel.add(mainmenubutton);
				//quit button
			}else if(e.getX() >= 30 && e.getX() <= 597 && e.getY() >= 540 && e.getY() <= 617){ 
				theframe.dispatchEvent(new WindowEvent(theframe, WindowEvent.WINDOW_CLOSING));
				//help screen
			}else if(e.getX() >= 30 && e.getX() <= 597 && e.getY() >= 415 && e.getY() <= 492){ 
				thepanel.blnHelp = true;
				blnMenu = false; 
				//settings screen
			}else if (e.getX() >= 30 && e.getX() <= 597 && e.getY() >= 310 && e.getY() <= 387){ 
				thepanel.blnSettings = true;
				thepanel.add(settingsbackbutton);
				thepanel.add(portnumber);
				thepanel.add(portnumber2);
				blnSettings = true;
				blnMenu = false;
			}  
		}
		//add back button
		if(thepanel.blnHelp == true){ 
			thepanel.add(helpquitbutton);
		}	
		//server/client screen
		if (blnConnection == true){
			//player is server 
			if(e.getX() >= 138 && e.getX() <= 611 && e.getY() >= 390 && e.getY() <= 680){ 
				blnServer = true; 
				blnClient = false; 
				thepanel.remove(thename); 
				thepanel.remove(mainmenubutton); 
				blnConnection = false; 
				if (blnServer == true){ 
					thepanel.remove(mainmenubutton);
					ssm = new SuperSocketMaster(intPortNumber,this);
					ssm2 = new SuperSocketMaster(intPortNumber2,this);
					ssm.connect();
					ssm2.connect(); 
					theIP.setText("IP: "+ssm.getMyAddress());
					thepanel.add(theIP);
					thepanel.blnWaiting = true; 
					//ssm2.sendText("Start");
					System.out.println(ssm.getMyAddress());
				}
				String fileName = "Score.txt"; 
				BufferedWriter score; 
				try{ 
					score = new BufferedWriter(new FileWriter("Score.txt",true)); 
					strText = thename.getText() + ": " +intScore+ "\n";
					score.append(strText);
					score.close(); 
				}catch(IOException p){ 
					p.printStackTrace(); 
				}
				//player is client
			}else if (e.getX() >= 720 && e.getX() < 1175 && e.getY() >= 390 && e.getY() <= 680){ 
				
				blnServer = false; 
				blnClient = true; 
				thepanel.blnConnection = false; 
				thepanel.blnConnection2 = true;  
				thepanel.add(theclientfield); 
				thepanel.add(theclientbutton); 	
				blnConnection = false;
			}
		}
			
	}
	public void mousePressed (MouseEvent e) {
		i=0;
		//movement of pieces
		while (i<21) {
			if (thepanel.Piece[i].getIntX()<=e.getX() && thepanel.Piece[i].getIntX() + thepanel.intDeltaPiece>e.getX() && thepanel.Piece[i].getIntY()<=e.getY() && thepanel.Piece[i].getIntY()+thepanel.intDeltaPiece>e.getY()) {
				intTemp=i;
				thepanel.blnFirstTime=false;
				intTempX=thepanel.Piece[intTemp].getIntX();
				intTempY=thepanel.Piece[intTemp].getIntY();
				break;
			}
			i++;
		}
		i=0;
	}
	public void mouseReleased(MouseEvent e) {
		//movement of pieces
		if (blnSwap==true) {
			while (i<27 && intTemp!=-1) {
				if (thepanel.Piece[i].getIntX()<=e.getX() && thepanel.Piece[i].getIntX() + thepanel.intDeltaPiece>e.getX() && thepanel.Piece[i].getIntY()<=e.getY() && thepanel.Piece[i].getIntY()+thepanel.intDeltaPiece>e.getY()) {
					if (intTemp2==-1) {
						intTemp2=i;
					}else {
						intTemp3=i;
						break; 
					}
				}
				i++;
			}
			if (intTemp3!=-1 && intTemp==intTemp2) {
				intTemp2=intTemp3;
			}
			if (intTemp2!=-1 && intTemp!=-1) {
				thepanel.Piece[intTemp].setIntX(thepanel.Piece[intTemp2].getIntX());
				thepanel.Piece[intTemp].setIntY(thepanel.Piece[intTemp2].getIntY());
				thepanel.Piece[intTemp2].setIntX(intTempX);
				thepanel.Piece[intTemp2].setIntY(intTempY);
			}
		}else if(thepanel.blnReady==true && blnTurn==true && blnSwap==false){
			i=0;
			while (i<21 && intTemp!=-1) {
				if (thepanel.Piece[i].getIntX()<=e.getX() && thepanel.Piece[i].getIntX() + thepanel.intDeltaPiece>e.getX() && thepanel.Piece[i].getIntY()<=e.getY() && thepanel.Piece[i].getIntY()+thepanel.intDeltaPiece>e.getY()) {
					if (intTemp2==-1) {
						intTemp2=i;
					}else {
						intTemp3=i;
						break;
					}
				}
				i++;
			}
			if (intTempX>e.getX() && intTempX-thepanel.intDeltaBoard<=e.getX() && intTemp3==-1 && intTempY<=e.getY() && intTempY+thepanel.intDeltaPiece>e.getY() && intTempX-thepanel.intDeltaBoard>=0) {
				thepanel.Piece[intTemp].setIntX(intTempX-thepanel.intDeltaBoard);
				thepanel.Piece[intTemp].setIntY(intTempY);
				ssm.sendText("L,"+String.valueOf(intTemp));
				blnTurn=false;
			}else if(intTempX+thepanel.intDeltaBoard<e.getX() && intTempX+(2*thepanel.intDeltaBoard)>=e.getX() && intTemp3==-1 && intTempY<=e.getY() && intTempY+thepanel.intDeltaPiece>e.getY() && intTempX+thepanel.intDeltaBoard<765) {
				thepanel.Piece[intTemp].setIntX(intTempX+thepanel.intDeltaBoard);
				thepanel.Piece[intTemp].setIntY(intTempY);
				ssm.sendText("R,"+String.valueOf(intTemp));
				blnTurn=false;
			}else if (intTempY>e.getY() && intTempY-thepanel.intDeltaBoard<=e.getY() && intTemp3==-1 && intTempX<=e.getX() && intTempX + thepanel.intDeltaPiece>e.getX() && intTempY-thepanel.intDeltaBoard>=0) {
				thepanel.Piece[intTemp].setIntY(intTempY-thepanel.intDeltaBoard);
				thepanel.Piece[intTemp].setIntX(intTempX);
				ssm.sendText("U,"+String.valueOf(intTemp));
				blnTurn=false;
			}else if(intTempY+thepanel.intDeltaBoard<e.getY() && intTempY+(2*thepanel.intDeltaBoard)>=e.getY() && intTemp3==-1 && intTempX<=e.getX() && intTempX + thepanel.intDeltaPiece>e.getX() && intTempY+thepanel.intDeltaBoard<680) {
				thepanel.Piece[intTemp].setIntY(intTempY+thepanel.intDeltaBoard);
				thepanel.Piece[intTemp].setIntX(intTempX);
				ssm.sendText("D,"+String.valueOf(intTemp));
				blnTurn=false;
			}else if (intTemp!=-1){
				thepanel.Piece[intTemp].setIntX(intTempX);
				thepanel.Piece[intTemp].setIntY(intTempY);
			}
			if (thepanel.Piece[20].getIntY()==0) {
				thepanel.blnWin = true; 
				thepanel.add(restartbutton); 
				thepanel.add(quitbutton);
				blnActivate = false;
				thepanel.remove(deadpiecelabel);
				intEnDead=0;
				intScore=intScore+1;
				thepanel.blnReady=false;
				intDeadX=800;
				intDeadY=10;
				
			}else if (thepanel.EnPiece[20].getIntY()==595){
				thepanel.blnLose = true; 
				thepanel.add(restartbutton); 
				thepanel.add(quitbutton);
				blnActivate = false;
				thepanel.remove(deadpiecelabel);
				thepanel.blnReady=false;
				intEnDead=0;
				intDeadX=800;
				intDeadY=10;
			}
			
				//battle conditions
			i=0;
			while(i<21 && intTemp!=-1) {
				if(thepanel.Piece[intTemp].getIntX()==thepanel.EnPiece[i].getIntX() && thepanel.Piece[intTemp].getIntY()==thepanel.EnPiece[i].getIntY()) {
					thepanel.add(deadpiecelabel);
					blnWin=null;
					blnWin=thepanel.Piece[intTemp].battle(thepanel.Piece[intTemp].getIntRank(),thepanel.EnPiece[i].getIntRank()); 
					if (blnWin[4]==true) { //both died
						blnWin[1]=true;
					}else if (blnWin[2]==true) {
						thepanel.Piece[intTemp].setIntX(intDeadX); 
						thepanel.Piece[intTemp].setIntY(intDeadY);
						thepanel.EnPiece[i].setIntX(3000);
						thepanel.EnPiece[i].setIntY(3000);
						intEnDead=intEnDead+1;
						deadpiecelabel.setText("Enemy Pieces Defeated: " + intEnDead);
						intDeadX=intDeadX+90;
						if (intDeadX>1195) {
							intDeadY=intDeadY+90;
							intDeadX=800;
						}
					}else if(blnWin[0] == false){
						thepanel.Piece[intTemp].setIntX(intDeadX); 
						thepanel.Piece[intTemp].setIntY(intDeadY);
						intDeadX = intDeadX + 90; 
						if (intDeadX>1195) {
							intDeadY=intDeadY+90;
							intDeadX=800;
						}
					}else if (blnWin[0] == true){ 
						intEnDead++;
						deadpiecelabel.setText("Enemy Pieces Defeated: " + intEnDead);
						thepanel.EnPiece[i].setIntX(3000);
						thepanel.EnPiece[i].setIntY(3000);
					}
					if(blnWin[1] == true){ 
						//Win Screen 
						thepanel.blnWin = true; 
						thepanel.add(restartbutton); 
						thepanel.add(quitbutton);
						blnActivate = false;
						thepanel.blnReady=false;
						thepanel.remove(deadpiecelabel);
						intScore=intScore+1;
						intEnDead=0;
						intDeadX=800;
						intDeadY=10;
					}else if (blnWin[3] == true){ 
						//Lose Screen
						thepanel.blnLose = true; 
						thepanel.add(restartbutton); 
						thepanel.add(quitbutton);
						blnActivate = false;
						thepanel.blnReady=false;
						thepanel.remove(deadpiecelabel);
						intEnDead=0;
						intDeadX=800;
						intDeadY=10;
					}
				}
			i++;
			}
			
		}else if(intTemp!=-1){
			thepanel.Piece[intTemp].setIntX(intTempX);
			thepanel.Piece[intTemp].setIntY(intTempY);
		}
		i=0;
		intTemp=-1;
		intTemp2=-1;
		intTemp3=-1;
		intTempX=-1;
		intTempY=-1;
	}
	public void mouseExited(MouseEvent e) {
		
	}
	public void mouseEntered(MouseEvent e) {
		
	}
	public void keyPressed(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
		
	}
	public void keyTyped(KeyEvent e) {
		
	}
	
	
	public CPTG () {
		//constructor
		thepanel = new CPTGanimation();
		thepanel.setLayout(null);
		thepanel.setPreferredSize(new Dimension(1280,780));
		thepanel.addMouseListener(this);
		thepanel.addMouseMotionListener(this);
		thepanel.addKeyListener(this);
		
		theframe = new JFrame("Game of Generals 2");
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		thebutton = new JButton("Ready!");
		thebutton.setBounds(800,300,100,100);
		thebutton.addActionListener(this); 
		
		thesendbutton = new JButton("Send"); 
		thesendbutton.setSize(100,50); 
		thesendbutton.setLocation(850,630); 
		thesendbutton.addActionListener(this); 
		
		helpquitbutton = new JButton("Back");
		helpquitbutton.setSize(300,100); 
		helpquitbutton.setLocation(980,620);
		helpquitbutton.addActionListener(this);
		
		mainmenubutton = new JButton("Main Menu"); 
		mainmenubutton.setSize(300,100); 
		mainmenubutton.setLocation(0,0); 
		mainmenubutton.addActionListener(this);
		
		settingsbackbutton = new JButton("Back"); 
		settingsbackbutton.setSize(300,100); 
		settingsbackbutton.setLocation(980,620); 
		settingsbackbutton.addActionListener(this);
		
		restartbutton = new JButton("Play Again"); 
		restartbutton.setSize(350,200); 
		restartbutton.setLocation(69,100); 
		restartbutton.addActionListener(this);
		
		quitbutton = new JButton("Quit"); 
		quitbutton.setSize(350,200); 
		quitbutton.setLocation(696,100); 
		quitbutton.addActionListener(this);	
		
		theclientbutton = new JButton ("Connect"); 
		theclientbutton.setSize(100,50); 
		theclientbutton.setLocation(600,625); 
		theclientbutton.addActionListener(this);

		thetextfield = new JTextField("");
		thetextfield.setSize(250,25); 
		thetextfield.setLocation(770,600);
		thetextfield.addActionListener(this);
		
		thename = new JTextField("YourBoy"); 
		thename.setSize(250,25); 
		thename.setLocation(500,175);
		thename.addActionListener(this);

		portnumber = new JTextField("6"); 
		portnumber.setSize(250,25); 
		portnumber.setLocation(289,170); 
		portnumber.addActionListener(this);
		
		portnumber2 = new JTextField("9"); 
		portnumber2.setSize(250,25); 
		portnumber2.setLocation(289,269); 
		portnumber2.addActionListener(this);
		
		portwarning = new JLabel("Type valid port number"); 
		portwarning.setSize(700,50); 
		portwarning.setLocation(0,0); 
		portwarning.setForeground(Color.red);
		
		theIP = new JLabel("");
		theIP.setSize(300,50); 
		theIP.setLocation(1150,0);
		theIP.setForeground(Color.red);
		
		deadpiecelabel=new JLabel("Enemy Pieces Defeated: ");
		deadpiecelabel.setSize(300,50);
		deadpiecelabel.setLocation(1070,500);
		deadpiecelabel.setForeground(Color.yellow);

		thearea = new JTextArea(""); 
		thearea.setSize(250,150); 
		thearea.setLocation(770,450); 

		thescroll = new JScrollPane(thearea);
		thescroll.setSize(250,150); 
		thescroll.setLocation(770,450);
		
		theclientfield = new JTextField(); 
		theclientfield.setSize(500,50); 
		theclientfield.setLocation(420,550); 
	
		thetimer = new Timer(1000/60,this);
		thetimer.start();
		
		theframe.setContentPane(thepanel);
		theframe.pack();
		theframe.setResizable(false);
		theframe.setVisible(true);
	
	}
	public static void main(String[] args) {
		new CPTG();
	}
}
//"192.168.2.21"

