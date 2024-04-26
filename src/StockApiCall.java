import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * StockApiCall class is used for making the api calls for the stocks.
 */
public class StockApiCall implements StockApiCallInt {

  private String apiKey;
  private File timestampFile;

  /**
   * Constructor for the apicall initializing the apikey and the timestamp file.
   */
  public StockApiCall() {
    this.apiKey = "WX5GQNRSWDWX1NZT";
    this.timestampFile = new File("apiTime.txt");
  }

  private static void delay(int milliseconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    } catch (InterruptedException e) {

      e.printStackTrace();
    }
  }

  /**
   * Method for getting the historical data of the ticker symbol provided to it.
   *
   * @param stockSymbols snp ticker symbols .
   * @param folderPath   folder path where they will be stored.
   */
  public void getPortfolioHistoricalData(List<String> stockSymbols, String folderPath) {
    if (!folderPath.endsWith(File.separator)) {
      folderPath = folderPath + File.separator;
    }

    if (folderPath == null) {
      throw new IllegalArgumentException("Proper path not provided");
    }

    long currentTime = System.currentTimeMillis();
    long lastApiCallTime = readLastApiCallTimestamp();
    if (currentTime - lastApiCallTime >= TimeUnit.HOURS.toMillis(24)) {
      int numberOfStocks = stockSymbols.size();
      for (int i = 0; i < numberOfStocks; i++) {
        String stockSymbol = stockSymbols.get(i);
        String outputFilePath = folderPath + stockSymbol + ".csv";

        try (FileWriter writer = new FileWriter(outputFilePath, false)) {
          delay(200);
          String stockData = fetchStockData(stockSymbol);
          writer.append(stockData);
          writer.append("\n");
          System.out.println("Historical data of ticker stored as an individual file at: "
                  + outputFilePath);
        } catch (IOException e) {
          System.out.println("Error while saving the file: " + e.getMessage());
        }
      }

      writeLastApiCallTimestamp(currentTime);
    } else {
      System.out.println("Skipping API call - latest data already fetch.");
      System.out.print("<--------------------------------------------------------------------->\n");
    }
  }

  private String fetchStockData(String stockSymbol) {
    URL url;
    StringBuilder output = new StringBuilder();
    try {
      url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full" + "&symbol=" + stockSymbol + "&apikey="
              + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("The AlphaVantage API doesn't work...");
    }

    try (InputStream in = url.openStream()) {
      Scanner scanner = new Scanner(in);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.startsWith("{") || line.isEmpty()) {
          continue;
        }
        output.append(line).append("\n");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Data is not available for " + stockSymbol);
    }

    return output.toString();
  }

  private long readLastApiCallTimestamp() {
    try {
      if (timestampFile.exists()) {
        Scanner scanner = new Scanner(timestampFile);
        return scanner.nextLong();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private void writeLastApiCallTimestamp(long timestamp) {
    try (PrintWriter writer = new PrintWriter(timestampFile)) {
      writer.print(timestamp);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

