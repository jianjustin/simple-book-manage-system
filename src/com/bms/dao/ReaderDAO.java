package com.bms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.bms.bean.Reader;

/**
 * 读者表操作DAO
 *
 */
public class ReaderDAO {
	
	/**
     *  在数据库插入指定读者信息
     */
	public void insert(Reader reader) {
		try {
           String sql = new String(" INSERT INTO reader(rName,rSex,rAge,rOccupation,rBorrowNum,rFinesAmount) VALUES(?,?,?,?,?,?) ");
           PreparedStatement ps = DAO.getConnection().prepareStatement(sql);
           ps.setString(1, reader.getrName());
           ps.setString(2, reader.getrSex());
           ps.setInt(3, reader.getrAge());
           ps.setString(4, reader.getrOccupation());
           ps.setInt(5, reader.getrBorrowNum());
           ps.setDouble(6, reader.getrFinesAmount());
           int flag = ps.executeUpdate();
           if (flag > 0) 
               JOptionPane.showMessageDialog(null, "添加读者成功！");
            else 
               JOptionPane.showMessageDialog(null, "添加读者失败！");
           ps.close();
       } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, "添加失败！\n" + ex.getMessage());
       }
	}

	/**
     *  在数据库修改指定读者信息
     */
	public void update(Reader reader) {
		try {
            String sql = new String("UPDATE reader SET rName= ? ,rSex= ? ,rAge= ? ,rOccupation= ? WHERE rID= ? ");
            PreparedStatement ps = DAO.getConnection().prepareStatement(sql);
            ps.setString(1, reader.getrName());
            ps.setString(2, reader.getrSex());
            ps.setInt(3, reader.getrAge());
            ps.setString(4, reader.getrOccupation());
            ps.setString(5, reader.getrID());
            int flag = ps.executeUpdate();
            if (flag > 0) {
                JOptionPane.showMessageDialog(null, "读者信息修改成功！");
            } else {
                JOptionPane.showMessageDialog(null, "读者信息修改失败！");
            }
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "读者信息修改失败！\n" + ex.getMessage());
            ex.printStackTrace();
        }
        return ;
	}

	/**
     * 在数据库删除指定读者信息
     */
	public void delete(String rID) {
		try {
			/*TODO 校验是否有未还的书*/
            PreparedStatement ps = DAO.getConnection().prepareStatement(" DELETE FROM reader  WHERE rID=?");
            ps.setString(1, rID);
            int flag = ps.executeUpdate(); 
            if (flag > 0) 
                JOptionPane.showMessageDialog(null, "读者删除成功！");
            else 
                JOptionPane.showMessageDialog(null, "读者删除失败！");
            
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "读者删除失败！\n" + ex.getMessage());
        }
		
	}

	/**
     * 在数据库查询全部读者信息
     */
	public List<Reader> query(Reader reader) {
		List<Reader> list = new ArrayList<Reader>();
		try {
			String sql = "SELECT rID,rName,rSex,rAge,rOccupation,rBorrowNum,rFinesAmount FROM reader WHERE 1=1";
			PreparedStatement ps = DAO.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Reader item = new Reader();
				item.setrID(rs.getString("rID"));
				item.setrName(rs.getString("rName"));
				item.setrOccupation(rs.getString("rOccupation"));
				item.setrSex(rs.getString("rSex"));
				item.setrBorrowNum(rs.getInt("rBorrowNum"));
				item.setrAge(rs.getInt("rAge"));
				list.add(item); 
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "查询失败！\n" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}
}
