import javax.swing.*;
import javax.swing.text.html.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Editor extends JFrame{
	
	public JLabel name,time;
	public JTextArea text;
	public JButton edit , newpost, heart ,changetoViewer,turnBack;
	public JButton save, newSave , input , cancel;
	public JPanel information,functionBar,eastButtons,centerButtons;
	public Date editTime=new Date();
	public Date now= new Date();
	public String content,backupText;
	public ImageIcon unlike, like;
	public ObjectInputStream inputStream;
	public ObjectOutputStream output;
	public PostSerializable post;
	public boolean isLike = false;
	private Color a = new Color(94,38,18);	
	private JComboBox<String> weather;
	private static final String[] weather1 = {"晴天","陰天","雨天"};
	private ImageIcon image1 = new ImageIcon();
	private final ImageIcon sunny = new ImageIcon();
	private final ImageIcon cloudy = new ImageIcon();
	private final ImageIcon rainy = new ImageIcon();
	
	public Editor(){
		
		super(" ");
		read();
		setLayout(new BorderLayout());

		//設定版面 上下兩個Panel
		information = new JPanel();
		functionBar = new JPanel();
		information.setBackground(Color.GREEN);
		functionBar.setBackground(a);

		//設定
				ImageIcon sunny = new ImageIcon(getClass().getResource("sunny.png"));
				ImageIcon cloudy = new ImageIcon(getClass().getResource("cloudy.png"));
				ImageIcon rainy = new ImageIcon(getClass().getResource("rainy.png"));

				Image imageSunny = sunny.getImage();
				imageSunny = imageSunny.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
				sunny.setImage(imageSunny);
				
				Image imageCloudy = cloudy.getImage();
				imageCloudy = imageCloudy.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
				cloudy.setImage(imageCloudy);
				
				Image imageRainy = rainy.getImage();
				imageRainy = imageRainy.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
				rainy.setImage(imageRainy);
				
		//設定上panel(information)
		name = new JLabel("聯絡簿");
		name.setFont(new Font("Serif",Font.BOLD,24));
		JLabel space = new JLabel(" ");
		time = new JLabel(editTime.toString());
		
		//設定天氣
		image1 = sunny;
		System.out.print(image1);
		JLabel wea = new JLabel("天氣");
		wea.setFont(new Font("Serif",Font.BOLD,20));
		weather = new JComboBox<String>(weather1);
		
		weather.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if(event.getStateChange() == ItemEvent.SELECTED) 
				{
					switch(weather.getSelectedIndex())
					{
					case 0:
						image1 = sunny;
					case 1:
						image1 = cloudy;
					case 2:
						image1 = rainy;
					}
				}
			}
			
		});//end call to addItemListener
		
		JLabel image = new JLabel(image1);
		System.out.print(image1);
		
		JPanel weaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		weaPanel.setBackground(Color.GREEN);
//		weaPanel.setLayout(new GridLayout(1,2));
		weaPanel.add(wea);
		weaPanel.add(weather);
		weaPanel.add(image);
		
//		ItemListener hButtonListener = new HeartButtonListener();
//		heart.addActionListener(hButtonListener);
		
		
		
		
		information.setLayout(new GridLayout(4,1));
		information.add(name);
		information.add(space);
