import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

public class Bloodpanel extends JPanel{
	private Graphics2D g2d;
	private int lose = 0;
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);  
		g2d = (Graphics2D) g;
		g.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(3,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND));
		
		g.drawLine(0, 5, 500-lose, 5);
	}
		
	
	public void blood(int i)
	{
		if(500-lose>0)
		{
			if(i==1) 
			{
				System.out.println("®¥³ßÄ¹¤F¡I");
			}
			else
			{
				lose+=i;
			}
		}
		else
		{System.out.println("¿é¤F¡I¡I¡I");}
//			System.exit(0);
	}

}
