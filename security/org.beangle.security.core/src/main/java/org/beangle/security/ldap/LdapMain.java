/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.security.ldap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.beangle.commons.lang.Strings;
import org.beangle.security.ldap.auth.DefaultLdapValidator;
import org.beangle.security.ldap.auth.LdapValidator;
import org.beangle.security.ldap.connect.LdapUserStore;
import org.beangle.security.ldap.connect.SimpleLdapUserStore;

public class LdapMain {

  private static void println(String msg) {
    println(msg);
  }

  private SimpleLdapUserStore getStore(String url, String username, String password, String base) {
    return new SimpleLdapUserStore(url, username, password, base);
  }

  private void tryGet(LdapUserStore store, String name) {
    String dn = store.getUserDN(name);
    String pwd = store.getPassword(name);
    if (null == dn) {
      println("Cannot find :" + name);
    } else {
      println("Find:" + name);
      println("dn:" + dn);
      println("pwd:" + pwd);
    }
  }

  private void tryTestPassword(LdapUserStore store, String name, String password) {
    LdapValidator ldapValidator = new DefaultLdapValidator(store);
    boolean isTrue = ldapValidator.verifyPassword(name, password);
    println("password " + (isTrue ? " ok! " : " WRONG!"));
  }

  public static void main(String[] args) throws IOException {
    String host, username, password, base;
    if (args.length < 4) {
      println("Usage: LdapMain host:port username password base");
      return;
    } else {
      host = args[0];
      username = args[1];
      password = args[2];
      base = args[3];
    }
    LdapMain a = new LdapMain();
    println("Connecting to ldap://" + host);
    println("Using base:" + base);
    SimpleLdapUserStore store = a.getStore("ldap://" + host, username, password, base);
    println("Enter query user[/password]: ");
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    String value = stdin.readLine();
    while (Strings.isNotBlank(value)) {
      String myname = value;
      String mypass = null;
      if (value.contains("/")) {
        myname = Strings.substringBefore(value, "/");
        mypass = Strings.substringAfter(value, "/");
      }
      try {
        a.tryGet(store, myname);
        if (null != mypass) {
          a.tryTestPassword(store, myname, mypass);
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        println("Enter query user[/password]: ");
      }
      value = stdin.readLine();
    }
  }

}
