package com.trafficbank.trafficbank.application.outPort;

import com.trafficbank.trafficbank.domain.Activity;

public interface SaveActivityPort {
    void saveActivity(Activity activity);
}
