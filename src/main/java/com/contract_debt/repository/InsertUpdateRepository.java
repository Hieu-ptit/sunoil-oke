package com.contract_debt.repository;

public interface InsertUpdateRepository<T> {
    <S extends T> S insert(S entity);

    <S extends T> S update(S entity);
}
