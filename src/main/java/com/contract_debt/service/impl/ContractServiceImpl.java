package com.contract_debt.service.impl;

import com.common.exception.BusinessException;
import com.common.model.response.Response;
import com.common.service.impl.InfoInJwt;
import com.common.util.ErrorCode;
import com.common.util.ErrorMessage;
import com.common.util.Json;
import com.contract_debt.caller.DriverCaller;
import com.contract_debt.caller.PublicManagementCaller;
import com.contract_debt.caller.RestClient;
import com.contract_debt.caller.WaveHouseCaller;
import com.contract_debt.mapper.ContractMapper;
import com.contract_debt.model.bo.StatusCommon;
import com.contract_debt.model.bo.StatusType;
import com.contract_debt.model.entity.Contract;
import com.contract_debt.model.request.*;
import com.contract_debt.model.response.ContractDetailResponse;
import com.contract_debt.model.response.ContractResponse;
import com.contract_debt.model.response.DetailEmployeeResponse;
import com.contract_debt.model.response.ProductCategoryResponse;
import com.contract_debt.repository.ContractRepository;
import com.contract_debt.service.ContractService;
import com.contract_debt.util.Global;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository repository;
    private final ContractMapper mapper;
    private final InfoInJwt infoInJwt;
    private final RestClient restClient;
    private final ApiStorage apiStorage;
    private final DriverCaller caller;
    private final PublicManagementCaller publicManagementCaller;
    private final WaveHouseCaller waveHouseCaller;


    /**
     * find all contract
     *
     * @param searchText
     * @param page
     * @param size
     * @param field
     * @param direction
     * @return
     */
    @Override
    public Page<ContractResponse> findAll(String searchText, Integer page, Integer size, String field, Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));

        // list contract of enterprise creator have status is DRAFT
        List<Integer> contractIdsExclude = repository.findContractHaveStatusDraft(StatusType.DRAFT.name());
        if (CollectionUtils.isEmpty(contractIdsExclude)) contractIdsExclude = List.of(0);

        if (StringUtils.isBlank(searchText)) {
            Page<Contract> contractPage = repository.findByStatusNot(contractIdsExclude, StatusCommon.DELETE.name(), pageable);
            return getContractPage(contractPage);
        }

        searchText = "%" + searchText.toLowerCase().trim() + "%";

        Page<Contract> contractPage = repository.findContractBySearchText(contractIdsExclude, StatusCommon.DELETE.name(), searchText, pageable);

        return getContractPage(contractPage);
    }

    /**
     * find contract by admin creator
     *
     * @param token
     * @param searchText
     * @param page
     * @param size
     * @param field
     * @param direction
     * @return
     */
    @Override
    public Page<ContractResponse> findByCustomer(String token, String searchText, Integer page, Integer size, String field, Sort.Direction direction) {

        String accountId = infoInJwt.getPayload(token).getAccountId();

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));

        if (StringUtils.isBlank(searchText)) {
            Page<Contract> contractPageCheck = repository.findByCustomerCreatorAndStatusNot(accountId, StatusCommon.DELETE.name(), pageable);
            if (CollectionUtils.isEmpty(contractPageCheck.getContent())) return Page.empty();
            return getContractPage(contractPageCheck);
        }

        searchText = "%" + searchText.toLowerCase().trim() + "%";

        Page<Contract> contractPage = repository.findContractBySearchTextAndCustomerId(accountId, StatusCommon.DELETE.name(), searchText, pageable);

        return getContractPage(contractPage);

    }

    /**
     * find contract by enterprise creator
     *
     * @param driverId
     * @param searchText
     * @param page
     * @param size
     * @param field
     * @param direction
     * @return
     */
    @Override
    public Page<ContractResponse> findContractForEnterprise(String driverId, String searchText, Integer page, Integer size, String field, Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));

        if (StringUtils.isEmpty(searchText) || searchText.trim().equals("null")) {
            Page<Contract> contractPage = repository.findByCustomerIdAndStatusNot(driverId, StatusCommon.DELETE.name(), pageable);
            return getContractPage(contractPage);
        }

        searchText = "%" + searchText.toLowerCase().trim() + "%";

        Page<Contract> contractPage = repository.findContractForEnterpriseSearchTextAndCustomerId(driverId, StatusCommon.DELETE.name(), searchText, pageable);

        return getContractPage(contractPage);
    }


    @Override
    public Boolean create(String accountId, CreateContractRequest request) {

        checkName(request.getName());
        DetailEmployeeResponse detailEmployee = findEmployeeByAccountId(accountId);
        Contract contract = mapper.mapToEntityPre(detailEmployee, request);
        repository.insert(contract);
        return true;
    }

    @Override
    public Boolean createContractPlan(String accountId, CreateContractPlanRequest request) {

        checkName(request.getName());
        DetailEmployeeResponse detailEmployee = findEmployeeByAccountId(accountId);
        Contract contract = mapper.mapToEntityPlan(detailEmployee, request);
        repository.insert(contract);

        return true;
    }

    @Override
    public Boolean updateContractPlan(String accountId, Integer id, ContractPlanRequest contractPlanRequest) {

        //validate name
        Contract contract = checkUpdateName(id, contractPlanRequest.getName());
        DetailEmployeeResponse detailEmployee = findEmployeeByAccountId(accountId);
        contract = mapper.mapUpdatePlan(contract, detailEmployee, contractPlanRequest);
        repository.update(contract);
        return true;
    }

    @Override
    public Boolean updateContractPre(String accountId, Integer id, ContractPreRequest contractPreRequest) {

        //validate name
        Contract contract = checkUpdateName(id, contractPreRequest.getName());
        DetailEmployeeResponse detailEmployee = findEmployeeByAccountId(accountId);
        contract = mapper.mapUpdatePre(contract, detailEmployee, contractPreRequest);
        repository.update(contract);

        return true;
    }

    /**
     * Reject Contract
     *
     * @param accountId
     * @param id
     * @param rejectContractRequest
     * @return
     */
    @Override
    public Boolean rejection(String accountId, Integer id, RejectContractRequest rejectContractRequest) {
        // get contract and validate
        Contract contract = checkContract(id);

        DetailEmployeeResponse detailEmployee = findEmployeeByAccountId(accountId);
        if (detailEmployee == null)
            throw new BusinessException(ErrorCode.EMPLOYEE_NOT_EXIST, "Employee with this account id not exist " + accountId);

        contract = mapper.map(contract, detailEmployee, StatusType.REJECT, rejectContractRequest.getReason());

        repository.update(contract);
        return true;
    }

    @Override
    @Transactional
    public Boolean accept(String accountId, Integer id) {

        // get contract and validate
        Contract contract = checkContract(id);

        DetailEmployeeResponse detailEmployee = findEmployeeByAccountId(accountId);
        if (detailEmployee == null)
            throw new BusinessException(ErrorCode.EMPLOYEE_NOT_EXIST, "Employee with this account id not exist " + accountId);

        List<ProductCategoryResponse> request = contract.getProduct();
        int amount = 0;
        if (!CollectionUtils.isEmpty(request)) {
            for (ProductCategoryResponse response : request) {
                amount += response.getProductResponse().getAmount();
            }
            caller.getUpdateDriver(contract.getCustomerId(), amount);
        }
        // increase limit
        boolean success = incrementLimit(contract);
        if (!success)
            throw new BusinessException(ErrorCode.INTERNAL_BUFFER_ERROR, "Error when increase limit, contract: " + id);

        // saving contract
        contract = mapper.map(contract, detailEmployee, StatusType.ACCEPTED, null);
        repository.update(contract);
        return true;
    }

    @Override
    public ContractDetailResponse getContractById(Integer id) {

        Contract contract = repository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.CONTRACT_NOT_FOUND, String.format(ErrorMessage.CONTRACT_NOT_FOUND, id)));
        return mapper.mapToResponseDetails(contract);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {

        repository.findByIdAndStatus(id, StatusType.DRAFT).orElseThrow(() ->
                new BusinessException(ErrorCode.CONTRACT_NOT_FOUND_OR_TYPE_DRAFT, String.format(ErrorMessage.CONTRACT_NOT_FOUND_OR_TYPE_DRAFT, id)));

        repository.delete(id);

        return true;
    }

    private void checkName(String name) {
        if (repository.existsByName(name))
            throw new BusinessException(ErrorCode.CONTRACT_NAME_EXISTED, String.format(ErrorMessage.CONTRACT_NAME_EXISTED, name));
    }

    private boolean incrementLimit(Contract contract) {
        IncrementLimitRequest request = new IncrementLimitRequest();

        request.setLimitMoney(contract.getLimitMoney())
                .setTypeContract(contract.getContractType().getCode())
                .setDriverId(contract.getCustomerId())
                .setProducts(contract.getProduct());


        return restClient.put(request, Global.incrementLimitWriter, apiStorage.increaseLimit(), restClient.headers)
                .thenApply(httpResponse -> Json.decode(httpResponse.body(), Global.booleanReader))
                .join().getData();
    }

    private Contract checkContract(Integer id) {
        return repository.findByIdAndStatus(id, StatusType.WAITING_ACCEPT).orElseThrow(() ->
                new BusinessException(ErrorCode.CONTRACT_NOT_FOUND_OR_TYPE_NOT_WAITING_ACCEPT, String.format(ErrorMessage.CONTRACT_NOT_FOUND_OR_TYPE_NOT_WAITING_ACCEPT, id)));
    }

    private PageImpl<ContractResponse> getContractPage(Page<Contract> contractPage) {
        List<ContractResponse> contractResponses = contractPage.stream().map(mapper::map).collect(Collectors.toList());
        return new PageImpl<>(contractResponses, contractPage.getPageable(), contractPage.getTotalElements());
    }

    /**
     * Call to Admin Service for finding employee by account id
     *
     * @param accountId
     * @return
     */
    private DetailEmployeeResponse findEmployeeByAccountId(String accountId) {
        CompletableFuture<Response<DetailEmployeeResponse>> cfDetailEmployee = restClient.get(apiStorage.findEmployeeByAccountId(accountId), restClient.headers)
                .thenApply(httpResponse -> {
                    if (httpResponse.statusCode() != 200)
                        throw new BusinessException(ErrorCode.EMPLOYEE_NOT_EXIST, "Employee with this account id not exist " + accountId);

                    return Json.decode(httpResponse.body(), Global.detailEmployeeReader);
                });

        Response<DetailEmployeeResponse> response = cfDetailEmployee.join();
        if (response == null) return null;

        return response.getData();
    }

    private Contract checkUpdateName(Integer id, String name) {
        Contract contract = repository.findByIdAndStatusIn(id);
        if (contract == null)
            throw new BusinessException(ErrorCode.CONTRACT_NOT_FOUND, String.format(ErrorMessage.CONTRACT_NOT_FOUND, id));
        if (!name.equals(contract.getName()) && !repository.existsByName(name))
            contract.setName(name);
        if (!name.equals(contract.getName()) && repository.existsByName(name))
            throw new BusinessException(ErrorCode.CONTRACT_NAME_EXISTED, String.format(ErrorMessage.CONTRACT_NAME_EXISTED, name));
        return contract;
    }

    /**
     * check file sort exit
     *
     * @param field
     */
    public void checkFieldExist(String field) {
        if (!StringUtils.isEmpty(field) && !Global.fieldSortVehicle.contains(field))
            throw new BusinessException(ErrorCode.CONTRACT_FIELD_SORT_INVALID, "Field sort: " + field + " invalid !");
    }

}
