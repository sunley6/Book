package com.java456.controller.houtai;

import com.java456.dao.ManagerDao;
import com.java456.entity.*;
import com.java456.service.BookTypeService;
import com.java456.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/houtai/user")
public class HouTai_User_Controller {
	
	@Resource
	private ManagerDao managerDao;
	@Resource
	private RoleService roleService;

	@Resource
	private BookTypeService bookTypeService;
	
	/**
	 * /houtai/user/manage
	 */
	@RequiresPermissions(value = {"用户管理"})
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("pageTitle", "用户管理");
		mav.addObject("title", "用户管理");
		mav.setViewName("/admin/page/user/user_manage");
		return mav;
	}
	
	/**
	 * /houtai/user/add
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	public ModelAndView add() throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> map = null;
		List<Role> roleList = roleService.list(map, 0, 1000);
		mav.addObject("roleList", roleList);
		
		mav.addObject("btn_text", "添加");
		mav.addObject("save_url", "/admin/user/add");
		mav.setViewName("/admin/page/user/add_update");
		return mav;
	}
	
	/**
	 * /houtai/user/edit?id=1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> map = null;
		List<Role> roleList = roleService.list(map, 0, 1000);
		mav.addObject("roleList", roleList);
		
		
		
		Manager manager = managerDao.findId(id);
		mav.addObject("user", manager);
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/manager/update?id=" + id);
		mav.setViewName("/admin/page/manager/add_update");
		return mav;
	}
	
	/**
	 * /houtai/user/set_new_pwd?id=1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/set_new_pwd")
	public ModelAndView set_new_pwd(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		Manager manager = managerDao.findId(id);
		mav.addObject("user", manager);
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/manager/set_new_pwd?id=" + id);
		mav.setViewName("/admin/page/manager/set_new_pwd");
		return mav;
	}

	@RequestMapping("/main")
	public ModelAndView f1(Model model){
		ModelAndView mav = new ModelAndView();
		List<String> list1= bookTypeService.findAll();
		model.addAttribute("typelist",list1);
		mav.setViewName("/pc/user/main");
		return mav;
	}

	@RequestMapping("/userinfo")
	public ModelAndView bookborrow() throws Exception {

		ModelAndView mav = new ModelAndView();

			mav.addObject("save_url", "/user/update" );
			mav.addObject("save_url1", "/user/update/password" );
			mav.setViewName("/pc/user/userinfo");
			return mav;
//		}

	}



}
