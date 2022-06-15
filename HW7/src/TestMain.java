import javax.swing.JFrame;

//107403037_林驊萱
public class TestMain {
	public static void main(String[] args) {
		Main main = new Main();
		//設定Main大小
		main.setResizable(false);//不能放大縮小
		main.setSize(450,750);
	    main.setVisible(true); 
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setLocationRelativeTo(null); //將畫面置中
	}
}
