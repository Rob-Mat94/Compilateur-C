package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


/* Pyramide 
 * Sphère
 * Cylindre
 */
/**
 * 
 * Classe représentant la partie graphique de l'application
 * 
 */

public class AppBoard extends JFrame  {
	
	
	
	private static final long serialVersionUID = 1L;
	private final String id_version = "3D_Alphav";
	/**
	 * Conteneur avec tous les élements graphiques
	 */
	private JPanel content;
	
	/**
	 * Bouttons permettant de choix le type de fichier STL souhaité et de valider 
	 */
	private final JRadioButton ascii_choice  = new JRadioButton("ASCII");
	private final JRadioButton binary_choice = new JRadioButton("Binary");
	private final JButton validate = new JButton("Generate");
	private final ButtonGroup bg = new ButtonGroup();
	private final JLabel label = new JLabel("STL Type : ");
	private final TextField height_field = new TextField("Height (mm)");
	private final TextField width_field = new TextField("Width (mm)");
	private final TextField length_field = new TextField("Length (mm)");
	private final TextField file_name_field = new TextField("name.stl");
	
	
	private Container img; 
	private JLabel imgicon;
	private JLabel option_label;
	
	private int itemAmount = 1;

	private String itemSelected= "";
	
	public AppBoard()
	{	
		Collections.initCollections();
		SwingUITheme();
		this.setTitle(id_version);
		this.setSize(800,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initPanel();
		this.setContentPane(this.content);
		this.setVisible(true);	
	}
	
	private void SwingUITheme()
	{
		try {
			  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			  
			  UIManager.put("nimbusBase", Color.YELLOW);
			  UIManager.put("nimbusBlueGrey", Color.ORANGE);
			  UIManager.put("control", Color.WHITE);
			  
			  
			  for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				    if ("Nimbus".equals(info.getName())) {
				        UIManager.setLookAndFeel(info.getClassName());
				        break;
				    }
				}
			} catch(Exception e) {
			  System.out.println("Error setting native LAF: " + e);
			}
	}
	/**
	 * Initialise tous les éléments graphiques 
	 */
	
	private void initPanel()
	{
		this.content = new JPanel(); 
		this.content.setBackground(Color.WHITE);
		this.content.setLayout(new BorderLayout());
		this.content.setBackground(Color.LIGHT_GRAY);
		//Ajout de la barre de sélection des objets 
		this.content.add(initBarObject(),BorderLayout.NORTH);
	    //Initilisation de l'image au centre (image par défaut)
		initImageIcon();
		this.content.add(img,BorderLayout.EAST);
		//Ajout de la JToolBar (Selection ASCII / BINAIRE / NOM / GENERER)
		this.content.add(initToolBar(),BorderLayout.SOUTH);
		// Ajout de la partie option permettant de renseigner les tailles souhaitées 
		this.content.add(initOptionBar());
		
		
	}
	
	
	
	/**
	 * Ajoute l'image centrale
	 */
	
