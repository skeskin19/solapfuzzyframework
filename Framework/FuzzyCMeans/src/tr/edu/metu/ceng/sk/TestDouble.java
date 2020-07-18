package tr.edu.metu.ceng.sk;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TestDouble {

	private static DecimalFormat df2 = new DecimalFormat("#.##");
	static DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());

	public static void main(String[] args) {

		double input = 32.123456;
		System.out.println("double : " + input);
		System.out.println("double (default) : " + df2.format(input));

		df2.setRoundingMode(RoundingMode.UP);
		System.out.println("double (UP) : " + df2.format(input));

		 otherSymbols.setDecimalSeparator('.');
	      otherSymbols.setGroupingSeparator(','); 
	      df2.setDecimalFormatSymbols(otherSymbols);
	      
		df2.setRoundingMode(RoundingMode.DOWN);
		System.out.println("double (DOWN) : " + df2.format(input));

	}

}
