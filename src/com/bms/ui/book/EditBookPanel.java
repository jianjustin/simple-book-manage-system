package com.bms.ui.book;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
import com.bms.dao.BookDAO;

/**
 * 图书操作界面
 */
public class EditBookPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> selectFieldName;
	private JTextField selectValueTextField = new JTextField(10);
	private JScrollPane jScrollPane;
	private JTable tb;
	
	public EditBookPanel() {
		// 查询面板
		JPanel queryPanel = new JPanel();
		JLabel selectFieldNameLabel = new JLabel("查询方式：");
		JLabel selectValueLabel = new JLabel("查询内容：");
		queryPanel.add(selectFieldNameLabel);
		queryPanel.add(getSelectFieldName());
		queryPanel.add(selectValueLabel);
		queryPanel.add(selectValueTextField);
		queryPanel.add(getQueryBttn());
		this.add(queryPanel);
		// 显示面板
		this.add(getJScrollPane());
		// 操作面板
		JPanel operatePanel = new JPanel();
		operatePanel.add(getModifyBttn());
		operatePanel.add(getDeleteBttn());
		this.add(operatePanel);
	}
	
	/**
	 * 初始化selectFieldName(查询方式下拉选择字段名)
	 */
	private JComboBox<String> getSelectFieldName() {
		if(selectFieldName == null) {
			selectFieldName = new JComboBox<String>();
			selectFieldName.addItem("书名");
			selectFieldName.addItem("作者");
			selectFieldName.addItem("出版社");
		}
		return selectFieldName;
	}
	
	/**
	 * 初始化queryBttn(图书查询按钮)
	 */
	private JButton getQueryBttn() {
	    JButton queryBttn = new JButton();
		queryBttn.setText("查询");
		queryBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Book book = new Book();
				String filedName = selectFieldName.getSelectedItem().toString();
				String value = selectValueTextField.getText().trim();
				if("书名".equals(filedName)) 
					book.setbName(value);
				else if("作者".equals(filedName))
					book.setbAuthor(value);
				else if("出版社".equals(filedName))
					book.setbPress(value);
				
				BookDAO bookDAO = new BookDAO();
				List<Book> list = bookDAO.query(book);
				DefaultTableModel model = new DefaultTableModel(getTableData(list), getTitle());
				tb.setModel(model);
			}
		});
		return queryBttn;
	}
	

	/**
	 * 初始化modifyBttn(修改按钮)
	 */
	private JButton getModifyBttn() {
		JButton modifyBttn = new JButton("修改");
		modifyBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int row = tb.getSelectedRow();
				if(row == -1) return ;
				
				Book book = new Book();
				book.setbID(((String)tb.getValueAt(row, 0)).trim());
				book.setbName(((String)tb.getValueAt(row, 1)).trim());
				book.setbPress(((String)tb.getValueAt(row, 2)).trim());
				book.setbAuthor(((String)tb.getValueAt(row, 3)).trim());
				book.setbPrice((double)tb.getValueAt(row, 4));
				book.setbNum((int)tb.getValueAt(row, 5));
				ModifyBookPanel modifyBookPanel = new ModifyBookPanel(book);
				modifyBookPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				Toolkit tookit = modifyBookPanel.getToolkit();
				Dimension dm = tookit.getScreenSize();
				modifyBookPanel.setLocation((dm.width - modifyBookPanel.getWidth())/2, (dm.height-modifyBookPanel.getHeight())/2);
				modifyBookPanel.setSize(new Dimension(250, 230));
				modifyBookPanel.setVisible(true);
			}
		});
		return modifyBttn;
	}
	
	
	/**
	 * 初始化deleteBttn(删除按钮)
	 */
	private JButton getDeleteBttn() {
		JButton deleteBttn = new JButton("删除");
		deleteBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int row = tb.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "你还没有选中图书！");
					return ;
				}
				int n = JOptionPane.showConfirmDialog(null, "确认删除\""+ tb.getValueAt(row, 1).toString().trim() +"\"这本书吗?", "确认删除框", JOptionPane.YES_NO_OPTION);  
				if (n == JOptionPane.YES_OPTION) {  
					BookDAO bookDAO = new BookDAO();
					String bID = tb.getValueAt(row, 0).toString();
					bookDAO.delete(bID);
					
					List<Book> list = bookDAO.query(new Book());
					DefaultTableModel model = new DefaultTableModel(getTableData(list), getTitle());
					tb.setModel(model);
		        } else if (n == JOptionPane.NO_OPTION) {  
		           return ;
		        }
			}
		});
		return deleteBttn;
	}
	
	/**
	 * 初始化jScrollPane(书籍查询结果表格显示界面)
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTb());
			jScrollPane.setSize(new Dimension(550,250));
        }
		return jScrollPane;
    }
	
	/**
	 * 初始化title(书籍选择表格的表头)
	 */
	private Object[] getTitle() {
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
	
	/**
	 * 获取数据表格
	 */
	private JTable getTb() {
		if(null == tb) {
			tb = new JTable();
			
			BookDAO bookDAO = new BookDAO();//数据查询
			List<Book> list = bookDAO.query(new Book());
			
			DefaultTableModel model = new DefaultTableModel(getTableData(list), getTitle());
			tb.setModel(model);
			tb.setPreferredScrollableViewportSize(new Dimension(550,250));// 设置表格的初始大小
			tb.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(JLabel.CENTER);
			tb.setDefaultRenderer(Object.class,tcr);
		}
		return tb;
	}
	
	

}
