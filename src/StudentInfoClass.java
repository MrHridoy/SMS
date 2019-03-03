import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;



public class StudentInfoClass {
	private JFrame frame;
    private JPanel panel_left,panel_right;
    private JLabel lbl_search,lbl_home,lbl_id,lbl_first_name,lbl_surname,lbl_birthdate,lbl_father_name,lbl_mother_name,lbl_gender,lbl_number,lbl_class,lbl_roll,lbl_admission_date,lbl_address,lbl_image_show;
	private JTextField txt_search,txt_id,txt_first_name,txt_surname,txt_father_name,txt_mother_name,txt_roll;
    private JComboBox cb_gender,cb_class,cb_search;
    private JFormattedTextField txt_f_number;
    private MaskFormatter mask_f_number;
    private JDateChooser j_calendar_birthdate,j_calendar_admission; 
    private JTextArea txt_area_address;
    private JButton btn_add,btn_update,btn_delete,btn_clear,btn_attach_image;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel model;
    private Cursor tableSelect;
    
    private String[] search_list= {"Student Id","Class","Roll"};
    private String[] gender_list= {"Male","Female"};
    private String[] class_list= {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten"};
    
    public String[] colums={"ID","FIRST NAME","SURNAME","BIRTHDATE","FATHER'S NAME","MOTHER'S NAME","GENDER","MOBILE NUMBER","CLASS","ROLL","ADMISSION DATE","ADDRESS","IMAGES"};
    public String[] rows=new String[13];
    
    private FileNameExtensionFilter filter;
    File f,image;
    String filename=null;
    byte [] picture=null;
    
    Connection conn=null;
    ResultSet rs=null;
	PreparedStatement pst=null;
	
	StudentInfoClass(){
		conn=DatabaseClass.ConnectDB();
		
		//this code belongs to jframe create	
	    frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,600);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Student Management System"); 
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        
        
      
        
        //this code belongs to panel called for jframe and border layout
        panel_left=new JPanel();
        panel_left.setBackground(Color.CYAN);
        panel_left.setPreferredSize(new Dimension(750, 180));
        panel_left.setLayout(null);   //this code belongs to set layout format by panel
        
        
       //this code belongs to panel called for jframe and border layout
        panel_right=new JPanel();
        panel_right.setBackground(Color.blue);
        panel_right.setBorder(new EmptyBorder(50, 50, 50, 50));                  //setting panel border
        panel_right.setPreferredSize(new Dimension(600, 800));
        panel_right.setLayout(null);   //this code belongs to set layout format by panel
        
      //this code belongs to mouse cursor set by jtable select
        tableSelect=new Cursor(Cursor.HAND_CURSOR);
        
        
        //this code belongs to font style & font size
        Font fontInfo=new Font("ARIAL",Font.BOLD,30);
        Font font=new Font("ARIAL",Font.CENTER_BASELINE,20);
       
        
       //this code belongs to textfield
        lbl_search=new JLabel("Search");;
        lbl_search.setFont(font); 
        lbl_search.setBounds(10, 30, 100, 20);
        panel_left.add(lbl_search);
        
        
       //this code belongs to textfield
        cb_search=new JComboBox(search_list);
        cb_search.setFont(font); 
        cb_search.setBounds(10, 60, 150, 30);
        panel_left.add(cb_search);
        
       //this code belongs to textfield
        txt_search=new JTextField();;
        txt_search.setFont(font); 
        txt_search.setBounds(200, 60, 500, 30);
        panel_left.add(txt_search);
        
       //this code belongs to label
        lbl_home=new JLabel(" S t u d e n t   I n f o r m a t i o n ");;
        lbl_home.setFont(fontInfo);
        lbl_home.setBounds(10, 120, 500, 40);
        panel_left.add(lbl_home);
        
        iniComponents();	//method called
        showTableData(); 	//method called
        
        
      
		
	}

