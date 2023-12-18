package com.accountingProcessor.accountingProcessor.services;

import com.accountingProcessor.accountingProcessor.dto.AccountBalanceUpdateRequest;
import com.accountingProcessor.accountingProcessor.feingclients.CasaServiceClient;
import com.accountingProcessor.accountingProcessor.feingclients.ExchangeRateClient;
import com.accountingProcessor.accountingProcessor.dto.AccountingEntries;
import com.accountingProcessor.accountingProcessor.dto.CurrencyExchangeRate;
import com.accountingProcessor.accountingProcessor.model.AccountingModel;
import com.accountingProcessor.accountingProcessor.model.TransactionResponseModel;
import com.accountingProcessor.accountingProcessor.repository.AccountingPostingRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Service
public class AccountingPostingService {
    private AccountingPostingRepository accountingRepository;
    private ExchangeRateClient exchangeRateClient;
    private CasaServiceClient casaServiceClient;

    @Autowired
    public AccountingPostingService(AccountingPostingRepository accountingRepository, ExchangeRateClient exchangeRateClient,CasaServiceClient casaServiceClient) {
        this.accountingRepository = accountingRepository;
        this.exchangeRateClient= exchangeRateClient;
        this.casaServiceClient = casaServiceClient;
    }

   BiPredicate<String ,String > checkCurrency = (ccy1,ccy2) -> ccy1 ==ccy2;

    public List<AccountingModel> getTransactions(String txnRefNo) {

        return  accountingRepository.findBytxnRefNo(txnRefNo)
                .stream()
                .map((a)-> new ModelMapper().map(a,AccountingModel.class))
                .collect(Collectors.toList());
    }

   @Transactional
    public ResponseEntity saveTransaction(List<AccountingModel> txnList)
        {
            // Loop through the Transactions
            // if the LCY = FCy do nothing
            // else I need to Call the exchange Rate and populate the corresponding Amount
            // if LCY <> FCY and FCY is Null populate FCY amount
            // Else Populate LCY Amount

            for(int idx = 0 ; idx < txnList.size(); idx++)
            {
                if( !txnList.get(idx).getLccy().equals(txnList.get(idx).getAccy()))
                {
                    if( txnList.get(idx).getLcyamount()==null || txnList.get(idx).getLcyamount()==0)
                    {
                        CurrencyExchangeRate exchangeRate = exchangeRateClient.getExchangeRate(txnList.get(idx).getAccy(), txnList.get(idx).getLccy());
                        txnList.get(idx).setExchRate(exchangeRate.getExchangeRate());
                        txnList.get(idx).setLcyamount(exchangeRate.getExchangeRate()*txnList.get(idx).getAcyAmount());
                    } else {
                        CurrencyExchangeRate exchangeRate = exchangeRateClient.getExchangeRate(txnList.get(idx).getLccy(), txnList.get(idx).getAccy());
                        txnList.get(idx).setExchRate(exchangeRate.getExchangeRate());
                        txnList.get(idx).setAcyAmount(exchangeRate.getExchangeRate()*txnList.get(idx).getLcyamount());
                    }
                }
            }


            List<AccountBalanceUpdateRequest> balanceUpdateReqList = txnList
                                                                     .stream()
                                                                     .map((a) -> new AccountBalanceUpdateRequest(a.getTxnRefNo(),a.getCustAccno(),a.getDrcr(),a.getAcyAmount()))
                                                                     .collect(Collectors.toList());


            ResponseEntity<List<AccountBalanceUpdateRequest>> casaBalanceUpdateResponse = casaServiceClient.updateCustomerbalance(balanceUpdateReqList);

            HttpStatusCode statusCode = casaBalanceUpdateResponse.getStatusCode();
          //  String message = casaBalanceUpdateResponse.

            if (!statusCode.is2xxSuccessful()) {
                return TransactionResponseModel.generateResponse("Failed To Process Request", HttpStatus.EXPECTATION_FAILED,txnList);
            }

            List<AccountingEntries> accountingEntriesList = txnList
                    .stream()
                    .map((a)-> new ModelMapper().map(a, AccountingEntries.class))
                    .collect(Collectors.toList());

             accountingRepository.saveAll(accountingEntriesList)
                    .stream()
                    .map((a)-> new ModelMapper().map(a,AccountingModel.class))
                    .collect(Collectors.toList());

            return TransactionResponseModel.generateResponse("Successfully To Processed Request", HttpStatus.OK,txnList);

        }
}
