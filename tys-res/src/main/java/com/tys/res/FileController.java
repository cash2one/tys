package com.tys.res;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tys.entity.MdFile;
import com.tys.service.MdFileSerivce;
import com.tys.util.MStrUtil;
import com.tys.util.MUtil;

@Controller
@RequestMapping("/file")
@Scope("prototype")
public class FileController {

	@Resource
	private MdFileSerivce fileService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest request, @RequestParam(required = false) String id,
			@RequestParam(required = false) MultipartFile file) throws ServletException, IOException {
		try {
			if (file != null) {
				String fileName = file.getOriginalFilename();

				try {
					fileName = new String(fileName.getBytes(), "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}

				String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

				String newName = fileName.substring(fileName.lastIndexOf(".")) + System.currentTimeMillis();

				newName = MStrUtil.getBASE64(newName) + "." + ext;

				String folder = chooseFolder(ext).get("folder").toString();

				String uploadPath = request.getSession().getServletContext().getRealPath(folder);

				int type = Integer.parseInt(chooseFolder(ext).get("type").toString());
				File targetFile = new File(uploadPath, newName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				file.transferTo(targetFile);
				
				// 判断是否是修改文件 还是 添加文件
				// 将文件信息保存到数据库
				if (id != null) {
					MdFile mdFile = this.fileService.findById(Integer.parseInt(id));

					// 删除旧文件
					MUtil.delFile(mdFile.getPath());

					mdFile.setName(fileName);
					mdFile.setType(type);
					mdFile.setPath(uploadPath + File.separator + newName);
					mdFile.setUrl(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
							+ request.getContextPath() + folder + "/" + newName);
					mdFile.setUpdateTime(new Date());
					this.fileService.update(mdFile);
				} else {
					MdFile mdFile = new MdFile();
					
					mdFile.setName(fileName);
					mdFile.setType(type);
					mdFile.setPath(uploadPath + File.separator + newName);
					mdFile.setUrl(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
							+ request.getContextPath() + folder + "/" + newName);
					fileService.save(mdFile);
				}
					

				return fileService.getFileByName(newName).getId()+"";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "no";
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public String remove(HttpServletResponse response,@RequestParam(value = "id", required = true) String id) {
		MdFile mdFile = this.fileService.findById(Integer.parseInt(id));
		MUtil.delFile(mdFile.getPath());
		
		mdFile.setIsDeleted(1);
		this.fileService.update(mdFile);
		return "ok";
	}

	public Map chooseFolder(String ext) {
		Map map = new HashMap();
		ext = ext.toLowerCase();
		map.put("folder", "/resources/unknown");
		map.put("type", 0);
		if (ext.equals("jpg") || ext.equals("gif") || ext.equals("bmp") || ext.equals("png")) {
			map.put("folder", "/resources/images");
			map.put("type", 1);
		}
		if (ext.equals("txt") || ext.equals("doc") || ext.equals("docx") || ext.equals("xls") || ext.equals("xlsx")
				|| ext.equals("ppt") || ext.equals("pptx") || ext.equals("pdf") || ext.equals("html")
				|| ext.equals("htm") || ext.equals("xml")) {
			map.put("folder", "/resources/documents");
			map.put("type", 4);
		}
		if (ext.equals("mp4") || ext.equals("avi") || ext.equals("mov") || ext.equals("wmv") || ext.equals("flv")
				|| ext.equals("rmvb") || ext.equals("3gp")) {
			map.put("folder", "/resources/video");
			map.put("type", 2);
		}
		if (ext.equals("mp3") || ext.equals("wav") || ext.equals("wma") || ext.equals("flac")) {
			map.put("folder", "/resources/audio");
			map.put("type", 3);
		}
		if (ext.equals("apk") || ext.equals("exe") || ext.equals("ios") || ext.equals("msi")) {
			map.put("folder", "/resources/apps");
			map.put("type", 5);
		}
		return map;
	}
}
