package com.accountingProcessor.accountingProcessor.services;


import com.accountingProcessor.accountingProcessor.entity.AccountingEntries;
import com.accountingProcessor.accountingProcessor.model.AccountBalanceRequest;
import com.accountingProcessor.accountingProcessor.model.AccountingModel;
import com.accountingProcessor.accountingProcessor.model.TransactionResponseModel;
import com.accountingProcessor.accountingProcessor.repository.AccountingPostingRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountingPostingService {
    private AccountingPostingRepository accountingRepository;
    private ExchangeRate exchangeRate;
    private AccountBalances accountBalances;
    private MessagePublisher messagePublisher;
    private static final Logger LOGGER = LogManager.getLogger(AccountingPostingService.class);


    @Autowired
    public AccountingPostingService(AccountingPostingRepository accountingRepository,
                                    ExchangeRate exchangeRate,
                                    AccountBalances accountBalances,
                                    MessagePublisher messagePublisher) {
        this.accountingRepository = accountingRepository;
        this.exchangeRate = exchangeRate;
        this.accountBalances  = accountBalances;
        this.messagePublisher =  messagePublisher;
    }

    public List<AccountingModel> getTransactions(String txnRefNo) {

        return  accountingRepository.findBytxnRefNo(txnRefNo)
                .stream()
                .map((a)-> new ModelMapper().map(a,AccountingModel.class))
                .collect(Collectors.toList());
    }
   @Transactional
    public ResponseEntity saveTransaction(List<AccountingModel> txnList)
        {
            LOGGER.info("Inside the Save Transactions");

           for(int idx = 0 ; idx < txnList.size(); idx++)
            {
                Double derivedExchangeRate = txnList.get(idx).getExchRate();
                if(derivedExchangeRate== null || derivedExchangeRate ==0)
                {
                    derivedExchangeRate = exchangeRate.getDerviveExchangeRate(txnList.get(idx).getAccy(), txnList.get(idx).getLccy());
                    txnList.get(idx).setExchRate(derivedExchangeRate);
                }

                if(txnList.get(idx).getLcyamount() == null || txnList.get(idx).getLcyamount()==0)
                {
                    txnList.get(idx).setLcyamount(exchangeRate.getDerviveAmount(derivedExchangeRate,txnList.get(idx).getAcyAmount()));
                } else if (txnList.get(idx).getAcyAmount() == null || txnList.get(idx).getAcyAmount()==0)
                {
                    Double reversedRate = Double.valueOf(1/derivedExchangeRate);
                    LOGGER.info("reversedRate Rate is "+ reversedRate);
                    txnList.get(idx).setAcyAmount(exchangeRate.getDerviveAmount(reversedRate, txnList.get(idx).getLcyamount()));
                }
                LOGGER.info("Exchange Rate is "+ txnList.get(idx).getExchRate());
                LOGGER.info("LCY Amount is "+txnList.get(idx).getLcyamount());
                LOGGER.info("ACY Amount is "+txnList.get(idx).getAcyAmount());
            }


            List<AccountBalanceRequest> balanceUpdateRequestList =  txnList
                                                                    .stream()
                                                                    .map(a -> new AccountBalanceRequest(a.getTxnRefNo(),a.getCustAccno(),a.getDrcr(),a.getAcyAmount()))
                                                                    .collect(Collectors.toList());

           LOGGER.info("Sending the Transactions for balance Update");
            List<AccountingModel> savedAccounting = new ArrayList<>();
            if(accountBalances.updateCustomerBalance(balanceUpdateRequestList))
            {
                List<AccountingEntries> accountingEntriesList = txnList
                        .stream()
                        .map((a)-> new ModelMapper().map(a, AccountingEntries.class))
                        .collect(Collectors.toList());

                savedAccounting =   accountingRepository.saveAll(accountingEntriesList)
                                                        .stream()
                                                        .map((a)-> new ModelMapper().map(a,AccountingModel.class))
                                                        .collect(Collectors.toList());

            }

            LOGGER.info("Sending the Transactions for Kafka ");

                    savedAccounting
                    .stream()
                    .forEach(messagePublisher::SendMessage);

            LOGGER.info("Successfully processed all Transactions");
            return TransactionResponseModel.generateResponse("Successfully To Processed Request", HttpStatus.OK,savedAccounting);

        }
}
