//林驊萱_107403037_資管3B

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel Panel1;
	private final JPanel Panel2;
	private final JPanel Panel3;
	
	private final JLabel label1;
	private final JLabel label2;
	private final JLabel label3;
	   
	private final JComboBox<String> toolsComboBox;
	private static final String[] tools = {"筆刷","直線","橢圓形","矩形","圓角矩形"};

	private final JRadioButton small;
	private final JRadioButton medium;
	private final JRadioButton big;
	private final ButtonGroup group;
	
	private final JCheckBox fill;
	
	private final JButton pen;
	private final JButton clean;
	
	private final JPanel mousePanel;
	private final JLabel statusbar;
	
	public MainFrame()
	{
		super("小畫家");
		setLayout(new FlowLayout()); // set frame layout
		
		//設定圖片icon
		Icon icon = new ImageIcon(getClass().getResource("logo.png"));
		//設定圖片大小
		Image image = ((ImageIcon) icon).getImage(); // transform it 
		Image newimg = image.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 
		icon = new ImageIcon(newimg); // transform it back
		//設定訊息
		JOptionPane.showMessageDialog(null,"Welcome","訊息",
				JOptionPane.DEFAULT_OPTION,icon);
		//北
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER,150,10));
		
		//Combo Box 繪圖工具
		Panel1 = new JPanel();
		Panel1.setLayout(new GridLayout(2,1));
		Panel1.setPreferredSize(new Dimension(200,50));
		//Panel1.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
		
		label1 = new JLabel("繪圖工具");
		
		toolsComboBox = new JComboBox<String>(tools);
		
		Panel1.add(label1);
		Panel1.add(toolsComboBox);
		add(Panel1);
		northPanel.add(Panel1);
		
		toolsComboBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if(event.getStateChange() == ItemEvent.SELECTED)
					System.out.printf("選擇%s\n",toolsComboBox.getSelectedItem());
			}
			
		}
		);//end call to addItemListener
		
		
		//Radio Button 筆刷大小
		Panel2 = new JPanel();
		Panel2.setLayout(new GridLayout(2,3));
		Panel2.setPreferredSize(new Dimension(200,50));
		
		label2 = new JLabel("筆刷大小");
		JLabel n = new JLabel("");
		JLabel n1 = new JLabel("");
		Panel2.add(label2);
		Panel2.add(n);
		Panel2.add(n1);
		
		small = new JRadioButton("小",true);
		medium = new JRadioButton("中",false);
		big = new JRadioButton("大",false);
		
		group = new ButtonGroup();
		group.add(small);
		group.add(medium);
		group.add(big);
		
		Panel2.add(small);
		Panel2.add(medium);
		Panel2.add(big);
		add(Panel2);
		northPanel.add(Panel2);
		
		small.addItemListener(new RadioButtonHandler("小"));
		medium.addItemListener(new RadioButtonHandler("中"));
		big.addItemListener(new RadioButtonHandler("大"));
		
		//Check Box 填滿
		Panel3 = new JPanel();
		Panel3.setLayout(new GridLayout(2,1));
		Panel3.setPreferredSize(new Dimension(200,50));
		
		label3 = new JLabel("填滿");
		fill = new JCheckBox();
		add(label3);
		add(fill);
		Panel3.add(label3);
		Panel3.add(fill);
		add(Panel3);
		northPanel.add(Panel3);
		
		fill.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(fill.isSelected())
					System.out.printf("選擇 填滿\n");
				else
					System.out.printf("取消 填滿\n");
			}
		}
		);//end of CheckBox
		
		//Button （筆刷顏色、清除畫面）
		pen = new JButton("筆刷顏色");
		clean = new JButton("清除畫面");
		add(pen);
		add(clean);
		northPanel.add(pen);
		northPanel.add(clean);
		
		pen.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{System.out.println("點選 筆刷顏色");}
		}
		);//end call to addItemListener
		
		clean.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{System.out.println("點選 清除畫面");}
		}
		);//end call to addItemListener	
		
		//游標位置偵測
		mousePanel = new JPanel();
		mousePanel.setPreferredSize(new Dimension(2600,870));
		mousePanel.setBackground(Color.WHITE); 
	    add(mousePanel);
		
	    statusbar = new JLabel("指標位置",SwingConstants.LEFT); 
	    statusbar.setForeground(Color.WHITE);
	    add(statusbar);
	    JPanel s = new JPanel();
	    s.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	    s.setBackground(Color.BLACK);
	    s.setPreferredSize(new Dimension(1900,50));
	    s.add(statusbar,BorderLayout.SOUTH);
	    
	    
	    MouseHandler handler1 = new MouseHandler(); 
	    mousePanel.addMouseListener(handler1); 
	    mousePanel.addMouseMotionListener(handler1); 
	    
	    /*northPanel.setLayout(new BorderLayout());
	    mousePanel.setLayout(new BorderLayout());
	    s.setLayout(new BorderLayout());*/
		
		BorderLayout layout = new BorderLayout();
	    layout.setHgap(10);
	    layout.setVgap(10); 
		
		add(northPanel, layout.NORTH);
	    add(mousePanel, layout.CENTER); // add panel to JFrame
	    add(s, layout.SOUTH); // add label to JFrame

	}
	
	private class RadioButtonHandler implements ItemListener
	{
		private String size;
		
		public RadioButtonHandler(String s)
		{
			size = s;
		}
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange() == ItemEvent.SELECTED)
			System.out.printf("選擇 %s 筆刷\n",size);
		}
	}//end RadioButtonHandler

			
	private class MouseHandler implements MouseListener, 
    MouseMotionListener 
 {
    // MouseListener event handlers
    // handle event when mouse released immediately after press
    @Override
    public void mouseClicked(MouseEvent event)
    {
       statusbar.setText(String.format("Clicked at [%d, %d]", 
          event.getX(), event.getY()));
    } 

    // handle event when mouse pressed
    @Override
    public void mousePressed(MouseEvent event)
    {
       statusbar.setText(String.format("Pressed at [%d, %d]", 
          event.getX(), event.getY()));
    }

    // handle event when mouse released 
    @Override
    public void mouseReleased(MouseEvent event)
    {
       statusbar.setText(String.format("Released at [%d, %d]", 
          event.getX(), event.getY()));
    }

    // handle event when mouse enters area
    @Override
    public void mouseEntered(MouseEvent event)
    {
       statusbar.setText(String.format("Mouse entered at [%d, %d]", 
          event.getX(), event.getY()));
       mousePanel.setBackground(Color.WHITE);
    }

    // handle event when mouse exits area
    @Override
    public void mouseExited(MouseEvent event)
    {
       statusbar.setText("Mouse outside JPanel");
       mousePanel.setBackground(Color.WHITE);
    }

    // MouseMotionListener event handlers
    // handle event when user drags mouse with button pressed
    @Override
    public void mouseDragged(MouseEvent event)
    {
       statusbar.setText(String.format("Dragged at [%d, %d]", 
          event.getX(), event.getY()));
    } 

    // handle event when user moves mouse
    @Override
    public void mouseMoved(MouseEvent event)
    {
       statusbar.setText(String.format("指標位置： (%d, %d)", 
          event.getX(), event.getY()));
    } 
	
	
 }
}//the end class MainFrame
