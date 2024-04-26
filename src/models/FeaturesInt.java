package models;

import java.util.List;

/**
 * The interface of features class which contains methods for analysis of stocks.
 */
public interface FeaturesInt {

  /**
   * This method is used to determine the performance of stock on a specific date.
   *
   * @param symbol the symbol of stock.
   * @param date   the date on which the performance is required.
   * @return the performance.
   */
  String determineGainOrLoss(String symbol, String date);

  /**
   * Method is used to get the performance of the stock over between 2 dates.
   *
   * @param symbol    symbol of stock.
   * @param startDate start date of the period.
   * @param endDate   end date of the period.
   * @return performance over the time.
   */
  String getPerformanceOverNDays(String symbol, String startDate, String endDate);

  /**
   * Method used to calculate the average of X days.
   *
   * @param symbol       symbol of stock.
   * @param startDate    start date from which the performance is needed.
   * @param numberOfDays number of days.
   * @return the average of the days.
   */
  double calculateXDayMovingAverage(String symbol, String startDate, int numberOfDays);

  /**
   * Used to detect the crossovers of the specific period.
   *
   * @param symbol    symbol of the stock.
   * @param startDate the start date from which we want crossovers.
   * @param endDate   the end date to which crossovers in needed.
   * @return List of the crossovers.
   */
  List<String> detectCrossovers(String symbol, String startDate, String endDate);

  /**
   * Method used to calculate moving crossovers.
   *
   * @param symbol    symbol of the stock.
   * @param startDate the start date from which we want crossovers.
   * @param endDate   the end date to which crossovers in needed.
   * @param xDays     the days of which average is needed.
   * @param yDays     the days of which average is needed.
   * @return List of crossovers.
   */
  List<String> movingCrossOvers(String symbol, String startDate, String endDate, int xDays,
      int yDays);

}
