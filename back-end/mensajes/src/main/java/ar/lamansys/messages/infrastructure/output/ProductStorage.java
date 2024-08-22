package ar.lamansys.messages.infrastructure.output;

import ar.lamansys.messages.domain.product.ProductBo;
import ar.lamansys.messages.infrastructure.output.entity.Product;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProductStorage {

    void save(ProductBo product);

    void delete(String productId);

    Optional<Product> getProductByProductId(String productId);

    Stream<ProductBo> getAllProductsByUserId(String userId);

    int getStockByProductId(String productId);

    float getUnityPriceByProductId(String productId);

    String getOwnerByProductId(String productId);

    boolean exists(String productId);
}
