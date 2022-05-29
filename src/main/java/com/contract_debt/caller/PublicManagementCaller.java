package com.contract_debt.caller;

import com.common.exception.BusinessException;
import com.common.model.response.Response;
import com.common.util.ErrorCode;
import com.common.util.Json;
import com.contract_debt.model.bo.PropertiesCode;
import com.contract_debt.model.response.FileResponse;
import com.contract_debt.model.response.PropertiesResponse;
import com.contract_debt.model.response.StationResponse;
import com.contract_debt.service.impl.ApiStorage;
import com.contract_debt.util.Global;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class PublicManagementCaller {

    private final RestClient restClient;
    private final ApiStorage apiStorage;

    public CompletableFuture<Response<PropertiesResponse>> getProperties(PropertiesCode code) {

        return restClient.get(apiStorage.getProperties(code), restClient.headers)
                .thenApply(httpResponse -> {
                    if (httpResponse.statusCode() != 200)
                        throw new BusinessException(ErrorCode.PROFILE_IS_NULL, "Properties with this code not exist " + code);

                    return Json.decode(httpResponse.body(), Global.propertiesReaders);
                });
    }

    public CompletableFuture<Response<FileResponse>> getFile(int id) {

        return restClient.get(apiStorage.getFile(id), restClient.headers)
                .thenApply(httpResponse -> {
                    if (httpResponse.statusCode() != 200)
                        throw new BusinessException(ErrorCode.FILE_NOT_FOUND, "File with this id not exist " + id);

                    return Json.decode(httpResponse.body(), Global.fileReader);
                });

    }

    public CompletableFuture<Response<PropertiesResponse>> getTransportMethod(PropertiesCode code) {

        return restClient.get(apiStorage.getProperties(code), restClient.headers)
                .thenApply(httpResponse ->
                        Json.decode(httpResponse.body(), Global.propertiesReaders)
                );
    }

    public CompletableFuture<Response<StationResponse>> checkStation(int id) {

        return restClient.get(apiStorage.getStation(id), restClient.headers)
                .thenApply(httpResponse ->
                    Json.decode(httpResponse.body(), Global.stationReader)
                );
    }

}
