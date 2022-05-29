package com.contract_debt.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileResponse {

    private Integer id;

    private String url;

    private String name;
}
