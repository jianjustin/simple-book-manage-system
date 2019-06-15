package com.bms.ui.book;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bms.bean.Book;
import com.bms.dao.BookDAO;
import com.bms.tools.InputText;

public class ModifyBookPanel extends JDialog{
	private static final long serialVersionUID = 1L;
	
	public ModifyBookPanel() {}
	
	private Book book;//存储要修改的读者的信息

	private JLabel modifyBNameLabel = new JLabel("书名:");//书名标签
	private JTextField modifyBNameTextField = new JTextField(book.getbName(),15);//书名文本输入框
	private JLabel modifyBPressLabel = new JLabel("出版社:");//出版社标签
	private JTextField modifyBPressTextField = new JTextField(book.getbPress(),15);//出版社文本输入框
	private JLabel modifyBAuthorLabel = new JLabel("作者:");//作者标签
	private JTextField modifyBAuthorTextField = new JTextField(book.getbAuthor(),15);//作者文本输入框
	private JLabel modifyBPriceLabel = new JLabel("价格:");//价格标签
	private JTextField modifyBPriceTextField = new JTextField(book.getbPrice()+"",15);//价格文本输入框
	private JLabel modifyBNumLabel = new JLabel("库存量：");//库存量标签
	private JTextField numTextField = new JTextField(book.getbNum()+"",3);//库存量文本输入框
	
	
	public ModifyBookPanel(Book book) {
		this.book = book;
		// 设置布局为GridLayout
		setLayout(new GridLayout(6, 1));
		// 书名输入面板
		JPanel namePanel = new JPanel();
		namePanel.add(modifyBNameLabel);
		namePanel.add(modifyBNameTextField);
		this.add(namePanel);
		// 出版社输入面板
		JPanel pressPanel = new JPanel();
		pressPanel.add(modifyBPressLabel);
		pressPanel.add(modifyBPressTextField);
		this.add(pressPanel);
		// 作者输入面板
		JPanel authorPanel = new JPanel();
		authorPanel.add(modifyBAuthorLabel);
		authorPanel.add(modifyBAuthorTextField);
		this.add(authorPanel);
		// 价格输入面板
		JPanel pricePanel = new JPanel();
		pricePanel.add(modifyBPriceLabel);
		pricePanel.add(modifyBPriceTextField);
		this.add(pricePanel);
		// 库存量输入面板
		JPanel numPanel = new JPanel();
		numPanel.setLayout(new FlowLayout());
		numPanel.add(modifyBNumLabel);
		numPanel.add(getMinusBttn());
		numPanel.add(numTextField);
		numPanel.add(getPlusBttn());
		this.add(numPanel);
		// 提交按钮
		JPanel bttnPanel = new JPanel();
		bttnPanel.add(getModifyBttn());
		bttnPanel.setLayout(new FlowLayout());
		this.add(bttnPanel);
	}
	
	
	/**
	 * 初始化minusBttn(修改界面的库存量减少1按钮)
	 */
	private JButton getMinusBttn() {
		JButton minusBttn = new JButton();
		minusBttn.setText("-");
		minusBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// 判断库存量输入框是否被修改了
				if(InputText.isPositiveInteger(numTextField.getText()) == true) {
					// 是正整数
					int temp = Integer.parseInt(numTextField.getText());
					if(temp >= 2) {
						temp--;
						numTextField.setText(String.valueOf(temp));
					} else {
						JOptionPane.showMessageDialog(null, "库存量不能减小了");
					}
				} else {
					// 是其他非法字符
					numTextField.setText("1");
				}
			}
		});
		return minusBttn;
	}
	
	
	
	
	/**
	 * 初始化plusBttn(修改界面的库存量增加1按钮)
	 */
	private JButton getPlusBttn() {
		JButton plusBttn = new JButton();
		plusBttn.setText("+");
		plusBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// 判断库存量输入框是否被修改了
				if(InputText.isPositiveInteger(numTextField.getText()) == true) {
					// 是正整数
					int temp = Integer.parseInt(numTextField.getText());
					temp++;
					numTextField.setText(String.valueOf(temp));
				} else {
					// 是其他非法字符
					numTextField.setText("1");
				}
				
			}
		});
		return plusBttn;
	}
	
	
	/**
	 * 初始化modifyBttn(修改界面的修改按钮)
	 *
	 * @return JButton modifyBttn(修改界面的修改按钮)
	 */
	private JButton getModifyBttn() {
			JButton modifyBttn = new JButton();
			modifyBttn.setText("修改书籍");
			modifyBttn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Book newbook = new Book();
					
					newbook.setbID(new String(book.getbID()));
					
					if(modifyBNameTextField.getText().trim().equals("") == true) {
						JOptionPane.showMessageDialog(null, "不能修改为空的书名!");
						return ;
					}
					newbook.setbName(new String(modifyBNameTextField.getText()));
					
					if(modifyBPressTextField.getText().trim().equals("") == true) {
						JOptionPane.showMessageDialog(null, "不能修改为空的出版社!");
						return ;
					}
					newbook.setbPress(new String(modifyBPressTextField.getText()));
					
					if(modifyBAuthorTextField.getText().trim().equals("") == true) {
						JOptionPane.showMessageDialog(null, "不能修改为空的作者!");
						return ;
					}
					newbook.setbAuthor(new String(modifyBAuthorTextField.getText()));
					
					if(modifyBPriceTextField.getText().trim().equals("") == true) {
						JOptionPane.showMessageDialog(null, "还没有输入价格!");
						return ;
					}
					if(InputText.isRealNumber(modifyBPriceTextField.getText()) == false) {
						JOptionPane.showMessageDialog(null, "价格输入不合法");
						return ;
					}
					newbook.setbPrice(Double.parseDouble(modifyBPriceTextField.getText()));
					
					if(numTextField.getText().trim().equals("") == true) {
						JOptionPane.showMessageDialog(null, "还没有输入库存量!");
						return ;
					}
					if(InputText.isPositiveInteger(numTextField.getText()) == false) {
						JOptionPane.showMessageDialog(null, "库存量输入不合法");
						return ;
					}
					newbook.setbNum(Integer.parseInt(numTextField.getText()));
					if(newbook.getbAuthor().equals(book.getbAuthor())
							&& newbook.getbName().equals(book.getbName())
							&& newbook.getbPress().equals(book.getbPress())
							&& newbook.getbNum() == book.getbNum()
							&& Math.abs(newbook.getbPrice()-book.getbPrice())<0.001) {
						JOptionPane.showMessageDialog(null, "你没有做出任何修改！");
						// dispose();
						return ;
					}
					// 修改图书
					BookDAO bookDAO = new BookDAO();
					bookDAO.update(newbook);
					dispose();
				}
			});
		return modifyBttn;
	}

}
