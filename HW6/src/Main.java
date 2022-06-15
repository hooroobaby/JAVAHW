//資管三_107403037_林驊萱
//主畫面
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Main extends JFrame {
	//宣告northpanel物件
	private final JPanel northpanel = new JPanel(new GridLayout(3, 2, 2, 2));
	private final JButton newFish = new JButton("新增魚");
	private final JButton newTurtle = new JButton("新增烏龜");
	private final JButton delChoose = new JButton("刪除選擇");
	private final JButton delAll = new JButton("刪除全部");
	private int button;
	private int fishnum=0;
	private int turtlenum=0;
	private final JLabel nowLabel = new JLabel("目前功能：");
	private final JLabel numLabel = new JLabel("魚數量：      烏龜數量：");
	//宣告southpanel物件
	private final SouthPanel southpanel = new SouthPanel();
	private ExecutorService executorService;
//	private Fish[] fishthread = new Fish[100];
	private ArrayList<Fish> fish = new ArrayList<Fish>();// 把class-Fish放在ArrayList
	private ArrayList<Turtle> turtle = new ArrayList<Turtle>();// 把class-Fish放在ArrayList

				/*------------------------------------------constructor------------------------------------------*/
	public Main() 
	{
		super("FishBowl");
	    setLayout(new BorderLayout());
	    // create ExecutorService to manage threads
	      executorService = Executors.newCachedThreadPool();
	    /*-------------上方控制區-------------*/
	    //新增魚按鈕
		northpanel.add(newFish);
		newFish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowLabel.setText(String.format("目前功能：新增魚"));
				button=1;//待會會用到
			}
		});
		//刪除選擇按鈕
		northpanel.add(delChoose);
		delChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowLabel.setText(String.format("目前功能：刪除選擇"));
				button=2;
			}
		});
		//新增烏龜按鈕
		northpanel.add(newTurtle);
		newTurtle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowLabel.setText(String.format("目前功能：新增烏龜"));
				button=3;
			}
		});
		//刪除全部按鈕
		northpanel.add(delAll);
		delAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowLabel.setText(String.format("目前功能：刪除全部"));
				button=4;
				fish.clear();
				turtle.clear();
				southpanel.removeAll();
				repaint();
				fishnum=0;
				turtlenum=0;
				numLabel.setText(String.format("魚數量：" + fishnum + "烏龜數量" + turtlenum));
			}
		});
		//設定控制列
		nowLabel.setForeground(Color.BLUE); //設定字體顏色
		numLabel.setForeground(Color.BLUE); //設定字體顏色
		northpanel.add(nowLabel);
		northpanel.add(numLabel);
		/*-------------frame版面配置-----------------*/
		BorderLayout layout = new BorderLayout();
	    layout.setHgap(10);
	    layout.setVgap(10); 
		add(northpanel, layout.NORTH);
		add(southpanel, layout.SOUTH);
	} // end constructor
	
					/*------------------------------------------下方魚缸------------------------------------------*/
	private class SouthPanel extends JPanel
	{
		public SouthPanel() {
			setBackground(new Color(176,224,230));
			setPreferredSize(new Dimension(1000, 650));
			addMouseListener(new MouseHandler());
//			addMouseMotionListener(new MouseMotionHandler());
		}
		private class MouseHandler extends MouseAdapter {
			@Override
			public void mousePressed(MouseEvent e)
			{
				switch(button) {
				case 1:{//新增魚
					fish.add(new Fish(e.getPoint()));//把這點加入Fish的ArrayList
					//fish那邊stopExe！！！！！！！試試看
					
					executorService.execute(fish.get(fish.size()-1)); // execute/start 剛剛加入ArrayList的點	
					southpanel.add(fish.get(fish.size()-1));//加入panel中
					fishnum++;//魚數量++
					numLabel.setText(String.format("魚數量：" + fishnum + "烏龜數量：" + turtlenum));//改變上排狀態列
					
					//寫一個Listener繼承MouseHandler
					fish.get(fish.size()-1).addMouseListener(new DeleteHandler());
					//我也不知道為什麼，寫就對了！！！！！！！
					
					break;
				}
				case 3:{//新增烏龜
					turtle.add(new Turtle(e.getPoint()));//把這點加入Fish的ArrayList
					executorService.execute(turtle.get(turtle.size()-1)); // execute/start 剛剛加入ArrayList的點	
					southpanel.add(turtle.get(turtle.size()-1));//加入panel中
					turtlenum++;
					numLabel.setText(String.format("魚數量："+fishnum+"烏龜數量："+turtlenum));//改變上排狀態列
					
					//寫一個Listener繼承MouseHandler
					turtle.get(turtle.size()-1).addMouseListener(new DeleteHandler());
					//我也不知道為什麼，寫就對了！！！！！！！
					
					break;
				}
									
			}
			}
		}
		private class DeleteHandler extends MouseAdapter{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(button==2) {//移除選擇
					JLabel remove1 = (JLabel) e.getSource();//因為要使用panel的remove()功能，故將此設為Label
					
					if(fish.contains(e.getSource())) 
					{//若點選的物件是魚arraylist裡面的
						fish.remove(e.getSource());//移除點選
						fishnum--;
						numLabel.setText(String.format("魚數量：" + fishnum + "烏龜數量" + turtlenum));
//						Fish.stopExecute();
					}
					else if(turtle.contains(e.getSource()))
					{
						turtle.remove(e.getSource());
						turtlenum--;
						numLabel.setText(String.format("魚數量：" + fishnum + "烏龜數量" + turtlenum));
//						Fish.stopExecute();
					}
					southpanel.remove(remove1);
					remove1.setVisible(false);
					southpanel.repaint();
			}
			}
		}
	}
}

