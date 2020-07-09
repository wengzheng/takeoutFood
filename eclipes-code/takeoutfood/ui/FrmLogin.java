package cn.edu.zucc.takeoutfood.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

import cn.edu.zucc.takeoutfood.control.SystemUserManager;
import cn.edu.zucc.takeoutfood.model.BeanSystemUser;
import cn.edu.zucc.takeoutfood.util.BaseException;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class FrmLogin extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnLogin = new Button("登录");
	private Button btnCancel = new Button("退出");
	private JLabel labelUser = new JLabel("用户：");
	private JLabel labelPwd = new JLabel("密码：");
	private JLabel labeltype = new JLabel("用户类型：");
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	private JComboBox comboBox = new JComboBox();
	public FrmLogin(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		btnLogin.setBounds(184, 5, 44, 25);
		toolBar.add(btnLogin);
		btnCancel.setBounds(233, 5, 44, 25);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(null);
		labelUser.setBounds(8, 8, 45, 18);
		workPane.add(labelUser);
		edtUserId.setBounds(58, 5, 166, 24);
		workPane.add(edtUserId);
		labelPwd.setBounds(8, 39, 45, 18);
		workPane.add(labelPwd);
		edtPwd.setBounds(58, 34, 166, 24);
		workPane.add(edtPwd);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		labeltype.setBounds(8, 70, 81, 18);
		workPane.add(labeltype);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "管理员", "用户", "商家", "骑手"}));
		comboBox.setBounds(95, 64, 83, 24);
		workPane.add(comboBox);
		
		
		this.setSize(300, 180);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogin) {
			SystemUserManager sum=new SystemUserManager();
			String userid=this.edtUserId.getText();
			String pwd=new String(this.edtPwd.getPassword());
			FrmMain.type=this.comboBox.getSelectedIndex();
			if(this.comboBox.getSelectedIndex()==0) {
				JOptionPane.showMessageDialog(null,  "用户类型为空","错误提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				BeanSystemUser user=sum.loadUser(userid, this.comboBox.getSelectedIndex());			
				if(pwd.equals(user.getPwd())){
					SystemUserManager.currentUser=user;
					setVisible(false);
				}
				else{
					JOptionPane.showMessageDialog(null,  "密码错误","错误提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, "其他错误"+e1.getMessage(), "错误提示",JOptionPane.ERROR_MESSAGE);
			}
			
			
			
		} else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		}
	}
}
	
