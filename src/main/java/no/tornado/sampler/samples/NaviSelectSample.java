package no.tornado.sampler.samples;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import no.tornado.sampler.ControlSample;
import no.tornado.sampler.model.Email;
import tornadofx.control.Fieldset;
import tornadofx.control.Form;
import tornadofx.control.NaviSelect;

import java.util.Collections;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class NaviSelectSample extends ControlSample {

	public Node createPanel() {
		Form form = new Form();
		form.getStyleClass().add("content");
		form.setPrefWidth(500);
		Fieldset fieldset = form.fieldset("NaviSelect");

		NaviSelect<Email> navi = new NaviSelect<>();
		navi.setValue(new Email("john@doe.com", "John Doe"));

		navi.setOnEdit(event -> selectEmail(navi));

		navi.setOnGoto(event -> {
			Alert alert = new Alert(INFORMATION);
			alert.setContentText(String.format("Action to navigate to %s should be placed here.", navi.getValue()));
			alert.setHeaderText("Goto action triggered");
			alert.show();
		});

		fieldset.field("Choose person:", navi);
		return form;
	}

	/**
	 * Select value example. Implement whatever technique you want to change value of the NaviSelect
	 */
	private void selectEmail(NaviSelect<Email> navi) {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.setTitle("Choose person");
		ListView<Email> listview = new ListView<>(FXCollections.observableArrayList(
			new Email("john@doe.com", "John Doe"),
			new Email("jane@doe.com", "Jane Doe"),
			new Email("some@dude.com", "Some Dude")
		));
		listview.setOnMouseClicked(event -> {
			Email item = listview.getSelectionModel().getSelectedItem();
			if (item != null) {
				navi.setValue(item);
				dialog.close();
			}
		});
		dialog.setScene(new Scene(listview));
		dialog.setWidth(navi.getWidth());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setHeight(100);
		dialog.showAndWait();
	}

	public List<String> getSourcePaths() {
		return Collections.singletonList("NaviSelectSample.java");
	}

	public String getDocsPath() {
		return "tornadofx/control/NaviSelect.html";
	}

	public String getCssPath() {
		return "tornadofx/control/naviselect.css";
	}
}
