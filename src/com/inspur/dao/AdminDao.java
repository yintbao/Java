package com.inspur.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.inspur.model.Admin;
import com.inspur.util.DbCon;

public class AdminDao {
	DbCon dbcon = new DbCon();

	public Admin getAdmin(Admin admin) {
		Connection con = null;
		Admin CurrentUser = null;
		try {
			con = DbCon.getCon();
			String sql = "select * from t_admin where username=? and password=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, admin.getUsername());
			pstmt.setString(2, admin.getPassword());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CurrentUser = new Admin();
				CurrentUser.setId(rs.getInt("id"));
				CurrentUser.setUsername(rs.getString("username"));
				CurrentUser.setPassword(rs.getString("password"));
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
				try {
					dbcon.closeCon(con);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
		return CurrentUser;
	}
	
	
	public int UpdateAdmin(Admin admin){
		Connection con = null;
		PreparedStatement pstmt=null;
		int a=0;
		try {
			con=DbCon.getCon();
			String sql="update t_admin set loginip=?  where id=?";
			 pstmt =con.prepareStatement(sql);
			pstmt.setString(1,admin.getLoginip() );
			pstmt.setInt(2,admin.getId());
			 a= pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbcon.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return a;
	}
	/**
	 * ���Ĺ���Ա������
	 */
	public int updateAdminPassWord(Admin  admin) {
		DbCon dbcon = new DbCon();
		Connection con = null;
		int a=0;
		String sql = "update t_admin set password=? where id=?";
		try {
			con = DbCon.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, admin.getPassword());
			pstmt.setInt(2, admin.getId());
			a= pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbcon.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return a;
	}
}
