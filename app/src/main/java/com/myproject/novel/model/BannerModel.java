package com.myproject.novel.model;

import com.myproject.novel.net.C;

public class BannerModel {
    private int bannerId;
    private int novelId;
    private String bannerUrl;

    public BannerModel(int bannerId, int novelId, String bannerUrl) {
        this.bannerId = bannerId;
        this.novelId = novelId;
        this.bannerUrl = bannerUrl;
    }

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    public String getBannerUrl() {
        if (bannerUrl != null) {
            return C.BASE_URL + "/" + bannerUrl;
        }
        return null;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
}
