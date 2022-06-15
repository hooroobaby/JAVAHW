//turtle������
import java.awt.*;
import java.security.SecureRandom;
import javax.swing.*;

//�b�ƹ����w����m�s�W�Q�t�Ϯ�
//�Q�t�إ߫�|�U���ܩ����A�ۦa�� random �M�w���k��V�M�t��
public class Turtle extends JLabel implements Runnable{
	private final static SecureRandom generator = new SecureRandom();
//	private ImageIcon[] pictureroung = {new ImageIcon("src/turtle.png"),new ImageIcon("src/turtle.png")};
	private int right;
	private int distance;
	private Icon[] picture = {new ImageIcon("src/turtle2.png"),new ImageIcon("src/turtle.png")};//�Q�t���Ϥ��G�]���T�w�j�p�A�ҥH��Icon�N�n�C0�V���A1�V�k
	private int x,y;
	private boolean exe=true;
	public Turtle(Point p) {
		right = generator.nextInt(2);//0�O���A1�O�k
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
				if(y< 560) {//��y����̤U��
					y+=distance;
				}
				else {
					switch(right) {
						case 0:{//�V��
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
						case 1:{//�V�k
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
