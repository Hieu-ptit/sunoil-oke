package com.contract_debt.model.response;

import com.contract_debt.model.bo.StatusType;
import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
@CompiledJson
public class ContractResponse {

    private Integer id;

    private String code;

    private String name;

    @JsonAttribute(name = "contract_type")
    private PropertiesResponse contractType;

    private StatusType status;

    @JsonAttribute(name = "effect_end_date")
    private OffsetDateTime effectEndDate;

    @JsonAttribute(name = "created_at")
    private OffsetDateTime createdAt;
}
