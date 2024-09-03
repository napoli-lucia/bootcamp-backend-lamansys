package ar.lamansys.messages.infrastructure.input.rest.product;

import ar.lamansys.messages.application.product.AddProduct;
import ar.lamansys.messages.application.product.ListProducts;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.addedproduct.NewProductBo;
import ar.lamansys.messages.domain.product.PostProductBo;
import ar.lamansys.messages.domain.product.ProductBo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ListProducts listProducts;
    private final AddProduct addProduct;

    @GetMapping("/{ownerId}")
    public List<ProductBo> getAllProductsByOwnerId(@PathVariable String ownerId) throws UserNotExistsException {
        return listProducts.run(ownerId);
    }

    @PostMapping ()
    public ResponseEntity<String> getAllProductsByOwnerId(@RequestBody PostProductBo newProduct) throws UserNotExistsException {
        addProduct.run(newProduct);
        return ResponseEntity.ok("Product added successfully");
    }

}
