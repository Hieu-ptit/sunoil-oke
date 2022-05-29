package com.contract_debt.model.response;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@CompiledJson
public class DetailEmployeeResponse {

    @JsonAttribute(name = "employee_id")
    private Integer employeeId;

    @JsonAttribute(name = "account_id")
    private String accountId;

    private String name;

    private PropertiesResponse position;

    private PropertiesResponse department;

}
