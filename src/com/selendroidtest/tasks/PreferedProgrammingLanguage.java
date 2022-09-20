package com.selendroidtest.tasks;

public enum PreferedProgrammingLanguage {
	RUBY("Ruby"), PHP("PHP"), SCALA("Scala"), PYTHON("Python"), JAVA_SCRIPT("Javascript"), JAVA("Java"), CPP("C++"), C_SAHRP(
			"C#");
	String value;

	public String getValue() {
		return value;
	}

	PreferedProgrammingLanguage(String value) {
		this.value = value;
	}
}
