package kr.gooroom.gpms.grm.serveragent.nodes;

public class ModuleNode {

	private String module_name;
	private TaskNode task;

	public ModuleNode() {
		this.module_name = "";
		this.task = null;
	}

	public ModuleNode(String module_name) {
		this.module_name = module_name;
		this.task = null;
	}

	public ModuleNode(String module_name, TaskNode task) {
		this.module_name = module_name;
		this.task = task;
	}

	public String getModule_name() {
		return module_name;
	}
	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}
	public TaskNode getTask() {
		return task;
	}
	public void setTask(TaskNode task) {
		this.task = task;
	}
}
