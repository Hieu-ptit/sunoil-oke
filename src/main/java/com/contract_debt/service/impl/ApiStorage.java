package com.contract_debt.service.impl;

import com.contract_debt.model.bo.ApiList;
import com.contract_debt.model.bo.PropertiesCode;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiStorage {

    /**
     * Destination Micro Service
     */
    /**
     * Destination Micro Service
     */
    @Value("${app.base.url.driver}")
    public String urlDriver;
    @Value("${app.base.url.enterprise}")
    public String urlEnterprise;
    @Value("${app.base.url.management.pay}")
    public String urlPay;
    @Value("${app.base.url.management.admin}")
    public String urlAdminManagement;
    @Value("${app.base.url.management.public}")
    public String urlPublicManagement;
    @Value("${app.base.url.management.ware-house}")
    public String urlWaveHouseManagement;
    @Value("${app.base.url.management.contract_debt}")
    public String urlContractDebt;

    public String getProperties(PropertiesCode code) {
        return urlPublicManagement + String.format(ApiList.API_GET_PROPERTIES, code);
    }

    public String UpdateDetail(String driverId, Integer amount) {
        return urlDriver + String.format(ApiList.API_UPDATE_DRIVER, driverId, amount);
    }

    public String getAccountById(String id) {
        return urlAdminManagement + String.format(ApiList.API_GET_ACCOUNT, id);
    }

    public String getProfile(int id) {
        return urlDriver + String.format(ApiList.API_GET_PROFILE, id);
    }

    public String getProduct(int id, int categoryId) {
        return urlWaveHouseManagement + String.format(ApiList.API_GET_PRODUCT, id, categoryId);
    }

    public String findEmployeeByAccountId(String accountId) {
        return urlAdminManagement + String.format(ApiList.API_GET_EMPLOYEE_BY_ACCOUNT_ID, accountId);
    }

    public String increaseLimit() {
        return urlEnterprise + ApiList.API_INCREMENT_LIMIT_ENTERPRISE;
    }

    public String getStation(int id) {
        return urlPublicManagement + String.format(ApiList.API_GET_STATION, id);
    }

    public String getFile(int id) {
        return urlPublicManagement + String.format(ApiList.API_GET_FILE, id);
    }

}