//		information.add(wea);
//		information.add(weather);
//		information.add(image);
		information.add(weaPanel);
		weather.setVisible(false);
		information.add(time);
		add(information, BorderLayout.NORTH);

		
		//設定中間的文字版
		text = new JTextArea(content);
		text.setFont(new Font("Serif",Font.BOLD,16));
		text.setOpaque(true);
		text.setBackground(Color.GREEN);
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		add(text,BorderLayout.CENTER);
		
		
		//設定愛心大小和按鈕
		like = new ImageIcon(getClass().getResource("like.png"));
		unlike = new ImageIcon(getClass().getResource("unlike.png"));

		Image imageLike = like.getImage();
		imageLike = imageLike.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
		like.setImage(imageLike);
		Image imageUnlike = unlike.getImage();
		imageUnlike = imageUnlike.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
		unlike.setImage(imageUnlike);
		
		heart = new JButton();
		heart.setIcon(unlike);
		heart.setPreferredSize(new Dimension(50,50));
		
		HeartButtonListener hButtonListener = new HeartButtonListener();
		heart.addActionListener(hButtonListener);

		//右邊按鈕的Panel跟listener
		eastButtons = new JPanel();
		eastButtons.setBackground(a);
		turnBack = new JButton("返回");
		edit = new JButton("編輯");
		newpost = new JButton("全新貼文");
		changetoViewer = new JButton("預覽畫面");
		eastButtons.add(turnBack);
		eastButtons.add(edit);
		eastButtons.add(newpost);
		eastButtons.add(changetoViewer);
		turnBack.setVisible(false);
	
		
		EastButtonListener ebuttonListener = new EastButtonListener();
		edit.addActionListener(ebuttonListener);
		newpost.addActionListener(ebuttonListener);
		changetoViewer.addActionListener(ebuttonListener);
		
		TButtonListener tButtonListener = new TButtonListener();
		turnBack.addActionListener(tButtonListener);
		
		//中間按鈕的Panel跟listener
		
		centerButtons = new JPanel();
		centerButtons.setBackground(a);
		centerButtons.setVisible(false);
		save = new JButton("儲存");
		newSave = new JButton("另存內容");
		input = new JButton("匯入內容");
		cancel = new JButton("取消");

		centerButtons.add(save);
		centerButtons.add(newSave);
		centerButtons.add(input);
		centerButtons.add(cancel);		
		
		CenterButtonListener cbuttonListener = new CenterButtonListener();
		save.addActionListener(cbuttonListener);
		newSave.addActionListener(cbuttonListener);
		input.addActionListener(cbuttonListener);
		cancel.addActionListener(cbuttonListener);
		
		//設定功能列
		functionBar.setLayout(new BorderLayout());
		functionBar.add(heart,BorderLayout.WEST);
		functionBar.add(eastButtons,BorderLayout.EAST);
		functionBar.add(centerButtons,BorderLayout.CENTER);
		add(functionBar, BorderLayout.SOUTH);


	}
	//讀post
	public void read() {
		openFile();
		readRecords();
		closeFile();}
	
	public void openFile() {
		try {inputStream = new ObjectInputStream(Files.newInputStream(Paths.get("src\\post")));}
		catch(IOException ioException){System.err.println("Error opening File");}}
	
	public void readRecords() {
		try {
			while(true) {
				PostSerializable record = (PostSerializable)inputStream.readObject();
				content =record.getContent();
				editTime =record.getEditTime();
				isLike = record.getIsLike();}}
		catch (EOFException endOfFileException){System.out.printf("%nNo more records%n");} 
	    catch (ClassNotFoundException classNotFoundException){System.err.println("Invalid object type. Terminating.");} 
	    catch (IOException ioException){System.err.println("Error reading from file. Terminating.");}}
	
	public void closeFile() {
	      try{
	         if (inputStream != null)
	            inputStream.close();} 
	      catch (IOException ioException){
	    	 System.err.println("Error closing file. Terminating.");
	         System.exit(1);}}

	public void back() {//??
		centerButtons.setVisible(false);
		text.setBackground(Color.GREEN);
		turnBack.setVisible(false);
		eastButtons.setVisible(true);
		heart.setVisible(true);}
	
	
	
	//EastButtonListener
	private class EastButtonListener implements ActionListener{
		
		
		public void actionPerformed(ActionEvent event) {
			//當點按鈕 打開edit的frame
			if(event.getActionCommand()=="編輯") {
				
				weather.setVisible(true);
				
				centerButtons.setVisible(true);
				heart.setVisible(false);
				eastButtons.setVisible(false);
				text.setEditable(true);
				text.setBackground(Color.WHITE);
				backupText=text.getText();
				switch(weather.getSelectedIndex())
				{
				case 0:
					image1 = sunny;
				case 1:
					image1 = cloudy;
				case 2:
					image1 = rainy;
					}
}
			//當點按鈕 打開newEdit的frame
			else if(event.getActionCommand()=="全新貼文"){
				centerButtons.setVisible(true);
				heart.setVisible(false);
				eastButtons.setVisible(false);
				text.setText(null);
				text.setEditable(true);
				text.setBackground(Color.WHITE);
}
			//當點按鈕 切換成Viewer
			else if(event.getActionCommand()=="預覽畫面") {
				changetoViewer.setVisible(false);
				newpost.setVisible(false);
				edit.setVisible(false);
				turnBack.setVisible(true);
				heart.setEnabled(true);
	}}}
	
	private class CenterButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						
			if(event.getActionCommand()=="儲存") {
				back();
				try {
					output = new ObjectOutputStream(Files.newOutputStream(Paths.get("post")));
					output.writeObject(new PostSerializable(text.getText(), false, editTime));
					output.close();
				} catch (IOException e) {e.printStackTrace();}
				text.setEditable(false);
				backupText=text.getText();
				now= new Date();
				time.setText(now.toString());
				weather.setVisible(false);
			
				switch(weather.getSelectedIndex())
				{
				case 0:
					image1 = sunny;
				case 1:
					image1 = cloudy;
				case 2:
					image1 = rainy;
					}
			
			}
			
			else if(event.getActionCommand()=="另存內容") {	
				
				time.setText(now.toString());
				content = text.getText();
				fileChooser.showSaveDialog(null);

				StringBuilder builder = new StringBuilder();
				Path path= fileChooser.getSelectedFile().toPath();
				builder.append(String.format("%s",(path.toAbsolutePath()+".txt")));
				
				try{
					FileWriter fileWriter=new FileWriter(builder.toString());
					fileWriter.write(content);
					fileWriter.close();}
				catch (IOException e) {e.printStackTrace();}
				back();}
			
			else if(event.getActionCommand()=="匯入內容") {
				
				int result = fileChooser.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION){
					File selectedfile = fileChooser.getSelectedFile();
					    try {
					    	content = " ";
					        Scanner scanner = new Scanner(selectedfile);
					        while (scanner.hasNext()) {
					        	content += scanner.nextLine();
					        	content += "\n";}							        
					        scanner.close();} 		
					    catch (FileNotFoundException e) {e.printStackTrace();}
					    text.setText(content);}
				else {System.exit(1);}}
			
			else if(event.getActionCommand()=="取消") {
				text.setText(backupText);
				weather.setVisible(false);
				back();}}}

	
	
	private class HeartButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(isLike == false) {
				heart.setIcon(like);
				isLike = true;}
			else {
				heart.setIcon(unlike);
				isLike = false;}}}

	public class TButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
				changetoViewer.setVisible(true);
				newpost.setVisible(true);
				edit.setVisible(true);
				turnBack.setVisible(false);
				heart.setEnabled(false);}}
}




