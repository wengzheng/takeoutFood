package cn.edu.zucc.takeoutfood.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cn.edu.zucc.takeoutfood.control.CouponManager;
import cn.edu.zucc.takeoutfood.control.RiderManager;
import cn.edu.zucc.takeoutfood.model.BeanComoditytype;
import cn.edu.zucc.takeoutfood.model.BeanCoupon;
import cn.edu.zucc.takeoutfood.model.BeanRider;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class FrmCouponManager_AddCoupon extends JDialog implements ActionListener{
		
		private BeanCoupon rider=null;
		private JPanel toolBar = new JPanel();
		private JPanel workPane = new JPanel();
		private Button btnOk = new Button("确定");
		private Button btnCancel = new Button("取消");
		private JLabel labelUserid = new JLabel("优惠金额");
		private JLabel labelUsername = new JLabel("结束时间");
		private JLabel labelAdminpwd = new JLabel("集单要求数");
		private JTextField edtUserid = new JTextField(20);
		private JTextField edtUsername = new JTextField(20);
		private JTextField passwordField= new JTextField(20);
		

	public FrmCouponManager_AddCoupon(JDialog f, String s, boolean b) {
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
			float discount;//优惠金额
			int num;//要求集单数
			if("".equals(new String(this.edtUserid.getText() ))) {
				discount=0;
			}
			else {
				discount=Float.parseFloat(new String(this.edtUserid.getText()));
			}
			String string=this.edtUsername.getText();
			if("".equals(new String(this.passwordField.getText() )))
				num=0;
			else 
				num=Integer.parseInt(new String(this.passwordField.getText()));
			
			rider=new BeanCoupon();
			rider.setC_discount(discount);;
			rider.setRequarrenum(num);;
			
			try {
				(new CouponManager()).createRider(rider,string);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.rider=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
	}

	public BeanCoupon getcoupon()  {
		// TODO Auto-generated method stub
		return rider;
	}


}
