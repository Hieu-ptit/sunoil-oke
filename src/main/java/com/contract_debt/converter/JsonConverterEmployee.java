package com.contract_debt.converter;

import com.contract_debt.model.response.DetailEmployeeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class JsonConverterEmployee implements AttributeConverter<DetailEmployeeResponse, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DetailEmployeeResponse objectValue) {
        if (objectValue == null) return null;

        try {
            return mapper.writeValueAsString(objectValue);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to serialize to json field", e);
        }
    }

    @Override
    public DetailEmployeeResponse convertToEntityAttribute(String dataValue) {
        if (dataValue == null) return null;

        try {
            return mapper.readValue(dataValue, new TypeReference<DetailEmployeeResponse>() {
            });
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to serialize to json field", e);
        }
    }
}
