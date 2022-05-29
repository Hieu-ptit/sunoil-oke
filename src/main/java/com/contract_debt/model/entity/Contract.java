package com.contract_debt.model.entity;


import com.contract_debt.converter.*;
import com.contract_debt.model.bo.CreatorType;
import com.contract_debt.model.bo.StatusType;
import com.contract_debt.model.request.PaymentTime;
import com.contract_debt.model.response.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity(name = "contracts")
@Table(indexes = {
//        @Index(name="idx_driver_phone", columnList = "phone"),
//        @Index(name="idx_driver_status", columnList = "status"),
})
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Contract extends BaseEntity {

    @Id
    @SequenceGenerator(name = "contracts_id_seq", sequenceName = "contracts_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contracts_id_seq")
    Integer id;

    @Column(length = 100, nullable = false)
    String name;

    @Column(length = 50, nullable = false)
    String code;

    @Column(columnDefinition = "timestamp")
    OffsetDateTime effectEndDate;

    @Column(columnDefinition = "timestamp")
    OffsetDateTime approveDate;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonConverterPaymentTime.class)
    PaymentTime dateOfPayment;

    @Column(length = 64, nullable = false)
    String customerId;

    @Column
    Integer stationId;

    @Column(length = 200)
    String fullAddress;

    @Column(columnDefinition = "jsonb", nullable = false)
    @Convert(converter = JsonConverterProperties.class)
    PropertiesResponse contractType;

    @Column(columnDefinition = "jsonb", nullable = false)
    @Convert(converter = JsonConverterProperties.class)
    PropertiesResponse payMethod;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonConverterProperties.class)
    PropertiesResponse transportMethod;

    @Column(columnDefinition = "jsonb", nullable = false)
    @Convert(converter = JsonConverterCustomer.class)
    ProfileResponse customer;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonConverterProductList.class)
    List<ProductCategoryResponse> product;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonConverterAttachmentList.class)
    List<FileResponse> attachment;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonConverterEmployee.class)
    DetailEmployeeResponse employeeApprove;

    @Column(columnDefinition = "int default 0")
    int employeeApproveId;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonConverterEmployee.class)
    DetailEmployeeResponse employeeCreator;

    @Column(columnDefinition = "varchar(20)", nullable = false)
    @Enumerated(EnumType.STRING)
    CreatorType creatorType;

    @Column(columnDefinition = "decimal(15,2) default '0'")
    BigDecimal totalPayment;

    @Column(columnDefinition = "varchar(300)")
    String rejectReason;

    @Column(columnDefinition = "varchar(20) default 'DRAFT'", nullable = false)
    @Enumerated(EnumType.STRING)
    StatusType status;

    @Column(columnDefinition = "decimal(18,0) default '0'")
    BigDecimal limitMoney;

    @Column
    Integer countPayment;
}
