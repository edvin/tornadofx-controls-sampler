package no.tornado.sampler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<String> getSourcePaths() { return Collections.emptyList(); }
	public String getCssPath() { return null; }

	public String getDocsUrl() {
		return "https://edvin.github.io/tornadofx-controls/" + getDocsPath();
	}

	public List<String> getSourceUrls() {
		String base = "https://raw.githubusercontent.com/edvin/tornadofx-controls-sampler/master/src/main/";
		return getSourcePaths().stream().map(s -> base + s).collect(Collectors.toList());
	}

	public String getCssUrl() {
		return "https://github.com/edvin/tornadofx-controls/blob/master/src/main/resources/" + getCssPath();
	}
}
