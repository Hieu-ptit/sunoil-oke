package com.contract_debt.model.request;

import com.contract_debt.model.bo.PropertiesCode;
import com.contract_debt.model.response.ProductCategoryResponse;
import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
@CompiledJson
public class IncrementLimitRequest {

    @JsonAttribute(name = "type_contract")
    private PropertiesCode typeContract;

    @JsonAttribute(name = "driver_id")
    private String driverId;

    @JsonAttribute(name = "limit_money")
    private BigDecimal limitMoney;

    private List<ProductCategoryResponse> products;
}
