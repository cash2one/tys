package com.tys.excel;

import java.io.Serializable;

/**
 * Excel导入用DTO
 */
public class ImportExportDTO implements Serializable {

    private static final long serialVersionUID = -1531323825520664156L;

    private String configFile;

    private String serviceBeans;

    private String exportActionUrl;

    private Integer exportTotalCount;

    private Integer exportPaginationSize;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceBeans() {
        return serviceBeans;
    }

    public void setServiceBeans(String serviceBeans) {
        this.serviceBeans = serviceBeans;
    }

    public Integer getExportTotalCount() {
        return exportTotalCount;
    }

    public void setExportTotalCount(Integer exportTotalCount) {
        this.exportTotalCount = exportTotalCount;
    }

    public Integer getExportPaginationSize() {
        return exportPaginationSize;
    }

    public void setExportPaginationSize(Integer exportPaginationSize) {
        this.exportPaginationSize = exportPaginationSize;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getExportActionUrl() {
        return exportActionUrl;
    }

    public void setExportActionUrl(String exportActionUrl) {
        this.exportActionUrl = exportActionUrl;
    }
}
