package com.inspur.action;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import com.inspur.dao.UserDao;
import com.inspur.model.User;
import com.inspur.util.JsonUtil;
import com.inspur.util.MD5;
import com.inspur.util.PageBean;
import com.inspur.util.ResponseUtil;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request; //����Servlet����
	UserDao userdao = new UserDao();
	private String page;//��̨����ʾ��Ա��page�� �ڼ�ҳ ��easyui���ģ�
	private String rows;//��̨����ʾ��Ա��rows�� һҳ������ ��easyui���ģ�
	private int id;//��̨��ɾ����ʱ��������id
	private String username; // �첽�����û��Ƿ����
	private String email; // �첽����email�Ƿ����
	private String imageValue; // �첽������֤���Ƿ���ȷ
	private User user; // ע��ı���Ϣ
	private User loginUser;//��½�ı���Ϣ
	private String activecode; // �û��ļ�����
	private String oldpassword;//�û��޸��Լ�������ʱ��ajax��֤ԭ�����Ƿ���ȷ
    private String newpassword;//�û��޸��Լ�������ʱ��������
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageValue() {
		return imageValue;
	}

	public void setImageValue(String imageValue) {
		this.imageValue = imageValue;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getActivecode() {
		return activecode;
	}

	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	/**
	 * ǰ̨���첽У���û����Ƿ�ע��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String CheckUsername() throws Exception {
		boolean flage = false;
		User ResultUser = userdao.findUserByname(username);
		if (ResultUser == null) {
			ResponseUtil.write1(flage);
		} else {
			flage = true;
			ResponseUtil.write1(flage);
		}
		return null;
	}

	/**
	 * ǰ̨���첽У�������Ƿ�ע��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String CheckEmail() throws Exception {
		boolean flage = false;
		User ResultUser = userdao.findUserByemail(email);
		if (ResultUser == null) {
			ResponseUtil.write1(flage);
		} else {
			flage = true;
			ResponseUtil.write1(flage);
		}
		return null;
	}

	/**
	 *ǰ̨�� �첽У����֤���Ƿ���ȷ
	 * 
	 * @return
	 * @throws Exception
	 */
	public String checkImageValue() throws Exception {
		boolean flage = false;
		HttpSession session = request.getSession();
		String sRand = (String) session.getAttribute("sRand");
		if (!sRand.equals(imageValue)) {
			ResponseUtil.write1(flage);
		} else {
			flage = true;
			ResponseUtil.write1(flage);
		}
		return null;
	}

	/**
	 *ǰ̨�� �û�ע��
	 */
	public String register()throws Exception{
		HttpSession session=request.getSession();
		@SuppressWarnings("unused")
		String sRand=(String)session.getAttribute("sRand");
	  if(user.getUsername()==null||"".equals(user.getUsername())){
			request.setAttribute("error", "�û�������Ϊ�գ�");
			return "register";
		}else if(user.getPassword()==null||"".equals(user.getPassword())){
			request.setAttribute("error", "���벻��Ϊ�գ�");
			return "register";
		}else if(!user.getPassword().equals(user.getRepassword())){
				request.setAttribute("error", "�����������벻һ��!");
				return "register";
			}else if(userdao.findUserByname(user.getUsername())!=null){
					request.setAttribute("error", "�û����Ѵ���!");
					return "register";
				}else if(userdao.findUserByemail(user.getEmail())!=null){
					request.setAttribute("error", "�������ѱ�ע��!");
					return "register";
				}else {
		           user.setState(0);//�Ѹ�ע����û���״̬��Ϊ0��δ����
		           user.setPassword(MD5.getMD5(user.getPassword().getBytes()));//���û����������MD5����
		           user.setActivecode(CommonUtils.uuid() + CommonUtils.uuid());//�����û��ļ�����
		          int result=userdao.addUser(user);
		           if(result!=0){
			       // ��ȡemailģ���е�����
			       try{
					Properties props = new Properties();
					props.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
					String host = props.getProperty("host");//��ȡ�ʼ���������ַ
					String username = props.getProperty("username");//��ȡ�û���
					String password = props.getProperty("password");//��ȡ����
					String from = props.getProperty("from");//��ȡ�����˵�ַ
					String to = user.getEmail();//��ȡ�ռ��˵�ַ
					String subject = props.getProperty("subject");//��ȡ����
					//��ȡ����ģ�壬�滻���еļ�����
					String content = MessageFormat.format(props.getProperty("content"), user.getActivecode());
					// �����ʼ�
					Session session1 = MailUtils.createSession(host, username, password);
					Mail mail = new Mail(from, to, subject, content);
					MailUtils.send(session1, mail);
					request.setAttribute("error", "ע��ɹ��������ȼ��");
					return "register";
		          }catch(Exception e){
		            	 throw new RuntimeException(e);
		            }
			       }else{
				       request.setAttribute("error", "ע��ʧ�ܣ�");
				       return "register";
		            }
	             }
              }

	/**
	 *ǰ̨�� �û�����
	 * 
	 */
	public String activeUser() throws Exception {
		User resultUser = userdao.findUserByactiveCode(activecode);
		if (resultUser == null) {
			request.setAttribute("error", "û�и��û���������ע��");
			return "activeuser";
		} else if (resultUser.getState() == 1) {
			request.setAttribute("error", "���û��Ѽ���������¼���");
			return "activeuser";
		} else {
			int a = userdao.activeUser(activecode);
			if (a != 0) {
				request.setAttribute("error", "��ϲ������ɹ���");
				return "activeuser";
			} else {
				request.setAttribute("error", "����ʧ�ܣ�");
				return "activeuser";
			}
		}
	}
 
	 /**
     *ǰ̨�� �û���½
     */
	public String login()throws Exception{
		/**
		 * �ȶԱ�������֤
		 */
		if(!vateLogin(loginUser)){
			request.setAttribute("error", "��Ϣ����ȷ�����������룡");
			return "login";
		}else{
		HttpSession session=request.getSession();
		String passwordMD5=MD5.getMD5(loginUser.getPassword().getBytes());//���û���������תΪMD5��ʽ
		User ResultUser=userdao.findUserByname(loginUser.getUsername());
		if(ResultUser==null){
			request.setAttribute("error", "û�и��û���");
			return "login";
		}else if(!ResultUser.getPassword().equals(passwordMD5)){
			request.setAttribute("error", "�û������������");
			return "login";
		}else if(ResultUser.getState()==0){
			request.setAttribute("error", "����û���");
			return "login";
		}else{
			DateFormat ddtf = DateFormat.getDateTimeInstance(); 
	     	Date date=new Date();
	     	ResultUser.setPassword(loginUser.getPassword());//���û���Ϣ�ŵ�session֮ǰ����MD5��ʽת��Ϊ������ʽ
		    ResultUser.setLastlogintime(ddtf.format(date));
		    ResultUser.setIp(request.getRemoteAddr());
			int a=userdao.updateUserLogin(ResultUser);
			if(a!=0){
				session.setAttribute("user", ResultUser);
				return "loginSuccess";
			}else{
			return "login";
			}
			
		}
	  }
	 }
	
	/**
	 * ǰ̨����½У�鷽��
	 */

	   public boolean  vateLogin(User user){
		   HttpSession session=request.getSession();
		   String username=user.getUsername();
		   String password=user.getPassword();
		   String imageValue=(String)session.getAttribute("sRand");
		   if(username==null||"".equals(username)){
			     return false;
		   }else if(username.length()<3||username.length()>20){
			     return false;
		     }else if(password==null||"".equals(password)){
		    	 return false;
		     }else if(password.length()<3||password.length()>20){
		    	 return false;
		     }else if(!user.getImageValue().equals(imageValue)){
		    	 return false;
		     }else if(user.getImageValue()==null||user.getImageValue().isEmpty()){
		    	 return false;
		     }
		    return true;
		   
	   }
	   
	   /**
	    * �û��޸�����
	    */
	   public String updatePassword() throws Exception{
		   HttpSession session=request.getSession();
		   User user=(User)session.getAttribute("user");
		   user.setPassword(MD5.getMD5(newpassword.getBytes())) ;//���û����������ΪMD5����ʽ
		   int result=userdao.updateUserPassWord(user);
		   if(result!=0){
			   return "updatepasswordSuccess";
		   }else{
			   return "login";
		   }
	   }
	   
	   
	   
	/**
	 * ǰ̨���û��˳�
	 */
 public String resetUser() throws Exception{
		   HttpSession session=request.getSession();
		   session.removeAttribute("user");
		   return "login";
	   }
 
   /**
    * ǰ̨���û��޸��Լ�������ʱ��ajax��֤ԭ�����Ƿ���ȷ
    * @return
    * @throws Exception
    */
 public String CheckPassword() throws Exception{
	  boolean flage=true;
	   HttpSession session=request.getSession();
	   User user=(User)session.getAttribute("user");
	    if(!(user.getPassword().equals(oldpassword))){
			ResponseUtil.write1(flage);//����ȷ�Ļ�����true
	    }
	   return null;
 }
 
 
//��̨��չʾ���л�Ա
 public String ShowUser() throws Exception {
 	int total=userdao.userCount();
 	PageBean pagebean= new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
 	ResultSet rs=userdao.showUser(pagebean);
 	JSONObject result = new JSONObject();
 	JSONArray jsonArray = JsonUtil.formatRsToJsonArray(rs);
 	result.put("rows", jsonArray);
 	result.put("total", total);
 	ResponseUtil.write(ServletActionContext.getResponse(), result);
 	return null;
 }
 

//ɾ����Ա
public String deleteUser() throws Exception {
	  JSONObject result=new JSONObject();
	  int a=userdao.deleteUser(id);
      if(a!=0){
		 result.put("success", "true");
		}else{
		 result .put("errorMsg", "ʧ��");
	   }
    ResponseUtil.write(ServletActionContext.getResponse(), result);
    return null;
}
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

}
