package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.product.ProductBo;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.Product;
import ar.lamansys.messages.infrastructure.output.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Primary
@RequiredArgsConstructor
@Service
public class ProductStorageImpl implements ProductStorage {

    private final ProductRepository productRepository;

    @Override
    public void save(ProductBo product) {
        productRepository.save(new Product(product));
    }

    @Override
    public void delete(String productId) {
        productRepository.deleteById(productId);
    }

    //Consultar si es recomendable que sea un optional para despues devolver el error
    /*@revision
     * Muchas veces devolver directamente el Optinal
     * le deja a quien lo usa mayor "maniobrabilidad" que una excepcion
     */
    @Override
    public Optional<Product> getProductByProductId(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Stream<ProductBo> getAllProductsByUserId(String userId) {
        return productRepository.findAllByOwnerId(userId);
    }

    //@revision estaria bueno armar una query que solo traiga el dato requerido
    @Override
    public int getStockByProductId(String productId) {
        return productRepository.findById(productId).get().getStock();
    }

    @Override
    public float getUnityPriceByProductId(String productId) {
        return productRepository.findById(productId).get().getUnityPrice();
    }

    //@revision Se podria hacer una consulta que solo traiga el id 
    //@revision si el el producto no existe se va a lanzar una excepcion NoSuchElementException
    @Override
    public String getOwnerByProductId(String productId) {
        return productRepository.findById(productId).get().getOwnerId();
    }

    @Override
    public boolean exists(String productId) {
        return productRepository.existsById(productId);
    }


    //@revision evitar utilizar reglas de negocio en el storage
    //podria pasar product.get().getStock() - quantity ya ese resultado
    @Override
    public void updateStock(String productId, Integer quantity) {
        Optional<Product> product = productRepository.findById(productId);
        product.get().setStock(product.get().getStock() - quantity);
        productRepository.save(product.get());
    }
}
