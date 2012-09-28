package nlp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RightPanel extends JPanel{
	
	private JTextArea textArea;
	private JEditorPane editorPane; 
	

	//Put the editor pane in a scroll pane.
	
	
	public RightPanel() throws IOException
	{
		setBorder(BorderFactory.createRaisedBevelBorder());
		this.setPreferredSize(new Dimension(500, 600));
		this.setVisible(true);
		editorPane = new JEditorPane();
		editorPane.setText("<enter output here>");
		editorPane.setContentType("text/html");
		JScrollPane editorScrollPane = new JScrollPane(editorPane);
		editorScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(490, 600));
		this.add(editorScrollPane);
	}

	public void setOutput(String returnsReport) {
		editorPane.setText(returnsReport);
	}
	
}
