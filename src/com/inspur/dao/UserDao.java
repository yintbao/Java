package com.inspur.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.inspur.model.User;
import com.inspur.util.DbCon;
import com.inspur.util.PageBean;

public class UserDao {
	
	

	/**
	 * 异步校验用户名是否存在
	 * @param username
	 * @return
	 */
	public User findUserByname(String username) {
		DbCon dbcon = new DbCon();
		Connection con = null;
		User user = null;
		String sql = "select * from t_user where username=?";
		try {
			con = DbCon.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setState(rs.getInt("state"));
				user.setActivecode(rs.getString("activecode"));
				user.setId(rs.getInt("id"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	} 
				finally {
			try {
				dbcon.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}
	
	/**
	 * 异步校验email是否存在
	 * @param username
	 * @return
	 */
	public User findUserByemail(String email) {
		DbCon dbcon = new DbCon();
		Connection con = null;
		User user = null;
		String sql = "select * from t_user where email=?";
		try {
			con = DbCon.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setState(rs.getInt("state"));
				user.setActivecode(rs.getString("activecode"));
				user.setState(rs.getInt("id"));
			}
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
		return user;
	}
	/**
	 * 注册
	 * @param username
	 * @return
	 */
	public int addUser(User  user) {
		DbCon dbcon = new DbCon();
		Connection con = null;
		int a=0;
		String sql = "insert into t_user values(t_user_seq.nextval,?,?,?,?,?,null,null)";
		try {
			con = DbCon.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setInt(4, user.getState());
			pstmt.setString(5, user.getActivecode());
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
	/**
	 * 通过激活码查找用户
	 * @param username
	 * @return
	 */
	public User findUserByactiveCode(String activecode) {
		DbCon dbcon = new DbCon();
		Connection con = null;
		User user = null;
		String sql = "select * from t_user where activecode=?";
		try {
			con = DbCon.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, activecode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setState(rs.getInt("state"));
				user.setActivecode(rs.getString("activecode"));
				user.setId(rs.getInt("id"));
			}
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
		return user;
	}
	/**
	 * 激活用户
	 * @param username
	 * @return
	 */
	public int activeUser(String code) {
		DbCon dbcon = new DbCon();
		Connection con = null;
		int a=0;
		String sql = "update t_user set state=1 where activecode=?";
		try {
			con = DbCon.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,code );
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
	/**
	 * 更改用户登陆ip和登入时间信息
	 */
	
	public int updateUserLogin(User  user) {
		DbCon dbcon = new DbCon();
		Connection con = null;
		int a=0;
		String sql = "update t_user set lastlogintime=?,ip=? where id=?";
		try {
			con = DbCon.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getLastlogintime());
			pstmt.setString(2, user.getIp());
			pstmt.setInt(3, user.getId());
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
	
	
	 /**
	  * 修改用户的密码
	  * @param page
	  * @return
	  */
	
	public int updateUserPassWord(User  user) {
		DbCon dbcon = new DbCon();
		Connection con = null;
		int a=0;
		String sql = "update t_user set password=? where id=?";
		try {
			con = DbCon.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setInt(2, user.getId());
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
	
	// 后台：显示会员
	public  ResultSet showUser(PageBean page){
		Connection con = null;
		ResultSet rs=null;
		String sql = "select * from ( select rownum rn ,a.* from t_user a ) where   rn >? and rn <=? order by id   ";
		try {
			con = DbCon.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page.getStart());
			pstmt.setInt(2, page.getRows());
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	//后台：显示会员,必须有会员的总数
	public int userCount() {
		DbCon dbcon = new DbCon();
		Connection con = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "SELECT COUNT(*) AS total FROM  t_user   ";
		try {
			con = DbCon.getCon();
			PreparedStatement tmt = con.prepareStatement(sql);
			rs = tmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("total");
			}
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
		return count;
	}
	
	// 后台：删除会员
    public int deleteUser(int id){
  	  DbCon dbcon=new DbCon();
  	  int result=0;
  	  Connection con=null;
  	  try {
			con=DbCon.getCon();
			String sql="delete  from t_user where id=? ";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, id);
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				dbcon.closeCon(con);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
  	  return result;
    }
}
