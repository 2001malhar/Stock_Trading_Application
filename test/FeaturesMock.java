import java.util.Collections;
import java.util.List;
import models.FeaturesInt;

/**
 * This is the mock model of the features class.
 */
public class FeaturesMock implements FeaturesInt {

  @Override
  public String determineGainOrLoss(String symbol, String date) {
    return "Gain or Loss";
  }

  @Override
  public String getPerformanceOverNDays(String symbol, String startDate, String endDate) {
    return "Gain or Loss 1";
  }

  @Override
  public double calculateXDayMovingAverage(String symbol, String startDate, int numberOfDays) {
    return 0.01;
  }

  @Override
  public List<String> detectCrossovers(String symbol, String startDate, String endDate) {
    return Collections.singletonList("Gain or Loss");
  }

  @Override
  public List<String> movingCrossOvers(String symbol, String startDate, String endDate, int xDays,
      int yDays) {
    return Collections.singletonList("Gain or Loss1");
  }
}
