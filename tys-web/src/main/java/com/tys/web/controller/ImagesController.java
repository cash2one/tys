package com.tys.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 图片上传action
 * @author wjc
 *
 */
@Controller
@RequestMapping("/res")
public class ImagesController {

    @RequestMapping("/ckeditorUpload")
	public String uploadImage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
		
	//  获得第1张图片（根据前台的name名称得到上传的文件）   
        MultipartFile imgFile1  =  multipartRequest.getFile("upload"); 
        
        String fileName = imgFile1.getOriginalFilename();  
        //获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名  
         String ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());  
         //对扩展名进行小写转换  
         ext = ext.toLowerCase();
		
		// CKEditor提交的很重要的一个参数  
        String callback = request.getParameter("CKEditorFuncNum");  
        String expandedName = ext; // 文件扩展名  

        if (ext.equals("jpg")) {  
            // IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg  
            expandedName = ".jpg";  
        } else if (ext.equals("png")) {  
            // IE6上传的png图片的headimageContentType是"image/x-png"  
            expandedName = ".png";  
        } else if (ext.equals("gif")) {  
            expandedName = "gif";  
        } else if (ext.equals("bmp")) {  
            expandedName = ".bmp";  
        } else {  
            out.println("<script type=\"text/javascript\">");  
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback  
                    + ",''," + "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");  
            out.println("</script>");  
            return null;  
        }  
        if (imgFile1.getSize() > 1024 * 1024) {  
            out.println("<script type=\"text/javascript\">");  
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback  
                    + ",''," + "'文件大小不得大于1Mb');");  
            out.println("</script>");  
            return null;  
        }  
  
        //图片上传路径  

        String uploadPath = request.getSession().getServletContext().getRealPath("/img/uploadImg");
 
        File targetFile = new File(uploadPath, fileName);
        
        if(!targetFile.exists()){
        	targetFile.mkdirs();
        }
        
        imgFile1.transferTo(targetFile);
        
        // 返回"图像"选项卡并显示图片  request.getContextPath()为web项目名   
        out.println("<script type=\"text/javascript\">");  
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback  
                + ",'" + request.getContextPath() + "/img/uploadImg/" + fileName + "','')");  
        out.println("</script>");
        
		return null;
	}
}
