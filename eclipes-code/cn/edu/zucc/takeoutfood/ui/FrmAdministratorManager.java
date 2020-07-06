package cn.edu.zucc.takeoutfood.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
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


import cn.edu.zucc.takeoutfood.control.AdministratorManager;
import cn.edu.zucc.takeoutfood.model.BeanAdministrator;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class FrmAdministratorManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加管理员");
	private Button btnResetPwd = new Button("重置密码");
	private Button btnDelete = new Button("注销管理员");
	private Object tblTitle[]={"账号","姓名"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable adminTable=new JTable(tablmod);
	private void reloadUserTable(){
		try {
			List<BeanAdministrator> admin=(new AdministratorManager()).loadAllUsers(false);
			tblData =new Object[admin.size()][2];
			for(int i=0;i<admin.size();i++){
				tblData[i][0]=admin.get(i).getAdminid();
				tblData[i][1]=admin.get(i).getAdminname();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.adminTable.validate();
			this.adminTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	public FrmAdministratorManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnResetPwd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadUserTable();
		this.getContentPane().add(new JScrollPane(this.adminTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnResetPwd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			FrmAdminManager_AddUser dlg=new FrmAdminManager_AddUser(this,"添加账号",true);
			dlg.setVisible(true);
			if(dlg.getUser()!=null){//刷新表格
				this.reloadUserTable();
			}
		}
		else if(e.getSource()==this.btnResetPwd){
			int i=this.adminTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择账号","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定重置密码吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String userid=this.tblData[i][0].toString();
				try {
					(new AdministratorManager()).resetAdminPwd(userid);
					JOptionPane.showMessageDialog(null,  "密码重置完成","提示",JOptionPane.INFORMATION_MESSAGE);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.adminTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择账号","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除账号吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String userid=this.tblData[i][0].toString();
				try {
					(new AdministratorManager()).deleteAdmin(userid);
					this.reloadUserTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
}
