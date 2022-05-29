package com.contract_debt.model.request;

import com.contract_debt.model.bo.CreatorType;
import com.contract_debt.model.bo.PropertiesCode;
import com.contract_debt.model.bo.StatusType;
import com.dslplatform.json.JsonAttribute;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ContractPlanRequest {

    @NotBlank(message = "4206")
    @Length(max = 100, message = "4207")
    private String name;

    @JsonAttribute(name = "transport_method_code")
    @JsonProperty("transport_method_code")
    private PropertiesCode transportMethodCode;

    @JsonAttribute(name = "status_type")
    @JsonProperty("status_type")
    @NotNull(message = "4250")
    private StatusType statusType;

    @NotNull(message = "4213")
    @JsonAttribute(name = "pay_method_code")
    @JsonProperty("pay_method_code")
    private PropertiesCode payMethodCode;

    @JsonAttribute(name = "effect_end_date")
    @JsonProperty("effect_end_date")
    private Date effectEndDate;

    @JsonAttribute(name = "date_of_payment")
    @JsonProperty("date_of_payment")
    private PaymentTime dateOfPayment;

    @JsonAttribute(name = "attachment_requests")
    @JsonProperty("attachment_requests")
    private Set<Integer> attachmentIds;

    @NotNull(message = "4241")
    @JsonAttribute(name = "creator_type")
    @JsonProperty("creator_type")
    private CreatorType creatorType;

    @JsonAttribute(name = "limit_money")
    @JsonProperty("limit_money")
    @Range(min = 0, max = 999999999999999999L, message = "4245")
    private BigDecimal limitMoney;

    @JsonAttribute(name = "count_payment")
    @JsonProperty("count_payment")
    private int countPayment;
}
