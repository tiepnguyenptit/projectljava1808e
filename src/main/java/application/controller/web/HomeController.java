package application.controller.web;

import application.data.model.Category;
import application.data.model.Product;
import application.data.service.CategoryService;
import application.data.service.ProductService;
import application.model.viewmodel.common.CategoryVM;
import application.model.viewmodel.common.ProductVM;
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

import javax.validation.Valid;
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

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/")
    public String home(Model model,
                       @Valid @ModelAttribute("productname") ProductVM productName,
                       @RequestParam(name = "categoryId", required = false) Integer categoryId,
                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                       @RequestParam(name = "sortByPrice", required = false) String sort){


        HomeLandingVM vm = new HomeLandingVM();


        /**
         * set list bannerVM
         */
        ArrayList<BannerVM> listBanners = new ArrayList<>();
        listBanners.add(new BannerVM("", "Text 1","https://forums.oscommerce.com/uploads/monthly_04_2016/post-336856-0-18918000-1459704022.jpg"));
        listBanners.add(new BannerVM("", "Text 2","http://bisnis.pnb.ac.id/skins/Standard/img/alila/03.jpg"));
        listBanners.add(new BannerVM("", "Text 3","http://akper.yayasanindah.ac.id/templates/slider/img/01.jpg"));


        /**
         * set list categoryVM
         */
        List<Category> categoryList = categoryService.getListAllCategories();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for(Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setName(category.getName());
            categoryVM.setShortDesc(category.getShortDesc());
            category.setCreatedDate(category.getCreatedDate());
            categoryVMList.add(categoryVM);
        }

        /**
         * set list productVM
         */

        Sort sortable = new Sort(Sort.Direction.ASC,"id");
        if(sort != null) {
            if (sort.equals("ASC")) {
                sortable = new Sort(Sort.Direction.ASC,"price");
            }else {
                sortable = new Sort(Sort.Direction.DESC,"price");
            }
        }

        Pageable pageable = new PageRequest(page, size, sortable);

        List<Product> productList = new ArrayList<>();

        if(categoryId != null) {
            productList = productService.getListProductByCategoryOrProductNameContaining(pageable,categoryId,null).getContent();
            Category category = categoryService.findOne(categoryId);
            vm.setKeyWord(category.getName());
        } else if (productName.getName() != null && !productName.getName().isEmpty()) {
            productList = productService.getListProductByCategoryOrProductNameContaining(pageable,null,productName.getName().trim()).getContent();
            vm.setKeyWord("Find by product name containing " + productName);
        } else {
            productList = productService.getListProductByCategoryOrProductNameContaining(pageable,null,null).getContent();
        }


        List<ProductVM> productVMList = new ArrayList<>();

        for(Product product : productList) {
            ProductVM productVM = new ProductVM();
            if(product.getCategory() == null) {
                productVM.setCategoryName("Unknown");
            } else {
                productVM.setCategoryName(product.getCategory().getName());
            }

            productVM.setName(product.getName());
            productVM.setMainImage(product.getMainImage());
            productVM.setPrice(product.getPrice());
            productVM.setShortDesc(product.getShortDesc());
            productVM.setCreatedDate(product.getCreatedDate());

            productVMList.add(productVM);
        }


        vm.setListBanners(listBanners);
        vm.setLayoutHeaderVM(this.getLayoutHeaderVM());
        vm.setCategoryVMList(categoryVMList);
        vm.setProductVMList(productVMList);
        if(productVMList.size() == 0) {
            vm.setKeyWord("Not found any product");
        }

        for(ProductVM productVM : productVMList) {
            System.out.println(productVM.getName());
        }

        model.addAttribute("vm",vm);
        return "home";
    }


}