	private void initImageIcon()
	{
		URL filename = getClass().getResource("img_menu.jpg");
		ImageIcon icon = new ImageIcon(filename);
		img = new Container();
		imgicon = new JLabel(icon);
		img.add(imgicon);
		img.setLayout(new FlowLayout());
	}
	
	
	private void initOptionLabel(JLabel option)
	{
			option.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e)
				{	
					if(itemSelected.equals(""))
						return;
					option.setForeground(Color.RED);
					new OptionBoard(AppBoard.this);
				}
			});
			
			
	}
	
	
	private void configureEditingButton(JButton b)
	{
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditionBoard(AppBoard.this);
				
			}
		});
	}
	/**
	 * Crée un conteneur avec 3 champs éditables, pour renseigner la largeur, la longueur et la hauteur souhaitées. 
	 * @return Container avec les 3 champs éditables et un Label de description
	 */
	private Container initOptionBar()
	{
		JPanel c = new JPanel();
		c.setLayout(new GridLayout(5,2));
		option_label = new JLabel("Options");
		this.initOptionLabel(option_label);
		
		/* Button edition */
		JButton _edition = new JButton("Edition");
		this.configureEditingButton(_edition);
		c.add(_edition);
		
		option_label.setForeground(Color.black);
		option_label.setHorizontalAlignment(JTextField.CENTER);
		c.add(option_label);
		
		
		
		c.add(this.height_field);
		c.add(this.width_field);
		c.add(this.length_field);
		this.content.add(c,BorderLayout.WEST);
		
		return c;
	}
	
	/**
	 * Crée une barre de choix, afin de choisir l'objet voulu parmis les propositions.
	 * @return JComBox<?> avec les différents Item correspondant aux objets imprimmables.
	 */
	private JComboBox<?> initBarObject()
	{
		JComboBox<String> combo = new JComboBox<String>(new String[] {"Box","Box Hole","Sphere","Pyramid","Cylinder","Cone"}); 
		combo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)arg0.getSource();
				itemSelected = (String)cb.getSelectedItem();
				URL filename = getClass().getResource(Collections.getUrlcollection().get(itemSelected));
				System.out.println(filename);
				width_field.setText(Collections.getDimcollection().get(itemSelected)[0]);
				length_field.setText(Collections.getDimcollection().get(itemSelected)[1]);
				height_field.setText(Collections.getDimcollection().get(itemSelected)[2]);
				ImageIcon icon = new ImageIcon(filename);
				img.remove(imgicon);
				imgicon = new JLabel(icon);
				imgicon.setSize(new Dimension(700,400));
				img.add(imgicon);
				img.repaint();		
			}
		});
		return combo;
	}
	
	/**
	 * 
	 * @param width largeur de l'objet
	 * @param height hauteur de l'objet
	 * @param length longueur de l'objet
	 * @return retourne la Forme3D décrite dans un fichier binaire STL correspondant au choix effectué
	 */
	private Forme3D selectBinaryObject(int width, int height, int length)
	{
		if(itemSelected.equals("Box"))
		{
			return new BoxBinary(width, height, length,itemAmount);
		}
		else if(itemSelected.equals("Box Hole"))
		{
			return new BoxHoleBinary(width, height, length);
		}
		else if(itemSelected.equals("Sphere"))
		{
			return new SphereBinary(width,height,length,itemAmount);
		}
		else if(itemSelected.equals("Cylinder"))
		{
			return new CylinderBinary(width, height, length);
		}
		else if(itemSelected.equals("Cone"))
		{
			return new ConeBinary(width, height, length);
		}
		else if(itemSelected.equals("Pyramid"))
		{
			JOptionPane.showMessageDialog(this,"This object is no longer avaible in Binary Format","Warning",JOptionPane.WARNING_MESSAGE);
		}
		return null;
	}
	
	/**
	 * 
	 * @param width largeur de l'objet
	 * @param height hauteur de l'objet
	 * @param length longueur de l'objet
	 * @return retourne la Forme3D décrite dans un fichier Ascii STL correspondant au choix effectué
	 */
	
	private Forme3D selectAsciiObject(int width, int height, int length)
	{
		if(itemSelected.equals("Box"))
		{
			return new BoxAscii(width, height, length,itemAmount);
		}
		else if(itemSelected.equals("Box Hole"))
		{
			return new BoxHoleAscii(width, height, length);
		}
		else if(itemSelected.equals("Sphere"))
		{
			return new SphereASCII(width,height,length,itemAmount);
		}
		else if(itemSelected.equals("Cylinder"))
		{
			return new CylinderAscii(width, height, length);
		}
		else if(itemSelected.equals("Pyramid"))
		{
			return new PyramideAscii(width,height,length,itemAmount);
		}
		else if(itemSelected.equals("Cone"))
		{
			JOptionPane.showMessageDialog(this,"This object is no longer avaible in Ascii Format","Warning",JOptionPane.WARNING_MESSAGE);
		}
		return null;
	}
	
	/**
	 * Configure le bouton "valider" afin de générer le fichier choisi 
	 * @param b la fenêtre courante de l'application
	 */
	private void configureValidateButton(final AppBoard b)
	{
		this.validate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try
				{	
					final int width = Integer.parseInt(b.getWidth_field().getText());
					final int height = Integer.parseInt(b.getHeight_field().getText());
					final int length = Integer.parseInt(b.getLength_field().getText());
					
		            //
					if(ascii_choice.isSelected())
					{
						selectAsciiObject(width, height, length).generate(file_name_field.getText());
						JOptionPane.showMessageDialog(b,"Ascii generated","Message",JOptionPane.INFORMATION_MESSAGE);
					}	
					else
					{
						selectBinaryObject(width, height, length).generate(file_name_field.getText());
						JOptionPane.showMessageDialog(b,"Binary generated","Message",JOptionPane.INFORMATION_MESSAGE);
					}					
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(b,"Invalid number format","Warning",JOptionPane.WARNING_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(b,"Failed to create file","Warning",JOptionPane.WARNING_MESSAGE);
				}
				catch(NullPointerException e)
				{
					JOptionPane.showMessageDialog(b,"No object selected or name already used","Warning",JOptionPane.WARNING_MESSAGE);
				}
							
			}
		});
	}
	
	/**
	 * 
	 * @return JToolBar contenant les choix possible de format, un champs pour renseigner le nom du fichier
	 * et le bouton valider.
	 */
	private JToolBar initToolBar()
	{	
		JToolBar toolbar = new JToolBar();
		toolbar.add(label);
		binary_choice.setSelected(true);
		bg.add(ascii_choice);
		bg.add(binary_choice);
		toolbar.add(binary_choice);
		toolbar.add(ascii_choice);
		configureValidateButton(this);
		toolbar.add(file_name_field);
		toolbar.add(validate);
		FlowLayout fl = new FlowLayout();
		fl.setHgap(70);
		toolbar.setLayout(fl);
		toolbar.setFloatable(false);;
		
		return toolbar;
	}
	
	public TextField getHeight_field() {
		return height_field;
	}

	public TextField getWidth_field() {
		return width_field;
	}

	public TextField getLength_field() {
		return length_field;
	}
	
	public JLabel getOption_label() {
		return option_label;
	}

	public void setItemAmount(int itemAmount) {
		this.itemAmount = itemAmount;
	}
	
	public int getItemAmount() {
		return itemAmount;
	}
	
	/*****************************************/
	
	public static void main(String[] args) {
		new AppBoard();
	}


}
