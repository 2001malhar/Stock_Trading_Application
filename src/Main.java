import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import controller.GUIController;
import controller.GUIControllerInt;
import controller.PortfolioController;
import models.PortfolioModel2;
import view.PortfolioView;

/**
 * The Main class serves as the entry point for the application.
 */
public class Main {

  /**
   * Main method for invoking the text-based interface.
   */
  public static void main(String[] args) throws IOException {

    PortfolioModel2 model = new PortfolioModel2("");
    PortfolioView view = new PortfolioView(System.out);
    Readable in = new InputStreamReader(System.in);

    PortfolioController controller = new PortfolioController(model, view, in);
    StockApiCall stockApiCall = new StockApiCall();
    List<String> symbols = controller.getSymb();
    stockApiCall.getPortfolioHistoricalData(symbols,   "Historical");
    GUIControllerInt guiController = new GUIController();
    controller.choosePortfolio();
  }

}



