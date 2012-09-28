package nlp.gui;

import java.awt.Dimension;
import java.awt.TextArea;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LeftPanel extends JPanel{
	
	private JEditorPane textArea;
	
	public LeftPanel()
	{
		setBorder(BorderFactory.createRaisedBevelBorder());
		this.setPreferredSize(new Dimension(200, 600));
		this.setVisible(true);
		textArea = new JEditorPane();
		textArea.setText("<enter input here>");
		JScrollPane editorScrollPane = new JScrollPane(textArea);
		editorScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(190, 600));
		this.add(editorScrollPane);
	}
	
	/*
	 * 
	 * Returns the input
	 */
	public String getInput()
	{
		return textArea.getText();
	}
	
}
