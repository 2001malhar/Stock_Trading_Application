import java.util.List;

import models.PortfolioInt;
import models.PortfolioModelSec;
import models.Stock;

/**
 * mockmodel class for testing the dummy methods of the model,
 * and checking the flow of the controller.
 */
public class MockModel implements PortfolioModelSec {
  private StringBuilder log;

  public MockModel(String name, StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addStock(Stock stock) {
    log.append("in the addStock method");
  }

  @Override
  public void removeStock(Stock stock) {
    log.append("in the removestock method");
  }

  @Override
  public List<Stock> getStocks() {
    return null;
  }

  @Override
  public void markComplete() {
    log.append("in the marked as complete method ");
  }

  @Override
  public double calculateTotalValue(String date) {
    return 0.01;
  }


  @Override
  public void loadFromCSV(String filePath) {
    log.append("in the loading of the csv path :" + filePath);
  }

  @Override
  public String getName() {
    log.append("in the get name method");
    return "Name of Portfolio";
  }

  @Override
  public double getStockPriceOnDate(String symbol, String date) {
    log.append("in the get stock price on date method").append(symbol).append(date);
    return 0;
  }

  @Override
  public Stock getStockBySymbol(String stockSymbol) {
    log.append("in the get stock by symbol method").append(stockSymbol);

    return null;
  }

  @Override
  public boolean manualAddStock(Stock newStock) {
    log.append("in the manual add stock method").append(newStock);
    return false;
  }

  @Override
  public boolean isComplete() {
    log.append("in the is complete method");
    return false;
  }

  @Override
  public double getCurrentPrice(Stock stock) {
    log.append("in the get current price method").append(stock);
    return 0;
  }

  @Override
  public String barChart(String startDate, String endDate, int scale) {
    log.append("in the bar chart method").append(startDate).append(endDate).append(scale);
    return "Bar Chart";
  }



  @Override
  public double calculateTotalInvestment(PortfolioInt portfolio, String date) {
    return 0.01;
  }

  @Override
  public double volumeShare(String symbol, String date, double shares) {
    log.append("in the volume share method").append(symbol).append(date).append(shares);
    return 0.01;
  }



  @Override
  public String stockChart(String symbol, String date1, String date2, int scale) {
    return "Stock Chart";
  }

  @Override
  public double radicalValue(String date) {
    log.append("in the radical value method").append(date);
    return 0.01;
  }

  @Override
  public List<String> dollarCost(String portfolioName, double amount,
                                 String startDate, String endDate,
                                 int frequency, List<Double> weights) {
    log.append("in the dollar cost method").append(portfolioName);
    return null;
  }
}
