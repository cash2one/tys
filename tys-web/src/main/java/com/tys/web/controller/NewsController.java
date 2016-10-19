package com.tys.web.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tys.dto.query.ReqNewsDTO;
import com.tys.util.MUtil;

/**
 * 新闻资讯controller
 * 
 * @author wjc
 * @version 1.0
 * @since 2016-3-26 10:20:30
 */

@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class NewsController {
	@RequestMapping("/getinformations")
	public String getInformations() {
		return "news/informations";
	}

	@RequestMapping(value = "/addnews", method = RequestMethod.POST)
	public void addNews(@ModelAttribute ReqNewsDTO news) {
		String prjPath = MUtil.getPrjClassPath();
		String path = prjPath.substring(1, prjPath.length() - 16) + "news/html";
		path = path.replaceAll("/", "\\\\");
		String fileName = news.getTitle() + ".html";
		this.convert2html(path, fileName, news.getTitle(), news.getNews());
	}

	public void convert2html(String path, String fileName, String title, String html) {

		String meta = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />";
		String jquery = "<script src=\"http://localhost:8080/tysp-web/resources/plugins/jQuery/jQuery-2.1.4.min.js\"></script>";
		String function = "<script type=\"text/javascript\">" + "$(function(){" + "$('img').removeAttr(\"style\");"
				+ "$('img').css('display','block').css('width','100%').css('max-width','100%');" + "})" + "</script>";
		html = html.replace("<title></title>", "<title>" + title + "</title>" + meta + jquery + function);
		
		try {	
			InputStream is = new ByteArrayInputStream(html.getBytes());
			MUtil.saveFile(path+"\\"+fileName, is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
