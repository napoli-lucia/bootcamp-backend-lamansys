package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.UserSessionNotExists;
import ar.lamansys.messages.domain.ProductBo;
import ar.lamansys.messages.infrastructure.output.LogUtil;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
