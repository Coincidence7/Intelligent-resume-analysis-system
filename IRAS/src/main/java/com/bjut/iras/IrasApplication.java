package com.bjut.iras;

import com.bjut.iras.service.QueryConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IrasApplication {

    public static void main(String[] args) {
        QueryConstants.initFilters();
        SpringApplication.run(IrasApplication.class, args);
    }

}
