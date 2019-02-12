package application.controller.web;



import application.constant.CompanyConstant;
import application.model.viewmodel.common.HeaderMenuVM;
import application.model.viewmodel.common.LayoutHeaderVM;

import java.util.ArrayList;


public class BaseController {

    public LayoutHeaderVM getLayoutHeaderVM() {
        LayoutHeaderVM vm = new LayoutHeaderVM();
        ArrayList<HeaderMenuVM> headerMenuVMArrayList = new ArrayList<>();
        headerMenuVMArrayList.add(new HeaderMenuVM("Home", "/"));
        headerMenuVMArrayList.add(new HeaderMenuVM("Contact", "/"));
        headerMenuVMArrayList.add(new HeaderMenuVM("Cart", "/"));

        vm.setHeaderMenuVMArrayList(headerMenuVMArrayList);
        vm.setCompanyName(CompanyConstant.name);

        return vm;
    }

}
