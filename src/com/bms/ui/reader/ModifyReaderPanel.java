package com.bms.ui.reader;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.bms.bean.Reader;
import com.bms.dao.ReaderDAO;
import com.bms.tools.InputText;

public class ModifyReaderPanel extends JDialog{
	
	private static final long serialVersionUID = 1L;
	public ModifyReaderPanel() {}
	
	private Reader reader;//读者信息
	
	private JLabel modifyRNameLabel = new JLabel("姓名:");//姓名标签
	private JTextField modifyRNameTextField = new JTextField(reader.getrName(),15);//姓名文本输入框
	private JLabel modifySexLabel = new JLabel("性别：");//性别选择标签
	private boolean sexFlag = true;//记录性别输入的变量；true为女，false为男
	private JRadioButton maleJRBttn = new JRadioButton("男",false);//男性单选按钮
	private JRadioButton femaleJRBttn = new JRadioButton("女",true);//女性单选按钮
	private JLabel modifyRAgeLabel = new JLabel("年龄:");//年龄标签
	private JTextField modifyRAgeTextField = new JTextField(reader.getrAge()+"",15);//年龄文本输入框
	private JLabel modifyROccupationLabel = new JLabel("职业:");//职业标签
	private JTextField modifyROccupationTextField = new JTextField(reader.getrOccupation(),15);//职业文本输入框
	
	/**
	 * 初始化修改读者界面
	 */
	public ModifyReaderPanel(Reader reader) {
		this.reader = reader;
		// 设置布局为GridLayout
		setLayout(new GridLayout(5, 1));
		// 姓名输入面板
		JPanel namePanel = new JPanel();
		namePanel.add(modifyRNameLabel);
		namePanel.add(modifyRNameTextField);
		this.add(namePanel);
		// 性别输入面板：单选
		getBttnGroup();
		JPanel sexPanel = new JPanel();
		sexPanel.add(modifySexLabel);
		sexPanel.add(maleJRBttn);
		sexPanel.add(femaleJRBttn);
		if(reader.getrSex().equals("男")) 
			maleJRBttn.setSelected(true);
		 else 
		    femaleJRBttn.setSelected(true);
		
		this.add(sexPanel);
		// 年龄输入面板，没有做输入检查
		JPanel agePanel = new JPanel();
		agePanel.add(modifyRAgeLabel);
		agePanel.add(modifyRAgeTextField);
		this.add(agePanel);
		// 职业输入面板
		JPanel occupationPanel = new JPanel();
		occupationPanel.add(modifyROccupationLabel);
		occupationPanel.add(modifyROccupationTextField);
		this.add(occupationPanel);
		// 提交按钮
		JPanel bttnPanel = new JPanel();
		bttnPanel.add(getModifyBttn());
		bttnPanel.setLayout(new FlowLayout());
		this.add(bttnPanel);
	}
	
	
	
	
	/**
	 * 初始化bttnGroup(修改界面的性别选择按钮组)；
	 * 将男性和女性选择按钮加到按钮组，保证只能选择其中一个
	 */
	private ButtonGroup getBttnGroup() {
		ButtonGroup bttnGroup = new ButtonGroup();
		bttnGroup.add(maleJRBttn);
		bttnGroup.add(femaleJRBttn);
		return bttnGroup;
	}
	
	
	
	/**
	 * 初始化modifyBttn(修改界面的添加按钮)
	 */
	private JButton getModifyBttn() {
			JButton modifyBttn = new JButton("修改读者");
			modifyBttn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 事件响应：获取要插入的读者的信息
					Reader newreader = new Reader();
					
					newreader.setrID(new String(reader.getrID()));
					
					if(modifyRNameTextField.getText().trim().equals("") == true) {
						JOptionPane.showMessageDialog(null, "还没有输入读者姓名!");
						return ;
					}
					newreader.setrName(new String(modifyRNameTextField.getText()));
					if(sexFlag == true) {
						newreader.setrSex(new String("女"));
					} else {
						newreader.setrSex(new String("男"));
					}
					
					if(modifyRAgeTextField.getText().trim().equals("") == true) {
						JOptionPane.showMessageDialog(null, "还没有输入读者年龄!");
						return ;
					}
					if(InputText.isRealNumber(modifyRAgeTextField.getText()) == false) {
						JOptionPane.showMessageDialog(null, "输入的年龄不合法!");
						return ;
					}
					newreader.setrAge(Integer.parseInt(modifyRAgeTextField.getText()));
					
					if(modifyROccupationTextField.getText().trim().equals("") == true) {
						JOptionPane.showMessageDialog(null, "还没有输入读者职业!");
						return ;
					}
					newreader.setrOccupation(new String(modifyROccupationTextField.getText()));
					if(newreader.getrName().equals(reader.getrName())
							&& newreader.getrSex().equals(reader.getrSex())
							&& newreader.getrOccupation().equals(reader.getrOccupation())
							&& newreader.getrAge() == reader.getrAge()) {
						JOptionPane.showMessageDialog(null, "你没有做出任何修改！");
						return ;
					}
					// 回调ReaderDAO中的insert函数
					ReaderDAO readerDAO = new ReaderDAO();
					readerDAO.update(newreader);
					dispose();
				}
			});
		return modifyBttn;
	}

}
