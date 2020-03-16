package com.java456.controller.houtai;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.java456.dao.BookBorrowDao;
import com.java456.dao.UserDao;
import com.java456.entity.BookBorrow;
import com.java456.entity.User;
import com.java456.service.BookTypeService;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.java456.dao.BookDao;
import com.java456.dao.BookTypeDao;
import com.java456.entity.Book;
import com.java456.entity.BookType;
import com.java456.service.BookService;

@Controller
@RequestMapping("/houtai/book")
public class HouTai_Book_Controller {
	

	@Resource
	private BookDao bookDao;

	@Resource
	private BookBorrowDao bookBorrowDao;

	@Resource
	private UserDao userDao;
	@Resource
	private BookService bookService;
	@Resource
	private BookTypeDao bookTypeDao;

	@Resource
	private BookTypeService bookTypeService;
	
	
	/**
	 * /houtai/book/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage(Model model) throws Exception {
		ModelAndView mav = new ModelAndView();

		List<String> list1= bookTypeService.findAll();

		model.addAttribute("typelist",list1);
		mav.addObject("title", "图书管理");
		mav.setViewName("/admin/page/book/book_manage");
		return mav;
	}

	@RequestMapping("/borrow")
	public ModelAndView borrow(Model model) throws Exception {
		ModelAndView mav = new ModelAndView();

		List<String> list1= bookTypeService.findAll();

		model.addAttribute("typelist",list1);

		mav.addObject("title", "图书管理");
		mav.setViewName("/admin/page/book/borrowlist");
		return mav;
	}


	/**
	 * /houtai/book/add
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	public ModelAndView add() throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<BookType> list = bookTypeDao.findAll(pageable);
		List<BookType> bookTypeList = list.getContent();//拿到list集合
		mav.addObject("bookTypeList", bookTypeList);
		System.out.println(pageable+"11"+list+"33"+bookTypeList);
		
		
		mav.addObject("btn_text", "添加");
		mav.addObject("save_url", "/admin/book/add");
		mav.setViewName("/admin/page/book/add_update");
		return mav;
	}
	
	
	/**
	 * /houtai/book/edit?id=1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<BookType> list = bookTypeDao.findAll(pageable);
		List<BookType> bookTypeList = list.getContent();//拿到list集合
		mav.addObject("bookTypeList", bookTypeList);
		Date date=new Date();
		Book book = bookDao.findId(id);
		mav.addObject("book", book);
		System.out.println("book="+book.getImageUrl());
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/book/update?id=" + id);
		mav.setViewName("/admin/page/book/add_update");
		return mav;
	}

	@RequestMapping("/booklook")
	public ModelAndView booklook(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();

		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<BookType> list = bookTypeDao.findAll(pageable);
		List<BookType> bookTypeList = list.getContent();//拿到list集合
		mav.addObject("bookTypeList", bookTypeList);
		Date date=new Date();
		Book book = bookDao.findId(id);
		mav.addObject("book", book);

//		mav.addObject("btn_text", "修改");
//		mav.addObject("save_url", "/admin/book/update?id=" + id);
		mav.setViewName("/pc/member/book_look");
		return mav;
	}

	@RequestMapping("testborrow")
	public ModelAndView f1(Model model,@Valid BookBorrow bookBorrow){
		ModelAndView mav = new ModelAndView();


		System.out.println("bookborrow"+bookBorrow);
		bookBorrowDao.save(bookBorrow);
		return mav;
	}

	@RequestMapping("/bookborrow")
	public ModelAndView bookborrow(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		System.out.println("id="+id);
		ModelAndView mav = new ModelAndView();
		Book book = bookDao.findId(id);
		System.out.println("bprrow="+book);
		BookType bookType=book.getBookType();
		System.out.println("bookType="+bookType.getName());
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		mav.addObject("id", id);
		mav.addObject("bookType", bookType);
		mav.addObject("book", book);
		mav.addObject("btn_text", "添加");
		mav.addObject("save_url", "/admin/book/bookborrow");
		mav.setViewName("/pc/member/book_borrow");
		return mav;
	}


	@RequestMapping("/preborrow")
	public ModelAndView preborrow(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		System.out.println("id="+id);
		ModelAndView mav = new ModelAndView();
		Book book = bookDao.findId(id);
		BookType bookType=book.getBookType();
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		mav.addObject("id", id);
		mav.addObject("bookType", bookType);
		mav.addObject("book", book);
		mav.addObject("btn_text", "添加");
		mav.addObject("save_url", "/admin/book/preborrow");
		if (currentUser==null){
			mav.setViewName("/common/nologin");
			return mav;
		}
		else
		{
			mav.setViewName("/pc/member/book_preborrow");
			return mav;
		}
	}


	@RequestMapping("/bookcontinue")
	public ModelAndView bookcontinue(@RequestParam(value = "id", required = false) Integer id) throws Exception {

		ModelAndView mav = new ModelAndView();
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		mav.addObject("id",id);
		mav.addObject("btn_text", "续借");
		mav.addObject("save_url", "/admin/book/bookcontinue");

		if (currentUser==null){
			mav.setViewName("/common/nologin");
			return mav;
		}
		else
		{
			mav.setViewName("/pc/member/book_continue_borrow");
			return mav;
		}

	}


	@RequestMapping("searchmyborrow")
	public ModelAndView searchmyborrow(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		System.out.println("id="+id);
		ModelAndView mav = new ModelAndView();
		List<BookBorrow> list = bookBorrowDao.findByUId(id);
		System.out.println("mybprrow="+list.size());
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");

		mav.addObject("btn_text", "添加");
		mav.addObject("save_url", "/admin/book/bookborrow");

		if (currentUser==null){
			mav.setViewName("/common/nologin");
			return mav;
		}
		else
		{
			mav.setViewName("/pc/member/book_borrow");
			return mav;
		}

	}


	
}
