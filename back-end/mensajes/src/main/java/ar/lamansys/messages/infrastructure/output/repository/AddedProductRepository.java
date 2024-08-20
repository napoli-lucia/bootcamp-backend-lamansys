package ar.lamansys.messages.infrastructure.output.repository;

import ar.lamansys.messages.infrastructure.output.entity.AddedProduct;
import ar.lamansys.messages.infrastructure.output.entity.AddedProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddedProductRepository extends JpaRepository<AddedProduct, AddedProductId> {

}
