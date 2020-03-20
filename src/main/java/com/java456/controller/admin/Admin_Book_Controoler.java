package com.java456.controller.admin;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.java456.dao.*;
import com.java456.entity.*;
import com.java456.service.BookBorrowService;
import com.java456.service.BookTypeService;

import com.java456.service.StatisticsService;
import com.java456.util.GetDate;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
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

import com.java456.service.BookService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/book")
public class Admin_Book_Controoler {

	@Resource
	private BookDao bookDao;

	@Resource
	private UserDao userDao;

	@Resource
	private BookBorrowDao bookBorrowDao;
	@Resource
	private BookService bookService;
	@Resource
	private BookBorrowService bookBorrowService;

	@Resource
	private StatisticsService statisticsService;

	@Resource
	private BookTypeService bookTypeService;

	@Resource
	private StatisticsDao statisticsDao;

	/**
	 * /admin/book/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Book book, @Valid Statistics statistics, BindingResult bindingResult, HttpServletResponse response,
						  HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		System.out.println("statis"+statistics.toString());
		System.out.println(book.toString());
		if (bindingResult.hasErrors()) {
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		} else {
			Date date=new Date();
			book.setCreateDateTime(date);
			book.setState(1);
			System.out.println("book="+book.toString());
			bookDao.save(book);
			statistics.setState(1);
			statistics.setCreateDateTime(new Date());
			statisticsDao.save(statistics);
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

	@ResponseBody
	@RequestMapping("/total")
	public Map<String, Object> total(@RequestParam(required = false,defaultValue = "0") String type,
									@RequestParam(required = false,defaultValue = "") String content,
									@RequestParam(value = "page", required = false) Integer page,
									@RequestParam(value = "limit", required = false) Integer limit,
									Model model,
									HttpServletResponse response,
									HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("type="+type+"content="+content+"page="+page+"limit="+limit);
//		BookType bookType=bookTypeService.findByName(type);
		List<Statistics> list = new ArrayList<>();
//		if (bookType!=null){
//			map.put("bookType",bookType.getId());
//			list = bookService.list(map, page-1, limit);
//
//			System.out.println(1111);
//		}else if (!"".equals(content)){
//			map.put("q",content);
//			list = bookService.list(map, page-1, limit);
//			System.out.println(2222);
//		}
//		else {
			list = statisticsService.list(map, page-1, limit);
			System.out.println(3333);
//		}
		System.out.println("page="+list);
		System.out.println("page="+page);

		long total = statisticsService.getTotal(map);
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

			Book book=bookDao.findId(Integer.parseInt(idsStr[i]));
			Statistics statistics=statisticsDao.findId(book.getBianhao());
			statistics.setState1(1);
			statistics.setUpdateDateTime(new Date());
			System.out.println(statistics);
			statisticsDao.save(statistics);
			bookDao.deleteById(Integer.parseInt(idsStr[i]));

		}
		result.put("success", true);
		return result;
	}

	@ResponseBody
	@RequestMapping( value = "/bookborrow",method = {RequestMethod.POST})
	public JSONObject bookborrow(
								 @RequestParam(value = "id", required = false) Integer id,
								 @RequestParam(value = "userPhone", required = false) String userPhone,
								 @RequestParam(value = "datenum", required = false) Integer datenum,
								 HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();

		GetDate getDate=new GetDate();
		Date date=getDate.getlastdate(datenum);
		Book book=bookDao.findId(id);
		User user=userDao.findByPhone(userPhone);
		System.out.println(user);
		BookBorrow bookBorrow=new BookBorrow(user,book,new Date(),date,1);
		BookBorrow bookBorrow1=bookBorrowDao.findIdUid(id,user.getId(),5);
//		if (currentUser==null){
//			result.put("success", false);
//			result.put("msg", "请先登录");
//			return result;
//		}else
			if (book.getNum()<1){
			result.put("success", false);
			result.put("msg", "数量不足");
			return result;
		}
		else if (bookBorrow1!=null){
			result.put("success", false);
			result.put("msg", "书籍已借阅");
			return result;
		}else if (user==null){
				result.put("success", false);
				result.put("msg", "该用户不存在");
				return result;
			}
		else if (user.getCredit()<70){
			result.put("success", false);
			result.put("msg", "信用分不足");
			return result;
		}
		else {
				System.out.println("borrowfenjsanu+"+user.getCredit());
			book.setNum(book.getNum()-1);
			bookService.update(book);
			bookBorrowDao.save(bookBorrow);
			result.put("success", true);
			result.put("msg", "添加成功");
			return result;
		}

	}

	@ResponseBody
	@RequestMapping( value = "/preborrow",method = {RequestMethod.POST})
	public JSONObject preborrow(
			@Valid  BookBorrow bookBorrow,
			@RequestParam(value = "datenum", required = false) Integer datenum,
			HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
        System.out.println(bookBorrow);
		GetDate getDate=new GetDate();
		Date date=getDate.getlastdate(datenum);
		Book book=bookDao.findId(bookBorrow.getBookId());
		User currentUser = userDao.findId(bookBorrow.getuId().getId());
		BookBorrow bookBorrow1=bookBorrowDao.findIdUid(bookBorrow.getBookId(),currentUser.getId(),5);
		List<BookBorrow> alllist = new ArrayList<>();
		alllist=bookBorrowDao.findByUId(bookBorrow.getuId().getId());

		if (currentUser==null){
			result.put("success", false);
			result.put("msg", "请先登录");
			return result;
		}else
		if (book.getNum()<1){
			result.put("success", false);
			result.put("msg", "数量不足");
			return result;
		}else if (getDate.overflag(alllist)==0){
			result.put("success", false);
			result.put("msg", "有过期图书为归还");
			return result;
		}
		else if (bookBorrow1!=null){
			System.out.println(bookBorrow1);
			result.put("success", false);
			result.put("msg", "书籍已借阅");
			return result;
		}
		else if (currentUser.getCredit()<70){
			result.put("success", false);
			result.put("msg", "信用分不足");
			return result;
		}
		else {
//			book.setNum(book.getNum()-1);
//			bookService.update(book);
			System.out.println("分“+”"+currentUser.getCredit());
			bookBorrow.setState(2);
			bookBorrow.setBorrowCreateDateTime(new Date());
			bookBorrow.setBorrowLastDateTime(date);
            System.out.println(bookBorrow);
			bookBorrowDao.save(bookBorrow);
			result.put("success", true);
			result.put("msg", "添加成功");
			return result;
		}

	}



	@ResponseBody
	@RequestMapping( value = "/bookcontinue",method = {RequestMethod.POST})
	public JSONObject bookcontinue( @RequestParam(value = "id", required = false) Integer id,
								 @RequestParam(value = "datenum", required = false) Integer datenum,
								 HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		System.out.println("333"+id+"3344"+datenum);
		BookBorrow bookBorrow=bookBorrowDao.findId1(id);
		System.out.println(bookBorrow);

		GetDate getDate=new GetDate();
		Date date=getDate.getcontinuelastdate(bookBorrow.getBorrowLastDateTime(),datenum);
		bookBorrow.setState(6);
		User currentUser = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");

		if (currentUser==null){
			result.put("success", false);
			result.put("msg", "请先登录");
			return result;
		}
		else if (currentUser.getCredit()<70){
			result.put("success", false);
			result.put("msg", "信用分不足");
			return result;
		}
		else {

			bookBorrow.setBorrowLastDateTime(date);
			System.out.println(bookBorrow);
			bookBorrowService.update(bookBorrow);
			result.put("success", true);
			result.put("msg", "添加成功");
			return result;
		}

	}

	@ResponseBody
	@RequestMapping( value = "/check_preborrow",method = {RequestMethod.POST})
	public JSONObject check_preborrow( @RequestParam(value = "id", required = false) Integer id,
									HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();

		BookBorrow bookBorrow=bookBorrowDao.findId1(id);
		System.out.println(bookBorrow);
		bookBorrow.setState(3);
		User currentUser = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");

		if (currentUser==null){
			result.put("success", false);
			result.put("msg", "请先登录");
			return result;
		}
		else if (currentUser.getCredit()<70){
			result.put("success", false);
			result.put("msg", "信用分不足");
			return result;
		}
		else {

			bookBorrow.setBorrowCreateDateTime(new Date());
			System.out.println(bookBorrow);
			bookBorrowService.update(bookBorrow);
			result.put("success", true);
			result.put("msg", "审核通过");
			return result;
		}

	}

	@ResponseBody
	@RequestMapping( value = "/sure_preborrow",method = {RequestMethod.POST})
	public JSONObject sure_preborrow( @RequestParam(value = "id", required = false) Integer id,
									   HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		GetDate getDate=new GetDate();

		BookBorrow bookBorrow=bookBorrowDao.findId1(id);
		int datemu=getDate.differentDaysByMillisecond(bookBorrow.getBorrowCreateDateTime(),bookBorrow.getBorrowLastDateTime());
		Date date=getDate.getcontinuelastdate(new Date(),datemu+1);
		System.out.println(bookBorrow);
		bookBorrow.setState(1);
		User currentUser = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");

//		if (currentUser==null){
//			result.put("success", false);
//			result.put("msg", "请先登录");
//			return result;
//		}
//		else
			if (currentUser.getCredit()<70){
			result.put("success", false);
			result.put("msg", "信用分不足");
			return result;
		}
		else {

			bookBorrow.setBorrowCreateDateTime(new Date());
			bookBorrow.setBorrowLastDateTime(date);
			System.out.println(bookBorrow);
			bookBorrowService.update(bookBorrow);
			result.put("success", true);
			result.put("msg", "借阅成功");
			return result;
		}

	}

	@ResponseBody
	@RequestMapping( value = "/bookreturn",method = {RequestMethod.POST})
	public JSONObject bookreturn( @RequestParam(value = "id", required = false) Integer id,

									HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();

		BookBorrow bookBorrow=bookBorrowDao.findId1(id);
		bookBorrow.setState(5);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		List<BookBorrow> list = new ArrayList<>();
		list=bookBorrowDao.findByUId(bookBorrow.getuId().getId());
		System.out.println(list.size());
		GetDate getDate=new GetDate();
		getDate.compDate(sd.format(bookBorrow.getBorrowLastDateTime()));
		int overDay=getDate.compDate(sd.format(bookBorrow.getBorrowLastDateTime()));
			User user=userDao.findId(bookBorrow.getuId().getId());
			if (overDay<=0){

			}else if (overDay<4){
				user.setCredit(user.getCredit()-overDay*10);
			}else{
				user.setCredit(user.getCredit()-40);
			}
			bookBorrowService.update(bookBorrow);
			Book book=bookDao.findId(bookBorrow.getBookId());
			book.setNum(book.getNum()+1);
			bookService.update(book);
			result.put("success", true);
			result.put("msg", "归还成功");
			return result;


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
		if (currentUser==null){

		}
		else {
				map.put("userId",currentUser.getId());
				if ("0".equals(type))
				{
					list = bookBorrowService.list(map, page - 1, limit);
					System.out.println(66);
				}else {
					map.put("bookType",type );
					list = bookBorrowService.list(map, page - 1, limit);
					System.out.println(3333);
				}



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

	@ResponseBody
	@RequestMapping("/allborrow")
	public Map<String, Object> allborrow(
										@RequestParam(value = "page", required = false) Integer page,
										@RequestParam(value = "limit", required = false) Integer limit,
										Model model,
										HttpServletResponse response,
										HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BookBorrow> list = new ArrayList<>();
		list = bookBorrowService.list(map, page-1, limit);

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
