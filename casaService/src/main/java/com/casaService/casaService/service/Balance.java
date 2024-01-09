package com.casaService.casaService.service;

import com.casaService.casaService.model.BalanceUpdateRequest;
import com.casaService.casaService.model.MessagePublisherRequest;

public interface Balance {

    public void updateBalance(BalanceUpdateRequest balanceUpdateRequest);

}
