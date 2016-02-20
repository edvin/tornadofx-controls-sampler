package no.tornado.sampler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import no.tornado.sampler.samples.InlineHTMLSample;
import no.tornado.sampler.samples.NaviSelectSample;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	@FXML TreeView<ControlSample> sampleTree;
	@FXML TabPane tabs;
	@FXML Tab sampleTab;
	@FXML Tab docsTab;
	@FXML Tab sourceTab;
	@FXML Tab cssTab;
	@FXML WebView docsView;
	@FXML WebView sourceView;
	@FXML WebView cssView;

	public void initialize(URL location, ResourceBundle resources) {
		sampleTree.setRoot(sampleRoot());

		sampleTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedTreeItem) -> {
			if (selectedTreeItem != null) {
				ControlSample sample = selectedTreeItem.getValue();
				loadContent(sample);

				if (tabs.getSelectionModel().getSelectedItem() == docsTab)
					loadDocs(sample);
				else if (tabs.getSelectionModel().getSelectedItem() == sourceTab)
					loadSource(sample);
				else if (tabs.getSelectionModel().getSelectedItem() == cssTab)
					loadCSS(sample);
			}
		});

		tabs.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, selectedTab) -> {
			if (!sampleTree.getSelectionModel().isEmpty()) {
				ControlSample sample = sampleTree.getSelectionModel().getSelectedItem().getValue();

				if (selectedTab == docsTab)
					loadDocs(sample);
				else if (selectedTab == sourceTab)
					loadSource(sample);
				else if (selectedTab == cssTab)
					loadCSS(sample);
			}
		}));
	}

	private void loadContent(ControlSample sample) {
		Node panel = sample.panel();
		Node info = sample.info();

		if (info == null)
			sampleTab.setContent(panel);
		else
			sampleTab.setContent(new SplitPane(panel, info));
	}

	private void loadSource(ControlSample sample) {
		if (sample.getSourcePath() == null)
			sourceView.getEngine().loadContent("<p>No sources for this sample.</p>");
		else
			sourceView.getEngine().load(sample.getSourceUrl());
	}

	private void loadDocs(ControlSample sample) {
		if (sample.getDocsPath() == null)
			docsView.getEngine().loadContent("<p>No documentation for this sample.</p>");
		else
			docsView.getEngine().load(sample.getSourceUrl());
	}

	private void loadCSS(ControlSample sample) {
		if (sample.getCssPath() == null)
			cssView.getEngine().loadContent("<p>No CSS for this sample.</p>");
		else
			cssView.getEngine().load(sample.getCssUrl());
	}

	private TreeItem<ControlSample> sampleRoot() {
		TreeItem<ControlSample> root = new TreeItem<>(new ControlSample("TornadoFX Controls") {});
		root.setExpanded(true);
		root.getChildren().add(new NaviSelectSample().treeItem());
		root.getChildren().add(new InlineHTMLSample().treeItem());
		return root;
	}

}
