package com.myproject.novel.model;

import java.util.List;

public class ListCommentModel {
    private List<CommentModel> data;
    private MetaDataModel meta;

    public ListCommentModel(List<CommentModel> data, MetaDataModel meta) {
        this.data = data;
        this.meta = meta;
    }

    public List<CommentModel> getData() {
        return data;
    }

    public void setData(List<CommentModel> data) {
        this.data = data;
    }

    public MetaDataModel getMeta() {
        return meta;
    }

    public void setMeta(MetaDataModel meta) {
        this.meta = meta;
    }
}
