package net.forestany.forestj.lib.test.nettest.sock.https;

/**
 * seed class for testing
 */
public class TestSeed extends net.forestany.forestj.lib.net.https.dynm.ForestSeed {
	/**
	 * empty constructor
	 */
	public TestSeed() {
		
	}
	
	/**
	 * prepare content method of ForestSeed
	 */
	@Override
	public void prepareContent() throws Exception {
		this.getSeed().getTemp().put("key1", "value1");
		this.getSeed().getTemp().put("key2", "value2");
		this.getSeed().getTemp().put("key3", "value3");
		this.getSeed().getTemp().put("key4", "value4");
		
		java.util.Map<String, Object> a_foo1 = new java.util.HashMap<String, Object>(
			java.util.Map.of(
				"0", "a",
				"1", "b",
				"2", "c",
				"3", "d",
				"4", "e"
			)
		);
		this.getSeed().getTemp().put("list1", a_foo1);
		
		java.util.Map<String, Object> a_subFoo1 = new java.util.HashMap<String, Object>(
			java.util.Map.of(
				"0", "000",
				"1", "001",
				"2", "010",
				"3", "011",
				"4", "100",
				"5", "101",
				"6", "110",
				"7", "111"
			)
		);	
		
		java.util.Map<String, Object> a_subFoo2 = new java.util.HashMap<String, Object>(
			java.util.Map.of(
				"0", 9,
				"1", 8,
				"2", 7,
				"3", 6
			)
		);
		
		java.util.Map<String, Object> a_subFoo3 = new java.util.HashMap<String, Object>(
			java.util.Map.of(
				"0", true,
				"1", false,
				"2", false,
				"3", true,
				"4", true
			)
		);
		
		java.util.Map<String, Object> a_foo2 = new java.util.HashMap<String, Object>(
			java.util.Map.of(
				"0", a_subFoo1,
				"1", a_subFoo2,
				"2", a_subFoo3
			)
		);
		
		this.getSeed().getTemp().put("list2", a_foo2);
		
		this.getSeed().getTemp().put("list3", java.util.Arrays.asList("one", "two", "three", "four", "five"));
		
		this.getSeed().getTemp().put("set", java.util.Set.of(8, 7, 5, 6)); /* with output this always will be rendered sorted */
		
		this.getSeed().getTemp().put("collection", java.util.Arrays.asList("z", "y", "x", "w"));
		
		java.util.Map<String, Object> a_record1 = new java.util.HashMap<String, Object>(
			java.util.Map.of(
				"name", "Max Mustermann",
				"street", "Berlin Street 1",
				"country", "DE",
				"age", 23
			)
		);
		
		java.util.Map<String, Object> a_record2 = new java.util.HashMap<String, Object>(
			java.util.Map.of(
				"name", "Kim Day",
				"street", "London Street 1",
				"country", "GB",
				"age", 28
			)
		);
		
		java.util.Map<String, Object> a_record3 = new java.util.HashMap<String, Object>(
			java.util.Map.of(
				"name", "Timothy Johnson",
				"street", "Washington Street 1",
				"country", "US",
				"age", 36
			)
		);
		
		this.getSeed().getTemp().put("records", java.util.Arrays.asList(a_record1, a_record2, a_record3));
		
		if (this.getSeed().getPostData().size() > 0) {
			java.util.Map<String, Object> a_postData = new java.util.HashMap<String, Object>();
			
			this.getSeed().getPostData().entrySet().forEach(o_postDataEntry -> {
				a_postData.put(o_postDataEntry.getKey(), o_postDataEntry.getValue());
				//System.out.println(o_postDataEntry.getKey() + " = " + o_postDataEntry.getValue());
			});
			
			this.getSeed().getTemp().put("postdata", a_postData);
		}
		
		if (this.getSeed().getFileData().size() > 0) {
			java.util.Map<String, Object> a_fileData = new java.util.HashMap<String, Object>();
			
			this.getSeed().getFileData().forEach(o_fileDataEntry -> {
				a_fileData.put("field", o_fileDataEntry.getFieldName());
				a_fileData.put("filename", o_fileDataEntry.getFileName());
				a_fileData.put("contenttype", o_fileDataEntry.getContentType());
				a_fileData.put("filelength", o_fileDataEntry.getFileData().length);
				//System.out.println(o_fileDataEntry.getFieldName() + " | " + o_fileDataEntry.getFileName() + " | " + o_fileDataEntry.getContentType() + " | " + o_fileDataEntry.getFileData().length);
			});
			
			this.getSeed().getTemp().put("filedata", java.util.Arrays.asList(a_fileData));
		}
	}
}
