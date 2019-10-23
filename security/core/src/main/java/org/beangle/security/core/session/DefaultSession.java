/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.core.session;

import java.time.Instant;

import org.beangle.security.core.userdetail.DefaultAccount;

public class DefaultSession implements Session {

  private String id;
  private DefaultAccount principal;
  private Instant loginAt;
  private Instant lastAccessAt;
  private Session.Agent agent;

  private int ttiMinutes;

  public DefaultSession() {
    super();
  }

  public DefaultSession(String id, DefaultAccount principal, Instant loginAt, Agent agent) {
    super();
    this.id = id;
    this.principal = principal;
    this.loginAt = loginAt;
    this.agent = agent;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DefaultAccount getPrincipal() {
    return principal;
  }

  public void setPrincipal(DefaultAccount principal) {
    this.principal = principal;
  }

  public Instant getLoginAt() {
    return loginAt;
  }

  public void setLoginAt(Instant loginAt) {
    this.loginAt = loginAt;
  }

  public Instant getLastAccessAt() {
    return lastAccessAt;
  }

  public void setLastAccessAt(Instant lastAccessAt) {
    this.lastAccessAt = lastAccessAt;
  }

  public Session.Agent getAgent() {
    return agent;
  }

  public void setAgent(Session.Agent agent) {
    this.agent = agent;
  }

  @Override
  public int getTtiMinutes() {
    return ttiMinutes;
  }

  public void setTtiMinutes(int ttiMinutes) {
    this.ttiMinutes = ttiMinutes;
  }
}
