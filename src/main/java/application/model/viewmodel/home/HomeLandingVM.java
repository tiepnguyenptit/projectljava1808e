package application.model.viewmodel.home;

import application.model.viewmodel.common.LayoutHeaderVM;

import java.util.ArrayList;

public class HomeLandingVM {
    private ArrayList<BannerVM> listBanners;
    private LayoutHeaderVM layoutHeaderVM;

    public LayoutHeaderVM getLayoutHeaderVM() {
        return layoutHeaderVM;
    }

    public void setLayoutHeaderVM(LayoutHeaderVM layoutHeaderVM) {
        this.layoutHeaderVM = layoutHeaderVM;
    }

    public ArrayList<BannerVM> getListBanners() {
        return listBanners;
    }

    public void setListBanners(ArrayList<BannerVM> listBanners) {
        this.listBanners = listBanners;
    }
}
