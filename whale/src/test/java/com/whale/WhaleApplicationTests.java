package com.whale;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.whale.model.UserInfor;
import com.whale.repostitory.UserInforRepostitory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WhaleApplicationTests {
	@Resource
	private UserInforRepostitory userInforRepostitory;
	@Test
	public void contextLoads() {
	}

	@Test
	public void test1() {
	Optional<UserInfor> userInfor = userInforRepostitory.findById("1");
	
		System.out.println(userInfor);
	}
}
