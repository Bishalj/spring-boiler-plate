package com.application.services.v1.demo.impl;

import com.application.services.v1.demo.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeanFactoryImpl {

    @Autowired
    private IDemoService demoService;

    public IDemoService getDemoService(){
        return demoService;
    }
}
