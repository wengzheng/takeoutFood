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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.takeoutfood.control.ComodityTypeManager;
import cn.edu.zucc.takeoutfood.control.CouponHoldManager;
import cn.edu.zucc.takeoutfood.control.GiveCouponManager;
import cn.edu.zucc.takeoutfood.control.OrderManager;
import cn.edu.zucc.takeoutfood.control.SystemUserManager;
import cn.edu.zucc.takeoutfood.control.UserManager;
import cn.edu.zucc.takeoutfood.model.BeanComoditytype;
import cn.edu.zucc.takeoutfood.model.BeanOrder;
import cn.edu.zucc.takeoutfood.model.BeanUser;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class FrmOrderManager_AddOrder extends JDialog implements ActionListener{
	
	private JPanel toolBar = new JPanel();
	private JButton btnCommodity = new JButton("商品明细");
	private JButton btnText = new JButton("相关信息填写");
	private Object tblTitle[]={"商品类编号","商店编号","商品类名（商品名）"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	
	
	private void reloadUserTable(){
		try {
			List<BeanComoditytype> users=(new ComodityTypeManager()).loadAllComoditytype(false);
			tblData =new Object[users.size()][3];
			for(int i=0;i<users.size();i++){
				tblData[i][0]=users.get(i).getCommoditytypeNUM();
				tblData[i][1]=users.get(i).getShopNUM();
				tblData[i][2]=users.get(i).getCommoditytypename();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	public FrmOrderManager_AddOrder(FrmOrderManager frmOrderManager, String s, boolean b) {
		super(frmOrderManager, s, b);
		setTitle("\u8BF7\u5148\u8BA2\u8D2D\u5546\u54C1\uFF0C\u518D\u586B\u5199\u76F8\u5173\u4FE1\u606F\uFF01\uFF01\uFF01");
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		toolBar.add(btnCommodity);
		
		toolBar.add(btnText);
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

		this.btnCommodity.addActionListener(this);
		this.btnText.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnText) {
			FrmAddressManager_AddAddress dlg=new FrmAddressManager_AddAddress(this,"购物",true);
			dlg.setVisible(true);
			//刷新表格
			this.reloadUserTable();
		}
		else if(e.getSource()==this.btnCommodity){
			int i=this.userTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品类","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确认该商品类吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				BeanOrder order=new BeanOrder();
				order.setUserID(SystemUserManager.currentUser.getSystemNUM());
				order.setShopID(Integer.parseInt(this.tblData[i][1].toString()));
				FrmComodityTypeManager.comoditytypeid=Integer.parseInt(this.tblData[i][0].toString());
				try {
					FrmOrdeDetailManager dlg=new FrmOrdeDetailManager(this, "编辑商品", true);
					dlg.setVisible(true);
					(new OrderManager()).createOrder(order);
					(new GiveCouponManager()).AddGiveCoupon(Integer.parseInt(this.tblData[i][0].toString()));
					//(new CouponHoldManager()).addCouponHold();
					this.reloadUserTable();
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		}
	}
}
