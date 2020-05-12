package com.mtl.cypw.web;

import com.mtl.cypw.common.utils.PropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.FileInputStream;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-18 12:03
 */

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableFeignClients(basePackages = "com.mtl")
@EnableEurekaClient
@ComponentScan(basePackages = {"com.mtl", "com.juqitech"})
@Slf4j
@EnableWebMvc
public class TktStarWebAppBoot {

    public static void main(String[] args) {
        try {
            // TODO: 根据不同业务端的配置放置目录调整

//            PropertyRepository.init("cypw-global.properties");
//            PropertyRepository.init("cypw-web.properties");
            System.getProperties().load(new FileInputStream("C:\\Users\\admin\\Desktop\\MTL\\cypw-global.properties"));
            System.getProperties().load(new FileInputStream("C:\\Users\\admin\\Desktop\\MTL\\cypw-web.properties"));

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(0);
        }
        SpringApplication.run(TktStarWebAppBoot.class, args);
    }


}
