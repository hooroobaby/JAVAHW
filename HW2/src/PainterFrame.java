//林驊萱_107403037_資管3B

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import javax.swing.*;

import java.util.*;
import java.util.List;

public class PainterFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final JPanel Panel1;
	private final JPanel Panel2;
	private final JPanel Panel3;
	
	private final JLabel label1;
	private final JLabel label2;
	private final JLabel label3;
	   
	public static JComboBox<String> toolsComboBox;
	private static final String[] tools = {"筆刷","直線","橢圓形","矩形","圓角矩形","3D矩形"};

	private final JRadioButton small;
	private final JRadioButton medium;
	private final JRadioButton big;
	private final ButtonGroup group;
	public static float paintsize = 2;
	
	private final JCheckBox fill;
	public static boolean fillcheck;
	
	private final JButton pen;
	public static Color color = Color.BLACK;
	private final JButton eraser;
	private final JButton clean;
	private int clear;
	private final JButton back;
	public static Color backColor = Color.WHITE;
	
	private MousePanel mousePanel = new MousePanel();
	private final  JLabel statusbar;
	
	public static int startpntx;
	public static int startpnty;
	public static int endpntx;
	public static int endpnty;
	private BufferedImage image;
	
	private final ArrayList<Point> points = new ArrayList<>(); 
	private final ArrayList<Color> color1 = new ArrayList<>();
	private final ArrayList<Integer> paintsize1 = new ArrayList<>();
	
	public PainterFrame()
	{
		super("小畫家");
		setLayout(new BorderLayout()); // set frame layout
		
		/*
		 *Container cp = this.getContentPane();
		 *this.getContentPane().setVisible(true);
		*/
		
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
		//northPanel.setLayout(new FlowLayout(FlowLayout.CENTER,150,10));
//		northPanel.setPreferredSize(new Dimension(2600,80));
		
/********************************************************Combo Box 繪圖工具***********************************************************/
		Panel1 = new JPanel();
		Panel1.setLayout(new GridLayout(2,1));
//		Panel1.setPreferredSize(new Dimension(200,50));
		//Panel1.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
		
		label1 = new JLabel("繪圖工具");
		
		toolsComboBox = new JComboBox<String>(tools);
		
		Panel1.add(label1);
		Panel1.add(toolsComboBox);
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
		
		
/**************************************************************Radio Button 筆刷大小*********************************************************/
		Panel2 = new JPanel();
		Panel2.setLayout(new GridLayout(2,3));
//		Panel2.setPreferredSize(new Dimension(200,50));
		
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
		northPanel.add(Panel2);
		
		small.addItemListener(new RadioButtonHandler("小"));
		medium.addItemListener(new RadioButtonHandler("中"));
		big.addItemListener(new RadioButtonHandler("大"));
		
/***********************************************************Check Box 填滿***************************************************************/
		Panel3 = new JPanel();
		Panel3.setLayout(new GridLayout(2,1));
//		Panel3.setPreferredSize(new Dimension(200,50));
		
		label3 = new JLabel("填滿");
		fill = new JCheckBox();
		Panel3.add(label3);
		Panel3.add(fill);
		northPanel.add(Panel3);
		
		fill.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(fill.isSelected())
				{
					System.out.printf("選擇 填滿\n");
				}
				else
				{
					System.out.printf("取消 填滿\n");
				}
			}
		} 
		);//end of CheckBox
		
/**************************************************************Button （筆刷顏色、清除畫面、被景色、橡皮擦）***************************************************/
		pen = new JButton("筆刷顏色");
		eraser = new JButton("橡皮擦");
		clean = new JButton("清除畫面");
		back = new JButton("背景色");
		/*
		add(pen);
		add(clean);
		add(eraser);
		*/
		northPanel.add(pen);
		northPanel.add(eraser);
		northPanel.add(clean);
		northPanel.add(back);
		
		pen.addActionListener(new JButtonHandler(pen.getText()));
		eraser.addActionListener(new JButtonHandler(eraser.getText()));
		clean.addActionListener(new JButtonHandler(clean.getText()));
		back.addActionListener(new JButtonHandler(back.getText()));
		
/**************************************************************游標位置偵測**************************************************************/
		statusbar = new JLabel("指標位置",SwingConstants.LEFT); 
		statusbar.setBackground(Color.black);
	    statusbar.setForeground(Color.white);
	    statusbar.setOpaque(true); 
/**************************************************************設定mousePanel顏色**************************************************************/
		mousePanel.setBackground(backColor); 
	    
	    /*MouseHandler handler1 = new MouseHandler(); 
	    mousePanel.addMouseListener(handler1); 
	    mousePanel.addMouseMotionListener(handler1); 
	    
	    northPanel.setLayout(new BorderLayout());
	    mousePanel.setLayout(new BorderLayout());
	    s.setLayout(new BorderLayout());*/
		
		BorderLayout layout = new BorderLayout();
	    layout.setHgap(10);
	    layout.setVgap(10); 
		
	    add(northPanel, layout.NORTH);
	    add(mousePanel, layout.CENTER); // add panel to JFrame
	    add(statusbar, layout.SOUTH); // add label to JFrame
	    
	}//end of PainterFrame()
	
