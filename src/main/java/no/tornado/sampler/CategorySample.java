package no.tornado.sampler;

import javafx.scene.Node;

public class CategorySample implements ControlSample {
	private String name;

	public CategorySample(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public String getDocsPath() {
		return null;
	}

	public String getSourcePath() {
		return null;
	}

	public String getCssPath() {
		return null;
	}

	public Node panel() {
		return null;
	}
}
