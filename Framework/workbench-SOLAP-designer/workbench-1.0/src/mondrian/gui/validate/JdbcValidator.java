/*
// $Id: //open/mondrian/src/main/mondrian/gui/validate/JdbcValidator.java#2 $
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// Copyright (C) 2008-2009 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.gui.validate;

/**
 * Validation for database schema, table, and columns. Extracted interface from
 * <code>mondrian.gui.JDBCMetaData</code>.
 *
 * @author mlowery
 */
public interface JdbcValidator {
    /**
     * Returns the data type of given column.
     *
     * @return SQL type from java.sql.Types
     */
    int getColumnDataType(String schemaName, String tableName, String colName);

    /**
     * Returns true if column exists.
     */
    boolean isColExists(String schemaName, String tableName, String colName);

    /**
     * Returns true if table exists.
     */
    boolean isTableExists(String schemaName, String tableName);

    /**
     * Returns true if this object successfully connected to database (and
     * validation methods can now be called).
     */
    boolean isInitialized();

    /**
     * Returns true if schema exists.
     */
    boolean isSchemaExists(String schemaName);
}

// End JdbcValidator.java
