package com.contract_debt.model.request;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@CompiledJson
public class PaymentTime {

    @JsonAttribute(name = "payment_time_one")
    @JsonProperty("payment_time_one")
    private Date paymentTimeOne;

    @JsonAttribute(name = "payment_time_two")
    @JsonProperty("payment_time_two")
    private Date paymentTimeTwo;

    @JsonAttribute(name = "payment_time_three")
    @JsonProperty("payment_time_three")
    private Date paymentTimeThree;

    @JsonAttribute(name = "payment_time_four")
    @JsonProperty("payment_time_four")
    private Date paymentTimeFour;

    @JsonAttribute(name = "payment_time_five")
    @JsonProperty("payment_time_five")
    private Date paymentTimeFive;

}
