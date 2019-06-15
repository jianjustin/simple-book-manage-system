package com.bms.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ManageFrame extends JFrame{
	private static final long serialVersionUID = 6189676558269937890L;
	
	public ManageFrame() {
		this.setSize(550, 430);
		this.setContentPane(new DefaultPanel());
		this.setResizable(false);
		this.setJMenuBar(getMenubar());
	}
	
	public JMenuBar getMenubar() {
		JMenuBar menubar = new JMenuBar();
		
		JMenu bookManageMenu = new JMenu("图书维护");
		bookManageMenu.add(getAddBook());
		bookManageMenu.add(getModifyBook());
		
		JMenu readerManageMenu = new JMenu("读者维护");
		readerManageMenu.add(getAddReader());
		readerManageMenu.add(getModifyReader());
		
		JMenu bookOperateMenu = new JMenu("借还书");
		bookOperateMenu.add(getBorrowBook());
		bookOperateMenu.add(getReturnBook());
		
		JMenu otherMenu = new JMenu("其他");
		otherMenu.add(getAboutItem());
		otherMenu.add(getExitItem());
		
		menubar.add(bookManageMenu);
		menubar.add(readerManageMenu);
		menubar.add(bookOperateMenu);
		menubar.add(otherMenu);
		return menubar;
	}
	
	/**
	 * 
	 * 增加图书菜单项
	 */
	private JMenuItem getAddBook() {
	    JMenuItem addBook = new JMenuItem("增加图书");
		addBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setTitle("增加图书信息界面");
				//setContentPane(new AddBookPanel());
				revalidate();
			}
		});
		return addBook;
	}
	
	/**
	 * 
	 * 编辑图书菜单项
	 */
	private JMenuItem getModifyBook() {
		JMenuItem modifyBook = new JMenuItem("修改图书");
		modifyBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setTitle("修改图书信息界面");
				//setContentPane(new EditBookPanel());
				revalidate();
			}
		});
		return modifyBook;
	}

	/**
	 * 
	 * 增加读者菜单项
	 */
	private JMenuItem getAddReader() {
	    JMenuItem addReader  = new JMenuItem("增加读者");
		addReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setTitle("增加读者信息界面");
				//setContentPane(new AddReaderPanel());
				revalidate();
			}
		});
		return addReader;
	}
	
	/**
	 * 
	 * 编辑读者菜单项
	 */
	private JMenuItem getModifyReader() {
	    JMenuItem modifyReader = new JMenuItem("修改读者");
		modifyReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setTitle("修改读者信息界面");
				//setContentPane(new EditReaderPanel());
				revalidate();
			}
		});
		return modifyReader;
	}
	
	/**
	 * 
	 * 还书菜单项
	 */
	private JMenuItem getReturnBook() {
	    JMenuItem returnBook = new JMenuItem("还书");
		returnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setTitle("还书界面");
				//setContentPane(new ReturnBookPanel());
				revalidate();
			}
		});
		return returnBook;
	}
	
	/**
	 * 
	 * 借书菜单项
	 */
	private JMenuItem getBorrowBook() {
		JMenuItem borrowBook = new JMenuItem("借书");
		borrowBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setTitle("借书界面");
				//setContentPane(new BorrowBookPanel());
				revalidate();
			}
		});
		return borrowBook;
	}
	
	/**
	 * 
	 * 关于菜单项
	 */
	private JMenuItem getAboutItem() {
		JMenuItem aboutItem = new JMenuItem("关于");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String aboutInfo = "关于信息。。。";
				JOptionPane.showMessageDialog(null, aboutInfo);
			}
		});
		return aboutItem;
	}
	
	/**
	 * 
	 * 退出菜单项
	 */
	private JMenuItem getExitItem() {
		JMenuItem exitItem = new JMenuItem("退出系统");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		return exitItem;
	}
	
	
	
}
