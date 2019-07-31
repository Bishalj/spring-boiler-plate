package com.application.utils.v1.Demo;

import com.application.mappers.v1.DemoResponseMapper;
import com.application.models.v1.Demo;
import org.springframework.http.HttpStatus;

public class DemoResponseMapperUtils {

    public static DemoResponseMapper createSuccessResponse(Demo demo){
        DemoResponseMapper demoResponseMapper = new DemoResponseMapper();
        demoResponseMapper.setMessage("SuccessFul");
        demoResponseMapper.setDemo(demo);
        demoResponseMapper.setStatus(HttpStatus.resolve(200));
        return demoResponseMapper;
    }

    public static DemoResponseMapper createErrorResponse(String message){
        DemoResponseMapper demoResponseMapper = new DemoResponseMapper();
        demoResponseMapper.setMessage(message);
        demoResponseMapper.setStatus(HttpStatus.resolve(500));
        return demoResponseMapper;
    }
}
