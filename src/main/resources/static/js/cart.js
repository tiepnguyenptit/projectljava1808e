$(document).ready(function() {

    $(".change-product-amount").change(function () {
        dataCartProduct = {};
        dataCartProduct.id = $(this).data("id");
        dataCartProduct.amount = $(this).val();


        NProgress.start();

        var linkPost = "/api/cart-product/update";

        axios.post(linkPost, dataCartProduct).then(function(res){
            NProgress.done();
            if(res.data.success) {
                location.reload();
            } else {
                swal(
                    'Fail',
                    res.data.message,
                    'error'
                ).then(function() {
                    location.reload();
                });
            }
        }, function(err){
            NProgress.done();
            swal(
                'Error',
                'Fail',
                'error'
            );
        });
    });
    $(".delete-cart-product").on("click",function(){
        var dataCart = {};
        var pdInfo = $(this).data("product");

        dataCart.cartProductId = pdInfo;

        NProgress.start();
        var linkGet = "/api/cart-product/delete";
        axios.get(linkGet, dataCart).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    'Success',
                    res.data.message,
                    'success'
                ).then(function() {
                    location.reload();
                });
            } else {
                swal(
                    'Fail',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Error',
                'Fail',
                'error'
            );
        });
    })


});