package com.contract_debt.model.response;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@CompiledJson
public class ProductResponse {

    private int id;

    private String name;

    @JsonAttribute(name = "total_money")
    private BigDecimal totalMoney;

    private BigDecimal price;

    private Double discount;

    private String unit;

    private int amount;

}
