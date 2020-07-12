package cn.edu.zucc.takeoutfood.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cn.edu.zucc.takeoutfood.control.AddressManager;
import cn.edu.zucc.takeoutfood.control.SystemUserManager;
import cn.edu.zucc.takeoutfood.control.UserManager;
import cn.edu.zucc.takeoutfood.model.BeanAddress;
import cn.edu.zucc.takeoutfood.model.BeanUser;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class FrmAddressManager_AddAddress extends JDialog implements ActionListener {
	private BeanAddress user=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelUsername = new JLabel("\u6635\u79F0\uFF1A");
	private JLabel phonelabel_1 = new JLabel("\u8054\u7CFB\u7535\u8BDD:");
	private JLabel addresslabel_3 = new JLabel("\u5730\u5740\uFF1A");
	private JTextField edtUsername = new JTextField(20);
	private JTextField phoneField;
	private JTextField addressField;
	private JTextField textreachtime;
	public FrmAddressManager_AddAddress(FrmOrderManager_AddOrder f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(null);
		labelUsername.setBounds(8, 39, 90, 18);
		workPane.add(labelUsername);
		edtUsername.setBounds(102, 34, 166, 24);
		workPane.add(edtUsername);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		btnOk.setBounds(116, 160, 44, 25);
		workPane.add(btnOk);
		btnCancel.setBounds(204, 160, 44, 25);
		workPane.add(btnCancel);
		phonelabel_1.setBounds(8, 70, 90, 18);
		workPane.add(phonelabel_1);
		addresslabel_3.setBounds(8, 101, 72, 18);
		workPane.add(addresslabel_3);
		phoneField = new JTextField();
		phoneField.setBounds(102, 67, 166, 24);
		workPane.add(phoneField);
		phoneField.setColumns(10);
		addressField = new JTextField();
		addressField.setBounds(102, 98, 166, 24);
		workPane.add(addressField);
		addressField.setColumns(10);
		
		JLabel labeltime = new JLabel("\u8981\u6C42\u9001\u8FBE\u65F6\u95F4:");
		labeltime.setBounds(0, 133, 98, 18);
		workPane.add(labeltime);
		
		textreachtime = new JTextField();
		textreachtime.setBounds(102, 130, 166, 24);
		workPane.add(textreachtime);
		textreachtime.setColumns(10);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
		this.setSize(300, 252);
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
			String username=this.edtUsername.getText();
			String phone=this.phoneField.getText();
			String address=this.addressField.getText();
			user=new BeanAddress();
			user.setUserID(SystemUserManager.currentUser.getSystemNUM());
			user.setUsername(username);
			user.setPhone(phone);
			user.setAddress(address);
			try {
				(new AddressManager()).createAddress(user,new String(this.textreachtime.getText()));
				this.setVisible(false);
			} catch (BaseException e1) {
				this.user=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
