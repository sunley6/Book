package com.java456.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.java456.entity.Manager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.java456.dao.ManagerDao;
import com.java456.service.ManagerService;
import com.java456.util.CryptographyUtil;
import com.java456.util.StringUtil;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/admin/manager")
public class Admin_Manager_Controller {
	
	@Resource
	private ManagerDao managerDao;
	 
	   @Resource 
	   private ManagerService managerService;
	  
	 /**
		 *   /admin/manager/add
		 */
		@ResponseBody
		@RequestMapping("/add")
		public JSONObject add(@Valid Manager manager, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) throws Exception {
			JSONObject result = new JSONObject();
			
			if(bindingResult.hasErrors()){
				result.put("success", false);
				result.put("msg", bindingResult.getFieldError().getDefaultMessage());
				return result;
			}else{
				manager.setCreateDateTime(new Date());
				manager.setPwd(CryptographyUtil.md5(manager.getPwd(), "java"));
				managerDao.save(manager);
				result.put("success", true);
				result.put("msg", "添加成功");
				return result;
			}
		}
		
		
		

		/**
		 * /admin/manager/update
		 */
		@ResponseBody
		@RequestMapping("/update")
		public JSONObject update(@Valid Manager manager, BindingResult bindingResult, HttpServletRequest request)throws Exception {
			JSONObject result = new JSONObject();
			
			if(bindingResult.hasErrors()){
				result.put("success", false);
				result.put("msg", bindingResult.getFieldError().getDefaultMessage());
				return result;
			}else{
				manager.setUpdateDateTime(new Date());
				managerService.update(manager);
				result.put("success", true);
				result.put("msg", "修改成功");
				return result;
			}
		}
		
		
		/**
		 * /admin/manager/set_new_pwd
		 */
		@ResponseBody
		@RequestMapping("/set_new_pwd")
		public JSONObject set_new_pwd(Manager manager, HttpServletRequest request)throws Exception {
			JSONObject result = new JSONObject();
			
			manager.setUpdateDateTime(new Date());
			if(StringUtil.isNotEmpty(manager.getPwd())){
				manager.setPwd(CryptographyUtil.md5(manager.getPwd(),"java"));
			}
			managerService.update(manager);
			result.put("success", true);
			result.put("msg", "修改成功");
			return result;
		}
		
		
		/**
		 * /admin/user/list
		 * @param page    默认1
		 * @param limit   数据多少
		 */
		@ResponseBody
		@RequestMapping("/list")
		public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
				@RequestParam(value = "limit", required = false) Integer limit, 
				HttpServletResponse response,
				HttpServletRequest request) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			
			List<Manager> managerList = managerService.list(map, page-1, limit);
			long total = managerService.getTotal(map);
			map.put("data", managerList);
			map.put("count", total);
			map.put("code", 0);
			map.put("msg", "");
			return map;
		}
		
		/**
		 * /admin/user/delete
		 */
		@ResponseBody
		@RequestMapping("/delete")
		public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
				throws Exception {
			String[] idsStr = ids.split(",");
			JSONObject result = new JSONObject();

			for (int i = 0; i < idsStr.length; i++) {
				managerDao.deleteById(Integer.parseInt(idsStr[i]));
			}
			result.put("success", true);
			return result;
		}
		
		
	
}
