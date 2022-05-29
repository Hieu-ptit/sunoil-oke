package com.contract_debt.model.response;

import com.common.model.bo.AccountStatus;
import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@CompiledJson
public class AccountInfo {

    @JsonAttribute(name = "account_id")
    private String accountId;

    private String code;

    private String username;

    private AccountStatus status;

}
