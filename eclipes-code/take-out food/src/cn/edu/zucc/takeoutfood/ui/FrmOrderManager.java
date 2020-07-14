package cn.edu.zucc.takeoutfood.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
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

import cn.edu.zucc.takeoutfood.control.ComodityTypeManager;
import cn.edu.zucc.takeoutfood.control.CouponHoldManager;
import cn.edu.zucc.takeoutfood.control.OrderManager;
import cn.edu.zucc.takeoutfood.control.ShopManager;
import cn.edu.zucc.takeoutfood.model.BeanOrder;
import cn.edu.zucc.takeoutfood.model.BeanShop;
import cn.edu.zucc.takeoutfood.util.BaseException;
import javax.swing.JButton;

public class FrmOrderManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("购物");
	private Button btnDelete = new Button("删除订单");
	private JButton btnNewButton = new JButton("使用优惠劵");
	private Object tblTitle[]={"序列号","结算金额","要求送达时间","订单状态"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	private void reloadUserTable(){
		try {
			//集单送券
			List<BeanOrder> users=(new OrderManager()).loadAllOrders(false);
			tblData =new Object[users.size()][4];
			for(int i=0;i<users.size();i++){
				tblData[i][0]=users.get(i).getOrderID();
				tblData[i][1]=users.get(i).getSettlementamount();
				tblData[i][2]=users.get(i).getRequiretime();
				tblData[i][3]=users.get(i).getOrderstate();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	public FrmOrderManager(FrmMain f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
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

		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnNewButton.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			FrmOrderManager_AddOrder dlg=new FrmOrderManager_AddOrder(this,"购物",true);
			dlg.setVisible(true);
			//刷新表格
			this.reloadUserTable();
			
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.userTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除订单吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				int Orderid=Integer.parseInt(this.tblData[i][0].toString());
				try {
					(new OrderManager()).deleteOrder(Orderid);
					this.reloadUserTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
		else if(e.getSource()==this.btnNewButton){
			int i=this.userTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定该订单使用优惠劵吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				CouponHoldManager.dec_orderid=Integer.parseInt(this.tblData[i][0].toString());
				FrmCouponDetail dlg=new FrmCouponDetail(this,"使用优惠劵",true);
				dlg.setVisible(true);				
			}
			this.reloadUserTable();
		}
	}
}
	
	


