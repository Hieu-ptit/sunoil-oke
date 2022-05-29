package com.contract_debt.model.response;

import com.contract_debt.model.bo.CredentialImages;
import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@CompiledJson
public class ProfileResponse {

    private int id;

    private String name;

    private String address;

    @JsonAttribute(name = "enterprise_name")
    private String enterpriseName;

    private String email;

    private List<CredentialImages> avatar;

    @JsonAttribute(name = "province_id")
    private int provinceId;

    @JsonAttribute(name = "rank_id")
    private Integer rankId;

    @JsonAttribute(name = "district_id")
    private int districtId;

    @JsonAttribute(name = "ward_id")
    private int wardId;

    @JsonAttribute(name = "date_of_birth")
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonAttribute(name = "account_id")
    private String accountId;

    @JsonAttribute(name = "id_card")
    private String idCard;

    private String phone;

    private String location;

    private Integer point;

}