	private void iniComponents() {
		 //this code belongs to font style & font size
		 Font fonts=new Font("ARIAL",Font.BOLD,14);
		 
		 
		 //this code belongs to label
        lbl_id=new JLabel("Student Id");;
        lbl_id.setFont(fonts);
        lbl_id.setBounds(10, 200, 200, 20);
        panel_left.add(lbl_id);

        
        //this code belongs to textfield
        txt_id=new JTextField();
        txt_id.setFont(fonts);
        txt_id.setBounds(120, 200, 200, 25);
        panel_left.add(txt_id);       
      //this code belongs to text field by only accept integer value input
        txt_id.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent id_inputs) {
                char input = id_inputs.getKeyChar();
                if ((input < '0' || input > '9') && input != '\b') {
                    id_inputs.consume();
                    System.out.println("Please enter a valid id");
                }
            }
        });
        
       //this code belongs to label
        lbl_first_name = new JLabel("First Name");
        lbl_first_name.setFont(fonts);
        lbl_first_name.setBounds(10, 240, 200, 20);
        panel_left.add(lbl_first_name);
        
        //this code belongs to textfield
        txt_first_name=new JTextField();
        txt_first_name.setFont(fonts);
        txt_first_name.setBounds(120, 240, 200, 25);
        panel_left.add(txt_first_name);
        
        //this code belongs to label
        lbl_surname = new JLabel("Surname");
        lbl_surname.setFont(fonts);
        lbl_surname.setBounds(10, 280, 200, 20);
        panel_left.add(lbl_surname);
        
        //this code belongs to textfield
        txt_surname=new JTextField();
        txt_surname.setFont(fonts);
        txt_surname.setBounds(120, 280, 200, 25);
        panel_left.add(txt_surname);
        
        //this code belongs to label
        lbl_birthdate = new JLabel("Date Of Birth");
        lbl_birthdate.setFont(fonts);
        lbl_birthdate.setBounds(10, 320, 200, 20);
        panel_left.add(lbl_birthdate);
        
       //this code belongs to set birthdate date field
        j_calendar_birthdate=new JDateChooser();
        j_calendar_birthdate.setBounds(120, 320, 200, 25);
        j_calendar_birthdate.setDateFormatString("yyyy-MM-dd");
        panel_left.add(j_calendar_birthdate);
        
        //this code belongs to label
        lbl_father_name = new JLabel("Father's Name");
        lbl_father_name.setFont(fonts);
        lbl_father_name.setBounds(10, 360, 200, 20);
        panel_left.add(lbl_father_name);
        
        //this code belongs to textfield
        txt_father_name=new JTextField();
        txt_father_name.setFont(fonts);
        txt_father_name.setBounds(120, 360, 200, 25);
        panel_left.add(txt_father_name);
        
        //this code belongs to label
        lbl_mother_name = new JLabel("Mother's Name");
        lbl_mother_name.setFont(fonts);
        lbl_mother_name.setBounds(10, 400, 200, 20);
        panel_left.add(lbl_mother_name);
        
        //this code belongs to textfield
        txt_mother_name=new JTextField();
        txt_mother_name.setFont(fonts);
        txt_mother_name.setBounds(120, 400, 200, 25);
        panel_left.add(txt_mother_name);
        
        //this code belongs to label
        lbl_gender = new JLabel("Gender");
        lbl_gender.setFont(fonts);
        lbl_gender.setBounds(10, 440, 200, 20);
        panel_left.add(lbl_gender);
        
        //this code belongs to textfield
        cb_gender=new JComboBox(gender_list);
        cb_gender.setFont(fonts);
        cb_gender.setBounds(120, 440, 200, 25);
        panel_left.add(cb_gender);
        
        //this code belongs to label
        lbl_number = new JLabel("Mobile Number");
        lbl_number.setFont(fonts);
        lbl_number.setBounds(10, 480, 200, 20);
        panel_left.add(lbl_number);
        
        mask_f_number = new MaskFormatter();
        try {
        	mask_f_number.setMask("+8801#########"); //this code belongs to handling number input
			txt_f_number=new JFormattedTextField(mask_f_number);
	        txt_f_number.setBounds(120, 480, 200, 25);
			panel_left.add(txt_f_number);
		} catch (ParseException e1) {
	
			e1.printStackTrace();
		}	
        
        
       
        //this code belongs to label
        lbl_class = new JLabel("Class");
        lbl_class.setFont(fonts);
        lbl_class.setBounds(10, 520, 200, 20);
        panel_left.add(lbl_class);
        
        //this code belongs to textfield
        cb_class=new JComboBox(class_list);
        cb_class.setFont(fonts);
        cb_class.setBounds(120, 520, 200, 25);
        panel_left.add(cb_class);
        
        //this code belongs to label
        lbl_roll=new JLabel("Roll");
        lbl_roll.setFont(fonts);
        lbl_roll.setBounds(10, 560, 200, 20);
        panel_left.add(lbl_roll);
        
       
        
        //this code belongs to textfield
        txt_roll=new JTextField();
        txt_roll.setFont(fonts);
        txt_roll.setBounds(120, 560, 200, 25);
        panel_left.add(txt_roll);
        //this code belongs to text field by only accept integer value input
        txt_roll.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent roll_inputs) {
                char inputs = roll_inputs.getKeyChar();
                if ((inputs < '0' || inputs > '9') && inputs != '\b') {
                	roll_inputs.consume();
                    System.out.println("Please enter a valid roll");
                }
            }
        });
        
        
        
        //this code belongs to label
        lbl_admission_date = new JLabel("Admission Date");
        lbl_admission_date.setFont(fonts);
        lbl_admission_date.setBounds(10, 600, 200, 20);
        panel_left.add(lbl_admission_date);
        
        //this code belongs to set admission date field
        j_calendar_admission=new JDateChooser();
        j_calendar_admission.setBounds(120, 600, 200, 25);
        j_calendar_admission.setDateFormatString("yyyy-MM-dd");
        panel_left.add(j_calendar_admission);
        
        //this code belongs to label
        lbl_address = new JLabel("Address");
        lbl_address.setFont(fonts);
        lbl_address.setBounds(10, 640, 200, 20);
        panel_left.add(lbl_address);
        
        //this code belongs to textfield
        txt_area_address=new JTextArea();
        txt_area_address.setFont(fonts);
        txt_area_address.setBounds(120, 640, 200, 45);
        panel_left.add(txt_area_address);
        
        
        //attach picture label
        lbl_image_show=new JLabel();
        lbl_image_show.setFont(fonts);
        lbl_image_show.setBounds(450, 155, 210, 200);
        panel_left.add(lbl_image_show);
        
        //attach picture add button
        btn_attach_image=new JButton("Attach Image");		        
        btn_attach_image.setBounds(450, 360, 210, 25);
        panel_left.add(btn_attach_image);
        
        //this code belongs to add button
        btn_add=new JButton("Add");
        btn_add.setBounds(450, 410, 100, 30);
        panel_left.add(btn_add);
        
        //this code belongs to add button
        btn_update=new JButton("Update");
        btn_update.setBounds(560, 410, 100, 30);
        panel_left.add(btn_update);
        
        //this code belongs to add button
        btn_clear=new JButton("Clear");
        btn_clear.setBounds(450, 470, 100, 30);
        panel_left.add(btn_clear);
        
        //this code belongs to add button
        btn_delete=new JButton("Delete");
        btn_delete.setBounds(560, 470, 100, 30);
        panel_left.add(btn_delete);
        
        
        //this code belongs to set jtable model field
        table=new JTable();	        
        model=new DefaultTableModel(); //this code belongs to model set
        model.setColumnIdentifiers(colums);
        table.setModel(model);
        table.setFont(fonts);
        table.setBackground(Color.cyan);
        table.setSelectionBackground(Color.WHITE);		       
        table.setForeground(Color.BLACK);
        table.setDefaultEditor(Object.class, null); //this code means table not editable by double click
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setCursor(tableSelect);	 //this code belongs to set mouse cursor by select table
        
        
       
       
        table.setRowHeight(100);
        scroll=new JScrollPane(table); //this code belongs to scrollpane called table
        scroll.setBounds(0, 0, 600, 1000);
        panel_right.add(scroll);
        
        
        //this code belongs to picture add button mouseclick work
        btn_attach_image.addActionListener(new ActionListener() {
			
			private FileInputStream fis;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JFileChooser file = new JFileChooser();
		        file.setCurrentDirectory(new File(System.getProperty("user.home")));				        
				filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
				file.setFileFilter(filter);
				file.showOpenDialog(null);
				f=file.getSelectedFile();
				filename=f.getAbsolutePath();
				ImageIcon imageicon=new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lbl_image_show.getWidth(), lbl_image_show.getHeight(), Image.SCALE_SMOOTH));
				lbl_image_show.setIcon(imageicon);
				
				try {
					image=new File(filename);
					fis = new FileInputStream(image);
					ByteArrayOutputStream output=new ByteArrayOutputStream();
					byte [] buf=new byte [1024];
					for(int readNum; (readNum=fis.read(buf))!=-1;) {
						output.write(buf,0,readNum);
					}
					picture=output.toByteArray();
					
				}
				catch (Exception eb) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, eb);
					
				}	 
		}
			
		});
        
        //this code belongs to add button action listener
		btn_add.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent addKey) {
				String id=txt_id.getText();
				String first_name=txt_first_name.getText();
				String surname=txt_surname.getText();
				String father_name=txt_father_name.getText();
				String mother_name=txt_mother_name.getText();
				String address=txt_area_address.getText();
				String roll=txt_roll.getText();
				
				if(id.equals("")) {
					JOptionPane.showMessageDialog(null, "Id can't be empty");
				}
				else if(first_name.equals("")) {
					JOptionPane.showMessageDialog(null, "First name can't be empty");
				}
				 else if(!(Pattern.matches("^[a-zA-z ]{0,30}$", txt_first_name.getText())))
				 {
                     try{
                        JOptionPane.showMessageDialog(null,"First name input incorrect");
                 
             }
                     catch(Exception ex){
                             System.out.println("Successfully first name enter");
                             }
                     }
				else if(surname.equals("")) {
							JOptionPane.showMessageDialog(null, "Surname can't be empty");
						}
				else if(!(Pattern.matches("^[a-zA-z ]{0,15}$", txt_surname.getText())))
				 {
                    try{
                       JOptionPane.showMessageDialog(null,"Surname input incorrect");
                
            }
                    catch(Exception ex){
                            System.out.println("Successfully surname enter");
                            }
                    }
				else if (j_calendar_birthdate.getDate()==null) {

	                   JOptionPane.showMessageDialog(null, "Birthdate can't be empty");
	                }
				else if(father_name.equals("")) {
					JOptionPane.showMessageDialog(null, "Father's name can't be empty");
				}
				else if(!(Pattern.matches("^[a-zA-z ]{0,35}$", txt_father_name.getText())))
				 {
                    try{
                       JOptionPane.showMessageDialog(null,"Father's name input incorrect");
                
            }
                    catch(Exception ex){
                            System.out.println("Successfully father's name enter");
                            }
                    }
				else if(mother_name.equals("")) {
					JOptionPane.showMessageDialog(null, "Mother's name can't be empty");
				}
				else if(!(Pattern.matches("^[a-zA-z ]{0,35}$", txt_mother_name.getText())))
				 {
                   try{
                      JOptionPane.showMessageDialog(null,"Mother's name input incorrect");
               
           }
                   catch(Exception ex){
                           System.out.println("Successfully mother's name enter");
                           }
                   }
				else if(txt_f_number.getText().trim().equals("+8801")) {
					
									JOptionPane.showMessageDialog(null, "Phone number can't be empty");
								}
				else if(roll.equals("")) {
					JOptionPane.showMessageDialog(null, "Roll can't be empty");
				}
				 else if (j_calendar_admission.getDate()==null) {

	                   JOptionPane.showMessageDialog(null, "Admissiondate can't be empty");
	                }
				 else if(address.equals("")) {
						JOptionPane.showMessageDialog(null, "Address can't be empty");
				 }
				 else if (picture == null) {
	                    JOptionPane.showMessageDialog(null,"Image can't be empty");

	                } 
					            
				else {
				try{
					
					String sql = "INSERT INTO tbl_student"
							+"(`ID`, `FIRST NAME`, `SURNAME`, `BIRTHDATE`, `FATHER'S NAME`, `MOTHER'S NAME`, `GENDER`, `MOBILE NUMBER`, `CLASS`, `ROLL`, `ADMISSION DATE`, `ADDRESS`, `IMAGES`)"
							+"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
				             
							
					pst = conn.prepareStatement(sql);
					
					pst.setString(1,txt_id.getText());
					pst.setString(2,txt_first_name.getText());
					pst.setString(3,txt_surname.getText());
					pst.setString(4,((JTextField) j_calendar_birthdate.getDateEditor().getUiComponent()).getText());
					pst.setString(5,txt_father_name.getText());
					pst.setString(6,txt_mother_name.getText());
					pst.setString(7,(String) cb_gender.getSelectedItem());
					pst.setString(8,txt_f_number.getText());
					pst.setString(9,(String) cb_class.getSelectedItem());
					pst.setString(10,txt_roll.getText());
					pst.setString(11,((JTextField) j_calendar_admission.getDateEditor().getUiComponent()).getText());
					pst.setString(12,txt_area_address.getText());
					pst.setBytes(13, picture);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Inserted successfully");
					 
					}
					catch(SQLException | HeadlessException ex){
					JOptionPane.showMessageDialog(null, ex);
					} 
				
		   
				}
				
				showTableData();
				
			
			}
		});
		
		
		
		//this code belongs to update button action listener
		btn_update.addActionListener(new ActionListener() {
					
					
					@Override
					public void actionPerformed(ActionEvent addKey) {
						String id=txt_id.getText();
						String first_name=txt_first_name.getText();
						String surname=txt_surname.getText();
						String father_name=txt_father_name.getText();
						String mother_name=txt_mother_name.getText();
						String address=txt_area_address.getText();
						String roll=txt_roll.getText();
						
						if(id.equals("")) {
							JOptionPane.showMessageDialog(null, "Id can't be empty");
						}
						else if(first_name.equals("")) {
							JOptionPane.showMessageDialog(null, "First name can't be empty");
						}
						 else if(!(Pattern.matches("^[a-zA-z ]{0,30}$", txt_first_name.getText())))
						 {
		                     try{
		                        JOptionPane.showMessageDialog(null,"First name input incorrect");
		                 
		             }
		                     catch(Exception ex){
		                             System.out.println("Successfully first name enter");
		                             }
		                     }
						else if(surname.equals("")) {
									JOptionPane.showMessageDialog(null, "Surname can't be empty");
								}
						else if(!(Pattern.matches("^[a-zA-z ]{0,15}$", txt_surname.getText())))
						 {
		                    try{
		                       JOptionPane.showMessageDialog(null,"Surname input incorrect");
		                
		            }
		                    catch(Exception ex){
		                            System.out.println("Successfully surname enter");
		                            }
		                    }
						else if (j_calendar_birthdate.getDate()==null) {

			                   JOptionPane.showMessageDialog(null, "Birthdate can't be empty");
			                }
						else if(father_name.equals("")) {
							JOptionPane.showMessageDialog(null, "Father's name can't be empty");
						}
						else if(!(Pattern.matches("^[a-zA-z ]{0,35}$", txt_father_name.getText())))
						 {
		                    try{
		                       JOptionPane.showMessageDialog(null,"Father's name input incorrect");
		                
		            }
		                    catch(Exception ex){
		                            System.out.println("Successfully father's name enter");
		                            }
		                    }
						else if(mother_name.equals("")) {
							JOptionPane.showMessageDialog(null, "Mother's name can't be empty");
						}
						
						else if(txt_f_number.getText().trim().equals("+8801")) {
							
											JOptionPane.showMessageDialog(null, "Phone number can't be empty");
										}
						else if(roll.equals("")) {
							JOptionPane.showMessageDialog(null, "Roll can't be empty");
						}
						 else if (j_calendar_admission.getDate()==null) {

			                   JOptionPane.showMessageDialog(null, "Admissiondate can't be empty");
			                }
						 else if(address.equals("")) {
								JOptionPane.showMessageDialog(null, "Address can't be empty");
						 }
						 else if (picture == null) {
			                    JOptionPane.showMessageDialog(null,"Image can't be empty");

			                } 
						 else if(!(Pattern.matches("^[a-zA-z ]{0,35}$", txt_mother_name.getText())))
						 {
		                   try{
		                      JOptionPane.showMessageDialog(null,"Mother's name input incorrect");
		               
		           }
		                   catch(Exception ex){
		                           System.out.println("Successfully mother's name enter");
		                           }
		                   }
							            
						else {
						try{
							
							String sql ="UPDATE `tbl_student` SET `FIRST NAME`=?,`SURNAME`=?,`BIRTHDATE`=?,`FATHER'S NAME`=?,`MOTHER'S NAME`=?,`GENDER`=?,`MOBILE NUMBER`=?,`CLASS`=?,`ROLL`=?,`ADMISSION DATE`=?,`ADDRESS`=?,`IMAGES`=? WHERE ID=?";		
							pst = conn.prepareStatement(sql);
							
							
							pst.setString(1,txt_first_name.getText());
							pst.setString(2,txt_surname.getText());
							pst.setString(3,((JTextField) j_calendar_birthdate.getDateEditor().getUiComponent()).getText());
							pst.setString(4,txt_father_name.getText());
							pst.setString(5,txt_mother_name.getText());
							pst.setString(6,(String) cb_gender.getSelectedItem());
							pst.setString(7,txt_f_number.getText());
							pst.setString(8,(String) cb_class.getSelectedItem());
							pst.setString(9,txt_roll.getText());
							pst.setString(10,((JTextField) j_calendar_admission.getDateEditor().getUiComponent()).getText());
							pst.setString(11,txt_area_address.getText());
							pst.setBytes(12, picture);
							pst.setString(13,txt_id.getText());
							pst.executeUpdate();
							JOptionPane.showMessageDialog(null,"Update successfully");
							}
							catch(SQLException | HeadlessException ex){
								System.out.println(ex);
							} 
						
				   
						}
						
						showTableData();
						
					
					}
				});
				
		//this code belongs to delete button action listener
        btn_delete.addActionListener((ActionEvent e) -> {
            String id = txt_id.getText(); //id field are null than accept if condition another else condition follow
            int p = JOptionPane.showConfirmDialog(null, "Do you really want to delete","Delete",JOptionPane.YES_NO_OPTION);						
	    if(p==0) {
                if (id.equals("")) {

                   System.out.println("No data found");
                } else {
                    try {

                        String sql = "DELETE FROM tbl_student WHERE ID =?";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, txt_id.getText());
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Delete successfully");

                        //this code belongs to all field blank after click delete button
                        txt_id.setText("");
                        txt_first_name.setText("");
                        j_calendar_birthdate.setDate(null);
                        txt_surname.setText("");
                        txt_father_name.setText("");
                        txt_mother_name.setText("");
                        cb_gender.setSelectedItem("Male");  
                        txt_f_number.setText("");
                        cb_class.setSelectedItem("One");
                        txt_roll.setText("");
                        j_calendar_admission.setDate(null);
                        txt_area_address.setText("");
                        lbl_image_show.setIcon(null);
                        table.getSelectionModel().clearSelection();
                        cb_search.setSelectedItem("Student Id");
                        txt_search.setText("");
                    } catch (SQLException | HeadlessException ex) {
                        System.out.println(ex);
                    }

                }
            
            }
            showTableData();
        });
        
      //this code belongs to reset button action listener
        btn_clear.addActionListener((ActionEvent resetKey) -> {
            // TODO Auto-generated method stub

            if (resetKey.getSource() == btn_clear) {

            	 txt_id.setText("");
                 txt_first_name.setText("");
                 j_calendar_birthdate.setDate(null);
                 txt_surname.setText("");
                 txt_father_name.setText("");
                 txt_mother_name.setText("");
                 cb_gender.setSelectedItem("Male");  
                 txt_f_number.setText("");
                 cb_class.setSelectedItem("One");
                 txt_roll.setText("");
                 j_calendar_admission.setDate(null);
                 txt_area_address.setText("");
                 lbl_image_show.setIcon(null);
                 table.getSelectionModel().clearSelection();
                 cb_search.setSelectedItem("Student Id");
                 txt_search.setText("");
                 

                System.out.println("All data are reset");

            }
            showTableData();
        });
        
        
        //this code belongs to sql set query search comboBox field
        cb_search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
        			
					String sql="SELECT * FROM `tbl_student` WHERE ID=?";
        			System.out.println(sql);
        			pst=conn.prepareStatement(sql);
        			pst.setString(1,(String) cb_search.getSelectedItem().toString());
        			rs=pst.executeQuery();
        			table.setModel(DbUtils.resultSetToTableModel(rs));	
        			pst.close();
        			rs.close();
        			
				}
				
        		catch(Exception ex){
        			JOptionPane.showMessageDialog(null, ex);
					} 
				
					showTableData();
			}
		});
        
		
        //this code belongs search field action listener
        txt_search.addKeyListener(new KeyAdapter() {
        	public void keyReleased(KeyEvent arg0) {
        		
						
						try {
							String selection=(String) cb_search.getSelectedItem();
							String sql=" SELECT * FROM `tbl_student` WHERE "+selection+"=?";
							System.out.println(sql);
							pst=conn.prepareStatement(sql);
							pst.setString(1,txt_search.getText());
							rs=pst.executeQuery();
							table.setModel(DbUtils.resultSetToTableModel(rs));
							pst.close();									
		        			}
						 catch (SQLException e) {
					e.printStackTrace();
				}
        		
        	}
        
		});
		
		
		//this code belongs to table mouseclick
        table.addMouseListener(new MouseListener() {


			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				 // TODO Auto-generated method stub
                int rows = table.getSelectedRow();

                //this code belongs to all data pass from jtable click and show image data have a store mysql field
                String Table_click = (table.getModel().getValueAt(rows, 0).toString());
                ImageIcon imageicon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lbl_image_show.getWidth(), lbl_image_show.getHeight(), Image.SCALE_SMOOTH));
                lbl_image_show.setIcon(imageicon);
                try {

                    String sql = " SELECT IMAGES FROM tbl_admin WHERE ID = '" + Table_click + "' ";
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        byte[] imagedata = rs.getBytes("IMAGES");
                        ImageIcon formate = new ImageIcon(imagedata);
                        lbl_image_show.setIcon(formate);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                //this code belongs to all data pass from jtable click and show data have a store mysql field
                try {

                    String sql = " SELECT * FROM tbl_student WHERE ID = '" + Table_click + "' ";
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        txt_id.setText(rs.getString("ID"));
                        txt_first_name.setText(rs.getString("FIRST NAME"));
                        txt_surname.setText(rs.getString("SURNAME"));
                        Date birth_date = rs.getDate("BIRTHDATE");
                        j_calendar_birthdate.setDate(birth_date);
                        txt_father_name.setText(rs.getString("FATHER'S NAME"));
                        txt_mother_name.setText(rs.getString("MOTHER'S NAME"));
                        cb_gender.setSelectedItem(rs.getString("GENDER"));
                        txt_f_number.setText(rs.getString("MOBILE NUMBER"));
                        cb_class.setSelectedItem(rs.getString("CLASS"));
                        txt_roll.setText(rs.getString("ROLL"));
                        Date admission_date = rs.getDate("ADMISSION DATE");
                        j_calendar_admission.setDate(admission_date);
                        txt_area_address.setText(rs.getString("ADDRESS"));
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			});
        
        
       
        //adding panels into frames
        frame.add(panel_left, BorderLayout.WEST);
        frame.add(panel_right, BorderLayout.EAST);

        //make frame visible
        frame.setVisible(true);
		
	}
	
	public void showTableData(){
		
		try{
		String sql = "SELECT * FROM tbl_student ORDER BY ID";
		pst = conn.prepareStatement(sql);
		rs=pst.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(Exception ex){
		JOptionPane.showMessageDialog(null, ex);
		 
		}
		 
		}

}
