package ar.lamansys.messages.application.product;

import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.domain.product.ProductBo;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ListProducts {

    private final ProductStorage productStorage;
    private final AssertUserExists assertUserExists;

    @Transactional(readOnly = true)
    public List<ProductBo> run(String ownerId) throws UserNotExistsException {
        assertUserExists.run(ownerId);
        List<ProductBo> products = productStorage.getAllProductsByUserId(ownerId)
                .collect(Collectors.toList());

        //LogUtil.logResult(ownerId, products);

        return products;
    }
}
