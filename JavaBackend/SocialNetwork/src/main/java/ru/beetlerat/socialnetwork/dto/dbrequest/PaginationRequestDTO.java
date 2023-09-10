package ru.beetlerat.socialnetwork.dto.dbrequest;

public class PaginationRequestDTO {
    private Integer page;
    private Integer pageSize;
    private Integer count;
    private Integer maxCount;

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public Integer getStartIndex() {
        return page * pageSize;
    }

    public Integer getEndIndex() {
        return getStartIndex() + Math.max(1, Math.min(maxCount, count));
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }
}
