import java.util.List;

/**
 * Interface representing methods to make API calls for retrieving historical data for a portfolio
 * of stocks.
 */
public interface StockApiCallInt {

  /**
   * Retrieves historical data for the given list of stock symbols and saves it in the specified
   * folder path.
   *
   * @param stockSymbols The list of stock symbols for which historical data needs to be retrieved.
   * @param folderPath   The folder path where the historical data will be saved.
   */
  void getPortfolioHistoricalData(List<String> stockSymbols, String folderPath);
}
