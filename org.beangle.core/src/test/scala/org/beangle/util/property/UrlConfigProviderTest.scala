/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.util.property

import org.scalatest.FunSuite

class UrlConfigProviderTest extends FunSuite{
  test("Get Url Config properties"){
    val localtion=new ConfigResource()
    location.global = classOf[UrlPropertyConfigProvider].getResource("/system-default.properties")
    location.user = classOf[UrlPropertyConfigProvider].getResource("/system.properties")
    val provider= new UrlPropertyConfigProvider()
    provider.resource=location;
    val config = new PropertyConfigBean(provider.config)
    assert(1 == config(classOf[Integer],"testInt"))
    assert("beangle.org",config("system.vendor"))
    assert("http://localhost",config("system.url"))
  }
}
