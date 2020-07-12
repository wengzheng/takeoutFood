package cn.edu.zucc.takeoutfood.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cn.edu.zucc.takeoutfood.control.CouponManager;
import cn.edu.zucc.takeoutfood.control.FullReductionPlanManager;
import cn.edu.zucc.takeoutfood.model.BeanCoupon;
import cn.edu.zucc.takeoutfood.model.FullReductionPlan;
import cn.edu.zucc.takeoutfood.util.BaseException;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class FrmFullReductionPlan_addPlan extends JDialog implements ActionListener{
	private FullReductionPlan rider=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelUserid = new JLabel("�Żݽ�");
	private JLabel labelUsername = new JLabel("������");
	private JTextField edtUserid = new JTextField(20);
	private JTextField edtUsername = new JTextField(20);
	private JComboBox comboBox = new JComboBox();
	private JLabel withcoupon = new JLabel("�Ƿ�����Ż�ȯ���ӣ�");
	
	

public FrmFullReductionPlan_addPlan(JDialog f, String s, boolean b) {
	super(f, s, b);
	toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
	this.getContentPane().add(toolBar, BorderLayout.SOUTH);
	workPane.setLayout(null);
	labelUserid.setVerticalAlignment(SwingConstants.BOTTOM);
	labelUserid.setBounds(8, 8, 90, 18);
	workPane.add(labelUserid);
	edtUserid.setBounds(102, 5, 166, 24);
	workPane.add(edtUserid);
	labelUsername.setBounds(8, 39, 90, 18);
	workPane.add(labelUsername);
	edtUsername.setBounds(102, 34, 166, 24);
	workPane.add(edtUsername);
	this.getContentPane().add(workPane, BorderLayout.CENTER);
	btnOk.setBounds(178, 128, 44, 25);
	workPane.add(btnOk);
	btnCancel.setBounds(228, 128, 44, 25);
	workPane.add(btnCancel);
	withcoupon.setBounds(8, 78, 160, 18);
	
	workPane.add(withcoupon);
	
	
	comboBox.setModel(new DefaultComboBoxModel(new String[] {"��", "��"}));
	comboBox.setBounds(178, 75, 44, 24);
	workPane.add(comboBox);
	
	
	
	this.btnCancel.addActionListener(this);
	this.btnOk.addActionListener(this);
	this.setSize(300, 220);
	// ��Ļ������ʾ
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
		float discount;//�Żݽ��
		float fullcount;//�������
		boolean wc;//�Ƿ�����Ż�ȯ����
		if("".equals(new String(this.edtUserid.getText() ))) 
			discount=0;
		else 
			discount=Float.parseFloat(new String(this.edtUserid.getText()));
		if("".equals(new String(this.edtUsername.getText() )))
			fullcount=0;
		else 
			fullcount=Float.parseFloat(new String(this.edtUsername.getText()));
		if(this.comboBox.getSelectedIndex()==1)
			wc=true;
		else
			wc=false;
		
		rider=new FullReductionPlan();
		rider.setF_discount(fullcount);
		rider.setWithcoupon(wc);
		rider.setFullreduceprice(discount);
		//System.out.println(wc);
		try {
			(new FullReductionPlanManager()).createFullReductionPlan(rider);
			this.setVisible(false);
		} catch (BaseException e1) {
			this.rider=null;
			JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
}

	public FullReductionPlan getReductionPlan()  {
		return rider;
	}
}
