package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import scaleCalculator.ScaleBoxFactory;
import scaleCalculator.ScaleCylinderFactory;
import scaleCalculator.ScalePyramidFactory;
import scaleCalculator.ScaleSphereFactory;




public class EditionBoard extends JFrame  
{
	
	class FusionPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private JFileChooser choix_file_1;
		private JFileChooser choix_file_2;
		private int countfile = 0;
		
		private JLabel label_file1 = new JLabel("File 1 : ");
		private JLabel label_file2 = new JLabel("File 2 : "); 
		private String file1Path = null; 
		private String file2Path = null;	
		
		private JRadioButton top = new JRadioButton("Top");
		private JRadioButton front = new JRadioButton("Front");
		private JRadioButton right = new JRadioButton("Right");
		private JRadioButton left = new JRadioButton("Left");
		private JRadioButton back = new JRadioButton("Back");
		
		private JTextField f_name = new JTextField("name.stl");
		
		private JLabel slider_value_text = new JLabel("Depth : 0");
		
		private JLabel slider_value_text_x = new JLabel("Scale X : 0");
		private JLabel slider_value_text_y = new JLabel("Scale Y : 0");
		
		private JSlider scaling = new JSlider();
		
		/* */
		private JSlider scaling_x = new JSlider();
		private int jslider_value_x = 0;
		private int jslider_value_y = 0;
		private JSlider scaling_y = new JSlider();
		/* */
		
		private int jslider_value = 0;
		
		private ButtonGroup bg = new ButtonGroup();
		
		public FusionPanel()
		{	
			this.setLayout(new FlowLayout());
			JButton choice_b = new JButton("explorer");
			addButtonListener(choice_b);
			this.setVisible(true);
			this.add(choice_b);
			this.add(label_file1);
			this.add(label_file2);
		}
		
		private void initSlider() 
		{
			scaling.setValue(0);scaling.setMinimum(-100);scaling.setMaximum(100);
			scaling_x.setValue(0);scaling_x.setMinimum(-100);scaling_x.setMaximum(100);
			scaling_y.setValue(0);scaling_y.setMinimum(-100);scaling_y.setMaximum(100);
			
			scaling.addChangeListener(e -> {jslider_value = scaling.getValue();slider_value_text.setText("Depth : " + String.valueOf(jslider_value));});
			scaling_x.addChangeListener(e -> {jslider_value_x = scaling_x.getValue();slider_value_text_x.setText("Scale X : "+String.valueOf(jslider_value_x));});
			scaling_y.addChangeListener(e -> {jslider_value_y = scaling_y.getValue();slider_value_text_y.setText("Scale Y : "+String.valueOf(jslider_value_y));});
			
			this.add(scaling);
			this.add(slider_value_text);
			this.add(scaling_x);this.add(slider_value_text_x);
			this.add(scaling_y);this.add(slider_value_text_y);
			
		}
		
