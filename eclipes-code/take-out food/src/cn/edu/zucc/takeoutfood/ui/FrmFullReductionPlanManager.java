package cn.edu.zucc.takeoutfood.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.takeoutfood.control.CouponManager;
import cn.edu.zucc.takeoutfood.control.FullReductionPlanManager;
import cn.edu.zucc.takeoutfood.model.BeanCoupon;
import cn.edu.zucc.takeoutfood.model.FullReductionPlan;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class FrmFullReductionPlanManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("������������");
	private Button btnDelete = new Button("ɾ����������");
	private Object tblTitle[]={"���","���������","�Żݽ��","�Ƿ�����Ż�ȯ����"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	private void reloadUserTable(){
		try {
			List<FullReductionPlan> users=(new FullReductionPlanManager()).loadAllComoditys(false);
			tblData =new Object[users.size()][4];
			for(int i=0;i<users.size();i++){
				tblData[i][0]=users.get(i).getFullreduceplanNUM();
				tblData[i][1]=users.get(i).getF_discount();
				tblData[i][2]=users.get(i).getFullreduceprice();
				tblData[i][3]=users.get(i).getWithcoupon();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	public FrmFullReductionPlanManager(FrmMain f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadUserTable();
		this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAdd){
			FrmFullReductionPlan_addPlan dlg=new FrmFullReductionPlan_addPlan(this,"������������",true);
			dlg.setVisible(true);
			if(dlg.getReductionPlan()!=null){//ˢ�±���
				this.reloadUserTable();
			}
		}
		
		
		else if(e.getSource()==this.btnDelete){
			int i=this.userTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ����������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ���Ż�ȯ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				int comodityid=Integer.parseInt(this.tblData[i][0].toString());
				try {
					(new FullReductionPlanManager()).deleteComodity(comodityid);
					
					this.reloadUserTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
}