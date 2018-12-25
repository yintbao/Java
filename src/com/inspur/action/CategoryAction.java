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
 *  ��̨һ������Ͷ�������
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
	private String page;//��ʾһ�������page�� �ڼ�ҳ ��easyui���ģ�
	private String rows;//��ʾһ�������rows�� һҳ������ ��easyui���ģ�
	private String cname;//һ�����������
	private String desc;//һ�����������
	private File image;//�ϴ�һ�������ͼƬ�ļ�
	private String imageFileName;
	private String imageContentType;
	private int cid;//ɾ��һ���������ɾ���������ഫ������cid
	private String cids;//�༭һ������ʱ��������cid
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
	
	//��̨��չʾһ������
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
	 //��̨:���һ������
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
			result.put("errorMsg", "ʧ��");
		  }
	     ResponseUtil.write(ServletActionContext.getResponse(), result);
		os.close();
		is.close();
	  return null;
   }
     //��̨���༭һ������
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
			result.put("errorMsg", "ʧ��");
		  }
	     ResponseUtil.write(ServletActionContext.getResponse(), result);
		os.close();
		is.close();
	  return null;
  }
   
   
   
     //��̨��ɾ��һ������
    public String deleteType() throws Exception {
    	JSONObject result=new JSONObject();
    	  try{
    		  if(categorydao.foundChildCount(cid)>0)
    			 throw new  CategoryException("ɾ��һ������ʧ�ܣ�");
    		  int a=categorydao.deleteFirstType(cid);
    		  if(a!=0){
    			 result.put("success", "true");
    		  }else{
    			 result .put("errorMsg", "ʧ��");
    		  }
    	     }catch(Exception e){
    		  result.put("errorMsg", "ɾ��һ������ʧ�ܣ��÷������ж�������");
    	    }
    	   ResponseUtil.write(ServletActionContext.getResponse(), result);
	   return null;
     }
  //��̨��չʾ��������
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
   
	//��̨������Ӷ�������ʱ����Ҫչʾһ����������ƣ�ͨ��һ���������������Ӷ�������
	public String ShowType() throws Exception {
		ResultSet rs=categorydao.FirstType();
		JSONArray jsonArray = JsonUtil.formatRsToJsonArray(rs);
		ResponseUtil.write(ServletActionContext.getResponse(), jsonArray);
		return null;
	}
	 
   //��̨����Ӷ�������
	   public String addSecondType()throws Exception{
		   Category category=new Category(cname,desc,cid);
		    int a=categorydao.addSecondType(category);
		    JSONObject result=new JSONObject();
		      if(a!=0){
					result.put("success", "true");
		        }else{
				result.put("errorMsg", "���и÷��࣡");
			  }
		     ResponseUtil.write(ServletActionContext.getResponse(), result);
		  return null;
	   }
	//��̨��ɾ����������
	    public String deleteSecondType() throws Exception {
	    	JSONObject result=new JSONObject();
	    	  try{
	    		  if(categorydao.foundGoodsCount(cid)>0)
	    			 throw new  CategoryException("ɾ����������ʧ�ܣ�");
	    		  int a=categorydao.deleteFirstType(cid);
	    		  if(a!=0){
	    			 result.put("success", "true");
	    		  }else{
	    			 result .put("errorMsg", "ʧ��");
	    		  }
	    	     }catch(Exception e){
	    		  result.put("errorMsg", "ɾ����������ʧ�ܣ��÷��໹����Ʒ");
	    	    }
	    	   ResponseUtil.write(ServletActionContext.getResponse(), result);
		   return null;
	     }
   
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}
}
