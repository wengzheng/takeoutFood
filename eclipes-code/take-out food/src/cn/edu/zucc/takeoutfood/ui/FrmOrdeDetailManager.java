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

import cn.edu.zucc.takeoutfood.control.ComodityManager;
import cn.edu.zucc.takeoutfood.control.OrderManager;
import cn.edu.zucc.takeoutfood.model.BeanComodity;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class FrmOrdeDetailManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnDelete = new Button("加入订单");
	private Object tblTitle[]={"编号","商品名","单价"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	private void reloadUserTable(){
		try {
			List<BeanComodity> users=(new ComodityManager()).loadAllComoditys(false);
			tblData =new Object[users.size()][3];
			for(int i=0;i<users.size();i++){
				tblData[i][0]=users.get(i).getCommodityNUM();
				tblData[i][1]=users.get(i).getCommodityname();
				tblData[i][2]=users.get(i).getPrice();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
		
		public FrmOrdeDetailManager(FrmOrderManager_AddOrder f, String s, boolean b)  {
			super(f, s, b);
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			toolBar.add(this.btnDelete);
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

			this.btnDelete.addActionListener(this);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
				}
			});
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==this.btnDelete){
				int i=this.userTable.getSelectedRow();
				if(i<0) {
					JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(JOptionPane.showConfirmDialog(this,"确定购买商品吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					int comodityid=Integer.parseInt(this.tblData[i][0].toString());
					try {
						(new OrderManager()).AddOrderdetail(comodityid);
						this.reloadUserTable();
						
					} catch (BaseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
}
