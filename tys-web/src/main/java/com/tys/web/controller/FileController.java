package com.tys.web.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.tys.dto.query.DataTableReqDTO;
import com.tys.dto.query.DataTableRspDTO;
import com.tys.dto.query.ReqFileDTO;
import com.tys.entity.MdFile;
import com.tys.service.MdFileSerivce;
import com.tys.util.MUtil;
import com.tys.util.custom.MPage;
import com.tys.web.utils.ResultInfo;

@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class FileController {

	@Resource
	private MdFileSerivce fileService;

	@RequestMapping(value = "/getfiles", method = RequestMethod.GET)
	public ModelAndView getFiles() {
		ModelAndView mv = new ModelAndView("resources/files");
		return mv;
	}

	@RequestMapping(value = "/getfilelist", method = RequestMethod.GET)
	public void getFileList(HttpServletResponse response, @ModelAttribute DataTableReqDTO req,
			@ModelAttribute ReqFileDTO file) {
		DataTableRspDTO<MdFile> rsp = new DataTableRspDTO<MdFile>();
		MPage<MdFile> page = fileService.findFileList(req.getStart() / req.getLength() + 1, req.getLength(),
				file.getName(), file.getType() == null ? -1 : file.getType());
		rsp.setDraw(req.getDraw());
		rsp.setData(page.getResult());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		MUtil.outPrint(response, JSONObject.toJSONString(rsp).toString());
	}

	// 上传文件
	@RequestMapping(value = "/mergefile", method = RequestMethod.POST)
	@ResponseBody
	public ResultInfo mergeFile(HttpServletRequest request) throws UnsupportedEncodingException {
		ResultInfo rt = new ResultInfo(true);
		
		String id = request.getParameter("id");
		System.out.println("id"+id);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("upload");
		File temp = this.convertFile(file);

		String tempPath = new String(temp.getAbsolutePath().getBytes(),"utf-8");
		String ret="";
		if(id!=null){
			//修改文件
			ret = MUtil.postFileByUrl("http://localhost:8888/tysp-res/file/upload",tempPath,id);			
		}else{
			//添加文件
			ret = MUtil.postFileByUrl("http://localhost:8888/tysp-res/file/upload",tempPath);			
		}
		
		rt.setMessage(ret);
		return rt;
	}
	
	@RequestMapping("/removefile")
	@ResponseBody
	public String removeFile(@RequestParam(required=true) Integer id){
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setCharset(Charset.forName("UTF-8"));
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
		builder.addTextBody("id", id+"");
		HttpEntity entity = builder.build();
		 
		return MUtil.postStringByUrl("http://localhost:8888/tysp-res/file/remove", entity);
	}

	private File convertFile(MultipartFile file) {
		File targetFile = new File("C:\\temps\\", file.getOriginalFilename());

		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		try {
			file.transferTo(targetFile);
			return targetFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
