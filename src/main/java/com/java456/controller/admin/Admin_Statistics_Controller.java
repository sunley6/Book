package com.java456.controller.admin;


import com.java456.entity.Book;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/admin/book")
public class Admin_Statistics_Controller {

    @ResponseBody
    @RequestMapping("/statistice")
    public JSONObject add(@Valid Book book, BindingResult bindingResult, HttpServletResponse response,
                          HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();

        return result;
    }
}
