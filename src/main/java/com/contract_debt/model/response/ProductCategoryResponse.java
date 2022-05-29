package com.contract_debt.model.response;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@CompiledJson
public class ProductCategoryResponse {

    @JsonAttribute(name = "product_response")
    private ProductResponse productResponse;

    @JsonAttribute(name = "category_response")
    private CategoryResponse categoryResponse;

}
