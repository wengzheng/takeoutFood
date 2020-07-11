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

import cn.edu.zucc.takeoutfood.control.ComodityManager;
import cn.edu.zucc.takeoutfood.control.ShopManager;
import cn.edu.zucc.takeoutfood.control.SystemUserManager;
import cn.edu.zucc.takeoutfood.model.BeanComodity;
import cn.edu.zucc.takeoutfood.model.BeanShop;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.BusinessException;

public class FrmcomodityManager_AddComodity extends JDialog implements ActionListener {
		private BeanComodity comodity=null;
		
		private JPanel toolBar = new JPanel();
		private JPanel workPane = new JPanel();
		private Button btnOk = new Button("确定");
		private Button btnCancel = new Button("取消");
		
		private JLabel labelUsername = new JLabel("商品名：");
		private JLabel labelprice = new JLabel("定价:");
		private final JTextField edtprice = new JTextField(20);
		private final JTextField edtname = new JTextField(20);
		public FrmcomodityManager_AddComodity(JDialog f, String s, boolean b) {
			super(f, s, b);
			edtname.setBounds(94, 5, 168, 24);
			edtname.setColumns(10);
			toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
			this.getContentPane().add(toolBar, BorderLayout.SOUTH);
			workPane.setLayout(null);
			labelUsername.setBounds(8, 8, 72, 18);
			workPane.add(labelUsername);
			this.getContentPane().add(workPane, BorderLayout.CENTER);
			btnOk.setBounds(171, 65, 44, 25);
			workPane.add(btnOk);
			btnCancel.setBounds(221, 65, 44, 25);
			workPane.add(btnCancel);
			labelprice.setBounds(8, 39, 72, 18);
			workPane.add(labelprice);
			edtprice.setBounds(96, 35, 166, 24);
			
			workPane.add(edtprice);
			
			workPane.add(edtname);
			
			
			
			
			this.btnCancel.addActionListener(this);
			this.btnOk.addActionListener(this);
			this.setSize(300, 148);
			// 屏幕居中显示
			double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			this.setLocation((int) (width - this.getWidth()) / 2,
					(int) (height - this.getHeight()) / 2);

			this.validate();
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==this.btnCancel) {
				this.setVisible(false);
				return;
			}
			else if(e.getSource()==this.btnOk){
				float price ;
				String commodityname=this.edtname.getText();
				if("".equals(this.edtprice.getText())) {
					price=0;
				}
				else{
					price =Float.parseFloat(new String(this.edtprice.getText()));
				}
				
				comodity=new BeanComodity();
				comodity.setCommoditytypeNUM(FrmComodityTypeManager.comoditytypeid);
				comodity.setCommodityname(commodityname);
				comodity.setPrice(price);
				try {
					(new ComodityManager()).createComodity(comodity);
					this.setVisible(false);
				} catch (BaseException e1) {
					this.comodity=null;
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
		public BeanComodity getcomodity() {
			return comodity;
		}
}
