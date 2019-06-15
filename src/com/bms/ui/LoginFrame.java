package com.bms.ui;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/****
 * 
 * 登录面板UI
 */
public class LoginFrame extends JFrame{
	private static final long serialVersionUID = -7717226507368692923L;
	
	/**
	 * 初始化登录面板
	 * 1. 初始化 用户名控件
	 * 2. 初始化 密码控件
	 * 3. 初始化 按钮控件
	 * 4. 初始化 主面板
	 */
	public LoginFrame() {
		super("用户登录");//设置面板标题
		//用户名
		JPanel userPanel = new JPanel();
		JLabel userLabel = new JLabel("用    户：");
		JTextField userTextField = new JTextField(15);
		userPanel.add(userLabel);
		userPanel.add(userTextField);
		
		//密码
		JPanel psswPanel = new JPanel();
		JLabel psswLabel = new JLabel("密    码：");
		JPasswordField psswTextField = new JPasswordField(15);
		psswPanel.add(psswLabel);
		psswPanel.add(psswTextField);
		
		JPanel bttnPanel = new JPanel();
		//登录按钮
		JButton loginBttn = new JButton("登    录");
		loginBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	ManageFrame thisClass = new ManageFrame();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Toolkit tookit = thisClass.getToolkit();
                thisClass.setLocation((tookit.getScreenSize().width - thisClass.getWidth()) / 2,(tookit.getScreenSize().height - thisClass.getHeight()) / 2);
                thisClass.setVisible(true);
                dispose();
//                String user = userTextField.getText();
//                String pwd = new String(psswTextField.getPassword());
//                UserDAO userdao = UserDAO.getInstance();
//                if (userdao.isUser(user, pwd) == true) {
//                    ManageFrame thisClass = new ManageFrame();
//                    thisClass.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//                    Toolkit tookit = thisClass.getToolkit();
//                    thisClass.setLocation(
//                            (tookit.getScreenSize().width - thisClass.getWidth()) / 2,
//                            (tookit.getScreenSize().height - thisClass.getHeight()) / 2);
//                    thisClass.setVisible(true);
//                    dispose();
//                }
            }
        });
		
		//退出按钮
		JButton exitBttn = new JButton("退    出");
		exitBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				System.exit(0);
			}
		});
		bttnPanel.add(loginBttn);
		bttnPanel.add(exitBttn);
		
		JPanel jContentPanel = new JPanel();
		jContentPanel.setLayout(new GridLayout(3, 1));
		jContentPanel.add(userPanel);
		jContentPanel.add(psswPanel);
		jContentPanel.add(bttnPanel);
		
		setSize(282, 167);
		setLocation((this.getToolkit().getScreenSize().width - this.getWidth())/2,(this.getToolkit().getScreenSize().height-this.getHeight())/2);
		setContentPane(jContentPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	
	public static void main(String[] args) {
		LoginFrame frame = new LoginFrame();
		frame.setVisible(true);
		
	}

}
