//�L�~��_107403037_��ޤT

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
	private static final String DEFAULT_QUERY = "SELECT name FROM people";//JTable�һ�
	
	private Person person;
	public PersonQueries personQueries;
	private static ResultSetTableModel tableModel;
	private List<Person> persons;
	private int numberOfEntries=0;
	private int personIndex;
	
	/*�HMySQL����Ʈw�A�s�Wjava/java���޲z�̱b�K*/
	private JPanel northpanel;
	private JLabel contacts;//contact���D
	private JLabel plus;//�[��
	private JTextField searchTxt;//�j�M��r��J��
	private JButton searchButton;//�j�M���s
	
	private JTable resultTable;
	
	private JPanel panel;
	private JLabel nameLabel;
	private JLabel phoneLabel;
	private JLabel typeLabel;
	private JTextField phoneTxtField;
	private JTextField nameTxtField;
	private static final String[] types = {"cell", "company",  "home"};
	private JComboBox<String> typeComboBox;
	
	
	//�]�w�r��˦�
	Font titlefont = new Font("�L�n������", Font.BOLD, 50);
	Font nameFont = new Font("�L�n������", Font.BOLD, 25);
	
	public Main() {
		super("contacts");
		try {
			tableModel = new ResultSetTableModel(URL, USERNAME, PASSWORD, DEFAULT_QUERY);//�Ǧ^ResultSetTableModel�A�o��tableModel
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
		    plus.setCursor(new Cursor(Cursor.HAND_CURSOR)); //��ƹ����ܥ[���W��ɡA���ܼ˦�
			plus.addMouseListener(new MouseAdapter() { //�I���ϡA�i�J�s�W�e���A�i��J�m�W�B�����B�q��
				public void mouseClicked(MouseEvent evt) {
					insertButtonActionPerformed(evt);
				}
			});
		    searchTxt = new JTextField();
		    searchTxt.setPreferredSize(new Dimension(300, 70));
	//	    	    indexTextFieldActionPerformed
		    searchButton = new JButton("search");
		    searchButton.setPreferredSize(new Dimension(100, 40));
		    searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //��ƹ����ܥ[���W��ɡA���ܼ˦�
		    searchButton.addActionListener(new ActionListener() {/*��W�覬�M�C��J�W�r�ιq�ܡA�i�C�X�������W�r*/
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
	    
	    //�]�w���Xpanel
	    panel = new JPanel(new GridLayout(3, 2, 10, 10));
	    nameLabel = new JLabel("�W�r: ");
		typeLabel = new JLabel("�q������: ");
		phoneLabel = new JLabel("�q��: ");
		nameTxtField = new JTextField(15);
		typeComboBox = new JComboBox<String>(types);
		phoneTxtField = new JTextField(15);
		panel.add(nameLabel);
		panel.add(nameTxtField);
		panel.add(typeLabel);
		panel.add(typeComboBox);
		panel.add(phoneLabel);
		panel.add(phoneTxtField);
		
		//��ܪ�JTable(�ޤJtableModel)
		resultTable = new JTable(tableModel);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel); //�ת��ɭ�Table�|�ƦC
        resultTable.setRowSorter(sorter); //setRowSorter( TableRowSorter)
        resultTable.setFont(nameFont);
        resultTable.setRowHeight(50);
        add(new JScrollPane(resultTable), BorderLayout.CENTER); //JTable�|����scroll
        resultTable.addMouseListener(new MouseAdapter() {//��U���ƪ����ʧ@
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int optionSelected;
		        int ok = 0;
				String[] options = {"����", "����"};
				boolean phoneValid = false;
		        int result = 0;
		        int row = table.rowAtPoint(point);
		        String nameSelected = table.getValueAt(row, 0).toString();
		        /*������H�����e�{�����p����T�Ա�*/
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	optionSelected = cellDoubleClickedPerformed(mouseEvent, nameSelected);
		        	
		        	/*�����p����T�A�I�� Update�i�J�ק�e��*/
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
	        				ok = JOptionPane.showOptionDialog(Main.this, Main.this.panel, "�ק���", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	        				persons = personQueries.getPeopleByName(nameTxtField.getText());
	        			    numberOfEntries = persons.size();
	        				if(typeComboBox.getSelectedIndex() == 0) {
	        					phoneValid = phoneTxtField.getText().matches("09[0-9]{8}");
	        				} else {
	        					phoneValid = phoneTxtField.getText().matches("0[0-9]{8}") ||  phoneTxtField.getText().matches("0[0-9]{9}");
	        				}
	        				
	        				if(!phoneValid && ok == JOptionPane.OK_OPTION)
	        					JOptionPane.showMessageDialog(Main.this, "�q�ܮ榡���~!", "���~", JOptionPane.WARNING_MESSAGE);
	        				
	        			}while(!phoneValid && ok == JOptionPane.OK_OPTION);
	        			
	        			if(ok == JOptionPane.OK_OPTION) {
	        				result = personQueries.updatePersonByName(nameSelected, nameTxtField.getText(), typeComboBox.getSelectedItem().toString(), phoneTxtField.getText());
	        				refreshTable();
	        			    
	        		        if (result == 1)
	        		           JOptionPane.showMessageDialog(Main.this, "���\�ק�!", "�T��", JOptionPane.INFORMATION_MESSAGE);
	        		        else
	        		           JOptionPane.showMessageDialog(Main.this, "�X�{���~!", "���~", JOptionPane.ERROR_MESSAGE);
	        			}
		        		refreshTable();
		        		
		        	/*�����p����T�A�I�� Delete�R���ӵ���ơA�æ^��D����*/
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
	
	/*�i�J�s�W�e���A�i��J�m�W�B�����B�q��*/
	private void insertButtonActionPerformed(MouseEvent evt) {
		int result = 0;
		String[] options = {"����", "����"};
		nameTxtField.setText("");
		typeComboBox.setSelectedIndex(0);
		phoneTxtField.setText("");
		boolean phoneValid = false;
		boolean nameValid = false;
		int ok = 0;
		do {
			ok = JOptionPane.showOptionDialog(this, this.panel, "�s�W�p���H", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
			persons = personQueries.getPeopleByName(nameTxtField.getText());
		    numberOfEntries = persons.size();
		    
		    /*��J��ƫ�A�ШϥΥ��W��F���Ӱ��q�ܪ����b*/
			if(typeComboBox.getSelectedIndex() == 0) {
				/*Cell �榡�G09XXXXXXXX�C09�}�Y�A �Q�X*/
				phoneValid = phoneTxtField.getText().matches("09[0-9]{8}");
			} else {
				/*home�Bcompany �榡�G0�}�Y�A�᭱�K�ΤE�X*/
				phoneValid = phoneTxtField.getText().matches("0[0-9]{8}") ||  phoneTxtField.getText().matches("0[0-9]{9}");
			}
			
			if(numberOfEntries == 0)
				nameValid = true;
			
			if(!phoneValid && ok == JOptionPane.OK_OPTION)
				JOptionPane.showMessageDialog(this, "�q�ܮ榡���~!", "���~", JOptionPane.WARNING_MESSAGE);
			
			if(!nameValid && ok == JOptionPane.OK_OPTION)
				JOptionPane.showMessageDialog(this, "���H�w�b�q�T����!", "���~", JOptionPane.WARNING_MESSAGE);
			
		}while((!phoneValid || !nameValid) && ok == JOptionPane.OK_OPTION);//��J������A�I�粒���^��D����
		
		if(ok == JOptionPane.OK_OPTION) {
			//System.out.println(typeComboBox.getSelectedItem().toString());
			result = personQueries.addPerson(nameTxtField.getText(), typeComboBox.getSelectedItem().toString(), phoneTxtField.getText());
			refreshTable();
		    
	        if (result == 1)
	           JOptionPane.showMessageDialog(this, "���\�[�J!", "�T��", JOptionPane.INFORMATION_MESSAGE);
	        else
	           JOptionPane.showMessageDialog(this, "�X�{���~!", "���~", JOptionPane.ERROR_MESSAGE);
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
