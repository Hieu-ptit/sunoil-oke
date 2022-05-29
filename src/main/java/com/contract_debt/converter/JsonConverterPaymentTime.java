package com.contract_debt.converter;

import com.contract_debt.model.request.PaymentTime;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class JsonConverterPaymentTime implements AttributeConverter<PaymentTime, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(PaymentTime objectValue) {
        if (objectValue == null) return null;

        try {
            return mapper.writeValueAsString(objectValue);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to serialize to json field", e);
        }
    }

    @Override
    public PaymentTime convertToEntityAttribute(String dataValue) {
        if (dataValue == null) return null;

        try {
            return mapper.readValue(dataValue, new TypeReference<PaymentTime>() {
            });
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to serialize to json field", e);
        }
    }
}
