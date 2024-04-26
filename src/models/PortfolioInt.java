package models;

import java.util.List;

/**
 * Interface defining methods for managing a portfolio of stocks.
 */
public interface PortfolioInt {

  /**
   * Adds a stock to the portfolio.
   *
   * @param stock The stock to add.
   */
  void addStock(Stock stock);

  /**
   * Removes a stock from the portfolio.
   *
   * @param stock The stock to remove.
   */
  void removeStock(Stock stock);

  List<Stock> getStocks();

  /**
   * Marks the portfolio as complete.
   */
  void markComplete();


  /**
   * Calculates the total value of the portfolio on the specified date.
   *
   * @param date The date for which to calculate the total value.
   * @return The total value of the portfolio on the specified date.
   */
  double calculateTotalValue(String date);


  /**
   * Loads file to program.
   *
   * @param filePath path of file.
   */
  void loadFromCSV(String filePath);

  /**
   * Used to get the name of portfolio.
   *
   * @return the name of Portfolio.
   */
  String getName();

  /**
   * Method used to give a price of stock on a date.
   *
   * @param symbol of the stock.
   * @param date   on which price is required.
   * @return price on the date.
   */
  double getStockPriceOnDate(String symbol, String date);

  /**
   * Method used to get stock by the Symbol.
   *
   * @param stockSymbol of the required Models.Stock.
   * @return the required stock.
   */
  StockInt getStockBySymbol(String stockSymbol);

  /**
   * Used to add a manual stock.
   *
   * @param newStock stock to be added.
   * @return value of the stock.
   */
  boolean manualAddStock(Stock newStock);

  /**
   * Method to know the value of isComplete.
   *
   * @return value of isComplete.
   */
  boolean isComplete();

  /**
   * Used to get the Current Price of stock.
   *
   * @param stock stock of which price is needed.
   * @return price of stock.
   */
  double getCurrentPrice(Stock stock);

}
