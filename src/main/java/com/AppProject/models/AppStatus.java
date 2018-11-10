package com.AppProject.models;

public enum AppStatus {

	STARTED(1),
	TESTING(2), 
	GOLIVE(3), 
	COMPLETED(4), 
	HOLDED(5), 
	STOPPED(6), 
	ALLOWED(7), 
	NOTALLOWED(8), 
	HOLD(9),
	TASK_DEVELOPMENT(10),
	TASK_UIDESIGN(11),
	TASK_SERVER(12),
	TASK_COMMENTS(13),
	TICKET_CLIENT(14),
	TICKET_INTERNAL(15),
	TICKET_COMMENTS(16),
	MEDIA_IMAGE(51),
	MEDIA_VIDEO(52),
	MEDIA_DOCUMENT(53),
	SCHEDULE_CALL(61),
	SCHEDULE_VIDEOCALL(62),
	SCHEDULE_INPERSON(63);

	private final int value;

	private AppStatus(int value) {
		this.value = value;
	}

	public int get() {
		return value;
	}
}
