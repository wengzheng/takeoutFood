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
import javax.swing.JTextField;

import cn.edu.zucc.takeoutfood.control.UserManager;
import cn.edu.zucc.takeoutfood.model.BeanUser;
import cn.edu.zucc.takeoutfood.util.BaseException;
import javax.swing.SwingConstants;

public class FrmAdminManager_AddUser extends JDialog implements ActionListener {
	private BeanUser user=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelUserid = new JLabel("管理员账号：");
	private JLabel labelUsername = new JLabel("姓名：");
	
	private JTextField edtUserid = new JTextField(20);
	private JTextField edtUsername = new JTextField(20);
	public FrmAdminManager_AddUser(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(null);
		labelUserid.setVerticalAlignment(SwingConstants.BOTTOM);
		labelUserid.setBounds(8, 8, 90, 18);
		workPane.add(labelUserid);
		edtUserid.setBounds(102, 5, 166, 24);
		workPane.add(edtUserid);
		labelUsername.setBounds(8, 37, 90, 18);
		workPane.add(labelUsername);
		edtUsername.setBounds(102, 34, 166, 24);
		workPane.add(edtUsername);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		btnOk.setBounds(182, 73, 44, 25);
		workPane.add(btnOk);
		btnCancel.setBounds(233, 73, 44, 25);
		workPane.add(btnCancel);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
		this.setSize(300, 158);
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
			user=new BeanUser();
			user.setUserid(userid);
			user.setUsername(username);
			
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
