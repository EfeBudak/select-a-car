package com.pasha.efebudak.selectacar.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by efebudak on 05/05/2017.
 */

public abstract class PaginationObject {

    @SerializedName("page")
    private int page;
    @SerializedName("pageSize")
    private int pageSize;
    @SerializedName("totalPageCount")
    private int totalPageCount;

    public PaginationObject() {
    }

    public PaginationObject(int page, int pageSize, int totalPageCount) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalPageCount = totalPageCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public void copyObject(final PaginationObject paginationObject) {
        page = paginationObject.getPage();
        pageSize = paginationObject.getPageSize();
        totalPageCount = paginationObject.getTotalPageCount();
    }
}
