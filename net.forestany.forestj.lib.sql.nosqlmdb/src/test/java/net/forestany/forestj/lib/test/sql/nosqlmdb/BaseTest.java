package net.forestany.forestj.lib.test.sql.nosqlmdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * base test class for nosqlmdb tests
 */
public class BaseTest {
	/**
	 * base test method for nosqlmdb tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testBaseNOSQMDB() {
		try {
			net.forestany.forestj.lib.test.sql.nosqlmdb.SetBase.setBase();
			beforeBaseSQLiteTest();
			net.forestany.forestj.lib.test.sqltest.BaseTest.testBase();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	/**
	 * prepare base test for nosqlmdb tests
	 * @throws Exception
	 */
	private void beforeBaseSQLiteTest() throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		/* insert category records */
		String s_jsonCategoriesFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "nosqlmdb" + net.forestany.forestj.lib.io.File.DIR + "categories.json";
		
		net.forestany.forestj.lib.io.File o_categoriesFile = new net.forestany.forestj.lib.io.File(s_jsonCategoriesFile);
		
		java.util.List<org.bson.Document> a_categoriesDocuments = net.forestany.forestj.lib.sql.nosqlmdb.BaseNoSQLMDB.jsontoListOfBSONDocuments(o_categoriesFile.getFileContent());
		
		net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create> o_queryCreate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.CREATE, "sys_forestj_categories");
		@SuppressWarnings("unused")
		java.util.List<java.util.LinkedHashMap<String, Object>> a_result = o_glob.Base.fetchQuery(o_queryCreate);
		
		for (org.bson.Document o_insertObject : a_categoriesDocuments) {
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_categories");
			
			for (java.util.Map.Entry<String, Object> o_entry : o_insertObject.entrySet()) {
				o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, o_entry.getKey()), o_entry.getValue()) );
			}
			
			a_result = o_glob.Base.fetchQuery(o_queryInsert);
		}
		
		/* insert product records */
		String s_jsonProductsFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "nosqlmdb" + net.forestany.forestj.lib.io.File.DIR + "products.json";
		
		net.forestany.forestj.lib.io.File o_productsFile = new net.forestany.forestj.lib.io.File(s_jsonProductsFile);
		
		java.util.List<org.bson.Document> a_productsDocuments = net.forestany.forestj.lib.sql.nosqlmdb.BaseNoSQLMDB.jsontoListOfBSONDocuments(o_productsFile.getFileContent());
		
		o_queryCreate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.CREATE, "sys_forestj_products");
		a_result = o_glob.Base.fetchQuery(o_queryCreate);
		
		for (org.bson.Document o_insertObject : a_productsDocuments) {
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_products");
			
			for (java.util.Map.Entry<String, Object> o_entry : o_insertObject.entrySet()) {
				o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, o_entry.getKey()), o_entry.getValue()) );
			}
			
			a_result = o_glob.Base.fetchQuery(o_queryInsert);
		}
	}
}
