package cn.edu.zucc.takeoutfood.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cn.edu.zucc.takeoutfood.control.UserManager;
import cn.edu.zucc.takeoutfood.model.BeanUser;
import cn.edu.zucc.takeoutfood.util.BaseException;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class FrmUserManager_AddUser extends JDialog implements ActionListener {
	private BeanUser user=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelUserid = new JLabel("用户账号：");
	private JLabel labelUsername = new JLabel("姓名：");
	private JLabel labelAdminpwd = new JLabel("密码:");
	private JLabel label = new JLabel("性别");
	private JComboBox comboBox = new JComboBox();
	private JLabel phonelabel_1 = new JLabel("联系电话");
	private JLabel emaillabel = new JLabel("邮箱");
	private JLabel addresslabel_3 = new JLabel("地址");
	private JTextField edtUserid = new JTextField(20);
	private JTextField edtUsername = new JTextField(20);
	private JPasswordField passwordField= new JPasswordField(20);
	private JTextField phoneField;
	private JTextField emailField_2;
	private JTextField addressField;
	public FrmUserManager_AddUser(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(null);
		labelUserid.setVerticalAlignment(SwingConstants.BOTTOM);
		labelUserid.setBounds(8, 8, 90, 18);
		workPane.add(labelUserid);
		edtUserid.setBounds(102, 5, 166, 24);
		workPane.add(edtUserid);
		labelUsername.setBounds(8, 39, 72, 18);
		workPane.add(labelUsername);
		edtUsername.setBounds(102, 34, 166, 24);
		workPane.add(edtUsername);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		btnOk.setBounds(128, 220, 44, 25);
		workPane.add(btnOk);
		btnCancel.setBounds(224, 220, 44, 25);
		workPane.add(btnCancel);
		labelAdminpwd.setBounds(8, 70, 72, 18);
		workPane.add(labelAdminpwd);
		passwordField.setBounds(102, 65, 166, 24);
		workPane.add(passwordField);
		label.setBounds(8, 101, 72, 18);
		workPane.add(label);
		phonelabel_1.setBounds(8, 132, 90, 18);
		workPane.add(phonelabel_1);
		emaillabel.setBounds(8, 163, 72, 18);
		workPane.add(emaillabel);
		addresslabel_3.setBounds(8, 194, 72, 18);
		workPane.add(addresslabel_3);
		phoneField = new JTextField();
		phoneField.setBounds(102, 132, 166, 24);
		workPane.add(phoneField);
		phoneField.setColumns(10);
		emailField_2 = new JTextField();
		emailField_2.setBounds(102, 160, 166, 24);
		workPane.add(emailField_2);
		emailField_2.setColumns(10);
		addressField = new JTextField();
		addressField.setBounds(102, 190, 166, 24);
		workPane.add(addressField);
		addressField.setColumns(10);
		
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "boy", "girl"}));
		comboBox.setBounds(112, 98, 72, 24);
		workPane.add(comboBox);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
		this.setSize(300, 302);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
	}	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			String userid=this.edtUserid.getText();
			String username=this.edtUsername.getText();
			String userpwd=new String(this.passwordField.getPassword());
			String usex=this.comboBox.getSelectedItem().toString();
			String phone=this.phoneField.getText();
			String email=this.emailField_2.getText();
			String address=this.addressField.getText();
			user=new BeanUser();
			user.setUserid(userid);
			user.setUsername(username);
			user.setUpwd(userpwd);	
			user.setUsex(usex);
			user.setPhone(phone);
			user.setEmail(email);
			user.setAddress(address);
			try {
				(new UserManager()).createUser(user);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.user=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanUser getUser() {
		return user;
	}
}