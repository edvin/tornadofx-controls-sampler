package no.tornado.sampler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.net.URL;

public abstract class ControlSample {
	private String name;
	private Node panel;
	private Node info;

	public ControlSample() {
		name = getClass().getSimpleName().replaceAll("Sample$", "");
	}

	public ControlSample(String name) {
		this.name = name;
	}

	public final TreeItem<ControlSample> treeItem() {
		return new TreeItem<>(this);
	}
	public final String name() { return this.name; }

	protected Node createPanel() { return null; }

	public final Node panel() {
		if (panel == null)
			panel = createPanel();

		return panel;
	}

	protected Node createInfo() {
		URL infoResource = getClass().getResource(String.format("%sInfo.fxml", name));
		if (infoResource != null) {
			FXMLLoader loader = new FXMLLoader(infoResource);
			loader.setController(this);
			try {
				return loader.load();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	public final Node info() {
		if (info == null)
			info = createInfo();

		return info;
	}

	public String getDocsPath() { return null; }
	public String getSourcePath() { return null; }
	public String getCssPath() { return null; }

	public String getDocsUrl() {
		return "https://edvin.github.io/tornadofx-controls/" + getDocsPath();
	}

	public String getSourceUrl() {
		return "https://github.com/edvin/tornadofx-controls-sampler/blob/master/src/main/java/no/tornado/sampler/samples/" + getSourcePath();
	}

	public String getCssUrl() {
		return "https://github.com/edvin/tornadofx-controls/blob/master/src/main/resources/" + getCssPath();
	}
}
