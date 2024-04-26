package view;

import java.io.IOException;

import java.util.List;
import models.PortfolioInt;

/**
 * Interface defining methods for displaying portfolio information.
 */
public interface ViewInterface {

  /**
   * Used to display the composition of portfolio.
   *
   * @param portfolio portfolio of which composition is needed.
   * @throws IOException handles exception if any.
   */
  void displayPortfolioComposition(PortfolioInt portfolio) throws IOException;

  /**
   * Displays the composition of a portfolio by printing the details of each stock within the
   * portfolio,including stock symbol, shares, purchase price, current price, and profit/loss. If
   * the specified portfolio does not exist or is empty, appropriate messages are displayed.
   */
  void composition(PortfolioInt portfolio) throws IOException;

  /**
   * Method used to display the Models.Stock List.
   */
  void displayStockList() throws IOException;

  /**
   * Displays the list of dates available for a particular stock's historical data.
   *
   * @param symbol The symbol of the stock for which dates are displayed.
   */
  void displayStockDateList(String symbol) throws IOException;

  /**
   * Displays a message related to portfolio creation or existence.
   *
   * @param portfolioName The name of the portfolio.
   * @param status        The status message indicating existence or creation.
   */
  void displayPortfolioMessage(String portfolioName, String status)
      throws IOException;

  /**
   * Displays a message after adding a stock to a portfolio.
   *
   * @param message       The type of message (e.g., stockAdded, errorAddingStock).
   * @param portfolioName The name of the portfolio.
   * @param symbol        The symbol of the stock.
   * @param shares        The number of shares added.
   */
  void addDisplayMessage(String message, String portfolioName, String symbol, double shares)
      throws IOException;

 /**
   * Displays a message after removing a stock from a portfolio.
   *
   * @param message        The type of message (e.g., sharesRemoved, stockNotFound).
   * @param portfolioName  The name of the portfolio.
   * @param symbolToRemove The symbol of the stock removed.
   * @param sharesToRemove The number of shares removed.
   */
  void removeDisplayMessage(String message, String portfolioName,
      String symbolToRemove, double sharesToRemove) throws IOException;

  /**
   * Displays a message indicating the status of marking a portfolio as complete.
   *
   * @param portfolioName The name of the portfolio.
   * @param isComplete    Indicates whether the portfolio is marked as complete or not.
   */
  void displayMarkPortfolioComplete(String portfolioName,
      boolean isComplete) throws IOException;

  /**
   * Displays the total value of a portfolio on a specific date.
   *
   * @param portfolioName The name of the portfolio.
   * @param date          The date for which the total value is calculated.
   * @param totalValue    The total value of the portfolio on the specified date.
   */
  void displayPortfolioValue(String portfolioName, String date, double totalValue)
      throws IOException;

  /**
   * Displays a message when a specified portfolio does not exist.
   *
   * @param portfolioName The name of the non-existent portfolio.
   * @throws IOException if there is an exception.
   */
  void displayPortfolioNotExist(String portfolioName)
      throws IOException;

  /**
   * Displays a general message.
   *
   * @param message The message to be displayed.
   * @throws IOException if there is an exception.
   */
  void displayMessage(String message) throws IOException;

  /**
   * Displays a prompt message.
   *
   * @param message The prompt message.
   *
   */
  void displayPromptMessage(String message) throws IOException;

  /**
   * Displays the menu options for user interaction.
   * @throws IOException if there is an exception.
   */
  void displayMenuOptions() throws IOException;

  /**
   * This is list of functions available for the features.
   *
   * @throws IOException if there is an exception.
   */
  void displayFeatures() throws IOException;

  /**
   * It is used to display the bar chart.
   *
   * @param barChartOutput String of output of the bar chart from controller.
   * @throws IOException if there is an exception.
   */
  void displayBarChart(String barChartOutput) throws IOException;

  /**
   * This function is used to display whether there is a gain or loss.
   *
   * @param gainOrLoss String representing gain or loss.
   * @throws IOException if there is an exception.
   */
  void displayGainOrLossInfo(String gainOrLoss) throws IOException;

  /**
   * This is the display options of the start of the program.
   *
   * @throws IOException if there is an exception.
   */
  void displayPortfolioTypes() throws IOException;

  /**
   * This is the display menu function for the Flexible Portfolio.
   *
   * @throws IOException if there is an exception.
   */
  void displayFlexiblePortfolioOptions() throws IOException;

  /**
   * This function is used to display the crossovers.
   *
   * @param crossoverOpportunities is a list of strings of the values given from controller.
   * @throws IOException if there is an exception.
   */
  void displayCrossovers(List<String> crossoverOpportunities) throws IOException;

  /**
   * Display method for displaying the stock chart.
   *
   * @param output Output received from the controller.
   * @throws IOException handles exception if any.
   */
  void stockChart(String output) throws IOException;

  /**
   * View method for displaying the load csv message.
   *
   * @param portfolioName name of the portfolio.
   * @param filePath      path of the file.
   * @param csvFound      whether csv found or not.
   * @throws IOException handles the io exception.
   */
  void displayLoadCSVMessage(String portfolioName, String filePath, boolean csvFound)
      throws IOException;


}
