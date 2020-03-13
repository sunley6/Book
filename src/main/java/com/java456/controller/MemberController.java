package com.java456.controller;


import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.java456.dao.UserDao;
import com.java456.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.java456.dao.MemberDao;
import com.java456.entity.Member;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/member")
public class MemberController {

	@Resource
	private MemberDao memberDao   ;

	@Resource
	private UserDao userDao   ;
	
	/**
	 *    /member/a/login
	 */
	@RequestMapping("/a/login")
	public ModelAndView login(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/user/login");
		return mav;
	}

	@RequestMapping(value = "/b/login")
	public String f1(){
		return "pc/user/login";
	}
	
	/**
	 * /member/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Member member , BindingResult bindingResult, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		
		if (bindingResult.hasErrors()) {
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		} else {
			
			//看看 用户是否存在
			Member temp = memberDao.findByName(member.getName());
			if(temp==null) {
				member.setCreateDateTime(new Date());
				memberDao.save(member);
				result.put("success", true);
				result.put("msg", "注册成功");
			}else{
				result.put("success", false);
				result.put("msg", "用户已存在");
				return result;
			}
			//看看编号 有没有重复
			return result;
		}
	}

	/**
	 * /member/add
	 */
	@ResponseBody
	@RequestMapping("/add1")
	public JSONObject add1(@Valid User user , BindingResult bindingResult, HttpServletResponse response,
						   HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();


		if (bindingResult.hasErrors()) {
			result.put("success", false);
			System.out.println(user.toString());
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		} else {
			System.out.println("success");
			System.out.println(user.toString());


			//看看 用户是否存在
			Member temp = memberDao.findByName(user.getName());
			if(temp==null) {
				user.setCreateDateTime(new Date());
				user.setCredit(100);
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
	
	/**
	 *    /member/login
	 */
	@ResponseBody
	@RequestMapping("/login")
	public JSONObject login(String name,String  pwd,HttpSession session)throws Exception {
		JSONObject result = new JSONObject();
		System.out.println(name+"222"+pwd);
		Member temp = memberDao.findByName(name);
		if(temp==null) {
			result.put("success", false);
			result.put("msg","用户不存在");
		}else {
			if(temp.getPwd().equals(pwd)) {
				result.put("success", true);
				result.put("msg","登陆成功");
				session.setAttribute("member", temp);
			}else {
				result.put("success", false);
				result.put("msg","密码错误");
			}
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
}
