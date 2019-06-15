package com.bms.ui.reader;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.bms.bean.Reader;
import com.bms.dao.ReaderDAO;
import com.bms.tools.InputText;

public class AddReaderPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    
    private JLabel addRNameLabel = new JLabel("姓名:");//姓名标签
	private JTextField addRNameTextField = new JTextField(15);//姓名文本输入框
	private JLabel sexLabel = new JLabel("性别：");//性别选择标签
	private boolean sexFlag = true;//记录性别输入的变量；true为女，false为男
	private JRadioButton maleJRBttn = new JRadioButton("男",false);
	private JRadioButton femaleJRBttn = new JRadioButton("女",true);
	private JLabel addRAgeLabel = new JLabel("年龄:");//年龄标签
	private JTextField addRAgeTextField = new JTextField(15);//年龄文本输入框
	private JLabel addROccupationLabel = new JLabel("职业:");//职业标签
	private JTextField addROccupationTextField = new JTextField(15);//职业文本输入框
	
	public AddReaderPanel() {
		// 设置布局为GridLayout
		setLayout(new GridLayout(5, 1));
		// 姓名输入面板
		JPanel namePanel = new JPanel();
		namePanel.add(addRNameLabel);
		namePanel.add(addRNameTextField);
		this.add(namePanel);
		// 性别输入面板：单选
		getBttnGroup();
		JPanel sexPanel = new JPanel();
		sexPanel.add(sexLabel);
		sexPanel.add(maleJRBttn);
		sexPanel.add(femaleJRBttn);
		this.add(sexPanel);
		// 年龄输入面板，没有做输入检查
		JPanel agePanel = new JPanel();
		agePanel.add(addRAgeLabel);
		agePanel.add(addRAgeTextField);
		this.add(agePanel);
		// 职业输入面板
		JPanel occupationPanel = new JPanel();
		occupationPanel.add(addROccupationLabel);
		occupationPanel.add(addROccupationTextField);
		this.add(occupationPanel);
		// 提交按钮
		JPanel bttnPanel = new JPanel();
		bttnPanel.add(getAddBttn());
		bttnPanel.setLayout(new FlowLayout());
		this.add(bttnPanel);
	}
	
	/**
	 * 初始化bttnGroup(添加界面的性别选择按钮组)；
	 * 将男性和女性选择按钮加到按钮组，保证只能选择其中一个
	 */
	public ButtonGroup getBttnGroup() {
		ButtonGroup bttnGroup = new ButtonGroup();
		bttnGroup.add(maleJRBttn);
		bttnGroup.add(femaleJRBttn);
		return bttnGroup;
	}
	
	/**
	 * 初始化addBttn(添加界面的添加按钮)
	 */
	private JButton getAddBttn() {
		JButton addBttn = new JButton();
		addBttn.setText("添加读者");
		addBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// 事件响应：获取要插入的读者的信息
				Reader reader = new Reader();
				
				if(addRNameTextField.getText().trim().equals("") == true) {
					JOptionPane.showMessageDialog(null, "还没有输入读者姓名!");
					return ;
				}
				reader.setrName(new String(addRNameTextField.getText()));
				
				if(sexFlag == true) {
					reader.setrSex(new String("女"));
				} else {
					reader.setrSex(new String("男"));
				}
				
				if(addRAgeTextField.getText().trim().equals("") == true) {
					JOptionPane.showMessageDialog(null, "还没有输入读者年龄!");
					return ;
				}
				if(InputText.isRealNumber(addRAgeTextField.getText()) == false) {
					JOptionPane.showMessageDialog(null, "输入的年龄不合法!");
					return ;
				}
				reader.setrAge(Integer.parseInt(addRAgeTextField.getText()));
				
				if(addROccupationTextField.getText().trim().equals("") == true) {
					JOptionPane.showMessageDialog(null, "还没有输入读者职业!");
					return ;
				}
				reader.setrOccupation(new String(addROccupationTextField.getText()));
				
				// 回调ReaderDAO中的insert函数
				ReaderDAO readerDAO = new ReaderDAO();
				readerDAO.insert(reader);
			}
		});
		return addBttn;
	}

}
