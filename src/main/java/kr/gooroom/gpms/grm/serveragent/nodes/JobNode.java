package kr.gooroom.gpms.grm.serveragent.nodes;


import java.util.HashMap;

public class JobNode {
	private ModuleNode module;

	/**
	 * get module for job
	 *
	 * @return Module job module
	 *
	 */
	private ModuleNode getModule() {
		return module;
	}

	/**
	 * get module for job
	 *
	 * @param module job module
	 * @return void
	 *
	 */
	public void setModule(ModuleNode module) {
		this.module = module;
	}

	/**
	 * create job with package
	 *
	 * @param moduleName string job module name
	 * @param taskName   string job task name
	 * @param packages   string job package name
	 * @return void
	 *
	 */
	public void createJobWithPackage(String moduleName, String taskName, String packages) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pkgs", packages);

		createJobWithMap(moduleName, taskName, map);
	}

	/**
	 * create job
	 *
	 * @param moduleName string job module name
	 * @param taskName   string job task name
	 * @return void
	 *
	 */
	public void createJob(String moduleName, String taskName) {

		HashMap<String, String> map = new HashMap<String, String>();
		createJobWithMap(moduleName, taskName, map);
	}

	/**
	 * create job with label
	 *
	 * @param moduleName string job module name
	 * @param taskName   string job task name
	 * @param label      string job label
	 * @return void
	 *
	 */
	public void createJobWithLabel(String moduleName, String taskName, String label) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("label", label);

		createJobWithMap(moduleName, taskName, map);
	}

	/**
	 * create job with map(hashmap)
	 *
	 * @param moduleName string job module name
	 * @param taskName   string job task name
	 * @param map        HashMap
	 * @return void
	 *
	 */
	public void createJobWithMap(String moduleName, String taskName, HashMap<String, String> map) {

		ModuleNode module = new ModuleNode();
		module.setModule_name(moduleName);

		TaskNode task = new TaskNode();
		task.setIn(map);
		task.setTask_name(taskName);

		module.setTask(task);

		setModule(module);
	}

	/**
	 * generate job instance
	 *
	 * @param moduleName string job module name
	 * @param taskName   string job task name
	 * @return Job
	 *
	 */
	public static JobNode generateJob(String moduleName, String taskName) {

		JobNode job = new JobNode();
		HashMap<String, String> map = new HashMap<String, String>();
		job.createJobWithMap(moduleName, taskName, map);

		return job;
	}

	/**
	 * generate job instance with label
	 *
	 * @param moduleName string job module name
	 * @param taskName   string job task name
	 * @param label      string job label
	 * @return Job
	 *
	 */
	public static JobNode generateJobWithLabel(String moduleName, String taskName, String label) {

		JobNode job = new JobNode();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("label", label);
		job.createJobWithMap(moduleName, taskName, map);

		return job;
	}

	/**
	 * generate job instance with package
	 *
	 * @param moduleName string job module name
	 * @param taskName   string job task name
	 * @param packages   string job package name
	 * @return Job
	 *
	 */
	public static JobNode generateJobWithPackage(String moduleName, String taskName, String packages) {

		JobNode job = new JobNode();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pkgs", packages);

		job.createJobWithMap(moduleName, taskName, map);

		return job;
	}

	/**
	 * generate job instance with map(hashmap)
	 *
	 * @param moduleName string job module name
	 * @param taskName   string job task name
	 * @param map        HashMap
	 * @return void
	 *
	 */
	public static JobNode generateJobWithMap(String moduleName, String taskName, HashMap<String, String> map) {

		JobNode job = new JobNode();
		job.setModule(new ModuleNode(moduleName, new TaskNode(taskName, map, "invisiable")));

		return job;
	}

		
}
