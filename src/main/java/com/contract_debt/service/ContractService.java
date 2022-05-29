package com.contract_debt.service;

import com.contract_debt.model.request.*;
import com.contract_debt.model.response.ContractDetailResponse;
import com.contract_debt.model.response.ContractResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface ContractService {

    Page<ContractResponse> findAll(String searchText, Integer page, Integer size, String field, Sort.Direction direction);

    Page<ContractResponse> findByCustomer(String token, String searchText, Integer page, Integer size, String field, Sort.Direction direction);

    Page<ContractResponse> findContractForEnterprise(String driverId, String searchText, Integer page, Integer size, String field, Sort.Direction direction);

    void checkFieldExist(String field);

    Boolean create(String accountId, CreateContractRequest request);

    Boolean createContractPlan(String accountId, CreateContractPlanRequest request);

    Boolean updateContractPlan(String accountId, Integer id, ContractPlanRequest contractPlanRequest);

    Boolean updateContractPre(String accountId, Integer id, ContractPreRequest contractPreRequest);

    Boolean rejection(String accountId, Integer id, RejectContractRequest rejectContractRequest);

    Boolean accept(String accountId, Integer id);

    ContractDetailResponse getContractById(Integer id);

    Boolean delete(Integer id);
}
