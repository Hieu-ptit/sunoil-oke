package com.contract_debt.repository;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigInteger;

@RequiredArgsConstructor
@Repository
public class ExtraRepositoryImpl implements ExtraRepository {

    private final EntityManager entityManager;

    @Override
    public Long getNextValueSeq(String sequenceName) {

        if (StringUtils.isEmpty(sequenceName))
            return null;

        String query = String.format("SELECT nextval('%s')", sequenceName);
        BigInteger no = (BigInteger) entityManager.createNativeQuery(query).getSingleResult();

        return no.longValueExact();
    }


    public boolean refreshOrderCode(String sequenceName) {

        if (StringUtils.isEmpty(sequenceName))
            return false;

        String query = String.format("SELECT setval('%s', 1, true)", sequenceName);
        BigInteger no = (BigInteger) entityManager.createNativeQuery(query).getSingleResult();

        return no.longValueExact() > 0;
    }

}
