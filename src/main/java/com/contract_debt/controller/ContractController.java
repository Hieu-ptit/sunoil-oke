package com.contract_debt.controller;

import com.common.model.response.Response;
import com.common.service.impl.InfoInJwt;
import com.contract_debt.model.request.*;
import com.contract_debt.model.response.ContractDetailResponse;
import com.contract_debt.model.response.ContractResponse;
import com.contract_debt.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/contracts")
@Validated
@RequiredArgsConstructor
public class ContractController {

    private final ContractService service;
    private final InfoInJwt infoInJwt;

    @GetMapping
    public Response<List<ContractResponse>> findAll(
            @RequestParam(value = "search-text", required = false) String searchText,
            @Min(value = 1, message = "4156")
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Min(value = 0, message = "4157")
            @RequestParam(value = "size", defaultValue = "50") Integer size,
            @RequestParam(value = "field-sort", defaultValue = "created_at") String field,
            @RequestParam(value = "direction-sort", defaultValue = "DESC") Sort.Direction direction
    ) {
        service.checkFieldExist(field);
        return Response.ofSucceeded(service.findAll(searchText, page - 1, size, field, direction));
    }

    @GetMapping("/customers")
    public Response<List<ContractResponse>> findAll(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "search-text", required = false) String searchText,
            @Min(value = 1, message = "4156")
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Min(value = 0, message = "4157")
            @RequestParam(value = "size", defaultValue = "50") Integer size,
            @RequestParam(value = "field-sort", defaultValue = "created_at") String field,
            @RequestParam(value = "direction-sort", defaultValue = "DESC") Sort.Direction direction
    ) {
        service.checkFieldExist(field);
        return Response.ofSucceeded(service.findByCustomer(token, searchText, page - 1, size, field, direction));
    }

    @GetMapping("/enterprise")
    public Response<List<ContractResponse>> findContractForEnterprise(
            @NotBlank @RequestParam("driver-id") String driverId,
            @RequestParam(value = "search-text", required = false) String searchText,
            @Min(value = 1, message = "4156")
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Min(value = 0, message = "4157")
            @RequestParam(value = "size", defaultValue = "50") Integer size,
            @RequestParam(value = "field-sort", defaultValue = "created_at") String field,
            @RequestParam(value = "direction-sort", defaultValue = "DESC") Sort.Direction direction
    ) {
        service.checkFieldExist(field);
        return Response.ofSucceeded(service.findContractForEnterprise(driverId, searchText, page - 1, size, field, direction));
    }

    @GetMapping("/{id}")
    public Response<ContractDetailResponse> getContractById(@Min(value = 0, message = "4793") @PathVariable Integer id) {
        return Response.ofSucceeded(service.getContractById(id));
    }

    @PostMapping
    public Response<Boolean> create(@RequestHeader("Authorization") String token,
                                    @Valid @RequestBody CreateContractRequest createContractRequest) {
        String accountId = infoInJwt.getPayload(token).getAccountId();
        return Response.ofSucceeded(service.create(accountId, createContractRequest));
    }

    @PostMapping("/plan")
    public Response<Boolean> createContractPlan(@RequestHeader("Authorization") String token,
                                                @Valid @RequestBody CreateContractPlanRequest createContractRequest) {
        String accountId = infoInJwt.getPayload(token).getAccountId();
        return Response.ofSucceeded(service.createContractPlan(accountId, createContractRequest));
    }

    /**
     * Internal Enterprise Create pre
     *
     * @param accountId
     * @param
     * @param createContractRequest
     * @return
     */
    @PostMapping("/enterprise/pre")
    public Response<Boolean> createContractPreInternal(@NotBlank @RequestParam("driver-id") String accountId,
                                                       @Valid @RequestBody CreateContractRequest createContractRequest) {
        return Response.ofSucceeded(service.create(accountId, createContractRequest));
    }

    /**
     * Internal Enterprise plan
     *
     * @param accountId
     * @param
     * @param createContractRequest
     * @return
     */
    @PostMapping("/enterprise/plan")
    public Response<Boolean> createContractPlanInternal(@NotBlank @RequestParam("driver-id") String accountId,
                                                        @Valid @RequestBody CreateContractPlanRequest createContractRequest) {
        return Response.ofSucceeded(service.createContractPlan(accountId, createContractRequest));
    }

    @PutMapping("/plan/{id}")
    public Response<Boolean> updateContractPlan(@RequestHeader("Authorization") String token,
                                                @Min(0) @PathVariable("id") Integer id,
                                                @Valid @RequestBody ContractPlanRequest contractPlanRequest) {
        String accountId = infoInJwt.getPayload(token).getAccountId();
        return Response.ofSucceeded(service.updateContractPlan(accountId, id, contractPlanRequest));
    }

    /**
     * Internal Enterprise plan
     *
     * @param driverId
     * @param id
     * @param contractPlanRequest
     * @return
     */
    @PutMapping("/enterprise/plan/{id}")
    public Response<Boolean> updateContractPlan(@Min(0) @PathVariable("id") Integer id,
                                                @NotBlank @RequestParam("driver-id") String driverId,
                                                @Valid @RequestBody ContractPlanRequest contractPlanRequest) {
        return Response.ofSucceeded(service.updateContractPlan(driverId, id, contractPlanRequest));
    }

    @PutMapping("/pre-payment/{id}")
    public Response<Boolean> updateContractPre(@RequestHeader("Authorization") String token,
                                               @Min(0) @PathVariable("id") Integer id,
                                               @Valid @RequestBody ContractPreRequest contractPreRequest) {
        String accountId = infoInJwt.getPayload(token).getAccountId();
        return Response.ofSucceeded(service.updateContractPre(accountId, id, contractPreRequest));
    }

    /**
     * Internal Enterprise pre
     *
     * @param driverId
     * @param id
     * @param contractPreRequest
     * @return
     */
    @PutMapping("/enterprise/pre/{id}")
    public Response<Boolean> updateContractPreInternal(@Min(0) @PathVariable("id") Integer id,
                                                       @NotBlank @RequestParam("driver-id") String driverId,
                                                       @Valid @RequestBody ContractPreRequest contractPreRequest) {
        return Response.ofSucceeded(service.updateContractPre(driverId, id, contractPreRequest));
    }

    @PutMapping("/rejections/{id}")
    public Response<Boolean> rejection(@RequestHeader("Authorization") String token,
                                       @PathVariable("id") @Positive Integer id,
                                       @RequestBody @Valid RejectContractRequest rejectContractRequest) {
        String accountId = infoInJwt.getPayload(token).getAccountId();
        return Response.ofSucceeded(service.rejection(accountId, id, rejectContractRequest));
    }

    @PutMapping("/acceptances/{id}")
    public Response<Boolean> accept(@RequestHeader("Authorization") String token,
                                    @PathVariable("id") @Positive Integer id) {
        String accountId = infoInJwt.getPayload(token).getAccountId();
        return Response.ofSucceeded(service.accept(accountId, id));
    }

    @DeleteMapping("/{id}")
    public Response<Boolean> delete(@Min(value = 0, message = "4715") @PathVariable("id") Integer id) {
        return Response.ofSucceeded(service.delete(id));
    }

}
