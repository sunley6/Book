package com.java456.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java456.dao.MenuDao;
import com.java456.dao.RoleDao;
import com.java456.dao.RoleMenuDao;
import com.java456.entity.Menu;
import com.java456.entity.Role;
import com.java456.entity.RoleMenu;
import com.java456.service.MenuService;
import com.java456.service.RoleService;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/admin/role")
public class Admin_Role_Controller {

	@Resource
	private RoleService  roleService;
	@Resource
	private RoleDao roleDao;
	@Resource
	private MenuDao menuDao;
	@Resource
	private MenuService menuService;
	@Resource
	private RoleMenuDao roleMenuDao;
	/**
	 * /admin/role/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Role role,BindingResult bindingResult
			, HttpServletResponse response, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		role.setCreateDateTime(new Date());
		
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			roleDao.save(role);
			result.put("success", true);
			result.put("msg", "添加成功");
			return result;
		}
	}
	
	/**
	 * /admin/role/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update(@Valid Role role,BindingResult bindingResult)throws Exception {
		JSONObject result = new JSONObject();
		
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			role.setUpdateDateTime(new Date());
			roleService.update(role);
			result.put("success", true);
			result.put("msg", "修改成功");
			return result;
		}
	}
	
	/**
	 * /admin/role/list
	 * @param page    默认1(layui传来的)
	 * @param limit   数据多少（每页数据）
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit, 
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Role> list = roleService.list(map, page-1, limit);
		long total = roleService.getTotal(map);
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	
	/**
	 * /admin/role/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
			throws Exception {
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			try {
				roleDao.deleteById(Integer.parseInt(idsStr[i]));
			} catch (Exception e) {
				e.printStackTrace();
				result.put("success", false);
				result.put("msg", "有用户正在使此角色");
				return result;
			}
		}
		result.put("success", true);
		return result;
	}
	
	
	/**
	 * 这个接口是 layui tree组件用  用的。授权 的时候 用的，点对勾用
	 * /admin/role/getCheckedMenuData?roleId=12
	 */
	@ResponseBody
	@RequestMapping("/getCheckedMenuData")
	public List<JSONObject>  getCheckedMenuData(@RequestParam(value = "roleId", required = false) Integer roleId, HttpServletResponse response)
			throws Exception {
		
		List<JSONObject>  list =  new ArrayList<JSONObject>();
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pId", -1);
		List<Menu> menuList = menuService.list(map , 0, 100);
		
		for (Menu menu : menuList) {
			JSONObject node = new JSONObject();
			node.put("id", menu.getId());
			node.put("title", menu.getName());
			node.put("spread", true);
			
			node.put("children", getChildren(menu.getId(),roleId));
			list.add(node);
		}
		return list;
	}
	
	/**
	 * 辅助方法  辅助 上面的 admin_main（getCheckedMenuData）
	 * @param pId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getChildren(Integer pId, Integer roleId)  throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pId", pId);
		List<Menu> menuList = menuService.list(map , 0, 100);
		
		List<JSONObject>  list =  new ArrayList<JSONObject>();
		for (Menu menu : menuList) {
				JSONObject node = new JSONObject();
				node.put("id", menu.getId());
				node.put("title", menu.getName());
				
				//判断 当前菜单     当前角色有没有拥有。
				RoleMenu roleMenu=	roleMenuDao.findByRoleIdAndMenuId(roleId, menu.getId());
				if(roleMenu!=null){
					node.put("checked", true);
				}
				//判断 当前菜单     当前角色有没有拥有。
				
				list.add(node);
		}
		return list;
	}
	
	
	
	/**
	 * @param url    /admin/role/updateMenu
	 * @param roleId  =  12
	 * @param menuIds  1,2,5,6,8,5
	 */
	@ResponseBody
	@RequestMapping("/updateMenu")
	public JSONObject updateMenu(@RequestParam(value = "roleId", required = false) Integer roleId,
			@RequestParam(value = "menuIds", required = false) String menuIds)throws Exception {
		JSONObject result = new JSONObject();
		
		roleService.updateMenu(roleId, menuIds);
		
		result.put("success", true);
		result.put("msg", "修改成功");
		return result;
		
	}
	
	
	
	
	
}
