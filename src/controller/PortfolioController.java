package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import models.Features;
import models.PortfolioModel2;
import models.PortfolioModelSec;
import models.Stock;
import models.StockInt;
import view.PortfolioView;
import view.ViewInterface;


/**
 * Controller.PortfolioController class implements the Controller. ControllerInterface and provides
 * methods to manage portfolios and stocks.
 */
public class PortfolioController extends AbstractContoller implements ControllerInterface {


  private ViewInterface portfolioView;
  private Scanner scanner;
  private Features features;
  private PortfolioModelSec portfolio;
  private Stock stock;


  /**
   * Constructor for PortfolioController class.
   *
   * @param portfolio The portfolio interface.
   * @param view      The portfolio view.
   * @param in        The input source.
   */
  public PortfolioController(PortfolioModelSec portfolio, PortfolioView view, Readable in) {
    super();

    this.scanner = new Scanner(in);
    this.portfolioView = view;
    this.features = new Features();
    this.portfolio = portfolio;
  }

  /**
   * Creates a new portfolio with the provided name.
   *
   * @throws IOException If an I/O error occurs.
   */
  private void createPortfolio() throws IOException {
    String portfolioName = promptForPortfolioName();
    if (portfolioName != null) {
      createPortfolio1(portfolioName);
    }
  }

  /**
   * Creates a new portfolio with the provided name.
   *
   * @throws IOException If an I/O error occurs.
   */

  private void createFlexiblePortfolio() throws IOException {
    String portfolioName = promptForPortfolioName();
    if (flexiblePortfolios.containsKey(portfolioName) || portfolios.containsKey(portfolioName)) {
      portfolioView.displayPortfolioMessage(portfolioName, "exists");
    } else {
      PortfolioModelSec portfolio = new PortfolioModel2(portfolioName);
      flexiblePortfolios.put(portfolioName, portfolio);
      portfolioView.displayPortfolioMessage(portfolioName, "created");
    }
  }

  /**
   * Creates a new portfolio with the provided name.
   *
   * @param portfolioName name of portfolio.
   * @throws IOException If an I/O error occurs.
   */
  private void createPortfolio1(String portfolioName) throws IOException {
    if (portfolioName != null) {
      if (portfolios.containsKey(portfolioName) ||
              flexiblePortfolios.containsKey(portfolioName)) {
        portfolioView.displayPortfolioMessage(portfolioName, "exists");
      } else {

        PortfolioModelSec portfolio = new PortfolioModel2(portfolioName);
        portfolios.put(portfolioName, portfolio);
        portfolioView.displayPortfolioMessage(portfolioName, "created");

      }
    }
  }