		private void initScaleChoice()
		{	
			bg.add(top);bg.add(back);bg.add(left);bg.add(right);bg.add(front);
			top.setSelected(true);
			
			/* FRONT, BACK, TOP, RIGHT, LEFT */
			this.add(top);
			this.add(back);
			this.add(left);
			this.add(right);
			this.add(front);
			
			JButton launch = new JButton("Executer");
			launch.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					operation();
					
				}
			});
		
			this.add(launch);
		}
		
		
		private void ChoseFirstFile()
		{
			int result = choix_file_1.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) 
			{
				File fs = choix_file_1.getSelectedFile();
				file1Path = fs.getAbsolutePath();
					
				label_file1.setText(label_file1.getText()+fs.getName());
				countfile++;
			}
			else if (result == JFileChooser.CANCEL_OPTION) 
			    JOptionPane.showMessageDialog(EditionBoard.this, "Aucun fichiers sélectionnés", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		
		private void ChoseSndFile()
		{
			int result = choix_file_2.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) 
			{

				File fs = choix_file_2.getSelectedFile();
				file2Path = fs.getAbsolutePath();	
				label_file2.setText(label_file2.getText()+fs.getName());
				initScaleChoice();
				initSlider();
				add(f_name);
				countfile = 0;
			}
			else if (result == JFileChooser.CANCEL_OPTION) 
			    JOptionPane.showMessageDialog(EditionBoard.this, "Aucun fichiers sélectionnés", "Warning", JOptionPane.WARNING_MESSAGE);		
		}
		
		private void addButtonListener(JButton choice_b)
		{
				choice_b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					choix_file_1 = new JFileChooser();
					choix_file_2 = new JFileChooser();
					choix_file_1.setMultiSelectionEnabled(false) ;
					choix_file_2.setMultiSelectionEnabled(false) ;
					try
					{
						if(countfile == 0)
							ChoseFirstFile();
						else
							ChoseSndFile();
					}
					catch (Exception ex) {
						JOptionPane.showMessageDialog(EditionBoard.this, "Veuillez sélectionner 2 fichiers", "Warning", JOptionPane.WARNING_MESSAGE);
					}	
				}
			});
		}
		

		private float[] getForm3DSize(BufferedReader fr)
		{
			try {
				fr.readLine();
				String line_size = fr.readLine();
				String[] size = line_size.split(" ");
				return new float[] {Float.parseFloat(size[3]), Float.parseFloat(size[4]), Float.parseFloat(size[5])};
			} catch (IOException e) {
				JOptionPane.showMessageDialog(EditionBoard.this, "Format de fichier invalide ?", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			
			return null;
		}
		
		private String transformVertex(String line, float[] scale)
		{	
			float width = 0,height = 0,length = 0;
				try
				{	
					String tab_line[] = line.split(" ");
					width = Float.parseFloat(tab_line[3]);
					length = Float.parseFloat(tab_line[4]);
					height = Float.parseFloat(tab_line[5]);
					width+= scale[0]; length+=scale[1]; height+= scale[2];
				}
				catch (NumberFormatException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(EditionBoard.this, "Impossible de récupérer les sommets", "Warning", JOptionPane.WARNING_MESSAGE);
					dispose();
				}
				
				return " vertex " + String.valueOf(width) + " " + String.valueOf(length) + " " + String.valueOf(height) +"\n"; 	
		}
			
		private float[] getScale(float[] size,float[] form_2_size)
		{	 
			float scale_d = (float)jslider_value / 100;
			float scale_x = (float)jslider_value_x / 100;
			float scale_y = (float)jslider_value_y /100;
			
			if(size[2] == -8997.0)
				return new ScaleCylinderFactory(top, back, right, left, front, size, form_2_size, scale_d, scale_x, scale_y).generateScale();
			if(size[2] == -8999.0)
				return new ScaleSphereFactory(top, back, right, left, front, size, form_2_size, scale_d, scale_x, scale_y).generateScale();
			if(size[2] == -8998.0)
				return new ScalePyramidFactory(top, back, right, left, front, size, form_2_size, scale_d, scale_x, scale_y).generateScale();
			
			return new ScaleBoxFactory(top, back, right, left, front, size, form_2_size, scale_d, scale_x, scale_y).generateScale();
		}
		
		/**
		 * 
		 * @param fr1
		 * @param fw1
		 * @param form_size
		 * @throws IOException
		 * Copie sans modifier les points
		 */
		private void Copy(BufferedReader fr1, FileWriter fw1, float[] form_size) throws IOException
		{
			String normal = String.format(" facet normal %s %s %s\n", String.valueOf(form_size[0]), String.valueOf(form_size[1]), String.valueOf(form_size[2]));
			fw1.write(normal);
			for(String line = fr1.readLine(); line != null ; line = fr1.readLine())
			{	
				if(!line.contains("endsolid"))
					fw1.write(line + "\n");
			}
		}
		
		/**
		 * 
		 * @param fr1
		 * @param fw1
		 * @param scale
		 * @throws IOException
		 * Copie en modifiant les points en fonction de scale
		 */
		private void CopyModifying(BufferedReader fr1, FileWriter fw1, float[] scale) throws IOException
		{
			fw1.write(" facet normal 0 0 0\n");
			for(String line = fr1.readLine(); line != null;line = fr1.readLine())
			{	
				if(!line.startsWith("solid"))
				{
					if(line.contains("vertex"))
						fw1.write(" " + transformVertex(line, scale));
					else
						fw1.write(line + "\n");
				}
			}
		}
		
		private void operation()
		{	
			
			File file1 = new File(file1Path);
			File file2 = new File(file2Path);
			File file_result = new File(f_name.getText());
			
			try 
			{
				FileWriter fw1 = new FileWriter(file_result);
				BufferedReader fr1 = new BufferedReader(new FileReader(file1));
				BufferedReader fr2 =  new BufferedReader(new FileReader(file2));
				fw1.write("solid fusion\n");
				float[] form_size = getForm3DSize(fr1);
				float[] form_size_2 = getForm3DSize(fr2);
				
				float[] scale = getScale(form_size,form_size_2);
				
				Copy(fr1,fw1,form_size);
				CopyModifying(fr2, fw1, scale);
				
				fr2.close();
				fr1.close();
				fw1.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/*************************************/

	
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	
	public EditionBoard(AppBoard main)
	{
		this.setTitle("Edition");
		this.setSize(400,200);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout(3,3));
		
		
		this.setContentPane(new FusionPanel());
	}
	

}
