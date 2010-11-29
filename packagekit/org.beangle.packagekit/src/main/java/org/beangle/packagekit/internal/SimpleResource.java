/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.internal;

import java.util.List;

import org.beangle.packagekit.Repository;
import org.beangle.packagekit.Resource;

public class SimpleResource implements Resource {

	private String id;
	private String name;
	private String summary;
	private String description;
	private int size;

	private String packageName;

	private Repository repository;

	private String groups;
	private String version;
	private boolean optional;

	private List<Resource> dependencies;

	public SimpleResource() {
	}

	public SimpleResource(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNewerThen(Resource other) {
		return id.compareTo(other.getId()) > 0;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public List<Resource> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Resource> dependencies) {
		this.dependencies = dependencies;
	}

}
