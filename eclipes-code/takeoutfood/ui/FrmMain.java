package cn.edu.zucc.takeoutfood.ui;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import cn.edu.zucc.takeoutfood.ui.FrmUserManager;

import cn.edu.zucc.takeoutfood.control.SystemUserManager;
import cn.edu.zucc.takeoutfood.model.BeanSystemUser;

import javax.swing.JTextPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class FrmMain extends JFrame implements ActionListener {
	private JMenuBar menubar=new JMenuBar();
    private JMenu menu_Manager=new JMenu("ϵͳ����");
    private JMenu menu_Order=new JMenu("��������");
    private JMenu menu_Commodity=new JMenu("��Ʒ����");
    private JMenu menu_takeout = new JMenu("���͹���");
    private JMenu menu_search=new JMenu("��ѯ");
    
    private JMenuItem  menuItem_Adminisitor=new JMenuItem("����Ա����");
    private JMenuItem  menuItem_UserManager=new JMenuItem("�û�����");
    private JMenuItem  menuItem_ShopManager=new JMenuItem("�̼ҹ���");
    private JMenuItem  menuItem_RiderManager=new JMenuItem("���ֹ���");
    
    private JMenu menu_more=new JMenu("����");
    
    private JMenuItem  menuItem_buy=new JMenuItem("��Ʒ�ɹ�");
    private JMenuItem  menuItem_Return=new JMenuItem("��Ʒ�˶�");
    private JMenuItem  menuItem_evaluation = new JMenuItem("����");
    
    private JMenuItem  menuItem_CommodityUpManager=new JMenuItem("�����Ʒ");
    private JMenuItem  menuItem_CommodityDownManager=new JMenuItem("�¼���Ʒ");
    private JMenuItem  menuItem_DisCountManager=new JMenuItem("�Żݹ���");
    
    private final JMenuItem menuItem_SendManager = new JMenuItem("�ӵ�����");
	private final JMenuItem menuItem_overdo = new JMenuItem("����ʹ�");
    
    private JMenuItem menuItem_UserOrderSearch=new JMenuItem("�û����������ѯ");
    private JMenuItem menuItem_RiderSendSearch=new JMenuItem("�����͵������ѯ");
    private JMenuItem menuItem_CommodityStatic=new JMenuItem("��Ʒ�����ѯ");
    
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("�����޸�");
    
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	
	public static int type=0;
	
	
	
	public FrmMain(){
		
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("��������ϵͳ");
		dlgLogin=new FrmLogin(this,"��½",true);
		dlgLogin.setVisible(true);
				
	    //�˵�
		//����Ա��
		if(type==1){
			menu_Manager.add(menuItem_Adminisitor);
			menuItem_Adminisitor.addActionListener(this);
	    	menu_Manager.add(menuItem_UserManager);
	    	menuItem_UserManager.addActionListener(this);
	    	menu_Manager.add(menuItem_ShopManager);
	    	menuItem_ShopManager.addActionListener(this);
	    	menu_Manager.add(menuItem_RiderManager);
	    	menuItem_RiderManager.addActionListener(this);
	    	menubar.add(menu_Manager);
	    	
		}
	    //�û�������
		else if(type==2) {
	    	menu_Order.add(this.menuItem_buy);
		    menuItem_buy.addActionListener(this);
		    menu_Order.add(this.menuItem_Return);
		    menuItem_Return.addActionListener(this);
		    menubar.add(menu_Order);
		    menu_Order.add(menuItem_evaluation);
		    menuItem_evaluation.addActionListener(this);
		    
		    }
	    //�̼ҿ�
		else if(type==3) {
		    menu_Commodity.add(menuItem_CommodityUpManager);
		    menuItem_CommodityUpManager.addActionListener(this);
		    menu_Commodity.add(menuItem_CommodityDownManager);
		    menuItem_CommodityDownManager.addActionListener(this);
		    menu_Commodity.add(menuItem_DisCountManager);
	    	menuItem_DisCountManager.addActionListener(this);
		    menubar.add(menu_Commodity);
		    
	    	} 
	    //���Ϳ�
		else if(type==4)  {
		    menubar.add(menu_takeout);
		    menu_takeout.add(menuItem_SendManager);
		    menuItem_SendManager.addActionListener(this);
		    menu_takeout.add(menuItem_overdo);
		    menuItem_overdo.addActionListener(this);
		    
			}  
		
		//��ѯ��    
		menubar.add(menu_search);
	    menu_search.add(this.menuItem_UserOrderSearch);
	    menuItem_UserOrderSearch.addActionListener(this);
	    menu_search.add(this.menuItem_RiderSendSearch);
	    menuItem_RiderSendSearch.addActionListener(this);
	    menu_search.add(this.menuItem_CommodityStatic);
	    menuItem_CommodityStatic.addActionListener(this);
	    menu_search.add(this.menuItem_CommodityStatic);
	    this.setJMenuBar(menubar);
	    
	    //����
	    menubar.add(menu_more);
	    menuItem_modifyPwd.addActionListener(this);
	    menu_more.add(this.menuItem_modifyPwd); 
	    this.setJMenuBar(menubar);
	    
	    //״̬��
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("����!"+SystemUserManager.currentUser.getSystemUserid());
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.menuItem_Adminisitor) {
			FrmAdministratorManager dlg =new FrmAdministratorManager(this, "����Ա����", true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_UserManager) {
			FrmUserManager dlg=new FrmUserManager(this,"�û�����",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ShopManager) {
			FrmShopManager dlg=new FrmShopManager(this, "��ҹ���", true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_RiderManager) {
			FrmRiderManager dlg=new FrmRiderManager(this, "���ֹ���", true);
			dlg.setVisible(true);
		}
		
		
		else if(e.getSource()==this.menuItem_buy) {
			
		}
		else if(e.getSource()==this.menuItem_Return) {
			
		}
		else if(e.getSource()==this.menuItem_evaluation) {
			
		}
		
		else if(e.getSource()==this.menuItem_CommodityUpManager) {
			
		}
		else if(e.getSource()==this.menuItem_CommodityDownManager) {
			
		}
		else if(e.getSource()==this.menuItem_DisCountManager) {
			
		}
		
		else if(e.getSource()==this.menuItem_SendManager) {
			
		}
		else if(e.getSource()==this.menuItem_overdo) {
			
		}
		
		else if(e.getSource()==this.menuItem_UserOrderSearch) {
			
		}
		else if(e.getSource()==this.menuItem_RiderSendSearch) {
			
		}
		else if(e.getSource()==this.menuItem_CommodityStatic) {
			
		}
		else if(e.getSource()==this.menuItem_modifyPwd){
			FrmModifyPwd dlg=new FrmModifyPwd(this,"�����޸�",true);
			dlg.setVisible(true);
		}
	}
}
