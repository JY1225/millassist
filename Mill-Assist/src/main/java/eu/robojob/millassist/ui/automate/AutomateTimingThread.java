package eu.robojob.millassist.ui.automate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.robojob.millassist.process.ProcessFlowTimer;

public class AutomateTimingThread implements Runnable {

	private ProcessFlowTimer processFlowTimer;
	private boolean running;
	private AutomatePresenter automatePresenter;
	
	private static final int SLEEP_INTERVAL = 500;
	
	private static Logger logger = LogManager.getLogger(AutomateTimingThread.class.getName());
	
	public AutomateTimingThread(final AutomatePresenter automatePresenter, final ProcessFlowTimer processFlowTimer) {
		this.automatePresenter = automatePresenter;
		this.processFlowTimer = processFlowTimer;
		this.running = true;
	}
	
	@Override
	public void run() {
		logger.debug("Started execution.");
		try {
			while (running) {
				long totalTime = processFlowTimer.getTotalTime();
				long finishedInterval = processFlowTimer.getFinishedInterval();
				long remainingCurrent = processFlowTimer.getRemainingTimeCurrent(automatePresenter.getMainProcessFlowId());
				long remainingTime = processFlowTimer.getRemainingTime(automatePresenter.getMainProcessFlowId());

				automatePresenter.setTimers(toTimeString(totalTime), toTimeString(finishedInterval), toTimeString(remainingCurrent), toTimeString(remainingTime));
				Thread.sleep(SLEEP_INTERVAL);
			}
		} catch (InterruptedException e) {
			interrupted();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
		} finally {
			stopRunning();
		}
		logger.info(toString() + " ended...");
	}
	
	private String toTimeString(final long milliSeconds) {
		if (milliSeconds < 0) {
			return "--:--:--";
		} else {
			int hours = (int) ((milliSeconds / (1000 * 60 * 60)) % 24);
			String hoursStr = "";
			if (hours < 10) {
				hoursStr += "0";
			}
			hoursStr += hours;
			int minutes = (int) ((milliSeconds / (1000 * 60)) % 60);
			String minutesStr = "";
			if (minutes < 10) {
				minutesStr += "0";
			}
			minutesStr += minutes;
			int seconds = (int) (milliSeconds / 1000) % 60;
			String secondsStr = "";
			if (seconds < 10) {
				secondsStr += "0";
			}
			secondsStr += seconds;
			return hoursStr + ":" + minutesStr + ":" + secondsStr;
		}
	}
	
	public void interrupted() {
		this.running = false;
	}
	
	public void stopRunning() {
		this.running = false;
	}
	
	@Override
	public String toString() {
		return "AutomateTimingThread";
	}
}
