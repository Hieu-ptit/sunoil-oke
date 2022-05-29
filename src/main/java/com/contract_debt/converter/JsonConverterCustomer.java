package com.contract_debt.converter;

import com.contract_debt.model.response.ProfileResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class JsonConverterCustomer implements AttributeConverter<ProfileResponse, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ProfileResponse objectValue) {
        if (objectValue == null) return null;

        try {
            return mapper.writeValueAsString(objectValue);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to serialize to json field", e);
        }
    }

    @Override
    public ProfileResponse convertToEntityAttribute(String dataValue) {
        if (dataValue == null) return null;

        try {
            return mapper.readValue(dataValue, new TypeReference<ProfileResponse>() {
            });
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to serialize to json field", e);
        }
    }
}
