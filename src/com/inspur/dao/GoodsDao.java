package com.inspur.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.inspur.model.Goods;
import com.inspur.util.DbCon;
import com.inspur.util.Page;
import com.inspur.util.PageBean;

public class GoodsDao {
	public Page<Goods> listGoods(int cid, int pc) {
		GoodsDao goodsdao =new GoodsDao();
		List<Goods> list = new ArrayList<Goods>();
		Connection con = null;
		int ps = 5;// 一页显示的记录数
		// pc是当前页,计算从那条记录开始
		int start = (pc - 1) * ps;
		int end=start+ps;
		int tr=goodsdao.Categorygoodscount(cid);//总记录数
		if (pc == 1) {
			try {
				String sql = "select * from (SELECT T_CATEGORY.*,goods.* ,rownum rn  FROM GOODS,T_CATEGORY WHERE GOODS.TYPEID=T_CATEGORY.CID AND T_CATEGORY.CID=?) where  rn>=0 and rownum<=?  ";
				con = DbCon.getCon();
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cid);
				pstmt.setInt(2, ps);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Goods goods = new Goods();
					goods.setCname(rs.getString("cname"));
					goods.setDescribe(rs.getString("describe"));
					goods.setDiscount(rs.getFloat("discount"));
					goods.setGoodsno(rs.getInt("goodsno"));
					goods.setId(rs.getInt("id"));
					goods.setImg(rs.getString("img"));
					goods.setName(rs.getString("name"));
					goods.setPrice(rs.getFloat("price"));
					goods.setQty(rs.getInt("qty"));
					goods.setTypeid(rs.getInt("typeid"));
					list.add(goods);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          } else {
			try {
				String sql = "select * from (SELECT T_CATEGORY.*,goods.* ,rownum rn  FROM GOODS,T_CATEGORY WHERE GOODS.TYPEID=T_CATEGORY.CID AND T_CATEGORY.CID=?) where  rn>? and rn<=? ";
				con = DbCon.getCon();
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cid);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Goods goods = new Goods();
					goods.setCname(rs.getString("cname"));
					goods.setDescribe(rs.getString("describe"));
					goods.setDiscount(rs.getFloat("discount"));
					goods.setGoodsno(rs.getInt("goodsno"));
					goods.setId(rs.getInt("id"));
					goods.setImg(rs.getString("img"));
					goods.setName(rs.getString("name"));
					goods.setPrice(rs.getFloat("price"));
					goods.setQty(rs.getInt("qty"));
					goods.setTypeid(rs.getInt("typeid"));
					list.add(goods);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Page<Goods> pb = new Page<Goods>();
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		pb.setDataList(list);
		return pb;

	}
	/**
	 * 计算某二级分类下商品的个数
	 */
	public int Categorygoodscount(int cid) {
		DbCon dbcon = new DbCon();
		Connection con = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "SELECT COUNT(*) AS total FROM  goods  WHERE typeid=? ";
		try {
			con = DbCon.getCon();
			PreparedStatement tmt = con.prepareStatement(sql);
			tmt.setInt(1, cid);
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
	
	//前台：根据商品的id号，去查找商品
	 public Goods findGoodsById(int id){
		 DbCon dbcon=new DbCon();
		 Connection con=null;
		 Goods goods=null;
		 try {
			con=DbCon.getCon();
			String sql="select * from goods where id=?";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				goods=new Goods();
				goods.setId(id);
				goods.setName(rs.getString("name"));
				goods.setDescribe(rs.getString("describe"));
				goods.setDiscount(rs.getFloat("discount"));
				goods.setGoodsno(rs.getInt("goodsno"));
				goods.setImg(rs.getString("img"));
				goods.setPrice(rs.getFloat("price"));
				goods.setQty(rs.getInt("qty"));
				goods.setTypeid(rs.getInt("typeid"));
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
		return goods;
		 
	 }
	
	// 后台：显示商品
	public  ResultSet showGoods(PageBean page){
		Connection con = null;
		ResultSet rs=null;
		String sql = "select * from (SELECT rownum rn ,a.*,b.* FROM GOODS a,T_CATEGORY b  WHERE a.TYPEID=b.CID  ORDER BY  TYPEID) where   rn>? and rn<=? ";
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
	// 后台：显示商品，必须返回一个总记录数
	public int goodsCount(){
		DbCon dbcon = new DbCon();
		Connection con = null;
		ResultSet rs=null;
		int count=0;
		String sql = "SELECT COUNT(*) AS TOTAL  FROM goods ";
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
	// 后台：添加商品，应该以所属的分类的名字添加，而不是typeid号
	public  ResultSet GoodsType(){
		Connection con = null;
		ResultSet rs=null;
		String sql = "SELECT CID,CNAME FROM T_CATEGORY WHERE PID IS NOT  NULL   ORDER BY  CID";
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
	
	// 后台：添加商品
	   public  int addGoods(Goods goods){
	    	  DbCon dbcon=new DbCon();
	    	  Connection con=null;
	    	  int result=0;
	    	  try {
				con=DbCon.getCon();
				String sql="INSERT INTO goods VALUES(goods_seq.nextval,?,?,?,?,?,?,?,?)";
				PreparedStatement pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, goods.getGoodsno());
				pstmt.setString(2, goods.getName());
				pstmt.setInt(3, goods.getTypeid());
				pstmt.setString(4, goods.getDescribe());
				pstmt.setString(5, goods.getImg());
				pstmt.setFloat(6, goods.getPrice());
				pstmt.setInt(7, goods.getQty());
				pstmt.setFloat(8, goods.getDiscount());
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
		// 后台：删除商品
	      public int deleteGoods(int id){
	    	  DbCon dbcon=new DbCon();
	    	  int result=0;
	    	  Connection con=null;
	    	  try {
				con=DbCon.getCon();
				String sql="delete  from goods where id=? ";
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
	      
	 /**
	  * 后台导出所有的商品excel表格的形式
	  *      
	  */
	  	public  ResultSet showAllGoods(){
	  		Connection con = null;
	  		ResultSet rs=null;
	  		String sql = "SELECT goodsno,cname,name,price,discount,qty,describe FROM GOODS a,T_CATEGORY b  WHERE a.TYPEID=b.CID  ORDER BY  TYPEID";
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
