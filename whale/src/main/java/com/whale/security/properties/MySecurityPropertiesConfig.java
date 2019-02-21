package com.whale.security.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@EnableConfigurationProperties(MySecurityproperties.class)//使配置生效
public class MySecurityPropertiesConfig {

}
