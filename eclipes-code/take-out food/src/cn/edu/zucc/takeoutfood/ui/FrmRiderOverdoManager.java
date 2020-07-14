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

import cn.edu.zucc.takeoutfood.control.OrderManager;
import cn.edu.zucc.takeoutfood.control.RiderSendManager;
import cn.edu.zucc.takeoutfood.model.BeanOrder;
import cn.edu.zucc.takeoutfood.model.BeanRiderSend;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class FrmRiderOverdoManager extends JDialog implements ActionListener {
		private JPanel toolBar = new JPanel();
		private Button btnDelete = new Button("确认送达");
		private Object tblTitle[]={"序列号","订单号","送达时间","每单收入"};
		private Object tblData[][];
		DefaultTableModel tablmod=new DefaultTableModel();
		private JTable userTable=new JTable(tablmod);
		private void reloadUserTable(){
			try {
				//集单送券
				List<BeanRiderSend> users=(new RiderSendManager()).loadAllOrdersends(false);
				tblData =new Object[users.size()][4];
				for(int i=0;i<users.size();i++){
					tblData[i][0]=i+1;
					tblData[i][1]=users.get(i).getOrderID();
					tblData[i][2]=users.get(i).getSendtime();
					tblData[i][3]=users.get(i).getIncome();
				}
				tablmod.setDataVector(tblData,tblTitle);
				this.userTable.validate();
				this.userTable.repaint();
			} catch (BaseException e) {
				e.printStackTrace();
			}
		}
		public FrmRiderOverdoManager(FrmMain f, String s, boolean b) {
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
					JOptionPane.showMessageDialog(null,  "请选择派送单","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(JOptionPane.showConfirmDialog(this,"确定已完成该订单吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					int Orderid=Integer.parseInt(this.tblData[i][0].toString());
					try {
						(new RiderSendManager()).getmoney(Orderid);
						this.reloadUserTable();
					} catch (BaseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
			this.reloadUserTable();
		}
}
