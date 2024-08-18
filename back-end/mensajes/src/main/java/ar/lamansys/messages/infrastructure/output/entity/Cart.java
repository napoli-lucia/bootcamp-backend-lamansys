package ar.lamansys.messages.infrastructure.output.entity;

import ar.lamansys.messages.domain.CartBo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "cart")
@Entity
public class Cart {

    @Id
    @Column(name = "cart_id")
    private String cartId;

    //FK
    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    //FK
    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    public Cart(CartBo cart) {
        cartId = cart.getCartId();
        ownerId = cart.getOwnerId();
        sellerId = cart.getSellerId();
    }
}
