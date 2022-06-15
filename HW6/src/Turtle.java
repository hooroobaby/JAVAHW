//turtle執行續
import java.awt.*;
import java.security.SecureRandom;
import javax.swing.*;

//在滑鼠指定的位置新增烏龜圖案
//烏龜建立後會下降至底部，著地後 random 決定左右方向和速度
public class Turtle extends JLabel implements Runnable{
	private final static SecureRandom generator = new SecureRandom();
//	private ImageIcon[] pictureroung = {new ImageIcon("src/turtle.png"),new ImageIcon("src/turtle.png")};
	private int right;
	private int distance;
	private Icon[] picture = {new ImageIcon("src/turtle2.png"),new ImageIcon("src/turtle.png")};//烏龜的圖片：因為固定大小，所以用Icon就好。0向左，1向右
	private int x,y;
	private boolean exe=true;
	public Turtle(Point p) {
		right = generator.nextInt(2);//0是左，1是右
		distance = generator.nextInt(5)+1;
		x = p.x;
		y = p.y;
		setIcon(picture[right]);
		setBounds(x,y,picture[right].getIconWidth(),picture[right].getIconHeight());
	}
	
	public void stopExecute() {
		exe = false;
	}
	
	@Override
	public void run() {
		while(exe) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(generator.nextInt(20));
				if(y< 560) {//讓y掉到最下面
					y+=distance;
				}
				else {
					switch(right) {
						case 0:{//向左
							if(x>0)
							{
								x-=distance;
							}
							else {
								right=1;
								setIcon(picture[right]);
								x+=distance;
							}
							break;
						}
						case 1:{//向右
							if(x<1000-picture[right].getIconWidth()) {
								x+=distance;
							}
							else {
								right=0;
								setIcon(picture[right]);
								x-=distance;
							}
						}
					}
				}
					setLocation(x,y);
				
	
			}
			catch(InterruptedException exception) {
				exception.printStackTrace();
				Thread.currentThread().interrupt(); // re-interrupt the thread
			}
		}
	}
}
