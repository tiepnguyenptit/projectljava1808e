package application.controller.web;

import application.data.model.Product;
import application.data.service.ProductService;
import application.model.viewmodel.home.BannerVM;
import application.model.viewmodel.home.HomeLandingVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController extends BaseController {


    @Autowired
    private ProductService productService;

    @GetMapping(value = "/")
    public String home(Model model,
                       @RequestParam(name = "categoryId", required = false) Integer categoryId,
                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                       @RequestParam(name = "sortByPrice", required = false) String sort){

//        Sort sortable = new Sort(Sort.Direction.ASC,"id");;
//        if (sort.equals("ASC")) {
//            sortable = new Sort(Sort.Direction.ASC,"price");
//        }
//        if (sort.equals("DESC")) {
//            sortable = new Sort(Sort.Direction.DESC,"price");
//        }
//
//        Pageable pageable = new PageRequest(page, size, sortable);
//
//        Page<Product> productPage = productService.getListProductByCategory(pageable,categoryId);


        HomeLandingVM vm = new HomeLandingVM();

        ArrayList<BannerVM> listBanners = new ArrayList<>();
        listBanners.add(new BannerVM("", "Text 1","https://forums.oscommerce.com/uploads/monthly_04_2016/post-336856-0-18918000-1459704022.jpg"));
        listBanners.add(new BannerVM("", "Text 2","http://bisnis.pnb.ac.id/skins/Standard/img/alila/03.jpg"));
        listBanners.add(new BannerVM("", "Text 3","http://akper.yayasanindah.ac.id/templates/slider/img/01.jpg"));

        vm.setListBanners(listBanners);

        vm.setLayoutHeaderVM(this.getLayoutHeaderVM());

        model.addAttribute("vm",vm);
        return "home";
    }


}
