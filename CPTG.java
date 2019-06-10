import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

//game of the generals, 
/*TO DO LIST:
-determine winner and loser (create winner/loser page)
-create loading screen on server while client connects to server
-add pics of pieces
	-determine positioning of dead pieces
-settings page with port number
-change client page from port number to IP address
***REMEMBER to utilize file io
-add header and make the game display look better

*/
public class CPTG implements ActionListener, MouseMotionListener, MouseListener { //no timer has been set to repaint, so do that
	JFrame theframe;
	CPTGanimation thepanel;
	Timer thetimer;
	JButton thebutton;
	JButton thesendbutton; 
	JButton helpquitbutton; 
	JButton playbackbutton; 
	JButton settingsbackbutton;
	JButton clientbackbutton;  
	JButton theclientbutton;
	JScrollPane thescroll; 
	JTextField thetextfield;
	JTextField theclientfield; 
	JTextField thename; 
	JTextField portnumber;
	JTextField portnumber2; 
	JLabel theIP;
	JLabel reminder; 
	JLabel portwarning; 
	JTextArea thearea;
	int intTemp=-1;
	int intTemp2;
	int intTemp3;
	int intTempX;
	int intTempY;
	int intLength;
	int i=0;
	int j=0;
	int intRndNumbr;
	int intSplitX=0;
	int intSplitY=0;
	int intEX;
	int intEY;
	int intEnI;
	int intDeadX = 800; 
	int intDeadY = 10;
	int intDeadX2 = 800; 
	int intDeadY2 = 300; 
	int intPortNumber; 
	int intPortNumber2; 
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
	String strSplit[];
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
		strName = thename.getText();
		if(chrQuit == 'q'){ 
			theframe.dispatchEvent(new WindowEvent(theframe, WindowEvent.WINDOW_CLOSING));
		}
		if(blnSettings == true){
			try{ 
				intPortNumber = Integer.parseInt(portnumber.getText());
			}catch(NumberFormatException q){ 
				intPortNumber = 0; 
			} 
			if(intPortNumber > 10000 || intPortNumber < 0){ 
				intPortNumber = 0; 
			} 
			if(intPortNumber == 0){ 
				thepanel.add(portwarning); 
			}else{ 
				thepanel.remove(portwarning); 
			}
		}
		if (e.getSource()==thetimer) {
			thepanel.repaint();
		} 
		if(e.getSource() == helpquitbutton){ 
			thepanel.remove(helpquitbutton); 
			thepanel.blnHelp = false; 
			blnMenu = true;
		} 
		if(e.getSource() == playbackbutton){
			thepanel.remove(playbackbutton); 
			thepanel.remove(thename);
			blnMenu = true;
			blnConnection = false;
			thepanel.blnMenu = true; 
			thepanel.blnConnection = false;
			
		}
		if(e.getSource() == settingsbackbutton){ 
			thepanel.blnSettings = false; 
			thepanel.remove(settingsbackbutton);
			thepanel.remove(portnumber); 
			thepanel.remove(portnumber2);
			blnMenu = true;
		}
		if (e.getSource()==theclientbutton){
			strClientAddress = theclientfield.getText();
			thepanel.remove(thename); 
			System.out.println("button pressed");
			if (blnClient == true){ 
				ssm = new SuperSocketMaster(strClientAddress,intPortNumber,this);
				ssm2 = new SuperSocketMaster(strClientAddress,intPortNumber,this); 
				ssm.connect();
				ssm2.connect();
			}
			thepanel.blnConnection2 = false; 
			thepanel.blnGameboard = true;  
			blnGame = true; 
			thepanel.add(thebutton);
			thepanel.remove(theclientfield); 
			thepanel.remove(theclientbutton); 
			blnConnection = false; 
		}
		if (e.getSource()==thebutton) {
			thepanel.remove(thebutton);
			thepanel.add(thesendbutton);
			thepanel.add(thetextfield); 
			//thepanel.add(thearea);
			thepanel.add(thescroll); 
			blnActivate = true;
			blnSwap=false;
			if (blnClient==true) {
				blnTurn=false;
			}
			i=0;
			while (i<21) {
				strCoordinates.append(String.valueOf(thepanel.Piece[i].getIntX()) + "," + String.valueOf(thepanel.Piece[i].getIntY())+",");
				i++;
			}
			ssm.sendText(strCoordinates.toString());
			i=0;
		}else if (e.getSource() == thesendbutton){
			ssm2.sendText(strName+": "+thetextfield.getText()); 
			thearea.append(strName+": "+thetextfield.getText() + "\n");
			
		}else if (e.getSource()==ssm2) {
			thearea.append(ssm2.readText() + "\n");
		}else if (e.getSource()==ssm) {
			if (thepanel.blnReady==false) {
				strSplit=ssm.readText().split(",");
				while (i<21) {
					System.out.println(strSplit[j]+","+strSplit[j+1]);
					try{
						intEX=Integer.parseInt(strSplit[j]);
						intEY=Integer.parseInt(strSplit[j+1]);
					}catch(NumberFormatException n) {
						thepanel.EnPiece[i].setIntX(0);
						thepanel.EnPiece[i].setIntY(0);
					}
					// code for when ssm is fixed; used to determine location of enemy pieces.
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
					strSplit[0]=null;
					strSplit[1]=null;
				}
				
				j=0;
				thepanel.blnReady=true;
				//blnSwap=false;
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
					System.out.println("Win by getting to other side");
				}else if (thepanel.EnPiece[20].getIntY()==595){
					System.out.println("Lose by getting to other side");
				}
				blnTurn=true;
				i=0;
				blnWin=null;
				while(i<21) {
					//System.out.println("While loop az");
					if(thepanel.Piece[i].getIntX()==thepanel.EnPiece[intEnI].getIntX() && thepanel.Piece[i].getIntY()==thepanel.EnPiece[intEnI].getIntY()) {
						System.out.println("If statementaz");
						blnWin=thepanel.Piece[i].battle(thepanel.Piece[i].getIntRank(),thepanel.EnPiece[intEnI].getIntRank()); 
						System.out.println("Your: "+thepanel.Piece[i].getIntRank());
						System.out.println("Enemy: "+thepanel.EnPiece[intEnI].getIntRank());
						System.out.println("CPTG");
						System.out.println(blnWin[0] + " " + blnWin[1] + " " + blnWin[2] + " " + blnWin[3] + " " + blnWin[4]);
						if (blnWin[4]==true) {
							blnWin[3]=true;
							System.out.println("Both died");
							thepanel.Piece[i].setIntX(intDeadX);
							thepanel.Piece[i].setIntY(intDeadY);
						}else if (blnWin[2]==true) {
							System.out.println("Both die");
							thepanel.Piece[i].setIntX(intDeadX); 
							thepanel.Piece[i].setIntY(intDeadY);
							thepanel.blnDead = true;
							thepanel.EnPiece[intEnI].setIntX(intDeadX2); 
							thepanel.EnPiece[intEnI].setIntY(intDeadY2);
							intDeadX=intDeadX+90;
							intDeadX2=intDeadX2+90;
							if (intDeadX>1195) {
								intDeadY=intDeadY+90;
								intDeadX=840;
							}
							if (intDeadX2>1195) {
								intDeadY2=intDeadY2+90;
								intDeadX2=840;
							}
						}else if(blnWin[0] == false){
							System.out.println("Battle lose");
							thepanel.Piece[i].setIntX(intDeadX); 
							thepanel.Piece[i].setIntY(intDeadY);
							intDeadX = intDeadX + 90; 
							if (intDeadX>1195) {
								intDeadY=intDeadY+90;
								intDeadX=840;
							}
						}else if (blnWin[0] == true){ 
							System.out.println("Battle win");
							thepanel.blnDead = true;
							thepanel.EnPiece[intEnI].setIntX(intDeadX2); 
							thepanel.EnPiece[intEnI].setIntY(intDeadY2);
							intDeadX2 = intDeadX2+90;
							if (intDeadX2>1195) {
								intDeadY2=intDeadY2+90;
								intDeadX2=840;
							}
						} 
						if(blnWin[1] == true){ 
							//Win Screen 
							System.out.println("Win");
						}else if (blnWin[3] == true){ 
							//Lose Screen
							System.out.println("Lose");
						}
					}
					i++;
				}
			}
		}
	}
	public void mouseDragged(MouseEvent e) {
		
		if(intTemp!=-1) {
			thepanel.Piece[intTemp].setIntX(e.getX());
			thepanel.Piece[intTemp].setIntY(e.getY());
		}
	}
	public void mouseMoved (MouseEvent e) {
		
	}
	public void mouseClicked(MouseEvent e) {
		if (blnMenu == true){ 
			if(e.getX() >= 30 && e.getX() <= 597 && e.getY() >= 205 && e.getY() <= 282){ 
				thepanel.blnMenu = false; 
				thepanel.blnConnection = true;
				blnMenu = false; 
				blnConnection = true; 
				thepanel.add(thename);
				thepanel.add(playbackbutton);
				
			}else if(e.getX() >= 30 && e.getX() <= 597 && e.getY() >= 540 && e.getY() <= 617){ 
				theframe.dispatchEvent(new WindowEvent(theframe, WindowEvent.WINDOW_CLOSING));
			}else if(e.getX() >= 30 && e.getX() <= 597 && e.getY() >= 415 && e.getY() <= 492){ 
				thepanel.blnHelp = true;
				blnMenu = false; 
				
			}else if (e.getX() >= 30 && e.getX() <= 597 && e.getY() >= 310 && e.getY() <= 387){ 
				thepanel.blnSettings = true;
				thepanel.add(settingsbackbutton);
				thepanel.add(portnumber);
				thepanel.add(portnumber2);
				blnSettings = true;
				blnMenu = false;
			}  
		}
		if(thepanel.blnHelp == true){ 
			thepanel.add(helpquitbutton);
		}	
		if (blnConnection == true){ 
			if(e.getX() >= 138 && e.getX() <= 611 && e.getY() >= 390 && e.getY() <= 680){ 
				blnServer = true; 
				blnClient = false; 
				thepanel.blnConnection = false; 
				thepanel.blnGameboard = true;  
				blnGame = true; 
				thepanel.add(thebutton);
				thepanel.remove(thename); 
				thepanel.remove(playbackbutton); 
				blnConnection = false; 
				if (blnServer == true){ 
					thepanel.remove(playbackbutton);
					ssm = new SuperSocketMaster(intPortNumber,this);
					ssm2 = new SuperSocketMaster(intPortNumber,this);
					ssm.connect();
					ssm2.connect(); 
					theIP.setText("IP: "+ssm.getMyAddress());
					thepanel.add(theIP);
					System.out.println(ssm.getMyAddress());
				}
				
			}else if (e.getX() >= 720 && e.getX() < 1175 && e.getY() >= 390 && e.getY() <= 680){ 
				thepanel.remove(playbackbutton);
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
			if (intTempX>e.getX() && intTempX-thepanel.intDeltaBoard<=e.getX() && intTemp3==-1 && intTempY<=e.getY() && intTempY+thepanel.intDeltaPiece>e.getY()) {
				System.out.println("Left");
				thepanel.Piece[intTemp].setIntX(intTempX-thepanel.intDeltaBoard);
				thepanel.Piece[intTemp].setIntY(intTempY);
				ssm.sendText("L,"+String.valueOf(intTemp));
				blnTurn=false;
			}else if(intTempX+thepanel.intDeltaBoard<e.getX() && intTempX+(2*thepanel.intDeltaBoard)>=e.getX() && intTemp3==-1 && intTempY<=e.getY() && intTempY+thepanel.intDeltaPiece>e.getY()) {
				System.out.println("Right");
				thepanel.Piece[intTemp].setIntX(intTempX+thepanel.intDeltaBoard);
				thepanel.Piece[intTemp].setIntY(intTempY);
				ssm.sendText("R,"+String.valueOf(intTemp));
				blnTurn=false;
			}else if (intTempY>e.getY() && intTempY-thepanel.intDeltaBoard<=e.getY() && intTemp3==-1 && intTempX<=e.getX() && intTempX + thepanel.intDeltaPiece>e.getX()) {
				System.out.println("Up");
				thepanel.Piece[intTemp].setIntY(intTempY-thepanel.intDeltaBoard);
				thepanel.Piece[intTemp].setIntX(intTempX);
				ssm.sendText("U,"+String.valueOf(intTemp));
				blnTurn=false;
			}else if(intTempY+thepanel.intDeltaBoard<e.getY() && intTempY+(2*thepanel.intDeltaBoard)>=e.getY() && intTemp3==-1 && intTempX<=e.getX() && intTempX + thepanel.intDeltaPiece>e.getX()) {
				System.out.println("down");
				thepanel.Piece[intTemp].setIntY(intTempY+thepanel.intDeltaBoard);
				thepanel.Piece[intTemp].setIntX(intTempX);
				ssm.sendText("D,"+String.valueOf(intTemp));
				blnTurn=false;
			}else if (intTemp!=-1){
				System.out.println("Stay");
				thepanel.Piece[intTemp].setIntX(intTempX);
				thepanel.Piece[intTemp].setIntY(intTempY);
			}
			if (thepanel.Piece[20].getIntY()==0) {
				System.out.println("Win by getting to other side");
			}else if (thepanel.EnPiece[20].getIntY()==595){
				System.out.println("Lose by getting to other side");
			}
			
				
			i=0;
			while(i<21 && intTemp!=-1) {
				if(thepanel.Piece[intTemp].getIntX()==thepanel.EnPiece[i].getIntX() && thepanel.Piece[intTemp].getIntY()==thepanel.EnPiece[i].getIntY()) {
					blnWin=null;
					System.out.println(intTemp);
					blnWin=thepanel.Piece[intTemp].battle(thepanel.Piece[intTemp].getIntRank(),thepanel.EnPiece[i].getIntRank()); 
					System.out.println("Your: "+thepanel.Piece[intTemp].getIntRank());
					System.out.println("Enemy: "+thepanel.EnPiece[i].getIntRank());
					System.out.println("CPTG");
					System.out.println(blnWin[0] + " " + blnWin[1] + " " + blnWin[2]);
					if (blnWin[4]==true) {
						blnWin[1]=true;
						System.out.println("Both died");
						thepanel.EnPiece[i].setIntX(intDeadX2);
						thepanel.EnPiece[i].setIntY(intDeadY2);
					}else if (blnWin[2]==true) {
						System.out.println("Both die");
						thepanel.Piece[intTemp].setIntX(intDeadX); 
						thepanel.Piece[intTemp].setIntY(intDeadY);
						thepanel.blnDead = true;
						thepanel.EnPiece[i].setIntX(intDeadX2); 
						thepanel.EnPiece[i].setIntY(intDeadY2);
						intDeadX=intDeadX+90;
						intDeadX2=intDeadX2+90;
						if (intDeadX>1195) {
							intDeadY=intDeadY+90;
							intDeadX=840;
						}
						if (intDeadX2>1195) {
							intDeadY2=intDeadY2+90;
							intDeadX2=840;
						}
					}else if(blnWin[0] == false){
						System.out.println("Battle lose");
						thepanel.Piece[intTemp].setIntX(intDeadX); 
						thepanel.Piece[intTemp].setIntY(intDeadY);
						intDeadX = intDeadX + 90; 
						if (intDeadX>1195) {
							intDeadY=intDeadY+90;
							intDeadX=840;
						}
					}else if (blnWin[0] == true){ 
						System.out.println("Battle win");
						thepanel.blnDead = true;
						thepanel.EnPiece[i].setIntX(intDeadX2); 
						thepanel.EnPiece[i].setIntY(intDeadY2);
						intDeadX2 = intDeadX2+90;
						if (intDeadX2>1195) {
							intDeadY2=intDeadY2+90;
							intDeadX2=840;
						}
					}
					if(blnWin[1] == true){ 
						//Win Screen 
						System.out.println("Win");
					}else if (blnWin[3] == true){ 
						//Lose Screen
						System.out.println("Lose");
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
		thepanel = new CPTGanimation();
		thepanel.setLayout(null);
		thepanel.setPreferredSize(new Dimension(1280,780));
		thepanel.addMouseListener(this);
		thepanel.addMouseMotionListener(this);
		
		theframe = new JFrame("Game of Generals 1");
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
		
		playbackbutton = new JButton("Back"); 
		playbackbutton.setSize(300,100); 
		playbackbutton.setLocation(0,0); 
		playbackbutton.addActionListener(this);
		
		settingsbackbutton = new JButton("Back"); 
		settingsbackbutton.setSize(300,100); 
		settingsbackbutton.setLocation(980,620); 
		settingsbackbutton.addActionListener(this);
		
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

		portnumber = new JTextField(""); 
		portnumber.setSize(250,25); 
		portnumber.setLocation(289,170); 
		portnumber.addActionListener(this);
		
		portnumber2 = new JTextField(""); 
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

