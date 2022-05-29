package com.contract_debt.converter;

import com.common.util.Json;
import com.contract_debt.model.response.ProductCategoryResponse;
import com.contract_debt.util.Global;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Converter(autoApply = true)
public class JsonConverterProductList implements AttributeConverter<List<ProductCategoryResponse>, String> {

    @Override
    public String convertToDatabaseColumn(List<ProductCategoryResponse> attribute) {
        if (attribute == null) return null;

        return Json.encodeToString(attribute);
    }

    @Override
    public List<ProductCategoryResponse> convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        return Json.decode(dbData.getBytes(StandardCharsets.UTF_8), Global.listProductReader);
    }
}