package com.bms.ui.borrow;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

import com.bms.bean.Reader;
import com.bms.dao.ManageBookDAO;
import com.bms.dao.ReaderDAO;
import com.bms.tools.CountFines;

public class ReturnBookPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JLabel readerNameLabel = new JLabel("读者姓名：");//姓名查询标签
	private JTextField readerNameTextField = new JTextField(10);//姓名查询输入文本框
	private JTable readerTb;//读者选择表格
	private JScrollPane jReaderScrollPane;//读者查询结果表格显示界面
	private JScrollPane jRecordScrollPane;//读者借书记录查询结果表格显示界面
	private JLabel rDateLabel = new JLabel("还书时间(yyyy-MM-dd)");//还书时间标签
	private JTextField rDateTextField = new JTextField(15);//还书时间文本输入框
	
	public ReturnBookPanel() {
		// 查询读者面板
		JPanel readerQueryPanel = new JPanel();
		readerQueryPanel.add(readerNameLabel);
		readerQueryPanel.add(readerNameTextField);
		readerQueryPanel.add(getReaderQueryBttn());
		readerQueryPanel.add(getRecordBttn());
		readerQueryPanel.setSize(new Dimension(550, 20));
		this.add(readerQueryPanel);
		// 读者显示面板
		this.add(getJReaderScrollPane());
		// 借书记录显示面板
		this.add(getJRecordScrollPane());
		// 操作面板
		JPanel operatePanel = new JPanel();
		operatePanel.setLayout(new FlowLayout());
		operatePanel.add(rDateLabel);
		operatePanel.add(rDateTextField);
		operatePanel.add(getReturnBttn());
		this.add(operatePanel);
	}

	/**
	 * 初始化readerQueryBttn(读者查询按钮)
	 */
	private JButton getReaderQueryBttn() {
		JButton readerQueryBttn = new JButton();
		readerQueryBttn.setText("查询读者");
		readerQueryBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ReaderDAO readerDAO = new ReaderDAO();
				Reader reader = new Reader();
				reader.setrName(readerNameTextField.getText());
				List<Reader> readerList = readerDAO.query(reader);
				DefaultTableModel model = new DefaultTableModel(getTableData(readerList), getReaderTitle());
				readerTb.setModel(model);
			}
		});
		return readerQueryBttn;
	}
	
	
	/**
	 * 初始化recordBttn(读者借书记录查询按钮)
	 */
	private JButton getRecordBttn() {
		JButton recordBttn = new JButton("查询借书记录");
		recordBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int row = readerTb.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "还没有选中要查询借书记录的读者!\n");
					return ; 
				}
				ManageBookDAO manageBookDAO = new ManageBookDAO();
				tableRecordData = manageBookDAO.queryRecord(((String)readerTb.getValueAt(row, 0)).trim());
				DefaultTableModel model1 = new DefaultTableModel(tableRecordData, getRecordTitle());
				recordTb.setModel(model1);
			}
		});
		return recordBttn;
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
	 * 初始化title(书籍选择表格的表头)
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
	private Object[][] getTableData(List<Reader> readerList){
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
	
	/**
	 * 初始化readerTb(读者选择表格)
	 */
	private JTable getReaderTb() {
		if(null == readerTb) {	
			ReaderDAO readerDAO = new ReaderDAO();
			List<Reader> readerList = readerDAO.query(new Reader());
	        
			DefaultTableModel model = new DefaultTableModel(getTableData(readerList), getReaderTitle());
	        readerTb = new JTable(model);
	        readerTb.setPreferredScrollableViewportSize(new Dimension(550, 80));// 设置表格的初始大小
	        readerTb.setModel(model);// 设置表格数据
	        readerTb.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 设置只能选中单行
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置内容居中
			tcr.setHorizontalAlignment(JLabel.CENTER);
		}
		return readerTb;
	}
	
	
	/**
	 * 初始化jRecordScrollPane(读者借书记录查询结果表格显示界面)
	 */
	private JScrollPane getJRecordScrollPane() {
		if (jRecordScrollPane == null) {
			jRecordScrollPane = new JScrollPane();
			jRecordScrollPane.setViewportView(getRecordTb());
			jRecordScrollPane.setSize(new Dimension(550, 135));
        }
		return jRecordScrollPane;
	}
	
	/**
	 * 初始化recordTitle(读者借书记录选择表格的表头)
	 */
	private Vector<String> getRecordTitle() {
		Vector<String> recordTitle = new Vector<String>();
		recordTitle.add("借书人ID");
		recordTitle.add("借书人");
		recordTitle.add("书籍ID");
		recordTitle.add("书名");
		recordTitle.add("借书时间");
		return recordTitle;
	}
	

	private Vector tableRecordData = null;//读者借书记录选择表格的数据
	private JTable recordTb;//读者借书记录选择表格
	
	/**
	 * 初始化recordTb(读者借书记录选择表格)
	 */
	private JTable getRecordTb() {
		if(recordTb == null) {	        
	        // 最开始不显示数据
			DefaultTableModel model = new DefaultTableModel(tableRecordData, getRecordTitle()){
				private static final long serialVersionUID = 1L;
				public boolean isCellEditable(int row,int column){
	        		return false; 
	        	}
	        };
	        recordTb = new JTable(model);
	        // 设置表格的初始大小
	        recordTb.setPreferredScrollableViewportSize(new Dimension(550, 135));
			// 设置表格数据
	        recordTb.setModel(model);
			// 设置只能选中单行
	        recordTb.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			// 设置内容居中
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(JLabel.CENTER);
			recordTb.addMouseListener(new java.awt.event.MouseAdapter(){
	        	public void mouseClicked(java.awt.event.MouseEvent e) {
	        		
	        	}
			});
		}
		return recordTb;
	}
	
	
	
	/**
	 * 初始化returnBttn(还书按钮)
	 */
	private JButton getReturnBttn() {
		JButton returnBttn = new JButton("还书");
		returnBttn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int recordRow = recordTb.getSelectedRow();
				if(recordRow == -1) {
					JOptionPane.showMessageDialog(null, "还没有选中要还书的记录");
					return ;
				}
				int n = JOptionPane.showConfirmDialog(null, 
						"确认还书吗？\n"
						+ "读者姓名："+((String)recordTb.getValueAt(recordRow, 1)) +"\n"
						+ "书名："+((String) recordTb.getValueAt(recordRow, 3)) +"\n", 
						"确认还书框", 
						JOptionPane.YES_NO_OPTION);  
				if (n == JOptionPane.YES_OPTION) {
					if(rDateTextField.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "还没有输入还书时间！");
						return ;
					}
					String rID = ((String) recordTb.getValueAt(recordRow, 0)).trim();
					String bID = ((String) recordTb.getValueAt(recordRow, 2)).trim();
					Date bDate = ((Date)recordTb.getValueAt(recordRow, 4));
					String rDate = new String(rDateTextField.getText().trim());
					try {
						if(CountFines.longOfTwoDate(bDate,CountFines.stringToDate(rDate)) == 0) {
							JOptionPane.showMessageDialog(null, "还书时间早于借书时间！\n请重新输入！\n");
							return ;
						}
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					ManageBookDAO manageBookDAO = new ManageBookDAO();
					manageBookDAO.returnBook(rID, bID, bDate.toString(), rDate);
					// 刷新界面
					int row = readerTb.getSelectedRow();
					tableRecordData = manageBookDAO.queryRecord(((String)readerTb.getValueAt(row, 0)).trim());
					DefaultTableModel model1 = new DefaultTableModel(tableRecordData, getRecordTitle());
					recordTb.setModel(model1);
				} else if (n == JOptionPane.NO_OPTION) {
					return ;
				}
			}
		});
		return returnBttn;
	}

}
