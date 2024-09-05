package ar.lamansys.messages.application.product;


import ar.lamansys.messages.application.product.exception.InvalidSeller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertUserSeller {
    public void run(String sellerId, String productOwnerId) throws InvalidSeller {
        if (!sellerId.equals(productOwnerId)) {
            throw new InvalidSeller();
        }
    }
}
