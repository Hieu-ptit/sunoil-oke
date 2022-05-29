package com.contract_debt.model.response;

import com.contract_debt.model.bo.CreatorType;
import com.contract_debt.model.bo.StatusType;
import com.contract_debt.model.request.PaymentTime;
import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@CompiledJson
public class ContractDetailResponse {

    private Integer id;

    private String name;

    private String code;

    @JsonAttribute(name = "create_at")
    private OffsetDateTime createAt;

    @JsonAttribute(name = "effect_end_date")
    private OffsetDateTime effectEndDate;

    @JsonAttribute(name = "approve_date")
    private OffsetDateTime approveDate;

    @JsonAttribute(name = "customer_id")
    private String customerId;

    @JsonAttribute(name = "contract_type")
    private PropertiesResponse contractType;

    @JsonAttribute(name = "pay_method")
    private PropertiesResponse payMethod;

    @JsonAttribute(name = "transport_method")
    private PropertiesResponse transportMethod;

    private ProfileResponse customer;

    private List<ProductCategoryResponse> product;

    private List<FileResponse> attachment;

    @JsonAttribute(name = "employee_creator")
    private DetailEmployeeResponse employeeCreator;

    @JsonAttribute(name = "date_of_payment")
    private PaymentTime dateOfPayment;

    @JsonAttribute(name = "creator_type")
    private CreatorType creatorType;

    private StatusType status;

    @JsonAttribute(name = "total_payment")
    private BigDecimal totalPayment;

    @JsonAttribute(name = "full_address")
    private String fullAddress;

    @JsonAttribute(name = "reject_reason")
    private String rejectReason;

    @JsonAttribute(name = "limit_money")
    private BigDecimal limitMoney;

    private StationResponse station;

    @JsonAttribute(name = "count_payment")
    private int countPayment;

}
