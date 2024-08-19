package ar.lamansys.messages.infrastructure.output;

import ar.lamansys.messages.domain.AddedProductBo;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;

public interface AddedProductStorage {

    void save(AddedProductBo addedProduct);

    void delete(AddedProductId addedProductId);

}
