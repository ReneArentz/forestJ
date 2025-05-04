package net.forestany.forestj.sandbox.util.net.https;

public class TestSeedSqlPool extends net.forestany.forestj.lib.net.https.dynm.ForestSeed {
	@Override
	public void prepareContent() throws Exception {
		
		/* get https config to access base pool */
		net.forestany.forestj.lib.net.sql.pool.HttpsConfig o_config = (net.forestany.forestj.lib.net.sql.pool.HttpsConfig)this.getSeed().getConfig();
		
		/* get data with PersonRecord class */
		PersonRecord o_record = new PersonRecord();
		
		/* use BasePool as other base source */
		o_record.setOtherBaseSource(
			new net.forestany.forestj.lib.sql.Record.IDelegate() {
				@Override public java.util.List<java.util.LinkedHashMap<String, Object>> OtherBaseSourceImplementation(net.forestany.forestj.lib.sqlcore.IQuery<?> p_o_sqlQuery) throws IllegalAccessException, RuntimeException, InterruptedException {
					return o_config.getBasePool().fetchQuery((net.forestany.forestj.lib.sql.Query<?>)p_o_sqlQuery);
				}
			}
		);
		
		/* get all person records */
		java.util.List<PersonRecord> a_rows = o_record.getRecords();
		/* our record list for dynamic page */
		java.util.List<java.util.Map<String, Object>> a_list = new java.util.ArrayList<java.util.Map<String, Object>>();
		
		/* iterate each row */
		if (a_rows != null) {
			for (PersonRecord o_row : a_rows) {
				/* transpose record to map */
				java.util.Map<String, Object> m_record = new java.util.HashMap<String, Object>();
				
				m_record.put("Id", o_row.ColumnId);
				m_record.put("PersonalIdentificationNumber", o_row.ColumnPersonalIdentificationNumber);
				m_record.put("Name", o_row.ColumnName);
				m_record.put("Age", o_row.ColumnAge);
				m_record.put("City", o_row.ColumnCity);
				m_record.put("Country", o_row.ColumnCountry);
				
				a_list.add(m_record);
			}
		}
		
		/* add list to seed temp */
		this.getSeed().getTemp().put("records", a_list);
	}
}
