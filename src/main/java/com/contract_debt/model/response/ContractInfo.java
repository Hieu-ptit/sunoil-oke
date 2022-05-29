package com.contract_debt.model.response;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@CompiledJson
@Accessors(chain = true)
public class ContractInfo {

    @JsonAttribute(name = "total_page")
    private long totalPage;

    @JsonAttribute(name = "contract_responses")
    List<ContractResponse> contractResponses;
}
