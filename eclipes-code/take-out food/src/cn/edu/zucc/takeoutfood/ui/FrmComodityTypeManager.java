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

import cn.edu.zucc.takeoutfood.control.ComodityManager;
import cn.edu.zucc.takeoutfood.control.ComodityTypeManager;
import cn.edu.zucc.takeoutfood.model.BeanComodity;
import cn.edu.zucc.takeoutfood.model.BeanComoditytype;
import cn.edu.zucc.takeoutfood.util.BaseException;
import javax.swing.JButton;

public class FrmComodityTypeManager extends JDialog implements ActionListener {
	public static int comoditytypeid;
	
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加商品类");
	private Button btnDelete = new Button("下架商品类");
	private JButton btnCommodity = new JButton("商品明细");
	private Object tblTitle[]={"序号","商品类名（商品名）","数量"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	
	private void reloadUserTable(){
		try {
			List<BeanComoditytype> users=(new ComodityTypeManager()).loadAllComoditytype(false);
			tblData =new Object[users.size()][3];
			for(int i=0;i<users.size();i++){
				tblData[i][0]=users.get(i).getCommoditytypeNUM();
				tblData[i][1]=users.get(i).getCommoditytypename();
				tblData[i][2]=users.get(i).getCommoditynum();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	public FrmComodityTypeManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		toolBar.add(btnCommodity);
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
		this.btnCommodity.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAdd){
			FrmComodityTypeManager_AddComodity dlg=new FrmComodityTypeManager_AddComodity(this,"添加商品类",true);
			dlg.setVisible(true);
			if(dlg.getcomodity()!=null){//刷新表格
				this.reloadUserTable();
			}
		}
		
		else if(e.getSource()==this.btnDelete){
			int i=this.userTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品类","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定下架商品类吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				int comoditytypeid=Integer.parseInt(this.tblData[i][0].toString());
				try {
					(new ComodityTypeManager()).deleteComodityType(comoditytypeid);
					this.reloadUserTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
		else if(e.getSource()==this.btnCommodity){
			int i=this.userTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品类","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定编辑商品类吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				this.comoditytypeid=Integer.parseInt(this.tblData[i][0].toString());
				try {
					FrmcomodityManager dlg=new FrmcomodityManager(this, "编辑商品", true);
					dlg.setVisible(true);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
}
