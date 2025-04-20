package net.forestany.forestj.lib.test.sql.pool;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test base pool
 */
public class BasePoolTest {
	/**
	 * class to test base pool
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testBasePool() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testBasePool" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does not exist"
			);
			
			net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
			
			/* o_glob.LogCompleteSqlQuery(true); */
			
			for (java.util.Map.Entry<String, Integer> o_baseGatewayEntry : BaseCredentials.BaseGateways().entrySet()) {
				try {
					o_glob.BaseGateway = net.forestany.forestj.lib.sqlcore.BaseGateway.valueOf(o_baseGatewayEntry.getKey());
					System.out.println(java.time.LocalDateTime.now() + " - " + o_glob.BaseGateway + " started");
					
					/* create o_glob.Base connection and get credentials for base pool */
					BaseCredentials o_baseCredentials = new BaseCredentials(s_testDirectory);
					
					/* prepare database for base pool test */
					prepareDB();
					
					/* create base pool */
					net.forestany.forestj.lib.sql.pool.BasePool o_basePool = new net.forestany.forestj.lib.sql.pool.BasePool(
						3,
						new net.forestany.forestj.lib.DateInterval("PT10S"),
						1000,
						o_baseCredentials.e_baseGatewayBaseThread,
						o_baseCredentials.s_hostBaseThread,
						o_baseCredentials.s_datasourceBaseThread,
						o_baseCredentials.s_userBaseThread,
						o_baseCredentials.s_passwordBaseThread
					);
					
					/* create result list */
					java.util.List<Double> a_doubleList = new java.util.ArrayList<Double>();
					
					/* create class with runnable to use base pool */
					ThreadUsingBasePool o_threadUsingBasePool = new ThreadUsingBasePool(new java.util.ArrayList<>(java.util.Arrays.asList(o_basePool, a_doubleList)));

					/* create threads */
					Thread o_thread1 = new Thread(o_threadUsingBasePool);
					Thread o_thread2 = new Thread(o_threadUsingBasePool);
					Thread o_thread3 = new Thread(o_threadUsingBasePool);
					Thread o_thread4 = new Thread(o_threadUsingBasePool);
					Thread o_thread5 = new Thread(o_threadUsingBasePool);
					Thread o_thread6 = new Thread(o_threadUsingBasePool);
					Thread o_thread7 = new Thread(o_threadUsingBasePool);
					Thread o_thread8 = new Thread(o_threadUsingBasePool);
					Thread o_thread9 = new Thread(o_threadUsingBasePool);
					Thread o_thread10 = new Thread(o_threadUsingBasePool);
					
					/* start base pool */	
					o_basePool.start();
					
					/* wait 11500 milliseconds so base pool will test connection */
					Thread.sleep(11500);
					
					/* start threads */
					o_thread1.start();
					o_thread2.start();
					o_thread3.start();
					o_thread4.start();
					o_thread5.start();
					o_thread6.start();
					o_thread7.start();
					o_thread8.start();
					o_thread9.start();
					o_thread10.start();
					/* wait for threads to be finished */
					o_thread1.join();
					o_thread2.join();
					o_thread3.join();
					o_thread4.join();
					o_thread5.join();
					o_thread6.join();
					o_thread7.join();
					o_thread8.join();
					o_thread9.join();
					o_thread10.join();
					
					/* stop base pool */
					o_basePool.stop();
					
					/* wait for base pool to be finished */
					Thread.sleep(1000);
					
					/* recreate db connection */
					o_baseCredentials = new BaseCredentials(s_testDirectory);
					/* clean up database */
					cleanDB();
					
					/* check result list */
					double d_firstValue = a_doubleList.get(0);
					/* we expect that each thread has calculated the same average result */
					for (double d_value : a_doubleList) {
						assertEquals(d_firstValue, d_value, "unexpected average value '" + d_value + "', expected '" + d_firstValue + "'");
					}
					
					System.out.println(d_firstValue);
					System.out.println(java.time.LocalDateTime.now() + " - " + o_glob.BaseGateway + " finished");
				} catch (Exception o_exc) {
					throw o_exc;
				} finally {
					o_glob.Base.closeConnection();
				}
			}
			
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			assertFalse(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does exist"
			);
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
	
	private void prepareDB() {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		try {
			/* create table */
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create> o_queryCreate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.CREATE, "sys_forestj_data");
			java.util.List<java.util.Properties> a_columnsDefinition = new java.util.ArrayList<java.util.Properties>();
			
			java.util.Properties o_properties = new java.util.Properties();
			o_properties.put("name", "Id");
			o_properties.put("columnType", "integer [int]");
			o_properties.put("constraints", "NOT NULL;PRIMARY KEY;AUTO_INCREMENT");
			a_columnsDefinition.add(o_properties);
			
			/* we do not need Id column here, because object id _id is enough for this */
            if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB)
            {
                a_columnsDefinition.clear();
            }
			
