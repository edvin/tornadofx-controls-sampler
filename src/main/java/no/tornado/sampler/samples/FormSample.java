package no.tornado.sampler.samples;

import javafx.scene.Node;
import no.tornado.sampler.ControlSample;
import tornadofx.control.Fieldset;
import tornadofx.control.Form;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FormSample extends ControlSample {

	public Node createPanel() {
		Form form = new Form();
		form.getStyleClass().add("content");
		form.setPrefWidth(500);
		Fieldset fieldset = form.fieldset("");

		return form;
	}

	public List<String> getSourcePaths() {
		return Arrays.asList("FormSample.java", "FormSample.fxml");
	}

	public String getDocsPath() {
		return "tornadofx/control/Form.html";
	}

	public String getCssPath() {
		return "tornadofx/control/form.css";
	}
}
