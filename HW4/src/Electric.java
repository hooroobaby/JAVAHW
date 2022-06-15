import java.io.IOException;

import javax.swing.JFrame;

//資管三_107403037_林驊萱 

public class Electric {
	public static void main(String args[]) throws IOException 
	{
		Main ele = new Main();
		((JFrame) ele).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ele.setSize(500, 500);
		ele.setVisible(true);
	}
}
