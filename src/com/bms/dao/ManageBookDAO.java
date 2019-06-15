package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.bms.bean.Book;
import com.bms.bean.Reader;
import com.bms.tools.CountFines;

public class ManageBookDAO {
	
	/**
	 * 借书操作
	 */
	public void borrowBook(String bID, String rID) {
		try {
			/*判断是否有书可以借*/
			BookDAO bookDAO = new BookDAO();
			Book book = new Book();
			book.setbID(bID);
			book =bookDAO.query(book).get(0);
			if(0 == book.getbNum()) {
				JOptionPane.showMessageDialog(null, "该图书库存为0，不能借书!\n");
				return ;
			}
			
			/*检查借的书是否超出5本*/
			ReaderDAO readerDAO = new ReaderDAO();
			Reader reader = new Reader();
			reader.setrID(rID);
			reader = readerDAO.query(reader).get(0);
			if(5 == reader.getrBorrowNum()) {
				JOptionPane.showMessageDialog(null, "您已借了5本未还，不能借书!\n");
				return ;
			}
			/*添加借书记录*/
            PreparedStatement ps = DAO.getConnection().prepareStatement("INSERT INTO manageBook(bID,rID) VALUES(?,?)");
            ps.setString(1, bID.trim()); 
            ps.setString(2, rID.trim());
            
            /*减少书本数量*/
            PreparedStatement ps1 = DAO.getConnection().prepareStatement("UPDATE book SET bNum=bNum-1  WHERE bID=?");
            ps1.setString(1, bID.trim());
            
            /*增加读者的借书量*/
            PreparedStatement ps2 = DAO.getConnection().prepareStatement("UPDATE reader SET rBorrowNum=rBorrowNum+1 WHERE rID=?");
            ps2.setString(1, rID.trim());
            
            // 执行SQL语句，存储返回结果  返回到数据库里
            int flag = ps.executeUpdate(); 
            int flag1 = ps1.executeUpdate();
            int flag2 = ps2.executeUpdate();
            if (flag > 0 && flag1 >0 && flag2 > 0) {
                JOptionPane.showMessageDialog(null, "图书借阅成功！\n");
            } else {
                JOptionPane.showMessageDialog(null, "图书借阅失败！\n");
            }
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "图书借阅失败！可能重复借书了！\n" + ex.getMessage());
        }
	}
	
	/**
	 * 还书操作
	 */
	public void returnBook(String rID, String bID, String bDate ,String rDate) {
		try {
            PreparedStatement ps = DAO.getConnection().prepareStatement(" DELETE FROM manageBook WHERE manageBook.bID=? AND manageBook.rID=? ");
            ps.setString(1, bID.trim()); 
            ps.setString(2, rID.trim());
            int flag = ps.executeUpdate(); 
            if(flag > 0) {
            	double fines = CountFines.countFines(bDate, rDate);
            	// 图书库存量+1
                PreparedStatement ps1 = DAO.getConnection().prepareStatement(" UPDATE book SET bNum=bNum+1 WHERE bID=? ");
                ps1.setString(1, bID.trim());
                // 读者借阅数-1
                // 读者的罚款增加
                PreparedStatement ps2 = DAO.getConnection().prepareStatement(" UPDATE reader "
        				+ " SET rBorrowNum=rBorrowNum-1 " 
                		+ ",rFinesAmount=rFinesAmount+"+((new DecimalFormat("######0.00")).format(fines))
                		+ " WHERE rID=? ");
                ps2.setString(1, rID.trim());
                // 执行SQL语句，存储返回结果
                int flag1 = ps1.executeUpdate();
                int flag2 = ps2.executeUpdate();
                if (flag1 > 0 && flag2 > 0) {
                    JOptionPane.showMessageDialog(null, "还书成功！\n");
                } else {
                    JOptionPane.showMessageDialog(null, "出现异常！\n");
                }
            } else {
            	JOptionPane.showMessageDialog(null, "还书失败！\n");
            }
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "还书失败！\n" + ex.getMessage());
        }
	}
	
	/**
	 * 查询借书记录
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector queryRecord(String rID) {
		try {
			Vector record = new Vector();
			ResultSet rs = DAO.getConnection().prepareStatement("SELECT reader.rID,reader.rName,book.bID,book.bName,manageBook.bDate FROM manageBook,book,reader WHERE manageBook.rID=\'" + rID + "\' AND reader.rID=manageBook.rID AND book.bID=manageBook.bID").executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				Vector vector = new Vector();
				for (int col = 1; col <= rs.getMetaData().getColumnCount(); col++) {
	               	switch(col) {
		               	case 1:
		               		vector.add(rs.getString(col));;break;
		               	case 2:
		               		vector.add(rs.getString(col));break;
		                case 3:
		                	vector.add(rs.getString(col));break;
		                case 4:
		                	vector.add(rs.getString(col));break;
		                case 5:
		                	vector.add(rs.getDate("bDate"));break;
	                }
	            }
				record.add(vector);
			}
			return record; 
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "查询失败！\n" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 查询书籍是否被借出
	 */
	public boolean queryRecord_B(String bID) {
		try {
			String sql = "SELECT COUNT(*) FROM manageBook WHERE bID = ?";
			DAO.getConnection().prepareStatement(sql);
			ResultSet rs = DAO.getConnection().prepareStatement("SELECT COUNT(*) FROM manageBook WHERE bID=\'" + bID + "\'").executeQuery();
			int num = -1;
			while (rs.next() && rs.getRow() > 0) {
				num = rs.getInt(1);
			}
			if (num == 0) {
				return false;
			} else {
				return true; 
			} 
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "查询失败！\n" + ex.getMessage());
			ex.printStackTrace();
			return true;
		}
	}
	
	/**
	 * 查询读者是否借过书
	 */
	public boolean queryRecord_R(String rID) {
		try {
			ResultSet rs = DAO.getConnection().prepareStatement("SELECT COUNT(*) FROM manageBook WHERE rID=\'" + rID + "\'").executeQuery();
			int num = -1;
			while (rs.next()) {
				num = rs.getInt(1);
			}
			if (0 == num) 
				return false;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "查询失败！\n" + ex.getMessage());
			ex.printStackTrace();
		}
		return true;
	}

}
