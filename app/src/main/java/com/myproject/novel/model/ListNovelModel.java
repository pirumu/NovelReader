package com.myproject.novel.model;

import java.util.ArrayList;
import java.util.List;

public class ListNovelModel {
    private List<NovelModel> data;
    private MetaDataModel meta;

    public ListNovelModel(List<NovelModel> data, MetaDataModel meta) {
        this.data = data;
        this.meta = meta;
    }

    public List<NovelModel> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<NovelModel> data) {
        this.data = data;
    }

    public MetaDataModel getMeta() {
        return meta;
    }

    public void setMeta(MetaDataModel meta) {
        this.meta = meta;
    }
}
