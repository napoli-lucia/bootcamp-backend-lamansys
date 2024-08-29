package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.addedproduct.AddedProductBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProduct;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;
import ar.lamansys.messages.infrastructure.output.repository.AddedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class AddedProductStorageImpl implements AddedProductStorage {

    private final AddedProductRepository addedProductRepository;

    @Override
    public void save(AddedProductBo addedProduct) {
        addedProductRepository.save(new AddedProduct(addedProduct));
    }

    @Override
    public void delete(AddedProductId addedProductId) {
        addedProductRepository.deleteById(addedProductId);
    }

    @Override
    public List<AddedProduct> findAllByCartId(String cartId) {
        return addedProductRepository.findAllByCartId(cartId);
    }

    //@revision Deberia crear el AddedProductId dentro
    //del storage porque el application no deberia conocer la entidad y el storage si
    @Override
    public boolean exists(AddedProductId addedProductId) {
        return addedProductRepository.existsById(addedProductId);
    }

    @Override
    public void deleteAllByCartId(String cartId) {
        addedProductRepository.deleteAllByCartId(cartId);
    }
}
