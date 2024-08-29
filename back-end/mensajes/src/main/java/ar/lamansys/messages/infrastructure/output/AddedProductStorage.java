package ar.lamansys.messages.infrastructure.output;

import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.infrastructure.output.entity.AddedProduct;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;

import java.util.List;
import java.util.stream.Stream;

//@revision esta interface deberia estar en application port
//para poder cambiar de infraestructura de ser necesario
public interface AddedProductStorage {

    void save(AddedProductBo addedProduct);

    void delete(AddedProductId addedProductId);

    List<AddedProduct> findAllByCartId(String cartId);

    boolean exists(AddedProductId addedProductId);

    void deleteAllByCartId(String cartId);
}
