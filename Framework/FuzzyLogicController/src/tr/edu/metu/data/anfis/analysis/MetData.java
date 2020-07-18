package tr.edu.metu.data.anfis.analysis;

public class MetData {
	private String dateStr; // dd-mm-yyyy
	private String actualVal;
	private String predictedVal;

	public MetData(String dateStr, String actualVal, String predictedVal) {
		super();
		this.dateStr = dateStr;
		this.actualVal = actualVal;
		this.predictedVal = predictedVal;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getActualVal() {
		return actualVal;
	}

	public void setActualVal(String actualVal) {
		this.actualVal = actualVal;
	}

	public String getPredictedVal() {
		return predictedVal;
	}

	public void setPredictedVal(String predictedVal) {
		this.predictedVal = predictedVal;
	}

}
