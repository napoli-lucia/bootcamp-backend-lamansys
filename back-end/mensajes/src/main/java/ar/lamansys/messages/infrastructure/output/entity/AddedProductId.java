package ar.lamansys.messages.infrastructure.output.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AddedProductId implements Serializable {

    //Ver si asi reconoce las FK o falta algo?

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "product_id")
    private String productId;

}
