package ar.lamansys.messages.application.product;


import ar.lamansys.messages.application.product.exception.InvalidSellerException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertUserIsSeller {
    public void run(String sellerId, String productOwnerId) throws InvalidSellerException {
        if (!sellerId.equals(productOwnerId)) {
            throw new InvalidSellerException();
        }
    }
}
