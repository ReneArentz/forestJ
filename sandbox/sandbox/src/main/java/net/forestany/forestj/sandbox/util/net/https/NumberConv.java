package net.forestany.forestj.sandbox.util.net.https;

public class NumberConv {
	public class NumberToWords {
		public long ubiNum;
		
		public String toString() {
			return "ubiNum = " + this.ubiNum;
		}
	}
	
	public class NumberToWordsResponse {
		public String NumberToWordsResult;
		
		public String toString() {
			return "NumberToWordsResult = " + this.NumberToWordsResult;
		}
	}
	
	public class NumberToDollars {
		public java.math.BigDecimal dNum;
		
		public String toString() {
			return "dNum = " + this.dNum;
		}
	}
	
	public class NumberToDollarsResponse {
		public String NumberToDollarsResult;
		
		public String toString() {
			return "NumberToDollarsResult = " + this.NumberToDollarsResult;
		}
	}
}
