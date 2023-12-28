package com.accountingProcessor.accountingProcessor.services;

import com.accountingProcessor.accountingProcessor.dto.BalanceUpdateRequest;
import com.accountingProcessor.accountingProcessor.dto.DebitCreditEnum;
import com.accountingProcessor.accountingProcessor.feingclients.CasaServiceClient;
import com.accountingProcessor.accountingProcessor.feingclients.ExchangeRateClient;
import com.accountingProcessor.accountingProcessor.dto.AccountingEntries;
import com.accountingProcessor.accountingProcessor.dto.CurrencyExchangeRate;
import com.accountingProcessor.accountingProcessor.model.AccountingModel;
import com.accountingProcessor.accountingProcessor.model.CurrencyExchangeRateModel;
import com.accountingProcessor.accountingProcessor.model.TransactionResponseModel;
import com.accountingProcessor.accountingProcessor.repository.AccountingPostingRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                        ResponseEntity responseEntity = exchangeRateClient.getExchangeRate(txnList.get(idx).getAccy(), txnList.get(idx).getLccy());
                        System.out.println("responseEntity response "+responseEntity.getStatusCode());
                        System.out.println("responseEntity getBody "+responseEntity.getBody());
                        CurrencyExchangeRate exchangeRate = (CurrencyExchangeRate)responseEntity.getBody();
                        System.out.println("got the exchnage Rate inside IF "+ exchangeRate.getExchangeRate());
                        txnList.get(idx).setExchRate(exchangeRate.getExchangeRate());
                        txnList.get(idx).setLcyamount(exchangeRate.getExchangeRate()*txnList.get(idx).getAcyAmount());
                    } else {
                        ResponseEntity responseEntity = exchangeRateClient.getExchangeRate(txnList.get(idx).getLccy(), txnList.get(idx).getAccy());
                        System.out.println("else responseEntity response "+responseEntity.getStatusCode());
                        System.out.println("else responseEntity getBody "+responseEntity.getBody());
                        CurrencyExchangeRateModel currencyExchangeRateModel =  (CurrencyExchangeRateModel)responseEntity.getBody();

                        //CurrencyExchangeRateModel exchangeRate = responseEntity.getBody();
                        System.out.println("got the exchnage Rate inside else "+ currencyExchangeRateModel.getExchangeRate());
                        txnList.get(idx).setExchRate(currencyExchangeRateModel.getExchangeRate());
                        txnList.get(idx).setAcyAmount(currencyExchangeRateModel.getExchangeRate()*txnList.get(idx).getLcyamount());
                    }
                }
            }

            ModelMapper modelMapper = new ModelMapper();

            BalanceUpdateRequest balanceUpdateReq= new BalanceUpdateRequest(txnList.get(0).getTxnRefNo(),txnList.get(0).getCustAccno(),txnList.get(0).getDrcr(),txnList.get(0).getAcyAmount());
            System.out.println(balanceUpdateReq);
            List<BalanceUpdateRequest> BalanceUpdateRequestList = new ArrayList<>();
            BalanceUpdateRequestList.add(balanceUpdateReq);
            //casaServiceClient.updateCustomerbalance(balanceUpdateReqList);
            ResponseEntity<Map<String,Object>> casaBalanceUpdateResponse = casaServiceClient.updatemultiCustomerbalance(BalanceUpdateRequestList);

            System.out.println("vinod 123 ");


            System.out.println("list of request "+ casaBalanceUpdateResponse);
            HttpStatusCode statusCode = HttpStatus.resolve((int)casaBalanceUpdateResponse.getBody().get("status"));
            List<BalanceUpdateRequest> responseforRequest = (List<BalanceUpdateRequest>)casaBalanceUpdateResponse.getBody().get("data");
            System.out.println("casaBalanceUpdateResponse.getBody() "+casaBalanceUpdateResponse.getBody().get("status"));
            System.out.println("list of request "+ responseforRequest);

            if (!statusCode.is2xxSuccessful()) {
                return TransactionResponseModel.generateResponse("Failed To Process Request " + casaBalanceUpdateResponse.getBody().get("message"), HttpStatus.EXPECTATION_FAILED,txnList);
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
