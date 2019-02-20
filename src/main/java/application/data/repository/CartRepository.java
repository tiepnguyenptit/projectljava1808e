package application.data.repository;

import application.data.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query(value = "SELECT c FROM dbo_cart c " +
            "WHERE :guid IS NULL OR c.guid = :guid " +
            "ORDER BY c.id DESC LIMIT 1",nativeQuery = true)
    Cart findFirstCartByGuid(@Param("guid")String guid);

    @Query(value = "SELECT c from tbl_cart c " +
            "WHERE :userName IS NULL OR c.userName = :userName " +
            "ORDER BY c.id DESC LIMIT 1",nativeQuery = true)
    Cart findByUserName(@Param("userName") String userName);

}
