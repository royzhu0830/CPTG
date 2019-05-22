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
	JButton thesendbutton; 
	JScrollPane thescroll; 
	JTextField thetextfield; 
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
	String strReady="not ready";
	String strTempPiece;
	String strEnemyX="";
	String strEnemyY="";
	String strSplit[] = new String[2];
	StringBuilder strCoordinates = new StringBuilder(21);
	String strSend="";
	SuperSocketMaster ssm;
	
	
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
			thepanel.blnReady=true;
			i=0;
			while (i<21) {
				strCoordinates.append(String.valueOf(thepanel.Piece[i].getIntX()) + "," + String.valueOf(thepanel.Piece[i].getIntY())+",");
				i++;
			}
			strSend=strCoordinates.toString();
			ssm.sendText(strSend);
			i=0;
		}else if (e.getSource() == thesendbutton){
			ssm.sendText(thetextfield.getText()); 
			thearea.append(thetextfield.getText() + "\n");
			
		}else if (e.getSource()==ssm) {
			thearea.append(ssm.readText() + "\n");
			strSplit=ssm.readText().split(",");
			while (i<21) {
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
					thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*7);
				}else if(intEX==thepanel.intDeltaBoard*1) {
					thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*6);
				}else if(intEX==thepanel.intDeltaBoard*2) {
					thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*5);
				}else if(intEX==thepanel.intDeltaBoard*3) {
					thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*4);
				}else if(intEX==thepanel.intDeltaBoard*4) {
					thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*3);
				}else if(intEX==thepanel.intDeltaBoard*5) {
					thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*2);
				}else if(intEX==thepanel.intDeltaBoard*6) {
					thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*1);
				}else if(intEX==thepanel.intDeltaBoard*7) {
					thepanel.EnPiece[i].setIntX(thepanel.intDeltaBoard*0);
				}
				i++;
				j=j+2;
				strSplit[0]=null;
				strSplit[1]=null;
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
		}else{
		//put restriction on piece movement in game here please sirsssssssss
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
		
		thesendbutton = new JButton("Send"); 
		thesendbutton.setSize(100,50); 
		thesendbutton.setLocation(800,600); 
		thesendbutton.addActionListener(this); 
		
		thetextfield = new JTextField("");
		thetextfield.setSize(325,75); 
		thetextfield.setLocation(700,500);
		thetextfield.addActionListener(this);
		
		thearea = new JTextArea(""); 
		thearea.setSize(325,300); 
		thearea.setLocation(700,175);
		
		thescroll = new JScrollPane(thearea);
		thescroll.setSize(325,300); 
		thescroll.setLocation(700,175);
		
		thetimer = new Timer(1000/60,this);
		thetimer.start();
		
		thepanel.add(thebutton);
		
		theframe.setContentPane(thepanel);
		theframe.pack();
		theframe.setResizable(true);
		theframe.setVisible(true);
		
		ssm=new SuperSocketMaster("192.168.0.17",1337,this);
		ssm.connect();
		System.out.println(ssm.getMyAddress());
	}
	public static void main(String[] args) {
		new CPTG2();
	}
}
//"192.168.0.17"
