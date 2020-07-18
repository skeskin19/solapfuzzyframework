package tr.edu.metu.ceng.sk.fuzzy.impl2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class FuzzyCMeansMain {

	static Double data_set[][] = new Double[20000][100];
	static Double diff[][] = new Double[20000][100];
	static Double eud[][] = new Double[20000][1000];
	static Double intial_centroid[][] = new Double[300][400];
	static Double new_center[][] = new Double[300][400];
	static int num = 0;
	static int row = 4;// rows in Your DataSet here i use iris dataset

	static int cnum;
	static int itc = 0;
	static int checker = 1;

	private static void readFile() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("../test_data.txt"));// Dataset path
		//scanner.useDelimiter(System.getProperty("line.separator"));
		scanner.useDelimiter(",");
		int lineNo = 0;
		while (scanner.hasNext()) {
			parseLine(scanner.next(), lineNo);
			lineNo++;
			System.out.println();
		}
		// System.out.println("total"+num); PRINT THE TOTAL
		scanner.close();
	}

	// read file is copey to the data_set
	public static void parseLine(String line, int lineNo) {
		itc = 0;
		Scanner lineScanner = new Scanner(line);
		lineScanner.useDelimiter(",");
		for (int col = 0; col < row; col++) {
			Double arry = lineScanner.nextDouble();
			data_set[num][col] = arry; /// here read data set is assign the
										/// variable data_set
		}
		num++;

	}

	public static void init() {
		for (int i = 0; i < num; i++) {
			data_set[i][row] = 0.0;
			data_set[i][row + 1] = 0.0;
		}
	}

	public static void print() {
		double re = 0;
		double a = 0;

		if (itc == 0) {

			System.out.println("ENTER K");
			Scanner sc = new Scanner(System.in);
			cnum = sc.nextInt(); // enter the number of cenroid
			System.out.println("centroid");
			for (int i = 0; i < cnum; i++) {
				for (int j = 0; j < row; j++) {

					intial_centroid[i][j] = data_set[i][j]; //// CENTROID ARE
															//// STORED IN AN
															//// intial_centroid
															//// variable
					System.out.print(intial_centroid[i][j]);
				}
				System.out.println();
			}

		} else {

			for (int i = 0; i < cnum; i++) {
				for (int j = 0; j < row; j++) {
					intial_centroid[i][j] = new_center[i][j]; //// CENTROID ARE
																//// STORED IN
																//// AN
																//// intial_centroid
																//// variable
					System.out.print(intial_centroid[i][j]);
				}
				System.out.println();
			}

		}

		for (int i = 0; i < num; i++) {
			for (int j = 0; j < cnum; j++) {
				re = 0;
				for (int k = 0; k < row; k++) {
					a = (intial_centroid[j][k] - data_set[i][k]);
					// System.out.println(a);
					a = a * a;
					re = re + a; // store the row sum

				}

				diff[i][j] = Math.sqrt(re);// find the squre root
				System.out.println(diff[i][j]);

			}
		}

	}

	public static void s() {

		double b, c;
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < cnum; j++) {
				c = 0.0;
				b = 0.0;
				for (int k = 0; k < cnum; k++) {
					if (diff[i][k] == 0) {
						b = 0;
					}
					if (diff[i][k] != 0) {
						b = diff[i][j] / diff[i][k];
					}
					c = c + b;
				}
				if (c == 0) {
					eud[i][j] = 0.0;
				} else {
					eud[i][j] = 1 / c;
				}
			}

		}
		double a = 0;
		for (int i = 0; i < num; i++) {
			a = 0;
			for (int j = 0; j < cnum; j++) {
				a = a + eud[i][j];
				System.out.print(eud[i][j] + "    ");
			}
			System.out.print("total  " + a);
			System.out.println();
		}
		double aaa;
		int counter = 0;
		for (int i = 0; i < num; i++) {
			counter = 0;
			aaa = eud[i][0];
			for (int j = 0; j < cnum; j++) {
				if (aaa <= eud[i][j]) {
					aaa = eud[i][j];
					counter = j;
				}
			}
			if (itc % 2 == 0) {
				data_set[i][row] = (double) counter;
			}

			if (itc % 2 == 1) {
				data_set[i][row + 1] = (double) counter;
			}
		}
		for (int i = 0; i < num; i++) {
			for (int j = 0; j <= row + 1; j++) {
				System.out.print(data_set[i][j] + ",  ");

			}
			System.out.println();
		}
	}

	public static void newcenter() {
		itc++;
		double a = 0.0;
		double c = 0.0;
		double d = 0.0;
		double f = 0.0;
		for (int k = 0; k < cnum; k++) {
			for (int j = 0; j < row; j++) {
				a = 0.0;
				d = 0.0;

				c = 0.0;
				f = 0.0;
				for (int i = 0; i < num; i++) {
					// System.out.print("edu"+eud[i][k]);
					a = eud[i][k];
					a = a * a;
					c = c + a;
					// System.out.println("data"+data_set[i][j]);
					d = a * data_set[i][j];
					f = f + d;
				}
				new_center[k][j] = f / c;
				System.out.println("centroid new " + new_center[k][j]);
				// j=row+5;
				// k=cnum+5;

			}
		}
	}

	public static void print11() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("----OUTPUT----");
		int c = 0;
		int a = 0;
		for (int i = 0; i < cnum; i++) {
			System.out.println("---------CLUSTER-" + i + "-----");
			a = 0;
			for (int j = 0; j < num; j++) {
				if (data_set[j][row] == i) {
					a++;
					for (int k = 0; k < row; k++) {

						System.out.print(data_set[j][k] + "  ");
					}
					c++;
					System.out.println();
				}
				// System.out.println(num);

			}
			System.out.println("CLUSTER INSTANCES=" + a);

		}
		System.out.println("TOTAL INSTANCE" + c);
	}

	public static void check() {

		checker = 0;
		for (int i = 0; i < num; i++) {
			// System.out.println("hii");
			if (Double.compare(data_set[i][row], data_set[i][row + 1]) != 0)

			{
				checker = 1;
				// System.out.println("hii " + i + " " + data_set[i][4]+ "
				// "+data_set[i][4]);
				break;
			}
			System.out.println();
		}

	}

	public static void main(String[] args) throws FileNotFoundException {
		readFile();
		//
		init();
		while (checker != 0)
		// for(int i=0;i<5;i++)
		{

			print();
			s();
			newcenter();
			check();

		}
		print11();
	}
}
