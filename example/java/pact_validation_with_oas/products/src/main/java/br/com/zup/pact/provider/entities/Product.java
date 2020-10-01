package br.com.zup.pact.provider.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private @NotEmpty String name;
    private String description;
    private @NotEmpty String SKU;
    private Double weight;
    private Double height;
    private Double size;
    private @NotEmpty BigDecimal price;
}
