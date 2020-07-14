package cn.edu.zucc.takeoutfood.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.takeoutfood.control.CouponHoldManager;
import cn.edu.zucc.takeoutfood.control.CouponManager;
import cn.edu.zucc.takeoutfood.control.ShopManager;
import cn.edu.zucc.takeoutfood.model.BeanCouponHold;
import cn.edu.zucc.takeoutfood.util.BaseException;
import javax.swing.JButton;

public class FrmCouponDetail extends JDialog implements ActionListener {
		private JPanel toolBar = new JPanel();
		private Button btnAdd = new Button("查看优惠劵");
		private JButton btnNewButton = new JButton("使用优惠劵");
		private Object tblTitle[]={"序号","数量","优惠价","截至时间"};
		private Object tblData[][];
		DefaultTableModel tablmod=new DefaultTableModel();
		private JTable userTable=new JTable(tablmod);
		
		private void reloadUserTable(){
			try {
				
				List<BeanCouponHold> users=(new CouponHoldManager()).loadMyComoditys(false);
				tblData =new Object[users.size()][4];
				for(int i=0;i<users.size();i++){
					tblData[i][0]=users.get(i).getIncouponID();
					tblData[i][1]=users.get(i).getCouponNumber();
					tblData[i][2]=users.get(i).getCoupondiscount();
					tblData[i][3]=users.get(i).getCdeadline();

				}
				tablmod.setDataVector(tblData,tblTitle);
				this.userTable.validate();
				this.userTable.repaint();
			} catch (BaseException e) {
				e.printStackTrace();
			}
		}
		public FrmCouponDetail(FrmMain f, String s, boolean b) {
			super(f, s, b);
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			toolBar.add(btnAdd);
			this.getContentPane().add(toolBar, BorderLayout.NORTH);
			
			
			//提取现有数据
			this.reloadUserTable();
			this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
			
			// 屏幕居中显示
			this.setSize(800, 600);
			double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			this.setLocation((int) (width - this.getWidth()) / 2,
					(int) (height - this.getHeight()) / 2);

			this.validate();
			this.btnAdd.addActionListener(this);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
				}
			});
		}

		public FrmCouponDetail(FrmOrderManager fo, String s, boolean b) {
			super(fo, s, b);
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			toolBar.add(btnNewButton);
			this.getContentPane().add(toolBar, BorderLayout.NORTH);
			
			
			//提取现有数据
			this.reloadUserTable();
			this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
			
			// 屏幕居中显示
			this.setSize(800, 600);
			double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			this.setLocation((int) (width - this.getWidth()) / 2,
					(int) (height - this.getHeight()) / 2);

			this.validate();
			this.btnNewButton.addActionListener(this);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
				}
			});
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==this.btnAdd) {
				try {
					(new CouponHoldManager()).addCouponHold();
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.reloadUserTable();
			}
			else if(e.getSource()==this.btnNewButton){
				int i=this.userTable.getSelectedRow();
				if(i<0) {
					JOptionPane.showMessageDialog(null,  "请选择优惠劵","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(JOptionPane.showConfirmDialog(this,"确定吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					int Incouponid=Integer.parseInt(this.tblData[i][0].toString());
					int num=Integer.parseInt(this.tblData[i][1].toString());
					double dis=Double.parseDouble(this.tblData[i][2].toString());
					try {
						(new CouponHoldManager()).deleteCoupon(Incouponid,num,dis);
						JOptionPane.showMessageDialog(null,  "该优惠卷已完成使用","提示",JOptionPane.INFORMATION_MESSAGE);
					} catch (BaseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		}
		
}