//******************************MousePanel畫布區***********************************
	private class MousePanel extends JPanel 
	{
		private Graphics2D g2d;
		public MousePanel()
		{
			addMouseListener(new MouseHandler());
			addMouseMotionListener(new MouseMotionHandler());
			image = new BufferedImage(1200, 600, BufferedImage.TYPE_INT_ARGB_PRE);
		}//end mousePanel
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);  
			Graphics gg = image.createGraphics();
			g2d = (Graphics2D) gg;
			g2d.setPaint(color);
			g2d.setStroke(new BasicStroke(paintsize,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND));
			g.drawImage(image,0,0,this);
	
//			Graphics g1d = mousePanel.getGraphics();
//			((Graphics2D) g1d).setPaint(color);
			
			if(clear==1) 
			{
				super.paintComponent(g);
				color = Color.BLACK;
				backColor = Color.white;
				mousePanel.setBackground(backColor);
				back.setBackground(backColor);
				image = new BufferedImage(1200, 600, BufferedImage.TYPE_INT_ARGB_PRE);
				repaint();
//				paintsize.clear();
				clear=0;
			}
		}//end of paintComponent
		
		
		private class MouseHandler extends MouseAdapter {
//		addMouseListener(new MouseAdapter()
//	    {// MouseListener event handlers
	    
			// handle event when mouse pressed
			//起點
			@Override
			public void mousePressed(MouseEvent e)
			{
				statusbar.setText(String.format("Pressed at [%d, %d]", e.getX(), e.getY()));
				startpntx = e.getX();
				startpnty = e.getY();
			}
			
			// handle event when mouse released 
			//設終點
			@Override
			public void mouseReleased(MouseEvent e)
			{
				statusbar.setText(String.format("Released at [%d, %d]", e.getX(), e.getY()));
				
				switch(toolsComboBox.getSelectedIndex())
				{
					case 0://筆刷
						{break;}
					case 1://直線
					{
						endpntx = e.getX();
						endpnty = e.getY();
						
//     			        ((Graphics2D) g1d).setStroke(new BasicStroke(paintsize));
//     			        ((Graphics2D)g1d).drawLine(startpntx,startpnty,endpntx,endpnty);
     			        
						g2d.setPaint(color);
						if(fill.isSelected())
						{
							g2d.setStroke(new BasicStroke(paintsize));
							g2d.drawLine(startpntx,startpnty,endpntx,endpnty);
							repaint();
						}
						else
						{
							float [] dashes = {10};
			    	 		g2d.setStroke(new BasicStroke(paintsize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,10 ,dashes, 0));
			    	 		g2d.draw(new Line2D.Double(startpntx,startpnty, endpntx, endpnty));
			    	 		repaint();
						}
						break;
					}
					case 2://橢圓形
					{
						endpntx = e.getX();
						endpnty = e.getY();
						g2d.setPaint(color);
						if(fill.isSelected())
						{
			    	 		g2d.setStroke(new BasicStroke(paintsize));
			    	 		g2d.fillOval(startpntx, startpnty,(int)(endpntx-startpntx) , (int)(endpnty-startpnty) );
			    	 		repaint();
						}
						else
						{
			    	 		g2d.setStroke(new BasicStroke(paintsize));
			    	 		g2d.drawOval(startpntx, startpnty,(int)(endpntx-startpntx) , (int)(endpnty-startpnty) );
			    	 		repaint();
						}
						break;
					}
					case 3://矩形
			    	{
			    		endpntx = e.getX();
						endpnty = e.getY();
						g2d.setPaint(color);
			    		if(fill.isSelected())
			    		{
			    	 		g2d.setStroke(new BasicStroke(paintsize));
			    	 		g2d.fillRect(startpntx, startpnty, (int)(endpntx-startpntx), (int)(endpnty-startpnty) );
			    	 		repaint();
			    		}
			    		else
			    		{
			    			g2d.setStroke(new BasicStroke(paintsize));
			    	 		g2d.drawRect(startpntx, startpnty,(int)(endpntx-startpntx) , (int)(endpnty-startpnty) );
			    	 		repaint();
			    		}
			    	 }
					case 4://圓角矩形
			    	 {
			    	 	endpntx = e.getX();
						endpnty = e.getY();
			    	 	if(fill.isSelected())
			    	 	{
			    	 		g2d.setStroke(new BasicStroke(paintsize));
				    	 	g2d.fillRoundRect(startpntx, startpnty, (int)(endpntx-startpntx), (int)(endpnty-startpnty), ((endpntx-startpntx)+(endpnty-startpnty))/10, ((endpntx-startpntx)+(endpnty-startpnty))/10);
				    	 	repaint();
			    	 	}
			    	 	else
			    	 	{
			    	 		g2d.setStroke(new BasicStroke(paintsize));
				    	 	g2d.drawRoundRect(startpntx, startpnty, (int)(endpntx-startpntx), (int)(endpnty-startpnty), ((endpntx-startpntx)+(endpnty-startpnty))/10, ((endpntx-startpntx)+(endpnty-startpnty))/10);
				    	 	repaint();
			    	 	}
			    	 }
					case 5://3D矩形
					{
						endpntx = e.getX();
						endpnty = e.getY();
						g2d.setPaint(color);
			    		if(fill.isSelected())
			    		{
			    	 		g2d.setStroke(new BasicStroke(paintsize));
			    	 		g2d.draw3DRect(startpntx, startpnty,(int)(endpntx-startpntx) , (int)(endpnty-startpnty) ,true);
			    	 		g2d.fill3DRect(startpntx, startpnty, (int)(endpntx-startpntx), (int)(endpnty-startpnty) ,true);
			    	 		repaint();
			    		}
			    		else
			    		{
			    			g2d.setStroke(new BasicStroke(paintsize));
			    	 		g2d.draw3DRect(startpntx, startpnty,(int)(endpntx-startpntx) , (int)(endpnty-startpnty) ,true);
			    	 		repaint();
			    		}
					}
				}//end switch(toolsComboBox.getSelectedIndex())
			}//mouseReleased
		      
						// handle event when mouse released immediately after press
						@Override
						public void mouseClicked(MouseEvent e)
						{
							statusbar.setText(String.format("Clicked at [%d, %d]", e.getX(), e.getY()));
							startpntx = e.getX();
							startpnty = e.getY();
						} 

					
						// handle event when mouse enters area
						@Override
						public void mouseEntered(MouseEvent e)
						{
							statusbar.setText(String.format("Mouse entered at [%d, %d]", e.getX(), e.getY()));
							mousePanel.setBackground(backColor);//設定背景顏色
						}

						// handle event when mouse exits area
						@Override
						public void mouseExited(MouseEvent e)
						{
							statusbar.setText("移標離開畫布！");
							//mousePanel.setBackground(Color.GREEN);
						}
	    	}//end addMouseListener
		
		private class MouseMotionHandler extends MouseMotionAdapter {
//		addMouseMotionListener(new MouseMotionAdapter()
//		{
			// MouseMotionListener event handlers
			// handle event when user drags mouse with button pressed
	        @Override
	        public void mouseDragged(MouseEvent event)
	        {
	        	 statusbar.setText(String.format("Dragged at [%d, %d]",  event.getX(), event.getY()));
	        	switch(PainterFrame.toolsComboBox.getSelectedIndex())
	        	{
	        	 case 0://筆刷
	        		 endpntx = event.getX();
        			 endpnty = event.getY();
	        		 if(fill.isSelected())
	        		 {
	        			 g2d.setColor(color);
				   		 g2d.drawLine(startpntx,startpnty, endpntx, endpnty);
				   		 repaint();
	        			 
	     				startpntx=endpntx;
	     				startpnty=endpnty;
	        		 }
	        		 break;
	        	}
	        }//end mouseDragged

				        // handle event when user moves mouse
				        @Override
				        public void mouseMoved(MouseEvent event)
				        {
				           statusbar.setText(String.format("Moved at [%d, %d]", 
				              event.getX(), event.getY()));
				        } 
		}//end addMouseMotionListener
	 
	}  //畫布區結束

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
			
			switch(size) 
			{
			case "小": 
				paintsize = (float) 2.0;
				break;
			case "中": 
				paintsize = (float)8.0;
				break;
			case "大": 
				paintsize = (float)15.0;
				break;
			}
		}
	}//end RadioButtonHandler


	private class JButtonHandler implements ActionListener
	{
		private String message;
		private float paintsize;
		private JComboBox<String> tool;
		
		public JButtonHandler(String m)
		{
			message = m;
		}
		@Override
		public void  actionPerformed(ActionEvent e) {
			System.out.printf("選擇 %s\n",message);
			
			//未完成！！！！
			switch(message) 
			{
			case "筆刷顏色": 
				color = JColorChooser.showDialog(PainterFrame.this, "Choose a color", color);
				break;
			case "橡皮擦": //白色畫筆
				color = Color.white;	
				break;
			case "清除畫面": 
				clear = 1;
				points.clear();
				mousePanel.repaint();
				break;
			case "背景色":
				backColor = JColorChooser.showDialog(PainterFrame.this, "背景色", backColor);
				mousePanel.setBackground(backColor);
				back.setBackground(backColor);
				break;
			}
		}
	}//end RadioButtonHandler
}//the end class MainFrame
