package de.forestj.sandbox.util;

public class WorkSimulationWithDelegate extends Thread {
	public interface IDelegate {
		void PostProgress(int p_i_progress);
	}
	
	/* Fields */
	
	private int i_workingProgress;
	private IDelegate itf_postProgress;
	
	/* Properties */
	
	/* Methods */
	
	public WorkSimulationWithDelegate(IDelegate p_itf_postProgress) {
		this.i_workingProgress = 0;
		this.itf_postProgress = p_itf_postProgress;
	}
	
	@Override
	public void run() {
		for (this.i_workingProgress = 0; this.i_workingProgress < 100; this.i_workingProgress++) {
			if (this.itf_postProgress != null) {
				this.itf_postProgress.PostProgress(i_workingProgress);
			}
			
			int i_sleep = de.forestj.lib.Helper.randomIntegerRange(25, 100);
			
			try {
				Thread.sleep(i_sleep);
			} catch (InterruptedException e) {
				// nothing to do
			}
		}
		
		if (this.itf_postProgress != null) {
			this.itf_postProgress.PostProgress(100);
		}
	}
}
