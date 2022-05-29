package com.contract_debt.converter;

import com.common.util.Json;
import com.contract_debt.model.response.FileResponse;
import com.contract_debt.util.Global;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Converter(autoApply = true)
public class JsonConverterAttachmentList implements AttributeConverter<List<FileResponse>, String> {

    @Override
    public String convertToDatabaseColumn(List<FileResponse> attribute) {
        if (attribute == null) return null;

        return Json.encodeToString(attribute);
    }

    @Override
    public List<FileResponse> convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        return Json.decode(dbData.getBytes(StandardCharsets.UTF_8), Global.listAttachmentReader);
    }
}
