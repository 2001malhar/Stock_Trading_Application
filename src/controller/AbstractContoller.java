package controller;

import java.util.HashMap;
import java.util.Map;

import models.PortfolioModelSec;

/**
 * Abstract controller class that provides the common functionality for the controller classes.
 */
public abstract class AbstractContoller {

  public Map<String, PortfolioModelSec> flexiblePortfolios;
  public Map<String, PortfolioModelSec> portfolios;

  /**
   * Constructor for the abstract controller class.
   * It initializes the portfolios and flexible portfolios.
   */
  public AbstractContoller() {
    this.portfolios = new HashMap<>();
    this.flexiblePortfolios = new HashMap<>();
  }
}
