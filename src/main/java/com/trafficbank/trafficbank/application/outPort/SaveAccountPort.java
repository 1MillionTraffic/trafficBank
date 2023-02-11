package com.trafficbank.trafficbank.application.outPort;

import com.trafficbank.trafficbank.domain.Account;

public interface SaveAccountPort {
    void saveAccount(Account account);
}
