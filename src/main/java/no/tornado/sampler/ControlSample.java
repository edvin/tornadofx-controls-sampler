package no.tornado.sampler;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public interface ControlSample {
	default TreeItem<ControlSample> treeItem() {
		return new TreeItem<>(this);
	}
	String name();
	Node panel();
	String getDocsPath();
	String getSourcePath();
	String getCssPath();

	default String getDocsUrl() {
		return "https://edvin.github.io/tornadofx-controls/" + getDocsPath();
	}

	default String getSourceUrl() {
		return "https://github.com/edvin/tornadofx-controls-sampler/blob/master/src/main/java/" + getSourcePath();
	}

	default String getCssUrl() {
		return "https://github.com/edvin/tornadofx-controls/blob/master/src/main/resources/" + getCssPath();
	}
}
