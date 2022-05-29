package com.contract_debt.model.response;

import com.contract_debt.model.bo.DepartmentType;
import com.contract_debt.model.bo.PropertiesCode;
import com.contract_debt.model.bo.PropertiesType;
import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@CompiledJson
public class PropertiesResponse {

    private int id;

    private String name;

    private PropertiesType type;

    private PropertiesCode code;

    @JsonAttribute(name = "department_type")
    @JsonProperty("department_type")
    private DepartmentType departmentType;

}
