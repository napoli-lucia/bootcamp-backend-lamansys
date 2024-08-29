package ar.lamansys.messages.application.cart;

import ar.lamansys.messages.application.cart.exception.ProductStockNotEnough;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertProductStockEnough {

    private ProductStorage productStorage;

    //@revision cuidado: con tener excepciones no chequeadas
    public void run(String productId, int quantity) throws ProductStockNotEnough {
        if ( productStorage.getStockByProductId(productId) < quantity) {//tira excepcion si no existe
            throw new ProductStockNotEnough(productId);
        }
    }
}
