package cn.edu.zucc.takeoutfood.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import cn.edu.zucc.takeoutfood.control.PwdManager;
import cn.edu.zucc.takeoutfood.control.SystemUserManager;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class FrmModifyPwd extends JDialog implements ActionListener {
		PwdManager pm=new PwdManager();
		
		private JPanel toolBar = new JPanel();
		private JPanel workPane = new JPanel();
		private Button btnOk = new Button("确定");
		private Button btnCancel = new Button("取消");
		
		private JLabel labelPwdOld = new JLabel("原密码：");
		private JLabel labelPwd = new JLabel("新密码：");
		private JLabel labelPwd2 = new JLabel("新密码：");
		private JPasswordField edtPwdOld = new JPasswordField(20);
		private JPasswordField edtPwd = new JPasswordField(20);
		private JPasswordField edtPwd2 = new JPasswordField(20);
		public FrmModifyPwd(Frame f, String s, boolean b) {
			super(f, s, b);
			toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
			toolBar.add(this.btnOk);
			toolBar.add(btnCancel);
			this.getContentPane().add(toolBar, BorderLayout.SOUTH);
			workPane.setLayout(null);
			labelPwdOld.setBounds(23, 8, 60, 18);
			workPane.add(labelPwdOld);
			edtPwdOld.setBounds(88, 5, 166, 24);
			workPane.add(edtPwdOld);
			labelPwd.setBounds(23, 39, 60, 18);
			workPane.add(labelPwd);
			edtPwd.setBounds(88, 36, 166, 24);
			workPane.add(edtPwd);
			labelPwd2.setBounds(23, 66, 60, 18);
			workPane.add(labelPwd2);
			edtPwd2.setBounds(88, 63, 166, 24);
			workPane.add(edtPwd2);
			this.getContentPane().add(workPane, BorderLayout.CENTER);
			this.setSize(360, 240);
			this.btnCancel.addActionListener(this);
			this.btnOk.addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==this.btnCancel)
				this.setVisible(false);
			else if(e.getSource()==this.btnOk) {
				try {
					pm.changePwd(SystemUserManager.currentUser, new String(edtPwdOld.getPassword()),new String(edtPwd.getPassword()),new String(edtPwd2.getPassword()));
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				this.setVisible(false);
			}
			
		}
}
