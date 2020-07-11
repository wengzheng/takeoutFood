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

import cn.edu.zucc.takeoutfood.control.ComodityManager;
import cn.edu.zucc.takeoutfood.control.ComodityTypeManager;
import cn.edu.zucc.takeoutfood.model.BeanComodity;
import cn.edu.zucc.takeoutfood.model.BeanComoditytype;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class FrmComodityTypeManager_AddComodity extends JDialog implements ActionListener{
	private BeanComoditytype comodity=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelUsername = new JLabel("商品名：");
	private JLabel labelNum = new JLabel("数量：");
	private JTextField edtnum = new JTextField(20);
	private final JTextField edtname = new JTextField(20);
	public FrmComodityTypeManager_AddComodity(JDialog f, String s, boolean b)  {
			super(f, s, b);
			edtname.setBounds(94, 5, 168, 24);
			edtname.setColumns(10);
			toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
			this.getContentPane().add(toolBar, BorderLayout.SOUTH);
			workPane.setLayout(null);
			labelNum.setVerticalAlignment(SwingConstants.BOTTOM);
			labelNum.setBounds(8, 37, 90, 18);
			workPane.add(labelNum);
			labelUsername.setBounds(8, 8, 72, 18);
			workPane.add(labelUsername);
			this.getContentPane().add(workPane, BorderLayout.CENTER);
			btnOk.setBounds(168, 82, 44, 25);
			workPane.add(btnOk);
			btnCancel.setBounds(218, 82, 44, 25);
			workPane.add(btnCancel);
			edtnum.setBounds(94, 34, 168, 24);
			workPane.add(edtnum);
			workPane.add(edtname);
			
			
			this.btnCancel.addActionListener(this);
			this.btnOk.addActionListener(this);
			this.setSize(300, 164);
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
				int commoditynumber;
				String commodityname=this.edtname.getText();
				
				if("".equals(this.edtnum.getText())){
					commoditynumber=0;
				}else {
					commoditynumber=Integer.parseInt(new String(this.edtnum.getText()));
				}
				comodity=new BeanComoditytype();
				comodity.setCommoditytypename(commodityname);
				comodity.setCommoditynum(commoditynumber);
				try {
					(new ComodityTypeManager()).createComodityType(comodity);
					this.setVisible(false);
				} catch (BaseException e1) {
					this.comodity=null;
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
		public BeanComoditytype getcomodity() {
			return comodity;
		}
		
}