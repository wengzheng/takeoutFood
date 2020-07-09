package cn.edu.zucc.takeoutfood.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cn.edu.zucc.takeoutfood.control.RiderManager;
import cn.edu.zucc.takeoutfood.control.ShopManager;
import cn.edu.zucc.takeoutfood.model.BeanRider;
import cn.edu.zucc.takeoutfood.model.BeanShop;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class FrmRiderManager_AddRider extends JDialog implements ActionListener{

	private BeanRider rider=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelUserid = new JLabel("骑手账号：");
	private JLabel labelUsername = new JLabel("姓名：");
	private JLabel labelAdminpwd = new JLabel("密码:");
	private JTextField edtUserid = new JTextField(20);
	private JTextField edtUsername = new JTextField(20);
	private JPasswordField passwordField= new JPasswordField(20);
	private final JLabel label = new JLabel("骑手身份：");
	private final JComboBox comboBox = new JComboBox();
	
	public FrmRiderManager_AddRider(JDialog f, String s, boolean b) {
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
		btnOk.setBounds(178, 128, 44, 25);
		workPane.add(btnOk);
		btnCancel.setBounds(228, 128, 44, 25);
		workPane.add(btnCancel);
		labelAdminpwd.setBounds(8, 70, 72, 18);
		workPane.add(labelAdminpwd);
		
		passwordField.setBounds(102, 65, 166, 24);
		workPane.add(passwordField);
		label.setBounds(8, 103, 90, 18);
		
		workPane.add(label);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "old","new"}));
		comboBox.setBounds(112, 102, 62, 24);
		
		workPane.add(comboBox);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
		this.setSize(300, 220);
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
			String shopid=this.edtUserid.getText();
			String shopname=this.edtUsername.getText();
			String shoppwd=new String(this.passwordField.getPassword());
			String rIdtpye=this.comboBox.getSelectedItem().toString();
			rider=new BeanRider();
			rider.setRiderid(shopid);
			rider.setRidername(shopname);
			rider.setRiderpwd(shoppwd);
			rider.setrIdtpye(rIdtpye);
			try {
				(new RiderManager()).createRider(rider);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.rider=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanRider getRider() {
		return rider;
	}
}

