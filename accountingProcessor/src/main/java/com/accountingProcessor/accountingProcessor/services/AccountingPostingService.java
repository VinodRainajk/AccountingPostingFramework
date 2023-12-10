package com.accountingProcessor.accountingProcessor.services;

import com.accountingProcessor.accountingProcessor.clients.ExchangeRateClient;
import com.accountingProcessor.accountingProcessor.dto.AccountingEntries;
import com.accountingProcessor.accountingProcessor.dto.CurrencyExchangeRate;
import com.accountingProcessor.accountingProcessor.model.AccountingModel;
import com.accountingProcessor.accountingProcessor.repository.AccountingPostingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AccountingPostingService {
    private AccountingPostingRepository accountingRepository;
    private ExchangeRateClient exchangeRateClient;

    @Autowired
    public AccountingPostingService(AccountingPostingRepository accountingRepository, ExchangeRateClient exchangeRateClient) {
        this.accountingRepository = accountingRepository;
        this.exchangeRateClient= exchangeRateClient;
    }

   BiPredicate<String ,String > checkCurrency = (ccy1,ccy2) -> ccy1 ==ccy2;

    public List<AccountingModel> getTransactions(String txnRefNo) {

        return  accountingRepository.findBytxnRefNo(txnRefNo)
                .stream()
                .map((a)-> new ModelMapper().map(a,AccountingModel.class))
                .collect(Collectors.toList());
    }

    public List<AccountingModel> saveTransaction(List<AccountingModel> txnList)
        {
            // Loop through the Transactions
            // if the LCY = FCy do nothing
            // else I need to Call the exchange Rate and populate the corresponding Amount
            // if LCY <> FCY and FCY is Null populate FCY amount
            // Else Populate LCY Amount

            for(int idx = 0 ; idx < txnList.size(); idx++)
            {
                if( !txnList.get(idx).getLccy().equals(txnList.get(idx).getFccy()))
                {
                    if( txnList.get(idx).getLcyamount()==null || txnList.get(idx).getLcyamount()==0)
                    {
                        CurrencyExchangeRate exchangeRate = exchangeRateClient.getExchangeRate(txnList.get(idx).getFccy(), txnList.get(idx).getLccy());
                        txnList.get(idx).setExchRate(exchangeRate.getExchangeRate());
                        txnList.get(idx).setLcyamount(exchangeRate.getExchangeRate()*txnList.get(idx).getFcyAmount());
                    } else {
                        CurrencyExchangeRate exchangeRate = exchangeRateClient.getExchangeRate(txnList.get(idx).getLccy(), txnList.get(idx).getFccy());
                        txnList.get(idx).setExchRate(exchangeRate.getExchangeRate());
                        txnList.get(idx).setFcyAmount(exchangeRate.getExchangeRate()*txnList.get(idx).getLcyamount());
                    }
                }
            }

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
