package com.bms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import com.bms.bean.Book;

public class BookDAO {
	
	public void insert(Book book) {
		try {
            String sql = new String(" INSERT INTO book(bName,bPress,bAuthor,bPrice,bNum) VALUES(?,?,?,?,?)");
            PreparedStatement ps = DAO.getConnection().prepareStatement(sql);
            ps.setString(1, book.getbName());
            ps.setString(2, book.getbPress());
            ps.setString(3, book.getbAuthor());
            ps.setDouble(4, book.getbPrice());
            ps.setInt(5, book.getbNum());
            int flag = ps.executeUpdate();
            if (flag > 0) {
                JOptionPane.showMessageDialog(null, "添加书籍成功！");
            } else {
                JOptionPane.showMessageDialog(null, "添加书籍失败！");
            }
            // 关闭连接
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "添加失败！\n" + ex.getMessage());
        }
	}
	
	/**
     * 在数据库删除指定图书信息
     */
	public void delete(String bID) {
		try {
			ManageBookDAO manageBookDAO = new ManageBookDAO();
			if(true == manageBookDAO.queryRecord_B(bID)) {
				JOptionPane.showMessageDialog(null, "图书删除失败！\n" + "该图书还是借出状态，不允许删除！\n");
				return ;
			}
            PreparedStatement ps = DAO.getConnection().prepareStatement(" DELETE FROM book  WHERE bID=?");
            ps.setString(1, bID); 
            int flag = ps.executeUpdate(); 
            if (flag > 0) {
                JOptionPane.showMessageDialog(null, "图书删除成功！");
            } else {
                JOptionPane.showMessageDialog(null, "图书删除失败！");
            }
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "图书删除失败！\n" + ex.getMessage());
        }
	}
	
	public void update(Book book) {
		try {
            String sql = "UPDATE book SET bName=?,bPress=?,bAuthor=?,bPrice=?,bNum=? WHERE bID=?";
            PreparedStatement ps = DAO.getConnection().prepareStatement(sql);
            ps.setString(1, book.getbName());
            ps.setString(2, book.getbPress());
            ps.setString(3, book.getbAuthor());
            ps.setDouble(4, book.getbPrice());
            ps.setInt(5, book.getbNum());
            ps.setString(6, book.getbID());
            int flag = ps.executeUpdate();
            if (flag > 0) {
                JOptionPane.showMessageDialog(null, "图书信息修改成功！");
            } else {
                JOptionPane.showMessageDialog(null, "图书信息修改失败！");
            }
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "图书信息修改失败！\n" + ex.getMessage());
            ex.printStackTrace();//打印异常堆栈信息
        }
	}
	
	public List<Book> query(Book book) {
		List<Book> list = new ArrayList<Book>(); 
		try {
			String sql = "SELECT bID,bName,bAuthor,bPress,bPrice,bNum FROM book WHERE 1=1";
			PreparedStatement ps = DAO.getConnection().prepareStatement(sql);
			/*设置参数*/
			if(null != book.getbID() && !"".equals(book.getbID()))sql += " and bID = '" + book.getbID() + "'";
			if(null != book.getbName() && !"".equals(book.getbName()))sql += " and bName = '" + book.getbName() + "'";
			if(null != book.getbPress() && !"".equals(book.getbPress()))sql += " and bPress = '" + book.getbPress() + "'";
			if(null != book.getbAuthor() && !"".equals(book.getbAuthor()))sql += " and bAuthor = '" + book.getbAuthor() + "'";
				
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book oldBook = new Book();
				oldBook.setbID(rs.getString("bID"));
				oldBook.setbName(rs.getString("bName"));
				oldBook.setbAuthor(rs.getString("bAuthor"));
				oldBook.setbPress(rs.getString("bPress"));
				oldBook.setbPrice(rs.getDouble("bPrice"));
				oldBook.setbNum(rs.getInt("bNum"));
				list.add(oldBook);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "查询失败！\n" + ex.getMessage());
		}
		return list;
	}
}
