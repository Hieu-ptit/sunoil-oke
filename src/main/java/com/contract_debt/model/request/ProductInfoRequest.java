package com.contract_debt.model.request;

import com.dslplatform.json.JsonAttribute;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ProductInfoRequest {

    @NotNull(message = "4731")
    @JsonAttribute(name = "product_id")
    @JsonProperty("product_id")
    private int productId;

    @JsonAttribute(name = "category_product_id")
    @JsonProperty("category_product_id")
    private int categoryProductId;

    @NotNull(message = "4225")
    private BigDecimal price;

    @NotBlank(message = "4222")
    @Length(max = 98, message = "4061")
    private String unit;

    @NotNull(message = "4223")
    private int amount;

    @NotNull(message = "4209")
    @JsonAttribute(name = "total_money")
    @JsonProperty("total_money")
    private BigDecimal totalMoney;

    @NotNull(message = "4212")
    private Double discount;
}
