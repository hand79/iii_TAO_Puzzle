package com.ya803g2.jdbc.util;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTable jTable;

	private JButton btnWriteToDb;
	private JButton btnOpenPicFolder;
	private JButton btnWriteToDb_1;
	private JButton btnLoadDataFile;
	private JButton btnLoadTableInfo;
	private Vector<Table> tables;
	private JScrollPane scrollPane_1;
	private JTextArea textArea;

	private JFileChooser fileChooser;
	private JFileChooser fileChooser2;
	private JFileChooser fileChooser3;
	private File picFolder;
	private JTextField txtYag;
	private JTextField txtYag_1;
	private List<String> cpBLOBs;
	private List<String> spBLOBs;
	private JLabel lblNewLabel;
	private JLabel lblShopproductClob;
	private JSeparator separator_3;
	private JButton button;
	private JLabel label;
	private JButton button_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		fileChooser = new JFileChooser(new File("."));
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {

				return "text file";
			}

			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith(".txt") || f.isDirectory())
					return true;
				return false;
			}
		});
		fileChooser3 = new JFileChooser(new File("."));
		fileChooser3.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {

				return "text file";
			}

			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith(".txt") || f.isDirectory())
					return true;
				return false;
			}
		});

		fileChooser2 = new JFileChooser(new File("."));
		fileChooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		setTitle("YA803G2 JDBC Utility");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 756);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 109, 445, 123);
		contentPane.add(scrollPane);

		jTable = new JTable(new Object[][] {}, new Object[] { "TableName",
				"PicColumn Count" });
		scrollPane.setViewportView(jTable);

		btnLoadTableInfo = new JButton("Step1 - Load Table Info");
		btnLoadTableInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = fileChooser.showOpenDialog(null);
				if (i == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						tables = InitTableData.getTables(file);
						DefaultTableModel model = new DefaultTableModel();
						model.setColumnCount(2);
						model.setColumnIdentifiers(new Object[] { "TableName",
								"PicColumn Count" });

						for (Table t : tables) {
							model.addRow(new Object[] { t.getName(),
									t.getBlob().size() });
						}
						jTable.setModel(model);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Failed To Load Table Info", "Oops!",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnLoadTableInfo.setBounds(10, 76, 445, 23);
		contentPane.add(btnLoadTableInfo);

		btnOpenPicFolder = new JButton("Step2 - Open Pic Folder");
		btnOpenPicFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = fileChooser2.showOpenDialog(null);
				if (i == JFileChooser.APPROVE_OPTION) {
					picFolder = fileChooser2.getSelectedFile();
					textArea.setText("pic folder: " + picFolder.getPath());
				}
			}
		});
		btnOpenPicFolder.setBounds(10, 267, 221, 23);
		contentPane.add(btnOpenPicFolder);

		btnWriteToDb = new JButton("Step 3 - Write to DB");
		btnWriteToDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tables != null && picFolder != null) {

					try {
						DAO dao = new DAO(txtYag.getText(), txtYag_1.getText(),
								textArea);
						dao.writeToDB(tables, picFolder);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Load Table Info and Specify Pic Folder First",
							"Oops!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnWriteToDb.setBounds(10, 294, 221, 23);
		contentPane.add(btnWriteToDb);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 327, 445, 381);
		contentPane.add(scrollPane_1);

		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);

		txtYag = new JTextField();
		txtYag.setText("YA803G2");
		txtYag.setBounds(10, 45, 221, 21);
		contentPane.add(txtYag);
		txtYag.setColumns(10);

		txtYag_1 = new JTextField();
		txtYag_1.setText("YA803G2");
		txtYag_1.setBounds(234, 45, 221, 21);
		contentPane.add(txtYag_1);
		txtYag_1.setColumns(10);

		JLabel lblUsername = new JLabel("DB Username");
		lblUsername.setBounds(10, 25, 221, 15);
		contentPane.add(lblUsername);

		JLabel lblDbPassword = new JLabel("DB Password");
		lblDbPassword.setBounds(234, 25, 221, 15);
		contentPane.add(lblDbPassword);

		JLabel lblStep = new JLabel("Step 0 - Check Username & Password");
		lblStep.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep.setBounds(10, 2, 445, 15);
		contentPane.add(lblStep);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 20, 445, 2);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 70, 445, 2);
		contentPane.add(separator_1);

		btnLoadDataFile = new JButton("Load Data File");
		btnLoadDataFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = fileChooser3.showOpenDialog(null);
				if (i == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser3.getSelectedFile();
					try (BufferedReader reader = new BufferedReader(
							new FileReader(file))) {
						List<String> list = new ArrayList<>();
						String str = null;
						String entity = "";
						int count = 0;
						while ((str = reader.readLine()) != null) {
							str = str.trim();
							if (str.startsWith("//") || str.startsWith("/*")) {
								continue;
							}
							if (str.equals("@")) {
								list.add(entity);
								System.out.println("entity added");
								entity = "";
								count++;
								continue;
							}
							entity += (str + "\n");
						}
						cpBLOBs = list;
						lblNewLabel.setText(count + " entities found");
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Failed To Load CLOB Data File", "Oops!",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		btnLoadDataFile.setBounds(465, 76, 129, 23);
		contentPane.add(btnLoadDataFile);

		JLabel lblCaseproductClob = new JLabel("CaseProduct CLOB");
		lblCaseproductClob.setHorizontalAlignment(SwingConstants.CENTER);
		lblCaseproductClob.setBounds(465, 48, 129, 15);
		contentPane.add(lblCaseproductClob);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(465, 70, 129, 2);
		contentPane.add(separator_2);

		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(465, 113, 129, 21);
		contentPane.add(lblNewLabel);

		btnWriteToDb_1 = new JButton("Write to DB");
		btnWriteToDb_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cpBLOBs != null) {
					try {
						DAO dao = new DAO(txtYag.getText(), txtYag_1.getText(),
								textArea);
						dao.writeCLOB(cpBLOBs, "caseproduct", "cpno", "cpdesc");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Load CLOB Data File First", "Oops!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnWriteToDb_1.setBounds(465, 144, 129, 23);
		contentPane.add(btnWriteToDb_1);

		lblShopproductClob = new JLabel("ShopProduct CLOB");
		lblShopproductClob.setHorizontalAlignment(SwingConstants.CENTER);
		lblShopproductClob.setBounds(465, 216, 129, 15);
		contentPane.add(lblShopproductClob);

		separator_3 = new JSeparator();
		separator_3.setBounds(465, 240, 129, 2);
		contentPane.add(separator_3);

		button = new JButton("Load Data File");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = fileChooser3.showOpenDialog(null);
				if (i == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser3.getSelectedFile();
					try (BufferedReader reader = new BufferedReader(
							new FileReader(file))) {
						List<String> list = new ArrayList<>();
						String str = null;
						String entity = "";
						int count = 0;
						while ((str = reader.readLine()) != null) {
							str = str.trim();
							if (str.startsWith("//") || str.startsWith("/*")) {
								continue;
							}
							if (str.equals("@")) {
								list.add(entity);
								System.out.println("entity added");
								entity = "";
								count++;
								continue;
							}
							entity += (str + "\n");
						}
						spBLOBs = list;
						label.setText(count + " entities found");
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Failed To Load CLOB Data File", "Oops!",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		button.setBounds(465, 252, 129, 23);
		contentPane.add(button);

		label = new JLabel("");
		label.setBounds(465, 285, 129, 21);
		contentPane.add(label);

		button_1 = new JButton("Write to DB");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (spBLOBs != null) {
					try {
						DAO dao = new DAO(txtYag.getText(), txtYag_1.getText(),
								textArea);
						dao.writeCLOB(spBLOBs, "SHOP", "SHOPNO", "SHOP_DESC");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Load CLOB Data File First", "Oops!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		button_1.setBounds(465, 327, 129, 23);
		contentPane.add(button_1);

		btnNewButton = new JButton("Step2 - Choose Target Folder");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = fileChooser2.showOpenDialog(null);
				if (i == JFileChooser.APPROVE_OPTION) {
					dumpFolder = fileChooser2.getSelectedFile();
					textArea.setText("pic folder: " + dumpFolder.getPath());
				}
			}
		});
		btnNewButton.setBounds(246, 267, 209, 23);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("Step3 - Dump Pictures");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dumpFolder != null && tables != null) {
					if (!validFolder(dumpFolder)) {
						dumpFolder.mkdirs();
					}
					
					BufferedOutputStream fo = null;
					try {
						DAO dao = new DAO(txtYag.getText(), txtYag_1.getText(),
								textArea);

						for (Table table : tables) {
							File tableDir = new File(dumpFolder, table
									.getName());
							if (!validFolder(tableDir)) {
								tableDir.mkdir();
							}
							// pk , pics
							List<BlobRowData> tablePics = dao.getAllPics(table);
							for (BlobRowData row : tablePics) {
								File rowDir = new File(tableDir, row.getPkValue());
								if (!validFolder(rowDir)) {
									rowDir.mkdir();
								}
								byte[][] rowPics = row.getPics();
								String[] mimes = row.getMimes();
								for (int i = 0; i < rowPics.length; i++) {
									byte[] pic = rowPics[i];
									if (pic != null) {
										String mime = mimes[i];
										File picFile = new File(rowDir, Integer
												.toString(i) + getExt(mime));
										if (!picFile.exists()
												|| picFile.isDirectory()) {
											picFile.createNewFile();
										}
										fo = new BufferedOutputStream(
												new FileOutputStream(picFile));
										fo.write(pic);
										fo.flush();
										fo.close();
									}
								}
							}
						}
						dao.closeConnection();
					} catch (Exception e1) {
						if(fo != null){
							try {
								fo.close();
							} catch (IOException e2) {
								e2.printStackTrace();
							}
						}
						e1.printStackTrace();
						StackTraceElement[] sts = e1.getStackTrace();
						for (StackTraceElement ste : sts) {
							textArea.append("\n" + ste.toString());
						}
						JOptionPane.showMessageDialog(null, "Dump failed",
								"Oops!", JOptionPane.ERROR_MESSAGE);
					}

				} else if (tables == null) {
					JOptionPane.showMessageDialog(null,
							"Load Table Info First", "Oops!",
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,
							"Choose Dump Dir First", "Oops!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton_1.setBounds(246, 294, 209, 23);
		contentPane.add(btnNewButton_1);
		
		lblInsertPicturesInto = new JLabel("Insert Pictures Into DB");
		lblInsertPicturesInto.setHorizontalAlignment(SwingConstants.CENTER);
		lblInsertPicturesInto.setBounds(10, 242, 221, 15);
		contentPane.add(lblInsertPicturesInto);
		
		lblDumpPicturesFrom = new JLabel("Dump Pictures From DB");
		lblDumpPicturesFrom.setHorizontalAlignment(SwingConstants.CENTER);
		lblDumpPicturesFrom.setBounds(246, 242, 209, 15);
		contentPane.add(lblDumpPicturesFrom);
		
		separator_5 = new JSeparator();
		separator_5.setBounds(10, 259, 221, 2);
		contentPane.add(separator_5);
		
		separator_6 = new JSeparator();
		separator_6.setBounds(246, 259, 209, 5);
		contentPane.add(separator_6);
	}

	private boolean validFolder(File file) {
		if (file == null)
			throw new IllegalArgumentException(
					"File object subjected to verify is null");
		return file.exists() && file.isDirectory();
	}

	private File dumpFolder;
	private JLabel lblInsertPicturesInto;
	private JLabel lblDumpPicturesFrom;
	private JSeparator separator_5;
	private JSeparator separator_6;
	
	
	private String getExt(String mime){
		if(mime.endsWith("jpeg"))
			return ".jpg";
		if(mime.endsWith("png"))
			return ".png";
		if(mime.endsWith("gif"))
			return ".gif";
		if(mime.endsWith("bmp"))
			return ".bmp";
		return ".picture";
	}
	
}
