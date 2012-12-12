package eu.robojob.irscw.external.robot;

import java.util.Map;

public class RobotSettings {
	
	private GripperBody gripperBody;
	private Map<GripperHead, Gripper> grippers;
	
	public RobotSettings(final GripperBody gripperBody, final Map<GripperHead, Gripper> grippers) {
		this.gripperBody = gripperBody;
		this.grippers = grippers;
	}

	public void setGripper(final GripperHead head, final Gripper gripper) {
		grippers.put(head, gripper);
	}
	
	public Gripper getGripper(final GripperHead head) {
		return grippers.get(head);
	}

	public GripperBody getGripperBody() {
		return gripperBody;
	}

	public Map<GripperHead, Gripper> getGrippers() {
		return grippers;
	}
}
