//林驊萱_107403037_資管三

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class Main extends JFrame{
	private static final String URL = "jdbc:mysql://localhost/member?serverTimezone=CST";
	private static final String USERNAME = "java";
	private static final String PASSWORD = "java";
	private static final String DEFAULT_QUERY = "SELECT name FROM people";//JTable所需
	
	private Person person;
	public PersonQueries personQueries;
	private static ResultSetTableModel tableModel;
	private List<Person> persons;
	private int numberOfEntries=0;
	private int personIndex;
	
	/*以MySQL為資料庫，新增java/java為管理者帳密*/
	private JPanel northpanel;
	private JLabel contacts;//contact標題
	private JLabel plus;//加號
	private JTextField searchTxt;//搜尋文字輸入區
	private JButton searchButton;//搜尋按鈕
	
	private JTable resultTable;
	
	private JPanel panel;
	private JLabel nameLabel;
	private JLabel phoneLabel;
	private JLabel typeLabel;
	private JTextField phoneTxtField;
	private JTextField nameTxtField;
	private static final String[] types = {"cell", "company",  "home"};
	private JComboBox<String> typeComboBox;
	
	
	//設定字體樣式
	Font titlefont = new Font("微軟正黑體", Font.BOLD, 50);
	Font nameFont = new Font("微軟正黑體", Font.BOLD, 25);
	
	public Main() {
		super("contacts");
		try {
			tableModel = new ResultSetTableModel(URL, USERNAME, PASSWORD, DEFAULT_QUERY);//傳回ResultSetTableModel，得到tableModel
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		personQueries = new PersonQueries();

		northpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		northpanel.setPreferredSize(new Dimension(450, 160));
			contacts = new JLabel("Contacts");
			contacts.setFont(titlefont);
			contacts.setPreferredSize(new Dimension(350, 50));
		    plus = new JLabel("+");
		    plus.setFont(titlefont);
		    plus.setPreferredSize(new Dimension(50, 50));
		    plus.setCursor(new Cursor(Cursor.HAND_CURSOR)); //當滑鼠移至加號上方時，改變樣式
			plus.addMouseListener(new MouseAdapter() { //點擊＋，進入新增畫面，可輸入姓名、類型、電話
				public void mouseClicked(MouseEvent evt) {
					insertButtonActionPerformed(evt);
				}
			});
		    searchTxt = new JTextField();
		    searchTxt.setPreferredSize(new Dimension(300, 70));
	//	    	    indexTextFieldActionPerformed
		    searchButton = new JButton("search");
		    searchButton.setPreferredSize(new Dimension(100, 40));
		    searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //當滑鼠移至加號上方時，改變樣式
		    searchButton.addActionListener(new ActionListener() {/*於上方收尋列輸入名字或電話，可列出相關的名字*/
		        public void actionPerformed(ActionEvent e) {
			        String text = searchTxt.getText();
			        String queryString = "SELECT name FROM people WHERE phone LIKE \"" + text + "%\" OR name LIKE\"" + text + "%\"";
			        try {
			           //System.out.println(queryString);
			        	tableModel.setQuery(queryString);
					} 
			        catch (IllegalStateException e1) {
						e1.printStackTrace();
					} 
			        catch (SQLException e1) {
						e1.printStackTrace();
					}
		        } 
		    });
	    northpanel.add(contacts);
	    northpanel.add(plus);
	    northpanel.add(searchTxt);
	    northpanel.add(searchButton);
	    add(northpanel, BorderLayout.NORTH);
	    
	    //設定跳出panel
	    panel = new JPanel(new GridLayout(3, 2, 10, 10));
	    nameLabel = new JLabel("名字: ");
		typeLabel = new JLabel("電話類型: ");
		phoneLabel = new JLabel("電話: ");
		nameTxtField = new JTextField(15);
		typeComboBox = new JComboBox<String>(types);
		phoneTxtField = new JTextField(15);
		panel.add(nameLabel);
		panel.add(nameTxtField);
		panel.add(typeLabel);
		panel.add(typeComboBox);
		panel.add(phoneLabel);
		panel.add(phoneTxtField);
		
		//顯示的JTable(引入tableModel)
		resultTable = new JTable(tableModel);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel); //案的時候Table會排列
        resultTable.setRowSorter(sorter); //setRowSorter( TableRowSorter)
        resultTable.setFont(nameFont);
        resultTable.setRowHeight(50);
        add(new JScrollPane(resultTable), BorderLayout.CENTER); //JTable會做成scroll
        resultTable.addMouseListener(new MouseAdapter() {//對下方資料表做的動作
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int optionSelected;
		        int ok = 0;
				String[] options = {"完成", "取消"};
				boolean phoneValid = false;
		        int result = 0;
		        int row = table.rowAtPoint(point);
		        String nameSelected = table.getValueAt(row, 0).toString();
		        /*雙擊後以視窗呈現此筆聯絡資訊詳情*/
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	optionSelected = cellDoubleClickedPerformed(mouseEvent, nameSelected);
		        	
		        	/*雙擊聯絡資訊，點選 Update進入修改畫面*/
		        	if(optionSelected == 0) {
		        		persons = personQueries.getPeopleByName(nameSelected);
	        	        numberOfEntries = persons.size();
	        	        if (numberOfEntries != 0){
//	        	        	currentEntryIndex = 0;
//	        	        	currentEntry = persons.get(currentEntryIndex);
			        		nameTxtField.setText(person.getName());
			        		typeComboBox.setSelectedItem(person.getType());
			        		phoneTxtField.setText(person.getPhone());	        	           
	        	        }
	        	        
	        	        do {
	        				ok = JOptionPane.showOptionDialog(Main.this, Main.this.panel, "修改資料", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	        				persons = personQueries.getPeopleByName(nameTxtField.getText());
	        			    numberOfEntries = persons.size();
	        				if(typeComboBox.getSelectedIndex() == 0) {
	        					phoneValid = phoneTxtField.getText().matches("09[0-9]{8}");
	        				} else {
	        					phoneValid = phoneTxtField.getText().matches("0[0-9]{8}") ||  phoneTxtField.getText().matches("0[0-9]{9}");
	        				}
	        				
	        				if(!phoneValid && ok == JOptionPane.OK_OPTION)
	        					JOptionPane.showMessageDialog(Main.this, "電話格式有誤!", "錯誤", JOptionPane.WARNING_MESSAGE);
	        				
	        			}while(!phoneValid && ok == JOptionPane.OK_OPTION);
	        			
	        			if(ok == JOptionPane.OK_OPTION) {
	        				result = personQueries.updatePersonByName(nameSelected, nameTxtField.getText(), typeComboBox.getSelectedItem().toString(), phoneTxtField.getText());
	        				refreshTable();
	        			    
	        		        if (result == 1)
	        		           JOptionPane.showMessageDialog(Main.this, "成功修改!", "訊息", JOptionPane.INFORMATION_MESSAGE);
	        		        else
	        		           JOptionPane.showMessageDialog(Main.this, "出現錯誤!", "錯誤", JOptionPane.ERROR_MESSAGE);
	        			}
		        		refreshTable();
		        		
		        	/*雙擊聯絡資訊，點選 Delete刪除該筆資料，並回到主頁面*/
		        	}else if(optionSelected == 1) {
		        		personQueries.deletePersonByName(nameSelected);
		        		refreshTable();
		        	}
		        	//System.out.println(table.getValueAt(row, 0));
		        }
		    }
		});
	}
	
	 // handles call when insertButton is clicked
