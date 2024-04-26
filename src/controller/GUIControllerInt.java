package controller;

import java.io.IOException;
import java.util.List;

/**
 * GUI interface represents the functionality of the GUI Controller. It provides the methods that
 * the GUI Controller should implement to communicate, with the GUI View.
 */
public interface GUIControllerInt {

  /**
   * createPortfolioGUI Creates a portfolio with the given name by the user in GUI.
   *
   * @param portfolioName the name of the portfolio to be created.
   */
  void createPortfolioGUI(String portfolioName);

  /**
   * buyStockGUI buys a stock with the given symbol, shares and date given by the user in GUI.
   *
   * @param portfolioName the name of the portfolio in which the stock is to be bought.
   * @param symbol        the symbol of the stock to buy
   * @param shares        the number of shares of the stock to be bought.
   * @param date          the date on which the stock is to be bought.
   */

  void buyStockGUI(String portfolioName, String symbol, double shares, String date);


  /**
   * sellStockGUI sells a stock with the given symbol, shares and date given by the user in GUI.
   *
   * @param portfolioName the name of the portfolio from which the stock is to be sold.
   * @param symbol        the symbol of the stock to sell
   * @param shares        the number of shares of the stock to be sold.
   * @param date          the date on which the stock is to be sold.
   */

  void sellStockGUI(String portfolioName, String symbol, double shares, String date);

  /**
   * savePortfolioGUI saves the portfolio with the given name to the given file path.
   *
   * @param portfolioName the name of the portfolio to be saved.
   * @param filePath      the file path where the portfolio is to be saved.
   */

  void savePortfolioGUI(String portfolioName, String filePath);

  /**
   * loadPortfolioGUI loads the portfolio with the given name from the given file path.
   *
   * @param filePath the file path from where the portfolio is to be loaded.
   */

  void loadPortfolioGUI(String filePath);

  /**
   * calculateCostGUI calculates the cost of the portfolio with the given name on the given date.
   *
   * @param portfolioName the name of the portfolio for which the cost basis is to be calculated.
   * @param date          the date on which the cost basis is to be calculated.
   */

  void calculateCostGUI(String portfolioName, String date);

  /**
   * calculateValueGUI calculates the value of the portfolio with the given name on the given date.
   *
   * @param portfolioName the name of the portfolio for which the value is to be calculated.
   * @param date          the date on which the value is to be calculated.
   */

  void calculateValueGUI(String portfolioName, String date);


  /**
   * performanceOnDayGUI calculates the performance of the stock, with the given symbol on the given
   * date.
   *
   * @param symbol the symbol of the stock for which the performance is to be calculated.
   * @param date   the date on which the performance is to be calculated.
   */
  void performanceOnDayGUI(String symbol, String date);

  /**
   * performanceOverNDaysGUI calculates the performance of the stock, with the given symbol over the
   * given number of days.
   *
   * @param symbol    the symbol of the stock for which the performance is to be calculated.
   * @param startDate the start date of the period for which the performance is to be calculated.
   * @param endDate   the end date of the period for which the performance is to be calculated.
   */
  void performanceOverNDaysGUI(String symbol, String startDate, String endDate);

  /**
   * calculateXDayMovingAverageGUI calculates the X day moving average, of the stock with the given
   * symbol over the given number of days.
   *
   * @param symbol       the symbol of the stock for which the moving average is to be calculated.
   * @param startDate    the start date of the period for which the moving average is to be
   *                     calculated.
   * @param numberOfDays the number of days over which the moving average is to be calculated.
   */
  void calculateXDayMovingAverageGUI(String symbol,
      String startDate, int numberOfDays);

  /**
   * calculateExponentialMovingAverageGUI calculates the exponential moving average of the stock,
   * with the given symbol over the given number of days.
   *
   * @param symbol    the symbol of the stock for which the moving average is to be calculated.
   * @param startDate the start date of the period for which the moving average is to be
   *                  calculated.
   * @param endDate   the end date of the period for which the moving average is to be calculated.
   */
  void detectCrossoversGUI(String symbol, String startDate, String endDate);


  /**
   * calculateExponentialMovingAverageGUI calculates the exponential moving, average of the stock
   * with the given symbol over the given number of days.
   *
   * @param symbol    the symbol of the stock for which the moving average is to be calculated.
   * @param startDate the start date of the period for which the moving average is to be
   *                  calculated.
   * @param endDate   the end date of the period for which the moving average is to be calculated.
   */

  void movingCrossOvers(String symbol, String startDate, String endDate, int xDays,
      int yDays);


  /**
   * getPortfolioNamesGUI gets the names of all the portfolios.
   *
   * @return the names of all the portfolios.
   */
  String[] getFlexiblePortfolioNames();

  /**
   * getStocksInPortfolio gets the stocks in the portfolio with the given name.
   *
   * @param portfolioName the name of the portfolio for which the stocks are to be fetched.
   */
  void stockInPortfolio(String portfolioName, List<String> stock);

  /**
   * dollarCostAverageGUI does the dollar cost averaging for the given portfolio name, based on,
   * given parameter like th wieghts , amount and the frequency.
   *
   * @param portfolioName the name of the portfolio for which the dollar, cost averaging is to be
   *                      done.
   * @param amount        the amount to be invested in the dollar cost averaging.
   * @param startDate     the start date of the period for which the dollar cost, averaging is to be
   *                      done.
   * @param endDate       the end date of the period for which the dollar cost, averaging is to be
   *                      done.
   * @param frequency     the frequency of the dollar cost averaging.
   * @param weights       the weights of the stocks in the portfolio.
   * @param symbol        the symbols of the stocks in the portfolio.
   * @throws IOException       If an I/O error occurs while listing the files.
   */

  void dollarCostAverageGUI(String portfolioName, double amount, String startDate,
      String endDate, int frequency, List<Double> weights,
      List<String> symbol) throws IOException;


  /**
   * loadDollarFromCSV loads the portfolio with the given name from the given file path.
   *
   * @param portfolioName the name of the portfolio, for which the dollar cost averaging is to be
   *                      done.
   */
  void loadDollarFromCSV(String portfolioName) throws IOException;

  /**
   * portfolioChartGUI gets the portfolio chart of the portfolio with the given name, over the given
   * period of time.
   *
   * @param portfolio the name of the portfolio for which the chart is to be displayed.
   * @param startDate the start date of the period for which the chart is to be displayed.
   * @param endDate   the end date of the period for which the chart is to be displayed.
   * @param scale     the scale of the chart.
   */

  void portfolioChartGUI(String portfolio, String startDate, String endDate, int scale);

  /**
   * stockChartGUI gets the stock chart of the stock with the given symbol, over the given period of
   * time.
   *
   * @param symbol    the symbol of the stock for which the chart is to be displayed.
   * @param startDate the start date of the period for which the chart is to be displayed.
   * @param endDate   the end date of the period for which the chart is to be displayed.
   * @param scale     the scale of the chart.
   */

  void stockChartGUI(String symbol, String startDate, String endDate, int scale);

  /**
   * Lists the files in the "dollar" directory and returns an array of their names.
   *
   * @return An array of strings containing the names of files in the "dollar" directory.
   * @throws IOException If an I/O error occurs while listing the files.
   */
  String[] listDollarCostGUI() throws IOException;
}
