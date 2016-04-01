/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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

import java.util.List;

import org.beangle.commons.collection.CollectUtils;

/**
 * Browser Render Engine.
 * 
 * @author chaostone
 * @since 2.1
 */
public enum Engine {

  /**
   * Trident is the the Microsoft layout engine, mainly used by Internet
   * Explorer.
   */
  Trident("Trident"),
  /**
   * Open source and cross platform layout engine, used by Firefox and many
   * other browsers.
   */
  Gecko("Gecko"),
  /**
   * Layout engine based on KHTML, used by Safari, Chrome and some other
   * browsers.
   */
  WebKit("WebKit"),
  /**
   * Proprietary layout engine by Opera Software ASA
   */
  Presto("Presto"),
  /**
   * Original layout engine of the Mozilla browser and related products.
   * Predecessor of Gecko.
   */
  Mozilla("Mozilla"),
  /**
   * Layout engine of the KDE project
   */
  Khtml("KHTML"),
  /**
   * HTML parsing and rendering engine of Microsoft Office Word, used by some
   * other products of the Office suite instead of Trident.
   */
  Word("Microsoft Office Word"),
  /**
   * Other or unknown layout engine.
   */
  Other("Other");

  String name;

  List<BrowserCategory> browserCategories = CollectUtils.newArrayList();

  private Engine(String name) {
    this.name = name;
  }

  public List<BrowserCategory> getBrowserCategories() {
    return browserCategories;
  }

  void addCategory(BrowserCategory category) {
    browserCategories.add(category);
  }
}
