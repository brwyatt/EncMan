import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class OpenID extends JDialog implements ActionListener{
	private JPanel main;
	private JComboBox ids=new JComboBox();
	private JLabel idlbl=new JLabel("Select ID:");
	private JPasswordField pass=new JPasswordField();
	private JLabel passlbl=new JLabel("Password:");
	private JButton newID=new JButton("New Identity");
	private JButton importID=new JButton("Import Identity");
	private JButton cont=new JButton("Continue");
	private JButton cancel=new JButton("Cancel");
	
	private ID selected=null;
	
	public OpenID(){
		setTitle("Open Identity");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Icon_SM.jpg")));//set frame icon
		setSize(425,125);
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});	
		setLocationRelativeTo(null);
		setResizable(false);
		
		main=new JPanel(new SpringLayout());
		
		//Disable import ID feature
		importID.setEnabled(false);
		
		newID.addActionListener(this);
		importID.addActionListener(this);
		cont.addActionListener(this);
		cancel.addActionListener(this);
		
		main.add(idlbl);
		main.add(ids);
		main.add(passlbl);
		main.add(pass);
		main.add(newID);
		main.add(importID);
		main.add(cont);
		main.add(cancel);

		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.WEST, idlbl, 5, SpringLayout.WEST, main);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.NORTH, idlbl, 8, SpringLayout.NORTH, main);

		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.WEST, ids, 20, SpringLayout.EAST, idlbl);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.NORTH, ids, 5, SpringLayout.NORTH, main);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.EAST, ids, 175, SpringLayout.WEST, ids);

		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.WEST, passlbl, 5, SpringLayout.WEST, main);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.NORTH, passlbl, 8, SpringLayout.SOUTH, ids);

		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.WEST, pass, 0, SpringLayout.WEST, ids);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.EAST, pass, 0, SpringLayout.EAST, ids);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.NORTH, pass, 8, SpringLayout.SOUTH, ids);

		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.EAST, newID, -5, SpringLayout.EAST, main);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.NORTH, newID, 5, SpringLayout.NORTH, main);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.WEST, newID, -150, SpringLayout.EAST, newID);

		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.EAST, importID, 0, SpringLayout.EAST, newID);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.WEST, importID, 0, SpringLayout.WEST, newID);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.NORTH, importID, 5, SpringLayout.SOUTH, newID);

		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.SOUTH, cancel, -5, SpringLayout.SOUTH, main);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.WEST, cancel, 105, SpringLayout.WEST, main);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.EAST, cancel, 100, SpringLayout.WEST, cancel);

		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.SOUTH, cont, -5, SpringLayout.SOUTH, main);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.EAST, cont, -105, SpringLayout.EAST, main);
		((SpringLayout)main.getLayout()).putConstraint(SpringLayout.WEST, cont, -100, SpringLayout.EAST, cont);
		
		
		add(main);
		getIDs();
		setModal(true);
		setVisible(true);
	}
	public ID getSelectedID(){
		return selected;
	}
	private void getIDs(){
		EncryptionManager.importIDs();
		int s=0;
		while((s=ids.getItemCount())>0){
			ids.removeItemAt(s-1);
		}
		ArrayList<ID> idlist=EncryptionManager.getIDs();
		if(idlist.size()>0){
			for(int x=0;x<idlist.size();x++){
				ids.insertItemAt(idlist.get(x).getName(), ids.getItemCount());
			}
			ids.setSelectedIndex(0);
			idlbl.setEnabled(true);
			ids.setEnabled(true);
			passlbl.setEnabled(true);
			pass.setEnabled(true);
			cont.setEnabled(true);
		}else{
			idlbl.setEnabled(false);
			ids.setEnabled(false);
			passlbl.setEnabled(false);
			pass.setEnabled(false);
			cont.setEnabled(false);
		}
	}
	public void actionPerformed(ActionEvent arg0) {
		Object src=arg0.getSource();
		if(src==newID){
			new NewID(this);
			getIDs();
		}else if(src==importID){
			
		}else if(src==cancel){
			System.exit(0);
		}else if(src==cont){
			String p=new String(pass.getPassword());
			if(p.length()>0){
				selected=EncryptionManager.getIDs().get(ids.getSelectedIndex());
				selected.setPass(p);
				if(!selected.readID()){
					JOptionPane.showMessageDialog(this, "The given password is incorrect.", "Wrong Password", JOptionPane.WARNING_MESSAGE);
					selected=null;
					getIDs();
					pass.setText("");
					return;
				}
				setVisible(false);
			}
		}
	}
}