package ai.rnt.pins.model;

public class EffortsStatus {

	String effortsstatus;
	String projectName;
	int estimatedefforts;
	int actualEfforts;
	String status="Efforts Exceeded";
	Double variance;
	double finalEfforts;
	
	
	public double getFinalEfforts() {
		return finalEfforts;
	}
	public void setFinalEfforts(double finalEfforts) {
		this.finalEfforts = finalEfforts;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getEstimatedefforts() {
		return estimatedefforts;
	}
	
	
	
	public Double getVariance() {
		return variance;
	}
	public void setVariance(Double variance) {
		this.variance = variance;
	}
	public String getEffortsstatus() {
		return effortsstatus;
	}
	public void setEffortsstatus(String effortsstatus) {
		this.effortsstatus = effortsstatus;
	}
	
	public void setEstimatedefforts(int estimatedefforts) {
		this.estimatedefforts = estimatedefforts;
	}
	public int getActualEfforts() {
		return actualEfforts;
	}
	public void setActualEfforts(int actualEfforts) {
		this.actualEfforts = actualEfforts;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
