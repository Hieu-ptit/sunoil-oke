package com.contract_debt.model.bo;

public interface ApiList {

    // DRIVER

    String API_ACCOUNT_IN_DRIVE = "/drivers/accounts/exists";
    String API_GET_DETAIL_CASH_LIMIT_DRIVE = "drivers/cash-limit/detail?account-id=%s&user-type=%s&product-id=%d";
    String API_GET_CASH_LIMIT_DRIVE = "drivers/cash-limit?account-id=%s&user-type=%s";
    String API_GET_PROFILE = "/drivers/profiles/%s";
    String API_UPDATE_DRIVER = "/drivers/driver-detail?driver-id=%s&amount=%d";
    // Enterprise
    String API_INCREMENT_LIMIT_ENTERPRISE = "/drivers/cash-limit/increment";


    // ADMIN Management
    String API_GET_ACCOUNT = "/management/accounts/information?id=%s";
    String API_GET_EMPLOYEE_BY_ACCOUNT_ID = "/management/employees/details?accountId=%s";
    // Public management
    String API_QR_CODE_IN_MANAGEMENT = "/management/qrs/infos";
    String API_GET_PROPERTIES = "/management/properties/contracts-type?code=%s";
    String API_GET_STATION = "/management/gas-stations/%s";
    String API_GET_FILE = "/management/files/%s";
    // Specific Management

    // Ware-house
    String API_GET_PRODUCT = "/management/products/category-product/%s?category-id=%s";
}
