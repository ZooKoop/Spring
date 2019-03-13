package com.whale.security.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.whale.security.model.SecurityUser;
import com.whale.security.repository.SecurityUserRepository;

@Service
public class SecurityService implements UserDetailsService {
	@Autowired
	private SecurityUserRepository securityUserRepository;

	 @Autowired
	 private PasswordEncoder passwordEncoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SecurityUser user = securityUserRepository.findByuserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		Logger log = Logger.getLogger("名字随便起");
		log.info("登录用户名：" + username); // 根据用户名查找用户信息
		// 静态权限：admin 这些在实际开发中需要从数据库中获取
		return new User(user.getUserName(),user.getUserPassword(),true,true,true,true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//		return new User(user.getUserName(),passwordEncoder.encode(user.getUserPassword()),true,true,true,true,
//				AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}
}
