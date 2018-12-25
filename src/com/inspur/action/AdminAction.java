package com.inspur.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.inspur.dao.AdminDao;
import com.inspur.model.Admin;
import com.inspur.util.ResponseUtil;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport implements ServletRequestAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AdminDao admindao=new AdminDao();
	private String image;
	private Admin admin;//��̨��¼ʱ������Admin������
	private String oldpassword; //����Ա��������ʱ�ľ�����
	private String newpassword;//����Ա��������ʱ��������
	private String repassword;//����Ա��������ʱ��ȷ��������
	private HttpServletRequest request;

	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	/**
	 * ����Ա��¼
	 */
	public String execute() throws Exception {
		HttpSession session=request.getSession();
		Admin Curentadmin=admindao.getAdmin(admin);
		String ip=request.getRemoteAddr();
		if(image==null||"".equals(image)){
			request.setAttribute("error", "��֤�벻��Ϊ��");
			return "failed";
		}
		String sRand=(String)session.getAttribute("sRand");
		if(!image.equals(sRand)){
			request.setAttribute("error", "��֤����󣡣�");
			return "failed";
		}
		if(Curentadmin==null){
			request.setAttribute("error", "�û�����������󣡣�");
			return "failed";
		}else{
			session.setAttribute("Curentadmin", Curentadmin);
			Curentadmin.setLoginip(ip);
			admindao.UpdateAdmin(Curentadmin);
			return "success";
		}
	}
	/**
	 * ���Ĺ���Ա������
	 * @return
	 * @throws Exception
	 */
	public String updatePassword()throws Exception{
		HttpSession session=request.getSession();
		JSONObject result=new JSONObject();
		Admin admin=(Admin)session.getAttribute("Curentadmin");
		if(!repassword.equals(newpassword)){
			result.put("errorMsg", "�����������벻һ�£�");	
		    ResponseUtil.write(ServletActionContext.getResponse(), result);//������Ϣ
		    return null;
		}else if(!admin.getPassword().equals(oldpassword)){
			result.put("errorMsg", "ԭ���벻��ȷ��");	
		    ResponseUtil.write(ServletActionContext.getResponse(), result);//������Ϣ
		    return null;
		}else {
			admin.setPassword(newpassword);
			int a=admindao.updateAdminPassWord(admin);
			if(a!=0){
			    ResponseUtil.write(ServletActionContext.getResponse(), result);//������Ϣ
			}
		}
		return null;
	}
	/**
	 * ����Ա�˳�
	 * @return
	 * @throws Exception
	 */
	public String logout()throws Exception{
		HttpSession session=request.getSession();
		session.removeAttribute("Curentadmin");
		return "logout";
	}
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}
}
