import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

//game of the generals, 
/*ISSUES:
when click anywhere, automatic swap of 5G testing 123
*/
public class CPTG implements ActionListener, MouseMotionListener, MouseListener { //no timer has been set to repaint, so do that
	JFrame theframe;
	CPTGanimation thepanel;
	Timer thetimer;
	JButton thebutton;
	JButton thesendbutton; 
	JButton theclientbutton; 
	JScrollPane thescroll; 
	JTextField thetextfield; 
	JTextField theclientfield; 
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
	int intDeadX = 890; 
	int intDeadY = 10;
	int intDeadX2 = 890; 
	int intDeadY2 = 110; 
	boolean blnTurn=true;
	boolean blnWin[] = new boolean[3];
	boolean blnActivate = false;
	String strClientAddress; 
	String strTempPiece;
	String strEnemyX="";
	String strEnemyY="";
	String strSplit[] = new String[2];
	StringBuilder strCoordinates = new StringBuilder(21);
	SuperSocketMaster ssm;
	boolean blnMenu = true; 
	boolean blnGame = false; 
	boolean blnConnection = false; 
	boolean blnServer = false; 
	boolean blnClient = false; 

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==thetimer) {
			thepanel.repaint();
		}
		if (e.getSource()==thebutton) {
			thepanel.remove(thebutton);
			thepanel.add(thesendbutton);
			thepanel.add(thetextfield); 
			//thepanel.add(thearea);
			thepanel.add(thescroll); 
			blnActivate = true;
			i=0;
			while (i<21) {
				strCoordinates.append(String.valueOf(thepanel.Piece[i].getIntX()) + "," + String.valueOf(thepanel.Piece[i].getIntY())+",");
				i++;
			}
			ssm.sendText(strCoordinates.toString());
			i=0;
		}else if (e.getSource() == thesendbutton){
			ssm.sendText(thetextfield.getText()); 
			thearea.append(thetextfield.getText() + "\n");
			
		}else if (e.getSource()==ssm) {
			thearea.append(ssm.readText() + "\n");
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
				blnTurn=true;
			}
		}
		if (blnClient == true){ 
			strClientAddress = theclientfield.getText(); 
			//System.out.println(""+strClientAddress); 
			
		}
	}
	public void mouseDragged(MouseEvent e) {
		
		if(intTemp!=-1 && blnTurn==true) {
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
			}	
		}
		if (blnConnection == true){ 
			if(e.getX() >= 138 && e.getX() <= 611 && e.getY() >= 390 && e.getY() <= 680){ 
				thepanel.blnConnection = false; 
				thepanel.blnGameboard = true;  
				blnGame = true; 
				thepanel.add(thebutton);
				blnConnection = false; 
				blnServer = true; 
			}else if (e.getX() >= 720 && e.getX() < 1175 && e.getY() >= 390 && e.getY() <= 680){ 
				thepanel.blnConnection = false; 
				thepanel.blnClient = true; 
				blnConnection = false; 
				blnServer = false; 
				blnClient = true; 
				thepanel.add(theclientfield); 
				thepanel.add(theclientbutton); 
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
		if (thepanel.blnReady==false) {
			while (i<27) {
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
		}else if(thepanel.blnReady==true && blnTurn==true){
			i=0;
			while (i<21) {
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
			i=0;
			while(i<21) {
				if(thepanel.Piece[intTemp].getIntX()==thepanel.EnPiece[i].getIntX() && thepanel.Piece[intTemp].getIntY()==thepanel.EnPiece[i].getIntY()) {
					System.out.println(intTemp);
					blnWin=thepanel.Piece[intTemp].battle(thepanel.Piece[intTemp].getIntRank(),thepanel.EnPiece[i].getIntRank()); 
					System.out.println("Your: "+thepanel.Piece[intTemp].getIntRank());
					System.out.println("Enemy: "+thepanel.EnPiece[i].getIntRank());
					System.out.println("CPTG");
					if(blnWin[0] == false){
						thepanel.Piece[intTemp].setIntX(intDeadX); 
						thepanel.Piece[intTemp].setIntY(intDeadY);
						ssm.sendText(String.valueOf(intTemp)+",E");
						intDeadX = intDeadX + 10; 
					
						break;
				
					}else if (blnWin[0] == true){ 
						thepanel.blnDead = true;
						thepanel.EnPiece[i].setIntX(2000); 
						thepanel.EnPiece[i].setIntY(2000);
						ssm.sendText(String.valueOf(intTemp)+",O");
						break;
					}
					if(blnWin[1] == true){ 
						//Win Screen 
						System.out.println("Win");
					}else if (blnWin[1] == false){ 
						//Lose Screen
						System.out.println("Lose");
					}
					if(blnWin[2] == true){ 
						thepanel.Piece[intTemp].setIntX(intDeadX); 
						thepanel.Piece[intTemp].setIntY(10);
						intDeadX = intDeadX + 10;  
						ssm.sendText(String.valueOf(intTemp)+",B");
					}
					
				}
			i++;
			}
			
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
		
		theclientbutton = new JButton ("Connect"); 
		theclientbutton.setSize(100,50); 
		theclientbutton.setLocation(600,625); 

		thetextfield = new JTextField("");
		thetextfield.setSize(250,25); 
		thetextfield.setLocation(770,600);
		thetextfield.addActionListener(this);

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
		
		if (blnServer == true){ 
			ssm = new SuperSocketMaster(1337,this);
			ssm.connect();
			System.out.println(ssm.getMyAddress());
		}
		if (blnClient == true){ 
			ssm = new SuperSocketMaster(""+strClientAddress,1337,this); 
			ssm.connect();
		}
	}
	public static void main(String[] args) {
		new CPTG();
	}
}
//"192.168.2.21"

