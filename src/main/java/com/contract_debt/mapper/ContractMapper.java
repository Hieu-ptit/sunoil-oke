package com.contract_debt.mapper;

import com.common.exception.BusinessException;
import com.common.model.response.Response;
import com.common.util.ErrorCode;
import com.common.util.ErrorMessage;
import com.contract_debt.caller.DriverCaller;
import com.contract_debt.caller.PublicManagementCaller;
import com.contract_debt.caller.WaveHouseCaller;
import com.contract_debt.model.bo.AreaType;
import com.contract_debt.model.bo.PropertiesCode;
import com.contract_debt.model.bo.StatusType;
import com.contract_debt.model.bo.TypeContract;
import com.contract_debt.model.entity.Contract;
import com.contract_debt.model.request.*;
import com.contract_debt.model.response.*;
import com.contract_debt.service.SequenceInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class ContractMapper {

    private final SequenceInternalService service;
    private final PublicManagementCaller publicManagementCaller;
    private final DriverCaller driverCaller;
    private final WaveHouseCaller waveHouseCaller;

    public ContractResponse map(Contract contract) {

        return new ContractResponse()
                .setId(contract.getId())
                .setCode(contract.getCode())
                .setName(contract.getName())
                .setContractType(contract.getContractType())
                .setStatus(contract.getStatus())
                .setCreatedAt(contract.getCreatedAt())
                .setEffectEndDate(contract.getEffectEndDate());
    }

    /**
     * map CreateContractRequest To Contract
     *
     * @param request
     * @return
     */
    public Contract mapToEntityPre(DetailEmployeeResponse detailEmployee, CreateContractRequest request) {

        if (request.getEffectEndDate() != null && request.getEffectEndDate().before(Date.from(Instant.now())))
            throw new BusinessException(ErrorCode.EFFECT_END_DATE_INVALID, ErrorMessage.EFFECT_END_DATE_INVALID);

        CompletableFuture<Response<PropertiesResponse>> completableFuture = publicManagementCaller.getProperties(request.getContractTypeCode());
        Contract contract = new Contract();
        contract.setName(request.getName());
        contract.setCode(TypeContract.HDTT + service.generateContractCode());
        contract.setEffectEndDate(request.getEffectEndDate() != null ? request.getEffectEndDate().toInstant().atOffset(ZoneOffset.UTC) : null);
        contract.setStatus(request.getStatusType());
        contract.setCreatorType(request.getCreatorType());
        contract.setTotalPayment(request.getTotalPayment());
        contract.setStationId(request.getStationId());
        contract.setFullAddress(request.getFullAddress());
        contract.setEmployeeCreator(detailEmployee);
        contract.setContractType(completableFuture.join().getData());
        checkTransportMethod(request.getTransportMethodCode(), contract);
        contract.setPayMethod(completableFuture.join().getData());
        checkProfile(request.getProfileId(), contract);
        contract.getCustomer().setRankId(request.getRankId());
        checkProduct(request.getProductInfoRequests(), contract);
        if (!CollectionUtils.isEmpty(request.getAttachments()))
            checkFile(request.getAttachments(), contract);

        return contract;
    }

    public Contract mapToEntityPlan(DetailEmployeeResponse detailEmployee, CreateContractPlanRequest request) {

        if (request.getEffectEndDate() != null && request.getEffectEndDate().before(Date.from(Instant.now())))
            throw new BusinessException(ErrorCode.EFFECT_END_DATE_INVALID, ErrorMessage.EFFECT_END_DATE_INVALID);

        CompletableFuture<Response<PropertiesResponse>> completableFuture = publicManagementCaller.getProperties(request.getContractTypeCode());
        checkPayDateTime(request);
        Contract contract = new Contract();
        contract.setName(request.getName());
        contract.setCode(TypeContract.HDDT + service.generateContractCode());
        contract.setStatus(request.getStatusType());
        contract.setCreatorType(request.getCreatorType());
        contract.setLimitMoney(request.getLimitMoney());
        checkTransportMethod(request.getTransportMethodCode(), contract);
        checkProfile(request.getProfileId(), contract);
        contract.setContractType(completableFuture.join().getData());
        contract.setPayMethod(completableFuture.join().getData());
        contract.setEmployeeCreator(detailEmployee);
        if (!CollectionUtils.isEmpty(request.getAttachments()))
            checkFile(request.getAttachments(), contract);
        contract.setDateOfPayment(request.getDateOfPayment());
        contract.setCountPayment(request.getCountPayment());
        contract.setEffectEndDate(request.getEffectEndDate() != null ? request.getEffectEndDate().toInstant().atOffset(ZoneOffset.UTC) : null);

        return contract;
    }

    public Contract mapUpdatePlan(Contract contract, DetailEmployeeResponse detailEmployee, ContractPlanRequest request) {

        if (request.getEffectEndDate() != null && request.getEffectEndDate().before(Date.from(Instant.now())))
            throw new BusinessException(ErrorCode.EFFECT_END_DATE_INVALID, ErrorMessage.EFFECT_END_DATE_INVALID);

        CompletableFuture<Response<PropertiesResponse>> completableFuture = publicManagementCaller.getProperties(request.getPayMethodCode());
        contract.setStatus(request.getStatusType());
        contract.setCreatorType(request.getCreatorType());
        contract.setLimitMoney(request.getLimitMoney());
        checkTransportMethod(request.getTransportMethodCode(), contract);
        contract.setPayMethod(completableFuture.join().getData());
        contract.setEmployeeCreator(detailEmployee);
        checkFile(request.getAttachmentIds(), contract);
        contract.setDateOfPayment(request.getDateOfPayment());
        contract.setCountPayment(request.getCountPayment());
        contract.setEffectEndDate(request.getEffectEndDate() != null ? request.getEffectEndDate().toInstant().atOffset(ZoneOffset.UTC) : null);

        return contract;
    }

    public Contract mapUpdatePre(Contract contract, DetailEmployeeResponse detailEmployee, ContractPreRequest request) {

        if (request.getEffectEndDate() != null && request.getEffectEndDate().before(Date.from(Instant.now())))
            throw new BusinessException(ErrorCode.EFFECT_END_DATE_INVALID, ErrorMessage.EFFECT_END_DATE_INVALID);

        CompletableFuture<Response<PropertiesResponse>> completableFuture = publicManagementCaller.getProperties(request.getPayMethodCode());
        contract.setStatus(request.getStatusType());
        contract.setCreatorType(request.getCreatorType());
        contract.setTotalPayment(request.getTotalPayment());
        contract.setStationId(request.getStationId());
        contract.setFullAddress(request.getFullAddress());
        contract.setEmployeeCreator(detailEmployee);
        contract.getCustomer().setRankId(request.getRankId());
        contract.setPayMethod(completableFuture.join().getData());
        checkTransportMethod(request.getTransportMethodCode(), contract);
        checkProduct(request.getProductInfoRequests(), contract);
        checkFile(request.getAttachmentIds(), contract);
        contract.setEffectEndDate(request.getEffectEndDate() != null ? request.getEffectEndDate().toInstant().atOffset(ZoneOffset.UTC) : null);

        return contract;
    }

    public ContractDetailResponse mapToResponseDetails(Contract contract) {

        ContractDetailResponse response = new ContractDetailResponse();
        StationResponse stationResponse = new StationResponse().setAreaType(AreaType.AREA_2).setName("Tổng công ty");
        if (contract.getStationId() == null) {
            mapToDetail(contract, response);
            response.setCountPayment(contract.getCountPayment());
        }
        if (contract.getStationId() != null && contract.getStationId() == 0) {
            mapToDetail(contract, response);
            if (contract.getCountPayment() != null)
                response.setCountPayment(contract.getCountPayment());
            response.setStation(stationResponse);
        } else {
            if (contract.getStationId() != null && publicManagementCaller.checkStation(contract.getStationId()) != null) {
                mapToDetail(contract, response);
                response.setStation(publicManagementCaller.checkStation(contract.getStationId()).join().getData());
            }
        }
        return response;
    }

    private void mapToDetail(Contract contract, ContractDetailResponse response) {

        response.setCode(contract.getCode())
                .setName(contract.getName())
                .setCreatorType(contract.getCreatorType())
                .setCustomerId(contract.getCustomerId())
                .setCreateAt(contract.getCreatedAt())
                .setEffectEndDate(contract.getEffectEndDate())
                .setProduct(contract.getProduct())
                .setContractType(contract.getContractType())
                .setPayMethod(contract.getPayMethod())
                .setTransportMethod(contract.getTransportMethod())
                .setAttachment(contract.getAttachment())
                .setEmployeeCreator(contract.getEmployeeCreator())
                .setCustomer(contract.getCustomer())
                .setStatus(contract.getStatus())
                .setId(contract.getId())
                .setTotalPayment(contract.getTotalPayment())
                .setFullAddress(contract.getFullAddress())
                .setRejectReason(contract.getRejectReason())
                .setApproveDate(contract.getApproveDate())
                .setLimitMoney(contract.getLimitMoney())
                .setDateOfPayment(contract.getDateOfPayment());
    }

    private void checkPayDateTime(CreateContractPlanRequest request) {

        if (request.getDateOfPayment().getPaymentTimeOne() != null && request.getDateOfPayment().getPaymentTimeOne().before(Date.from(Instant.now())))
            throw new BusinessException(ErrorCode.PAYMENT_DATE_INVALID, ErrorMessage.PAYMENT_DATE_INVALID);

        if (request.getDateOfPayment().getPaymentTimeTwo() != null && request.getDateOfPayment().getPaymentTimeTwo().before(Date.from(Instant.now())))
            throw new BusinessException(ErrorCode.PAYMENT_DATE_INVALID, ErrorMessage.PAYMENT_DATE_INVALID);

        if (request.getDateOfPayment().getPaymentTimeThree() != null && request.getDateOfPayment().getPaymentTimeThree().before(Date.from(Instant.now())))
            throw new BusinessException(ErrorCode.PAYMENT_DATE_INVALID, ErrorMessage.PAYMENT_DATE_INVALID);

        if (request.getDateOfPayment().getPaymentTimeFour() != null && request.getDateOfPayment().getPaymentTimeFour().before(Date.from(Instant.now())))
            throw new BusinessException(ErrorCode.PAYMENT_DATE_INVALID, ErrorMessage.PAYMENT_DATE_INVALID);

        if (request.getDateOfPayment().getPaymentTimeFive() != null && request.getDateOfPayment().getPaymentTimeFive().before(Date.from(Instant.now())))
            throw new BusinessException(ErrorCode.PAYMENT_DATE_INVALID, ErrorMessage.PAYMENT_DATE_INVALID);
    }

    private void checkProfile(int id, Contract contract) {
        ProfileResponse profileResponse = driverCaller.getProfile(id).join().getData();
        if (profileResponse == null)
            throw new BusinessException(ErrorCode.PROFILE_IS_NULL, "Profile with this id not exist " + id);
        contract.setCustomer(profileResponse);
        contract.setCustomerId(profileResponse.getAccountId());
    }

    private void checkTransportMethod(PropertiesCode code, Contract contract) {
        CompletableFuture<Response<PropertiesResponse>> checkTransportMethod = publicManagementCaller.getTransportMethod(code);
        PropertiesResponse transportMethod = checkTransportMethod.join().getData();
        contract.setTransportMethod(transportMethod);
    }

    private void checkProduct(List<ProductInfoRequest> infoRequests, Contract contract) {
        List<ProductCategoryResponse> responseList = new ArrayList<>();
        infoRequests.forEach(it -> {
            ProductCategoryResponse productCategoryResponse = waveHouseCaller.getProduct(it.getProductId(), it.getCategoryProductId()).join().getData();
            if (productCategoryResponse == null)
                throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, ErrorMessage.PRODUCT_NOT_FOUND);
            productCategoryResponse.getProductResponse().setAmount(it.getAmount());
            productCategoryResponse.getProductResponse().setDiscount(it.getDiscount());
            productCategoryResponse.getProductResponse().setUnit(it.getUnit());
            productCategoryResponse.getProductResponse().setTotalMoney(it.getTotalMoney());
            productCategoryResponse.getProductResponse().setPrice(it.getPrice());

            responseList.add(productCategoryResponse);
        });
        contract.setProduct(responseList);
    }

    private void checkFile(Set<Integer> fileIds, Contract contract) {
        List<FileResponse> responseList = new ArrayList<>();
        fileIds.forEach(it -> {

            FileResponse fileResponse = publicManagementCaller.getFile(it).join().getData();
            if (fileResponse == null)
                throw new BusinessException(ErrorCode.FILE_NOT_FOUND, ErrorMessage.FILE_NOT_FOUND);
            responseList.add(fileResponse);
        });
        contract.setAttachment(responseList);
    }

    public Contract map(Contract contract, DetailEmployeeResponse detailEmployee, StatusType status, String reason) {
        return contract.setEmployeeApproveId(detailEmployee.getEmployeeId())
                .setEmployeeApprove(detailEmployee)
                .setApproveDate(OffsetDateTime.now())
                .setStatus(status)
                .setRejectReason(reason);
    }
}
