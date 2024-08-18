package ar.lamansys.messages.infrastructure.input.rest;

import ar.lamansys.messages.application.ListProducts;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.UserSessionNotExists;
import ar.lamansys.messages.domain.ProductBo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

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