			o_properties = new java.util.Properties();
			o_properties.put("name", "Value");
			o_properties.put("columnType", "double");
			o_properties.put("constraints", "NOT NULL");
			a_columnsDefinition.add(o_properties);
			
			for (java.util.Properties o_columnDefinition : a_columnsDefinition) {
				net.forestany.forestj.lib.sql.ColumnStructure o_column = new net.forestany.forestj.lib.sql.ColumnStructure(o_queryCreate);
				o_column.columnTypeAllocation(o_columnDefinition.getProperty("columnType"));
				o_column.s_name = o_columnDefinition.getProperty("name");
				o_column.setAlterOperation("ADD");
				
				if (o_columnDefinition.containsKey("constraints")) {
					String[] a_constraints = o_columnDefinition.getProperty("constraints").split(";");
					
					for (int i = 0; i < a_constraints.length; i++) {
						o_column.addConstraint(o_queryCreate.constraintTypeAllocation(a_constraints[i]));
						
						if ( (a_constraints[i].compareTo("DEFAULT") == 0) && (o_columnDefinition.containsKey("constraintDefaultValue")) ) {
							o_column.setConstraintDefaultValue((Object)o_columnDefinition.getProperty("constraintDefaultValue"));
						}
					}
				}
				
				o_queryCreate.getQuery().a_columns.add(o_column);
			}

			java.util.List<java.util.LinkedHashMap<String, Object>> a_result = o_glob.Base.fetchQuery(o_queryCreate);
			
			/* check table has been created */
			
