package com.casaService.casaService.service;

import com.casaService.casaService.dto.CustomerAccountOfflineBalance;
import com.casaService.casaService.model.BalanceUpdateRequest;
import com.casaService.casaService.model.MessagePublisherRequest;
import com.casaService.casaService.repository.BalanceRepository;
import com.casaService.casaService.repository.CustBalOfflineRepo;
import com.casaService.casaService.repository.CustomerAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfflineBalanceImpl implements  Balance{
    private static final Logger lOGGER = LogManager.getLogger(OfflineBalanceImpl.class);
    private ModelMapper maper = new ModelMapper();
    private CustBalOfflineRepo custBalOfflineRepo ;
    private CustomerAccountRepository customerAccountRepository;
    private BalanceRepository balanceRepository;

    @Autowired
    public OfflineBalanceImpl(CustBalOfflineRepo custBalOfflineRepo,
                              CustomerAccountRepository customerAccountRepository,
                              BalanceRepository balanceRepository)
    {
        this.custBalOfflineRepo = custBalOfflineRepo;
        this.customerAccountRepository = customerAccountRepository;
        this.balanceRepository = balanceRepository;
    }

    @Override
    public void updateBalance(BalanceUpdateRequest balanceUpdateRequest) {
        lOGGER.info("Inside  OfflineBalanceImpl updateBalance");
        CustomerAccountOfflineBalance customerAccountOfflineBalance = maper.map(balanceUpdateRequest, CustomerAccountOfflineBalance.class);
        customerAccountOfflineBalance.setStatus("U");
        CustomerAccountOfflineBalance returnedval=  custBalOfflineRepo.save(customerAccountOfflineBalance);


    }

    @Override
    public MessagePublisherRequest messageGenerator(BalanceUpdateRequest balanceUpdateRequest)
    {
        MessagePublisherRequest messagePublisherRequest = new MessagePublisherRequest();
        messagePublisherRequest.setBalanceUpdateRequest(balanceUpdateRequest);
        messagePublisherRequest.setCustomerAccNo(balanceUpdateRequest.getCustomerAccNo());

        messagePublisherRequest.setBalance(balanceRepository.getAccountBalance(balanceUpdateRequest.getCustomerAccNo()).getAccountBalance());
        messagePublisherRequest.setCustomerName(customerAccountRepository.getReferenceById(balanceUpdateRequest.getCustomerAccNo()).getCustomerName());
        messagePublisherRequest.setAccountStatus(customerAccountRepository.getReferenceById(balanceUpdateRequest.getCustomerAccNo()).getAccountStatus());
        return messagePublisherRequest;
    }
}
