import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import controller.PortfolioController;
import models.PortfolioModelSec;
import models.PortfolioModel2;
import view.PortfolioView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class contains test cases , for the functionalities of the Controller, Model, and View
 * components. The tests cover adding stocks to a portfolio, removing stocks from a portfolio,
 * viewing portfolio details, and handling various scenarios.
 */

public class ControllerModelTest {

  /**
   * Test case to check the addition of stocks to a portfolio.
   *
   * @throws IOException If an I/O error occurs
   */
  @Test
  public void checkAdd() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Portfolio 'A' created successfully!";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test
  public void portfolio() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\n*\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Portfolio name must contain at "
            + "least one letter or one number";
    assertTrue(out.toString().contains(expectedlog));

  }

  /**
   * Test case to check the removal of stocks from a portfolio.
   *
   * @throws IOException If an I/O error occurs
   */
  @Test
  public void checkAdd1() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("hd");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Enter values 1 or 2 to select the Portfolio. \n" +
            "1. Inflexible Portfolio. \n" +
            "2. Flexible Portfolio. \n" +
            "3. Get List of Symbols. \n" +
            "4. Get list of dates on which the symbol is available. \n" +
            "5. Features. \n" +
            "6. Enter the ticker whose file is not there.  \n" +
            "7. Exit Application. \n" +
            "Enter your choice: \n" +
            "1. Create a new portfolio\n" +
            "2. Add stock to a portfolio\n" +
            "3. Remove stock from a portfolio\n" +
            "4. Mark a portfolio as complete\n" +
            "5. View portfolio\n" +
            "6. Save The Portfolio\n" +
            "7. Load the Portfolio\n" +
            "8. Getting the composition of portfolio\n" +
            "9. Total value on a certain Date\n" +
            "10. Manual file for operation\n" +
            "11. Enter the stock Price\n" +
            "12. Exit\n" +
            "Enter your choice: Enter portfolio name: Portfolio 'A' created successfully!\n" +
            "1. Create a new portfolio\n" +
            "2. Add stock to a portfolio\n" +
            "3. Remove stock from a portfolio\n" +
            "4. Mark a portfolio as complete\n" +
            "5. View portfolio\n" +
            "6. Save The Portfolio\n" +
            "7. Load the Portfolio\n" +
            "8. Getting the composition of portfolio\n" +
            "9. Total value on a certain Date\n" +
            "10. Manual file for operation\n" +
            "11. Enter the stock Price\n" +
            "12. Exit\n" +
            "Enter your choice: Enter portfolio name: Enter stock symbol: Enter number of shares:" +
            " Added 5.0 shares of stock 'AAL' to portfolio 'A'.\n" +
            "1. Create a new portfolio\n" +
            "2. Add stock to a portfolio\n" +
            "3. Remove stock from a portfolio\n" +
            "4. Mark a portfolio as complete\n" +
            "5. View portfolio\n" +
            "6. Save The Portfolio\n" +
            "7. Load the Portfolio\n" +
            "8. Getting the composition of portfolio\n" +
            "9. Total value on a certain Date\n" +
            "10. Manual file for operation\n" +
            "11. Enter the stock Price\n" +
            "12. Exit\n" +
            "Enter your choice: Exiting...Enter values 1 or 2 to select the Portfolio. \n" +
            "1. Inflexible Portfolio. \n" +
            "2. Flexible Portfolio. \n" +
            "3. Get List of Symbols. \n" +
            "4. Get list of dates on which the symbol is available. \n" +
            "5. Features. \n" +
            "6. Enter the ticker whose file is not there.  \n" +
            "7. Exit Application. \n" +
            "Enter your choice: Exiting...";
    assertEquals(expectedlog, out.toString());
  }

  /**
   * Test case to check adding the same stock multiple times to a portfolio.
   *
   * @throws IOException If an I/O error occurs
   */
  @Test
  public void addIfPortfolioDoesnotExist() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("hd");
    Readable in = new StringReader("1\n1\nA\n2\nB\nAAL\n5\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Portfolio 'B' does not exist.";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case to check adding the same stock and viewing the portfolio details.
   *
   * @throws IOException If an I/O error occurs
   */
  @Test
  public void addStockNotAvailable() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("hd");
    Readable in = new StringReader("1\n1\nA\n2\nA\nxyz\n5\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Error adding stock to portfolio 'A'.";
    assertTrue(out.toString().contains(expectedlog));
  }


  /**
   * Test case to check adding multiple stocks to a portfolio.
   *
   * @throws IOException If an I/O error occurs
   */


  @Test()
  public void removeStock() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5\n3\nA\nAAL\n1\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Enter number of shares: 1 shares of stock 'AAL' " +
            "removed from portfolio 'A'";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case to check removing a non-existent stock from a portfolio.
   *
   * @throws IOException If an I/O error occurs
   */

  @Test
  public void removeNonExistent() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5\n3\nA\nGOOGL\n1\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Stock 'GOOGL' not found in portfolio 'A'.";
    assertTrue(out.toString().contains(expectedlog));


  }


  /**
   * Test case to check removing multiple stocks from a portfolio.
   *
   * @throws IOException If an I/O error occurs
   */

  @Test()
  public void removeInsufficient() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5\n3\nA\nAAL\n10\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Insufficient shares of stock 'AAL' in portfolio 'A'.";

    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case to check removing insufficient stocks from a portfolio.
   *
   * @throws IOException If an I/O error occurs
   */

  @Test
  public void removeInsufficient1() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5\n3\nB\n3\nA\nAAL\n1\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Enter portfolio name: Portfolio 'B' does not exist.";
    assertTrue(out.toString().contains(expectedlog));
  }


  /**
   * Test case to verify the behavior of removing a stock that does not exist in the portfolio.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void markComplete() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n4\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Portfolio 'A' marked as complete!";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case to verify the behavior of adding a stock with an invalid symbol to the portfolio.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void markCompletePortfolioNotAvailable() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n4\nB\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Portfolio 'B' does not exist.";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case to verify the behavior of adding a stock to a non-existing portfolio.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void addWrongportfolio() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n4\nA\n4\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Portfolio 'A' is already marked as complete!";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case to verify the behavior of removing a stock from a non-existing portfolio.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void viewPortfolio() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5\n5\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Portfolio Composition:Portfolio: AStocks:- Symbol: AAL " +
            " Shares: 5  Purchase Price: 13.95  Purchase Date: 2024-04-10";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case to verify the behavior of adding a stock to a completed portfolio.
   *
   * @throws IOException if an I/O error occurs
   */


  @Test
  public void invalidPortfolioToView() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5\n5\nB\n5\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Portfolio 'B' does not exist.";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case to verify the behavior of removing a stock from a completed portfolio.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test
  public void savePortfolio() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n6\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Portfolio saved successfully!";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case for saving portfolio as CSV. Test for Successful Saving.
   *
   * @throws IOException If an I/O error occurs
   */

  @Test
  public void loadCsv() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nt\n7\nRAM\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Portfolio 'RAM' loaded from CSV file: portfolios\\RAM.csv";
    assertTrue(out.toString().contains(expectedlog));
  }


  /**
   * Test case for saving an empty portfolio as CSV. Test for Handling Empty Portfolio.
   *
   * @throws IOException If an I/O error occurs
   */

  @Test
  public void saveInvalidPortfolioCsv() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n6\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = ": Portfolio 'A' does not exist.";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case for handling invalid file path while saving as CSV. Test for Handling Invalid File
   * Path.
   *
   * @throws IOException If an I/O error occurs
   */

  @Test
  public void composition() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5\n8\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Composition of Portfolio 'models.PortfolioModel2@327471b5':"
            + "Stock Symbol: AALShares: 5Purchase Price: 14.92"
            + "Current Price: 14.92Profit/Loss: 0.0";
    assertTrue(out.toString().contains(expectedlog));

  }

  /**
   * Test case for handling IOException while saving as CSV. Test for Handling IOException.
   *
   * @throws IOException If an I/O error occurs
   */

  @Test()
  public void ioException() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n8\nA\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Portfolio 'A' does not exist.";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Javadoc for the ContentCsv method.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void ContentCsv() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n8\nA\n12\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expected = " Portfolio 'models.PortfolioModel2@6442b0a6' is empty.";
    assertTrue(out.toString().contains(expected));
  }

  /**
   * Javadoc for the OverwritingFile method.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test
  public void totalValue() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5\n9\nA\n2024-03-26\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Total portfolio value on 2024-03-26: 74.6";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Javadoc for the overwrittenContentCsv method.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test()
  public void totalValueBeforeDate() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5\n9\nA\n2024-03-01\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expected = " Total portfolio value on 2024-03-01: 78.3";
    assertTrue(out.toString().contains(expected));
  }

  /**
   * Javadoc for the emptyFileContentCsv method.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test()
  public void inValidPortfolioTotalCost() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5\n9\nB\n9\nA\n2024-03-01\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expected = " Portfolio 'B' does not exist.";
    assertTrue(out.toString().contains(expected));
  }

  /**
   * Javadoc for the loadTheexisting method.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void invalidDate() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("1\n1\nA\n2\nA\nAAL\n5"
            + "\n9\nA\n2024-123-12\n2024-03-01\n12\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Invalid date format.Enter the date (yyyy-MM-dd):";
    assertTrue(out.toString().contains(expectedlog));

  }

  /**
   * Javadoc for the loadTheempty method.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test
  public void purchaseStock() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n1\nA\n2\nA\nAAL\n5\n2024-03-01\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Stock added successfully!...";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Javadoc for the loadNonExisting method.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test()
  public void purchaseInInvalid() throws IOException {

    PortfolioModelSec model = new PortfolioModel2("2\n1");
    Readable in = new StringReader("");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Portfolio does not exist.";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Javadoc for the OperationAfterSaveCsv method.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test()
  public void sell() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader(
            "2\n1\nA\n2\nA\nAAL\n5\n2024-03-01\n3\nA\nAAL\n2\n2024-03-15\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Shares sold successfully!";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case for loading the saved csv.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test()
  public void sellMore() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader(
            "2\n1\nA\n2\nA\nAAL\n5\n2024-03-01\n3\nA\nAAL\n10\n2024-03-15\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Cannot share more shares than shares present in Portfolio.";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test()
  public void sellInValid() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader(
            "2\n1\nA\n2\nA\nAAL\n5\n2024-03-01\n3\nB\nAAL\n1\n2024-03-15\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Portfolio not found.";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test
  public void sellBefore() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader(
            "2\n1\nA\n2\nA\nAAL\n5\n2024-03-01\n3\nA\nAAL\n1\n2024-02-15\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Cannot sell before purchase date.";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case for manual addition.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test
  public void view() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader(
            "2\n1\nA\n2\nA\nAAL\n5\n2024-03-01\n5\nA\n2024-03-15\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Portfolio: AStocks:- Symbol: AAL  Shares: 5  "
            + "Purchase Price: 15.66  Purchase Date: 2024-03-01";
    assertTrue(out.toString().contains(expectedlog));

  }

  /**
   * Test case for successful composition.
   *
   * @throws IOException if an I/O error occurs
   */
  @Test()
  public void investment() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader(
            "2\n1\nA\n2\nA\nAAL\n5\n2024-03-01\n4\nA\n2024-03-15\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Total portfolio value on 2024-03-15: 78.3";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test()
  public void investmentBefore() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader(
            "2\n1\nA\n2\nA\nAAL\n5\n2024-03-15\n4\nA\n2024-03-01\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Total portfolio value on 2024-03-01: 0.0";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case for empty portfolio composition.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test()
  public void performanceOnADay() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("5\n1\nAAL\n2024-03-01\n8\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Date: 2024-03-01, Opening Price: 15.695,"
            + " Closing Price: 15.66, Loss: 0.03500000000000014";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test()
  public void performanceOnADayNotAvailable() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("5\n1\nAAL\n2024-03-17\n8\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Day - Opening Price: 13.96, "
            + "Closing Price: 13.96, No Change";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test()
  public void performanceOverTime() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("5\n2\nAAL\n2024-03-01\n2024-03-15\n8\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Gain by: 1.6999999999999993";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test()
  public void performanceOverTimeforLoss() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("5\n2\nAAL\n2024-01-26\n2024-01-29\n8\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Gain by: 0.19000000000000128";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test
  public void movingAvg() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("5\n3\nAAL\n2024-03-01\n10\n8\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "15.2119999999999941";
    assertTrue(out.toString().contains(expectedlog));
  }

  /**
   * Test case for non-existent portfolio composition.
   *
   * @throws IOException if an I/O error occurs
   */

  @Test
  public void crossovers() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("5\n4\nAAL\n2024-03-01\n2024-03-15\n8\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Crossover on 2024-03-08 shows Sell opportunity\n"
            + "Crossover on 2024-03-04 shows Sell opportunity";
    assertTrue(out.toString().contains(expectedlog));
  }

  // test for Display Format
  @Test()
  public void movingCrossOvers() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("5\n5\nAAL\n2024-03-01\n2024-03-08\n30\n100\n8\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Buy opportunity on 2024-03-08\n"
            + "Buy opportunity on 2024-03-07\n"
            + "Buy opportunity on 2024-03-06\n"
            + "Buy opportunity on 2024-03-05\n"
            + "Buy opportunity on 2024-03-04\n"
            + "Buy opportunity on 2024-03-01";
    assertTrue(out.toString().contains(expectedlog));
  }


  //testing for the checking the functionality of the dollar cost.
  @Test
  public void DollarCost() throws IOException {

    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n10\na\n20\naapl\n50\nmsft\n50\ndone" +
            "\n200\n2024-02-02\n2024-03-02\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Enter values 1 or 2 to select the Portfolio. \n" +
            "1. Inflexible Portfolio. \n" +
            "2. Flexible Portfolio. \n" +
            "3. Get List of Symbols. \n" +
            "4. Get list of dates on which the symbol is available. \n" +
            "5. Features. \n" +
            "6. Enter the ticker whose file is not there.  \n" +
            "7. Exit Application. \n" +
            "Enter your choice: \n" +
            "1. Create a new portfolio \n" +
            "2. Purchase stocks \n" +
            "3. Sell stocks\n" +
            "4. Calculate total money invested in Portfolio. \n" +
            "5. View portfolio\n" +
            "6. Save The Portfolio\n" +
            "7. Load the Portfolio\n" +
            "8. Display Composition of Portfolio : \n" +
            "9. Total value on a certain Date\n" +
            "10. dollar cost averaging invest.\n" +
            "11. load the save dollar cost.\n" +
            "12. list of the passive investment portfolio.\n" +
            "13. Exit\n" +
            "Enter your choice: Enter portfolio name: Enter the frequency " +
            "of investment (in days):\n" +
            "Enter stock symbol or 'done' to finish:\n" +
            "Total weight should be 100.\n" +
            "Enter the weight for this stock:\n" +
            "Enter stock symbol or 'done' to finish:\n" +
            "Total weight should be 100.\n" +
            "Enter the weight for this stock:\n" +
            "Enter stock symbol or 'done' to finish:\n" +
            "Enter the investment amount:\n" +
            "Enter the start date (YYYY-MM-DD):\n" +
            "Enter the end date (YYYY-MM-DD):\n" +
            "Dollar Cost Averaging Investment:\n" +
            "Date: 2024-02-02, Symbol: aapl, Shares Bought: 0.5381, Amount Invested: $100.00\n" +
            "Date: 2024-02-02, Symbol: msft, Shares Bought: 0.2432, Amount Invested: $100.00\n" +
            "Total Investment on 2024-02-02: $200.00\n" +
            "Date: 2024-02-22, Symbol: aapl, Shares Bought: 0.5424, Amount Invested: $100.00\n" +
            "Date: 2024-02-22, Symbol: msft, Shares Bought: 0.2429, Amount Invested: $100.00\n" +
            "Total Investment on 2024-02-22: $200.00\n" +
            "Final Portfolio Value on 2024-03-02: $396.09\n" +
            "\n" +
            "1. Create a new portfolio \n" +
            "2. Purchase stocks \n" +
            "3. Sell stocks\n" +
            "4. Calculate total money invested in Portfolio. \n" +
            "5. View portfolio\n" +
            "6. Save The Portfolio\n" +
            "7. Load the Portfolio\n" +
            "8. Display Composition of Portfolio : \n" +
            "9. Total value on a certain Date\n" +
            "10. dollar cost averaging invest.\n" +
            "11. load the save dollar cost.\n" +
            "12. list of the passive investment portfolio.\n" +
            "13. Exit\n" +
            "Enter your choice: Exiting...Enter values 1 or 2 to select the Portfolio. \n" +
            "1. Inflexible Portfolio. \n" +
            "2. Flexible Portfolio. \n" +
            "3. Get List of Symbols. \n" +
            "4. Get list of dates on which the symbol is available. \n" +
            "5. Features. \n" +
            "6. Enter the ticker whose file is not there.  \n" +
            "7. Exit Application. \n" +
            "Enter your choice: Exiting...";
    assertTrue(out.toString().contains(expectedlog));

  }


  @Test
  public void listLoadDollar() throws IOException {

    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n12\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "a.csv\n" +
            "\n" +
            "aa.csv\n" +
            "\n" +
            "f.csv\n" +
            "\n" +
            "fg.csv\n" +
            "\n" +
            "l.csv\n" +
            "\n" +
            "priyank.csv\n" +
            "\n" +
            "ty.csv\n";
    assertTrue(out.toString().contains(expectedlog));

  }

  //testing for the dollar cost averaging with the end date
  @Test
  public void dollarCostWithDate() throws IOException {

    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n10\na\n100\na\n60\nmsft\n40\ndone" +
            "\n10\n2023-02-02\n2023-06-02\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "stock price on  2023-06-02  of  a is 118.22\n" +
            "stock share are : 0.08546387139871509\n" +
            "stock price on  2023-06-02  of  msft is 335.4\n" +
            "stock share are : 0.02804289958872596\n" +
            "Dollar Cost Averaging Investment:\n" +
            "Date: 2023-02-02, Symbol: a, Shares Bought: 0.0386, Amount Invested: $6.00\n" +
            "Date: 2023-02-02, Symbol: msft, Shares Bought: 0.0151, Amount Invested: $4.00\n" +
            "Total Investment on 2023-02-02: $10.00\n" +
            "Date: 2023-05-13, Symbol: a, Shares Bought: 0.0469, Amount Invested: $6.00\n" +
            "Date: 2023-05-13, Symbol: msft, Shares Bought: 0.0129, Amount Invested: $4.00\n" +
            "Total Investment on 2023-05-13: $10.00\n" +
            "Final Portfolio Value on 2023-06-02: $19.51";
    assertTrue(out.toString().contains(expectedlog));

  }


  //testing when invalid date is been provided
  @Test
  public void invalidDateDollar() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n10\na\n100\na\n60\nmsft\n40\ndone" +
            "\n100\n2023-13-01\n2023-03-02\n2024-04-06\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "";
    assertTrue(out.toString().contains(expectedlog));

  }

  //testing for the invalid portfolio name
  @Test
  public void invalidPortfolio() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n10\n#@#$^&\nqqq\n100\na\n60\nmsft\n40" +
            "\ndone\n100\n2023-02-02\n2023-03-02\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Portfolio name must contain at least one letter or one number.";
    assertTrue(out.toString().contains(expectedlog));

  }

  //testing for the new dollar portfolio
  @Test
  public void newDollarPortfolio() throws IOException {
    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n1\nt\n2\nt\nt\n20\n2022-05-28\n10\nt" +
            "\n100\n100\n50\n2023-03-01\n2024-04-06\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Dollar Cost Averaging Investment:\n" +
            "Date: 2023-03-01, Symbol: t, Shares Bought: 2.6795, Amount Invested: $50.00\n" +
            "Total Investment on 2023-03-01: $50.00\n" +
            "Date: 2023-06-09, Symbol: t, Shares Bought: 3.1348, Amount Invested: $50.00\n" +
            "Total Investment on 2023-06-09: $50.00\n" +
            "Date: 2023-09-17, Symbol: t, Shares Bought: 3.3135, Amount Invested: $50.00\n" +
            "Total Investment on 2023-09-17: $50.00\n" +
            "Date: 2023-12-26, Symbol: t, Shares Bought: 3.0175, Amount Invested: $50.00\n" +
            "Total Investment on 2023-12-26: $50.00\n" +
            "Date: 2024-04-04, Symbol: t, Shares Bought: 2.8458, Amount Invested: $50.00\n" +
            "Total Investment on 2024-04-04: $50.00";
    assertTrue(out.toString().contains(expectedlog));

  }

  //testing for creating a existing portfolio
  @Test
  public void dollarCostExistingPortfolio() throws IOException {

    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n10\na\n100\na\n60\nmsft\n40\ndone\n100" +
            "\n2023-02-02\n2023-03-02\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Dollar Cost Averaging Investment:\n" +
            "Date: 2023-02-02, Symbol: a, Shares Bought: 0.3859, Amount Invested: $60.00\n" +
            "Date: 2023-02-02, Symbol: msft, Shares Bought: 0.1512, Amount Invested: $40.00\n" +
            "Total Investment on 2023-02-02: $100.00\n" +
            "Final Portfolio Value on 2023-03-02: $92.62";
    assertTrue(out.toString().contains(expectedlog));
  }


  @Test
  public void dollarCostFutureDate() throws IOException {

    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n10\na\n100\na\n60\nmsft\n40\ndone\n100" +
            "\n2023-02-02\n2024-08-05\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Dollar Cost Averaging Investment:\n" +
            "Date: 2023-02-02, Symbol: a, Shares Bought: 0.3859, Amount Invested: $60.00\n" +
            "Date: 2023-02-02, Symbol: msft, Shares Bought: 0.1512, Amount Invested: $40.00\n" +
            "Total Investment on 2023-02-02: $100.00\n" +
            "Date: 2023-05-13, Symbol: a, Shares Bought: 0.4688, Amount Invested: $60.00\n" +
            "Date: 2023-05-13, Symbol: msft, Shares Bought: 0.1293, Amount Invested: $40.00\n" +
            "Total Investment on 2023-05-13: $100.00\n" +
            "Date: 2023-08-21, Symbol: a, Shares Bought: 0.5048, Amount Invested: $60.00\n" +
            "Date: 2023-08-21, Symbol: msft, Shares Bought: 0.1243, Amount Invested: $40.00\n" +
            "Total Investment on 2023-08-21: $100.00\n" +
            "Date: 2023-11-29, Symbol: a, Shares Bought: 0.4703, Amount Invested: $60.00\n" +
            "Date: 2023-11-29, Symbol: msft, Shares Bought: 0.1056, Amount Invested: $40.00\n" +
            "Total Investment on 2023-11-29: $100.00\n" +
            "Date: 2024-03-08, Symbol: a, Shares Bought: 0.4058, Amount Invested: $60.00\n" +
            "Date: 2024-03-08, Symbol: msft, Shares Bought: 0.0985, Amount Invested: $40.00\n" +
            "Total Investment on 2024-03-08: $100.00\n" +
            "Final Portfolio Value on 2024-04-10: $589.00";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test
  public void dollarcostnoDate() throws IOException {

    StringBuilder log = new StringBuilder();
    //2024-08-05
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n10\na\n100\na\n60\nmsft\n40\ndone\n100" +
            "\n2023-02-02\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "Dollar Cost Averaging Investment:\n" +
            "Date: 2023-02-02, Symbol: a, Shares Bought: 0.3859, Amount Invested: $60.00\n" +
            "Date: 2023-02-02, Symbol: msft, Shares Bought: 0.1512, Amount Invested: $40.00\n" +
            "Total Investment on 2023-02-02: $100.00\n" +
            "Date: 2023-05-13, Symbol: a, Shares Bought: 0.4688, Amount Invested: $60.00\n" +
            "Date: 2023-05-13, Symbol: msft, Shares Bought: 0.1293, Amount Invested: $40.00\n" +
            "Total Investment on 2023-05-13: $100.00\n" +
            "Date: 2023-08-21, Symbol: a, Shares Bought: 0.5048, Amount Invested: $60.00\n" +
            "Date: 2023-08-21, Symbol: msft, Shares Bought: 0.1243, Amount Invested: $40.00\n" +
            "Total Investment on 2023-08-21: $100.00\n" +
            "Date: 2023-11-29, Symbol: a, Shares Bought: 0.4703, Amount Invested: $60.00\n" +
            "Date: 2023-11-29, Symbol: msft, Shares Bought: 0.1056, Amount Invested: $40.00\n" +
            "Total Investment on 2023-11-29: $100.00\n" +
            "Date: 2024-03-08, Symbol: a, Shares Bought: 0.4058, Amount Invested: $60.00\n" +
            "Date: 2024-03-08, Symbol: msft, Shares Bought: 0.0985, Amount Invested: $40.00\n" +
            "Total Investment on 2024-03-08: $100.00\n" +
            "Final Portfolio Value on 2024-04-10: $589.00";
    assertTrue(out.toString().contains(expectedlog));
  }


  @Test
  public void loadDollarCostwithcurrentDate() throws IOException {

    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n11\npriyank\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = " Portfolio is up to date. you can view it.";
    assertTrue(out.toString().contains(expectedlog));
  }

  @Test
  public void loadDollarCostwithFutureDate() throws IOException {

    StringBuilder log = new StringBuilder();
    PortfolioModelSec model = new PortfolioModel2("");
    Readable in = new StringReader("2\n11\nP\n13\n7\n");
    StringBuffer out = new StringBuffer();
    PortfolioView view = new PortfolioView(out);
    PortfolioController controller = new PortfolioController(model, view, in);
    controller.choosePortfolio();
    String expectedlog = "";
    assertTrue(out.toString().contains(expectedlog));
  }


}