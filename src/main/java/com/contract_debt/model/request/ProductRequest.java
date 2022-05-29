package com.contract_debt.model.request;

import com.contract_debt.model.bo.CategoryProductType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ProductRequest {

    private CategoryProductType categoryProductType;

    private String productName;

    private String unit;

    private int amount;

    private BigDecimal price;

    private BigDecimal totalMoney;

    private BigDecimal totalPayment;

    private Double discount;
}
