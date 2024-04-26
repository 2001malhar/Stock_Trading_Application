package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import models.PortfolioInt;
import models.Stock;

/**
 * portfolio view for displaying the content to user.
 */
public class PortfolioView implements ViewInterface {

  private final Appendable out;

  /**
   * Constructor of the view class.
   *
   * @param out Appendable type of the
   */
  public PortfolioView(Appendable out) {
    this.out = out;
  }

  /**
   * Used to display the composition of portfolio.
   *
   * @param portfolio portfolio of which composition is needed.
   * @throws IOException handles exception if any.
   */
  @Override
  public void displayPortfolioComposition(PortfolioInt portfolio) throws IOException {
    out.append("Portfolio Composition:");
    out.append("Portfolio: ").append(portfolio.getName());

    if (portfolio.getStocks().isEmpty()) {
      out.append("No stocks found in the portfolio.");
    } else {
      out.append("Stocks:");
      for (Stock stock : portfolio.getStocks()) {
        out.append("- Symbol: " + stock.getSymbol());
        out.append("  Shares: " + stock.getShares());
        out.append("  Purchase Price: " + stock.getPurchasePrice());
        out.append("  Purchase Date: " + stock.getPurchaseDate());
      }
    }
  }


  /**
   * Displays the composition of a portfolio by printing the details of each stock within the
   * portfolio,including stock symbol, shares, purchase price, current price, and profit/loss. If
   * the specified portfolio does not exist or is empty, appropriate messages are displayed.
   */
  @Override
  public void composition(PortfolioInt portfolio) throws IOException {

    if (portfolio != null) {

      if (portfolio.getStocks().isEmpty()) {
        out.append("Portfolio '" + portfolio + "' is empty.");
      } else {
        out.append("Composition of Portfolio '" + portfolio + "':");

        for (Stock stock : portfolio.getStocks()) {
          out.append("Stock Symbol: " + stock.getSymbol());
          out.append("Shares: " + stock.getShares());
          out.append("Purchase Price: " + stock.getPurchasePrice());

          double currentPrice = portfolio.getCurrentPrice(stock);
          out.append("Current Price: " + currentPrice);

          double profitLoss = (currentPrice - stock.getPurchasePrice()) * stock.getShares();
          out.append("Profit/Loss: " + profitLoss);
        }
      }
    }
  }

  /**
   * Method used to display the Models.Stock List.
   */
  @Override
  public void displayStockList() throws IOException {
    String filePath = "snp500.csv";
    try (Scanner scanner = new Scanner(new File(filePath))) {

      for (int i = 0; i < 500; i++) {
        String next = scanner.nextLine();
        String[] values = next.split(",");

        if (values.length > 2) {
          String symbol = values[0].trim();
          String stockName = values[1].trim();
          out.append(" Name: " + stockName + " , ---> SYMBOL: " + symbol + "\n");

        }
      }
    } catch (FileNotFoundException e) {
      out.append("Error: " + e.getMessage());
    }
  }

