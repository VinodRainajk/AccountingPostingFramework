package com.accountingProcessor.accountingProcessor.repository;

import com.accountingProcessor.accountingProcessor.entity.AccountingEntries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountingPostingRepository extends JpaRepository<AccountingEntries,Integer> {

    public List<AccountingEntries> findBytxnRefNo(String txnRefNo);

}
