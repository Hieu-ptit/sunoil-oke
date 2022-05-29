package com.contract_debt.model.request;

import com.contract_debt.model.bo.CreatorType;
import com.contract_debt.model.bo.PropertiesCode;
import com.contract_debt.model.bo.StatusType;
import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
@CompiledJson
public class CreateContractRequest {

    @NotBlank(message = "4206")
    @Length(max = 100, message = "4207")
    private String name;

    @JsonAttribute(name = "effect_end_date")
    @JsonProperty("effect_end_date")
    private Date effectEndDate;

    @JsonAttribute(name = "status_type")
    @JsonProperty("status_type")
    @NotNull(message = "4250")
    private StatusType statusType;

    @JsonAttribute(name = "contract_type_code")
    @JsonProperty("contract_type_code")
    @NotNull(message = "4244")
    private PropertiesCode contractTypeCode;

    @JsonAttribute(name = "transport_method_code")
    @JsonProperty("transport_method_code")
    private PropertiesCode transportMethodCode;

    @NotNull(message = "4213")
    @JsonAttribute(name = "pay_method_code")
    @JsonProperty("pay_method_code")
    private PropertiesCode payMethodCode;

    @Min(value = 0, message = "4214")
    @JsonAttribute(name = "station_id")
    @JsonProperty("station_id")
    private int stationId;

    @Length(max = 98, message = "4061")
    @JsonAttribute(name = "full_address")
    @JsonProperty("full_address")
    private String fullAddress;

    @Min(value = 0, message = "4224")
    @JsonAttribute(name = "profile_id")
    @JsonProperty("profile_id")
    private int profileId;

    @Min(value = 0, message = "4795")
    @JsonAttribute(name = "rank_id")
    @JsonProperty("rank_id")
    private Integer rankId;

    @JsonAttribute(name = "product_info_requests")
    @JsonProperty("product_info_requests")
    @NotNull(message = "4444")
    private List<ProductInfoRequest> productInfoRequests;

    @JsonAttribute(name = "attachment_requests")
    @JsonProperty("attachment_requests")
    private Set<Integer> attachments;

    @JsonAttribute(name = "total_payment")
    @JsonProperty("total_payment")
    private BigDecimal totalPayment;

    @NotNull(message = "4241")
    @JsonAttribute(name = "creator_type")
    @JsonProperty("creator_type")
    private CreatorType creatorType;

}
