package ar.lamansys.messages.infrastructure.input.rest.product;

import ar.lamansys.messages.application.product.ListProducts;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.product.ProductBo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ListProducts listProducts;

    @GetMapping("/{ownerId}")
    public List<ProductBo> getAllProductsByOwnerId(@PathVariable String ownerId) throws UserNotExistsException {
        return listProducts.run(ownerId);
    }
}
