import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class PrisonManagementCore extends JFrame implements ActionListener {
	// Panels
	JPanel basePlane, subPlane1, subPlane2, subPlane3;

	// SubPlane1 - Add Prisoner Components
	JLabel lblAddTitle, lblName, lblAge, lblYear, lblSentence, lblReason;
	JTextField txtName, txtAge, txtYear, txtSentence, txtReason;
	JButton btnAddPrisoner, btnClearAdd;

	// SubPlane2 - Remove Prisoner Components
	JLabel lblRemoveTitle, lblRemoveId;
	JTextField txtRemoveId;
	JButton btnRemovePrisoner, btnClearRemove;
	JTextArea txtRemoveResult;

	// SubPlane3 - Search Prisoner Components
	JLabel lblSearchTitle, lblSearchBy;
	JTextField txtSearchTerm;
	JButton btnSearchById, btnSearchByName, btnSearchAll, btnClearSearch;
	JTextArea txtSearchResults;
	JScrollPane scrollSearchResults;

	// Data storage
	ArrayList<Prisoner> prisoners;

	public PrisonManagementCore() {
		super("Prison Management System");
		this.setSize(1400, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// Initialize prisoner list
		prisoners = new ArrayList<>();
		loadPrisonersFromFile();

		setupGUI();
		this.setVisible(true);
	}

	private void setupGUI() {
		// Base plane setup (main container)
		basePlane = new JPanel();
		basePlane.setLayout(null);
		basePlane.setBackground(Color.WHITE);

		setupAddPrisonerPanel();
		setupRemovePrisonerPanel();
		setupSearchPrisonerPanel();

		// Add all subplanes to basePlane
		basePlane.add(subPlane1);
		basePlane.add(subPlane2);
		basePlane.add(subPlane3);

		// Add basePlane to the frame
		this.add(basePlane);
	}

	private void setupAddPrisonerPanel() {
		// Subplane1 - Add Prisoner
		subPlane1 = new JPanel();
		subPlane1.setLayout(null);
		subPlane1.setBackground(Color.LIGHT_GRAY);
		subPlane1.setBounds(30, 20, 610, 450);
		subPlane1.setBorder(BorderFactory.createTitledBorder("Add New Prisoner"));

		// Title
		lblAddTitle = new JLabel("ADD PRISONER");
		lblAddTitle.setFont(new Font("Arial", Font.BOLD, 16));
		lblAddTitle.setBounds(20, 20, 200, 30);
		subPlane1.add(lblAddTitle);

		// Name
		lblName = new JLabel("Name:");
		lblName.setBounds(20, 60, 100, 25);
		subPlane1.add(lblName);

		txtName = new JTextField();
		txtName.setBounds(140, 60, 200, 25);
		subPlane1.add(txtName);

		// Age
		lblAge = new JLabel("Age:");
		lblAge.setBounds(20, 100, 100, 25);
		subPlane1.add(lblAge);

		txtAge = new JTextField();
		txtAge.setBounds(140, 100, 200, 25);
		subPlane1.add(txtAge);

		// Year Admitted
		lblYear = new JLabel("Year Admitted:");
		lblYear.setBounds(20, 140, 100, 25);
		subPlane1.add(lblYear);

		txtYear = new JTextField();
		txtYear.setBounds(140, 140, 200, 25);
		subPlane1.add(txtYear);

		// Sentence Years
		lblSentence = new JLabel("Sentence (Years):");
		lblSentence.setBounds(20, 180, 120, 25);
		subPlane1.add(lblSentence);

		txtSentence = new JTextField();
		txtSentence.setBounds(140, 180, 200, 25);
		subPlane1.add(txtSentence);

		// Reason
		lblReason = new JLabel("Reason:");
		lblReason.setBounds(20, 220, 100, 25);
		subPlane1.add(lblReason);

		txtReason = new JTextField();
		txtReason.setBounds(140, 220, 400, 25);
		subPlane1.add(txtReason);

		// Buttons
		btnAddPrisoner = new JButton("Add Prisoner");
		btnAddPrisoner.setBounds(140, 270, 120, 35);
		btnAddPrisoner.addActionListener(this);
		subPlane1.add(btnAddPrisoner);

		btnClearAdd = new JButton("Clear");
		btnClearAdd.setBounds(280, 270, 80, 35);
		btnClearAdd.addActionListener(this);
		subPlane1.add(btnClearAdd);
	}

	private void setupRemovePrisonerPanel() {
		// Subplane2 - Remove Prisoner
		subPlane2 = new JPanel();
		subPlane2.setLayout(null);
		subPlane2.setBackground(Color.LIGHT_GRAY);
		subPlane2.setBounds(30, 490, 610, 260);
		subPlane2.setBorder(BorderFactory.createTitledBorder("Remove Prisoner"));

		// Title
		lblRemoveTitle = new JLabel("REMOVE PRISONER");
		lblRemoveTitle.setFont(new Font("Arial", Font.BOLD, 16));
		lblRemoveTitle.setBounds(20, 20, 200, 30);
		subPlane2.add(lblRemoveTitle);

		// Prisoner ID
		lblRemoveId = new JLabel("Prisoner ID:");
		lblRemoveId.setBounds(20, 60, 100, 25);
		subPlane2.add(lblRemoveId);

		txtRemoveId = new JTextField();
		txtRemoveId.setBounds(120, 60, 150, 25);
		subPlane2.add(txtRemoveId);

		// Buttons
		btnRemovePrisoner = new JButton("Remove");
		btnRemovePrisoner.setBounds(120, 100, 100, 35);
		btnRemovePrisoner.addActionListener(this);
		subPlane2.add(btnRemovePrisoner);

		btnClearRemove = new JButton("Clear");
		btnClearRemove.setBounds(240, 100, 80, 35);
		btnClearRemove.addActionListener(this);
		subPlane2.add(btnClearRemove);

		// Result area
		txtRemoveResult = new JTextArea();
		txtRemoveResult.setBounds(20, 150, 550, 80);
		txtRemoveResult.setEditable(false);
		txtRemoveResult.setBackground(Color.WHITE);
		txtRemoveResult.setBorder(BorderFactory.createLoweredBevelBorder());
		subPlane2.add(txtRemoveResult);
	}

	private void setupSearchPrisonerPanel() {
		// Subplane3 - Search Prisoner
		subPlane3 = new JPanel();
		subPlane3.setLayout(null);
		subPlane3.setBackground(Color.LIGHT_GRAY);
		subPlane3.setBounds(660, 20, 700, 730);
		subPlane3.setBorder(BorderFactory.createTitledBorder("Search Prisoners"));
		
		// Title
		lblSearchTitle = new JLabel("SEARCH PRISONERS");
		lblSearchTitle.setFont(new Font("Arial", Font.BOLD, 16));
		lblSearchTitle.setBounds(20, 20, 200, 30);
		subPlane3.add(lblSearchTitle);
		
		// Search term
		lblSearchBy = new JLabel("Prisoner ID:");
		lblSearchBy.setBounds(20, 60, 100, 25);
		subPlane3.add(lblSearchBy);
		
		txtSearchTerm = new JTextField();
		txtSearchTerm.setBounds(120, 60, 200, 25);
		subPlane3.add(txtSearchTerm);
		
		// Search buttons - only search by ID and show all
		btnSearchById = new JButton("Search by ID");
		btnSearchById.setBounds(20, 100, 120, 35);
		btnSearchById.addActionListener(this);
		subPlane3.add(btnSearchById);
		
		btnSearchAll = new JButton("Show All");
		btnSearchAll.setBounds(160, 100, 100, 35);
		btnSearchAll.addActionListener(this);
		subPlane3.add(btnSearchAll);
		
		btnClearSearch = new JButton("Clear");
		btnClearSearch.setBounds(280, 100, 80, 35);
		btnClearSearch.addActionListener(this);
		subPlane3.add(btnClearSearch);
		
		// Results area
		txtSearchResults = new JTextArea();
		txtSearchResults.setEditable(false);
		txtSearchResults.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		scrollSearchResults = new JScrollPane(txtSearchResults);
		scrollSearchResults.setBounds(20, 150, 650, 550);
		subPlane3.add(scrollSearchResults);
	}


	// Action event handling
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddPrisoner) {
			addPrisoner();
		} else if (e.getSource() == btnClearAdd) {
			clearAddFields();
		} else if (e.getSource() == btnRemovePrisoner) {
			removePrisoner();
		} else if (e.getSource() == btnClearRemove) {
			clearRemoveFields();
		} else if (e.getSource() == btnSearchById) {
			searchPrisonerById();
		} else if (e.getSource() == btnSearchAll) {
			showAllPrisoners();
		} else if (e.getSource() == btnClearSearch) {
			clearSearchFields();
		}
	}


	// Add Prisoner functionality
	private void addPrisoner() {
		try {
			String name = txtName.getText().trim();
			String ageStr = txtAge.getText().trim();
			String yearStr = txtYear.getText().trim();
			String sentenceStr = txtSentence.getText().trim();
			String reason = txtReason.getText().trim();

			// Validation
			if (name.isEmpty() || ageStr.isEmpty() || yearStr.isEmpty() || sentenceStr.isEmpty() || reason.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int age = Integer.parseInt(ageStr);
			int year = Integer.parseInt(yearStr);
			int sentence = Integer.parseInt(sentenceStr);

			// Generate unique ID
			String id = Prisoner.generateNextId(prisoners);

			// Create new prisoner
			Prisoner newPrisoner = new Prisoner(id, name, age, year, sentence, reason);

			if (newPrisoner.isValid()) {
				prisoners.add(newPrisoner);
				savePrisonersToFile();
				JOptionPane.showMessageDialog(this, "Prisoner added successfully!\nID: " + id, "Success", JOptionPane.INFORMATION_MESSAGE);
				clearAddFields();
			} else {
				JOptionPane.showMessageDialog(this, "Invalid prisoner data!", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter valid numbers for age, year, and sentence!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Remove Prisoner functionality
	private void removePrisoner() {
		String id = txtRemoveId.getText().trim();
		if (id.isEmpty()) {
			txtRemoveResult.setText("Please enter a Prisoner ID!");
			return;
		}

		boolean found = false;
		for (int i = 0; i < prisoners.size(); i++) {
			if (prisoners.get(i).matchesId(id)) {
				Prisoner removed = prisoners.remove(i);
				savePrisonersToFile();
				txtRemoveResult.setText("Prisoner removed successfully!\n" + removed.getFormattedInfo());
				found = true;
				break;
			}
		}

		if (!found) {
			txtRemoveResult.setText("Prisoner with ID '" + id + "' not found!");
		}
	}

	// Search Prisoner by ID only
private void searchPrisonerById() {
    String id = txtSearchTerm.getText().trim();
    if (id.isEmpty()) {
        txtSearchResults.setText("Please enter a Prisoner ID to search!");
        return;
    }
    
    StringBuilder results = new StringBuilder();
    results.append("SEARCH RESULTS (By ID):\n");
    results.append("=".repeat(50)).append("\n\n");
    
    boolean found = false;
    for (Prisoner p : prisoners) {
        if (p.matchesId(id)) {
            results.append("PRISONER FOUND:\n");
            results.append("-".repeat(30)).append("\n\n");
            
            results.append(" Prisoner ID:\n");
            results.append("    ").append(p.getPrisonerId()).append("\n\n");
            
            results.append(" Name:\n");
            results.append("    ").append(p.getName()).append("\n\n");
            
            results.append(" Age:\n");
            results.append("    ").append(p.getAge()).append(" years old\n\n");
            
            results.append(" Year Admitted:\n");
            results.append("    ").append(p.getYearAdmitted()).append("\n\n");
            
            results.append(" Sentence Years:\n");
            results.append("    ").append(p.getSentenceYears()).append(" years\n\n");
            
            results.append(" Reason for Imprisonment:\n");
            results.append("    ").append(p.getReason()).append("\n\n");
            
            results.append(" Expected Release Year:\n");
            results.append("    ").append(p.getExpectedReleaseYear()).append("\n\n");
            
            results.append(" Years Served:\n");
            results.append("    ").append(p.getYearsServed()).append(" years\n\n");
            
            results.append(" Remaining Years:\n");
            results.append("    ").append(p.getRemainingYears()).append(" years\n\n");
            
            results.append(" Current Status:\n");
            results.append("    ").append(p.getStatus()).append("\n\n");
            
            found = true;
            break;
        }
    }
    
    if (!found) {
        results.append("PRISONER NOT FOUND:\n");
        results.append("-".repeat(30)).append("\n\n");
        results.append("No prisoner found with ID: ").append(id).append("\n\n");
        results.append("Please check the ID and try again.\n");
        results.append("Use 'Show All' to see all available prisoner IDs.");
    }
    
    txtSearchResults.setText(results.toString());
}

	// Show All Prisoners
	private void showAllPrisoners() {
		StringBuilder results = new StringBuilder();
		results.append("ALL PRISONERS:\n");
		results.append("=".repeat(50)).append("\n\n");

		if (prisoners.isEmpty()) {
			results.append("No prisoners in the system.");
		} else {
			for (Prisoner p : prisoners) {
				results.append(p.getFormattedInfo()).append("\n\n");
			}
			results.append("Total prisoners: ").append(prisoners.size());
		}

		txtSearchResults.setText(results.toString());
	}

	// Clear methods
	private void clearAddFields() {
		txtName.setText("");
		txtAge.setText("");
		txtYear.setText("");
		txtSentence.setText("");
		txtReason.setText("");
	}

	private void clearRemoveFields() {
		txtRemoveId.setText("");
		txtRemoveResult.setText("");
	}

	private void clearSearchFields() {
		txtSearchTerm.setText("");
		txtSearchResults.setText("");
	}

	// File operations
	private void loadPrisonersFromFile() {
		try {
			File file = new File(Prisoner.DATA_FILE);
			if (!file.exists()) {
				return; // No file exists yet
			}

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine(); // Skip header

			while ((line = reader.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					try {
						Prisoner p = Prisoner.fromTSVString(line);
						if (p != null && p.isValid()) {
							prisoners.add(p);
						}
					} catch (Exception e) {
						// Skip invalid lines
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading prisoner data: " + e.getMessage(), "File Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void savePrisonersToFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(Prisoner.DATA_FILE));
			writer.write(Prisoner.getTSVHeader());
			writer.newLine();

			for (Prisoner p : prisoners) {
				writer.write(p.toSafeTSVString());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error saving prisoner data: " + e.getMessage(), "File Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}