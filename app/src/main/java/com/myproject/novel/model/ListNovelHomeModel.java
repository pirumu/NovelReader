package com.myproject.novel.model;

public class ListNovelHomeModel {
    private int type;
    private ListNovelModel listNovelModel;

    public ListNovelHomeModel(int type, ListNovelModel listNovelModel) {
        this.type = type;
        this.listNovelModel = listNovelModel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ListNovelModel getListNovelModel() {
        return listNovelModel;
    }

    public void setListNovelModel(ListNovelModel listNovelModel) {
        this.listNovelModel = listNovelModel;
    }
}
