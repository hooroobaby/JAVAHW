//資管三_107403037_林驊萱
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Scanner;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Main extends JFrame
{
	private JLabel[] maplist = new JLabel[200]; //宣告一個可裝載JLabel的陣列
	String map = "";
	public Main() throws IOException
	{
		//讀入檔案
		try
		{
			FileReader fr = new FileReader("src\\map.txt");
			BufferedReader br = new BufferedReader(fr);//使用BufferedReader方便讀取整行
			
			//設定字串map存取txt檔readline讀進來的內容
			while (br.ready())
				{map += br.readLine()+"\n";} //
			fr.close();
		}
		catch (IOException ioException)
	    {
	         ioException.printStackTrace();
	    }
		BorderLayout layout = new BorderLayout();
		
		Bloodpanel bloodpanel = new Bloodpanel();
		add(bloodpanel,layout.SOUTH);
		
		JPanel southpanel = new JPanel(new GridLayout(10,10));
		add(southpanel,layout.NORTH);
		Scanner s = new Scanner(map);
	
		int i=0;//當作maplist裡面的陣列參數
		while(s.hasNext())//當Scanner還有讀到map的東西(代表還有圖示)
		{
//			int n = Integer.parseInt(s.next());
			//xxxxxx 將Scsnner讀到(由txt轉過來的)map讀進maplist陣列中
			switch(s.nextInt())
			{
			case 0:{//道路1
				 maplist[i] = new JLabel(" ");//若第i個讀到的是0(道路)，將空白存入陣列
				 //扣血功能
				 addMouseListener(new MouseAdapter()
				 {
					// handle event when mouse enters area
						@Override
						public void mouseEntered(MouseEvent e)
						{
							bloodpanel.blood(5/100*500);//5/100*500
							repaint();
						}
				 });
				break;}
			case 1:{//牆壁
				
				int r = (int)(Math.random()*5)+1; //建立一個1-5的亂數
				
				if(r==4) //若亂數=4，變鬼
				{
					maplist[i] = new JLabel("",setghost(),SwingConstants.LEFT);
					//扣血功能
					addMouseListener(new MouseAdapter()
					{
					// handle event when mouse enters area
						@Override
						public void mouseEntered(MouseEvent e)
						{
							bloodpanel.blood(100/100*500);//100/100*500
							repaint();
						}
					});}
				
				else if(r==2) //若=2，變愛心
				{
					maplist[i]= new JLabel("",setheart(),SwingConstants.LEFT);
					//扣血功能
					addMouseListener(new MouseAdapter()
					{
					// handle event when mouse enters area
						@Override
						public void mouseEntered(MouseEvent e)
						{
							bloodpanel.blood(-30/100*500);//-30/100*500
							repaint();
						}
					});
				}
				
				else//1、3、5都是牆壁
				{
					maplist[i] = new JLabel("",setbrickwall(),SwingConstants.LEFT);
					//扣血功能
					addMouseListener(new MouseAdapter()
					{	
					// handle event when mouse enters area
						@Override
						public void mouseEntered(MouseEvent e)
						{
							bloodpanel.blood(20/100*500	);//20/100*500	
							repaint();
						}
					});
				}
				
				break;}
			case 2:{//出口
				maplist[i] = new JLabel("",setdiamond(),SwingConstants.LEFT);
				
				addMouseListener(new MouseAdapter()
				{	
				// handle event when mouse enters area
					@Override
					public void mouseEntered(MouseEvent e)
					{
						bloodpanel.blood(1);//20/100*500	
						repaint();
					}
				});
				break;}
			}//end switch
			southpanel.add(maplist[i]);
			i++;
		}//end while

		
				
	}
	//設定磚塊圖片()
	public static ImageIcon setbrickwall() throws IOException{
		try
		{
			ImageIcon brickwall = new ImageIcon(ImageIO.read(new File("src\\brickwall.png")));  
			Image image = brickwall.getImage(); 
			Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_AREA_AVERAGING);  
			brickwall = new ImageIcon(newimg);
			
			return brickwall;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
	//設定鑽石圖片()
	public static ImageIcon setdiamond(){
		try
		{
			ImageIcon diamond = new ImageIcon(ImageIO.read(new File("src\\diamond.png")));  
			Image image = diamond.getImage(); 
			Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_AREA_AVERAGING);  
			diamond = new ImageIcon(newimg);
			
			return diamond;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
	//設定鬼圖片()
	public static ImageIcon setghost() throws IOException{
		File fg = new File("src\\ghost.png");
		//設定圖片icon
//		Icon ghost = new ImageIcon(Main.class.getResource("ghost.png"));
		Icon ghost = new ImageIcon(ImageIO.read(fg));
		//設定圖片大小
		Image image = ((ImageIcon) ghost).getImage(); // transform it 
		Image newimg = image.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 
		ghost = new ImageIcon(newimg); // transform it back
		
		if (ghost == null) {throw new IllegalArgumentException("diamond == null!");}
		if (!fg.canRead())	 {throw new IIOException("Can't read input file!");}
		
		return (ImageIcon) ghost;
	}
	//設定愛心圖片()
	public static ImageIcon setheart() throws IOException{
		File fh = new File("src\\heart.png");
		//設定圖片icon
		Icon heart = new ImageIcon(ImageIO.read(fh));
		//設定圖片大小
		Image image = ((ImageIcon) heart).getImage(); // transform it 
		Image newimg = image.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 
		heart = new ImageIcon(newimg); // transform it back
		
		if (heart == null) {throw new IllegalArgumentException("heart == null!");}
		if (!fh.canRead())	 {throw new IIOException("Can't read input file!");}
		
		return (ImageIcon) heart;
	}
	}

