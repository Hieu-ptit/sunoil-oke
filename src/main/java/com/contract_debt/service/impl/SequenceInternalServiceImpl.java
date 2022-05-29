package com.contract_debt.service.impl;

import com.common.exception.BusinessException;
import com.common.util.ErrorCode;
import com.contract_debt.repository.ExtraRepository;
import com.contract_debt.service.SequenceInternalService;
import com.contract_debt.util.Global;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SequenceInternalServiceImpl implements SequenceInternalService {

    private final ExtraRepository extraRepository;

    @Override
    public String generateContractCode() {

        Long nextNoOrder = extraRepository.getNextValueSeq(Global.CONTRACT_CODE_NO_SEQUENCE);

        if (nextNoOrder == null)
            throw new BusinessException(ErrorCode.COULD_NOT_GENERATE_CODE, "Could not Generate code for object");

        String datetime = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyy"));
        String no = String.format("%03d", nextNoOrder);

        return datetime + no;
    }


}
