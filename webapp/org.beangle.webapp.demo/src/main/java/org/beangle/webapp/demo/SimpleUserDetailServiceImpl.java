package org.beangle.webapp.demo;

import java.util.ArrayList;

import org.beangle.security.blueprint.model.CategoryBean;
import org.beangle.security.blueprint.service.UserToken;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

public class SimpleUserDetailServiceImpl implements UserDetailService<Authentication> {

	public UserDetail loadDetail(Authentication token) {
		UserToken user = new UserToken(1L, token.getName(), token.getName(), "NULL", new CategoryBean(1L),
				true, false, false, false, new ArrayList<GrantedAuthority>());
		System.out.println(user.getCategory().getId());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return user;
	}

}
