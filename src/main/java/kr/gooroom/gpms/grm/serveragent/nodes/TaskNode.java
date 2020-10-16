package kr.gooroom.gpms.grm.serveragent.nodes;

import kr.gooroom.gpms.common.utils.MessageSourceHelper;

import java.util.HashMap;

public class TaskNode {

	private String task_name;
	private HashMap<String, String> in;
	private String task_desc;
	private String task_type;

	public TaskNode() {
		this.task_name = "";
		this.in = null;
		this.task_desc = "";
		this.task_type = "";
	}

	public TaskNode(String task_name, HashMap<String, String> in, String task_type) {
		this.task_name = task_name;
		this.in = in;
		this.task_desc = MessageSourceHelper.getMessage("job.task.desc." + task_name);
		this.task_type = task_type;
	}

	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public HashMap<String, String> getIn() {
		return in;
	}
	public void setIn(HashMap<String, String> in) {
		this.in = in;
	}
	public String getTask_desc() {
		return task_desc;
	}
	public void setTask_desc(String task_desc) {
		this.task_desc = task_desc;
	}
	public String getTask_type() {
		return task_type;
	}
	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}
}
