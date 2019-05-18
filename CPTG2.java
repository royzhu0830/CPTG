import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

//game of the generals, 
/*ISSUES:
when click anywhere, automatic swap of 5G testing 123
*/
public class CPTG2 implements ActionListener, MouseMotionListener, MouseListener { //no timer has been set to repaint, so do that
	JFrame theframe;
	CPTGanimation thepanel;
	Timer thetimer;
	JButton thebutton;
	int intTemp=-1;
	int intTemp2;
	int intTemp3;
	int intTempX;
	int intTempY;
	int intLength;
	int i=0;
	int intRndNumbr;
	String strReady="not ready";
	String strTempPiece;
	String strEnemyX="";
	String strEnemyY="";
	String strSplit[];
	SuperSocketMaster ssm;
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==thetimer) {
			thepanel.repaint();
		}
		if (e.getSource()==thebutton) {
			thepanel.remove(thebutton);
			thepanel.blnReady=true;
		}else if (e.getSource()==ssm) {
			i=0;
			while (i<21 && thepanel.blnReady==true) {
				ssm.sendText(String.valueOf(thepanel.Piece[i].getIntX())+","+String.valueOf(thepanel.Piece[i].getIntY()));
				strSplit=ssm.readText().split("");
				System.out.println(strSplit[0] + " " + strSplit[1]);
				try{
					thepanel.EnPiece[i].setIntX(Integer.parseInt(strSplit[0]));
					thepanel.EnPiece[i].setIntY(Integer.parseInt(strSplit[1]));
				}catch(NumberFormatException n) {
					thepanel.EnPiece[i].setIntX(0);
					thepanel.EnPiece[i].setIntY(0);
				
				}
				// code for when ssm is fixed; used to determine location of enemy pieces.
				if (thepanel.EnPiece[i].getIntY()==thepanel.intDeltaBoard*5) {
					thepanel.EnPiece[i].setIntY(thepanel.intDeltaBoard*2);
				}else if (thepanel.EnPiece[i].getIntY()==thepanel.intDeltaBoard*6) {
					thepanel.EnPiece[i].setIntY(thepanel.intDeltaBoard*1);
				}else if (thepanel.EnPiece[i].getIntY()==thepanel.intDeltaBoard*7) {
					thepanel.EnPiece[i].setIntY(thepanel.intDeltaBoard*0);
				}
				for (int k=0; k<3; k++) {
					for (int j=0; j<8; j++) {
						for (int h=0; h<8; h++) {
							if (j+h==7) {
								thepanel.EnPiece[i].setIntX(thepanel.intXBoard[k][j]);
							}
						}
					}
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
	
	}
	public void mousePressed (MouseEvent e) {
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
		while (i<24) {
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
		if (thepanel.blnReady==false) {
			if (intTemp3!=-1 && intTemp==intTemp2) {
				intTemp2=intTemp3;
			}
			if (intTemp2!=-1 && intTemp!=-1) {
				thepanel.Piece[intTemp].setIntX(thepanel.Piece[intTemp2].getIntX());
				thepanel.Piece[intTemp].setIntY(thepanel.Piece[intTemp2].getIntY());
				thepanel.Piece[intTemp2].setIntX(intTempX);
				thepanel.Piece[intTemp2].setIntY(intTempY);
			}
			i=0;
			intTemp=-1;
			intTemp2=-1;
			intTemp3=-1;
			intTempX=-1;
			intTempY=-1;
		}else{
			//put restriction on piece movement in game here please sirsssssssss
		}
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
	public CPTG2 () {
		thepanel = new CPTGanimation();
		thepanel.setLayout(null);
		thepanel.setPreferredSize(new Dimension(1040,680));
		thepanel.addMouseListener(this);
		thepanel.addMouseMotionListener(this);
		
		theframe = new JFrame("Game of Generals");
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		thebutton = new JButton("Ready!");
		thebutton.setBounds(800,300,100,100);
		thebutton.addActionListener(this);
		
		thetimer = new Timer(1000/60,this);
		thetimer.start();
		
		thepanel.add(thebutton);
		
		theframe.setContentPane(thepanel);
		theframe.pack();
		theframe.setResizable(true);
		theframe.setVisible(true);
		
		ssm=new SuperSocketMaster("990.241.111.71",3000,this);
		ssm.connect();
	}
	public static void main(String[] args) {
		new CPTG2();
	}
}


