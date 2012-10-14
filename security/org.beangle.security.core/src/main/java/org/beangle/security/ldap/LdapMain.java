/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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

  private SimpleLdapUserStore getStore(String url, String username, String password, String base) {
    return new SimpleLdapUserStore(url, username, password, base);
  }

  private void tryGet(LdapUserStore store, String name) {
    String dn = store.getUserDN(name);
    String pwd = store.getPassword(name);
    if (null == dn) {
      System.out.println("Cannot find :" + name);
    } else {
      System.out.println("Find:" + name);
      System.out.println("dn:" + dn);
      System.out.println("pwd:" + pwd);
    }
  }

  private void tryTestPassword(LdapUserStore store, String name, String password) {
    LdapValidator ldapValidator = new DefaultLdapValidator(store);
    boolean isTrue = ldapValidator.verifyPassword(name, password);
    System.out.println("password " + (isTrue ? " ok! " : " WRONG!"));
  }

  public static void main(String[] args) throws IOException {
    String host, username, password, base;
    if (args.length < 4) {
      System.out.println("Usage: LdapMain host:port username password base");
      return;
    } else {
      host = args[0];
      username = args[1];
      password = args[2];
      base = args[3];
    }
    LdapMain a = new LdapMain();
    System.out.println("Connecting to ldap://" + host);
    System.out.println("Using base:" + base);
    SimpleLdapUserStore store = a.getStore("ldap://" + host, username, password, base);
    System.out.print("Enter query user[/password]: ");
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
        System.out.print("Enter query user[/password]: ");
      }
      value = stdin.readLine();
    }
  }

}
