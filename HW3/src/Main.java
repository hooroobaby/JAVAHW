import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//���3_107403037_�L�~��
public class Main {
	static JFrame main;
	public static void main(String[] args) {
		
		
		String [] option = {"Cancel","No","Yes"};
		int in = JOptionPane.showOptionDialog(null,"�O�_���o����","�n�J",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null,option,null);
		
		if(in==0) {main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}
		else
		{
			Editor editor = new Editor();
			editor.setSize(1200, 600);
			editor.setVisible(true);
			if(in==1) {		
				editor.eastButtons.setVisible(false);}
			if(in==2) {
				editor.heart.setEnabled(false);
		
		}
	}
}
}
