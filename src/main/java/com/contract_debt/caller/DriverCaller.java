package com.contract_debt.caller;

import com.common.exception.BusinessException;
import com.common.model.response.Response;
import com.common.util.ErrorCode;
import com.common.util.Json;
import com.contract_debt.model.response.ProfileResponse;
import com.contract_debt.service.impl.ApiStorage;
import com.contract_debt.util.Global;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class DriverCaller {

    private final RestClient restClient;
    private final ApiStorage apiStorage;

    public CompletableFuture<Response<ProfileResponse>> getProfile(int id) {

        return restClient.get(apiStorage.getProfile(id), restClient.headers)
                .thenApply(httpResponse -> {
                    if (httpResponse.statusCode() != 200)
                        throw new BusinessException(ErrorCode.PROFILE_IS_NULL, "Profile with this id not exist " + id);

                    return Json.decode(httpResponse.body(), Global.profileReaders);
                });
    }

    public void getUpdateDriver(String driverId, Integer amount) {

        restClient.get(apiStorage.UpdateDetail(driverId, amount), restClient.headers)
                .thenApply(httpResponse ->
                        Json.decode(httpResponse.body(), Global.driverReaders)
                );
    }
}
