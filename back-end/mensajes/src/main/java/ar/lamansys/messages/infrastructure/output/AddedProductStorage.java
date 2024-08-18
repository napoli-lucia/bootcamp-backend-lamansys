package ar.lamansys.messages.infrastructure.output;

import ar.lamansys.messages.domain.AddedProductBo;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;

import java.util.stream.Stream;

public interface AddedProductStorage {

    void save(AddedProductBo addedProduct);

    // Nos parece que no hace falta
    //void modifyQuantity(AddedProductBo addedProduct);

    void delete(AddedProductId addedProductId);

    // TODO: obtener addedproduct por cartId
    //Stream<AddedProductBo> getByCartId(String cartId);
}
