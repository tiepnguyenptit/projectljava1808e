package application.controller.web;



import application.constant.CompanyConstant;
import application.data.model.Cart;
import application.data.service.CartService;
import application.model.viewmodel.common.HeaderMenuVM;
import application.model.viewmodel.common.LayoutHeaderVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.UUID;


public class BaseController {

    @Autowired
    private CartService cartService;

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

    public void checkCookie(HttpServletResponse response,
                            HttpServletRequest request,
                            final Principal principal) {
        Cookie cookie[] = request.getCookies();

        boolean flag = true;

        for(Cookie c : cookie) {
            if(c.getName().equals("guid")) {
                flag = false;
            }
        }


        if(principal!= null) {
            boolean flag2 = true;
            String  username = SecurityContextHolder.getContext().getAuthentication().getName();
            Cart cart = cartService.findByUserName(username);
            if(cart != null) {
                Cookie cookie1 = new Cookie("guid",cart.getGuid());
                cookie1.setPath("");
                response.addCookie(cookie1);
                flag2 = false;
            }
        }

        if(flag == true) {
            boolean flag2 = true;
            if(principal != null) {
                String  username = SecurityContextHolder.getContext().getAuthentication().getName();
                Cart cart = cartService.findByUserName(username);
                if(cart != null) {
                    flag2 = false;
                    Cookie cookie1 = new Cookie("guid",cart.getGuid());
                    cookie1.setPath("");
                    response.addCookie(cookie1);
                }
            }
            if(flag2 == true) {
                UUID uuid = UUID.randomUUID();
                String guid = uuid.toString();

                Cart cart = new Cart();
                cart.setGuid(guid);
                if(principal!=null) {
                    String  username = SecurityContextHolder.getContext().getAuthentication().getName();
                    cart.setUserName(username);
                }

                cartService.addNewCart(cart);

                Cookie cookie2 = new Cookie("guid",guid);
                cookie2.setPath("");
                response.addCookie(cookie2);
            }
        }
    }

}
