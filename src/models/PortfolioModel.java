package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * The PortfolioModel class represents a portfolio containing stocks.
 */
public class PortfolioModel implements PortfolioInt {

  private String name;
  private boolean isComplete;
  private List<Stock> stocks;


  /**
   * Constructs a Models.PortfolioModel object with the specified name.
   *
   * @param name The name of the portfolio.
   */
  public PortfolioModel(String name) {
    this.stocks = new ArrayList<>();
    this.name = name;
    this.isComplete = false;
  }

  /**
   * Returns price of stock from CSV.
   *
   * @param csvFilePath file path of CSV
   * @return price of the Models.Stock.
   * @throws IOException when io error occurs.
   */
  private static double readPriceFromCSV(String csvFilePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
      reader.readLine();

      double price = 0.0;
      String line = reader.readLine();
      String[] values = line.split(",");
      price = Double.parseDouble(values[4]);

      return price;
    }
  }

  /**
   * previous day method is used to return the price of last working date.
   *
   * @param date  date for which we require  the price.
   * @param lines lines of historical data stored as csv
   * @return price of the last working day.
   */

  protected static double previousDay(String date, List<String> lines) {
    LocalDate previousDay = LocalDate.parse(date).minusDays(1);
    while (!previousDay.isBefore(LocalDate.parse("1950-10-15"))) {
      String previousDateString = previousDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      for (int i = 0; i < lines.size(); i++) {
        String storedLine = lines.get(i);
        String[] value = storedLine.split(",");
        String currentDate = value[0].trim();
        if (currentDate.equals(previousDateString)) {
          return Double.parseDouble(value[4].trim());
        }
      }
      previousDay = previousDay.minusDays(1);
    }
    return 0.0;
  }

  /**
   * Retrieves the current price of a stock.
   *
   * @param stock The stock to get price for.
   * @return The current price of the stock.
   */
  public double getCurrentPrice(Stock stock) {
    String symbol = stock.getSymbol();
    String csvFilePath = "Historical" + File.separator;
    String filepath = csvFilePath + symbol + ".csv";

    try {
      double price = readPriceFromCSV(filepath);
      return price;
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
      return 0.0;
    }
  }

  /**
   * Method used to add Models.Stock in a portfolio.
   *
   * @param stock The stock to add.
   */
  @Override
  public void addStock(Stock stock) {
    if (!isComplete) {
      stocks.add(stock);
    }
  }

  /**
   * Method used to add manual stock price.
   *
   * @param stock The stock to add.
   * @return returns true if the stock is added.
   */

  public boolean manualAddStock(Stock stock) {
    if (!isComplete) {
      String symbol = stock.getSymbol();
      StockInt newStock = new Stock(symbol, stock.getShares(), stock.getPurchasePrice(),
              stock.getPurchaseDate());
      stocks.add((Stock) newStock);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method for removing stocks from portfolio.
   *
   * @param stock The stock to remove.
   */
  @Override
  public void removeStock(Stock stock) {
    if (!isComplete) {
      stocks.remove(stock);
    }
  }

  /**
   * Method to get the stocks of the portfolio.
   *
   * @return the List of Stocks of Portfolio.
   */
  public List<Stock> getStocks() {
    return Collections.unmodifiableList(stocks);
  }

  /**
   * Method used to mark the Portfolio as Complete.
   */
  @Override
  public void markComplete() {
    this.isComplete = true;
  }

  /**
   * Used to get the name of portfolio.
   *
   * @return the name of Portfolio.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Method used to give the value of portfolio on a certain date.
   *
   * @param date The date for which to calculate the total value.
   * @return total value of portfolio on the date
   */
  @Override
  public double calculateTotalValue(String date) {
    double totalValue = 0.0;
    List<Stock> stocks = getStocks();
    for (Stock stock : stocks) {
      String purchaseDate = stock.getPurchaseDate();
      if (LocalDate.parse(date).isBefore(LocalDate.parse(purchaseDate))) {
        totalValue += 0;
      } else {
        double stockPrice = getStockPriceOnDate(stock.getSymbol(), date);
        if (stockPrice > 0) {
          totalValue += stockPrice * stock.getShares();
        }
      }
    }

    return totalValue;
  }

  /**
   * Method used to give a price of stock on a date.
   *
   * @param symbol of the stock.
   * @param date   on which price is required.
   * @return price on the date.
   */

  public double getStockPriceOnDate(String symbol, String date) {
    String folderPath = "Historical" + File.separator;
    String filePath = folderPath + symbol + ".csv";

    try {
      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      List<String> lines = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
        String[] values = line.split(",");
        String currentDate = values[0].trim();
        if (currentDate.equals(date)) {
          return Double.parseDouble(values[4].trim());
        }
      }
      return previousDay(date, lines);
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
    }

    return 0.0;
  }

  /**
   * Method used to get stock by the Symbol.
   *
   * @param symbol of the required Models.Stock.
   * @return the required stock.
   */
  public StockInt getStockBySymbol(String symbol) {
    for (int i = 0; i < stocks.size(); i++) {
      StockInt stock = stocks.get(i);
      if (stock.getSymbol().equals(symbol)) {
        return stock;
      }
    }
    return null;
  }

  /**
   * Method used to load a CSV file.
   *
   * @param filePath takes the file path passed from controller.
   */
  @Override
  public void loadFromCSV(String filePath) {
    stocks.clear();
    boolean isFirstLine = true;

    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] data = line.split(",");

        if (data.length == 4) {

          String purchaseDate = (data[0].trim());
          String symbol = data[1].trim();
          double shares = Double.parseDouble(data[2].trim());
          double purchasePrice = Double.parseDouble(data[3].trim());
          Stock stock = new Stock(purchaseDate, symbol, shares, purchasePrice);
          stocks.add(stock);

        } else {
          System.out.println(line + " Invalid file. ");
        }
      }

    } catch (FileNotFoundException e) {
      System.out.println("Error File not found - " + e.getMessage());
    }
  }

  /**
   * Method to know the value of isComplete.
   *
   * @return value of isComplete.
   */
  public boolean isComplete() {
    return isComplete;
  }

  private boolean isBefore(String date1, String date2) {
    LocalDate localDate1 = LocalDate.parse(date1);
    LocalDate localDate2 = LocalDate.parse(date2);
    return localDate1.isBefore(localDate2);
  }


}