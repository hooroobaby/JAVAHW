//��ޤT_107403037_�L�~��
//�D�e��
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
	//�ŧinorthpanel����
	private final JPanel northpanel = new JPanel(new GridLayout(3, 2, 2, 2));
	private final JButton newFish = new JButton("�s�W��");
	private final JButton newTurtle = new JButton("�s�W�Q�t");
	private final JButton delChoose = new JButton("�R�����");
	private final JButton delAll = new JButton("�R������");
	private int button;
	private int fishnum=0;
	private int turtlenum=0;
	private final JLabel nowLabel = new JLabel("�ثe�\��G");
	private final JLabel numLabel = new JLabel("���ƶq�G      �Q�t�ƶq�G");
	//�ŧisouthpanel����
	private final SouthPanel southpanel = new SouthPanel();
	private ExecutorService executorService;
//	private Fish[] fishthread = new Fish[100];
	private ArrayList<Fish> fish = new ArrayList<Fish>();// ��class-Fish��bArrayList
	private ArrayList<Turtle> turtle = new ArrayList<Turtle>();// ��class-Fish��bArrayList

				/*------------------------------------------constructor------------------------------------------*/
	public Main() 
	{
		super("FishBowl");
	    setLayout(new BorderLayout());
	    // create ExecutorService to manage threads
	      executorService = Executors.newCachedThreadPool();
	    /*-------------�W�豱���-------------*/
	    //�s�W�����s
		northpanel.add(newFish);
		newFish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowLabel.setText(String.format("�ثe�\��G�s�W��"));
				button=1;//�ݷ|�|�Ψ�
			}
		});
		//�R����ܫ��s
		northpanel.add(delChoose);
		delChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowLabel.setText(String.format("�ثe�\��G�R�����"));
				button=2;
			}
		});
		//�s�W�Q�t���s
		northpanel.add(newTurtle);
		newTurtle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowLabel.setText(String.format("�ثe�\��G�s�W�Q�t"));
				button=3;
			}
		});
		//�R���������s
		northpanel.add(delAll);
		delAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowLabel.setText(String.format("�ثe�\��G�R������"));
				button=4;
				fish.clear();
				turtle.clear();
				southpanel.removeAll();
				repaint();
				fishnum=0;
				turtlenum=0;
				numLabel.setText(String.format("���ƶq�G" + fishnum + "�Q�t�ƶq" + turtlenum));
			}
		});
		//�]�w����C
		nowLabel.setForeground(Color.BLUE); //�]�w�r���C��
		numLabel.setForeground(Color.BLUE); //�]�w�r���C��
		northpanel.add(nowLabel);
		northpanel.add(numLabel);
		/*-------------frame�����t�m-----------------*/
		BorderLayout layout = new BorderLayout();
	    layout.setHgap(10);
	    layout.setVgap(10); 
		add(northpanel, layout.NORTH);
		add(southpanel, layout.SOUTH);
	} // end constructor
	
					/*------------------------------------------�U�賽��------------------------------------------*/
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
				case 1:{//�s�W��
					fish.add(new Fish(e.getPoint()));//��o�I�[�JFish��ArrayList
					//fish����stopExe�I�I�I�I�I�I�I�ոլ�
					
					executorService.execute(fish.get(fish.size()-1)); // execute/start ���[�JArrayList���I	
					southpanel.add(fish.get(fish.size()-1));//�[�Jpanel��
					fishnum++;//���ƶq++
					numLabel.setText(String.format("���ƶq�G" + fishnum + "�Q�t�ƶq�G" + turtlenum));//���ܤW�ƪ��A�C
					
					//�g�@��Listener�~��MouseHandler
					fish.get(fish.size()-1).addMouseListener(new DeleteHandler());
					//�ڤ]�����D������A�g�N��F�I�I�I�I�I�I�I
					
					break;
				}
				case 3:{//�s�W�Q�t
					turtle.add(new Turtle(e.getPoint()));//��o�I�[�JFish��ArrayList
					executorService.execute(turtle.get(turtle.size()-1)); // execute/start ���[�JArrayList���I	
					southpanel.add(turtle.get(turtle.size()-1));//�[�Jpanel��
					turtlenum++;
					numLabel.setText(String.format("���ƶq�G"+fishnum+"�Q�t�ƶq�G"+turtlenum));//���ܤW�ƪ��A�C
					
					//�g�@��Listener�~��MouseHandler
					turtle.get(turtle.size()-1).addMouseListener(new DeleteHandler());
					//�ڤ]�����D������A�g�N��F�I�I�I�I�I�I�I
					
					break;
				}
									
			}
			}
		}
		private class DeleteHandler extends MouseAdapter{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(button==2) {//�������
					JLabel remove1 = (JLabel) e.getSource();//�]���n�ϥ�panel��remove()�\��A�G�N���]��Label
					
					if(fish.contains(e.getSource())) 
					{//�Y�I�諸����O��arraylist�̭���
						fish.remove(e.getSource());//�����I��
						fishnum--;
						numLabel.setText(String.format("���ƶq�G" + fishnum + "�Q�t�ƶq" + turtlenum));
//						Fish.stopExecute();
					}
					else if(turtle.contains(e.getSource()))
					{
						turtle.remove(e.getSource());
						turtlenum--;
						numLabel.setText(String.format("���ƶq�G" + fishnum + "�Q�t�ƶq" + turtlenum));
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

