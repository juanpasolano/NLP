package nlp.gui;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import nlp.src.NewsSummarizer;

public class MainFrame extends JFrame {

	/*
	 * JPANELS
	 */
	LeftPanel leftPanel;
	RightPanel rightPanel;
	ButtonPanel buttonPanel;
	/*
	 * News Summarizer
	 */
	private NewsSummarizer ns;
	/*
	 * Main Frame 
	 */
	public MainFrame() throws IOException
	{		
		setLayout(new BorderLayout());
		leftPanel = new LeftPanel();
		add(leftPanel, BorderLayout.WEST);
		
		rightPanel = new RightPanel();
		add(rightPanel, BorderLayout.EAST);
		
		buttonPanel = new ButtonPanel(this);
		add(buttonPanel, BorderLayout.CENTER);
		
		try {
			ns = new NewsSummarizer();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame main;
		try {
			main = new MainFrame();
			main.setVisible(true);
			main.setTitle("Natural Language Processing News Resumer");
			main.setSize(800,600);
			main.setResizable(false);
			main.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		} catch (IOException e) {
			//put error on output
			System.out.println(e.getMessage());
		}
	}

	
	
	/*
	 * Process
	 */
	public void process() throws IOException, ClassNotFoundException
	{
		String input = leftPanel.getInput();
		ns.readInputFromGUI(input);
		rightPanel.setOutput(ns.returnsReportHTML());
	}
}
