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
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.http.agent;

public enum Engine {

  /**
   * Trident is the the Microsoft layout engine, mainly used by Internet
   * Explorer.
   */
  TRIDENT("Trident"),
  /**
   * HTML parsing and rendering engine of Microsoft Office Word, used by some
   * other products of the Office suite instead of Trident.
   */
  WORD("Microsoft Office Word"),
  /**
   * Open source and cross platform layout engine, used by Firefox and many
   * other browsers.
   */
  GECKO("Gecko"),
  /**
   * Layout engine based on KHTML, used by Safari, Chrome and some other
   * browsers.
   */
  WEBKIT("WebKit"),
  /**
   * Proprietary layout engine by Opera Software ASA
   */
  PRESTO("Presto"),
  /**
   * Original layout engine of the Mozilla browser and related products.
   * Predecessor of Gecko.
   */
  MOZILLA("Mozilla"),
  /**
   * Layout engine of the KDE project
   */
  KHTML("KHTML"),
  /**
   * Other or unknown layout engine.
   */
  OTHER("Other");

  String name;

  private Engine(String name) {
    this.name = name;
  }

}
