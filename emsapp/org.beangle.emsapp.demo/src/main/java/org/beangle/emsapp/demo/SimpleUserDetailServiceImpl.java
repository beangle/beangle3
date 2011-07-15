package org.beangle.emsapp.demo;

import java.util.ArrayList;

import org.beangle.ems.security.service.UserToken;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

public class SimpleUserDetailServiceImpl implements UserDetailService<Authentication> {

	public UserDetail loadDetail(Authentication token) {
		UserToken user = new UserToken(1L, token.getName(), token.getName(), "NULL", "user category a", true,
				false, false, false, new ArrayList<GrantedAuthority>());
		return user;
	}

}
