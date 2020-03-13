package com.java456.controller.houtai;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/houtai/upload_one")
public class HouTai_Upload_Controller {
    @Value("${file.uploadFolder}")
    private String uploadPath;
@ResponseBody
@RequestMapping(value = "/upload/img", method = RequestMethod.POST)
public Map<String, Object> upload(HttpServletRequest servletRequest, @RequestParam("file") MultipartFile file)
        throws IOException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
    String res = sdf.format(new Date());
    String UploadPath = uploadPath;
    String originalFilename = file.getOriginalFilename();
    //新的文件名称
    String newFileName = res+originalFilename.substring(originalFilename.lastIndexOf("."));
    //创建年月文件夹
    Calendar date = Calendar.getInstance();
    File dateDirs = new File(date.get(Calendar.YEAR)
            + File.separator + (date.get(Calendar.MONTH)+1));
    //新文件
    File newFile = new File(UploadPath+File.separator+dateDirs+File.separator+newFileName);
    //判断目标文件所在的目录是否存在
    if(!newFile.getParentFile().exists()) {
        //如果目标文件所在的目录不存在，则创建父目录
        newFile.getParentFile().mkdirs();
    }
    System.out.println(newFile);
    //将内存中的数据写入磁盘
    file.transferTo(newFile);
    // 如果文件内容不为空，则写入上传路径
    System.out.println( "name"+ file.getOriginalFilename());
    String fileUrl =  "/upload/"+date.get(Calendar.YEAR)+ "/"+(date.get(Calendar.MONTH)+1)+ "/"+ newFileName;
    Map<String, Object> map = new HashMap<>();
    // 返回的是一个url对象
    System.out.println(fileUrl+"url");
    map.put("url", fileUrl);
    map.put("code",200);
    System.out.println(res);
    return map;
//    if (!file.isEmpty()) {
//        // 上传文件路径
//        System.out.println(uploadPath);
//        String UploadPath = uploadPath;
//        // 上传文件名
//        String filename = file.getOriginalFilename();
//        File filepath = new File(UploadPath, filename);
//        // 判断路径是否存在,没有创建
//        if (!filepath.getParentFile().exists()) {
//            filepath.getParentFile().mkdirs();
//        }
//        // 将上传文件保存到一个目标文档中
//        File file1 = new File(UploadPath + File.separator + filename);
//        file.transferTo(file1);
//        Map<String, Object> res = new HashMap<>();
//        // 返回的是一个url对象
//        res.put("url", file1);
//        res.put("code",200);
//        System.out.println(res);
//        return res;
//    } else {
//        return null;
//    }
}
}
