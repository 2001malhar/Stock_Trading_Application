package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


/**
 * PortfolioModel2 class implements the PortfolioModelSec interface and extends the PortfolioModel
 * class.
 * It is used to create the bar chart, calculate the total investment of the portfolio,
 * calculate the volume of the shares, create the stock chart, calculate the passive investing of
 * the portfolio and get the radical value.
 */

public class PortfolioModel2 extends PortfolioModel implements PortfolioModelSec {

  /**
   * Constructs a Models.PortfolioModel object with the specified name.
   *
   * @param name The name of the portfolio.
   */
  public PortfolioModel2(String name) {
    super(name);
  }


  /**
   * Method used to create the bar chart.
   *
   * @param startDate start date from which bar is needed.
   * @param endDate   end date to which bar chart is needed.
   * @param scale     scale of the graph.
   * @return string which contains graph.
   */
  @Override
  public String barChart(String startDate, String endDate, int scale) {
    StringBuilder output = new StringBuilder();
    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);
    long daysDiff = ChronoUnit.DAYS.between(start, end) + 1;

    if (daysDiff <= 30) {
      output.append(daysDate(start, end, scale));
    } else if (daysDiff > 30 && daysDiff < 203) {
      output.append(weekBar(start, end, scale));
    } else if (daysDiff > 203) {
      output.append(monthBar(start, end, scale));
    }