			int i_expectedAffectedRows = 0;
			
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				i_expectedAffectedRows = 1;
			}
			
			assertTrue(
				a_result.size() == 1,
				"Result row amount of create query is not '1', it is '" + a_result.size() + "'"
			);
			
			java.util.Map.Entry<String, Object> o_resultEntry = a_result.get(0).entrySet().iterator().next();
			
			assertTrue(
				o_resultEntry.getKey().contentEquals("AffectedRows"),
				"Result row key of create query is not 'AffectedRows', it is '" + o_resultEntry.getKey() + "'"
			);
			
			assertTrue(
				Integer.valueOf(o_resultEntry.getValue().toString()) == i_expectedAffectedRows,
				"Result row value of create query is not '0', it is '" + o_resultEntry.getValue().toString() + "'"
			);
			
			/* fill table with data */
			
			int i_amount = 500_000;
			int i_amountRowValues = 10_000;
			
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) { /* for NOSQLMDB we must use single statements, because batch execution is not supported now */
				i_amount = 10_000;
				
				/* skip querying for last insert id */
				o_glob.Base.setSkipQueryLastInsertId(true);

				for (int i = 0; i < i_amount; i++) {
					net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_data");
					o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Value"), net.forestany.forestj.lib.Helper.randomDoubleRange(0.0, 100.0)) );
					o_glob.Base.fetchQuery(o_queryInsert, false);
				}
			} else { /* create insert batch statement for 10k values each statement */
				net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_data");
				o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Value"), net.forestany.forestj.lib.Helper.randomDoubleRange(0.0, 100.0)) );
				
				String s_query = o_queryInsert.toString();
				s_query = s_query.substring(0, s_query.indexOf("VALUES ") + 7);
				
				if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.ORACLE) { /* oracle must be different */
					s_query = "INSERT ALL "; 
				}
				
				StringBuilder o = new StringBuilder();
				o.append(s_query);
				
				if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.MSSQL) { /* mssql does not like more than 999 values in one insert batch statement */
					i_amountRowValues = 999;
				} else if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.ORACLE) { /* oracle is really slow, so we just insert 1k values in one insert batch statement, and just doing 100k */
					i_amount = 100_000;
					i_amountRowValues = 1_000;
				}
				
				/* deactivate auto commit and skip querying for last insert id */
				o_glob.Base.manualAutoCommit(false);
				o_glob.Base.setSkipQueryLastInsertId(true);
				
				for (int i = 0; i < i_amount; i++) {
					if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.ORACLE) { /* oracle must be different */
						o.append("INTO \"" + o_queryInsert.getTable() + "\" VALUES (" + (i + 1) + ", " + net.forestany.forestj.lib.Helper.randomDoubleRange(0.0, 100.0) + ") ");
					} else {
						o.append("(" + net.forestany.forestj.lib.Helper.randomDoubleRange(0.0, 100.0) + "),");
					}
					
					/* execute query after every i_amountRowValues'th value and the last one */
					if (((i % i_amountRowValues == 0) || (i == (i_amount - 1))) && (i != 0)) {
						String s_foo = o.toString();
						
						if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.ORACLE) { /* oracle must be different */
							o_queryInsert.setQuery(s_foo + "SELECT * FROM dual");
						} else {
							o_queryInsert.setQuery(s_foo.substring(0, s_foo.length() - 1) + ";");
						}
						
						o_glob.Base.fetchQuery(o_queryInsert, false);
						
						o = new StringBuilder();
						o.append(s_query);
					}
				}
				
				/* commit insert batch statement */
				o_glob.Base.manualCommit();
				/* activate auto commit */
				o_glob.Base.manualAutoCommit(true);
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
		} finally {
			o_glob.Base.closeConnection();
		}
	}
	
	private void cleanDB() {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		try {
			/* drop table */
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop> o_queryDrop = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.DROP, "sys_forestj_data");
			
			java.util.List<java.util.LinkedHashMap<String, Object>> a_result = o_glob.Base.fetchQuery(o_queryDrop);
			
			/* check that table has been dropped */
			
			int i_expectedAffectedRows = 0;
			
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				i_expectedAffectedRows = 1;
			}
			
			assertTrue(
				a_result.size() == 1,
				"Result row amount of query is not '1', it is '" + a_result.size() + "'"
			);
			
			java.util.Map.Entry<String, Object> o_resultEntry = a_result.get(0).entrySet().iterator().next();
			
			assertTrue(
				o_resultEntry.getKey().contentEquals("AffectedRows"),
				"Result row key of query is not 'AffectedRows', it is '" + o_resultEntry.getKey() + "'"
			);
			
			assertTrue(
				Integer.valueOf(o_resultEntry.getValue().toString()) == i_expectedAffectedRows,
				"Result row value of query is not '" + i_expectedAffectedRows + "', it is '" + o_resultEntry.getValue().toString() + "'"
			);
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
		} finally {
			o_glob.Base.closeConnection();
		}
	}
	
	private class ThreadUsingBasePool implements Runnable {
		private java.util.List<Object> a_param = new java.util.ArrayList<Object>();
		
		private net.forestany.forestj.lib.sql.pool.BasePool getBasePool() {
			return (net.forestany.forestj.lib.sql.pool.BasePool)this.a_param.get(0);
		}
		
		@SuppressWarnings("unchecked")
		private java.util.List<Double> getDoubleList() {
			return (java.util.List<Double>)this.a_param.get(1);
		}
		
		public ThreadUsingBasePool(java.util.List<Object> p_a_param) {
			if (p_a_param.size() != 2) {
				throw new IllegalArgumentException("Parameter object list must have '2' elements");
			}
			
			if (!(p_a_param.get(0) instanceof net.forestany.forestj.lib.sql.pool.BasePool)) {
				throw new IllegalArgumentException("Parameter object list with object #1 must be a 'BasePool' object, but is '" + p_a_param.get(0).getClass().getTypeName() + "'");
			}
			
			if (!(p_a_param.get(1) instanceof java.util.List)) {
				throw new IllegalArgumentException("Parameter object list with object #1 must be a 'java.util.List' object, but is '" + p_a_param.get(1).getClass().getTypeName() + "'");
			}
			
			this.a_param = p_a_param;
		}
		
		public void run() {
			try {
				/* select all data from table */
				net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select> o_querySelect = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select>(this.getBasePool().getBaseGateway(), net.forestany.forestj.lib.sqlcore.SqlType.SELECT, "sys_forestj_data");
				o_querySelect.getQuery().a_columns.add(new net.forestany.forestj.lib.sql.Column(o_querySelect, "*"));
				
				java.util.List<java.util.LinkedHashMap<String, Object>> a_rows = this.getBasePool().fetchQuery(o_querySelect);
				
				if (a_rows != null) {
					double d_sum = 0.0d;
					/* iterate each row and sum up Value column */
					for (java.util.LinkedHashMap<String, Object> o_row : a_rows) {
						d_sum += Double.valueOf( o_row.get("Value").toString() );
					}
					/* get average value and add it to result list */
					this.getDoubleList().add(d_sum/a_rows.size());
				} else {
					net.forestany.forestj.lib.Global.ilogWarning("Could not execute query and retrieve a result");
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.ilogSevere(o_exc.getMessage());
			}
		}
	}
}
