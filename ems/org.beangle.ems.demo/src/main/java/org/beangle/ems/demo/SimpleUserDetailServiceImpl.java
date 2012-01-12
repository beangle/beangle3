package org.beangle.ems.demo;

import java.util.ArrayList;

import org.beangle.ems.security.service.UserToken;
import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

public class SimpleUserDetailServiceImpl implements UserDetailService {

	public UserDetail loadDetail(String principle) {
		UserToken user = new UserToken(1L, principle, principle, "NULL", "user category a", true,
				false, false, false, new ArrayList<GrantedAuthority>());
		return user;
	}

}
