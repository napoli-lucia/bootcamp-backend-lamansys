package ar.lamansys.messages.infrastructure.output;

import ar.lamansys.messages.domain.cart.CartBo;
import ar.lamansys.messages.infrastructure.output.entity.Cart;

import java.util.Optional;

public interface CartStorage {

    void save(CartBo cart);

    void delete(String cartId);

    boolean exists(String cartId);

    Optional<Cart> getById(String cartId);

}
