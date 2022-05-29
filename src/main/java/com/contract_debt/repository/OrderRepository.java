package com.contract_debt.repository;

import com.contract_debt.model.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Contract, Integer> {


}
