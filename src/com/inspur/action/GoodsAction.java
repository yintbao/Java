package com.inspur.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.inspur.dao.GoodsDao;
import com.inspur.model.Goods;
import com.inspur.util.ExcelUtil;
import com.inspur.util.JsonUtil;
import com.inspur.util.Page;
import com.inspur.util.PageBean;
import com.inspur.util.ResponseUtil;
import com.opensymphony.xwork2.ActionSupport;

public class GoodsAction extends ActionSupport  implements ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	GoodsDao goodsdao=new GoodsDao();
	private String page;//显示商品的page： 第几页 （easyui传的）
	private String rows;//显示商品的rows： 一页多少行 （easyui传的）
	private File img;//上传商品的图片文件
	private String imgFileName;
	private String imgContentType;
	private Goods goods;
	private int id;//后台出来的商品id，用来删除商品
	private int goodsid;//前台用来添加购物车的商品id号
	private int deletegoodid;//
	private File goodsUploadFile;
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
	private int cid;
    public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
    public File getImg() {
		return img;
	}
	public void setImg(File img) {
		this.img = img;
	}
	public String getImgFileName() {
		return imgFileName;
	}
	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}
	public String getImgContentType() {
		return imgContentType;
	}
	public void setImgContentType(String imgContentType) {
		this.imgContentType = imgContentType;
	}
    public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
    public int getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}
 
    public int getDeletegoodid() {
		return deletegoodid;
	}
	public void setDeletegoodid(int deletegoodid) {
		this.deletegoodid = deletegoodid;
	}
    public File getGoodsUploadFile() {
		return goodsUploadFile;
	}
	public void setGoodsUploadFile(File goodsUploadFile) {
		this.goodsUploadFile = goodsUploadFile;
	}


	@Override
public String execute() throws Exception {
	/*
	 * 1. 获取当前页码
	 */
	int pc = getPageCode(request);
	/*
	 * 2. 使用BookService查询，得到PageBean
	 */
	Page<Goods> pb = goodsdao.listGoods(cid, pc);
	/*
	 * 3. 获取url，设置给PageBean
	 */
	String url = getUrl(request);
	pb.setUrl(url);
	/*
	 * 4. 把PageBean保存到request，转发到/jsps/book/list.jsp
	 */
	request.setAttribute("default", "/jsps/goods/list.jsp");
	request.setAttribute("pb", pb);
	return "list";
}


/**
 * 前台：添加购物车
 * @return
 * @throws Exception
 */
  public String addCar()throws Exception{
	HttpSession session=request.getSession();
	@SuppressWarnings("unchecked")
	List<Map<String ,Object>> carlist=(List<Map<String ,Object>>)session.getAttribute("carlist");
	if(carlist==null){
		 carlist=new ArrayList<Map<String ,Object>>();
	}
		//1.需要将该产品信息放入购物车  //2.需要判断是否已经买过该产品
		boolean isExits=false;
	    for( Map<String ,Object> map:carlist){
	    	if(((Goods)map.get("goods")).getId()==goodsid){
	    		int count=Integer.parseInt((map.get("count").toString()));
	    		count++;
	    		map.put("count", count);
	    		isExits=true;
	    		break;
	    	}
	    }
	    if(!isExits){
	    	Map<String, Object> goodsMap = new HashMap<String, Object>();
	    	Goods goods=goodsdao.findGoodsById(goodsid);
	    	goodsMap.put("goods", goods);
	    	goodsMap.put("count", "1");
	    	carlist.add(goodsMap);
	    	session.setAttribute("carlist", carlist);
	    }
	   request.setAttribute("default", "/jsps/car/car.jsp");
	   return "car";
}
  
  /**
   * 前台：删除购物车里的物品
   * @return
   * @throws Exception
   */
  public String deleteCar() throws Exception {
	   HttpSession session=request.getSession();
	  @SuppressWarnings("unchecked")
	List<Map<String,Object>> carlist=(List<Map<String,Object>>)session.getAttribute("carlist");
	  for(int i=0;i<=carlist.size();i++){
		  if(((Goods)(carlist.get(i).get("goods"))).getId()==deletegoodid){
			  carlist.remove(i);
			  break;
		  }
		  
	  }
	  request.setAttribute("default", "/jsps/car/car.jsp");
	  return "car";
	
	}
  
  
  
