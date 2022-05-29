package com.contract_debt.repository;

import com.contract_debt.model.bo.StatusCommon;
import com.contract_debt.model.bo.StatusType;
import com.contract_debt.model.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer>, InsertUpdateRepository<Contract> {

    @Query(value = "select * from contracts where id not in ?1 and status <> ?2", nativeQuery = true)
    Page<Contract> findByStatusNot(List<Integer> ids, String status, Pageable pageable);

    @Query(value = "SELECT * FROM contracts t WHERE jsonb_extract_path_text(t.employee_creator, 'accountId') = ?1 and status <> ?2", nativeQuery = true)
    Page<Contract> findByCustomerCreatorAndStatusNot(String customerId, String status, Pageable pageable);

    @Query(value = "SELECT * FROM contracts t WHERE customer_id = ?1 and status <> ?2", nativeQuery = true)
    Page<Contract> findByCustomerIdAndStatusNot(String customerId, String status, Pageable pageable);

    List<Contract> findContractByCustomerIdAndStatusNot(String customerId, StatusCommon status);

    @Query(value = "select * from contracts where id not in ?1 and status <> ?2 and (lower (name) like ?3 or lower (code) like ?3 " +
            "or lower(jsonb_extract_path_text(contract_type, 'name')) like ?3 )", nativeQuery = true)
    Page<Contract> findContractBySearchText(List<Integer> ids, String status, String searchText, Pageable pageable);

    @Query(value = "select * from contracts t where jsonb_extract_path_text(t.employee_creator, 'accountId') = ?1 and status <> ?2 " +
            "and (lower (name) like ?3 or lower (code) like ?3 or lower(jsonb_extract_path_text(contract_type, 'name')) like ?3 )"
            , nativeQuery = true)
    Page<Contract> findContractBySearchTextAndCustomerId(String customerId, String status, String searchText, Pageable pageable);

    @Query(value = "select * from contracts t where customer_id = ?1 and status <> ?2 " +
            "and (lower (name) like ?3 or lower (code) like ?3 or lower(jsonb_extract_path_text(contract_type, 'name')) like ?3 )"
            , nativeQuery = true)
    Page<Contract> findContractForEnterpriseSearchTextAndCustomerId(String customerId, String status, String searchText, Pageable pageable);

    @Query(value = "select id from contracts where  employee_creator is null and status = ?1", nativeQuery = true)
    List<Integer> findContractHaveStatusDraft(String status);

    boolean existsByName(String name);

    @Query(value = "UPDATE contracts  SET status = 'DELETE' WHERE id = ?1", nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void delete(Integer id);

    Optional<Contract> findByIdAndStatus(Integer id, StatusType statusType);

    @Query(value = "SELECT * from contracts where id = ?1 and status in ('DRAFT', 'WAITING_ACCEPT')", nativeQuery = true)
    Contract findByIdAndStatusIn(Integer id);
}
