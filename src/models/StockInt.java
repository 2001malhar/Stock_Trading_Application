package models;

/**
 * This is the interface of the Stock class.
 */
public interface StockInt {

  /**
   * Gets the purchase date of the stock.
   *
   * @return The purchase date.
   */
  String getPurchaseDate();

  /**
   * Sets the number of shares of the stock.
   *
   * @param shares The new number of shares.
   */
  void setShares(double shares);

  /**
   * Gets the purchase price per share of the stock.
   *
   * @return The purchase price per share.
   */
  double getPurchasePrice();

  /**
   * Gets the number of shares of the stock.
   *
   * @return The number of shares.
   */
  double getShares();

  /**
   * Gets the symbol of the stock.
   *
   * @return The symbol of the stock.
   */
  String getSymbol();
}
