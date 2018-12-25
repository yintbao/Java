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
	
     // ǰ̨����һ������Ͷ�������ŵ�һ��list����
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
				// �����
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
	
	// ��̨����ʾһ������
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
	
	// ��̨����easyui��ʾһ�����࣬���뷵��һ���ܼ�¼��
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
	
	//��̨�����һ������
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
      
  	   //��̨���༭һ������
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
       
       
	//��̨��ɾ��һ������
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
      //��̨��ɾ��һ������ʱ��Ҫ��֤��һ���������Ƿ��ж�������
      
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
      
     //��̨��ɾ����������ʱ��Ҫ��֤�ö����������Ƿ�����Ʒ����
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

  	 // ��̨����ʾ��������
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
  	
	// ��̨����easyui��ʾ�������࣬���뷵��һ���ܼ�¼��
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
	
	//��̨����Ӷ�������
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
 
	
	// ��̨����Ӷ��������ǣ�Ӧ����������һ�������������ӣ�������cid��
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


