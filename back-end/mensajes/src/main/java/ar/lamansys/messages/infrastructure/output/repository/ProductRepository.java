package ar.lamansys.messages.infrastructure.output.repository;

import ar.lamansys.messages.domain.MessageStoredBo;
import ar.lamansys.messages.domain.ProductBo;
import ar.lamansys.messages.infrastructure.output.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Transactional(readOnly = true)
    @Query("SELECT NEW ar.lamansys.messages.domain.ProductBo(p.productId,p.ownerId,p.productName,p.stock,p.unityPrice) " +
            "FROM Product p " +
            "WHERE p.ownerId = :userId ")
    Stream<ProductBo> findAllByOwnerId(@Param("userId") String userId);
}
