package com.inspur.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.inspur.dao.CategoryDao;
import com.inspur.model.Category;
import com.inspur.util.CategoryException;
import com.inspur.util.JsonUtil;
import com.inspur.util.PageBean;
import com.inspur.util.ResponseUtil;
import com.opensymphony.xwork2.ActionSupport;
/**
 *  后台一级分类和二级分类
 * @author DY1101shaoyuxian
 *
 */
public class CategoryAction extends ActionSupport  implements ServletRequestAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private HttpServletRequest request;
	CategoryDao categorydao=new CategoryDao();
	private String page;//显示一级分类的page： 第几页 （easyui传的）
	private String rows;//显示一级分类的rows： 一页多少行 （easyui传的）
	private String cname;//一级分类的名称
	private String desc;//一级分类的描述
	private File image;//上传一级分类的图片文件
	private String imageFileName;
	private String imageContentType;
	private int cid;//删除一级分类或者删除二级分类传过来的cid
	private String cids;//编辑一级分类时传过来的cid
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public String getImageContentType() {
		return imageContentType;
	}
	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCids() {
		return cids;
	}
	public void setCids(String cids) {
		this.cids = cids;
	}
	
	//后台：展示一级分类
	public String ShowFistType() throws Exception {
		int total=categorydao.firstTypeCount();
		PageBean pagebean= new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		ResultSet rs=categorydao.showFirstType(pagebean);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JsonUtil.formatRsToJsonArray(rs);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	 //后台:添加一级分类
   public String addFistType()throws Exception{
	    InputStream is=new FileInputStream(image);
		@SuppressWarnings("deprecation")
		String path=ServletActionContext.getRequest().getRealPath("/image/firstType");
		System.out.println(path);
		OutputStream os=new FileOutputStream(new File(path,this.imageFileName));
		byte buffer []=new byte[400];
		int length=0;
		while((length=is.read(buffer))>0){
			os.write(buffer, 0, length);
		}
	    Category category=new Category(cname,imageFileName,desc);
	    int a=categorydao.addFistType(category);
	    JSONObject result=new JSONObject();
	      if(a!=0){
				result.put("success", "true");
	        }else{
			result.put("errorMsg", "失败");
		  }
	     ResponseUtil.write(ServletActionContext.getResponse(), result);
		os.close();
		is.close();
	  return null;
   }
     //后台：编辑一级分类
   public String modifeFistType()throws Exception{
	    InputStream is=new FileInputStream(image);
		@SuppressWarnings("deprecation")
		String path=ServletActionContext.getRequest().getRealPath("/image/firstType");
		OutputStream os=new FileOutputStream(new File(path,this.imageFileName));
		byte buffer []=new byte[400];
		int length=0;
		while((length=is.read(buffer))>0){
			os.write(buffer, 0, length);
		}
	    Category category=new Category(Integer.parseInt(cids),cname,imageFileName,desc);
	    System.out.println(category.getCid()+category.getCname()+category.getDescs());
	    int a=categorydao.modefFistType(category);
	    JSONObject result=new JSONObject();
	      if(a!=0){
				result.put("success", "true");
	        }else{
			result.put("errorMsg", "失败");
		  }
	     ResponseUtil.write(ServletActionContext.getResponse(), result);
		os.close();
		is.close();
	  return null;
  }
   
   
   
     //后台：删除一级分类
    public String deleteType() throws Exception {
    	JSONObject result=new JSONObject();
    	  try{
    		  if(categorydao.foundChildCount(cid)>0)
    			 throw new  CategoryException("删除一级分类失败！");
    		  int a=categorydao.deleteFirstType(cid);
    		  if(a!=0){
    			 result.put("success", "true");
    		  }else{
    			 result .put("errorMsg", "失败");
    		  }
    	     }catch(Exception e){
    		  result.put("errorMsg", "删除一级分类失败！该分类下有二级分类");
    	    }
    	   ResponseUtil.write(ServletActionContext.getResponse(), result);
	   return null;
     }
  //后台：展示二级分类
	public String ShowSecondType() throws Exception {
		int total=categorydao.secondTypeCount();
		PageBean pagebean= new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		ResultSet rs=categorydao.showSecondType(pagebean);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JsonUtil.formatRsToJsonArray(rs);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
   
	//后台：在添加二级分类时，需要展示一级分类的名称，通过一级分类的名称来添加二级分类
	public String ShowType() throws Exception {
		ResultSet rs=categorydao.FirstType();
		JSONArray jsonArray = JsonUtil.formatRsToJsonArray(rs);
		ResponseUtil.write(ServletActionContext.getResponse(), jsonArray);
		return null;
	}
	 
   //后台：添加二级分类
	   public String addSecondType()throws Exception{
		   Category category=new Category(cname,desc,cid);
		    int a=categorydao.addSecondType(category);
		    JSONObject result=new JSONObject();
		      if(a!=0){
					result.put("success", "true");
		        }else{
				result.put("errorMsg", "已有该分类！");
			  }
		     ResponseUtil.write(ServletActionContext.getResponse(), result);
		  return null;
	   }
	//后台：删除二级分类
	    public String deleteSecondType() throws Exception {
	    	JSONObject result=new JSONObject();
	    	  try{
	    		  if(categorydao.foundGoodsCount(cid)>0)
	    			 throw new  CategoryException("删除二级分类失败！");
	    		  int a=categorydao.deleteFirstType(cid);
	    		  if(a!=0){
	    			 result.put("success", "true");
	    		  }else{
	    			 result .put("errorMsg", "失败");
	    		  }
	    	     }catch(Exception e){
	    		  result.put("errorMsg", "删除二級分类失败！该分类还有商品");
	    	    }
	    	   ResponseUtil.write(ServletActionContext.getResponse(), result);
		   return null;
	     }
   
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}
}