//	   private void insertButtonActionPerformed(ActionEvent evt) 
//	   {//addPerson.(String name, String type, String num)
//	      int result = personQueries.addPerson(nameTxtField.getText(), typeComboBox.getSelectedItem().toString(), phoneTxtField.getText());
//	      
//	      if (result == 1)
//	         JOptionPane.showMessageDialog(this, "Person added!",
//	            "Person added", JOptionPane.PLAIN_MESSAGE);
//	      else
//	         JOptionPane.showMessageDialog(this, "Person not added!",
//	            "Error", JOptionPane.PLAIN_MESSAGE);
//	          
//	      browseButtonActionPerformed(evt);
//	   }
	
	/*進入新增畫面，可輸入姓名、類型、電話*/
	private void insertButtonActionPerformed(MouseEvent evt) {
		int result = 0;
		String[] options = {"完成", "取消"};
		nameTxtField.setText("");
		typeComboBox.setSelectedIndex(0);
		phoneTxtField.setText("");
		boolean phoneValid = false;
		boolean nameValid = false;
		int ok = 0;
		do {
			ok = JOptionPane.showOptionDialog(this, this.panel, "新增聯絡人", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
			persons = personQueries.getPeopleByName(nameTxtField.getText());
		    numberOfEntries = persons.size();
		    
		    /*輸入資料後，請使用正規表達式來做電話的防呆*/
			if(typeComboBox.getSelectedIndex() == 0) {
				/*Cell 格式：09XXXXXXXX。09開頭， 十碼*/
				phoneValid = phoneTxtField.getText().matches("09[0-9]{8}");
			} else {
				/*home、company 格式：0開頭，後面八或九碼*/
				phoneValid = phoneTxtField.getText().matches("0[0-9]{8}") ||  phoneTxtField.getText().matches("0[0-9]{9}");
			}
			
			if(numberOfEntries == 0)
				nameValid = true;
			
			if(!phoneValid && ok == JOptionPane.OK_OPTION)
				JOptionPane.showMessageDialog(this, "電話格式有誤!", "錯誤", JOptionPane.WARNING_MESSAGE);
			
			if(!nameValid && ok == JOptionPane.OK_OPTION)
				JOptionPane.showMessageDialog(this, "此人已在通訊錄中!", "錯誤", JOptionPane.WARNING_MESSAGE);
			
		}while((!phoneValid || !nameValid) && ok == JOptionPane.OK_OPTION);//輸入完畢後，點選完成回到主頁面
		
		if(ok == JOptionPane.OK_OPTION) {
			//System.out.println(typeComboBox.getSelectedItem().toString());
			result = personQueries.addPerson(nameTxtField.getText(), typeComboBox.getSelectedItem().toString(), phoneTxtField.getText());
			refreshTable();
		    
	        if (result == 1)
	           JOptionPane.showMessageDialog(this, "成功加入!", "訊息", JOptionPane.INFORMATION_MESSAGE);
	        else
	           JOptionPane.showMessageDialog(this, "出現錯誤!", "錯誤", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	 private int cellDoubleClickedPerformed(MouseEvent evt, String name){
       persons = personQueries.getPeopleByName(name);
       numberOfEntries = persons.size();
       int optionSelected = 0;
       
       if (numberOfEntries != 0){
          personIndex = 0;
          person = persons.get(personIndex);
          String[] options = {"update", "delete"};
          String message = person.getType() + " : " + person.getPhone();
          optionSelected = JOptionPane.showOptionDialog(this, message, name, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
       }
       
       return optionSelected;
    } 
	 
	public void refreshTable() {
		try {
			tableModel.setQuery(DEFAULT_QUERY);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
