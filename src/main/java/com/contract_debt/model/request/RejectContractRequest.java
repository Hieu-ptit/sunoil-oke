package com.contract_debt.model.request;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class RejectContractRequest {

    @Length(max = 199, message = "4719")
    @NotBlank(message = "4718")
    private String reason;

    public String getReason() {
        return StringUtils.isBlank(reason) ? null : reason.trim();
    }
}
