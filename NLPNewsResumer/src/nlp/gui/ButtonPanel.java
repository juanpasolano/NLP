package nlp.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements ActionListener{
	
	JButton boton;
	MainFrame father;
	
	public ButtonPanel(MainFrame father2)
	{
		setBorder(BorderFactory.createRaisedBevelBorder());
		this.setPreferredSize(new Dimension(100, 600));
		this.setVisible(true);
		boton = new JButton("Process");
		boton.setSize(new Dimension(90,100));
		boton.addActionListener(this);
		this.add(boton);
		father = father2; 
		setPreferredSize(new Dimension(100,600));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			father.process();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
