package com.java456.controller.admin;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.java456.dao.BookBorrowDao;
import com.java456.dao.BookTypeDao;
import com.java456.entity.BookBorrow;
import com.java456.entity.User;
import com.java456.service.BookBorrowService;
import com.java456.service.BookTypeService;
import com.java456.util.GetDate;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java456.dao.BookDao;
import com.java456.entity.Book;
import com.java456.entity.BookType;
import com.java456.service.BookService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/book")
public class Admin_Book_Controoler {

	@Resource
	private BookDao bookDao;

	@Resource
	private BookBorrowDao bookBorrowDao;
	@Resource
	private BookService bookService;
	@Resource
	private BookBorrowService bookBorrowService;

	@Resource
	private BookTypeService bookTypeService;

	/**
	 * /admin/book/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Book book, BindingResult bindingResult, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();

		System.out.println(book.toString());
		if (bindingResult.hasErrors()) {
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		} else {
			Date date=new Date();
			book.setCreateDateTime(date);
			System.out.println("book="+book.toString());
			bookDao.save(book);
			result.put("success", true);
			result.put("msg", "添加成功");
			return result;
		}
	}
	

	/**
	 *  /admin/book/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update(@Valid  Book book  ,BindingResult bindingResult, HttpServletRequest request)throws Exception {
		JSONObject result = new JSONObject();
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			book.setUpdateDateTime(new Date());
			bookService.update(book);
			result.put("success", true);
			result.put("msg", "修改成功");
			return result;
		}
	}
	

	/**
	 * /admin/book/list
	 * @param page    默认1
	 * @param limit   数据多少
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(@RequestParam(required = false,defaultValue = "0") String type,
									@RequestParam(required = false,defaultValue = "") String content,
									@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			Model model,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("type="+type+"content="+content+"page="+page+"limit="+limit);
		BookType bookType=bookTypeService.findByName(type);
		List<Book> list = new ArrayList<>();
		if (bookType!=null){
			map.put("bookType",bookType.getId());
			list = bookService.list(map, page-1, limit);

			System.out.println(1111);
		}else if (!"".equals(content)){
			map.put("q",content);
			list = bookService.list(map, page-1, limit);
			System.out.println(2222);
		}
		else {
			list = bookService.list(map, page-1, limit);
			System.out.println(3333);
		}
		System.out.println("page="+list);
        System.out.println("page="+page);

		long total = bookService.getTotal(map);
		System.out.println("size"+list.size()+"      "+total);
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");

		return map;
	}


	/**
	 * /admin/book/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
			throws Exception {
		System.out.println(ids);
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();


		for (int i = 0; i < idsStr.length; i++) {
			bookDao.deleteById(Integer.parseInt(idsStr[i]));
		}
		result.put("success", true);
		return result;
	}

	@ResponseBody
	@RequestMapping( value = "/bookborrow",method = {RequestMethod.POST})
	public JSONObject bookborrow(@Valid BookBorrow bookBorrow, BindingResult bindingResult,
								 HttpServletResponse response,
								 @RequestParam(value = "datenum", required = false) Integer datenum,
								 HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		System.out.println("888888888888888"+bookBorrow.toString());
		System.out.println("nsduhfudf"+datenum);
		GetDate getDate=new GetDate();
		Date date=getDate.getlastdate(datenum);
		bookBorrow.setState(1);
		User currentUser = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		if (currentUser==null){
			result.put("success", false);
			result.put("msg", "请先登录");
			return result;
		}else {
			bookBorrow.setBorrowCreateDateTime(new Date());
			bookBorrow.setBorrowLastDateTime(date);
			bookBorrowDao.save(bookBorrow);
			result.put("success", true);
			result.put("msg", "添加成功");
			return result;
		}

	}

	@ResponseBody
	@RequestMapping("/myborrow")
	public Map<String, Object> myborrow(@RequestParam(required = false,defaultValue = "0") String type,
									@RequestParam(required = false,defaultValue = "") String content,
									@RequestParam(value = "page", required = false) Integer page,
									@RequestParam(value = "limit", required = false) Integer limit,
									Model model,
									HttpServletResponse response,
									HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BookBorrow> list = new ArrayList<>();
		User currentUser = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
//		if (!"0".equals(type)){
//			list =bookBorrowService.findByUid(content);
//			System.out.println(1111);
//		}else
		if (currentUser==null){

		}
		else {
			map.put("userId",currentUser.getId());
			list = bookBorrowService.list(map, page-1, limit);
			System.out.println(3333);
		}
		System.out.println("page="+list);
		System.out.println("page="+page);

		long total = bookBorrowService.getTotal(map);
		System.out.println("size"+map.size()+"      "+total);
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");

		return map;
	}
	
	
	
}
