/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.cas;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.security.cas.auth.CasAuthenticationProvider;
import org.beangle.security.cas.auth.vendor.NeusoftCasAliveChecker;
import org.beangle.security.cas.auth.vendor.NeusoftCasTicketValidator;
import org.beangle.security.cas.validation.Cas20ServiceTicketValidator;
import org.beangle.security.cas.web.CasEntryPoint;
import org.beangle.security.cas.web.CasPreauthFilter;

/**
 * CAS Default Module
 * 
 * @author chaostone
 */
public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(CasConfig.class, CasEntryPoint.class, NeusoftCasAliveChecker.class,
        Cas20ServiceTicketValidator.class, CasAuthenticationProvider.class, CasPreauthFilter.class,
        NeusoftCasTicketValidator.class).shortName();
  }

}
