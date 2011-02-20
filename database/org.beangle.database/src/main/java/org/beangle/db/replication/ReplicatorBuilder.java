/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.dialect.Dialect;
import org.beangle.db.dialect.Dialects;
import org.beangle.db.meta.Constraint;
import org.beangle.db.meta.Sequence;
import org.beangle.db.meta.Table;
import org.beangle.db.replication.impl.CompositeReplicator;
import org.beangle.db.replication.impl.ConstraintReplicator;
import org.beangle.db.replication.impl.DataReplicator;
import org.beangle.db.replication.impl.DefaultTableFilter;
import org.beangle.db.replication.impl.SequenceReplicator;
import org.beangle.db.replication.wrappers.DatabaseWrapper;

public final class ReplicatorBuilder {

	DatabaseSource source;
	DatabaseSource target;

	public DatabaseSource source(String dialectName, DataSource dataSource) {
		source = new DatabaseSource(dataSource, Dialects.getDialect(dialectName));
		return source;
	}

	public DatabaseSource target(String dialectName, DataSource dataSource) {
		target = new DatabaseSource(dataSource, Dialects.getDialect(dialectName));
		return target;
	}

	public Replicator build() {
		DatabaseWrapper sourceWrapper = source.buildWrapper();
		DatabaseWrapper targetWrraper = target.buildWrapper();

		DataReplicator dataReplicator = new DataReplicator(sourceWrapper, targetWrraper);
		dataReplicator.addAll(source.filterTables());

		ConstraintReplicator contraintRelicator = new ConstraintReplicator(sourceWrapper, targetWrraper);
		contraintRelicator.addAll(source.filterConstraints());

		SequenceReplicator sequenceReplicator = new SequenceReplicator(sourceWrapper, targetWrraper);
		sequenceReplicator.addAll(source.filterSequences());

		return new CompositeReplicator(dataReplicator, contraintRelicator, sequenceReplicator);
	}

	public final class DatabaseTarget {
		DataSource dataSource;
		Dialect dialect;
		String schema;
		String catelog;

		public DatabaseTarget(DataSource dataSource, Dialect dialect) {
			super();
			this.dataSource = dataSource;
			this.dialect = dialect;
		}

		public DatabaseTarget schema(String schema) {
			this.schema = schema;
			return this;
		}

		public DatabaseTarget catelog(String catelog) {
			this.catelog = catelog;
			return this;
		}
	}

	public final class DatabaseSource {
		DataSource dataSource;
		Dialect dialect;
		String schema;
		String catelog;
		List<Table> tables = CollectUtils.newArrayList();
		String[] includes;
		String[] excludes;

		DatabaseWrapper wrapper = null;

		Collection<String> tablenames = null;

		public DatabaseSource(DataSource dataSource, Dialect dialect) {
			super();
			this.dataSource = dataSource;
			this.dialect = dialect;
		}

		public DatabaseSource schema(String schema) {
			this.schema = schema;
			return this;
		}

		public DatabaseSource catelog(String catelog) {
			this.catelog = catelog;
			return this;
		}

		protected DatabaseWrapper buildWrapper() {
			wrapper = new DatabaseWrapper(dataSource, dialect, catelog, schema);
			return wrapper;
		}

		private Collection<String> filter(Set<String> finalTables) {
			DefaultTableFilter filter = new DefaultTableFilter();
			if (null != includes) {
				for (String include : includes)
					filter.addInclude(include);
			}
			if (null != excludes) {
				for (String exclude : excludes)
					filter.addExclude(exclude);
			}
			return filter.filter(finalTables);
		}

		public DatabaseSource tables(String... includes) {
			this.includes = includes;
			return this;
		}

		public DatabaseSource exclude(String... excludes) {
			this.excludes = excludes;
			return this;
		}

		public DatabaseSource indexes(String... indexes) {
			return this;
		}

		public DatabaseSource contraints(String... string) {
			return this;
		}

		public DatabaseSource sequences(String... string) {
			return this;
		}

		protected List<Table> filterTables() {
			if (null == tablenames) tablenames = filter(wrapper.getDatabase().getTables().keySet());
			List<Table> tables = CollectUtils.newArrayList();
			for (String name : tablenames) {
				tables.add(wrapper.getDatabase().getTable(name));
			}
			return tables;
		}

		protected List<Constraint> filterConstraints() {
			if (null == tablenames) tablenames = filter(wrapper.getDatabase().getTables().keySet());
			List<Constraint> contraints = CollectUtils.newArrayList();
			for (String name : tablenames) {
				Table table = wrapper.getDatabase().getTable(name);
				contraints.addAll(table.getForeignKeys().values());
			}
			return contraints;
		}

		protected Collection<Sequence> filterSequences() {
			return wrapper.getDatabase().getSequences();
		}
	}

}
