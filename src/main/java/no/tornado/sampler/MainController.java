package no.tornado.sampler;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.web.WebView;
import no.tornado.sampler.samples.FormSample;
import no.tornado.sampler.samples.InlineHTMLSample;
import no.tornado.sampler.samples.NaviSelectSample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	@FXML TreeView<ControlSample> sampleTree;
	@FXML TabPane tabs;
	@FXML Tab sampleTab;
	@FXML Tab docsTab;
	@FXML Tab sourceTab;
	@FXML Tab cssTab;
	@FXML WebView docsView;
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
		WebView sourceView = new WebView();

		if (sample.getSourcePaths() == null) {
			sourceView.getEngine().loadContent("<p>No sources for this sample.</p>");
		} else {
			sourceView.getEngine().loadContent(applyCodemirror(sample.getSourceUrls()));
		}
		sourceTab.setContent(sourceView);
	}

	private String applyCodemirror(List<String> sources) {
		StringBuilder template = new StringBuilder(
			"<!doctype html>\n" +
				"<html>\n" +
				"<head>\n" +
				"  <link rel=\"stylesheet\" href=\"http://codemirror.net/lib/codemirror.css\">\n" +
				"  <script src=\"http://codemirror.net/lib/codemirror.js\"></script>\n" +
				"  <script src=\"http://codemirror.net/mode/clike/clike.js\"></script>\n" +
				"  <script src=\"http://codemirror.net/mode/xml/xml.js\"></script>\n" +
				"</head>\n" +
				"<body>\n");

		for (String sourceURL : sources) {
			String name = sourceURL.substring(sourceURL.lastIndexOf("/") + 1);

			String code = downloadString(sourceURL);
			String mode = name.endsWith(".java") ? "java" : "xml";

			template.append(String.format("  <h2>%s</h2>\n", name));
			template.append(String.format("  <textarea data-mode='%s'>%s</textarea>\n", mode, code));
		}

		template.append("<script>\n" +
			"  var textareas = document.getElementsByTagName('textarea');\n" +
			"  var editors = [];\n" +
		    "  for (var i = 0; i < textareas.length; i++) editors.push(textareas[i]);\n" +
			"  for (var i = 0; i < editors.length; i++) {\n" +
			"    var textarea = editors[i];\n" +
			"    CodeMirror.fromTextArea(textarea, { lineNumbers: true, matchBrackets: true, mode: 'text/x-' + textarea.dataset.mode });\n" +
			" }\n" +
			"</script>\n");

		template.append("</body>\n</html>");

		return template.toString();
	}

	private String downloadString(String sourceURL) {
		try (InputStream input = (InputStream) new URL(sourceURL).getContent();
		     BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
			StringBuilder s = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null)
				s.append(line).append("\n");
			return s.toString().replace(">", "&gt;");
		} catch (Exception ex) {
			return String.format("Unable to extract data from %s: %s", sourceURL, ex.getMessage());
		}
	}

	private void loadDocs(ControlSample sample) {
		if (sample.getDocsPath() == null)
			docsView.getEngine().loadContent("<p>No documentation for this sample.</p>");
		else
			docsView.getEngine().load(sample.getDocsUrl());
	}

	private void loadCSS(ControlSample sample) {
		if (sample.getCssPath() == null)
			cssView.getEngine().loadContent("<p>No CSS for this sample.</p>");
		else
			cssView.getEngine().load(sample.getCssUrl());
	}

	private TreeItem<ControlSample> sampleRoot() {
		TreeItem<ControlSample> root = new TreeItem<>(new ControlSample("TornadoFX Controls") {
		});
		root.setExpanded(true);
		root.getChildren().add(new FormSample().treeItem());
		root.getChildren().add(new NaviSelectSample().treeItem());
		root.getChildren().add(new InlineHTMLSample().treeItem());
		return root;
	}

}
