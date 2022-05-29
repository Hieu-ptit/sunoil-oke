package com.contract_debt.model.response;

import com.contract_debt.model.bo.AreaType;
import com.contract_debt.model.bo.StatusCommon;
import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@CompiledJson
public class StationResponse {
    private int id;

    private String name;

    private String address;

    private StatusCommon status;

    private String code;

    @JsonAttribute(name = "area_type")
    private AreaType areaType;

    @JsonAttribute(name = "full_address")
    private String fullAddress;

    @JsonAttribute(name = "province_id")
    private int provinceId;

    @JsonAttribute(name = "district_id")
    private int districtId;

    @JsonAttribute(name = "ward_id")
    private int wardId;
}
