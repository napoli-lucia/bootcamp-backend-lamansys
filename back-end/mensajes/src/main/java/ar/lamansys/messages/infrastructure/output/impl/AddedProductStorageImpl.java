package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.AddedProductBo;
import ar.lamansys.messages.infrastructure.output.AddedProductStorage;
import ar.lamansys.messages.infrastructure.output.entity.AddedProduct;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;
import ar.lamansys.messages.infrastructure.output.repository.AddedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
