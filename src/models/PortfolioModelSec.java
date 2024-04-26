package models;

import java.util.List;

/**
 * This is the interface of the portfolio model which extends previous model and has implementation
 * of the graphs.
 */
public interface PortfolioModelSec extends PortfolioInt {

  /**
   * Method used to create the bar chart.
   *
   * @param startDate start date from which bar is needed.
   * @param endDate   end date to which bar chart is needed.
   * @param scale     scale of the graph.
   * @return string which contains graph.
   */
  String barChart(String startDate, String endDate, int scale);

  /**
   * Method is used to calculate the total investment of portfolio.
   *
   * @param portfolio portfolio of which investment is needed.
   * @param date      date on which investment is needed.
   * @return value of the investment.
   */
  double calculateTotalInvestment(PortfolioInt portfolio, String date);

  /**
   * Volume of the shares.
   *
   * @param symbol symbol of which volume is needed.
   * @param date   date on which volume is needed.
   * @param shares shares of the stock.
   * @return value of volume.
   */
  double volumeShare(String symbol, String date, double shares);

  /**
   * Used to make the stock chart.
   *
   * @param symbol symbol of the stock.
   * @param date1  date from which the graph starts.
   * @param date2  date on which the graph ends.
   * @param scale  scale of the graph.
   * @return information of the stock chart.
   */
  String stockChart(String symbol, String date1, String date2, int scale);

  /**
   * Used to get the radical value.
   *
   * @param date date on which value is needed.
   * @return value on the date.
   */
  double radicalValue(String date);


  /**
   * Method to perform dollar cost averaging for the portfolios.
   * This method will take the portfolio name, amount to invest, start date, end date, frequency.
   * and weights of the investment.
   * It will return the list of the stocks.
   * along with the shares bought on some fixed frequency.
   *
   * @param portfolioName name of the portfolio.
   * @param amount        amount to invest.
   * @param startDate     start date of the investment.
   * @param endDate       end date of the investment.
   * @param frequency     frequency of the investment.
   * @param weights       weights of the investment.
   * @return list of the stocks.
   */

  List<String> dollarCost(String portfolioName, double amount,
                          String startDate, String endDate, int frequency, List<Double> weights);

}
