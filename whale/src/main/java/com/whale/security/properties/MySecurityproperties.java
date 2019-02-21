package com.whale.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="my.security")
public class MySecurityproperties {
	//总配置
	private BrowserProperties browserProperties = new BrowserProperties();

	public BrowserProperties getBrowserProperties() {
		return browserProperties;
	}

	public void setBrowserProperties(BrowserProperties browserProperties) {
		this.browserProperties = browserProperties;
	}
	
}
