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
	int intTemp=-1;
	int intTemp2;
	int intTemp3;
	int intTempX;
	int intTempY;
	int intLength;
	int i=0;
	int intRndNumbr;
	String strTempPiece;
	String strEnemyX="";
	String strEnemyY="";
	SuperSocketMaster ssm;
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==thetimer) {
			thepanel.repaint();
		}
		if (e.getSource()==thebutton) {
			thepanel.remove(thebutton);
			thepanel.blnReady=true;
			i=0;
			while (i<21) {
				ssm.sendText(String.valueOf(thepanel.Piece[i].getIntX()));
				ssm.sendText(String.valueOf(thepanel.Piece[i].getIntY()));
				strEnemyX=ssm.readText();
				strEnemyY=ssm.readText();
				try{
					thepanel.intXPiece[i]=Integer.parseInt(strEnemyX);
					thepanel.intYPiece[i]=Integer.parseInt(strEnemyY);
				}catch(NumberFormatException n) {
					thepanel.intXPiece[i]=0;
					System.out.println("X error");
					thepanel.intYPiece[i]=0;
					System.out.println("Y error");
				}
			}
			
			i=0;
			
		}
			
	}
	public void mouseDragged(MouseEvent e) {
		
		if(intTemp!=-1) {
			thepanel.intXPiece[intTemp]=e.getX();
			thepanel.intYPiece[intTemp]=e.getY();
		}
	}
	public void mouseMoved (MouseEvent e) {
		
	}
	public void mouseClicked(MouseEvent e) {
	
	}
	public void mousePressed (MouseEvent e) {
		while (i<21) {
			
			if (thepanel.intXPiece[i]<=e.getX() && thepanel.intXPiece[i] + thepanel.intDeltaPiece>e.getX() && thepanel.intYPiece[i]<=e.getY() && thepanel.intYPiece[i]+thepanel.intDeltaPiece>e.getY()) {
				intTemp=i;
				thepanel.blnFirstTime=false;
				intTempX=thepanel.intXPiece[intTemp];
				intTempY=thepanel.intYPiece[intTemp];
				break;
			}
			i++;
		}
		i=0;
	}
	public void mouseReleased(MouseEvent w) {
		while (i<24) {
			if (thepanel.intXPiece[i]<=w.getX() && thepanel.intXPiece[i] + thepanel.intDeltaBoard>w.getX() && thepanel.intYPiece[i]<=w.getY() && thepanel.intYPiece[i]+thepanel.intDeltaBoard>w.getY()) {
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
				thepanel.intXPiece[intTemp]=thepanel.intXPiece[intTemp2];
				thepanel.intYPiece[intTemp]=thepanel.intYPiece[intTemp2];
				thepanel.intXPiece[intTemp2]=intTempX;
				thepanel.intYPiece[intTemp2]=intTempY;
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
	public CPTG () {
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
		
		ssm=new SuperSocketMaster(3000,this);
		ssm.connect();
	}
	public static void main(String[] args) {
		new CPTG();
	}
}
