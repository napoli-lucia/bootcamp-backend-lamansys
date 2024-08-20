package ar.lamansys.messages.infrastructure.output.entity;

import ar.lamansys.messages.domain.product.ProductBo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
@Entity
public class Product {

    //Como generar automatico el id con string?
    @Id
    @Column(name = "product_id")
    private String productId;

    //FK
    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "unity_price", nullable = false)
    private Float unityPrice;


    public Product(ProductBo product) {
        productId = product.getProductId();
        ownerId = product.getOwnerId();
        productName = product.getProductName();
        stock = product.getStock();
        unityPrice = product.getUnityPrice();
    }
}
