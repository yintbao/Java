package com.inspur.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.inspur.model.Category;
import com.inspur.util.DbCon;
import com.inspur.util.PageBean;

public class CategoryDao {
	
     // 前台：把一级分类和二级分类放到一个list里面
	public List<Category> listCategory() {
		DbCon dbcon = new DbCon();
		List<Category> list = new ArrayList<Category>();
		Connection con = null;
		String sql = "SELECT * FROM t_category WHERE pid IS  NULL   ORDER BY  cid ";
		try {
			con = DbCon.getCon();
			Statement tmt = con.createStatement();
			ResultSet rs = tmt.executeQuery(sql);
			while (rs.next()) {
				Category categroy = new Category();
				categroy.setCid(rs.getInt("CID"));
				categroy.setCname(rs.getString("CNAME"));
				categroy.setDescs(rs.getString("DESCS"));
				categroy.setImage(rs.getString("IMAGE"));
				list.add(categroy);
				// 子类别
			}
			for (Category parent : list) {
				String sql1 = "SELECT * FROM t_category WHERE pid=?    ORDER BY  pid ";
				PreparedStatement pstmt1 = con.prepareStatement(sql1);
				pstmt1.setInt(1, parent.getCid());
				ResultSet rs1 = pstmt1.executeQuery();
				List<Category> children = new ArrayList<Category>();
				while (rs1.next()) {
					Category category = new Category();
					category.setCid(rs1.getInt("cid"));
					category.setCname(rs1.getString("cname"));
					category.setDescs(rs1.getString("descs"));
					category.setParent(parent);
					children.add(category);
				}
				parent.setChildren(children);
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
		return list;

	}
	
	// 后台：显示一级分类
	public  ResultSet showFirstType(PageBean page){
		Connection con = null;
		ResultSet rs=null;
		if(page==null||page!=null){
		String sql = "select * from (SELECT T_CATEGORY.* ,rownum rn FROM t_category WHERE pid IS  NULL  ORDER BY  cid )where  rn>? and rn<=? ";
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
		}
		return rs;
	}
	
	// 后台：用easyui显示一级分类，必须返回一个总记录数
	public int firstTypeCount(){
		
		DbCon dbcon = new DbCon();
		Connection con = null;
		ResultSet rs=null;
		int count=0;
		String sql = "SELECT COUNT(*) AS TOTAL  FROM T_CATEGORY WHERE  PID IS NULL ";
		try {
			con = DbCon.getCon();
			PreparedStatement tmt = con.prepareStatement(sql);
			rs = tmt.executeQuery();
			 while(rs.next()){
				 count=rs.getInt("TOTAL");
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dbcon.closeCon(con);
			 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return count;
	}
	
	//后台：添加一级分类
      public  int addFistType(Category category){
    	  DbCon dbcon=new DbCon();
    	  Connection con=null;
    	  int result=0;
    	  try {
			con=DbCon.getCon();
			String sql="insert into t_category (cid,cname,image,t_category.descs) values(t_category_seq.nextval,?,?,?)";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, category.getCname());
			pstmt.setString(2, category.getImage());
			pstmt.setString(3, category.getDescs());
			result=pstmt.executeUpdate();
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
			dbcon.closeCon(con);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
      }
      
  	   //后台：编辑一级分类
       public  int  modefFistType(Category category){
    	  DbCon dbcon=new DbCon();
    	  Connection con=null;
    	  int result=0;
    	  try {
			con=DbCon.getCon();
			String sql="update t_category set cname=?,image=?,t_category.desc=? where cid=?";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, category.getCname());
			pstmt.setString(2, category.getImage());
			pstmt.setString(3, category.getDescs());
			pstmt.setInt(4, category.getCid());
			result=pstmt.executeUpdate();
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
			dbcon.closeCon(con);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
      }
       
       
	//后台：删除一级分类
      public int deleteFirstType(int cid){
    	  DbCon dbcon=new DbCon();
    	  int result=0;
    	  Connection con=null;
    	  try {
			con=DbCon.getCon();
			String sql="delete  from t_category where cid=? ";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, cid);
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
      //后台：删除一级分类时，要验证该一级分类下是否有二级分类
      
      public int foundChildCount(int cid ){
    	  DbCon dbcon=new DbCon();
    	  Connection con=null;
    	  int count=0;
    	  try {
			con=DbCon.getCon();
			String sql="SELECT COUNT(*) AS TOTAL FROM T_CATEGORY WHERE  PID=?";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, cid);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				count=rs.getInt("TOTAL");
			}
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
    	  return count;
        }
      
     //后台：删除二級分类时，要验证该二級分类下是否有商品存在
      public int foundGoodsCount(int cid ){
    	  DbCon dbcon=new DbCon();
    	  Connection con=null;
    	  int count=0;
    	  try {
			con=DbCon.getCon();
			String sql="SELECT COUNT(*) AS TOTAL FROM goods WHERE  typeid=?";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, cid);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				count=rs.getInt("TOTAL");
			}
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
    	  return count;
        }

  	 // 后台：显示二级分类
  	public  ResultSet showSecondType(PageBean page){
  		Connection con = null;
  		ResultSet rs=null;
  		String sql = "select * from (SELECT T_CATEGORY.* ,rownum rn FROM T_CATEGORY WHERE PID IS NOT NULL ORDER BY  pid ) where rn>? and rn<=?  ";
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
  	
	// 后台：用easyui显示二级分类，必须返回一个总记录数
	public int secondTypeCount(){
		DbCon dbcon = new DbCon();
		Connection con = null;
		ResultSet rs=null;
		int count=0;
		String sql = "SELECT COUNT(*) AS TOTAL  FROM T_CATEGORY WHERE  PID IS NOT NULL ";
		try {
			con = DbCon.getCon();
			PreparedStatement tmt = con.prepareStatement(sql);
			rs = tmt.executeQuery();
			 while(rs.next()){
				 count=rs.getInt("TOTAL");
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dbcon.closeCon(con);
			 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return count;
	}
	
	//后台：添加二级分类
    public  int addSecondType(Category category){
  	  DbCon dbcon=new DbCon();
  	  Connection con=null;
  	  int result=0;
  	  try {
			con=DbCon.getCon();
			String sql="INSERT INTO T_CATEGORY (cid,CNAME,PID,T_CATEGORY.DESCS) VALUES(t_category_seq.nextval,?,?,?)";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, category.getCname());
			pstmt.setInt(2, category.getPid());
			pstmt.setString(3, category.getDescs());
			result=pstmt.executeUpdate();
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
			dbcon.closeCon(con);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
    }
 
	
	// 后台：添加二级分类是，应该以所属的一级分类的名字添加，而不是cid号
	public  ResultSet FirstType(){
		Connection con = null;
		ResultSet rs=null;
		String sql = "SELECT * FROM t_category WHERE pid IS  NULL   ORDER BY  cid ";
		try {
			con = DbCon.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		}
	
	
	
	
}


