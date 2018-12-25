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
	private HttpServletRequest request; //创建Servlet对象
	UserDao userdao = new UserDao();
	private String page;//后台：显示会员的page： 第几页 （easyui传的）
	private String rows;//后台：显示会员的rows： 一页多少行 （easyui传的）
	private int id;//后台：删除会时传过来的id
	private String username; // 异步检验用户是否存在
	private String email; // 异步检验email是否存在
	private String imageValue; // 异步检验验证码是否正确
	private User user; // 注册的表单信息
	private User loginUser;//登陆的表单信息
	private String activecode; // 用户的激活码
	private String oldpassword;//用户修改自己的密码时，ajax验证原密码是否正确
    private String newpassword;//用户修改自己的密码时的新密码
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
	 * 前台：异步校验用户名是否被注册
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
	 * 前台：异步校验邮箱是否被注册
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
	 *前台： 异步校验验证码是否正确
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
	 *前台： 用户注册
	 */
	public String register()throws Exception{
		HttpSession session=request.getSession();
		@SuppressWarnings("unused")
		String sRand=(String)session.getAttribute("sRand");
	  if(user.getUsername()==null||"".equals(user.getUsername())){
			request.setAttribute("error", "用户名不能为空！");
			return "register";
		}else if(user.getPassword()==null||"".equals(user.getPassword())){
			request.setAttribute("error", "密码不能为空！");
			return "register";
		}else if(!user.getPassword().equals(user.getRepassword())){
				request.setAttribute("error", "两次输入密码不一致!");
				return "register";
			}else if(userdao.findUserByname(user.getUsername())!=null){
					request.setAttribute("error", "用户名已存在!");
					return "register";
				}else if(userdao.findUserByemail(user.getEmail())!=null){
					request.setAttribute("error", "该邮箱已被注册!");
					return "register";
				}else {
		           user.setState(0);//把刚注册的用户的状态设为0，未激活
		           user.setPassword(MD5.getMD5(user.getPassword().getBytes()));//对用户的密码进行MD5加密
		           user.setActivecode(CommonUtils.uuid() + CommonUtils.uuid());//设置用户的激活码
		          int result=userdao.addUser(user);
		           if(result!=0){
			       // 读取email模板中的数据
			       try{
					Properties props = new Properties();
					props.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
					String host = props.getProperty("host");//获取邮件服务器地址
					String username = props.getProperty("username");//获取用户名
					String password = props.getProperty("password");//获取密码
					String from = props.getProperty("from");//获取发件人地址
					String to = user.getEmail();//获取收件人地址
					String subject = props.getProperty("subject");//获取主题
					//获取内容模板，替换其中的激活码
					String content = MessageFormat.format(props.getProperty("content"), user.getActivecode());
					// 发送邮件
					Session session1 = MailUtils.createSession(host, username, password);
					Mail mail = new Mail(from, to, subject, content);
					MailUtils.send(session1, mail);
					request.setAttribute("error", "注册成功，请您先激活！");
					return "register";
		          }catch(Exception e){
		            	 throw new RuntimeException(e);
		            }
			       }else{
				       request.setAttribute("error", "注册失败！");
				       return "register";
		            }
	             }
              }

	/**
	 *前台： 用户激活
	 * 
	 */
	public String activeUser() throws Exception {
		User resultUser = userdao.findUserByactiveCode(activecode);
		if (resultUser == null) {
			request.setAttribute("error", "没有该用户！请重新注册");
			return "activeuser";
		} else if (resultUser.getState() == 1) {
			request.setAttribute("error", "该用户已激活，请勿重新激活");
			return "activeuser";
		} else {
			int a = userdao.activeUser(activecode);
			if (a != 0) {
				request.setAttribute("error", "恭喜，激活成功！");
				return "activeuser";
			} else {
				request.setAttribute("error", "激活失败！");
				return "activeuser";
			}
		}
	}
 
	 /**
     *前台： 用户登陆
     */
	public String login()throws Exception{
		/**
		 * 先对表单进行验证
		 */
		if(!vateLogin(loginUser)){
			request.setAttribute("error", "信息不正确，请重新输入！");
			return "login";
		}else{
		HttpSession session=request.getSession();
		String passwordMD5=MD5.getMD5(loginUser.getPassword().getBytes());//把用户表单的密码转为MD5形式
		User ResultUser=userdao.findUserByname(loginUser.getUsername());
		if(ResultUser==null){
			request.setAttribute("error", "没有该用户！");
			return "login";
		}else if(!ResultUser.getPassword().equals(passwordMD5)){
			request.setAttribute("error", "用户名或密码错误！");
			return "login";
		}else if(ResultUser.getState()==0){
			request.setAttribute("error", "您还没激活！");
			return "login";
		}else{
			DateFormat ddtf = DateFormat.getDateTimeInstance(); 
	     	Date date=new Date();
	     	ResultUser.setPassword(loginUser.getPassword());//把用户信息放到session之前，从MD5格式转化为正常格式
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
	 * 前台：登陆校验方法
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
	    * 用户修改密码
	    */
	   public String updatePassword() throws Exception{
		   HttpSession session=request.getSession();
		   User user=(User)session.getAttribute("user");
		   user.setPassword(MD5.getMD5(newpassword.getBytes())) ;//把用户的新密码改为MD5的形式
		   int result=userdao.updateUserPassWord(user);
		   if(result!=0){
			   return "updatepasswordSuccess";
		   }else{
			   return "login";
		   }
	   }
	   
	   
	   
	/**
	 * 前台：用户退出
	 */
 public String resetUser() throws Exception{
		   HttpSession session=request.getSession();
		   session.removeAttribute("user");
		   return "login";
	   }
 
   /**
    * 前台：用户修改自己的密码时，ajax验证原密码是否正确
    * @return
    * @throws Exception
    */
 public String CheckPassword() throws Exception{
	  boolean flage=true;
	   HttpSession session=request.getSession();
	   User user=(User)session.getAttribute("user");
	    if(!(user.getPassword().equals(oldpassword))){
			ResponseUtil.write1(flage);//不正确的话返回true
	    }
	   return null;
 }
 
 
//后台：展示所有会员
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
 

//删除会员
public String deleteUser() throws Exception {
	  JSONObject result=new JSONObject();
	  int a=userdao.deleteUser(id);
      if(a!=0){
		 result.put("success", "true");
		}else{
		 result .put("errorMsg", "失败");
	   }
    ResponseUtil.write(ServletActionContext.getResponse(), result);
    return null;
}
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

}
