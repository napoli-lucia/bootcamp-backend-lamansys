package ar.lamansys.messages.infrastructure.output.entity;

import ar.lamansys.messages.domain.AddedProductBo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "added_product")
public class AddedProduct {

    @EmbeddedId
    AddedProductId addedProductId = new AddedProductId();

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public AddedProduct(AddedProductBo addedProduct) {
        addedProductId.setProductId(addedProduct.getProductId());
        addedProductId.setCartId(addedProduct.getCartId());
        quantity = addedProduct.getQuantity();
    }
}
