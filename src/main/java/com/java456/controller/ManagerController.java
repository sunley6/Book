package com.java456.controller;

import javax.annotation.Resource;

import com.java456.dao.BookDao;
import com.java456.dao.UserDao;
import com.java456.entity.Book;
import com.java456.entity.BookBorrow;
import com.java456.entity.Manager;
import com.java456.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java456.dao.ManagerDao;
import com.java456.util.CryptographyUtil;

import net.sf.json.JSONObject;



@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@Resource
	private ManagerDao managerDao;

	@Resource
	private UserDao userDao;


	@Resource
	private BookDao bookDao;
	/**
	 * /manager/login
	 */
	@ResponseBody
	@RequestMapping("/login")
	public JSONObject login(String name,String password)throws Exception {
		JSONObject result = new JSONObject();
		
		//获取 subject
		Subject subject=SecurityUtils.getSubject();
		//封装用户数据
		UsernamePasswordToken token=new UsernamePasswordToken(name,CryptographyUtil.md5(password, "java"));
		System.out.println("password="+CryptographyUtil.md5(password, "java")+"subject"+subject);
		
		try {
			//执行登陆  shiro的登陆
			subject.login(token);
			//执行登陆  shiro的登陆
			
			result.put("success", true);
			result.put("msg","登陆成功");
			Manager manager = managerDao.findByName(name);


			System.out.println(manager);
			SecurityUtils.getSubject().getSession().setAttribute("currentManager", manager); //把当前用户信息存到session中
		} catch (UnknownAccountException e) {
			result.put("success", false);
			result.put("msg","用户名不存在");
		}catch (IncorrectCredentialsException e) {
			result.put("success", false);
			result.put("msg","密码错误");
		}
		return result;
	}
	
	
	/**
	 * 注销
	 *  /user/logout
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout()throws Exception{
		SecurityUtils.getSubject().logout(); //shiro的退出
		return "redirect:/login";
	}
	
	
}
