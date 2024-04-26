package controller;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import view.PortfolioView;

import static org.junit.Assert.assertEquals;


/**
 * Test class for Controller using a mock model.
 */
public class ControllerMockModelTest {

  private MockModel model;
  StringBuilder log = new StringBuilder();

  /**
   * Test case for adding a stock.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void checkAdd() throws IOException {
    MockModel mockmodel = new MockModel("",log);
    Readable in = new StringReader("\n1\nhm\n2\nhm\na\n34\n18\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(mockmodel, view, in);
    controller.start();
    String expectedlog = "in the addStock method";
    assertEquals(expectedlog, mockmodel.getLog());
  }

  /**
   * Test case for removing a stock.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test
  public void removeStock() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockmodel = new MockModel("hd",log);
    Readable in = new StringReader("\n1\nhm\n3\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(mockmodel, view, in);
    controller.start();
    String expectedlog = "in the removestock method";
    assertEquals(expectedlog, mockmodel.getLog());
  }

  /**
   * Test case for getting stocks.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test
  public void getStocks() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockmodel = new MockModel("hd",log);
    Readable in = new StringReader("\n1\nhm\n3\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(mockmodel, view, in);
    controller.start();
    String expectedlog = "";
    assertEquals(expectedlog, log.toString());
  }

  /**
   * Test case for marking a portfolio as complete.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void markComplete() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockmodel = new MockModel("hd",log);
    Readable in = new StringReader("\n1\nhm\n4\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(mockmodel, view, in);
    controller.start();
    String expectedlog = "in the marked as complete method";
    assertEquals(expectedlog, log.toString());

  }

  /**
   * Test case for calculating the total value.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void calculateTotalValue() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockmodel = new MockModel("hd",log);
    Readable in = new StringReader("\n1\nhm\n9\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(mockmodel, view, in);
    controller.start();
    double expectedlog = 0;
    assertEquals(expectedlog, log.toString());

  }

  /**
   * Test case for saving the portfolio as a CSV file.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test
  public void saveAsCSV() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockmodel = new MockModel("hd",log);
    Readable in = new StringReader("\n1\nhm\n6\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(mockmodel, view, in);
    controller.start();
    String expectedlog = "in the save as csvfile123";
    assertEquals(expectedlog, log.toString());

  }

  /**
   * Test case for loading the portfolio from a CSV file.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void loadFromCSV() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockmodel = new MockModel("hd",log);
    Readable in = new StringReader("\n1\nhm\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(mockmodel, view, in);
    controller.start();
    String expectedlog = "in the loading of the csv path :11";
    assertEquals(expectedlog, log.toString());

  }

}