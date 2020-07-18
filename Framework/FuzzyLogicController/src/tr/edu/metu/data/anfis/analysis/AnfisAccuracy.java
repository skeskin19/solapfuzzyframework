package tr.edu.metu.data.anfis.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnfisAccuracy {
	static Map<String, MetData> dataMap = new HashMap<String, MetData>();
	public static void main(String[] args) throws Exception {
		
		dataMap = DataUtil.getRealtiveHumidityData();
		
		double totalActual = 0.0;
		double totalDiff = 0.0;
		int totalPredicted = 0;
		
		Set<String> keys=dataMap.keySet();
		for(String dt:keys) {
			double actualVal = Double.parseDouble(dataMap.get(dt).getActualVal());
			double predictedVal = Double.parseDouble(dataMap.get(dt).getPredictedVal());
			
			totalDiff = totalDiff+Math.abs(actualVal-predictedVal);
			totalActual = totalActual+actualVal;
			totalPredicted++;
		}
		System.out.println("Mean Absolute Error:"+" Total Diff:"+totalDiff+" Number of Predicted:"+totalPredicted+" [(totalDiff/totalPredicted)] =>"+(totalDiff/totalPredicted)+" degrees.");
		System.out.println("mean absolute percentage error (MAPE): mape = 100 * (errors / test_labels) =>"+(100*(totalDiff/totalActual)));
		System.out.println("accuracy = 100 - np.mean(mape)=>"+(100 - ((100*(totalDiff/totalActual))) )+" %");
	}

}
