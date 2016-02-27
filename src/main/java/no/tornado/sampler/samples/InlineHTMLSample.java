package no.tornado.sampler.samples;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import no.tornado.sampler.ControlSample;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class InlineHTMLSample extends ControlSample {

	protected Node createPanel() {
		try {
			return FXMLLoader.load(getClass().getResource("InlineHTMLSample.fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getSourcePaths() {
		return Collections.singletonList("InlineHTMLSample.java");
	}

	public String getDocsPath() {
		return "tornadofx/control/InlineHTML.html";
	}

	public String getCssPath() {
		return "tornadofx/control/inline-html.css";
	}
}
