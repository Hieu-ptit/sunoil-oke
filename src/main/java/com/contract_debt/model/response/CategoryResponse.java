package com.contract_debt.model.response;

import com.contract_debt.model.bo.CategoryProductType;
import com.contract_debt.model.bo.StatusCommon;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)

public class CategoryResponse {

    private int id;

    private String code;

    private String name;

    private String description;

    private StatusCommon status;

    private CategoryProductType type;

}
