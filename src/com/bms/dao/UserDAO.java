package com.bms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class UserDAO {
	
	/**
	 * 用户登录API（校验账号密码是否正确）
	 */
	public boolean isUser(String user, String pwd) {
        try {
            PreparedStatement ps = DAO.getConnection().prepareStatement("SELECT userPsswd FROM users WHERE userName = ?");
            ps.setString(1, user); 
            ResultSet rs = ps.executeQuery(); 
            if(!rs.next()) {
            	JOptionPane.showMessageDialog(null, "你是非法用户!\n");
                return false;
            }
            String password = rs.getString("userPsswd");
            if(password.equals(pwd)) {
            	 return true;
            }else {
            	JOptionPane.showMessageDialog(null, "密码输入错误!\n");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据库异常！\n" + e.getMessage());
            return false;
        } 
	}
}
