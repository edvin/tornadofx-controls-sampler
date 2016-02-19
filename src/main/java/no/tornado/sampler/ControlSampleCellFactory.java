package no.tornado.sampler;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

public class ControlSampleCellFactory implements Callback<TreeView<ControlSample>, TreeCell<ControlSample>> {
	public TreeCell<ControlSample> call(TreeView<ControlSample> param) {
		return new TreeCell<ControlSample>() {
			protected void updateItem(ControlSample item, boolean empty) {
				super.updateItem(item, empty);
				setText(item == null || empty ? null : item.name());
			}
		};
	}
}
