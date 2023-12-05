package com.accountingProcessor.accountingProcessor.services;

import com.accountingProcessor.accountingProcessor.dto.AccountingEntries;
import com.accountingProcessor.accountingProcessor.model.AccountingModel;
import com.accountingProcessor.accountingProcessor.repository.AccountingPostingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountingPostingService {
    private AccountingPostingRepository accountingRepository;

    @Autowired
    public AccountingPostingService(AccountingPostingRepository accountingRepository) {
        this.accountingRepository = accountingRepository;
    }


    public List<AccountingModel> getTransactions(String txnRefNo) {

        return  accountingRepository.findBytxnRefNo(txnRefNo)
                .stream()
                .map((a)-> new ModelMapper().map(a,AccountingModel.class))
                .collect(Collectors.toList());
    }

    public List<AccountingModel> saveTransaction(List<AccountingModel> txnList)
        {
            List<AccountingEntries> accountingEntriesList = txnList
                    .stream()
                    .map((a)-> new ModelMapper().map(a, AccountingEntries.class))
                    .collect(Collectors.toList());

            return accountingRepository.saveAll(accountingEntriesList)
                    .stream()
                    .map((a)-> new ModelMapper().map(a,AccountingModel.class))
                    .collect(Collectors.toList());

        }
}
