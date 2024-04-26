package models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a stock with information such as symbol, shares, purchase price, and purchase date.
 */
public class Stock implements StockInt {
  private final String symbol;
  private double shares;
  private double purchasePrice;
  private String purchaseDate;

  /**
   * Constructs a Stock object with the specified symbol, shares,
   * purchase price, and current date as purchase date.
   *
   * @param symbol        The symbol of the stock.
   * @param shares        The number of shares.
   * @param purchasePrice The purchase price per share.
   * @param purchaseDate  The date of purchase.
   */
  public Stock(String symbol, double shares, double purchasePrice, String purchaseDate) {
    this.symbol = symbol;
    this.shares = shares;
    this.purchasePrice = purchasePrice;
    this.purchaseDate = purchaseDate;
  }

  /**
   * Constructs a Stock object with the specified symbol, shares, purchase price,
   * and current date as purchase date.
   *
   * @param symbol        The symbol of the stock.
   * @param shares        The number of shares.
   * @param purchasePrice The purchase price per share.
   * @param purchaseDate  The date of purchase.
   */
  public Stock(String purchaseDate, String symbol, double shares, double purchasePrice) {
    this.symbol = symbol;
    this.shares = shares;
    this.purchasePrice = purchasePrice;
    this.purchaseDate = purchaseDate;
  }

  /**
   * Constructs a Stock object with the specified symbol, shares, purchase price,
   * and current date as purchase date.
   *
   * @param symbol        The symbol of the stock.
   * @param shares        The number of shares.
   * @param purchasePrice The purchase price per share.
   */
  public Stock(String symbol, double shares, double purchasePrice) {
    this.symbol = symbol;
    this.shares = shares;
    this.purchasePrice = purchasePrice;
    this.purchaseDate = getDate();
  }

  /**
   * Constructs a Stock object with the specified symbol, shares, purchase price,
   * and current date as purchase date.
   *
   * @param symbol The symbol of the stock.
   * @param shares The number of shares.
   */
  public Stock(String symbol, double shares, String purchaseDate) {
    this.symbol = symbol;
    this.shares = shares;
    this.purchaseDate = getDate();
  }

  /**
   * Constructs a Stock object with the specified symbol, shares, purchase price,
   * and current date as purchase date.
   *
   * @param symbol The symbol of the stock.
   */
  public Stock(String symbol) {
    this.symbol = symbol;
  }

  public Stock(String symbol, double shares) {
    this.symbol = symbol;
    this.shares = shares;
  }

  /**
   * Gets the symbol of the stock.
   *
   * @return The symbol of the stock.
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Gets the number of shares of the stock.
   *
   * @return The number of shares.
   */

  public double getShares() {
    return shares;
  }


  /**
   * Sets the number of shares of the stock.
   *
   * @param shares The new number of shares.
   */
  public void setShares(double shares) {
    this.shares = shares;
  }

  /**
   * Gets the purchase price per share of the stock.
   *
   * @return The purchase price per share.
   */
  public double getPurchasePrice() {
    return purchasePrice;
  }

  /**
   * sets the purchase price of the stock.
   */
  public void setPurchasePrice(double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  /**
   * Gets the purchase date of the stock.
   *
   * @return The purchase date.
   */
  public String getPurchaseDate() {
    return purchaseDate;
  }

  /**
   * gets the date in the format of yyyy-mm-dd of the stock.
   */

  private String getDate() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return dateFormat.format(new Date());
  }


}
