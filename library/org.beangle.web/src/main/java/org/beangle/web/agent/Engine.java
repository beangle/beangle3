/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.agent;

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