  /**
   * Adds shares of a stock to the portfolio.
   *
   * @param portfolioName The name of the portfolio.
   * @param symbol        The symbol of the stock to be added.
   * @param shares        The number of shares to add.
   */
  private void addStoc(String portfolioName,
                       String symbol, double shares) throws IOException {
    PortfolioModelSec portfolio = portfolios.get(portfolioName);
    if (portfolio != null && !portfolio.isComplete()) {
      StockInt existingStock = portfolio.getStockBySymbol(symbol);
      if (existingStock != null) {
        existingStock.setShares(existingStock.getShares() + shares);
        portfolioView.addDisplayMessage("stockAdded",
                portfolioName, symbol, shares);
      } else {
        String csvFilePath = "Historical" + File.separator +
                symbol + ".csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
          reader.readLine();
          String line = reader.readLine();
          if (line != null) {
            String[] values = line.split(",");
            if (values.length >= 5) {
              double price = Double.parseDouble(values[4]);
              Stock newStock = new Stock(symbol, shares, price);
              portfolio.addStock(newStock);
              portfolioView.addDisplayMessage("stockAdded",
                      portfolioName, symbol, shares);
            }
          }
        } catch (IOException | NumberFormatException e) {
          portfolioView.addDisplayMessage("errorAddingStock",
                  portfolioName, null, 0);
        }
      }
    } else if (portfolio != null && portfolio.isComplete()) {
      portfolioView.addDisplayMessage("portfolioComplete",
              portfolioName, null, 0);
    } else {
      portfolioView.displayPortfolioNotExist(portfolioName);
    }
  }

  /**
   * User method for add method to take the inputs from user. Takes name of portfolio, symbol,
   * number of shares.
   */

  private void addStoc() throws IOException {
    String portfolioToAddStock = promptForPortfolioName();
    String stockSymbol = getValidStockSymbolFromUser();
    double shares = getValidNumberOfSharesFromUser();
    addStoc(portfolioToAddStock, stockSymbol, shares);
  }

  /**
   * User method for remove method to take the inputs from user. Takes name of portfolio, symbol,
   * number of shares.
   */
  private void removeStoc() throws IOException {
    String portfolioToRemoveStock = promptForPortfolioName();
    PortfolioModelSec portfolio = portfolios.get(portfolioToRemoveStock);
    if (portfolio != null) {
      String stockSymbolRemove = getValidStockSymbolFromUser();
      double sharesToRemove = getValidNumberOfSharesFromUser();
      removeStoc(portfolioToRemoveStock, stockSymbolRemove, sharesToRemove);
    } else {
      portfolioView.displayPortfolioNotExist(portfolioToRemoveStock);
    }
  }


  /**
   * Removes shares of a stock to the portfolio.
   *
   * @param portfolioName  The name of the portfolio.
   * @param symbolToRemove Symbol of stock.
   * @param sharesToRemove number of shares to remove.
   */
  private void removeStoc(String portfolioName,
                          String symbolToRemove, double sharesToRemove)
          throws IOException {
    PortfolioModelSec portfolio = portfolios.get(portfolioName);
    if (portfolio != null) {
      if (portfolio.isComplete()) {
        portfolioView.removeDisplayMessage("portfolioComplete",
                portfolioName, null, 0);
        return;
      }
      Stock stockToRemove = null;
      for (Stock stock : portfolio.getStocks()) {
        if (stock.getSymbol().equals(symbolToRemove)) {
          stockToRemove = stock;
          break;
        }
      }
      if (stockToRemove != null) {
        if (stockToRemove.getShares() >= sharesToRemove) {
          stockToRemove.setShares(stockToRemove.getShares() - sharesToRemove);
          portfolioView.removeDisplayMessage("sharesRemoved",
                  portfolioName, symbolToRemove, sharesToRemove);
        } else {
          portfolioView.removeDisplayMessage("insufficientShares",
                  portfolioName, symbolToRemove, sharesToRemove);
        }
      } else {
        portfolioView.removeDisplayMessage("stockNotFound",
                portfolioName, symbolToRemove, sharesToRemove);
      }
    } else {
      portfolioView.displayPortfolioNotExist(portfolioName);
    }
  }

  /**
   * Used to create and operate inflexible portfolio.
   *
   * @throws IOException handles exception if any.
   */
  public void start() throws IOException {
    boolean exit = false;
    while (!exit) {
      portfolioView.displayMenuOptions();
      portfolioView.displayMessage("Enter your choice: ");
      try {
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
          case 1:
            createPortfolio();
            break;
          case 2:
            addStoc();
            break;
          case 3:
            removeStoc();
            break;
          case 4:
            markPortfolioAsComplete();
            break;
          case 5:
            viewPortfolio();
            break;
          case 6:
            savePortfolio();
            break;
          case 7:
            loadPortfolio();
            break;
          case 8:
            compositionInput();
            break;
          case 9:
            totalValueOnGivenDate();
            break;
          case 10:
            providePortfolioManually();
            break;
          case 11:
            priceManually();
            break;
          case 12:
            exit = true;
            portfolioView.displayMessage("Exiting...");
            break;
          default:
            portfolioView.displayMessage("Invalid choice. Please try again.");
        }
      } catch (InputMismatchException e) {
        portfolioView.displayMessage("Invalid input. Please enter a number.");
        scanner.nextLine();
      }
    }
  }

  /**
   * Used to select operate flexible portfolio.
   *
   * @throws IOException handles exception if any.
   */
  public void flexible() throws IOException {
    boolean exit = false;
    while (!exit) {
      portfolioView.displayFlexiblePortfolioOptions();
      portfolioView.displayMessage("Enter your choice: ");
      try {
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > 14) {
          portfolioView.displayMessage("Invalid choice."
                  + " Please enter a number between 1 and 11.");
          continue;
        }
        switch (choice) {
          case 1:
            createFlexiblePortfolio();
            break;
          case 2:
            purchaseStock();
            break;
          case 3:
            sellStock();
            break;
          case 4:
            costOfPortfolio();
            break;
          case 5:
            viewPortfolio();
            break;
          case 6:
            savePortfolio();
            break;
          case 7:
            loadPortfolio1();
            break;
          case 8:
            compositionInput();
            break;
          case 9:
            totalValueOnGivenDate();
            break;
          case 10:
            dollarCost();
            break;
          case 11:
            loaddollarCost();
            break;
          case 12:
            listDollarCost();
            break;
          case 13:
            exit = true;
            portfolioView.displayMessage("Exiting...");
            break;
          default:
            portfolioView.displayMessage("Invalid choice. Please try again.");
        }
      } catch (InputMismatchException | IOException e) {
        portfolioView.displayMessage("Invalid input. Please enter a number.");
        scanner.nextLine();
      }
    }
  }

  /**
   * Method to load a dollar cost portfolio file.
   *
   * @throws IOException handles exception if any.
   */
  private void loaddollarCost() throws IOException {

    portfolioView.displayMessage("provide the dollar cost file name : ");
    String name = scanner.nextLine();
    loadDollarFromCSV(name);
  }

  /**
   * listDollarCost provides a list of the dollar cost portfolio files that exist.
   *
   * @throws IOException throws an exception if there is an error.
   */

  public void listDollarCost() throws IOException {

    File directory = new File("dollar");
    File[] files = directory.listFiles();
    for (File file : files) {
      if (file.isFile()) {
        portfolioView.displayBarChart("\n" + file.getName() + "\n");
      }
    }


  }


  /**
   * Used to select which feature you want to see.
   *
   * @throws IOException handles exception if any.
   */
  public void features() throws IOException {
    boolean exit = false;
    while (!exit) {
      portfolioView.displayFeatures();
      portfolioView.displayMessage("Enter your choice: ");
      try {
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > 8) {
          portfolioView.displayMessage("Invalid choice. "
                  + "Please enter a number between 1 and 7.");
          continue;
        }
        switch (choice) {
          case 1:
            determinePerformanceOnDay();
            break;
          case 2:
            determinePerformanceOverTime();
            break;
          case 3:
            determineMovingAverage();
            break;
          case 4:
            determineCrossovers();
            break;
          case 5:
            movingCrossovers();
            break;
          case 6:
            barChart();
            break;
          case 7:
            stockChart();
            break;
          case 8:
            exit = true;
            break;
          default:
            portfolioView.displayMessage("Invalid choice. Please try again.");
        }
      } catch (InputMismatchException | IOException e) {
        portfolioView.displayMessage("Invalid input. Please enter a number.");
        scanner.nextLine();
      }
    }
  }


  /**
   * Method for Taking date input for plotting the stock chart.
   *
   * @throws IOException handles exception if any.
   */
  private void stockChart() throws IOException {

    String symbol = getValidStockSymbolFromUser();
    Scanner scanner = new Scanner(System.in);
    String date1 = takeDate("Enter the start-date in YYYY-MM-DD format.");
    String date2 = takeDate("Enter the end-date in YYYY-MM-DD format.");
    portfolioView.displayMessage("enter the scale amt: ");
    int scale = scanner.nextInt();
    String stockChart = portfolio.stockChart(symbol, date1, date2, scale);
    portfolioView.stockChart(stockChart);
  }

  /**
   * Bar chart methods for the user to input the dates and scale. The Bar chart for the portfolio is
   * plotted based on this input.
   *
   * @throws IOException handles exception if any.
   */
  private void barChart() throws IOException {
    String portfolioName = promptForPortfolioName();
    if (flexiblePortfolios.containsKey(portfolioName)) {
      String startDate = takeDate("Enter Start Date (YYYY-MM-DD): ");
      String endDate = takeDate("Enter End Date (YYYY-MM-DD): ");
      Scanner scanner = new Scanner(System.in);
      portfolioView.displayMessage("Enter the scale amt: ");
      int scale = scanner.nextInt();
      displayBarChart(portfolioName, startDate, endDate, scale);
    } else {
      portfolioView.displayMessage("Portfolio not found.");
    }
  }

  /**
   * displayBarChart method is used to display the bar chart for the portfolio.
   *
   * @param portfolioName name of the portfolio.
   * @param startDate     start date for the bar chart.
   * @param endDate       end date for the bar chart.
   * @param scale         scale for the bar chart.
   * @throws IOException handles exception if any.
   */
  private void displayBarChart(String portfolioName,
                               String startDate, String endDate, int scale) throws IOException {
    PortfolioModelSec model = flexiblePortfolios.get(portfolioName);
    String barChartOutput = model.barChart(startDate, endDate, scale);
    portfolioView.displayBarChart(barChartOutput);
  }

  /**
   * method for taking input for manual price of stock.
   */
  private void priceManually() throws IOException {
    String portfolioToAddStock = promptForPortfolioName();
    String stockSymbol = getValidStockSymbolFromUser();
    double shares = getValidNumberOfSharesFromUser();
    double price = getValidStockPriceFromUser();
    PortfolioModelSec portfolio = portfolios.get(portfolioToAddStock);
    if (portfolio != null) {
      StockInt existingStock = portfolio.getStockBySymbol(stockSymbol);
      if (existingStock != null) {
        existingStock.setShares(existingStock.getShares() + shares);
        portfolioView.addDisplayMessage("stockAdded",
                portfolioToAddStock, stockSymbol, shares);
      } else {
        Stock newStock = new Stock(stockSymbol, shares, price);
        boolean stockAdded = portfolio.manualAddStock(newStock);
        if (!stockAdded) {
          portfolioView.addDisplayMessage("portfolioComplete",
                  portfolioToAddStock, stockSymbol, shares);
        } else {
          portfolioView.addDisplayMessage("stockAdded",
                  portfolioToAddStock, stockSymbol, shares);
        }
      }
    } else {
      portfolioView.addDisplayMessage("portfolioNotFound",
              portfolioToAddStock, stockSymbol, shares);
    }
  }

  /**
   * Method is used to get date list for a ticker symbol. it specifies on which date data is
   * available.
   */
  private void provideDate() throws IOException {
    String ticker = getValidStockSymbolFromUser();
    portfolioView.displayStockDateList(ticker);
  }

  /**
   * Method is used to load a csv manually from user. in which they can perform operation of add and
   * remove, stock.
   */
  private void providePortfolioManually() throws IOException {

    portfolioView.displayMessage("provide the file path: ");
    String filepath = scanner.nextLine();
    portfolioView.displayMessage("provide the file name: ");
    String name = scanner.nextLine();
    performManually(name, filepath);

  }

  /**
   * method that calls the model methof for functionality of manual, loading and perform operations
   * on it.
   */
  private void performManually(String name, String filepath) {
    PortfolioModelSec portfolio = new PortfolioModel2(name);
    portfolio.loadFromCSV(filepath);
    portfolios.put(name, portfolio);
  }

  /**
   * Used to display the composition of portfolio.
   *
   * @param portfolioName name of portfolio.
   * @throws IOException if exception.
   */
  private void portfolioComposition(String portfolioName) throws IOException {
    PortfolioModelSec portfolio = portfolios.get(portfolioName);
    PortfolioModelSec flexible = flexiblePortfolios.get(portfolioName);
    if (portfolio != null) {
      portfolioView.composition(portfolio);
    } else if (flexible != null) {
      portfolioView.composition(flexible);
    } else {
      portfolioView.displayPortfolioNotExist(portfolioName);
    }
  }

  /**
   * Calculates and displays the total value of a portfolio on a given date.
   */
  private void totalValueOnGivenDate() throws IOException {
    String portfolioName = promptForPortfolioName();
    if (portfolios.containsKey(portfolioName) ||
            flexiblePortfolios.containsKey(portfolioName)) {
      String date = takeDate("Enter the date (yyyy-MM-dd): ");
      double totalValue = getTotalPortfolioValue(portfolioName, date);
      portfolioView.displayPortfolioValue(portfolioName, date, totalValue);
    } else {
      portfolioView.displayPortfolioNotExist(portfolioName);
    }
  }

  /**
   * Used to display of composition.
   *
   * @throws IOException if there is an exception.
   */
  private void compositionInput() throws IOException {
    String portfolioToView = promptForPortfolioName();
    PortfolioModelSec portfolio = portfolios.get(portfolioToView);
    PortfolioModelSec flexible = flexiblePortfolios.get(portfolioToView);
    if (portfolio != null) {
      portfolioComposition(portfolio.getName());
    } else if (flexible != null) {
      portfolioComposition(flexible.getName());
    } else {
      portfolioView.displayPortfolioNotExist(portfolioToView);
    }
  }

  /**
   * Gives the value of Portfolio on a date.
   *
   * @param portfolioName name of Portfolio.
   * @param date          the date you want the value of portfolio on.
   * @return value of Portfolio.
   */
  private double getTotalPortfolioValue(String portfolioName,
                                        String date) throws IOException {
    double totalValue = 0.0;
    PortfolioModelSec portfolio = portfolios.get(portfolioName);
    PortfolioModelSec flexible = flexiblePortfolios.get(portfolioName);
    if (portfolio != null) {
      totalValue = portfolio.radicalValue(date);
    } else if (flexible != null) {
      totalValue = flexible.calculateTotalValue(date);
    } else {
      portfolioView.displayPortfolioNotExist(portfolioName);
    }
    return totalValue;
  }

  /**
   * Method for calling display method in view.
   *
   * @param portfolioName name of portfolio to be displayed.
   */
  void displayPortfolioComposition(String portfolioName)
          throws IOException {
    PortfolioModelSec portfolio = portfolios.get(portfolioName);
    PortfolioModelSec flexible = flexiblePortfolios.get(portfolioName);
    if (portfolio != null) {
      portfolioView.displayPortfolioComposition(portfolio);
    } else if (flexible != null) {
      portfolioView.displayPortfolioComposition(flexible);
    } else {
      portfolioView.displayPortfolioNotExist(portfolioName);
    }
  }

  /**
   * Checks if name of Portfolio is valid.
   *
   * @param input name of the portfolio.
   * @return validated value.
   */
  public String validatePortfolioName(String input)
          throws IOException {
    if (input.isEmpty()) {
      portfolioView.displayMessage("Portfolio name cannot be empty.");
      return null;
    } else {
      if (!input.matches(".*[a-zA-Z0-9]+.*")) {
        portfolioView.displayMessage("Portfolio name must contain" +
                " at least one letter or one number.");
        return null;
      } else {
        return input.trim();
      }
    }
  }

  /**
   * Method to take portfolio name from user. Checks if input is not empty and an alphanumeric
   * value.
   *
   * @return user's input.
   */
  private String promptForPortfolioName() throws IOException {
    String portfolioName = null;
    while (portfolioName == null) {
      portfolioView.displayMessage("Enter portfolio name: ");
      if (scanner.hasNextLine()) {
        portfolioName = validatePortfolioName(scanner.nextLine());
      } else {
        portfolioView.displayMessage("Invalid input."
                + " Please enter a valid portfolio name.");
        scanner.next();
      }
    }
    return portfolioName;
  }

  /**
   * Method to take the stock symbol from user.
   *
   * @return the stock's symbol.
   */
  private String getValidStockSymbolFromUser() throws IOException {
    String stockSymbol = "";
    while (stockSymbol.isEmpty()) {
      portfolioView.displayMessage("Enter stock symbol: ");
      stockSymbol = scanner.nextLine().trim();
      if (stockSymbol.isEmpty()) {
        portfolioView.displayMessage("Stock symbol cannot be empty.");
      }
    }
    return stockSymbol;
  }

  /**
   * Method to take the stock price from user.
   *
   * @return the stock's price.
   */
  private double getValidStockPriceFromUser() throws IOException {
    double stockPrice = 0.0;
    scanner.nextLine();
    while (stockPrice == 0.0) {
      portfolioView.displayMessage("Enter the manual stock price here: ");
      String priice = scanner.nextLine().trim();
      stockPrice = Double.parseDouble(priice);
      if (stockPrice == 0.0) {
        portfolioView.displayMessage("Stock price cannot be empty.");
      }
    }
    return stockPrice;
  }


  /**
   * Method the input for number of shares from user.
   *
   * @return number of shares.
   */
  private double getValidNumberOfSharesFromUser() throws IOException {
    double shares = -1.0;
    while (shares <= 0.0) {
      try {
        portfolioView.displayMessage("Enter number of shares: ");
        shares = scanner.nextDouble();
        if (shares <= 0.0) {
          portfolioView.displayMessage("Number of shares cannot be 0 or negative. "
                  + "Please enter a non-negative value.\n");
        }
      } catch (InputMismatchException e) {
        portfolioView.displayMessage("Invalid input."
                + " Please enter an integer for shares.");
        scanner.nextLine();
      }
    }
    return shares;
  }

  /**
   * Method to take the valid number of days from user.
   *
   * @return number of days.
   */
  private int getValidNumberOfDaysFromUser() throws IOException {
    int shares = -1;
    while (shares <= 0) {
      try {
        portfolioView.displayMessage("Enter number of days: ");
        shares = scanner.nextInt();
        if (shares <= 0) {
          portfolioView.displayMessage("Number of days cannot be 0 or negative. "
                  + "Please enter a non-negative value.");
        }
      } catch (InputMismatchException e) {
        portfolioView.displayMessage("Invalid input."
                + " Please enter an integer for days.");
        scanner.nextLine();
      }
    }
    return shares;
  }

  /**
   * Method used to take user input date from user.
   *
   * @param promptMessage message to be displayed.
   * @return date from user.
   * @throws IOException handles exception if any.
   */
  public String takeDate(String promptMessage) throws IOException {
    boolean validDate = false;
    String date = null;
    while (!validDate) {
      try {
        portfolioView.displayPromptMessage(promptMessage);
        date = scanner.next();
        if (isValidDateFormat(date)) {
          validDate = true;
        } else {
          portfolioView.displayMessage("Invalid date format.");
        }
      } catch (InputMismatchException e) {
        portfolioView.displayMessage("Invalid input.");
        scanner.next();
      }
    }
    return date;
  }

  /**
   * check if date is valid.
   *
   * @param date date in String
   * @return boolean value
   */
  public boolean isValidDateFormat(String date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    try {
      dateFormat.parse(date);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  /**
   * Method used to save the portfolio as a CSV file.
   *
   * @param portfolioName name of portfolio.
   */
  public void saveCSV(String portfolioName) throws IOException {
    PortfolioModelSec portfolio = portfolios.get(portfolioName);
    PortfolioModelSec flexible = flexiblePortfolios.get(portfolioName);
    if (portfolio != null || flexible != null) {
      String fileName = portfolioName + ".csv";
      String filePath = "portfolios" +
              File.separator + fileName;
      File folder = new File("portfolios");

      try {
        if (folder.exists() || folder.mkdirs()) {
          if (portfolio != null) {
            saveAsCSV(filePath, portfolio);
            portfolioView.displayMessage("Portfolio saved successfully!");
          } else if (flexible != null) {
            saveAsCSV(filePath, flexible);
            portfolioView.displayMessage("Portfolio saved successfully!");
          }
        } else {
          portfolioView.displayMessage("Error creating 'portfolios' folder.");
        }
      } catch (IOException e) {
        portfolioView.displayMessage("Error saving portfolio: " + e.getMessage());
        e.printStackTrace();
      }
    } else {
      portfolioView.displayPortfolioNotExist(portfolioName);
    }
  }

  /**
   * Method used to save the Portfolio as a CSV file.
   *
   * @param filePath takes the file path passed from controller.
   */

  public void saveAsCSV(String filePath,
                        PortfolioModelSec portfolio) throws IOException {
    try (FileWriter writer = new FileWriter(filePath, false)) {
      List<Stock> stocks = portfolio.getStocks();
      for (Stock stock : stocks) {
        String row = String.format("%s,%s,%.2f,%.2f%n", stock.getPurchaseDate(),
                stock.getSymbol(), stock.getShares(), stock.getPurchasePrice());
        writer.write(row);
      }
    }
  }

  /**
   * Method to load a CSV file.
   *
   * @param portfolioName name of the file.
   */
  private void loadCSV(String portfolioName) throws IOException {
    String fileName = portfolioName + ".csv";
    String filePath = "portfolios" + File.separator +
            fileName;
    PortfolioModelSec portfolio = new PortfolioModel2(portfolioName);
    File csv = new File(filePath);
    if (!csv.exists()) {
      portfolioView.displayLoadCSVMessage(portfolioName, filePath, false);
      return;
    }
    portfolio.markComplete();
    portfolio.loadFromCSV(filePath);
    portfolios.put(portfolioName, portfolio);
    portfolioView.displayLoadCSVMessage(portfolioName, filePath, true);
  }


  /**
   * Method to load a CSV file.
   *
   * @param portfolioName name of the file.
   */
  private void loadCSV1(String portfolioName) throws IOException {
    String fileName = portfolioName + ".csv";
    String filePath = "portfolios" + File.separator +
            fileName;
    PortfolioModelSec portfolio = new PortfolioModel2(portfolioName);
    File csv = new File(filePath);
    if (!csv.exists()) {
      portfolioView.displayLoadCSVMessage(portfolioName, filePath, false);
      return;
    }
    portfolio.loadFromCSV(filePath);
    flexiblePortfolios.put(portfolioName, portfolio);
    portfolioView.displayLoadCSVMessage(portfolioName, filePath, true);
  }

  /**
   * User method to mark Portfolio as complete.
   */
  private void markPortfolioAsComplete() throws IOException {
    String portfolioToMarkComplete = promptForPortfolioName();
    markPortfolioAsComplete(portfolioToMarkComplete);
  }

  /**
   * Marks the portfolio as complete.
   *
   * @param portfolioName name of portfolio.
   */
  private void markPortfolioAsComplete(String portfolioName) throws IOException {
    PortfolioModelSec portfolioComplete = portfolios.get(portfolioName);
    if (portfolioComplete != null) {
      boolean isComplete = portfolioComplete.isComplete();
      if (!isComplete) {
        portfolioComplete.markComplete();
      }
      portfolioView.displayMarkPortfolioComplete(portfolioName, isComplete);
    } else {
      portfolioView.displayPortfolioNotExist(portfolioName);
    }
  }


  /**
   * checks the portfolio exist or not .
   *
   * @param portfolioName name of the portfolio.
   * @return true or false.
   */
  public boolean doesFlexPortfolioExist(String portfolioName) {
    return flexiblePortfolios.containsKey(portfolioName);
  }


  /**
   * Helper method to view the portfolio.
   */
  private void viewPortfolio() throws IOException {
    String portfolioToView = promptForPortfolioName();
    PortfolioModelSec portfolio = portfolios.get(portfolioToView);
    PortfolioModelSec flexible = flexiblePortfolios.get(portfolioToView);
    if (portfolio != null) {
      displayPortfolioComposition(portfolio.getName());
    } else if (flexible != null) {
      displayPortfolioComposition(flexible.getName());
    } else {
      portfolioView.displayPortfolioNotExist(portfolioToView);
    }
  }

  /**
   * Helper method for saving the portfolio as CSV.
   */
  private void savePortfolio() throws IOException {
    String portfolioName = promptForPortfolioName();
    saveCSV(portfolioName);
  }

  /**
   * Helper method used to load the CSV file.
   */
  private void loadPortfolio() throws IOException {
    String portfolioName = promptForPortfolioName();
    loadCSV(portfolioName);
  }


  /**
   * Displays the list of symbols.
   */
  private void listsymbol() throws IOException {
    portfolioView.displayStockList();
  }

  /**
   * Checks if a portfolio exists with the given name.
   *
   * @param portfolioName The name of the portfolio to check.
   * @return True if the portfolio exists, false otherwise.
   */
  public boolean doesPortfolioExist(String portfolioName) {
    return portfolios.containsKey(portfolioName);
  }

  /**
   * method used to load the CSV file.
   */
  private void loadPortfolio1() throws IOException {
    String portfolioName = promptForPortfolioName();
    loadCSV1(portfolioName);
  }


  /**
   * Gets the list of ticker symbols.
   *
   * @return The list of ticker symbols.
   */
  public List<String> getSymb() {
    List<String> symbolsList = new ArrayList<>();
    String filePath = "snp500.csv";

    try (Scanner scanner = new Scanner(new File(filePath))) {
      for (int i = 0; i < 500; i++) {
        String next = scanner.nextLine();
        String[] values = next.split(",");

        String symbol = values[0].trim();
        if (symbol.equals("Symbol")) {
          continue;
        } else {
          symbolsList.add(symbol);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return symbolsList;
  }


  /**
   * costOfPortfolio method is used to calculate the cost of the portfolio.
   *
   * @throws IOException throws an exception if there is an error.
   */
  private void costOfPortfolio() throws IOException {
    String portfolioName = promptForPortfolioName();
    PortfolioModelSec portfolio = flexiblePortfolios.get(portfolioName);
    if (flexiblePortfolios.containsKey(portfolioName)) {
      String date = takeDate("Enter the date (yyyy-MM-dd): ");
      double totalValue = portfolio.calculateTotalInvestment(portfolio, date);
      portfolioView.displayPortfolioValue(portfolioName, date, totalValue);
    } else {
      portfolioView.displayPortfolioNotExist(portfolioName);
    }
  }

  /**
   * determinePerformanceOnDay method is used to take the input for validating, the performance of
   * the stock on a given date.
   *
   * @throws IOException throws an exception if there is an error.
   */
  private void determinePerformanceOnDay() throws IOException {
    String symbol = getValidStockSymbolFromUser();
    String date = takeDate("Enter the date in YYYY-MM-DD format.");
    String gainOrLoss = features.determineGainOrLoss(symbol, date);
    portfolioView.displayGainOrLossInfo(gainOrLoss);
  }

  /**
   * dollarCost method is used to take the input for the dollar cost averaging.
   *
   * @throws IOException throws an exception if there is an error.
   */


  private void purchaseStock() throws IOException {
    String portfolioName = promptForPortfolioName();
    String symbol = getValidStockSymbolFromUser();
    double shares = getValidNumberOfSharesFromUser();
    PortfolioModelSec portfolio = flexiblePortfolios.get(portfolioName);
    String purchaseDate = takeDate("Enter date in YYYY-MM-DD format:");


    if (portfolio != null) {
      double price = portfolio.getStockPriceOnDate(symbol, purchaseDate);
      if (price != 0) {
        Double actualVol = portfolio.volumeShare(symbol, purchaseDate, shares);
        if (actualVol > shares) {
          Stock stock = new Stock(symbol, shares, price, purchaseDate);
          portfolio.addStock(stock);
          portfolioView.displayMessage("Stock added successfully!...");
        } else {
          portfolioView.displayMessage("Not enough shares available on this date.");
        }
      } else {
        portfolioView.displayMessage("No price available for this date.");
      }
    } else {
      portfolioView.displayMessage("Portfolio does not exist.");
    }
  }

  /**
   * Method to sell the stock of the Portfolio and handle input given by the user.
   *
   * @throws IOException throws an exception if there is an error.
   */

  private void sellStock() throws IOException {
    String portfolioName = promptForPortfolioName();
    String symbol = getValidStockSymbolFromUser();
    double shares = getValidNumberOfSharesFromUser();
    String sellDate = takeDate("Enter date in YYYY-MM-DD format.");
    PortfolioModelSec name = flexiblePortfolios.get(portfolioName);
    if (name != null) {
      boolean ifDate = false;
      LocalDate parsedSellDate = LocalDate.parse(sellDate);
      boolean stockFound = false;
      Stock stockToRemove = null;
      for (Stock stock : name.getStocks()) {
        if (stock.getSymbol().equals(symbol)) {
          stockToRemove = stock;
          stockFound = true;
          break;
        }
      }
      if (stockFound) {
        if (parsedSellDate.isAfter(LocalDate.parse(stockToRemove.getPurchaseDate()))) {
          if (stockToRemove.getShares() > shares) {
            stockToRemove.setShares(stockToRemove.getShares() - shares);
            portfolioView.displayMessage("Shares sold successfully!");
          } else if (stockToRemove.getShares() == shares) {
            Stock stock = new Stock(symbol, shares);
            name.removeStock(stock);
          } else {
            portfolioView.displayMessage(
                    "Cannot share more shares than shares present in Portfolio.");
          }
        } else {
          portfolioView.displayMessage("Cannot sell before purchase date.");
        }
      } else {
        portfolioView.displayMessage("Stock is not found in Portfolio.");
      }
    } else {
      portfolioView.displayMessage("Portfolio not found.");
    }
  }

  /**
   * determinePerformanceOverTime method is used to take the input for validating, the performance
   * of the stock over a period of time.
   *
   * @throws IOException throws an exception if there is an error.
   */

  private void determinePerformanceOverTime() throws IOException {
    String symbol = getValidStockSymbolFromUser();
    String startDate = takeDate("Enter start date in YYYY-MM-DD : ");
    String endDate = getValidEndDate(LocalDate.parse(startDate),
            "Enter valid end date in YYYY-MM-DD : ");
    String gainOrLoss = features.getPerformanceOverNDays(symbol, startDate, endDate);
    portfolioView.displayGainOrLossInfo(gainOrLoss);
  }


  /**
   * determineMovingAverage method is used to take the input for the moving average of the stock.
   *
   * @throws IOException throws an exception if there is an error.
   */

  private void determineMovingAverage() throws IOException {
    String symbol = getValidStockSymbolFromUser();
    String startDate = takeDate("Enter start date in YYYY-MM-DD : ");
    int days = getValidNumberOfDaysFromUser();
    double average = features.calculateXDayMovingAverage(symbol, startDate, days);
    if (average == 0.0) {
      portfolioView.displayMessage("Specified start date is not available\n");
    } else {
      portfolioView.displayMessage(String.valueOf(average));
    }
  }

  /**
   * movingCrossovers method is used to take the input for the moving crossovers of the stock.
   *
   * @throws IOException throws an exception if there is an error.
   */

  private void movingCrossovers() throws IOException {
    String symbol = getValidStockSymbolFromUser();
    String startDate = takeDate("Enter date in YYYY-MM-DD :");
    String endDate = getValidEndDate(LocalDate.parse(startDate),
            "Enter valid end date in YYYY-MM-DD :");
    int xDays = getValidNumberOfDaysFromUser();
    int yDays = 0;
    boolean isYGreater = false;
    while (!isYGreater) {
      yDays = getValidNumberOfDaysFromUser();
      if (xDays < yDays) {
        portfolioView.displayMessage("Enters days greater than previously entered days. \n");
        isYGreater = true;
      }
    }
    List<String> crossoverOpportunities = features.movingCrossOvers(symbol, startDate,
            endDate, xDays, yDays);
    portfolioView.displayCrossovers(crossoverOpportunities);
  }

  /**
   * getValidEndDate method is used to take the input for the end date of the stock.
   *
   * @param startDate start date of the stock.
   * @param msg       message to be displayed.
   * @return the end date of the stock.
   * @throws IOException throws an exception if there is an error.
   */

  private String getValidEndDate(LocalDate startDate, String msg) throws IOException {
    boolean validDate = false;
    String endDateStr = null;
    LocalDate endDate;
    while (!validDate) {
      try {
        portfolioView.displayPromptMessage(msg);
        endDateStr = scanner.next();
        endDate = LocalDate.parse(endDateStr);
        if (endDate.isAfter(startDate)) {
          validDate = true;
        } else {
          portfolioView.displayMessage("End date must be after the start date.\n");
        }
      } catch (DateTimeParseException e) {
        portfolioView.displayMessage("Invalid date format.");
      } catch (InputMismatchException e) {
        portfolioView.displayMessage("Invalid input.");
        scanner.next();
      }
    }
    return endDateStr;
  }

  /**
   * determineCrossovers method is used to take the input for the crossovers of the stock.
   *
   * @throws IOException throws an exception if there is an error.
   */

  private void determineCrossovers() throws IOException {
    String symbol = getValidStockSymbolFromUser();
    String startDate = takeDate("Enter start date in YYYY-MM-DD : ");
    String endDate = getValidEndDate(LocalDate.parse(startDate),
            "Enter valid end date in YYYY-MM-DD : ");
    List<String> crossoverOpportunities =
            features.detectCrossovers(symbol, startDate, endDate);
    portfolioView.displayCrossovers(crossoverOpportunities);
  }

  /**
   * It marks the start point of the program.
   *
   * @throws IOException handles exception if any.
   */
  public void choosePortfolio() throws IOException {
    boolean exit = false;
    while (!exit) {
      portfolioView.displayPortfolioTypes();
      portfolioView.displayMessage("Enter your choice: ");
      try {
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
          case 1:
            start();
            break;
          case 2:
            flexible();
            break;
          case 3:
            listsymbol();
            break;
          case 4:
            provideDate();
            break;
          case 5:
            features();
            break;
          case 6:
            makeCall();
            break;
          case 7:
            exit = true;
            portfolioView.displayMessage("Exiting...");
            break;
          default:
            portfolioView.displayMessage("Invalid choice. Please try again.");
        }
      } catch (InputMismatchException e) {
        portfolioView.displayMessage("Invalid input. Please enter a number.");
        scanner.nextLine();
      }
    }
  }

  /**
   * dollarcost method is used to take the input for the dollar cost averaging of the stock.
   *
   * @throws IOException throws an exception if there is an error.
   */
  public void dollarCost() throws IOException {
    try {
      String portfolioName = promptForPortfolioName();
      PortfolioModelSec portfolio = flexiblePortfolios.get(portfolioName);
      if (doesFlexPortfolioExist(portfolioName)) {
        List<Stock> stocks = portfolio.getStocks();
        List<Double> weights = new ArrayList<>();
        double totalWeight = 0.0;

        portfolioView.displayMessage("Enter the frequency of investment (in days):\n");
        int days = Integer.parseInt(scanner.nextLine().trim());

        for (Stock stock : stocks) {
          portfolioView.displayMessage("Stock: " + stock.getSymbol() + ", Size: "
                  + stocks.size() + "\n");
          portfolioView.displayMessage("Enter the weight for this stock:\n");
          double weight = Double.parseDouble(scanner.nextLine().trim());
          totalWeight += weight;
          weights.add(weight);
        }

        if (totalWeight != 100.0) {
          throw new IllegalArgumentException("Make sure the distribution of weights is 100.");
        }

        portfolioView.displayMessage("Enter the investment amount:\n");
        double amount = Double.parseDouble(scanner.nextLine().trim());

        String startDate = takeDate("Enter the start date (YYYY-MM-DD):\n");
        String endDate = takeDate("Enter the end date (YYYY-MM-DD):\n");
        if (futureDate(endDate)) {
          endDate = LocalDate.now().toString();
        }

        List<String> report = portfolio.dollarCost(portfolioName, amount,
                startDate, endDate, days, weights);
        saveDollarAsCSV(portfolioName, portfolio, startDate, endDate, amount, days, weights);
        for (String line : report) {
          portfolioView.displayBarChart(line + "\n");
        }

      } else {
        if (portfolio == null) {
          portfolio = new PortfolioModel2(portfolioName);
          flexiblePortfolios.put(portfolioName, portfolio);
        }
        List<String> symbols = new ArrayList<>();
        List<Double> wieghts = new ArrayList<>();
        double totalWeight = 0.0;
        String symbol;
        portfolioView.displayMessage("Enter the frequency of investment (in days):\n");
        int days = Integer.parseInt(scanner.nextLine().trim());

        do {

          portfolioView.displayMessage("Enter stock symbol or 'done' to finish:\n");
          symbol = scanner.nextLine().trim();
          if ("done".equalsIgnoreCase(symbol)) {
            if (totalWeight != 100.0) {
              throw new IllegalArgumentException("Make sure the distribution of weights is 100.");

            }
          }

          if (!"done".equalsIgnoreCase(symbol)) {
            symbols.add(symbol);
            portfolioView.displayMessage("Total weight should be 100.\n");
            portfolioView.displayMessage("Enter the weight for this stock:\n");
            double weight = Double.parseDouble(scanner.nextLine().trim());
            totalWeight += weight;
            wieghts.add(weight);
          }
        }
        while (!"done".equalsIgnoreCase(symbol));

        for (int i = 0; i < symbols.size(); i++) {
          Stock stock = new Stock(symbols.get(i), 0.0, 0.0);
          portfolio.addStock(stock);
        }

        portfolioView.displayMessage("Enter the investment amount:\n");
        double amount = Double.parseDouble(scanner.nextLine().trim());

        String startDate = takeDate("Enter the start date (YYYY-MM-DD):\n");
        String endDate = takeDate("Enter the end date (YYYY-MM-DD):\n");
        if (futureDate(endDate)) {
          endDate = LocalDate.now().toString();
        }

        List<String> report = portfolio.dollarCost(portfolioName, amount,
                startDate, endDate, days, wieghts);
        saveDollarAsCSV(portfolioName, portfolio, startDate, endDate, amount, days, wieghts);

        for (String line : report) {
          portfolioView.displayBarChart(line + "\n");
        }
      }
    } catch (IllegalArgumentException e) {
      portfolioView.displayMessage("Error: " + e.getMessage() + "\n");

      dollarCost();
    }
  }


  /**
   * Method to check if the date is in the future.
   *
   * @param endDate the end date.
   * @return true if the date is in the future, false otherwise.
   */

  private boolean futureDate(String endDate) {
    LocalDate today = LocalDate.now();
    LocalDate inputDate = LocalDate.parse(endDate);
    return inputDate.isAfter(today);
  }

  /**
   * Method to save the dollar cost averaging as a CSV file.
   *
   * @param portfolioName name of the portfolio.
   * @param portfolio     the portfolio.
   * @param startDate     the start date.
   * @param endDate       the end date.
   * @param amount        the amount.
   * @param days          the days.
   * @param weights       the weights.
   * @throws IOException throws an exception if there is an error.
   */

  public void saveDollarAsCSV(String portfolioName,
                              PortfolioModelSec portfolio,
                              String startDate,
                              String endDate, double amount, int days, List<Double> weights)
          throws IOException {
    String fileName = portfolioName + ".csv";
    String filePath = "dollar" +
            File.separator + fileName;
    try (FileWriter writer = new FileWriter(filePath, false)) {
      List<Stock> stocks = portfolio.getStocks();
      for (int i = 0; i < stocks.size(); i++) {
        Stock stock = stocks.get(i);
        double weight = weights.get(i);
        String row = String.format("%s,%s,%.2f,%.2f,%.2f,%s,%s,%d,%.2f%n",
                stock.getPurchaseDate(),
                stock.getSymbol(),
                stock.getShares(),
                stock.getPurchasePrice(),
                weight,
                startDate,
                endDate,
                days,
                amount);
        writer.write(row);
      }
    }
  }

  /**
   * Method to load the dollar cost averaging from a CSV file.
   *
   * @param portfolioName name of the portfolio.
   * @throws IOException throws an exception if there is an error.
   */

  public void loadDollarFromCSV(String portfolioName) throws IOException {
    String fileName = portfolioName + ".csv";
    String filePath = "dollar" + File.separator + fileName;
    String purchaseDate = "";
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      List<Double> weights = new ArrayList<>();
      String startDate = "";
      String endDate = "";

      int days = 0;
      double amount = 0.0;
      portfolio = new PortfolioModel2(portfolioName);
      flexiblePortfolios.put(portfolioName, portfolio);
      while ((line = reader.readLine()) != null) {
        String[] values = line.split(",");
        purchaseDate = values[0];
        String symbol = values[1];
        double shares = Double.parseDouble(values[2]);
        double purchasePrice = Double.parseDouble(values[3]);
        double weight = Double.parseDouble(values[4]);
        weights.add(weight);

        if (startDate.equals("")) {
          startDate = values[5];
          endDate = values[6];
          days = Integer.parseInt(values[7]);
          amount = Double.parseDouble(values[8]);
        }

        Stock stock = new Stock(purchaseDate, symbol, shares, purchasePrice);
        portfolio.addStock(stock);

      }
      LocalDate parsedEndDate = LocalDate.parse(endDate);
      LocalDate parsedStartDate = LocalDate.parse(startDate);
      LocalDate purchaseDate2 = LocalDate.parse(purchaseDate);
      LocalDate current = LocalDate.now();
      if (parsedEndDate.isAfter(LocalDate.now())) {
        if (ChronoUnit.DAYS.between(purchaseDate2, current) + 1 >= days) {
          endDate = LocalDate.now().toString();
          List<String> report = portfolio.dollarCost(portfolioName, amount,
                  purchaseDate2.toString(),
                  endDate, days, weights);
          portfolioView.displayMessage("Portfolio loaded successfully!...\n");
          for (String line1 : report) {
            portfolioView.displayMessage(line1 + "\n");

          }
        }
      } else {
        portfolioView.displayMessage("Portfolio is up to date. you can view it.");
      }
    }
  }


  /**
   * Method to make a call to the API to get the historical data of the stock.
   *
   * @throws IOException if the data is not available.
   */
  private void makeCall() throws IOException {
    Scanner scanner = new Scanner(System.in);
    portfolioView.displayMessage("Enter the stock symbols separated by comma:");
    String stockSymbolsInput = scanner.nextLine();
    List<String> stockSymbols = Arrays.asList(stockSymbolsInput.split(","));
    makeCallForStock(stockSymbols, "Historical");
  }

  /**
   * Method to make a call to the API to get the historical data of the stock.
   *
   * @param stockSymbols list of stock symbols.
   * @param folderPath   the folder path.
   * @throws IOException if the data is not available.
   */

  public void makeCallForStock(List<String> stockSymbols, String folderPath) throws IOException {
    if (!folderPath.endsWith(File.separator)) {
      folderPath = folderPath + File.separator;
    }

    if (folderPath == null) {
      throw new IllegalArgumentException("Proper path not provided");
    }

    int numberOfStocks = stockSymbols.size();
    for (int i = 0; i < numberOfStocks; i++) {
      String stockSymbol = stockSymbols.get(i);
      String outputFilePath = folderPath + stockSymbol + ".csv";

      try (FileWriter writer = new FileWriter(outputFilePath, false)) {

        URL url;
        StringBuilder output = new StringBuilder();
        try {
          url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
                  + "&outputsize=full" + "&symbol=" + stockSymbol + "&apikey="
                  + "WX5GQNRSWDWX1NZT" + "&datatype=csv");
        } catch (MalformedURLException e) {
          throw new RuntimeException("The AlphaVantage API doesn't work...");
        }

        try (InputStream in = url.openStream()) {
          Scanner scanner = new Scanner(in);
          while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("{") || line.isEmpty()) {
              continue;
            }
            output.append(line).append("\n");
          }
        } catch (IOException e) {
          throw new IllegalArgumentException("Data is not available for " + stockSymbol);
        }

        String fileData = output.toString();
        writer.append(fileData);
        writer.append("\n");
        portfolioView.displayMessage("Historical data of ticker stored as an individual file at: "
                + outputFilePath);
      } catch (IOException e) {
        portfolioView.displayMessage("Error while saving the file: " + e.getMessage());
      }
    }
  }

}
