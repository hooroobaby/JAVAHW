//��ޤT_107403037_�L�~��
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
	private JLabel[] maplist = new JLabel[200]; //�ŧi�@�ӥi�˸�JLabel���}�C
	String map = "";
	public Main() throws IOException
	{
		//Ū�J�ɮ�
		try
		{
			FileReader fr = new FileReader("src\\map.txt");
			BufferedReader br = new BufferedReader(fr);//�ϥ�BufferedReader��KŪ�����
			
			//�]�w�r��map�s��txt��readlineŪ�i�Ӫ����e
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
	
		int i=0;//��@maplist�̭����}�C�Ѽ�
		while(s.hasNext())//��Scanner�٦�Ū��map���F��(�N���٦��ϥ�)
		{
//			int n = Integer.parseInt(s.next());
			//xxxxxx �NScsnnerŪ��(��txt��L�Ӫ�)mapŪ�imaplist�}�C��
			switch(s.nextInt())
			{
			case 0:{//�D��1
				 maplist[i] = new JLabel(" ");//�Y��i��Ū�쪺�O0(�D��)�A�N�ťզs�J�}�C
				 //����\��
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
			case 1:{//���
				
				int r = (int)(Math.random()*5)+1; //�إߤ@��1-5���ü�
				
				if(r==4) //�Y�ü�=4�A�ܰ�
				{
					maplist[i] = new JLabel("",setghost(),SwingConstants.LEFT);
					//����\��
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
				
				else if(r==2) //�Y=2�A�ܷR��
				{
					maplist[i]= new JLabel("",setheart(),SwingConstants.LEFT);
					//����\��
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
				
				else//1�B3�B5���O���
				{
					maplist[i] = new JLabel("",setbrickwall(),SwingConstants.LEFT);
					//����\��
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
			case 2:{//�X�f
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
	//�]�w�j���Ϥ�()
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
	//�]�w�p�۹Ϥ�()
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
	//�]�w���Ϥ�()
	public static ImageIcon setghost() throws IOException{
		File fg = new File("src\\ghost.png");
		//�]�w�Ϥ�icon
//		Icon ghost = new ImageIcon(Main.class.getResource("ghost.png"));
		Icon ghost = new ImageIcon(ImageIO.read(fg));
		//�]�w�Ϥ��j�p
		Image image = ((ImageIcon) ghost).getImage(); // transform it 
		Image newimg = image.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 
		ghost = new ImageIcon(newimg); // transform it back
		
		if (ghost == null) {throw new IllegalArgumentException("diamond == null!");}
		if (!fg.canRead())	 {throw new IIOException("Can't read input file!");}
		
		return (ImageIcon) ghost;
	}
	//�]�w�R�߹Ϥ�()
	public static ImageIcon setheart() throws IOException{
		File fh = new File("src\\heart.png");
		//�]�w�Ϥ�icon
		Icon heart = new ImageIcon(ImageIO.read(fh));
		//�]�w�Ϥ��j�p
		Image image = ((ImageIcon) heart).getImage(); // transform it 
		Image newimg = image.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 
		heart = new ImageIcon(newimg); // transform it back
		
		if (heart == null) {throw new IllegalArgumentException("heart == null!");}
		if (!fh.canRead())	 {throw new IIOException("Can't read input file!");}
		
		return (ImageIcon) heart;
	}
	}

