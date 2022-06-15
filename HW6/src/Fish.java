//Fish������
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.SecureRandom;
import java.util.Random;

import javax.imageio.*;
import javax.swing.*;

//�H�����ͳ����j�p�B�����B�B�ʤ�V�B�t��	
//�b�ƹ����w�]�I���^����m�s�W�����Ϯ� --> click()
//���Ϯׯ�̨�B�ʤ�V�e�i�A��Ĳ����ɫ�N�|���^��-->�Y���>=��ɪ��a�� or ���A�e����
//�C�j�@�q�ɶ��A�C�������ݭn���s random �t�פΤ�V -->Timer() or run()
//javax.swing.Timer --> Timer(int delay, ActionListener listener) --> ����delay��A�|start() listener

public class Fish extends JLabel implements Runnable {
	private final static SecureRandom generator = new SecureRandom();
	// �����Ϥ��G135�V�k�A246�V��
	private ImageIcon[][] picture = {
			{ new ImageIcon("src/2.png"), new ImageIcon("src/4.png"), new ImageIcon("src/6.png") },
			{ new ImageIcon("src/1.png"), new ImageIcon("src/3.png"), new ImageIcon("src/5.png") } };
	private int size = 100;
	private int kind = 0;
	private int up;// �W�U
	private int right = 0;// ���k
	private int distance;
	private Icon[] newImage = new ImageIcon[2];// ���ܹϤ��j�p�᪺Icon
	private static boolean exe = true;// ���S���b����
	private int x, y;
	private int countTime=0;

	public Fish(Point p) {
		size = generator.nextInt(75) + 50;
		kind = generator.nextInt(3);
		up = generator.nextInt(2);// 0�O�U�A1�O�W
		right = generator.nextInt(2);// 0�O���A1�O�k
//		speed(�٤��|)
		distance = generator.nextInt(6) + 1;// 1~6
		// �V����Fish
		newImage[0] = new ImageIcon(picture[0][kind].getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
		// �V�k��Fish
		newImage[1] = new ImageIcon(picture[1][kind].getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
		// �]�w�I�諸x,y�y��
		x = p.x;
		y = p.y;
		// �]�wJLabel�H��setIcon�BsetBounds�\��
		setIcon(newImage[right]);
		setBounds(x, y, newImage[right].getIconWidth(), newImage[right].getIconHeight());
	}
	
	//�i��Τ���~
	public static void stopExecute() {
		exe = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (exe) {// ��S���Q�����
			try {// ��a
				Thread.sleep(generator.nextInt(25));// 25/1000�����@��
				//������V
				switch (right) {
					case 0: {// �V��
							if(x>0)
							{//�Y���j�����0
								x-=distance;
							}
							else {
								right=1;//���V
								setIcon(newImage[right]);
								x+=distance;
							}
					break;
					}
					case 1: {
						if(x<1000-newImage[right].getIconWidth())
						{//�����W�L��ɡA�~�򩹥k
							x+=distance;
						}
						else {
							right=0;//���V
							setIcon(newImage[right]);
							x-=distance;
						}
					break;
					}
				}
				//������V
				switch(up) {
					case 0:{//�V�U
						if(y<620-newImage[right].getIconHeight()/2)
						{
							y+=distance;
						}
						else {
							up=1;//���V
							y-=distance;
						}
					break;
					}
					case 1:{//�V�W
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
			if(countTime==2000) {//�n�ץ����G���V�k�ɩǩ�
				up = generator.nextInt(2);// 0�O�U�A1�O�W
//				right = generator.nextInt(2);// 0�O���A1�O�k
				distance = generator.nextInt(3) + 1;// 1~6
				countTime=0;
			}
//			Timer change = new Timer(5000,new ActionListener(){
//				public void actionPerformed(ActionEvent e){//random ��V�B�t��
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
