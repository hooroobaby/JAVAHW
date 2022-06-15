//Fish執行續
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.SecureRandom;
import java.util.Random;

import javax.imageio.*;
import javax.swing.*;

//隨機產生魚的大小、種類、運動方向、速度	
//在滑鼠指定（點擊）的位置新增魚的圖案 --> click()
//魚圖案能依其運動方向前進，接觸到邊界後就會往回游-->若游到>=邊界的地方 or 不再畫布內
//每隔一段時間，每隻魚都需要重新 random 速度及方向 -->Timer() or run()
//javax.swing.Timer --> Timer(int delay, ActionListener listener) --> 等待delay後，會start() listener

public class Fish extends JLabel implements Runnable {
	private final static SecureRandom generator = new SecureRandom();
	// 魚的圖片：135向右，246向左
	private ImageIcon[][] picture = {
			{ new ImageIcon("src/2.png"), new ImageIcon("src/4.png"), new ImageIcon("src/6.png") },
			{ new ImageIcon("src/1.png"), new ImageIcon("src/3.png"), new ImageIcon("src/5.png") } };
	private int size = 100;
	private int kind = 0;
	private int up;// 上下
	private int right = 0;// 左右
	private int distance;
	private Icon[] newImage = new ImageIcon[2];// 改變圖片大小後的Icon
	private static boolean exe = true;// 有沒有在執行
	private int x, y;
	private int countTime=0;

	public Fish(Point p) {
		size = generator.nextInt(75) + 50;
		kind = generator.nextInt(3);
		up = generator.nextInt(2);// 0是下，1是上
		right = generator.nextInt(2);// 0是左，1是右
//		speed(還不會)
		distance = generator.nextInt(6) + 1;// 1~6
		// 向左的Fish
		newImage[0] = new ImageIcon(picture[0][kind].getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
		// 向右的Fish
		newImage[1] = new ImageIcon(picture[1][kind].getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
		// 設定點選的x,y座標
		x = p.x;
		y = p.y;
		// 設定JLabel以用setIcon、setBounds功能
		setIcon(newImage[right]);
		setBounds(x, y, newImage[right].getIconWidth(), newImage[right].getIconHeight());
	}
	
	//可能用不到~
	public static void stopExecute() {
		exe = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (exe) {// 當沒有被停止時
			try {// 游泳
				Thread.sleep(generator.nextInt(25));// 25/1000秒喚醒一次
				//水平方向
				switch (right) {
					case 0: {// 向左
							if(x>0)
							{//若魚大於邊界0
								x-=distance;
							}
							else {
								right=1;//改方向
								setIcon(newImage[right]);
								x+=distance;
							}
					break;
					}
					case 1: {
						if(x<1000-newImage[right].getIconWidth())
						{//魚不超過邊界，繼續往右
							x+=distance;
						}
						else {
							right=0;//改方向
							setIcon(newImage[right]);
							x-=distance;
						}
					break;
					}
				}
				//垂直方向
				switch(up) {
					case 0:{//向下
						if(y<620-newImage[right].getIconHeight()/2)
						{
							y+=distance;
						}
						else {
							up=1;//改方向
							y-=distance;
						}
					break;
					}
					case 1:{//向上
						if(y>0)
						{
							y-=distance;
						}
						else {
							up=0;
							y+=distance;
						}
					break; 
					}
				}
				setLocation(x,y);
				countTime+=10;
			} catch (InterruptedException exception) {
				exception.printStackTrace();
				Thread.currentThread().interrupt(); // re-interrupt the thread
			}
			if(countTime==2000) {//要修正的：魚向右時怪怪
				up = generator.nextInt(2);// 0是下，1是上
//				right = generator.nextInt(2);// 0是左，1是右
				distance = generator.nextInt(3) + 1;// 1~6
				countTime=0;
			}
//			Timer change = new Timer(5000,new ActionListener(){
//				public void actionPerformed(ActionEvent e){//random 方向、速度
//					if(distance!=0){
//					if(right==1) {right=0;}
//					else {right=1;}
//					if(up==1) {up=0;}
//					else {up=1;}
//					distance = generator.nextInt(4) + 1;// 1~6
//					}
//				}	
//			});
//			change.start();
		}
	}
}
