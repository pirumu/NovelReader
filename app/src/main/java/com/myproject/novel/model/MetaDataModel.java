package com.myproject.novel.model;

public class MetaDataModel {
    private int limit;
    private int offset;
    private int count;
    private int total;

    public MetaDataModel(int limit, int offset, int count, int total) {
        this.limit = limit;
        this.offset = offset;
        this.count = count;
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
