package com.contract_debt.model.bo;

import com.dslplatform.json.CompiledJson;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@CompiledJson
public class StationDetail {

    private int id;

    private String code;

    private String name;
}

