package com.java456.controller;

import com.java456.dao.BookDao;
import com.java456.dao.ManagerDao;
import com.java456.dao.UserDao;
import com.java456.entity.Manager;
import com.java456.entity.Member;
import com.java456.entity.User;
import com.java456.service.BookTypeService;
import com.java456.service.UserService;
import com.java456.util.CryptographyUtil;
import com.java456.util.MyUtil;
import com.java456.util.StringUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {


	@Resource
	private UserDao userDao;

	@Resource
	private UserService userService;

	@Resource
	private BookTypeService bookTypeService;


	@Resource
	private BookDao bookDao;
	/**
	 * /user/login
	 */
	@ResponseBody
	@RequestMapping("/login")
	public JSONObject login(String name,String pwd)throws Exception {
		JSONObject result = new JSONObject();

		System.out.println(name+pwd);
		User user=userDao.findByName(name);
//		System.out.println("useru"+user.toString());
		User user1=userDao.checkLogin(name,CryptographyUtil.md5(pwd, "java"));
		System.out.println(user1);
//		System.out.println("useru222"+user1.toString());
//		System.out.println(user.toString());
		if (user==null){
			result.put("success", false);
			result.put("msg","用户名不存在");
		}else if (user1==null){
			result.put("success", false);
			result.put("msg","密码错误");
		}else {
			result.put("success", true);
			result.put("msg","登陆成功");

			SecurityUtils.getSubject().getSession().setAttribute("currentUser", user); //把当前用户信息存到session中

		}

//		System.out.println(name+"1111"+pwd);
//
//		//获取 subject
//		Subject subject=SecurityUtils.getSubject();
//		//封装用户数据
//		UsernamePasswordToken token=new UsernamePasswordToken(name,CryptographyUtil.md5(pwd, "java"));
		System.out.println("password="+CryptographyUtil.md5(pwd, "java"));

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
		return "redirect:/houtai/user/main";
	}

	/**
	 * /user/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add1(@Valid User user , BindingResult bindingResult, HttpServletResponse response,
						   HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();

		System.out.println(user);
		if (bindingResult.hasErrors()) {
			result.put("success", false);
			System.out.println(user.toString());
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		} else {
			System.out.println("success");
			System.out.println(user.toString());


			//看看 用户是否存在
			User temp = userDao.findByName(user.getName());
			if(temp==null) {
				user.setCreateDateTime(new Date());
				user.setCredit(100);
				user.setPwd(CryptographyUtil.md5(user.getPwd(), "java"));
				userDao.save(user);
				result.put("success", true);
				result.put("msg", "注册成功");
			}else{
				System.out.println(user.getName()+"2777777777222");
				result.put("success", false);
				result.put("msg", "用户已存在");
				return result;
			}
			//看看编号 有没有重复
			System.out.println(user.getName()+"00000");
			return result;
		}
	}

	@RequestMapping(value = "myborrow")
	public String f1(Model model){
		List<String> list1= bookTypeService.findAll();

		model.addAttribute("typelist",list1);
		return "pc/user/my_borrow";
	}

	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update(@Valid User user, BindingResult bindingResult, HttpServletRequest request)throws Exception {
		JSONObject result = new JSONObject();
//
//		if(bindingResult.hasErrors()){
//			result.put("success", false);
//			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
//			return result;
//		}else{
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		currentUser.setMail(user.getMail());
		currentUser.setRemark(user.getRemark());
		currentUser.setPhone(currentUser.getPhone());
			userService.update(currentUser);
			result.put("success", true);
			result.put("msg", "修改成功");
			return result;
//		}
	}

	@ResponseBody
	@RequestMapping("/update/password")
	public JSONObject updatepassword(@RequestParam(value = "password", required = false) String password,
									 @RequestParam(value = "newPwd", required = false) String newPwd
									 ) {
		JSONObject result = new JSONObject();
//
//		if(bindingResult.hasErrors()){
//			result.put("success", false);
//			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
//			return result;
//		}else{
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");

		if (!CryptographyUtil.md5(password, "java").equals(currentUser.getPwd())){
			result.put("success", true);
			result.put("msg", "原密码不正确");
			return result;
		}else {
			currentUser.setPwd(CryptographyUtil.md5(newPwd, "java"));
			userService.update(currentUser);
			result.put("success", true);
			result.put("msg", "修改成功");
			return result;
		}

	}


	@ResponseBody
	@RequestMapping("/userlist")
	public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
									@RequestParam(value = "limit", required = false) Integer limit,
									HttpServletResponse response,
									HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		List<User> userList = userService.list(map, page-1, limit);
		long total = userService.getTotal(map);
		map.put("data", userList);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}


	@ResponseBody
	@RequestMapping("/set_new_pwd")
	public JSONObject set_new_pwd(User user, HttpServletRequest request)throws Exception {
		JSONObject result = new JSONObject();


		if(StringUtil.isNotEmpty(user.getPwd())){
			user.setPwd(CryptographyUtil.md5(user.getPwd(),"java"));
		}
		userService.update(user);
		result.put("success", true);
		result.put("msg", "修改成功");
		return result;
	}
	
}