  /**
   * it is used to show the list of date of a ticker symbol.
   *
   * @param symbol The symbol of the stock for which dates are displayed.
   * @throws IOException handles the io exception.
   */
  @Override
  public void displayStockDateList(String symbol) throws IOException {
    String filePath = "Historical" + File.separator + symbol +
            ".csv";
    String line;
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      while ((line = reader.readLine()) != null) {
        String[] values = line.split(",");
        out.append(values[0].trim() + "\n");

      }
    } catch (IOException | NumberFormatException e) {
      out.append("Error When reading CSV file for symbol '" +
              symbol + "': " + e.getMessage());
    }

  }

  /**
   * It is used to display portfolio messsage based on the status.
   *
   * @param portfolioName The name of the portfolio.
   * @param status        The status message indicating existence or creation.
   * @throws IOException handles the IOhandles.
   */
  @Override
  public void displayPortfolioMessage(String portfolioName, String status) throws IOException {
    switch (status) {
      case "exists":
        out.append("Portfolio '" + portfolioName + "' already exists.");
        break;
      case "created":
        out.append("Portfolio '" + portfolioName + "' created successfully!");
        break;
      default:
        break;
    }
  }

  /**
   * it is used to show the status of stock whether it is added or not.
   *
   * @param message       The type of message (e.g., stockAdded, errorAddingStock).
   * @param portfolioName The name of the portfolio.
   * @param symbol        The symbol of the stock.
   * @param shares        The number of shares added.
   * @throws IOException handles the io error.
   */
  @Override
  public void addDisplayMessage(String message, String portfolioName,
                                String symbol, double shares) throws IOException {
    if (message.equals("stockAdded")) {
      out.append("Added " + shares + " shares of stock '" + symbol +
              "' to portfolio '" + portfolioName + "'.");
    } else if (message.equals("errorAddingStock")) {
      out.append("Error adding stock to portfolio '" + portfolioName + "'.");
    } else if (message.equals("portfolioComplete")) {
      out.append("Portfolio '" + portfolioName + "' is completed. Cannot add stock.");
    }
  }

  /**
   * View method for showing the message for the remove option.
   *
   * @param message        The type of message (e.g., sharesRemoved, stockNotFound).
   * @param portfolioName  The name of the portfolio.
   * @param symbolToRemove The symbol of the stock removed.
   * @param sharesToRemove The number of shares removed.
   * @throws IOException handles io option.
   */
  @Override
  public void removeDisplayMessage(String message, String portfolioName,
                                   String symbolToRemove, double sharesToRemove) throws
          IOException {
    if (message.equals("portfolioComplete")) {
      out.append("Cannot remove stock from a completed portfolio.");
    } else if (message.equals("sharesRemoved")) {
      out.append(sharesToRemove + " shares of stock '" + symbolToRemove +
              "' removed from portfolio '" + portfolioName + "'.");
    } else if (message.equals("insufficientShares")) {
      out.append("Insufficient shares of stock '" + symbolToRemove +
              "' in portfolio '" + portfolioName + "'.");
    } else if (message.equals("stockNotFound")) {
      out.append("Stock '" + symbolToRemove + "' not found in portfolio '" + portfolioName + "'.");
    }
  }

  /**
   * View method for the displaying the portfolio to be complete.
   *
   * @param portfolioName The name of the portfolio.
   * @param isComplete    Indicates whether the portfolio is marked as complete or not.
   * @throws IOException handles the io exception.
   */
  @Override
  public void displayMarkPortfolioComplete(String portfolioName, boolean isComplete)
          throws IOException {
    if (isComplete) {
      out.append("Portfolio '" + portfolioName + "' is already marked as complete!");
    } else {
      out.append("Portfolio '" + portfolioName + "' marked as complete!");
    }
  }

  /**
   * View method for displaying the load csv message.
   *
   * @param portfolioName name of the portfolio.
   * @param filePath      path of the file.
   * @param csvFound      whether csv found or not.
   * @throws IOException handles the io exception.
   */
  public void displayLoadCSVMessage(String portfolioName, String filePath, boolean csvFound)
          throws IOException {
    if (csvFound) {
      out.append("Portfolio '" + portfolioName + "' loaded from CSV file: " + filePath + "\n");
    } else {
      out.append("CSV file '" + portfolioName + "' not found in the 'portfolios' folder." + "\n");
    }
  }

  /**
   * View method for the displaying the portfolio Values.
   *
   * @param portfolioName The name of the portfolio.
   * @param date          The date for which the total value is calculated.
   * @param totalValue    The total value of the portfolio on the specified date.
   * @throws IOException handles the io exception.
   */
  @Override
  public void displayPortfolioValue(String portfolioName, String date, double totalValue)
          throws IOException {
    if (totalValue >= 0) {
      out.append("Total portfolio value on " + date + ": " + totalValue);
    } else {
      out.append("No data available for the specified portfolio or date." + "\n");
    }
  }

  /**
   * This message is used to display message if Portfolio is not present.
   *
   * @param portfolioName The name of the non-existent portfolio.
   * @throws IOException if there is an exception.
   */
  @Override
  public void displayPortfolioNotExist(String portfolioName) throws IOException {
    out.append("Portfolio '" + portfolioName + "' does not exist.");
  }

  /**
   * This method is used to display any message which is in String.
   *
   * @param message The message to be displayed.
   * @throws IOException if there is an exception.
   */
  @Override
  public void displayMessage(String message) throws IOException {
    out.append(message);
  }

  /**
   * This is used to display the Prompt message.
   *
   * @param message The prompt message.
   * @throws IOException if there is an exception.
   */
  @Override
  public void displayPromptMessage(String message) throws IOException {
    out.append(message);
  }

  /**
   * View method for showing the text based interface option of application.
   *
   * @throws IOException handles the io exception.
   */
  @Override
  public void displayMenuOptions() throws IOException {
    displayMessage("\n");
    displayMessage("1. Create a new portfolio\n");
    displayMessage("2. Add stock to a portfolio\n");
    displayMessage("3. Remove stock from a portfolio\n");
    displayMessage("4. Mark a portfolio as complete\n");
    displayMessage("5. View portfolio\n");
    displayMessage("6. Save The Portfolio\n");
    displayMessage("7. Load the Portfolio\n");
    displayMessage("8. Getting the composition of portfolio\n");
    displayMessage("9. Total value on a certain Date\n");
    displayMessage("10. Manual file for operation\n");
    displayMessage("11. Enter the stock Price\n");
    displayMessage("12. Exit\n");
  }

  /**
   * This is list of functions available for the features.
   *
   * @throws IOException if there is an exception.
   */
  @Override
  public void displayFeatures() throws IOException {
    displayMessage("1. Determine the performance of stock on a day\n");
    displayMessage("2. Determine the performance of stock over a period of time.\n");
    displayMessage("3. Determine x-days moving average.\n");
    displayMessage("4. Determine which days are crossovers\n");
    displayMessage("5. Moving crossovers\n");
    displayMessage("6. Bar chart\n");
    displayMessage("7. Stock Chart. \n");
    displayMessage("8. Back to main menu\n");
  }

  /**
   * It is used to display the bar chart.
   *
   * @param barChartOutput String of output of the bar chart from controller.
   * @throws IOException if there is an exception.
   */
  @Override
  public void displayBarChart(String barChartOutput) throws IOException {
    StringBuilder outputBuilder = new StringBuilder();
    outputBuilder.append(barChartOutput);
    out.append(outputBuilder.toString());
  }

  /**
   * This function is used to display whether there is a gain or loss.
   *
   * @param gainOrLoss String representing gain or loss.
   * @throws IOException if there is an exception.
   */
  @Override
  public void displayGainOrLossInfo(String gainOrLoss) throws IOException {
    out.append(gainOrLoss);
  }

  /**
   * This function is used to display the crossovers.
   *
   * @param crossoverOpportunities is a list of strings of the values given from controller.
   * @throws IOException if there is an exception.
   */
  @Override
  public void displayCrossovers(List<String> crossoverOpportunities) throws IOException {
    for (String opportunity : crossoverOpportunities) {
      if (opportunity == null) {
        out.append("No crossover opportunities found.");
      } else {
        out.append(opportunity);
      }
    }
  }

  /**
   * This is the display options of the start of the program.
   *
   * @throws IOException if there is an exception.
   */
  @Override
  public void displayPortfolioTypes() throws IOException {
    displayMessage("Enter values 1 or 2 to select the Portfolio. \n");
    displayMessage("1. Inflexible Portfolio. \n");
    displayMessage("2. Flexible Portfolio. \n");
    displayMessage("3. Get List of Symbols. \n");
    displayMessage("4. Get list of dates on which the symbol is available. \n");
    displayMessage("5. Features. \n");
    displayMessage("6. Enter the ticker whose file is not there.  \n");
    displayMessage("7. Exit Application. \n");
  }

  /**
   * This is the display menu function for the Flexible Portfolio.
   *
   * @throws IOException if there is an exception.
   */
  @Override
  public void displayFlexiblePortfolioOptions() throws IOException {
    displayMessage("\n");
    displayMessage("1. Create a new portfolio \n");
    displayMessage("2. Purchase stocks \n");
    displayMessage("3. Sell stocks\n");
    displayMessage("4. Calculate total money invested in Portfolio. \n");
    displayMessage("5. View portfolio\n");
    displayMessage("6. Save The Portfolio\n");
    displayMessage("7. Load the Portfolio\n");
    displayMessage("8. Display Composition of Portfolio : \n");
    displayMessage("9. Total value on a certain Date\n");
    displayMessage("10. dollar cost averaging invest.\n");
    displayMessage("11. load the dollar cost file.\n");
    displayMessage("12. list of dollar cost file.\n");
    displayMessage("13. Exit\n");
  }

  /**
   * Display method for displaying the stock chart.
   *
   * @param output Output received from the controller.
   * @throws IOException handles exception if any.
   */
  public void stockChart(String output) throws IOException {
    StringBuilder outputBuilder = new StringBuilder();
    outputBuilder.append(output);
    out.append(outputBuilder.toString());
  }

}
