package no.tornado.sampler.samples;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import no.tornado.sampler.ControlSample;

import java.io.IOException;

public class InlineHTMLSample extends ControlSample {

	protected Node createPanel() {
		try {
			return FXMLLoader.load(getClass().getResource("InlineHTMLSample.fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
