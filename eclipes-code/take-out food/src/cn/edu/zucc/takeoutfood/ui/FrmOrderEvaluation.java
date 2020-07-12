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
import cn.edu.zucc.takeoutfood.model.BeanOrder;
import cn.edu.zucc.takeoutfood.util.BaseException;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class FrmOrderEvaluation extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnDelete = new Button("����");
	private Object tblTitle[]={"���к�","������","Ҫ���ʹ�ʱ��","����״̬"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	private final JComboBox comboBox = new JComboBox();
	private void reloadUserTable(){
		try {
			//������ȯ
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
	
	public FrmOrderEvaluation(FrmMain f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"NotEvaluated", "Favorable", "Negative"}));
		
		toolBar.add(comboBox);
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
				JOptionPane.showMessageDialog(null,  "��ѡ�񶩵�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ���Ǹö�����","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				int Orderid=Integer.parseInt(this.tblData[i][0].toString());
				try {
					
					(new OrderManager()).AddEvaluation(Orderid,(new String(this.comboBox.getSelectedItem().toString())));
					this.reloadUserTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
