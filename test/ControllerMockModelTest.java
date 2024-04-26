import java.util.Collections;
import java.util.List;
import models.Features;
import models.FeaturesInt;
import models.PortfolioModelSec;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import controller.PortfolioController;
import models.PortfolioInt;
import view.PortfolioView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Test class for Controller using a mock model.
 */
public class ControllerMockModelTest {


  /**
   * Test case for adding a stock.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void checkAdd() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("1\n1\nhd\n2\nhd\nAAL\n2\n12\n6\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String expectedlog = "in the addStock method";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case for removing a stock.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test
  public void removeStock() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("1\n1\nhd\n2\nhd\nAAL\n2\n3\nhd\nAAL\n2\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String expectedlog = "in the removestock method";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case for getting stocks.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test
  public void getStocks() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("3\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String expectedlog = "";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case for marking a portfolio as complete.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void markComplete() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("1\n1\nA\n4\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String expectedlog = "in the marked as complete method";
    assertTrue(out.toString().contains(expectedlog));

  }

  /**
   * Test case for calculating the total value.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void calculateTotalValue() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("1\n1\nA\n9\nA\n2024-03-01\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    assertEquals(String.valueOf(0.01), out.toString());
  }

  /**
   * Test case for saving the portfolio as a CSV file.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test
  public void saveAsCSV() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("1\n1\nA\n6\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String expectedlog = "in the save as csvfile123";
    assertTrue(out.toString().contains(expectedlog));

  }

  /**
   * Test case for loading the portfolio from a CSV file.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void loadFromCSV() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("1\n7\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String expectedlog = "in the loading of the csv path :11";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test
  public void stockChart() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("5\n7\nAAL\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String expectedlog = "Stock Chart";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test
  public void barChart() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("2\n1\nA\n2\nA\nAAL\n3\n2024-03-01"
            + "\n13\n5\n6\n6\nA\n2023-04-01\n2023-04-10\n50\n8\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String expectedlog = "in the bar chart method2024-03-012024-03-1510";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test
  public void calculateTotalInvestment() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("2\n1\nA\n4\nA\n2024-03-01\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    assertEquals(String.valueOf(0.01), out.toString());
  }

  @Test
  public void gainOrLoss() throws IOException {
    StringBuilder log = new StringBuilder();
    FeaturesInt mockmodel = new Features();
    Readable in = new StringReader("5\n1\nAAL\n2024-03-01\n8\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String exe = "Gain or Loss";
    assertTrue(out.toString().contains(exe));
  }

  @Test
  public void gainOrLoss1() throws IOException {
    StringBuilder log = new StringBuilder();
    FeaturesInt mockmodel = new Features();
    Readable in = new StringReader("5\n2\nAAL\n2024-03-01\n2024-03-08\n8\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String exe = "Gain or Loss 1";
    assertTrue(out.toString().contains(exe));
  }

  @Test
  public void gainOrLoss2() throws IOException {
    StringBuilder log = new StringBuilder();
    FeaturesInt mockmodel = new Features();
    Readable in = new StringReader("5\n3\nAAL\n2024-03-01\n10\n8\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    assertTrue(out.toString().contains("0.01"));
  }

  @Test
  public void gainOrLoss3() throws IOException {
    StringBuilder log = new StringBuilder();
    FeaturesInt mockmodel = new Features();
    Readable in = new StringReader("5\n4\nAAL\n2024-03-01\n2024-03-08\n8\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    List<String> exe = Collections.singletonList("Gain or Loss");
    assertTrue(out.toString().contains((CharSequence) exe));
  }

  @Test
  public void gainOrLoss4() throws IOException {
    StringBuilder log = new StringBuilder();
    FeaturesInt mockmodel = new Features();
    Readable in = new StringReader("5\n4\nAAL\n2024-03-01\n2024-03-08\n10\n50\n8\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    List<String> exe = Collections.singletonList("Gain or Loss1");
    assertTrue(out.toString().contains((CharSequence) exe));
  }


  @Test
  public void radicalValue() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n3"
            + "\n9\nA\n2023-05-02\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String expectedlog = "in the radical value method2023-05-02";
    assertTrue(out.toString().contains(expectedlog));
  }


  @Test
  public void dollarCost() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioInt mockmodel = new MockModel("hd", log);
    Readable in = new StringReader("2\n10\na\n100\na\n60\nmsft\n40" +
            "\ndone\n100\n2023-02-02\n2024-08-05\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController((PortfolioModelSec) mockmodel, view,
            in);
    controller.choosePortfolio();
    String expectedlog = "in the dollarCost methoda";
    assertTrue(out.toString().contains(expectedlog));

  }





}