package code;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 描画を行うクラス
 * @author inory
 *
 */
public class pyBoardView extends JPanel implements MouseListener{
	
	private int width;
	private int height;
	private int bWidth;
	private int bHeight;
	private drawPyramid dPy;
	
	pyBoardView(drawPyramid dpy){
		dPy = dpy;
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
		width = this.getWidth();
		height = this.getHeight();
		bHeight = height/9;
		bWidth = (width-bHeight*2)/9;
		int sX[]= {bHeight,bHeight, bHeight+bWidth,bHeight+bWidth, bHeight+bWidth*2,bHeight+bWidth*2, bHeight+bWidth*3,bHeight+bWidth*3,bHeight+bWidth*6,bHeight+bWidth*6};
		int sY[]= {bHeight*8, bHeight*6,bHeight*6, bHeight*5,bHeight*5, bHeight*4,bHeight*4, bHeight*3,bHeight*3, bHeight*8};
		int cX[]= {bHeight+bWidth*6,bHeight+bWidth*6,bHeight+bWidth*8,bHeight+bWidth*8, bHeight+bWidth*9,bHeight+bWidth*9};
		int cY[]= {bHeight*8, bHeight*5,bHeight*5, bHeight*6,bHeight*6, bHeight*8};
	    Graphics2D g2 = (Graphics2D)g;
		
	    Rectangle2D shape = new Rectangle2D.Double(bHeight+bWidth*4,bHeight,bWidth*2,bHeight*2);
	    Polygon spolygon = new Polygon(sX,sY,10);
	    Polygon cpolygon = new Polygon(cX,cY,6);
	    g2.setPaint(Color.white);
	    g2.fill(shape);
	    g2.setColor(Color.white);
	    g2.fill(spolygon);
	    g2.setColor(new Color(242, 247, 169));
	    g2.fill(cpolygon);
	    g2.setColor(Color.black);
	    g2.setStroke(new BasicStroke(4.0f));
	    g2.drawPolygon(spolygon);
	    g2.drawPolygon(cpolygon);
	    g2.draw(shape);

	    g2.setStroke(new BasicStroke(2.0f));
	    g2.drawLine(bHeight+bWidth, bHeight*7, bHeight+bWidth*8,bHeight*7 );
	    g2.drawLine(bHeight+bWidth*2, bHeight*6, bHeight+bWidth*7,bHeight*6 );
	    g2.drawLine(bHeight+bWidth*3, bHeight*5, bHeight+bWidth*6,bHeight*5 );
	    g2.drawLine(bHeight+bWidth*4, bHeight*4, bHeight+bWidth*6,bHeight*4 );
	    g2.drawLine(bHeight+bWidth*4, bHeight*2, bHeight+bWidth*6,bHeight*2 );
	    
	    
	    //setCharacter
	    g2.setColor(Color.black);
	    Font fm = new Font("Serif",Font.PLAIN,bHeight*3<bWidth?bHeight/2:bWidth/5);
	    g2.setFont(fm);
	    g2.drawString("CYCLO", bHeight+4, bHeight*8-bHeight/4);
	    g2.drawString("LOC", bHeight+bWidth+4,bHeight*7-bHeight/4);
	    g2.drawString("NOM", bHeight+bWidth*2+4,bHeight*6-bHeight/4);
	    g2.drawString("NOC", bHeight+bWidth*3+4,bHeight*5-bHeight/4);
	    g2.drawString("NOP", bHeight+bWidth*4+4,bHeight*4-bHeight/4);
	    g2.drawString("AHH", bHeight+bWidth*4+4,bHeight*3-bHeight/4);
	    g2.drawString("ANDC", bHeight+bWidth*4+4,bHeight*2-bHeight/4);
	    g2.drawString("NOM", bHeight+bWidth*6+4, bHeight*6-bHeight/4);
	    g2.drawString("CALLS", bHeight+bWidth*7+4,bHeight*7-bHeight/4);
	    g2.drawString("FANOUT", bHeight+bWidth*8+4,bHeight*8-bHeight/4);
	    g2.drawString(String.format("%.5f", dPy.cyclo), bHeight+bWidth*5+4,bHeight*8-bHeight/4);
	    g2.drawString(String.format("%.5f", dPy.loc), bHeight+bWidth*5+4,bHeight*7-bHeight/4);
	    g2.drawString(String.format("%.5f", dPy.nom), bHeight+bWidth*5+4,bHeight*6-bHeight/4);
	    g2.drawString(String.format("%.5f", dPy.noc), bHeight+bWidth*5+4,bHeight*5-bHeight/4);
	    g2.drawString(String.format("%.5f", dPy.nop), bHeight+bWidth*5+4,bHeight*4-bHeight/4);
	    g2.drawString(String.format("%.5f", dPy.ahh), bHeight+bWidth*5+4,bHeight*3-bHeight/4);
	    g2.drawString(String.format("%.5f", dPy.andc), bHeight+bWidth*5+4,bHeight*2-bHeight/4);
	    g2.drawString(String.format("%.5f", dPy.calls), bHeight+bWidth*6+4,bHeight*7-bHeight/4);
	    g2.drawString("--", bHeight+bWidth*6+4,bHeight*8-bHeight/4);
	    

	    g2.drawString(String.format("%.5f", dPy.cyclo/dPy.loc), bHeight+4,bHeight*7-bHeight/4);
	    g2.drawString(String.format("%.5f", dPy.loc/dPy.nom), bHeight+bWidth+4,bHeight*6-bHeight/4);
	    g2.drawString(String.format("%.5f", dPy.nom/dPy.noc), bHeight+bWidth*2+4,bHeight*5-bHeight/4);
	    g2.drawString(String.format("%.5f", dPy.noc/dPy.nop), bHeight+bWidth*3+4,bHeight*4-bHeight/4);
		g2.drawString("--", bHeight+bWidth*8+4,bHeight*7-bHeight/4);

	    g2.drawString(String.format("%.5f", dPy.calls/dPy.nom), bHeight+bWidth*7+4,bHeight*6-bHeight/4);
	    
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
