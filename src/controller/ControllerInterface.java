package controller;

import java.io.IOException;
import java.util.List;

/**
 * Interface for controller defining method for managing the controller of the portfolio.
 */
public interface ControllerInterface {

  /**
   * Method to start the controller handling the user data and the flow of the data.
   *
   * @throws IOException if an I/O error occurs
   */
  void start() throws IOException;


  /**
   * getSymbol is the method that returns the list of symbols.
   *
   * @return List of symbols.
   */
  List<String> getSymb();
}
