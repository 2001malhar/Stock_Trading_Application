package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Features class represents all the features which contains methods for analysis of stocks.
 */
public class Features implements FeaturesInt {

  /**
   * This method is used to determine the performance of stock on a specific date.
   *
   * @param symbol the symbol of stock.
   * @param date   the date on which the performance is required.
   * @return the performance.
   */
  public String determineGainOrLoss(String symbol, String date) {
    String folderPath = "Historical" + File.separator;
    String filePath = folderPath + symbol + ".csv";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      reader.readLine();
      String line;
      boolean foundDate = false;
      while ((line = reader.readLine()) != null) {
        String[] values = line.split(",");
        String currentDate = values[0].trim();

        if (currentDate.equals(date)) {
          double openPrice = Double.parseDouble(values[1].trim());
          double closePrice = Double.parseDouble(values[4].trim());

          if (closePrice > openPrice) {
            double diff = closePrice - openPrice;
            return "Date: " + date + ", Opening Price: " + openPrice + ", " +
                "Closing Price: " + closePrice + ", Gain: " + diff + "\n";
          } else if (closePrice < openPrice) {
            double diff = openPrice - closePrice;
            return "Date: " + date + ", Opening Price: " + openPrice + "," +
                " Closing Price: " + closePrice + ", Loss: " + diff + "\n";
          } else {
            return "Date: " + date + ", Opening Price: " + openPrice + ", " +
                "Closing Price: " + closePrice + ", No Change" + "\n";
          }
        }
      }

      double[] previousPrices = lastWorkingDay(date, filePath);
      if (previousPrices != null) {
        double previousClosePrice = previousPrices[1];
        double previousOpenPrice = previousPrices[0];

        if (previousClosePrice > previousOpenPrice) {
          double diff = previousClosePrice - previousOpenPrice;
          return " Day - Opening Price: " + previousOpenPrice + "," +
              " Closing Price: " + previousClosePrice + ", Gain: " + diff + "\n";
        } else if (previousClosePrice < previousOpenPrice) {
          double diff = previousOpenPrice - previousClosePrice;
          return " Day - Opening Price: " + previousOpenPrice + "," +
              " Closing Price: " + previousClosePrice + ", Loss: " + diff + "\n";
        } else {
          return " Day - Opening Price: " + previousOpenPrice + ", " +
              "Closing Price: " + previousClosePrice + ", No Change\n";
        }
      } else {
        return "Error: Specified date not found in the data.\n";
      }

    } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
      return "Error determining gain or loss: \n" + e.getMessage();
    }
  }

  double[] lastWorkingDay(String date, String filePath) {
    LocalDate prevDay = LocalDate.parse(date).minusDays(1);
    while (!prevDay.isBefore(LocalDate.parse("1950-10-15"))) {
      String previousDateString = prevDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        reader.readLine();

        String line;
        while ((line = reader.readLine()) != null) {
          String[] values = line.split(",");
          String currentDate = values[0].trim();
          if (currentDate.equals(previousDateString)) {
            double[] prevPrice = {Double.parseDouble(values[1].trim()),
                Double.parseDouble(values[4].trim())};
            return prevPrice;
          }
        }
      } catch (IOException | NumberFormatException e) {
        System.out.println("Error finding previous  data:\n " + e.getMessage());
      }
      prevDay = prevDay.minusDays(1);
    }
    return null;
  }

  /**
   * Method is used to get the performance of the stock over between 2 dates.
   *
   * @param symbol    symbol of stock.
   * @param startDate start date of the period.
   * @param endDate   end date of the period.
   * @return performance over the time.
   */
  public String getPerformanceOverNDays(String symbol, String startDate, String endDate) {
    String folderPath = "Historical" + File.separator;
    String filePath = folderPath + symbol + ".csv";
    double startClosingPrice = 0.0;
    double endClosingPrice = 0.0;
    boolean foundStart = false;
    boolean foundEnd = false;

    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      reader.readLine();

      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      return "Error reading the file: " + e.getMessage();
    }

    for (int i = 0; i < lines.size(); i++) {
      String storedLine = lines.get(i);
      String[] values = storedLine.split(",");
      String currentDate = values[0].trim();

      if (currentDate.equals(startDate)) {
        startClosingPrice = Double.parseDouble(values[4].trim());
        foundStart = true;
      } else if (currentDate.equals(endDate)) {
        endClosingPrice = Double.parseDouble(values[4].trim());
        foundEnd = true;
      }

      if (foundStart && foundEnd) {
        break;
      }
    }

    if (!foundStart) {
      startClosingPrice = PortfolioModel.previousDay(startDate, lines);
      foundStart = true;
    }

    if (!foundEnd) {
      endClosingPrice = PortfolioModel.previousDay(endDate, lines);
      foundEnd = true;
    }

    if (foundStart && foundEnd) {
      if (endClosingPrice < startClosingPrice) {
        Double gain = startClosingPrice - endClosingPrice;
        return "Gain by: " + gain + "\n";
      } else if (endClosingPrice > startClosingPrice) {
        Double loss = endClosingPrice - startClosingPrice;
        return "Loss by: " + loss + "\n";
      } else {
        return "No Change\n";
      }
    }
    return null;
  }


  /**
   * Method used to calculate the average of X days.
   *
   * @param symbol       symbol of stock.
   * @param startDate    start date from which the performance is needed.
   * @param numberOfDays number of days.
   * @return the average of the days.
   */
  @Override
  public double calculateXDayMovingAverage(String symbol,
      String startDate, int numberOfDays) {
    String folderPath = "Historical" + File.separator;
    String filePath = folderPath + symbol + ".csv";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      reader.readLine();

      LocalDate parsedStartDate = LocalDate.parse(startDate);
      double sum = 0.0;
      int count = 0;
      boolean started = false;

      String line;
      while ((line = reader.readLine()) != null) {
        String[] values = line.split(",");
        String dateString = values[0].trim();
        LocalDate currentDate = LocalDate.parse(dateString);
        double closingPrice = Double.parseDouble(values[4].trim());

        if (currentDate.isEqual(parsedStartDate)) {
          started = true;
        }

        if (started) {
          sum += closingPrice;
          count++;
          if (count == numberOfDays) {
            break;
          }
        }
      }

      if (count == numberOfDays) {
        return sum / numberOfDays;
      } else {
        System.err.println("Insufficient data to calculate the x-day moving average.\n");
        return 0.0;
      }
    } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
      System.err.println("Error calculating x-day moving average: \n" + e.getMessage());
      return 0.0;
    }
  }

  /**
   * Used to detect the crossovers of the specific period.
   *
   * @param symbol    symbol of the stock.
   * @param startDate the start date from which we want crossovers.
   * @param endDate   the end date to which crossovers in needed.
   * @return List of the crossovers.
   */
  @Override
  public List<String> detectCrossovers(String symbol, String startDate, String endDate) {
    List<String> crossoverOpportunities = new ArrayList<>();

    String folderPath = "Historical" + File.separator;
    String filePath = folderPath + symbol + ".csv";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      reader.readLine();

      LocalDate parsedStart = LocalDate.parse(startDate);
      LocalDate parsedEnd = LocalDate.parse(endDate);

      String line;
      while ((line = reader.readLine()) != null) {
        String[] values = line.split(",");
        String dateString = values[0].trim();
        LocalDate currentDate = LocalDate.parse(dateString);

        if (!currentDate.isBefore(parsedStart) && !currentDate.isAfter(parsedEnd)) {
          try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath))) {
            String line1;
            reader1.readLine();
            while ((line1 = reader1.readLine()) != null) {
              String[] values1 = line1.split(",");
              String dateString1 = values1[0].trim();
              LocalDate currentDate1 = LocalDate.parse(dateString1);
              if (currentDate1.isEqual(currentDate)) {
                double closingPrice = Double.parseDouble(values[4].trim());
                String crossoverOpportunity = getCrossover(symbol, currentDate, closingPrice);
                crossoverOpportunities.add(crossoverOpportunity);
              } else if (currentDate1.isBefore(parsedStart)) {
                break;
              }
            }
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        } else if (currentDate.isBefore(parsedStart)) {
          break;
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return crossoverOpportunities;
  }

  /**
   * Method used to get the crossover of the stock on a specific date.
   *
   * @param symbol       symbol of the stock.
   * @param date         the date on which the crossover is needed.
   * @param closingPrice the closing price of the stock.
   * @return the crossover.
   */
  private String getCrossover(String symbol, LocalDate date, double closingPrice)
      throws FileNotFoundException {

    String folderPath = "Historical" + File.separator;
    String filePath = folderPath + symbol + ".csv";
    double value = 0.0;
    double average = calculateXDayMovingAverage(symbol, date.toString(), 30);
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      reader.readLine();

      String line;
      while ((line = reader.readLine()) != null) {
        String[] values = line.split(",");
        String dateString = values[0].trim();
        LocalDate currentDate = LocalDate.parse(dateString);

        if (currentDate.isBefore(date)) {
          value = Double.parseDouble(values[4].trim());
          break;
        }
      }

      if (average > closingPrice && average < value) {
        return "Crossover on " + date + " shows Sell opportunity\n";
      } else if (average < closingPrice && average > value) {
        return "Crossover on " + date + " shows Buy opportunity\n";
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  /**
   * Method used to calculate moving crossovers.
   *
   * @param symbol    symbol of the stock.
   * @param startDate the start date from which we want crossovers.
   * @param endDate   the end date to which crossovers in needed.
   * @param xDays     the days of which average is needed.
   * @param yDays     the days of which average is needed.
   * @return List of crossovers.
   */
  @Override
  public List<String> movingCrossOvers(String symbol, String startDate, String endDate, int xDays,
      int yDays) {
    List<String> crossoverOpportunities = new ArrayList<>();

    String folderPath = "Historical" + File.separator;
    String filePath = folderPath + symbol + ".csv";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      LocalDate parsedStart = LocalDate.parse(startDate);
      LocalDate parsedEnd = LocalDate.parse(endDate);
      reader.readLine();

      String line;
      while ((line = reader.readLine()) != null) {
        String[] values = line.split(",");
        String dateString = values[0].trim();
        LocalDate currentDate = LocalDate.parse(dateString);

        if (!currentDate.isBefore(parsedStart) && !currentDate.isAfter(parsedEnd)) {
          double xDaysAverage = calculateXDayMovingAverage(symbol, currentDate.toString(), xDays);
          double yDaysAverage = calculateXDayMovingAverage(symbol, currentDate.toString(), yDays);

          if (xDaysAverage > yDaysAverage) {
            crossoverOpportunities.add("Buy opportunity on " + currentDate + "\n");
          } else if (xDaysAverage < yDaysAverage) {
            crossoverOpportunities.add("Sell opportunity on " + currentDate + "\n");
          }
        } else if (currentDate.isBefore(parsedStart)) {
          break;
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return crossoverOpportunities;
  }
}
