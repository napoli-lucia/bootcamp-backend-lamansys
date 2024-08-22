package ar.lamansys.messages.domain.cart;

import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartCreationBo {
    private String cartId;
    private List<NewProductBo> addedProducts;
    private List<String> exceptions;
}