package com.bms.ui.book;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bms.bean.Book;
import com.bms.dao.BookDAO;
import com.bms.tools.InputText;

public class AddBookPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JTextField addBNameTextField = new JTextField(15);//图书名
	private JTextField addBPressTextField = new JTextField(15);//图书出版社
	private JTextField addBAuthorTextField = new JTextField(15);//图书作者
	private JTextField addBPriceTextField = new JTextField(15);//图书价格
	private JTextField numTextField = new JTextField("1",3);//图书库存量

	public AddBookPanel() {
		setLayout(new GridLayout(6, 1));// 设置布局为GridLayout
		// 书名输入面板
		JPanel namePanel = new JPanel();
		JLabel addBNameLabel = new JLabel("书名:");
		namePanel.add(addBNameLabel);
		namePanel.add(addBNameTextField);
		this.add(namePanel);
		// 出版社输入面板
		JPanel pressPanel = new JPanel();
		JLabel addBPressLabel = new JLabel("出版社:");
		pressPanel.add(addBPressLabel);
		pressPanel.add(addBPressTextField);
		this.add(pressPanel);
		// 作者输入面板
		JPanel authorPanel = new JPanel();
		JLabel addBAuthorLabel = new JLabel("作者:");
		authorPanel.add(addBAuthorLabel);
		authorPanel.add(addBAuthorTextField);
		this.add(authorPanel);
		// 价格输入面板
		JPanel pricePanel = new JPanel();
		JLabel addBPriceLabel = new JLabel("价格:");
		pricePanel.add(addBPriceLabel);
		pricePanel.add(addBPriceTextField);
		this.add(pricePanel);
		// 库存量输入面板
		JPanel numPanel = new JPanel();
		JLabel addBNumLabel = new JLabel("库存量：");
		numPanel.setLayout(new FlowLayout());
		numPanel.add(addBNumLabel);
		numPanel.add(getMinusBttn());
		numPanel.add(numTextField);
		numPanel.add(getPlusBttn());
		this.add(numPanel);
		// 提交按钮
		JPanel bttnPanel = new JPanel();
		bttnPanel.add(getAddBttn());
		bttnPanel.setLayout(new FlowLayout());
		this.add(bttnPanel);
	}
	
	
	/**
	 * 添加界面的库存量减少1按钮
	 */
	private JButton getMinusBttn() {
		JButton minusBttn = new JButton("-");
		minusBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//判断是否为合法的正整数
				if(true == InputText.isPositiveInteger(numTextField.getText())) {
					int temp = Integer.parseInt(numTextField.getText());
					if(temp >= 2) {
						temp--;
						numTextField.setText(String.valueOf(temp));
					} else {
						JOptionPane.showMessageDialog(null, "库存量不能减小了");
					}
				} else {
					numTextField.setText("1");
				}
			}
		});
		return minusBttn;
	}
	
	/**
	 * 添加界面的库存量增加1按钮
	 */
	private JButton getPlusBttn() {
		JButton plusBttn = new JButton();
		plusBttn.setText("+");
		plusBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(true == InputText.isPositiveInteger(numTextField.getText())) {
					int temp = Integer.parseInt(numTextField.getText());
					temp++;
					numTextField.setText(String.valueOf(temp));
				} else {
					numTextField.setText("1");// 不是正整数则设置为1
				}
				
			}
		});
		return plusBttn;
	}
	
	private JButton getAddBttn() {
		JButton addBttn = new JButton();
		addBttn.setText("添加书籍");
		addBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// 获得输入的图书对象
				Book book = new Book();
				if(addBNameTextField.getText().trim().equals("") == true) {
					JOptionPane.showMessageDialog(null, "还没有输入书名!");
					return ;
				}
				if(addBPressTextField.getText().trim().equals("") == true) {
					JOptionPane.showMessageDialog(null, "还没有输入出版社!");
					return ;
				}
				if(addBAuthorTextField.getText().trim().equals("") == true) {
					JOptionPane.showMessageDialog(null, "还没有输入作者!");
					return ;
				}
				if(addBPriceTextField.getText().trim().equals("") == true) {
					JOptionPane.showMessageDialog(null, "还没有输入价格!");
					return ;
				}
				if(InputText.isRealNumber(addBPriceTextField.getText().trim()) == false) {
					JOptionPane.showMessageDialog(null, "价格输入不合法");
					return ;
				}
				if(numTextField.getText().equals("") == true) {
					JOptionPane.showMessageDialog(null, "还没有输入库存量!");
					return ;
				}
				if(InputText.isPositiveInteger(numTextField.getText()) == false) {
					JOptionPane.showMessageDialog(null, "库存量输入不合法");
					return ;
				}
				
				book.setbName(addBNameTextField.getText());
				book.setbPress(addBPressTextField.getText());
				book.setbAuthor(addBAuthorTextField.getText());
				book.setbPrice(Double.parseDouble(addBPriceTextField.getText()));
				book.setbNum(Integer.parseInt(numTextField.getText()));
				// 插入相应的图书
				BookDAO bookDAO = new BookDAO();
				bookDAO.insert(book);
			}
		});
		return addBttn;
	}
	
	

}
