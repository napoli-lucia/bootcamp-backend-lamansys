package ar.lamansys.messages.infrastructure.output;

import ar.lamansys.messages.domain.cart.CartBo;

public interface CartStorage {

    void save(CartBo cart);

    void delete(String cartId);

}
