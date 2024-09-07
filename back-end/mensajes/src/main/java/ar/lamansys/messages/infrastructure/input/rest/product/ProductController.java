package ar.lamansys.messages.infrastructure.input.rest.product;

import ar.lamansys.messages.application.product.*;
import ar.lamansys.messages.application.product.exception.InvalidSellerException;
import ar.lamansys.messages.application.product.exception.ProductExistsException;
import ar.lamansys.messages.application.product.exception.ProductNotExistsException;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.product.ProductBo;
import ar.lamansys.messages.domain.product.ProductStockBo;
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
    private final UpdateProductStock updateProductStock;

    @GetMapping("/{ownerId}")
    public List<ProductBo> getAllProductsByOwnerId(@PathVariable String ownerId) throws UserNotExistsException {
        return listProducts.run(ownerId);
    }

    @PostMapping("/{sellerId}")
    public ResponseEntity addNewProductToSell(@PathVariable String sellerId, @RequestBody ProductBo newProduct) throws UserNotExistsException, ProductExistsException, InvalidSellerException {
        addNewProductToSell.run(sellerId, newProduct);
        return ResponseEntity.ok("New product added to sell successfully");
    }

    @DeleteMapping("/{sellerId}/{productId}")
    public ResponseEntity deleteProductToSell(@PathVariable String sellerId, @PathVariable String productId) throws ProductNotExistsException, UserNotExistsException, InvalidSellerException {
        deleteProductToSell.run(sellerId, productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/info/{productId}")
    public Optional<Product> getProductInfo(@PathVariable String productId) throws ProductNotExistsException {
        return getProductInfo.run(productId);
    }

    @PutMapping("/{sellerId}/{productId}/updateStock")
    public ResponseEntity updateProductStock(@PathVariable String sellerId, @PathVariable String productId, @RequestBody ProductStockBo newStock) throws ProductNotExistsException, UserNotExistsException, InvalidSellerException {
        updateProductStock.run(sellerId, productId, newStock);
        return ResponseEntity.ok("Product stock updated successfully");
    }
}
