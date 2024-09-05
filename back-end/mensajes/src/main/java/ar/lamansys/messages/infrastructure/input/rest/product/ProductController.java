package ar.lamansys.messages.infrastructure.input.rest.product;

import ar.lamansys.messages.application.product.AddNewProductToSell;
import ar.lamansys.messages.application.product.DeleteProductToSell;
import ar.lamansys.messages.application.product.GetProductInfo;
import ar.lamansys.messages.application.product.ListProducts;
import ar.lamansys.messages.application.product.exception.ProductExistsException;
import ar.lamansys.messages.application.product.exception.ProductNotExistsException;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.product.ProductBo;
import ar.lamansys.messages.infrastructure.output.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ListProducts listProducts;
    private final AddNewProductToSell addNewProductToSell;
    private final DeleteProductToSell deleteProductToSell;
    private final GetProductInfo getProductInfo;

    @GetMapping("/{ownerId}")
    public List<ProductBo> getAllProductsByOwnerId(@PathVariable String ownerId) throws UserNotExistsException {
        return listProducts.run(ownerId);
    }

    @PostMapping
    public ResponseEntity addNewProductToSell(@RequestBody ProductBo newProduct) throws UserNotExistsException, ProductExistsException {
        addNewProductToSell.run(newProduct);
        return ResponseEntity.ok("New product added to sell successfully");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProductToSell(@PathVariable String productId) throws ProductNotExistsException {
        deleteProductToSell.run(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/info/{productId}")
    public Optional<Product> getProductInfo(@PathVariable String productId) throws ProductNotExistsException {
        return getProductInfo.run(productId);
    }
}