    return output.toString();
  }

  /**
   * Method is used to create the bar chart for the days.
   *
   * @param startDate start date from which bar is needed.
   * @param endDate   end date to which bar chart is needed.
   * @param scale     scale of the graph.
   * @return string which contains graph.
   */
  private String daysDate(LocalDate startDate, LocalDate endDate, int scale) {
    StringBuilder output = new StringBuilder();
    LocalDate date = startDate;
    output.append("Performance of portfolio ").append(getName()).append(" from ")
            .append(startDate.getMonth().toString().substring(0, 3)).append(" ")
            .append(startDate.getYear()).append(" to ")
            .append(endDate.getMonth().toString().substring(0, 3)).append(" ")
            .append(endDate.getYear()).append(".\n");
    while (!date.isAfter(endDate)) {
      double totalValue = radicalValue(date.toString());
      int asterisks = (int) (totalValue / scale);
      output.append(date).append(": ").append("*".repeat(asterisks)).append("\n");
      date = date.plusDays(1);
    }
    output.append("Scale: * = $" + scale + "\n");
    return output.toString();
  }

  /**
   * Method is used to create the bar chart for the weeks.
   *
   * @param startDate start date from which bar is needed.
   * @param endDate   end date to which bar chart is needed.
   * @param scale     scale of the graph.
   * @return string which contains graph.
   */
  private String weekBar(LocalDate startDate, LocalDate endDate, int scale) {
    StringBuilder out = new StringBuilder();
    LocalDate date = startDate;
    int count = 0;
    out.append("Performance of portfolio ").append(getName()).append(" from ")
            .append(startDate.getMonth().toString().substring(0, 3)).append(" ")
            .append(startDate.getYear()).append(" to ")
            .append(endDate.getMonth().toString().substring(0, 3)).append(" ")
            .append(endDate.getYear()).append(".\n");
    while (!date.isAfter(endDate)) {
      LocalDate weekEndDate = date.plusDays(6);
      count++;
      double totalValue = radicalValue(weekEndDate.toString());
      int asterisks = (int) (totalValue / scale);
      out.append("Week ").append(count).append(" ").append(date).append(": ")
              .append("*".repeat(asterisks)).append("\n");
      date = date.plusWeeks(1);
    }
    out.append("Scale: * = $" + scale + "\n");
    return out.toString();
  }

  /**
   * Method is used to create the bar chart for the months.
   *
   * @param startDate start date from which bar is needed.
   * @param endDate   end date to which bar chart is needed.
   * @param scale     scale of the graph.
   * @return string which contains graph.
   */
  private String monthBar(LocalDate startDate, LocalDate endDate, int scale) {
    StringBuilder out2 = new StringBuilder();
    LocalDate date = startDate;
    out2.append("Performance of portfolio ").append(getName()).append(" from ")
            .append(startDate.getMonth().toString().substring(0, 3)).append(" ")
            .append(startDate.getYear()).append(" to ")
            .append(endDate.getMonth().toString().substring(0, 3)).append(" ")
            .append(endDate.getYear()).append(".\n");
    while (!date.isAfter(endDate)) {
      double totalValue = radicalValue(date.toString());
      int asterisks = (int) (totalValue / scale);
      out2.append(date.getMonth().toString().substring(0, 3)).append(" ").append(date.getYear())
              .append(": ").append("*".repeat(asterisks)).append("\n");
      date = date.plusMonths(1);
    }
    out2.append("Scale: * = $" + scale + "\n");
    return out2.toString();
  }

  /**
   * The Method is used to calculate the total investment of the portfolio.
   *
   * @param portfolio portfolio of which investment is needed.
   * @param date      date on which investment is needed.
   * @return value of the investment.
   */
  @Override
  public double calculateTotalInvestment(PortfolioInt portfolio, String date) {
    double totalInvestment = 0.0;
    double investment;
    LocalDate valueOn = LocalDate.parse(date);
    List<Stock> stocks = portfolio.getStocks();
    for (StockInt stock : stocks) {
      if (LocalDate.parse(stock.getPurchaseDate()).isAfter(valueOn)) {
        investment = 0;
        totalInvestment += investment;
      } else {
        investment = stock.getShares() * stock.getPurchasePrice();
        totalInvestment += investment;
      }
    }
    return totalInvestment;
  }

  /**
   * Volume of the shares.
   *
   * @param symbol symbol of which volume is needed.
   * @param date   date on which volume is needed.
   * @param shares shares of the stock.
   * @return value of volume.
   */
  @Override
  public double volumeShare(String symbol, String date, double shares) {
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
          return Double.parseDouble(values[5].trim());
        }
      }
      return volPrev(date, lines);
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
    }
    return 0.0;
  }

  /**
   * The Method is used to get the previous volume.
   *
   * @param date  date on which volume is needed.
   * @param lines list of the lines.
   * @return value of the volume.
   */
  private double volPrev(String date, List<String> lines) {
    LocalDate previousDay = LocalDate.parse(date).minusDays(1);
    while (!previousDay.isBefore(LocalDate.parse("1950-10-15"))) {
      String previousDateString = previousDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      for (int i = 0; i < lines.size(); i++) {
        String storedLine = lines.get(i);
        String[] value = storedLine.split(",");
        String currentDate = value[0].trim();
        if (currentDate.equals(previousDateString)) {
          return Double.parseDouble(value[5].trim());
        }
      }
      previousDay = previousDay.minusDays(1);
    }
    return 0.0;
  }

  /**
   * Used to make the stock chart.
   *
   * @param symbol symbol of the stock.
   * @param date1  date from which the graph starts.
   * @param date2  date on which the graph ends.
   * @param scale  scale of the graph.
   * @return information of the stock chart.
   */
  public String stockChart(String symbol, String date1, String date2, int scale) {
    StringBuilder output = new StringBuilder();
    LocalDate start = LocalDate.parse(date1);
    LocalDate end = LocalDate.parse(date2);
    long daysDiff = ChronoUnit.DAYS.between(start, end) + 1;
    if (daysDiff <= 30) {
      output.append(stockDate(symbol, start, end, scale));
    } else if (daysDiff > 30 && daysDiff < 203) {
      output.append(stockWeek(symbol, start, end, scale));
    } else if (daysDiff > 203) {
      output.append(stockYear(symbol, start, end, scale));
    }
    return output.toString();
  }

  /**
   * stockDate is used to plot the graph of the stock.
   *
   * @param symbol symbol of the stock.
   * @return price of the stock.
   */
  private String stockDate(String symbol, LocalDate start, LocalDate end, int scale) {
    StringBuilder output = new StringBuilder();
    LocalDate date = start;
    output.append("Performance of stock ").append(symbol).append(" from ")
            .append(start.getMonth().toString().substring(0, 3)).append(" ")
            .append(start.getYear()).append(" to ")
            .append(end.getMonth().toString().substring(0, 3)).append(" ")
            .append(end.getYear()).append(".\n");
    while (!date.isAfter(end)) {
      double totalValue = getStockPriceOnDate(symbol, date.toString());
      int asterisks = (int) (totalValue / scale);
      output.append(date).append(": ").append("*".repeat(asterisks)).append("\n");
      date = date.plusDays(1);
    }
    output.append("Scale: * = $" + scale + "\n");
    return output.toString();
  }

  /**
   * stockWeek is used to plot the graph of the stock.
   *
   * @param symbol symbol of the stock.
   * @param start  start date of the graph.
   * @param end    end date of the graph.
   * @param scale  scale of the graph.
   * @return price of the stock.
   */
  private String stockWeek(String symbol, LocalDate start, LocalDate end, int scale) {

    StringBuilder out = new StringBuilder();
    LocalDate date = start;
    int count = 0;
    out.append("Performance of stock ").append(symbol).append(" from ")
            .append(start.getMonth().toString().substring(0, 3)).append(" ")
            .append(start.getYear()).append(" to ")
            .append(date.getMonth().toString().substring(0, 3)).append(" ")
            .append(date.getYear()).append(".\n");
    while (!date.isAfter(date)) {
      LocalDate weekEndDate = date.plusDays(6);
      count++;
      double totalValue = getStockPriceOnDate(symbol, date.toString());
      int asterisks = (int) (totalValue / scale);
      out.append("Week ").append(count).append(" ").append(date).append(": ")
              .append("*".repeat(asterisks)).append("\n");
      date = date.plusWeeks(1);
    }
    out.append("Scale: * = $" + scale + "\n");
    return out.toString();
  }

  /**
   * stockYear is used to plot the graph of the stock.
   *
   * @param symbol symbol of the stock.
   * @param start  start date of the graph.
   * @param end    end date of the graph.
   * @param scale  scale of the graph.
   * @return price of the stock.
   */

  private String stockYear(String symbol, LocalDate start, LocalDate end, int scale) {

    StringBuilder out2 = new StringBuilder();
    LocalDate date = start;
    out2.append("Performance of stock ").append(symbol).append(" from ")
            .append(start.getMonth().toString().substring(0, 3)).append(" ")
            .append(start.getYear()).append(" to ")
            .append(end.getMonth().toString().substring(0, 3)).append(" ")
            .append(end.getYear()).append(".\n");
    while (!date.isAfter(end)) {
      double totalValue = getStockPriceOnDate(symbol, date.toString());
      int asterisks = (int) (totalValue / scale);
      out2.append(date.getMonth().toString().substring(0, 3)).append(" ")
              .append(date.getYear()).append(": ").append("*".repeat(asterisks)).append("\n");
      date = date.plusMonths(1);
    }
    out2.append("Scale: * = $" + scale + "\n");
    return out2.toString();

  }


  /**
   * The method to calculate the passive investing of the portfolio. it have different wieght for
   * the stocks.
   *
   * @param portfolioName it is the name of the portfolio.
   * @param amount        amount to be invested.
   * @param startDate     start date of the investment.
   * @param endDate       end date of the investment.
   * @param frequency     frequency of the investment.
   * @param weights       weights of the stocks.
   * @return list of the investment.
   */
  @Override
  public List<String> dollarCost(String portfolioName, double amount,
                                 String startDate, String endDate,
                                 int frequency, List<Double> weights) {
    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);

    List<Stock> stocks = getStockAb();

    List<String> passiveWayInvesting = new ArrayList<>();
    passiveWayInvesting.add("Dollar Cost Averaging Investment:");

    for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(frequency)) {
      double totalInvestment = 0.0;
      for (int i = 0; i < stocks.size(); i++) {
        Stock stock = stocks.get(i);
        double stockAmount = amount * weights.get(i) / 100.0;
        double stockPrice = getStockPrice(stock.getSymbol(), date.toString(), end.toString());

        if (stockPrice > 0) {
          double shares = stockAmount / stockPrice;
          stock.setShares(stock.getShares() + shares);
          stock.setPurchasePrice(stock.getPurchasePrice() + stockAmount);
          totalInvestment += stockAmount;
          passiveWayInvesting.add(String.format("Date: %s, Symbol: %s, Shares Bought: %.4f," +
                          " Amount Invested: $%.2f",
                  date, stock.getSymbol(), shares, stockAmount, stock.getPurchasePrice()));
        } else {
          passiveWayInvesting.add(String.format("Price not available for %s on %s, skipping.",
                  stock.getSymbol(), date));
        }
      }
      if (totalInvestment > 0) {
        passiveWayInvesting.add(
                String.format("Total Investment on %s: $%.2f", date, totalInvestment));
      }
    }

    double finalValue = radicalValue(endDate);
    passiveWayInvesting.add(
            String.format("Final Portfolio Value on %s: $%.2f", endDate, finalValue));

    return passiveWayInvesting;
  }

  /**
   * Method to get the stock price on the date.
   *
   * @param symbol symbol of the stock.
   * @param date   date on which price is needed.
   * @return price of the stock.
   */
  public double getStockPrice(String symbol, String date, String endDate) {
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
      return nextWorkingDayPrice(symbol, date, endDate, lines);
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
    }

    return 0.0;
  }

  /**
   * Method to get the price of the stock on the next working day if the current day is holiday.
   *
   * @param symbol  symbol of the stock.
   * @param date    date on which price is needed.
   * @param endDate end date of the investment.
   * @param lines   list of the lines.
   * @return price of the stock.
   */
  private double nextWorkingDayPrice(String symbol, String date, String endDate,
                                     List<String> lines) {
    LocalDate currentDate = LocalDate.parse(date);
    LocalDate endLocalDate = LocalDate.parse(endDate);
    int daysToAdd = 1;
    while (currentDate.isBefore(endLocalDate)) {
      LocalDate nextDate = currentDate.plusDays(daysToAdd);
      String nextDateString = nextDate.toString();
      for (String line : lines) {
        String[] values = line.split(",");
        String lineDate = values[0].trim();
        if (lineDate.equals(nextDateString)) {
          return Double.parseDouble(values[4].trim());
        }
      }
      daysToAdd++;
    }
    return 0.0;
  }


  /**
   * Used to get the radical value.
   *
   * @param date date on which value is needed.
   * @return value on the date.
   */
  @Override
  public double radicalValue(String date) {
    double totalValue = 0.0;
    List<Stock> stocks = getStocks();

    for (int i = 0; i < stocks.size(); i++) {
      StockInt stock = stocks.get(i);
      double stockPrice = getStockPriceOnDate(stock.getSymbol(), date);
      System.out.println(
              "stock price on  " + date + "  of  " + stock.getSymbol() + " is " + stockPrice);
      System.out.println("stock share are : " + stock.getShares());
      if (stockPrice > 0) {
        totalValue = totalValue + stockPrice * stock.getShares();
      } else {
        System.out.println("Price  not available for stock: " + stock.getSymbol());
      }
    }
    return totalValue;
  }


  /**
   * This is the method which is used to get the list of stocks in portfolio.
   *
   * @return List of stocks.
   */
  public List<Stock> getStockAb() {
    return super.getStocks();
  }

}