//后台：展示所有商品
public String ShowGoods() throws Exception {
	int total=goodsdao.goodsCount();
	PageBean pagebean= new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
	ResultSet rs=goodsdao.showGoods(pagebean);
	JSONObject result = new JSONObject();
	JSONArray jsonArray = JsonUtil.formatRsToJsonArray(rs);
	result.put("rows", jsonArray);
	result.put("total", total);
	ResponseUtil.write(ServletActionContext.getResponse(), result);
	return null;
}
//后台：在商品时，需要展商品分类的名称，通过分类的名称来添加商品
public String ShowgoodsType() throws Exception {
	ResultSet rs=goodsdao.GoodsType();
	JSONArray jsonArray = JsonUtil.formatRsToJsonArray(rs);
	ResponseUtil.write(ServletActionContext.getResponse(), jsonArray);
	return null;
}
//后台：添加商品
public String addGoods()throws Exception{
	 JSONObject result=new JSONObject();
	 goods.setImg(imgFileName);//设置图片的名称放到数据库
	 int a= goodsdao.addGoods(goods);
	 if(a==1){
		  InputStream is=new FileInputStream(img);
		  @SuppressWarnings("deprecation")
		String path=ServletActionContext.getRequest().getRealPath("/image/products");
		  OutputStream os=new FileOutputStream(new File(path,this.imgFileName));
		  byte buffer []=new byte[400];
			int length=0;
			while((length=is.read(buffer))>0){
				os.write(buffer, 0, length);
			}
		   os.close();
		   is.close();
	       result.put("success", "true");//设置给客户端返回的执行信息
	  }else{
		 result.put("errorMsg", "失败");//设置给客户端返回的执行信息
	  }
	 ResponseUtil.write(ServletActionContext.getResponse(), result);//返回信息
	  return null;
}


//后台：删除商品 
 public String deleteType() throws Exception {
	JSONObject result=new JSONObject();
	int a=goodsdao.deleteGoods(id);
    if(a!=0){
		 result.put("success", "true");
		}else{
		 result .put("errorMsg", "失败");
	   }
   ResponseUtil.write(ServletActionContext.getResponse(), result);
   return null;
 }
 
 /**
  * 后台导出excel表
  * @return
  * @throws Exception
  */
// public String uploadExcel()throws Exception{
//	Workbook wb=new HSSFWorkbook();
//	ResultSet rs=goodsdao.showAllGoods();
//	String headers[]={"商品号","商品所属类别","商品名称","市场价","优惠价","库存量","商品描述"};
//	ExcelUtil.fillExcelData(rs, wb, headers);
//	ResponseUtil.export(ServletActionContext.getResponse(), wb, "导出excel.xls");
//	return null;
// }
 
public String uploadExcel()throws Exception{
	ResultSet rs=goodsdao.showAllGoods();
	Workbook wb=ExcelUtil.fillExcelDataWithTemplate(rs, "goodsExporTemplate.xls");
	ResponseUtil.export(ServletActionContext.getResponse(), wb, "商品列表.xls");
	return null;
}

 /**
  * 后台通过excel表格批量导入数据
  * @return
  * @throws Exception
  */
 public String uploadGoods()throws Exception{
		POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(goodsUploadFile));
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		HSSFSheet hssfSheet=wb.getSheetAt(0); // 获取第一个Sheet页
		if(hssfSheet!=null){
			for(int rowNum=1;rowNum<=hssfSheet.getLastRowNum();rowNum++){
				HSSFRow hssfRow=hssfSheet.getRow(rowNum);
				if(hssfRow==null){
					continue;
				}
			 Goods goods=new Goods();
			 hssfRow.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			 goods.setGoodsno(Integer.valueOf(hssfRow.getCell(0).getStringCellValue()));
			 goods.setName(hssfRow.getCell(1).getStringCellValue());
			 hssfRow.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			 goods.setTypeid(Integer.valueOf(hssfRow.getCell(2).getStringCellValue()));
			 goods.setDescribe(hssfRow.getCell(3).getStringCellValue());
			 goods.setImg(hssfRow.getCell(4).getStringCellValue());
			 goods.setPrice(Float.parseFloat(hssfRow.getCell(5).toString()));
			 hssfRow.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			 goods.setQty(Integer.valueOf(hssfRow.getCell(6).getStringCellValue()));
			 goods.setDiscount(Float.parseFloat(hssfRow.getCell(7).toString()));
			 goodsdao.addGoods(goods);
			}
		}
		JSONObject result=new JSONObject();
		result.put("success", "true");
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
 }
/*
 * 获取当前页码
 */
private int getPageCode(HttpServletRequest req) {
	String pageCode = req.getParameter("pc");
	if(pageCode == null) return 1; 
	try {
		return Integer.parseInt(pageCode); 
	} catch(RuntimeException e) {
		return 1;
	}
}

/*
 * 获取请求的url，但去除pc参数
 */
private String getUrl(HttpServletRequest req) {
	String url = req.getRequestURI() + "?" + req.getQueryString();
	int fromIndex = url.lastIndexOf("&pc=");
	if(fromIndex == -1) return url;
	int toIndex = url.indexOf("&", fromIndex + 1);
	if(toIndex == -1) return url.substring(0, fromIndex);
	return url.substring(0, fromIndex) + url.substring(toIndex);
}
public void setServletRequest(HttpServletRequest request) {
	// TODO Auto-generated method stub
	this.request=request;
}

}
