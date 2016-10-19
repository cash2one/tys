package com.tys.excel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tys.excel.exception.BusinessException;
import com.tys.excel.exp.ExportSheet;
import com.tys.excel.exp.SXSSFExcelExport;
import com.tys.excel.imp.ImportUtil;

/**
 * Excel导入用Controlle
 * 
 */
@Controller
@RequestMapping("/sys/excel")
public class ImportExportController {


    @Autowired
    private ImportUtil excelImportUtil;

    @RequestMapping("/toImport")
    public String toImport(ImportExportDTO dto, Model model) {
        model.addAttribute("importExportDTO", dto);

        return "/macro/excel/excelImport";
    }

    @RequestMapping("/downTemplate")
    public ModelAndView downTemplate(HttpServletRequest request, HttpServletResponse response, ImportExportDTO dto)
            throws Exception {

        String fileName = new String(dto.getConfigFile().getBytes(), "ISO8859-1");

        response.reset();
        response.setContentType("application/vnd.openxmlformats");
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        /* 创建输出流 */

        String[] serviceBeanArr = dto.getServiceBeans().split(",");
        ExportSheet[] sheets = new ExportSheet[serviceBeanArr.length];
        for (int i = 0; i < serviceBeanArr.length; i++) {
            sheets[i] = excelImportUtil.getTemplateExcel(request, dto.getConfigFile(), serviceBeanArr[i], i);
        }

        XSSFWorkbook rawWorkbook = new XSSFWorkbook();
        new SXSSFExcelExport(rawWorkbook).arrays2Excel(sheets);
        rawWorkbook.write(response.getOutputStream());
        response.getOutputStream().close();

        return null;
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ModelAndView importExcel(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("importFile") MultipartFile importFile, ImportExportDTO dto) throws Exception {
        model.addAttribute("importExportDTO", dto);

        if (importFile.getSize() > 5 * 1024 * 1024) {
            model.addAttribute("errorMsg", "Excel文件大小不能超过5M");

            return new ModelAndView("/macro/excel/excelImport");
        }

        Workbook wb = WorkbookFactory.create(importFile.getInputStream());
        int importResult = 0;
        String[] serviceBeanArr = dto.getServiceBeans().split(",");

        try {
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                importResult += excelImportUtil.importExcel(request, wb, dto.getConfigFile(), serviceBeanArr, i);
            }

            if (importResult == 0) {
                model.addAttribute("importSuccess", 1);
                model.addAttribute("importExportDTO", dto);

                return new ModelAndView("/macro/excel/excelImport");
            } else {
                String fileName = new String((importFile.getOriginalFilename().substring(0,
                        importFile.getOriginalFilename().lastIndexOf("."))
                        + "-导入出错" + importFile.getOriginalFilename().substring(
                        importFile.getOriginalFilename().lastIndexOf("."))).getBytes(), "ISO8859-1");

                response.reset();
                response.setContentType("application/vnd.openxmlformats");
                response.setCharacterEncoding("utf-8");
                response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
                /* 创建输出流 */

                wb.write(response.getOutputStream());

                response.getOutputStream().close();

                return null;
            }
        } catch (BusinessException e) {
            model.addAttribute("errorMsg", e.getCode());

            return new ModelAndView("/macro/excel/excelImport");
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping("/toExportPagination")
    public String toExportPage(ImportExportDTO dto, Model model) {
        model.addAttribute("importExportDTO", dto);

        return "/macro/excel/excelPaginationExport";
    }
}
