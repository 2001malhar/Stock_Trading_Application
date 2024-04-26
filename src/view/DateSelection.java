package view;

import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * A panel for selecting a date using combo boxes for year, month, and day.
 */
public class DateSelection extends JPanel implements DateSelectionInt {

  private JComboBox<Integer> year;
  private JComboBox<Integer> month;
  private JComboBox<Integer> day;

  /**
   * Constructs a DateSelection panel with combo boxes for year, month, and day.
   */
  public DateSelection() {
    setLayout(new GridLayout(1, 3));

    year = new JComboBox<>();
    for (int year = 2000; year <= 2030; year++) {
      this.year.addItem(year);
    }
    add(year);

    month = new JComboBox<>();
    for (int month = 1; month <= 12; month++) {
      this.month.addItem(month);
    }
    add(month);

    day = new JComboBox<>();
    updateDay();
    add(day);

    year.addItemListener(e -> updateDay());
    month.addItemListener(e -> updateDay());
  }


  /**
   * Updates the day combo box based on the selected year and month.
   */
  private void updateDay() {
    int year = (int) this.year.getSelectedItem();
    int month = (int) this.month.getSelectedItem();
    int maxDays = YearMonth.of(year, month).lengthOfMonth();

    day.removeAllItems();
    for (int day = 1; day <= maxDays; day++) {
      this.day.addItem(day);
    }
  }

  /**
   * Gets the selected date as a string in the format "yyyy-MM-dd".
   *
   * @return The selected date as a string.
   */
  public String getDateAsString() {
    int year = (int) this.year.getSelectedItem();
    int month = (int) this.month.getSelectedItem();
    int day = (int) this.day.getSelectedItem();

    LocalDate date = LocalDate.of(year, month, day);
    return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
  }
}