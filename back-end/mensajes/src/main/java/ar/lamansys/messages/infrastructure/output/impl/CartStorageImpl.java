package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.cart.CartBo;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.entity.Cart;
import ar.lamansys.messages.infrastructure.output.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@RequiredArgsConstructor
@Service
public class CartStorageImpl implements CartStorage {

    private final CartRepository cartRepository;

    @Override
    public void save(CartBo cart) {
        cartRepository.save(new Cart(cart));
    }

    @Override
    public void delete(String cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public boolean exists(String cartId) {
        return cartRepository.existsById(cartId);
    }

    @Override
    public Optional<Cart> getById(String cartId) {
        return cartRepository.findById(cartId);
    }
}
