package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import models.Features;
import models.PortfolioModel2;
import models.PortfolioModelSec;
import models.Stock;
import view.GUIView;

/**
 * The GUIController class implements the GUIControllerInt interface and provides methods to
 * interact with the GUIView class. it provides method like create a portfolio, buy,sell, save
 * portfolio etc. GUI Controller handles the user input.
 */
public class GUIController extends AbstractContoller implements GUIControllerInt {

  private PortfolioModelSec portfolio;
  private Features features;
  private GUIView view;

  /**
   * GUIController for initializing portfolio, object, view, and the feature. It also initializes
   * the flexible portfolio. It makes the call to the superclass constructor.
   */
  public GUIController() {
    super();
    this.portfolio = new PortfolioModel2("");
    this.view = new GUIView(this);
    this.features = new Features();
  }


  @Override
  public void createPortfolioGUI(String portfolioName) {
    if (flexiblePortfolios.containsKey(portfolioName) || portfolios.containsKey(portfolioName)) {
      view.displayOutput("Portfolio " + portfolioName + " already exists");
    } else {
      PortfolioModelSec portfolio = new PortfolioModel2(portfolioName);
      flexiblePortfolios.put(portfolioName, portfolio);
      view.displayOutput("Portfolio " + portfolioName + " created");
    }
  }

  @Override
  public void buyStockGUI(String portfolioName, String symbol, double shares, String date) {
    PortfolioModelSec portfolio = flexiblePortfolios.get(portfolioName);
    if (portfolio != null) {
      double price = portfolio.getStockPriceOnDate(symbol, date);
      if (price != 0) {
        double actualVol = portfolio.volumeShare(symbol, date, shares);
        if (actualVol > shares) {
          Stock stock = new Stock(symbol, shares, price, date);
          portfolio.addStock(stock);
          view.displayOutput("Stock added successfully!...");
        } else {
          view.displayOutput("Not enough shares available on this date.");
        }
      } else {
        view.displayOutput("No price available for this date.");
      }
    } else {
      view.displayOutput("Portfolio does not exist.");
    }
  }

