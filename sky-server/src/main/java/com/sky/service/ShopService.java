package com.sky.service;

import org.springframework.stereotype.Service;

public interface ShopService {
    void setStatus(Integer status);

    int getStatus();
}
