package com.bms.ui.borrow;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.bms.bean.Book;
import com.bms.bean.Reader;
import com.bms.dao.BookDAO;
import com.bms.dao.ManageBookDAO;
import com.bms.dao.ReaderDAO;

public class BorrowBookPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JLabel readerNameLabel = new JLabel("读者姓名：");//读者查询姓名标签
	private JTextField readerNameTextField = new JTextField(10);//姓名文本输入框
	private JScrollPane jReaderScrollPane = null;//读者查询结果表格显示界面
	private JLabel bookNameLabel = new JLabel("书籍名：");//书籍查询书名标签
	private JTextField bookNameTextField = new JTextField(10);//书名文本输入框
	
	public BorrowBookPanel() {
		// 查询读者面板
		JPanel readerQueryPanel = new JPanel();
		readerQueryPanel.add(readerNameLabel);
		readerQueryPanel.add(readerNameTextField);
		readerQueryPanel.add(getReaderQueryBttn());
		readerQueryPanel.setSize(new Dimension(550, 20));
		this.add(readerQueryPanel);
		// 读者显示面板
		this.add(getJReaderScrollPane());
		// 查询书籍面板
		JPanel bookQueryPanel = new JPanel();
		bookQueryPanel.add(bookNameLabel);
		bookQueryPanel.add(bookNameTextField);
		bookQueryPanel.add(getBookQueryBttn());
		bookQueryPanel.setSize(new Dimension(550, 20));
		this.add(bookQueryPanel);
		// 书籍显示面板
		this.add(getJBookScrollPane());
		
		// 操作面板
		JPanel operatePanel = new JPanel();
		operatePanel.setLayout(new FlowLayout());
		operatePanel.add(getBorrowBttn());
		this.add(operatePanel);
	}
	
	
	
	private JButton getReaderQueryBttn() {
		JButton readerQueryBttn = new JButton();
		readerQueryBttn.setText("查询");
		readerQueryBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Reader reader = new Reader();
				ReaderDAO readerDAO = new ReaderDAO();
				reader.setrName(readerNameTextField.getText());
				List<Reader> readerList = readerDAO.query(reader);
				DefaultTableModel model = new DefaultTableModel(getReaderTableData(readerList), getReaderTitle());
				readerTb.setModel(model);
			}
		});
		return readerQueryBttn;
	}
	
	
	/**
	 * 初始化jReaderScrollPane(读者查询结果表格显示界面)
	 */
	private JScrollPane getJReaderScrollPane() {
		if (jReaderScrollPane == null) {
			jReaderScrollPane = new JScrollPane();
			jReaderScrollPane.setViewportView(getReaderTb());
			jReaderScrollPane.setSize(new Dimension(550, 80));
        }
		return jReaderScrollPane;
    }
	
	/**
	 * 初始化title(读者选择表格的表头)
	 */
	private Object[] getReaderTitle() {
		List<String> list = new ArrayList<String>();
		list.add("ID");
		list.add("姓名");
		list.add("性别");
		list.add("年龄");
		list.add("职业");
		list.add("借书量");
		list.add("罚款金额");
	    return list.toArray();
	}
	
	/**
	 * 初始化表格数据
	 */
	private Object[][] getReaderTableData(List<Reader> readerList){
		Object[][] list = new Object[readerList.size()][7];
		for (int i = 0; i < readerList.size(); i++) {
			list[i][0] = readerList.get(i).getrID();
			list[i][1] = readerList.get(i).getrName();
			list[i][2] = readerList.get(i).getrSex();
			list[i][3] = readerList.get(i).getrAge();
			list[i][4] = readerList.get(i).getrOccupation();
			list[i][5] = readerList.get(i).getrBorrowNum();
			list[i][6] = readerList.get(i).getrFinesAmount();
		}
		return list;
	}
	
	private JTable readerTb;//读者选择表格
	
	/**
	 * 初始化readerTb(读者选择表格)
	 */
	private JTable getReaderTb() {
		if(readerTb == null) {
			ReaderDAO readerDAO = new ReaderDAO();
	        // 最开始显示全部的数据， 并且不能修改数据的内容
			List<Reader> readerList = readerDAO.query(new Reader());
			DefaultTableModel model = new DefaultTableModel(getReaderTableData(readerList), getReaderTitle());
	        readerTb = new JTable(model);
	        readerTb.setPreferredScrollableViewportSize(new Dimension(550, 80));// 设置表格的初始大小
	        readerTb.setModel(model);// 设置表格数据
	        readerTb.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 设置只能选中单行
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(JLabel.CENTER);// 设置内容居中
		}
		return readerTb;
	}
	
	
	
	/**
	 * 初始化bookQueryBttn(书籍查询按钮)
	 */
	private JButton getBookQueryBttn() {
		JButton bookQueryBttn = new JButton();
		bookQueryBttn.setText("查询");
		bookQueryBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Book book = new Book();
				BookDAO bookDAO = new BookDAO();
				book.setbName(bookNameTextField.getText());
				List<Book> bookList = bookDAO.query(book);
				DefaultTableModel model = new DefaultTableModel(getTableData(bookList), getBookTitle());
				bookTb.setModel(model);
			}
		});
		return bookQueryBttn;
	}
	
	/**
	 * 书籍查询结果表格显示界面
	 */
	private JScrollPane jBookScrollPane;
	
	/**
	 * 初始化jBookScrollPane(书籍查询结果表格显示界面)
	 */
	private JScrollPane getJBookScrollPane() {
		if (jBookScrollPane == null) {
			jBookScrollPane = new JScrollPane();
			jBookScrollPane.setViewportView(getBookTb());
			jBookScrollPane.setSize(new Dimension(550, 100));
        }
		return jBookScrollPane;
    }
	
	/**
	 * 初始化title(书籍选择表格的表头)
	 */
	private Object[] getBookTitle() {
		List<String> list = new ArrayList<String>();
		list.add("ID");
		list.add("书名");
		list.add("出版社");
		list.add("作者");
		list.add("价格");
		list.add("库存量");
	    return list.toArray();
	}
	
	/**
	 * 初始化表格数据
	 */
	private Object[][] getTableData(List<Book> bookList){
		Object[][] list = new Object[bookList.size()][6];
		for (int i = 0; i < bookList.size(); i++) {
			list[i][0] = bookList.get(i).getbID();
			list[i][1] = bookList.get(i).getbName();
			list[i][2] = bookList.get(i).getbPress();
			list[i][3] = bookList.get(i).getbAuthor();
			list[i][4] = bookList.get(i).getbPrice();
			list[i][5] = bookList.get(i).getbNum();
		}
		return list;
	}
	
	private JTable bookTb;//书籍选择表格
	
	/**
	 * 初始化bookTb(书籍选择表格)
	 */
	private JTable getBookTb() {
		if(bookTb == null) {
			BookDAO bookDAO = new BookDAO();
			List<Book> bookList = bookDAO.query(new Book());
			DefaultTableModel model = new DefaultTableModel(getTableData(bookList), getBookTitle());
	        bookTb = new JTable(model);
	        bookTb.setPreferredScrollableViewportSize(new Dimension(550, 100));
	        bookTb.setModel(model);
	        bookTb.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 设置只能选中单行
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(JLabel.CENTER);// 设置内容居中
			bookTb.setDefaultRenderer(Object.class,tcr);
		}
		return bookTb;
	}
	
	
	
	
	/**
	 * 初始化borrowBttn(借书按钮)
	 */
	private JButton getBorrowBttn() {
		JButton borrowBttn = new JButton();
		borrowBttn.setText("借书");
		borrowBttn.addMouseListener(new java.awt.event.MouseAdapter(){
        	public void mouseClicked(java.awt.event.MouseEvent e) {
        		// 获取读者选中行
				int readerRow = readerTb.getSelectedRow();
				if(readerRow == -1) {
					JOptionPane.showMessageDialog(null, "你还没有选中要借书的读者！");
					return ;
				}
        		// 获取图书选中行
				int bookRow = bookTb.getSelectedRow();
				if(bookRow == -1) {
					JOptionPane.showMessageDialog(null, "你还没有选中要借的书！");
					return ;
				}
				
				// 创建选择窗口
				int n = JOptionPane.showConfirmDialog(null, 
													"确认借书吗？\n"
													+ "读者姓名：\""+((String) readerTb.getValueAt(readerRow, 1)).trim() +"\"\n"
													+ "书名：\""+((String) bookTb.getValueAt(bookRow, 1)).trim() +"\"\n", 
													"确认删除框", 
													JOptionPane.YES_NO_OPTION);  
				if (n == JOptionPane.YES_OPTION) {  
					// 确定执行借书操作
					ManageBookDAO manageBookDAO = new ManageBookDAO();
					BookDAO bookDAO = new BookDAO();
					ReaderDAO readerDAO = new ReaderDAO();
					manageBookDAO.borrowBook(bookTb.getValueAt(bookRow, 0).toString().trim(),readerTb.getValueAt(readerRow, 0).toString().trim());
					// 借书完成后刷新一下表格
					Book book = new Book();
					book.setbName(bookNameTextField.getText());
					List<Book> bookList = bookDAO.query(book);
					DefaultTableModel model = new DefaultTableModel(getTableData(bookList), getBookTitle());
					bookTb.setModel(model);
					
					Reader reader = new Reader();
					reader.setrName(readerNameTextField.getText());
					List<Reader> readerList = readerDAO.query(reader);
					DefaultTableModel model1 = new DefaultTableModel(getReaderTableData(readerList), getReaderTitle());
					readerTb.setModel(model1);
					return ;
		        } else if (n == JOptionPane.NO_OPTION) {  
		        	
		        	return ;
		        }
        	}
		});
		return borrowBttn;
	}

}
