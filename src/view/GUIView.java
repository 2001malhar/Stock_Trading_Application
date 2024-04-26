package view;

import controller.GUIController;
import controller.GUIControllerInt;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Represents the graphical user interface (GUI) for the system.
 */
public class GUIView extends JFrame implements GUIVIewInt {

  private JPanel inputPanel;
  private JPanel outputPanel;
  private JButton createPortfolioButton;
  private JButton buyStockButton;
  private JButton sellStockButton;
  private JButton costButton;
  private JButton valueButton;
  private JButton features;
  private JButton loadDollarCostButton;
  private JTextField portfolioName;
  private JTextField shares;
  private JTextField symbolName;
  private JTextField startDate;
  private String[] featuresOptions;
  private String[] portfolioNames;
  private String[] dollarCostNames;
  private JMenuItem saveMenuItem;
  private JMenuItem loadMenuItem;
  private JFileChooser fileChooser;
  private String selectedPortfolio;

  /**
   * Constructor for the GUIView class.
   *
   * @param controller The controller that manages the GUI interactions.
   */
  public GUIView(GUIControllerInt controller) {
    super("Stock Management System");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1000, 750);
    this.setLayout(new BorderLayout());

    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        int confirmExit = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to exit?", "Confirm Exit",
                JOptionPane.YES_NO_OPTION);
        if (confirmExit == JOptionPane.YES_OPTION) {
          System.exit(0);
        }
      }
    });

    fileChooser = new JFileChooser();
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");
    fileMenu.setFont(fileMenu.getFont().deriveFont(20f));
    saveMenuItem = new JMenuItem("Save");
    saveMenuItem.setFont(saveMenuItem.getFont().deriveFont(20f));
    loadMenuItem = new JMenuItem("Load");
    loadMenuItem.setFont(loadMenuItem.getFont().deriveFont(20f));
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    exitMenuItem.setFont(exitMenuItem.getFont().deriveFont(20f));

    JPanel panel1 = new JPanel();
    panel1.setBackground(new Color(0x0066A2));
    panel1.setPreferredSize(new Dimension(300, 750));
    panel1.setLayout(new GridLayout(8, 1, 50, 10));

    JPanel panel2 = new JPanel();
    panel2.setBackground(Color.BLUE);
    panel2.setPreferredSize(new Dimension(650, 750));
    panel2.setLayout(new GridLayout(2, 1));

    inputPanel = new JPanel();
    inputPanel.setBackground(Color.BLACK);
    inputPanel.setPreferredSize(new Dimension(650, 300));
    inputPanel.setLayout(new GridBagLayout());

    outputPanel = new JPanel();
    outputPanel.setBackground(Color.DARK_GRAY);
    outputPanel.setPreferredSize(new Dimension(650, 450));

    createPortfolioButton = new JButton("Create Portfolio");
    buyStockButton = new JButton("Buy Stocks");
    sellStockButton = new JButton("Sell Stocks");
    costButton = new JButton("Calculate Cost of Portfolio");
    valueButton = new JButton("Calculate Value of Portfolio");
    JButton dollarCostAverage = new JButton("Dollar Cost Average");
    features = new JButton("Features");
    loadDollarCostButton = new JButton("Load Dollar Cost Average");

    featuresOptions = new String[]{"Performance on a Day", "Performance over N days",
        "Calculate X days moving average", "Get Crossovers", "Get Moving Crossovers",
        "Stock Chart", "Portfolio Chart"};

    JComboBox<String> featuresDropDown = new JComboBox<>(featuresOptions);

    portfolioName = new JTextField(30);
    portfolioName.setText("");
    shares = new JTextField(30);
    shares.setText("");
    symbolName = new JTextField(30);
    symbolName.setText("");
    startDate = new JTextField(30);
    startDate.setText("");
    JTextField endDate = new JTextField(30);
    endDate.setText("");
    JTextField noOfDays = new JTextField(15);
    noOfDays.setText("");
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(5, 10, 5, 10);

    exitMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int confirmExit = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to exit?", "Confirm Exit",
                JOptionPane.YES_NO_OPTION);
        if (confirmExit == JOptionPane.YES_OPTION) {
          System.exit(0);
        }
      }
    });

    createPortfolioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (createPortfolioButton.isEnabled()) {
          inputPanel.removeAll();
          gbc.gridx = 0;
          gbc.gridy = 0;
          JLabel portfolioLabel = new JLabel("Name of Portfolio");
          portfolioLabel.setForeground(Color.WHITE);
          inputPanel.add(portfolioLabel, gbc);

          gbc.gridx = 1;
          gbc.gridwidth = 2;
          gbc.fill = GridBagConstraints.HORIZONTAL;
          JTextField portfolioName = new JTextField(20);
          portfolioName.setForeground(Color.BLACK);
          inputPanel.add(portfolioName, gbc);

          gbc.gridwidth = 1;
          gbc.fill = GridBagConstraints.NONE;

          JButton okButton = new JButton("OK");
          gbc.gridx = 1;
          gbc.gridy++;
          inputPanel.add(okButton, gbc);

          okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String portfolioNameValue = portfolioName.getText();
              if (portfolioNameValue.matches(".*[a-zA-Z0-9].*")) {
                createPortfolio(controller, portfolioNameValue);
                portfolioName.setText("");
              } else {
                JOptionPane.showMessageDialog(null,
                    "Portfolio name must contain at least one alphanumeric character.", "Error",
                    JOptionPane.ERROR_MESSAGE);
              }
            }
          });

          inputPanel.revalidate();
          inputPanel.repaint();
        }
      }
    });

    saveMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (saveMenuItem.isEnabled()) {
          fileChooser = new JFileChooser();
          int returnValue = fileChooser.showSaveDialog(GUIView.this);
          if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String portfolioNameValue = selectedFile.getName();
            String filePath = selectedFile.getAbsolutePath();
            savePortfolio(controller, portfolioNameValue, filePath);
            portfolioName.setText("");
          }
          inputPanel.revalidate();
          inputPanel.repaint();
        }
      }
    });

    loadMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (loadMenuItem.isEnabled()) {
          fileChooser = new JFileChooser();
          fileChooser.setCurrentDirectory(new File("."));
          int returnValue = fileChooser.showOpenDialog(GUIView.this);

          if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            String portfolioNameValue = portfolioName.getText();
            loadPortfolio(controller, portfolioNameValue, filePath);
            portfolioName.setText("");

            inputPanel.revalidate();
            inputPanel.repaint();
          }
        }
      }
    });

    loadDollarCostButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (loadDollarCostButton.isEnabled()) {
          try {
            dollarCostNames = controller.listDollarCostGUI();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
          inputPanel.removeAll();
          gbc.gridx = 0;
          gbc.gridy = 0;
          JLabel portfolioLabel = new JLabel("Name of Portfolio");
          portfolioLabel.setForeground(Color.WHITE);
          inputPanel.add(portfolioLabel, gbc);

          JComboBox<String> dollarCostDropDown = new JComboBox<>();
          for (String name : dollarCostNames) {

            String portfolioNameWithoutExtension = name.replace(".csv", "");
            dollarCostDropDown.addItem(portfolioNameWithoutExtension);
          }
          dollarCostDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              selectedPortfolio = (String) dollarCostDropDown.getSelectedItem();
            }
          });
          gbc.gridx = 1;
          inputPanel.add(dollarCostDropDown, gbc);

          JButton okButton = new JButton("OK");
          gbc.gridx = 1;
          gbc.gridy++;
          inputPanel.add(okButton, gbc);

          okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              try {
                loadDollarCostCSV(controller, selectedPortfolio);
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
              portfolioName.setText("");
            }
          });

          inputPanel.revalidate();
          inputPanel.repaint();
        }
      }
    });

    buyStockButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (buyStockButton.isEnabled()) {
          portfolioNames = controller.getFlexiblePortfolioNames();
          inputPanel.removeAll();
          gbc.gridx = 0;
          gbc.gridy = 0;
          JLabel portfolioLabel = new JLabel("Name of Portfolio");
          portfolioLabel.setForeground(Color.WHITE);
          inputPanel.add(portfolioLabel, gbc);

          JComboBox<String> portfolioDropDown = new JComboBox<>(portfolioNames);
          portfolioDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              selectedPortfolio = (String) portfolioDropDown.getSelectedItem();
            }
          });
          gbc.gridx = 1;
          inputPanel.add(portfolioDropDown, gbc);

          gbc.gridx = 0;
          gbc.gridy++;
          JLabel sharesLabel = new JLabel("Number of Shares");
          sharesLabel.setForeground(Color.WHITE);
          inputPanel.add(sharesLabel, gbc);

          gbc.gridx = 1;
          inputPanel.add(shares, gbc);

          gbc.gridx = 0;
          gbc.gridy++;
          JLabel symbolLabel = new JLabel("Symbol");
          symbolLabel.setForeground(Color.WHITE);
          inputPanel.add(symbolLabel, gbc);

          gbc.gridx = 1;
          inputPanel.add(symbolName, gbc);

          gbc.gridx = 0;
          gbc.gridy++;
          JLabel dateLabel = new JLabel("Date (YYYY-MM-DD)");
          dateLabel.setForeground(Color.WHITE);
          inputPanel.add(dateLabel, gbc);

          gbc.gridx = 1;
          DateSelection dateSelection1 = new DateSelection();
          dateSelection1.setForeground(Color.WHITE);
          inputPanel.add(dateSelection1, gbc);

          JButton okButton = new JButton("OK");
          gbc.gridx = 1;
          gbc.gridy++;
          inputPanel.add(okButton, gbc);
          okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              try {
                double numShares = Double.parseDouble(shares.getText());
                if (numShares <= 0) {
                  JOptionPane.showMessageDialog(null,
                      "Number of shares must be greater than zero.", "Error",
                      JOptionPane.ERROR_MESSAGE);
                  return;
                }
                String portfolioNameValue = (String) portfolioName.getText();
                String symbol = symbolName.getText();
                String date = dateSelection1.getDateAsString();

                if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                  JOptionPane.showMessageDialog(null,
                      "Please enter the date in YYYY-MM-DD format.", "Error",
                      JOptionPane.ERROR_MESSAGE);
                  return;
                }
                System.out.println(selectedPortfolio);
                buyStocks(controller, selectedPortfolio, numShares, symbol, date);
                shares.setText("");
                symbolName.setText("");
                startDate.setText("");
                portfolioName.setText("");
              } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                    "Please enter a valid number for the number of shares.", "Error",
                    JOptionPane.ERROR_MESSAGE);
              }
            }
          });

          inputPanel.revalidate();
          inputPanel.repaint();
        }
      }
    });

    sellStockButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (sellStockButton.isEnabled()) {
          portfolioNames = controller.getFlexiblePortfolioNames();
          inputPanel.removeAll();
          gbc.gridx = 0;
          gbc.gridy = 0;
          JLabel portfolioLabel = new JLabel("Name of Portfolio");
          portfolioLabel.setForeground(Color.WHITE);
          inputPanel.add(portfolioLabel, gbc);

          JComboBox<String> portfolioDropDown = new JComboBox<>(portfolioNames);
          portfolioDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              selectedPortfolio = (String) portfolioDropDown.getSelectedItem();
            }
          });
          gbc.gridx = 1;
          inputPanel.add(portfolioDropDown, gbc);

          gbc.gridx = 0;
          gbc.gridy++;
          JLabel sharesLabel = new JLabel("Number of Shares");
          sharesLabel.setForeground(Color.WHITE);
          inputPanel.add(sharesLabel, gbc);

          gbc.gridx = 1;
          inputPanel.add(shares, gbc);

          gbc.gridx = 0;
          gbc.gridy++;
          JLabel symbolLabel = new JLabel("Symbol");
          symbolLabel.setForeground(Color.WHITE);
          inputPanel.add(symbolLabel, gbc);

          gbc.gridx = 1;
          inputPanel.add(symbolName, gbc);

          gbc.gridx = 0;
          gbc.gridy++;
          JLabel dateLabel = new JLabel("Date (YYYY-MM-DD)");
          dateLabel.setForeground(Color.WHITE);
          inputPanel.add(dateLabel, gbc);

          gbc.gridx = 1;
          DateSelection dateSelection3 = new DateSelection();
          dateSelection3.setForeground(Color.WHITE);
          inputPanel.add(dateSelection3, gbc);

          JButton okButton = new JButton("OK");
          gbc.gridx = 1;
          gbc.gridy++;
          inputPanel.add(okButton, gbc);

          okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              try {
                double numShares = Double.parseDouble(shares.getText());
                if (numShares <= 0) {
                  JOptionPane.showMessageDialog(null,
                      "Number of shares must be greater than zero.", "Error",
                      JOptionPane.ERROR_MESSAGE);
                  return;
                }
                String portfolioNameValue = portfolioName.getText();
                String symbol = symbolName.getText();
                String date = dateSelection3.getDateAsString();
                if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                  JOptionPane.showMessageDialog(null,
                      "Please enter the date in YYYY-MM-DD format.", "Error",
                      JOptionPane.ERROR_MESSAGE);
                  return;
                }
                sellStocks(controller, selectedPortfolio, numShares, symbol, date);
                portfolioName.setText("");
                shares.setText("");
                symbolName.setText("");
                startDate.setText("");
              } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                    "Please enter a valid number for the number of shares.", "Error",
                    JOptionPane.ERROR_MESSAGE);
              }
            }
          });

          inputPanel.revalidate();
          inputPanel.repaint();
        }
      }
    });

    costButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (costButton.isEnabled()) {
          portfolioNames = controller.getFlexiblePortfolioNames();
          inputPanel.removeAll();
          gbc.gridx = 0;
          gbc.gridy = 0;
          JLabel portfolioLabel = new JLabel("Name of Portfolio");
          portfolioLabel.setForeground(Color.WHITE);
          inputPanel.add(portfolioLabel, gbc);

          JComboBox<String> portfolioDropDown = new JComboBox<>(portfolioNames);
          portfolioDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              selectedPortfolio = (String) portfolioDropDown.getSelectedItem();
            }
          });
          gbc.gridx = 1;
          inputPanel.add(portfolioDropDown, gbc);
          gbc.gridx = 0;
          gbc.gridy++;
          JLabel dateLabel = new JLabel("Date (YYYY-MM-DD)");
          dateLabel.setForeground(Color.WHITE);
          inputPanel.add(dateLabel, gbc);

          gbc.gridx = 1;
          DateSelection dateSelection5 = new DateSelection();
          inputPanel.add(dateSelection5, gbc);

          JButton okButton = new JButton("OK");
          gbc.gridx = 1;
          gbc.gridy++;
          inputPanel.add(okButton, gbc);

          okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String portfolioNameValue = portfolioName.getText();
              String date = dateSelection5.getDateAsString();
              if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(null,
                    "Please enter the date in YYYY-MM-DD format.", "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
              }
              calculateCost(controller, selectedPortfolio, date);
              portfolioName.setText("");
              startDate.setText("");
            }
          });
          inputPanel.revalidate();
          inputPanel.repaint();
        }
      }
    });

    valueButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (valueButton.isEnabled()) {
          portfolioNames = controller.getFlexiblePortfolioNames();
          inputPanel.removeAll();
          gbc.gridx = 0;
          gbc.gridy = 0;
          JLabel portfolioLabel = new JLabel("Name of Portfolio");
          portfolioLabel.setForeground(Color.WHITE);
          inputPanel.add(portfolioLabel, gbc);

          JComboBox<String> portfolioDropDown = new JComboBox<>(portfolioNames);
          portfolioDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              selectedPortfolio = (String) portfolioDropDown.getSelectedItem();
            }
          });
          gbc.gridx = 1;
          inputPanel.add(portfolioDropDown, gbc);
          gbc.gridx = 0;
          gbc.gridy++;
          JLabel dateLabel = new JLabel("Date (YYYY-MM-DD)");
          dateLabel.setForeground(Color.WHITE);
          inputPanel.add(dateLabel, gbc);

          gbc.gridx = 1;
          DateSelection dateSelection7 = new DateSelection();
          inputPanel.add(dateSelection7, gbc);
          JButton okButton = new JButton("OK");
          gbc.gridx = 1;
          gbc.gridy++;
          inputPanel.add(okButton, gbc);

          okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String portfolioNameValue = portfolioName.getText();
              String date = dateSelection7.getDateAsString();
              if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(null,
                    "Please enter the date in YYYY-MM-DD format.", "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
              }
              calculateValue(controller, selectedPortfolio, date);
              portfolioName.setText("");
              startDate.setText("");
            }
          });

          inputPanel.revalidate();
          inputPanel.repaint();
        }
      }
    });

    dollarCostAverage.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        portfolioNames = controller.getFlexiblePortfolioNames();
        inputPanel.removeAll();
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel portfolioLabel = new JLabel("Name of Portfolio");
        portfolioLabel.setForeground(Color.WHITE);
        inputPanel.add(portfolioLabel, gbc);

        JComboBox<String> portfolioDropDown = new JComboBox<>(portfolioNames);
        portfolioDropDown.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            selectedPortfolio = (String) portfolioDropDown.getSelectedItem();
          }
        });
        gbc.gridx = 1;
        inputPanel.add(portfolioDropDown, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setForeground(Color.WHITE);
        inputPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        JTextField amountField = new JTextField(20);
        amountField.setForeground(Color.BLACK);
        inputPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel frequencyLabel = new JLabel("Frequency (in days):");
        frequencyLabel.setForeground(Color.WHITE);
        inputPanel.add(frequencyLabel, gbc);

        gbc.gridx = 1;
        JTextField frequencyField = new JTextField(20);
        frequencyField.setForeground(Color.BLACK);
        inputPanel.add(frequencyField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD)");
        startDateLabel.setForeground(Color.WHITE);
        inputPanel.add(startDateLabel, gbc);
        gbc.gridx = 1;
        DateSelection dateSelectionC = new DateSelection();
        inputPanel.add(dateSelectionC, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel endDateLabel = new JLabel("End Date (YYYY-MM-DD)");
        endDateLabel.setForeground(Color.WHITE);
        inputPanel.add(endDateLabel, gbc);
        gbc.gridx = 1;
        DateSelection dateSelectionD = new DateSelection();
        inputPanel.add(dateSelectionD, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel numOfStocksLabel = new JLabel("Number of Stocks:");
        numOfStocksLabel.setForeground(Color.WHITE);
        inputPanel.add(numOfStocksLabel, gbc);

        gbc.gridx = 1;
        JTextField numOfStocksField = new JTextField(20);
        numOfStocksField.setForeground(Color.BLACK);
        inputPanel.add(numOfStocksField, gbc);

        gbc.gridy++;
        JButton addStocksButton = new JButton("Add Stocks");
        addStocksButton.setForeground(Color.BLACK);
        inputPanel.add(addStocksButton, gbc);

        addStocksButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int numOfStocks = Integer.parseInt(numOfStocksField.getText());
            inputPanel.removeAll();
            List<JTextField> symbolFields = new ArrayList<>();
            List<JTextField> percentageFields = new ArrayList<>();
            for (int i = 0; i < numOfStocks; i++) {
              gbc.gridx = 0;
              gbc.gridy++;
              JLabel stockSymbolLabel = new JLabel("Stock " + (i + 1) + " Symbol:");
              stockSymbolLabel.setForeground(Color.WHITE);
              inputPanel.add(stockSymbolLabel, gbc);
              gbc.gridx = 1;
              JTextField symbolField = new JTextField(20);
              symbolField.setForeground(Color.BLACK);
              inputPanel.add(symbolField, gbc);
              symbolFields.add(symbolField);

              gbc.gridx = 0;
              gbc.gridy++;
              JLabel percentageLabel = new JLabel("Stock " + (i + 1) + " Percentage (%):");
              percentageLabel.setForeground(Color.WHITE);
              inputPanel.add(percentageLabel, gbc);
              gbc.gridx = 1;
              JTextField percentageField = new JTextField(20);
              percentageField.setForeground(Color.BLACK);
              inputPanel.add(percentageField, gbc);
              percentageFields.add(percentageField);
            }

            gbc.gridy++;
            JButton okButton = new JButton("OK");
            okButton.setForeground(Color.BLACK);
            inputPanel.add(okButton, gbc);

            okButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(amountField.getText());
                String date = dateSelectionC.getDateAsString();
                String eDate = dateSelectionD.getDateAsString();
                int frequency = Integer.parseInt(frequencyField.getText());

                if ((amountField.getText() == null) || (frequencyField.getText() == null)) {
                  JOptionPane.showMessageDialog(null,
                      "Please fill in all required fields.", "Error",
                          JOptionPane.ERROR_MESSAGE);
                  return;
                }

                try {
                  if (amount <= 0) {
                    throw new NumberFormatException();
                  }
                } catch (NumberFormatException ex) {
                  JOptionPane.showMessageDialog(null,
                      "Amount must be a positive number.", "Error",
                          JOptionPane.ERROR_MESSAGE);
                  return;
                }
                try {
                  if (frequency <= 0) {
                    throw new NumberFormatException();
                  }
                } catch (NumberFormatException ex) {
                  JOptionPane.showMessageDialog(null,
                      "Frequency must be a positive integer.",
                      "Error", JOptionPane.ERROR_MESSAGE);
                  return;
                }

                if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                  JOptionPane.showMessageDialog(null,
                      "Start date must be in YYYY-MM-DD format.", "Error",
                      JOptionPane.ERROR_MESSAGE);
                  return;
                }

                if (!eDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                  JOptionPane.showMessageDialog(null,
                      "End date must be in YYYY-MM-DD format.",
                      "Error", JOptionPane.ERROR_MESSAGE);
                  return;
                }

                List<String> symbols = new ArrayList<>();
                List<Double> weights = new ArrayList<>();
                double totalWeight = 0.0;

                for (int i = 0; i < numOfStocks; i++) {
                  String symbol = symbolFields.get(i).getText();
                  double percentage = Double.parseDouble(percentageFields.get(i).getText());
                  String symbolText = symbolFields.get(i).getText().trim();
                  String weightText = percentageFields.get(i).getText().trim();

                  if (symbolText.isEmpty() || weightText.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                        "Please fill in all symbol and weight fields.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                  }

                  try {
                    percentage = Double.parseDouble(weightText);
                    if (percentage <= 0 || percentage > 100) {
                      throw new NumberFormatException();
                    }
                    symbols.add(symbol);
                    weights.add(percentage);
                    totalWeight += percentage;
                  } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                        "Weight must be a positive number between 0 and 100.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                  }
                }

                if (totalWeight != 100.0) {
                  JOptionPane.showMessageDialog(null,
                      "Total weight of all stocks must be equal to 100.", "Error",
                      JOptionPane.ERROR_MESSAGE);
                  return;
                }

                try {
                  dollarCost(controller, selectedPortfolio, amount, date,
                      eDate, frequency, symbols, weights);
                } catch (IOException ex) {
                  throw new RuntimeException(ex);
                }

                inputPanel.revalidate();
                inputPanel.repaint();
              }
            });

            inputPanel.revalidate();
            inputPanel.repaint();
          }
        });

        inputPanel.revalidate();
        inputPanel.repaint();
      }
    });

    features.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (features.isEnabled()) {
          inputPanel.removeAll();
          gbc.gridx = 0;
          gbc.gridy = 0;
          JLabel selectFeatureLabel = new JLabel("Select Feature");
          selectFeatureLabel.setForeground(Color.WHITE);
          inputPanel.add(selectFeatureLabel, gbc);

          gbc.gridx = 1;
          JComboBox<String> featuresComboBox = new JComboBox<>(featuresOptions);
          inputPanel.add(featuresComboBox, gbc);

          inputPanel.revalidate();
          inputPanel.repaint();
          featuresComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String selectedFeature = (String) featuresComboBox.getSelectedItem();

              if (selectedFeature != null) {
                switch (selectedFeature) {
                  case "Performance on a Day":
                    inputPanel.removeAll();
                    gbc.gridx = 0;
                    gbc.gridy = 0;

                    JLabel symbolLabel = new JLabel("Symbol");
                    symbolLabel.setForeground(Color.WHITE);
                    inputPanel.add(symbolLabel, gbc);
                    gbc.gridx = 1;
                    inputPanel.add(symbolName, gbc);
                    gbc.gridx = 0;
                    gbc.gridy++;
                    JLabel dateLabel = new JLabel("Date (YYYY-MM-DD)");
                    dateLabel.setForeground(Color.WHITE);
                    inputPanel.add(dateLabel, gbc);
                    gbc.gridx = 1;
                    DateSelection dateSelection9 = new DateSelection();
                    inputPanel.add(dateSelection9, gbc);

                    inputPanel.revalidate();
                    inputPanel.repaint();

                    JButton okButton = new JButton("OK");
                    gbc.gridx = 1;
                    gbc.gridy++;
                    inputPanel.add(okButton, gbc);

                    okButton.addActionListener(new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        try {
                          String symbol = symbolName.getText();
                          String date = dateSelection9.getDateAsString();

                          if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            JOptionPane.showMessageDialog(null,
                                "Please enter the date in YYYY-MM-DD format.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                          }
                          performanceOnDay(controller, symbol, date);
                          symbolName.setText("");
                          startDate.setText("");
                        } catch (NumberFormatException ex) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter a valid integer for the number of shares.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                        }
                      }
                    });
                    break;
                  case "Calculate X days moving average":
                    inputPanel.removeAll();
                    gbc.gridx = 0;
                    gbc.gridy = 0;

                    symbolLabel = new JLabel("Symbol");
                    symbolLabel.setForeground(Color.WHITE);
                    inputPanel.add(symbolLabel, gbc);

                    gbc.gridx = 1;
                    inputPanel.add(symbolName, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    JLabel startDateLabel = new JLabel("Start Date");
                    startDateLabel.setForeground(Color.WHITE);
                    inputPanel.add(startDateLabel, gbc);

                    gbc.gridx = 1;
                    DateSelection dateSelection = new DateSelection();
                    inputPanel.add(dateSelection, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    JLabel numOfDaysLabel = new JLabel("Number of Days");
                    numOfDaysLabel.setForeground(Color.WHITE);
                    inputPanel.add(numOfDaysLabel, gbc);

                    gbc.gridx = 1;
                    JTextField noOfDaysField = new JTextField(10);
                    inputPanel.add(noOfDaysField, gbc);

                    inputPanel.revalidate();
                    inputPanel.repaint();

                    JButton okButton1 = new JButton("OK");
                    gbc.gridx = 1;
                    gbc.gridy++;
                    inputPanel.add(okButton1, gbc);
                    okButton1.addActionListener(new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        // Get input values
                        String symbol = symbolName.getText();
                        String formattedDate = dateSelection.getDateAsString();
                        if (!formattedDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter the date in YYYY-MM-DD format.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }
                        int noOfDays;
                        try {
                          noOfDays = Integer.parseInt(noOfDaysField.getText());
                          xDaysAverage(controller, symbol, formattedDate, noOfDays);
                        } catch (NumberFormatException ex) {
                          System.err.println("Invalid input for number of days");
                        }
                      }
                    });
                    break;
                  case "Performance over N days":
                    inputPanel.removeAll();
                    gbc.gridx = 0;
                    gbc.gridy = 0;

                    symbolLabel = new JLabel("Symbol");
                    symbolLabel.setForeground(Color.WHITE);
                    inputPanel.add(symbolLabel, gbc);

                    gbc.gridx = 1;
                    inputPanel.add(symbolName, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    startDateLabel = new JLabel("Start Date");
                    startDateLabel.setForeground(Color.WHITE);
                    inputPanel.add(startDateLabel, gbc);

                    gbc.gridx = 1;
                    DateSelection dateSelection13 = new DateSelection();
                    inputPanel.add(dateSelection13, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    JLabel endDateLabel = new JLabel("End Date");
                    endDateLabel.setForeground(Color.WHITE);
                    inputPanel.add(endDateLabel, gbc);

                    gbc.gridx = 1;
                    DateSelection dateSelection14 = new DateSelection();
                    inputPanel.add(dateSelection14, gbc);

                    inputPanel.revalidate();
                    inputPanel.repaint();

                    JButton okButton2 = new JButton("OK");
                    gbc.gridx = 1;
                    gbc.gridy++;
                    inputPanel.add(okButton2, gbc);

                    okButton2.addActionListener(new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        String symbol = symbolName.getText();
                        String date = dateSelection13.getDateAsString();
                        String eDate = dateSelection14.getDateAsString();

                        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter start date in YYYY-MM-DD format.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        if (!eDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter end date in YYYY-MM-DD format.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        LocalDate start = LocalDate.parse(date);
                        LocalDate end = LocalDate.parse(eDate);

                        if (end.isBefore(start)) {
                          JOptionPane.showMessageDialog(null,
                              "End date must be after the start date.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        nDaysPerformance(controller, symbol, date, eDate);
                      }
                    });
                    break;
                  case "Get Crossovers":
                    inputPanel.removeAll();
                    gbc.gridx = 0;
                    gbc.gridy = 0;

                    symbolLabel = new JLabel("Symbol");
                    symbolLabel.setForeground(Color.WHITE);
                    inputPanel.add(symbolLabel, gbc);

                    gbc.gridx = 1;
                    inputPanel.add(symbolName, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    startDateLabel = new JLabel("Start Date (YYYY-MM-DD)");
                    startDateLabel.setForeground(Color.WHITE);
                    inputPanel.add(startDateLabel, gbc);

                    gbc.gridx = 1;
                    DateSelection dateSelection17 = new DateSelection();
                    inputPanel.add(dateSelection17, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    endDateLabel = new JLabel("End Date (YYYY-MM-DD)");
                    endDateLabel.setForeground(Color.WHITE);
                    inputPanel.add(endDateLabel, gbc);

                    gbc.gridx = 1;
                    DateSelection dateSelection18 = new DateSelection();
                    inputPanel.add(dateSelection18, gbc);

                    gbc.gridy++;

                    inputPanel.revalidate();
                    inputPanel.repaint();

                    JButton okButton3 = new JButton("OK");
                    gbc.gridx = 1;
                    inputPanel.add(okButton3, gbc);

                    okButton3.addActionListener(new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        String symbol = symbolName.getText();
                        String date = dateSelection17.getDateAsString();
                        String eDate = dateSelection18.getDateAsString();

                        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter start date in YYYY-MM-DD format.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        if (!eDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter end date in YYYY-MM-DD format.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        LocalDate start = LocalDate.parse(date);
                        LocalDate end = LocalDate.parse(eDate);

                        if (end.isBefore(start)) {
                          JOptionPane.showMessageDialog(null,
                              "End date must be after the start date.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        getCrossOvers(controller, symbol, date, eDate);
                      }
                    });
                    break;
                  case "Get Moving Crossovers":
                    inputPanel.removeAll();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    symbolLabel = new JLabel("Symbol");
                    symbolLabel.setForeground(Color.WHITE);
                    inputPanel.add(symbolLabel, gbc);

                    gbc.gridx = 1;
                    inputPanel.add(symbolName, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    startDateLabel = new JLabel("Start Date (YYYY-MM-DD)");
                    startDateLabel.setForeground(Color.WHITE);
                    inputPanel.add(startDateLabel, gbc);

                    gbc.gridx = 1;
                    DateSelection dateSelection20 = new DateSelection();
                    inputPanel.add(dateSelection20, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    endDateLabel = new JLabel("End Date (YYYY-MM-DD)");
                    endDateLabel.setForeground(Color.WHITE);
                    inputPanel.add(endDateLabel, gbc);

                    gbc.gridx = 1;
                    DateSelection dateSelection21 = new DateSelection();
                    inputPanel.add(dateSelection21, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    JLabel xDaysLabel = new JLabel("X Number of Days");
                    xDaysLabel.setForeground(Color.WHITE);
                    inputPanel.add(xDaysLabel, gbc);

                    gbc.gridx = 1;
                    JTextField xDaysField = new JTextField(10);
                    inputPanel.add(xDaysField, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    JLabel yDaysLabel = new JLabel("Y Number of Days");
                    yDaysLabel.setForeground(Color.WHITE);
                    inputPanel.add(yDaysLabel, gbc);

                    gbc.gridx = 1;
                    JTextField yDaysField = new JTextField(10);
                    inputPanel.add(yDaysField, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    JButton okButton4 = new JButton("OK");
                    inputPanel.add(okButton4, gbc);

                    inputPanel.revalidate();
                    inputPanel.repaint();

                    okButton4.addActionListener(new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        String symbol = symbolName.getText();
                        String date = dateSelection20.getDateAsString();
                        String eDate = dateSelection21.getDateAsString();

                        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter start date in YYYY-MM-DD format.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        if (!eDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter end date in YYYY-MM-DD format.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }
                        int xDays;
                        int yDays;
                        try {
                          xDays = Integer.parseInt(xDaysField.getText());
                          yDays = Integer.parseInt(yDaysField.getText());
                          if (xDays <= 0 || yDays <= 0) {
                            JOptionPane.showMessageDialog(null,
                                "Please enter positive integers for X and Y days.",
                                    "Error",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                          }
                        } catch (NumberFormatException ex) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter valid integers for X and Y days.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        LocalDate start = LocalDate.parse(date);
                        LocalDate end = LocalDate.parse(eDate);

                        if (end.isBefore(start)) {
                          JOptionPane.showMessageDialog(null,
                              "End date must be after the start date.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        if (xDays > yDays) {
                          JOptionPane.showMessageDialog(null,
                              "X days cannot be greater than Y days.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        mcrossOvers(controller, symbol, date, eDate, xDays, yDays);
                      }
                    });
                    break;

                  case "Stock Chart":
                    inputPanel.removeAll();

                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.gridwidth = 1;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    gbc.insets = new Insets(5, 5, 5, 5);

                    symbolLabel = new JLabel("Symbol:");
                    symbolLabel.setForeground(Color.WHITE);
                    inputPanel.add(symbolLabel, gbc);

                    gbc.gridx = 1;
                    gbc.gridwidth = 2;
                    JTextField symbolField = new JTextField(20);
                    inputPanel.add(symbolField, gbc);

                    gbc.gridy++;
                    gbc.gridx = 0;
                    gbc.gridwidth = 1;
                    startDateLabel = new JLabel("Start Date (YYYY-MM-DD):");
                    startDateLabel.setForeground(Color.WHITE);
                    inputPanel.add(startDateLabel, gbc);

                    gbc.gridx = 1;
                    DateSelection dateSelection22 = new DateSelection();
                    inputPanel.add(dateSelection22, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    gbc.gridwidth = 1;
                    endDateLabel = new JLabel("End Date (YYYY-MM-DD):");
                    endDateLabel.setForeground(Color.WHITE);
                    inputPanel.add(endDateLabel, gbc);

                    gbc.gridx = 1;
                    DateSelection dateSelection23 = new DateSelection();
                    inputPanel.add(dateSelection23, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    gbc.gridwidth = 1;
                    JLabel scaleLabel = new JLabel("Scale:");
                    scaleLabel.setForeground(Color.WHITE);
                    inputPanel.add(scaleLabel, gbc);

                    gbc.gridx = 1;
                    gbc.gridwidth = 2;
                    String[] scaleOptions = {"1", "10", "100"};
                    JComboBox<String> scaleComboBox = new JComboBox<>(scaleOptions);
                    inputPanel.add(scaleComboBox, gbc);

                    gbc.gridy++;
                    gbc.gridx = 0;
                    gbc.gridwidth = 3;
                    gbc.fill = GridBagConstraints.NONE;
                    gbc.anchor = GridBagConstraints.CENTER;
                    JButton okButton5 = new JButton("OK");
                    inputPanel.add(okButton5, gbc);

                    inputPanel.revalidate();
                    inputPanel.repaint();

                    okButton5.addActionListener(new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        String symbol = symbolField.getText();
                        String date = dateSelection22.getDateAsString();
                        String eDate = dateSelection23.getDateAsString();
                        int scale = Integer.parseInt((String) scaleComboBox.getSelectedItem());

                        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter start date in YYYY-MM-DD format.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        if (!eDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter end date in YYYY-MM-DD format.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        try {
                          if (scale <= 0) {
                            throw new IllegalArgumentException("Scale must be greater than 0.");
                          }
                        } catch (IllegalArgumentException ex) {
                          JOptionPane.showMessageDialog(null,
                              ex.getMessage(), "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        LocalDate start = LocalDate.parse(date);
                        LocalDate end = LocalDate.parse(eDate);

                        if (end.isBefore(start)) {
                          JOptionPane.showMessageDialog(null,
                              "End date must be after the start date.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        stockChart((GUIController) controller, symbol, date, eDate, scale);
                        symbolField.setText("");
                      }
                    });

                    inputPanel.revalidate();
                    inputPanel.repaint();
                    break;

                  case "Portfolio Chart":
                    portfolioNames = controller.getFlexiblePortfolioNames();
                    inputPanel.removeAll();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    JLabel portfolioLabel = new JLabel("Name of Portfolio");
                    portfolioLabel.setForeground(Color.WHITE);
                    inputPanel.add(portfolioLabel, gbc);

                    JComboBox<String> portfolioDropDown = new JComboBox<>(portfolioNames);
                    portfolioDropDown.addActionListener(new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        selectedPortfolio = (String) portfolioDropDown.getSelectedItem();
                      }
                    });
                    gbc.gridx = 1;
                    inputPanel.add(portfolioDropDown, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    inputPanel.add(new JLabel("Start Date (YYYY-MM-DD)"), gbc);
                    gbc.gridx = 1;
                    DateSelection dateSelection24 = new DateSelection();
                    inputPanel.add(dateSelection24, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    inputPanel.add(new JLabel("End Date (YYYY-MM-DD)"), gbc);
                    gbc.gridx = 1;
                    DateSelection dateSelection25 = new DateSelection();
                    inputPanel.add(dateSelection25, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    scaleLabel = new JLabel("Scale:");
                    scaleLabel.setForeground(Color.WHITE);
                    inputPanel.add(scaleLabel, gbc);

                    gbc.gridx = 1;
                    gbc.gridwidth = 2;
                    String[] scaleOptions1 = {"1", "10", "100"};
                    JComboBox<String> scaleComboBox1 = new JComboBox<>(scaleOptions1);
                    inputPanel.add(scaleComboBox1, gbc);

                    gbc.gridy++;
                    gbc.gridx = 0;
                    gbc.gridwidth = 3;
                    gbc.fill = GridBagConstraints.NONE;
                    gbc.anchor = GridBagConstraints.CENTER;
                    JButton okButton6 = new JButton("OK");
                    inputPanel.add(okButton6, gbc);

                    inputPanel.revalidate();
                    inputPanel.repaint();

                    okButton6.addActionListener(new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        String date = dateSelection24.getDateAsString();
                        String eDate = dateSelection25.getDateAsString();
                        int scale = Integer.parseInt((String) scaleComboBox1.getSelectedItem());
                        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter start date in YYYY-MM-DD format.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        if (!eDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                          JOptionPane.showMessageDialog(null,
                              "Please enter end date in YYYY-MM-DD format.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        try {
                          if (scale <= 0) {
                            throw new IllegalArgumentException("Scale must be greater than 0.");
                          }
                        } catch (IllegalArgumentException ex) {
                          JOptionPane.showMessageDialog(null,
                              ex.getMessage(), "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        LocalDate start = LocalDate.parse(date);
                        LocalDate end = LocalDate.parse(eDate);

                        if (end.isBefore(start)) {
                          JOptionPane.showMessageDialog(null,
                              "End date must be after the start date.", "Error",
                              JOptionPane.ERROR_MESSAGE);
                          return;
                        }

                        portfolioChart((GUIController) controller, selectedPortfolio,
                            date, eDate, scale);
                      }
                    });

                    inputPanel.revalidate();
                    inputPanel.repaint();
                    break;

                  default:
                    break;
                }
              }
            }
          });
          inputPanel.revalidate();
          inputPanel.repaint();
        }
      }
    });

    setJMenuBar(menuBar);
    menuBar.add(fileMenu);
    fileMenu.add(saveMenuItem);
    fileMenu.add(loadMenuItem);
    fileMenu.add(exitMenuItem);
    panel1.add(createPortfolioButton);
    panel1.add(buyStockButton);
    panel1.add(sellStockButton);
    panel1.add(costButton);
    panel1.add(valueButton);
    panel1.add(dollarCostAverage);
    panel1.add(features);
    panel1.add(loadDollarCostButton);
    panel2.add(inputPanel);
    panel2.add(outputPanel);

    this.add(panel1, BorderLayout.WEST);
    this.add(panel2, BorderLayout.CENTER);

    this.setVisible(true);
  }

  /**
   * This is the method to update the output panel.
   *
   * @param message the message to be displayed.
   */
  private void updateOutputPanel(String message) {
    if (outputPanel != null) {
      outputPanel.removeAll();
      String[] lines = message.split("\n");
      for (String line : lines) {
        JLabel label = new JLabel(line);
        Font font = new Font("Arial", Font.PLAIN, 20);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        outputPanel.add(label);
      }
      outputPanel.setLayout(new GridLayout(lines.length, 1));
      outputPanel.revalidate();
      outputPanel.repaint();
    } else {
      System.err.println("Output panel is not initialized properly.");
    }
  }

  /**
   * This is the method to update the output panel.
   *
   * @param messages the message to be displayed.
   */
  private void updateListOutputPanel(List<String> messages) {
    if (outputPanel != null) {
      outputPanel.removeAll();

      JTextArea textArea = new JTextArea();
      textArea.setEditable(false);
      textArea.setForeground(Color.WHITE);
      textArea.setBackground(Color.DARK_GRAY);
      Font font = new Font("Arial", Font.PLAIN, 16);
      textArea.setFont(font);

      for (String message : messages) {
        if (message != null) {
          textArea.append(message + "\n");
        }
      }

      JScrollPane scrollPane = new JScrollPane(textArea);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

      outputPanel.setLayout(new BorderLayout());
      outputPanel.add(scrollPane, BorderLayout.CENTER);
      outputPanel.revalidate();
      outputPanel.repaint();
    } else {
      System.err.println("Output panel is not initialized properly.");
    }
  }

  /**
   * Helper method to display the output.
   *
   * @param messages the messages that are to be displayed
   */
  public void displayOutputList(List<String> messages) {
    SwingUtilities.invokeLater(() -> updateListOutputPanel(messages));
  }

  /**
   * Helper method to display the output.
   *
   * @param output the messages that are to be displayed.
   */
  public void displayOutput(String output) {
    SwingUtilities.invokeLater(() -> updateOutputPanel(output));
  }

  /**
   * Helper method to create a portfolio.
   *
   * @param controller         GUI Controller.
   * @param portfolioNameValue name of the portfolio to be created.
   */
  private void createPortfolio(GUIControllerInt controller, String portfolioNameValue) {
    controller.createPortfolioGUI(portfolioNameValue);
  }

  /**
   * Helper method to save a portfolio.
   *
   * @param controller         GUI controller.
   * @param portfolioNameValue name of portfolio to be saved.
   */
  private void savePortfolio(GUIControllerInt controller, String portfolioNameValue,
      String filePath) {
    controller.savePortfolioGUI(portfolioNameValue, filePath);
  }

  /**
   * Helper method to load a portfolio.
   *
   * @param controller         GUI controller.
   * @param portfolioNameValue name of portfolio to be saved.
   */
  private void loadPortfolio(GUIControllerInt controller, String portfolioNameValue,
      String filePath) {
    controller.loadPortfolioGUI(filePath);
  }

  /**
   * Helper method to buy stocks.
   *
   * @param controller         GUI controller.
   * @param portfolioNameValue name of portfolio.
   * @param numShares          number of shares.
   * @param symbol             symbol of the stock.
   * @param startDate          date of purchase.
   */
  private void buyStocks(GUIControllerInt controller, String portfolioNameValue, double numShares,
      String symbol, String startDate) {
    controller.buyStockGUI(portfolioNameValue, symbol, numShares, startDate);
  }

  /**
   * Helper method to sell stocks.
   *
   * @param controller         GUI controller.
   * @param portfolioNameValue name of portfolio.
   * @param numShares          number of shares.
   * @param symbol             symbol of the stock.
   * @param startDate          date of sell.
   */
  private void sellStocks(GUIControllerInt controller, String portfolioNameValue, double numShares,
      String symbol, String startDate) {
    controller.sellStockGUI(portfolioNameValue, symbol, numShares, startDate);
  }

  /**
   * Helper method for calculating cost.
   *
   * @param controller         GUI controller.
   * @param portfolioNameValue name of portfolio.
   * @param startDate          date on which cost is to be calculated.
   */
  private void calculateCost(GUIControllerInt controller, String portfolioNameValue,
      String startDate) {
    controller.calculateCostGUI(portfolioNameValue, startDate);
  }

  /**
   * Helper method for calculating value.
   *
   * @param controller         GUI controller.
   * @param portfolioNameValue name of portfolio.
   * @param startDate          date on which value is to be calculated.
   */
  private void calculateValue(GUIControllerInt controller, String portfolioNameValue,
      String startDate) {
    controller.calculateValueGUI(portfolioNameValue, startDate);
  }

  /**
   * Helper to get the performance of the symbol.
   *
   * @param controller GUI controller.
   * @param symbol     symbol for which performance is needed.
   * @param startDate  date on which performance is needed.
   */
  private void performanceOnDay(GUIControllerInt controller, String symbol, String startDate) {
    controller.performanceOnDayGUI(symbol, startDate);
  }

  /**
   * Helper to get the average of the symbol.
   *
   * @param controller GUI controller.
   * @param symbol     symbol for which average is needed.
   * @param startDate  date from which average is needed.
   * @param days       days for getting the average.
   */
  private void xDaysAverage(GUIControllerInt controller, String symbol, String startDate,
      int days) {
    controller.calculateXDayMovingAverageGUI(symbol, startDate, days);
  }

  /**
   * Helper to get the performance of the symbol.
   *
   * @param controller GUI controller.
   * @param symbol     symbol for which performance is needed.
   * @param startDate  date from which performance is needed.
   * @param endDate    date to which performance is needed.
   */
  private void nDaysPerformance(GUIControllerInt controller, String symbol, String startDate,
      String endDate) {
    controller.performanceOverNDaysGUI(symbol, startDate, endDate);
  }

  /**
   * Helper to get the crossovers of the symbol.
   *
   * @param controller GUI controller.
   * @param symbol     symbol for which crossovers are needed.
   * @param startDate  date from which crossovers are needed.
   * @param endDate    date to which crossovers is needed.
   */
  private void getCrossOvers(GUIControllerInt controller, String symbol, String startDate,
      String endDate) {
    controller.detectCrossoversGUI(symbol, startDate, endDate);
  }

  /**
   * Helper to get the crossovers of the symbol.
   *
   * @param controller GUI controller.
   * @param symbol     symbol for which crossovers are needed.
   * @param startDate  date from which crossovers are needed.
   * @param endDate    date to which crossovers is needed.
   * @param xDays      days for average.
   * @param yDays      days for average.
   */
  private void mcrossOvers(GUIControllerInt controller, String symbol,
      String startDate, String endDate, int xDays, int yDays) {
    controller.movingCrossOvers(symbol, startDate, endDate, xDays, yDays);
  }

  /**
   * Helper to get the dollar cost of the symbol.
   *
   * @param controller GUI controller.
   * @param portfolio  name of portfolio.
   * @param amount     amount to be invested.
   * @param startDate  date to start investment.
   * @param endDate    date to end investment.
   * @param frequency  of investing.
   * @param symbols    symbols to invest.
   * @param weights    weights to invest.
   */
  private void dollarCost(GUIControllerInt controller, String portfolio, double amount,
      String startDate, String endDate, int frequency, List<String> symbols, List<Double> weights)
      throws IOException {
    System.out.println(endDate);
    controller.dollarCostAverageGUI(portfolio, amount, startDate, endDate, frequency, weights,
        symbols);
  }

  /**
   * Helper method used to load the dollar cost files.
   *
   * @param controller    GUI controller.
   * @param portfolioName name of Portfolio.
   * @throws IOException handles exception if any.
   */
  private void loadDollarCostCSV(GUIControllerInt controller, String portfolioName)
      throws IOException {
    System.out.println(portfolioName);
    controller.loadDollarFromCSV(portfolioName);
  }

  /**
   * Helper method used to display bar chart of a stock.
   *
   * @param controller GUI controller.
   * @param symbol     of the stock
   * @param startDate  of the chart
   * @param endDate    of the chart
   * @param scale      unit of the bar chart
   */
  private void stockChart(GUIController controller, String symbol, String startDate, String endDate,
      int scale) {
    controller.stockChartGUI(symbol, startDate, endDate, scale);
  }

  /**
   * Helper method used to display bar chart of a portfolio.
   *
   * @param controller GUI controller.
   * @param portfolio  name of the portfolio
   * @param startDate  of the chart
   * @param endDate    of the chart
   * @param scale      unit of the bar chart
   */
  private void portfolioChart(GUIController controller, String portfolio, String startDate,
      String endDate, int scale) {
    controller.portfolioChartGUI(portfolio, startDate, endDate, scale);
  }
}