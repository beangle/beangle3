/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import static org.beangle.db.util.DataSourceUtil.getDataSource;

import org.apache.commons.lang.StringUtils;

public class ReplicatorMain {

	public static void main(String[] args) throws Exception {
		ReplicatorBuilder builder = new ReplicatorBuilder();
		if (args.length < 2) {
			System.out.println("Usage:ReplicatorMain datasource:dialect:schema targetsource:dialect:schema");
			System.exit(0);
		}
		String src = args[1];
		String tar = args[2];
		String source[] = StringUtils.split(src, ':');
		String target[] = StringUtils.split(tar, ':');
		builder.source(source[1], getDataSource(source[0])).schema(source[2]).tables("*").contraints("*")
				.sequences("*");
		builder.target(target[1], getDataSource(target[0])).schema(target[2]);
		Replicator replicator = builder.build();
		replicator.start();
	}
}
