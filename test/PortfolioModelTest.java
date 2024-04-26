import java.util.ArrayList;

import models.Features;
import models.FeaturesInt;
import models.PortfolioModel2;

import org.junit.Test;

import java.util.List;

import models.PortfolioModel;
import models.PortfolioModelSec;
import models.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test for testing of the model functions.
 */
public class PortfolioModelTest {


  @Test
  public void testAddStock() {
    PortfolioModel portfolio = new PortfolioModel("TestPortfolio");
    Stock stockToAdd = new Stock("AAPL", 10);
    portfolio.addStock(stockToAdd);
    List<Stock> stocks = portfolio.getStocks();
    assertEquals(1, stocks.size());
    assertEquals("AAPL", stocks.get(0).getSymbol());
    assertEquals(10, stocks.get(0).getShares());
  }

  @Test
  public void testAddStock_CompletePortfolio() {
    PortfolioModel portfolio = new PortfolioModel("TestPortfolio");
    portfolio.markComplete();
    Stock stockToAdd = new Stock("AAPL", 20);
    portfolio.addStock(stockToAdd);
    List<Stock> stocks = portfolio.getStocks();
    assertEquals(0, stocks.size());
  }

  @Test
  public void testAddStock_AddThenMarkComplete() {
    PortfolioModel portfolio = new PortfolioModel("TestPortfolio");
    Stock stockToAdd = new Stock("AAPL", 20);
    portfolio.addStock(stockToAdd);
    portfolio.markComplete();
    Stock anotherStock = new Stock("GOOG", 30);
    portfolio.addStock(anotherStock);
    List<Stock> stocks = portfolio.getStocks();
    assertEquals(1, stocks.size());
  }

  @Test
  public void testRemoveNonExistingStock() {
    PortfolioModel portfolio = new PortfolioModel("TestPortfolio");
    Stock stock1 = new Stock("AAPL", 10);
    Stock stock2 = new Stock("GOOGL", 15);
    portfolio.addStock(stock1);
    portfolio.addStock(stock2);
    Stock nonExistingStock = new Stock("MSFT", 5);
    portfolio.removeStock(nonExistingStock);
    List<Stock> stocks = portfolio.getStocks();
    assertEquals(2, stocks.size());

  }

  @Test
  public void testMarkPortfolioCompleteSuccessfully() {
    PortfolioModel portfolio = new PortfolioModel("TestPortfolio");
    portfolio.markComplete();
    assertTrue(portfolio.isComplete());
  }

  @Test
  public void testCalculateTotalValue_Success() {
    PortfolioModelSec portfolio = new PortfolioModel2("TestPortfolio");
    Stock stock1 = new Stock("AAPL", 10, "2023-02-29");
    Stock stock2 = new Stock("GOOGL", 5, "2023-02-29");
    portfolio.addStock(stock1);
    portfolio.addStock(stock2);
    double totalValue = portfolio.radicalValue("2024-03-04");
    assertEquals(2417.75, totalValue, 0.01);
  }

  @Test(expected = AssertionError.class)
  public void testLoadPortfolio_NonExistentFile() {
    String filePath = "non_existent_file.csv";
    PortfolioModel portfolio = new PortfolioModel("TestPortfolio");
    portfolio.loadFromCSV(filePath);
    assertNull(portfolio);
  }

  @Test
  public void testCalculateTotalValue() {
    PortfolioModel model = new PortfolioModel("A");
    Stock stock = new Stock("AAL", 10,
            150.0, "2024-03-01");

    model.addStock(stock);

    double totalValue = model.calculateTotalValue("2024-03-15");
    assertEquals(139.6, totalValue, 0.01);
  }

  @Test
  public void testCalculateTotalValue_PurchaseDateAfterGivenDate() {
    PortfolioModel model = new PortfolioModel("A");
    Stock stock = new Stock("AAPL", 10, 150.0, "2024-01-01");

    model.addStock(stock);
    double totalValue = model.calculateTotalValue("2023-12-31");
    assertEquals(0.0, totalValue, 0.01);
  }

  @Test
  public void testCalculateTotalInvestment() {
    PortfolioModel2 model = new PortfolioModel2("Test Portfolio");
    List<Stock> stocks = new ArrayList<>();
    stocks.add(new Stock("AAPL", 10,
            150.0, "2023-01-01"));

    double totalInvestment = model.calculateTotalInvestment(model, "2022-12-31");
    assertEquals(0.0, totalInvestment, 0.01);
  }

  @Test
  public void testCalculateTotalInvestment2() {
    PortfolioModel2 model = new PortfolioModel2("Test Portfolio");
    List<Stock> stocks = new ArrayList<>();
    stocks.add(new Stock("AAL", 10, "2024-03-01"));

    double totalInvestment = model.calculateTotalInvestment(model, "2024-03-15");
    assertEquals(156.6, totalInvestment, 0.01);
  }


  @Test
  public void gainOrLoss() {
    FeaturesInt model = new Features();
    String expect = model.determineGainOrLoss("AAL", "2024-03-01");
    assertEquals("Date: 2024-03-01, Opening Price: 15.695, Closing Price: 15.66," +
            " Loss: 0.03500000000000014\n", expect);
  }

  @Test
  public void gainOrLossOverNDays() {
    Features m = new Features();
    String exe = m.getPerformanceOverNDays("AAL", "2024-03-01",
            "2024-03-08");
    assertEquals("Gain by: 0.9800000000000004\n", exe);
  }

  @Test
  public void xDaysAvg() {
    Features m = new Features();
    double exe = m.calculateXDayMovingAverage("AAL", "2024-03-01",
            10);
    assertEquals(15.211999999999994, exe, 0.01);
  }

  @Test
  public void detectCrossOvers() {
    Features m = new Features();
    List<String> exe = m.detectCrossovers("AAL", "2024-03-01",
            "2024-03-08");
    ArrayList<String> expected = new ArrayList<>();
    expected.add("Crossover on 2024-03-08 shows Sell opportunity");
    expected.add(null);
    expected.add(null);
    expected.add(null);
    expected.add("Crossover on 2024-03-04 shows Sell opportunity");
    expected.add(null);
    assertEquals(expected, exe);
  }

  @Test
  public void movingCrossOvers() {
    Features m = new Features();
    List<String> exe = m.movingCrossOvers("AAL", "2024-03-01",
            "2024-03-08", 10, 50);
    ArrayList<String> expected = new ArrayList<>();
    expected.add("Buy opportunity on 2024-03-08");
    expected.add("Buy opportunity on 2024-03-07");
    expected.add("Buy opportunity on 2024-03-06");
    expected.add("Buy opportunity on 2024-03-05");
    expected.add("Buy opportunity on 2024-03-04");
    expected.add("Buy opportunity on 2024-03-01");

    assertEquals(expected, exe);
  }


}