package tr.edu.metu.ceng.sk.metrics;

public class GenerateDates {

	public static void main(String[] args) {
		
		for(int i=1;i<13;++i) {
			
			if(i%2==0) {
				for(int j=1;j<31;++j)
					System.out.println(i+""+String.format("%02d", j));
			} else {
				for(int j=1;j<32;++j)
					System.out.println(i+""+String.format("%02d", j));
			}
		}

	}

}
