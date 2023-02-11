package com.trafficbank.trafficbank.application.outPort;

import com.trafficbank.trafficbank.domain.Account;

public interface LoadAccountPort {
    Account loadAccount(String accountNumber);
}
