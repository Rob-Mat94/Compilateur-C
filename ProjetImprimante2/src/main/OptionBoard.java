package main;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;


public class OptionBoard extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private AppBoard parent;
	private JSlider js;
	private int js_value = 1;
	private JLabel label_value = new JLabel("["+String.valueOf(js_value)+"]",JLabel.CENTER);
	private JButton validate;
	
	public OptionBoard(AppBoard main)
	{   
		this.parent = main;
		this.setTitle("Option");
		this.setSize(250,100);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout(3,3));
		this.js = new JSlider();
		initSliderComponents();
		
		this.validate = Bvalidate();
		this.panel.add(validate,BorderLayout.SOUTH);
		this.setContentPane(this.panel);
	}
	
	/* Ajouter boutton valider */
	private void initSliderComponents()
	{
		js.setValue(1);
		js.setMinimum(1);
		js.addChangeListener(e -> {js_value = js.getValue();label_value.setText("["+String.valueOf(js_value)+"]");parent.setItemAmount(js_value);});
		
		this.panel.add(new JLabel("Amont   "),BorderLayout.WEST);
		this.label_value.setAlignmentX(JLabel.CENTER);
		this.panel.add(label_value,BorderLayout.EAST);
		this.panel.add(js,BorderLayout.CENTER);
		
	}
	
	private JButton Bvalidate()
	{
		JButton result = new JButton("Ok");
		result.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OptionBoard.this.dispose();
				OptionBoard.this.parent.getOption_label().setForeground(Color.BLACK);
				
			}
		});
		
		return result;
	}

}
