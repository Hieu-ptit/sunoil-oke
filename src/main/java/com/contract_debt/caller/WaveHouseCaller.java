package com.contract_debt.caller;

import com.common.exception.BusinessException;
import com.common.model.response.Response;
import com.common.util.ErrorCode;
import com.common.util.Json;
import com.contract_debt.model.response.ProductCategoryResponse;
import com.contract_debt.service.impl.ApiStorage;
import com.contract_debt.util.Global;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class WaveHouseCaller {

    private final RestClient restClient;
    private final ApiStorage apiStorage;

    /**
     * get all product oil
     *
     * @return
     */
    public CompletableFuture<Response<ProductCategoryResponse>> getProduct(int id, int categoryId) {

        return restClient.get(apiStorage.getProduct(id, categoryId), restClient.headers)
                .thenApply(httpResponse -> {
                    if (httpResponse.statusCode() != 200)
                        throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "product and category not exist ");

                    return Json.decode(httpResponse.body(), Global.productReaders);
                });

    }

}
