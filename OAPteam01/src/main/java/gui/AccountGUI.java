package gui;

import javax.swing.*;
import java.awt.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * @author Stian Sahlsten Rosvald
 */



public class AccountGUI extends HomePageGUI{

	public AccountGUI() {
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton addProfileButton = new JButton("Add Profile");
		addProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPanel.add(addProfileButton);
		
		JButton displayProfilesButton = new JButton("Display Profiles");
		contentPanel.add(displayProfilesButton);
		
		JButton deleteProfileButton = new JButton("Delete Profile");
		contentPanel.add(deleteProfileButton);
		// TODO Auto-generated constructor stub
	}

}
