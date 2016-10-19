package com.tys.web.controller;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Date;

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

import com.alibaba.fastjson.JSONObject;
import com.tys.dto.query.DataTableReqDTO;
import com.tys.dto.query.DataTableRspDTO;
import com.tys.dto.query.ReqSoftwareDTO;
import com.tys.entity.MdFile;
import com.tys.entity.MdSoftware;
import com.tys.service.MdFileSerivce;
import com.tys.service.MdSoftwareService;
import com.tys.util.MUtil;
import com.tys.util.custom.MPage;

@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class SoftwareController {

	@Resource
	private MdSoftwareService softwareService;
	
	@Resource
	private MdFileSerivce fileService;

	@RequestMapping("/getsoftware")
	public String getSoftware() {
		return "resources/software";
	}

	@RequestMapping(value = "/getsoftwarelist", method = RequestMethod.GET)
	public void getSoftwareList(HttpServletResponse response, @ModelAttribute DataTableReqDTO req,
			@RequestParam(value = "alias", required = false) String alias,
			@RequestParam(value = "type", required = false) Integer type) {
		DataTableRspDTO<MdSoftware> rsp = new DataTableRspDTO<MdSoftware>();
		MPage<MdSoftware> page = softwareService.findSoftwareList(req.getStart() / req.getLength() + 1, req.getLength(),
				alias, type == null ? -1 : type);
		rsp.setDraw(req.getDraw());
		rsp.setData(page.getResult());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		MUtil.outPrint(response, JSONObject.toJSONString(rsp).toString());
	}

	@RequestMapping(value = "/mergesoftware", method = RequestMethod.POST)
	@ResponseBody
	public Object mergeSofeware(HttpServletRequest request, @ModelAttribute ReqSoftwareDTO software) {
		if (software.getId() != null) {
			//更新
			MdSoftware ms = softwareService.findById(software.getId());
			if(software.getSource()==0){
				//上传本地文件
				if (software.getUpload() != null) {
					File file = convertFile(software.getUpload());				
					int id = softwareService.findById(software.getId()).getFile().getId();
					MUtil.postFileByUrl("http://localhost:8888/tysp-res/file/upload", file.getAbsolutePath(),id+"");
					MdFile mf = fileService.findById(id);
					
					ms.setFileId(id);
					ms.setUrl(mf.getUrl());
				}
			}else{
				//第三方链接 
				ms.setUrl(software.getTps());
				//如果是由本地切换到第三方链接 要删掉资源服务器上的文件
				if(ms.getFileId()!=null){			
					int fileId = ms.getFile().getId();
					
					MultipartEntityBuilder builder = MultipartEntityBuilder.create();
					builder.setCharset(Charset.forName("UTF-8"));
					builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
					builder.addTextBody("id", fileId+"");
					HttpEntity entity = builder.build();
					
					MUtil.postStringByUrl("http://localhost:8888/tysp-res/file/remove", entity);
					
					ms.setFileId(null);
				}
			}
			ms.setAlias(software.getAlias());
			ms.setCompanyId(software.getCompanyId());
			ms.setType(software.getType());
			ms.setDescription(software.getDescription());
			
			//把ABC.DEF.GHI转换为ABCDEFGHI整型数
			String versionStr = software.getVersion();
			String[] tmp = versionStr.split("\\.");
			int mainVer = Integer.parseInt(tmp[0]);
			int secondVer = Integer.parseInt(tmp[1]);
			int modfyVer = Integer.parseInt(tmp[2]);
			int version = mainVer * 1000000 + secondVer * 1000 + modfyVer;
			ms.setVersion(version);
			ms.setUpdateTime(new Date());
			this.softwareService.update(ms);
		} else {
			//添加
			MdSoftware ms = new MdSoftware();
			if(software.getSource()==0){
				//上传本地文件
				if(software.getUpload()!=null){
					File file = convertFile(software.getUpload());
					//先将文件上传到服务器 返回下载地址和文件ID
					String id = MUtil.postFileByUrl("http://localhost:8888/tysp-res/file/upload", file.getAbsolutePath());
					
					if(!"no".equals(id)){
						ms.setFileId(Integer.parseInt(id));
						ms.setUrl(fileService.findById(Integer.parseInt(id)).getUrl());
					}				
				}
			}else{
				//第三方链接
				ms.setUrl(software.getTps());
			}
			ms.setAlias(software.getAlias());
			ms.setCompanyId(software.getCompanyId());
			ms.setType(software.getType());
			ms.setDescription(software.getDescription());
			//把ABC.DEF.GHI转换为ABCDEFGHI整型数
			String versionStr = software.getVersion();
			String[] tmp = versionStr.split("\\.");
			int mainVer = Integer.parseInt(tmp[0]);
			int secondVer = Integer.parseInt(tmp[1]);
			int modfyVer = Integer.parseInt(tmp[2]);
			int version = mainVer * 1000000 + secondVer * 1000 + modfyVer;
			ms.setVersion(version);
			this.softwareService.save(ms);
		}

		return "<script>window.parent.submitSuccess('ok');</script>";
	}
	
	@RequestMapping("/toupdate")
	@ResponseBody
	public MdSoftware toUpdate(@RequestParam(value="id",required=false) Integer id){
		return softwareService.findById(id);
	}
	
	@RequestMapping("/removesoftware")
	@ResponseBody
	public String removeSoftware(@RequestParam(value="id",required=true) Integer id){
		
		MdSoftware ms = softwareService.findById(id);
		if(ms.getFileId()!=null){			
			int fileId = ms.getFile().getId();
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setCharset(Charset.forName("UTF-8"));
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
			builder.addTextBody("id", fileId+"");
			HttpEntity entity = builder.build();
			
			String rt = MUtil.postStringByUrl("http://localhost:8888/tysp-res/file/remove", entity);			
		}
		ms.setIsDeleted(1);
		this.softwareService.update(ms);
		return "ok";
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