  @Override
  public void sellStockGUI(String portfolioName, String symbol, double shares, String date) {
    PortfolioModelSec name = flexiblePortfolios.get(portfolioName);
    if (name != null) {

      LocalDate parsedSellDate = LocalDate.parse(date);
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
            view.displayOutput("Shares sold successfully!");
          } else if (stockToRemove.getShares() == shares) {
            Stock stock = new Stock(symbol, shares);
            name.removeStock(stock);
          } else {
            view.displayOutput(
                    "Cannot share more shares than shares present in Portfolio.");
          }
        } else {
          view.displayOutput("Cannot sell before purchase date.");
        }
      } else {
        view.displayOutput("Stock is not found in Portfolio.");
      }
    } else {
      view.displayOutput("Portfolio not found.");
    }
  }

  @Override
  public void savePortfolioGUI(String portfolioName, String filePath) {
    PortfolioModelSec flexible = flexiblePortfolios.get(portfolioName);
    if (flexible != null) {
      if (!filePath.toLowerCase().endsWith(".csv")) {
        filePath += ".csv";
      }
      File folder = new File(filePath).getParentFile();

      try {
        if (folder.exists() || folder.mkdirs()) {
          if (flexible != null) {
            saveAsCSV(filePath, flexible);
            view.displayOutput("Portfolio saved successfully!");
          }
        } else {
          view.displayOutput("Error creating folder: " + folder.getAbsolutePath());
        }
      } catch (IOException e) {
        view.displayOutput("Error saving portfolio: " + e.getMessage());
        e.printStackTrace();
      }
    } else {
      view.displayOutput("Portfolio " + portfolioName + " not found.");
    }
  }


  /**
   * saveAsCSV method is used to save the portfolio as a CSV file.
   *
   * @param filePath  the path of the file where the portfolio is to be saved.
   * @param portfolio the portfolio to be saved.
   * @throws IOException if an I/O error occurs while saving the portfolio.
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

  @Override
  public void loadPortfolioGUI(String filePath) {

    String fileName = new File(filePath).getName();
    String portfolioName = fileName.substring(0, fileName.lastIndexOf('.'));
    PortfolioModelSec portfolio = new PortfolioModel2(portfolioName);
    File csv = new File(filePath);
    if (!csv.exists()) {
      JOptionPane.showMessageDialog(null,
              "File does not exist", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
    portfolio.loadFromCSV(filePath);
    System.out.println(flexiblePortfolios);
    flexiblePortfolios.put(portfolioName, portfolio);
    System.out.println(flexiblePortfolios);
    JOptionPane.showMessageDialog(null,
            "Portfolio " + portfolioName + " loaded", "Success",
            JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void calculateValueGUI(String portfolioName, String date) {
    PortfolioModelSec portfolio = flexiblePortfolios.get(portfolioName);
    if (flexiblePortfolios.containsKey(portfolioName)) {
      portfolio.calculateTotalValue(date);
      view.displayOutput("Total value for " + portfolioName + " is " +
              portfolio.calculateTotalValue(date));
    } else {
      view.displayOutput("Portfolio does not exist.");
    }
  }

  @Override
  public void calculateCostGUI(String portfolioName, String date) {
    PortfolioModelSec portfolio = flexiblePortfolios.get(portfolioName);
    if (flexiblePortfolios.containsKey(portfolioName)) {
      double totalValue = portfolio.calculateTotalInvestment(portfolio, date);
      view.displayOutput("Total value for " + portfolioName + " is " + totalValue);
    } else {
      view.displayOutput("Portfolio does not exist.");
    }
  }

  @Override
  public void performanceOnDayGUI(String symbol, String date) {
    view.displayOutput("Performance On Day" + features.determineGainOrLoss(symbol, date));
  }

  @Override
  public void performanceOverNDaysGUI(String symbol, String startDate, String endDate) {
    view.displayOutput("Performance On Day" + features.determineGainOrLoss(symbol, startDate));
  }

  @Override
  public void calculateXDayMovingAverageGUI(String symbol, String startDate, int numberOfDays) {
    double average = features.calculateXDayMovingAverage(symbol, startDate, numberOfDays);
    view.displayOutput("X Day Moving Average is " + average);
  }

  @Override
  public void detectCrossoversGUI(String symbol, String startDate, String endDate) {
    view.displayOutputList(features.detectCrossovers(symbol, startDate, endDate));
  }

  @Override
  public void movingCrossOvers(String symbol, String startDate, String endDate, int xDays,
                               int yDays) {
    view.displayOutputList(features.movingCrossOvers(symbol, startDate,
            endDate, xDays, yDays));
  }

  @Override
  public void stockInPortfolio(String portfolioName, List<String> stock) {
    PortfolioModelSec portfolio = flexiblePortfolios.get(portfolioName);
    for (String stockName : stock) {
      Stock stock1 = new Stock(stockName, 0.0, 0.0);
      portfolio.addStock(stock1);
    }
  }


  @Override
  public void dollarCostAverageGUI(String portfolioName, double amount, String startDate,
                                   String endDate, int frequency, List<Double> weights,
                                   List<String> symbols) throws IOException {
    PortfolioModelSec portfolio = flexiblePortfolios.get(portfolioName);
    System.out.println(symbols);
    for (int i = 0; i < symbols.size(); i++) {
      Stock stock = new Stock(symbols.get(i), 0.0, 0.0);
      portfolio.addStock(stock);
    }
    if (futureDate(endDate)) {
      endDate = LocalDate.now().toString();
    }
    saveDollarAsCSV(portfolioName, portfolio, startDate, endDate, amount, frequency, weights);
    System.out.println(portfolio.getStocks());
    view.displayOutputList(portfolio.dollarCost(portfolioName, amount,
            startDate, endDate, frequency, weights));
  }

  /**
   * loadDollarFromCSV method is used to load the dollar cost averaging from a CSV file.
   *
   * @param portfolioName the name of the portfolio for which the dollar cost averaging, is to be
   *                      done.
   * @throws FileNotFoundException if the file is not found.
   */

  public void loadDollarFromCSV(String portfolioName) throws FileNotFoundException {
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
          for (String line1 : report) {
            view.displayOutput(line1 + "\n");

          }
        } else {
          view.displayOutput("Portfolio is up to date. you can view it.");
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * futureDate method is used to check if the end date is a future date.
   *
   * @param endDate the end date to be checked.
   * @return true if the end date is a future date, false otherwise.
   */
  private boolean futureDate(String endDate) {
    LocalDate today = LocalDate.now();
    LocalDate inputDate = LocalDate.parse(endDate);
    return inputDate.isAfter(today);
  }

  /**
   * saveDollarAsCSV method is used to save the dollar cost averaging as a CSV file.
   *
   * @param portfolioName the name of the portfolio for which the dollar cost averaging, is to be
   *                      done.
   * @param portfolio     the portfolio for which the dollar cost averaging is to be done.
   * @param startDate     the start date of the period for which the dollar cost averaging, is to be
   *                      done.
   * @param endDate       the end date of the period for which the dollar cost averaging, is to be
   *                      done.
   * @param amount        the amount to be invested in the dollar cost averaging.
   * @param days          the number of days for which the dollar cost averaging is to be done.
   * @param weights       the weights of the stocks in the portfolio.
   * @throws IOException    if an I/O error occurs while saving the dollar cost averaging.
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

  @Override
  public String[] getFlexiblePortfolioNames() {
    Set<String> names = flexiblePortfolios.keySet();
    return names.toArray(new String[0]);
  }

  /**
   * portfolioChartGUI method is used to display the chart for a portfolio. It displays the chart
   * for the portfolio with the given name for the given period. The chart is displayed with the
   * given scale.
   *
   * @param portfolio the name of the portfolio for which the chart is to be displayed.
   * @param startDate the start date of the period for which the chart is to be displayed.
   * @param endDate   the end date of the period for which the chart is to be displayed.
   * @param scale     the scale of the chart.
   */
  public void portfolioChartGUI(String portfolio, String startDate, String endDate, int scale) {
    List<String> chartData = new ArrayList<>();
    if (flexiblePortfolios.containsKey(portfolio)) {
      PortfolioModelSec portfolioM = flexiblePortfolios.get(portfolio);
      chartData.add(portfolioM.barChart(startDate, endDate, scale));
    } else {
      chartData.add("Portfolio does not exist.");
    }
    view.displayOutputList(chartData);
  }

  /**
   * stockChartGUI method is used to display the chart for a stock. It displays the chart for the
   * stock with the given symbol for the given period. The chart is displayed with the given scale.
   *
   * @param symbol    the symbol of the stock for which the chart is to be displayed.
   * @param startDate the start date of the period for which the chart is to be displayed.
   * @param endDate   the end date of the period for which the chart is to be displayed.
   * @param scale     the scale of the chart.
   */

  public void stockChartGUI(String symbol, String startDate, String endDate, int scale) {
    List<String> chartData = new ArrayList<>();
    chartData.add(portfolio.stockChart(symbol, startDate, endDate, scale));
    view.displayOutputList(chartData);
  }

  /**
   * Lists the files in the "dollar" directory and returns an array of their names.
   *
   * @return An array of strings containing the names of files in the "dollar" directory.
   * @throws IOException If an I/O error occurs while listing the files.
   */
  public String[] listDollarCostGUI() throws IOException {
    File directory = new File("dollar");
    File[] files = directory.listFiles();
    List<String> fileNames = new ArrayList<>();
    for (File file : files) {
      if (file.isFile()) {
        fileNames.add(file.getName());
      }
    }
    return fileNames.toArray(new String[0]);
  }

}
