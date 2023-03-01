package gregoriancalendar;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.time.LocalDate;
import java.time.Month;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;

public class GregorianCalendar extends JFrame {

    static int day, month, year;
    static int currentday, currentmonth, currentyear;
    static String currentmonthname;
    static JButton next, previous, getname;
    static JLabel[] namelabel = new JLabel[7];
    static JButton[][] daysbutton = new JButton[6][7];
    static JLabel date;
    static JPanel p1, p2, p3, p4, p5, tp7;
    static JFrame frame;
    static String[] Mname = {"Null", "JANUARY", "February", "March", "April", "May", "June", "July", "Augest", "Ceptember", "October", "November", "December"};
    static JTextField nameday, namemonth, nameyear;
    static JComboBox box;

    GregorianCalendar() {

        LocalDate currentdate = LocalDate.now();

        day = currentdate.getDayOfMonth();
        Month setmonth = currentdate.getMonth();
        currentmonthname = setmonth.toString();
        month = indexOfMonth(currentmonthname);
        year = currentdate.getYear();

        currentday = day;
        currentmonth = month;
        currentyear = year;

        namelabel[0] = new JLabel("Monday");
        namelabel[1] = new JLabel("Tuesday");
        namelabel[2] = new JLabel("Wendsday");
        namelabel[3] = new JLabel("Thursday");
        namelabel[4] = new JLabel("Friday");
        namelabel[5] = new JLabel("Saturday");
        namelabel[6] = new JLabel("Sunday");

        p1 = new JPanel();
        p1.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        next = new JButton(">>");
        next.setFont(new Font("Serif", 3, 25));
        previous = new JButton("<<");
        previous.setFont(new Font("Serif", 3, 25));
        date = new JLabel("date");
        date.setFont(new Font("Serif", 2, 30));

        p1.add(date);

        p2 = new JPanel(new GridLayout(1, 7));
        p3 = new JPanel(new GridLayout(6, 7));
        p3.setBackground(Color.WHITE);
        for (int i = 0; i < 7; i++) {
            p2.add(namelabel[i]);
            namelabel[i].setFont(new Font("Serif", 1, 15));
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                daysbutton[i][j] = new JButton("");
                daysbutton[i][j].setBackground(Color.WHITE);
                daysbutton[i][j].setVisible(false);
                p3.add(daysbutton[i][j]);
            }
        }

        p4 = new JPanel(new BorderLayout());

        p4.add(p2, BorderLayout.NORTH);
        p4.add(p3, BorderLayout.CENTER);

        p5 = new JPanel(new BorderLayout());

        p5.add(previous, BorderLayout.WEST);
        p5.add(p4, BorderLayout.CENTER);
        p5.add(next, BorderLayout.EAST);

        tp7 = new JPanel(new GridLayout(4, 2));
        tp7.setBorder(new TitledBorder("Get day name Using Gregorian Calendar"));
        tp7.add(new JLabel("Day"));
        nameday = new JTextField(10);
        tp7.add(nameday);

        tp7.add(new JLabel("Month "));
        box = new JComboBox(Mname);
        box.setCursor(new Cursor(Cursor.HAND_CURSOR));

        namemonth = new JTextField(10);
        tp7.add(box);

        box.addItemListener((ItemEvent e) -> {
            String s = "";
            if (box.getSelectedIndex() == 0) {
                s = "";
            } else {
                s = String.valueOf(box.getSelectedIndex());
            }

            namemonth.setText(s);
        });

        tp7.add(new JLabel("Year "));
        nameyear = new JTextField(10);
        tp7.add(nameyear);

        tp7.add(new JLabel(" "));
        getname = new JButton("get name");
        getname.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tp7.add(getname);

        add(p1, BorderLayout.NORTH);
        add(p5, BorderLayout.CENTER);
        add(tp7, BorderLayout.SOUTH);

        getCalendar(1, month, year);

        ButtonListener handler = new ButtonListener();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                daysbutton[i][j].addActionListener(handler);
            }
        }

        previous.addActionListener((ActionEvent e) -> {
            previousMonth();
        });

        next.addActionListener((ActionEvent e) -> {
            nextMonth();
        });
        getname.addActionListener((ActionEvent e) -> {
            getGName();
        });

    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (event.getSource() == daysbutton[i][j]) {
                        int d = Integer.parseInt(daysbutton[i][j].getText());
                        String string = showG(getGName(d, month, year));
                        JOptionPane.showMessageDialog(null, string + "\n" + d + "  " + nameOfMonth(month) + " " + year);
                    }
                }
            }
        }
    }

    public static void getCalendar(int d, int m, int y) {
        int index = getGName(d, m, y);
        int firstday = 1;

        date.setText(nameOfMonth(month) + "  " + year);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && (index + 5) % 7 > j) {
                    continue;
                }
                daysbutton[i][j].setVisible(true);
                daysbutton[i][j].setText("" + firstday);

                if (currentday == firstday && currentmonth == month && currentyear == year) {
                    daysbutton[i][j].setBackground(new Color(204, 204, 255));
                }
                firstday++;
                if (firstday > getNumberOfDay(m, y)) {
                    return;
                }
            }
        }

    }

    public static String nameOfMonth(int m) {
        String monthname = "";
        if (m < 1) {
            m = 12;
        }
        switch (m) {
            case 1:
                monthname = "January";
                break;
            case 2:
                monthname = "February";
                break;
            case 3:
                monthname = "March";
                break;
            case 4:
                monthname = "April";
                break;
            case 5:
                monthname = "May";
                break;
            case 6:
                monthname = "June";
                break;
            case 7:
                monthname = "July";
                break;
            case 8:
                monthname = "Augest";
                break;
            case 9:
                monthname = "Ceptember";
                break;
            case 10:
                monthname = "October";
                break;
            case 11:
                monthname = "November";
                break;
            case 12:
                monthname = "December";
                break;
        }
        return monthname;
    }

    public static void main(String[] args) {

        frame = new GregorianCalendar();

        frame.setSize(650, 500);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void getGName() {

        if (nameday.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "please enter day", "Warning", JOptionPane.ERROR_MESSAGE);
            nameday.requestFocusInWindow();
        } else if (namemonth.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "please enter month", "Warning", JOptionPane.ERROR_MESSAGE);
            namemonth.requestFocusInWindow();
        } else if (nameyear.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "please enter year", "Warrning", JOptionPane.ERROR_MESSAGE);
            nameyear.requestFocusInWindow();
        } else {
            try {

                int iday, imonth, iyear;
                iday = Integer.parseInt(nameday.getText());
                imonth = Integer.parseInt(namemonth.getText());
                iyear = Integer.parseInt(nameyear.getText());

                if (iyear < 1) {
                    JOptionPane.showMessageDialog(null, "invalid year", "Warrning", JOptionPane.ERROR_MESSAGE);
                    nameyear.requestFocusInWindow();
                } else if ((imonth == 1 || imonth == 3 || imonth == 5 || imonth == 7 || imonth == 8 || imonth == 10 || imonth == 12)) {
                    if (iday > 0 && iday < 32) {
                        JOptionPane.showMessageDialog(null, showG(getGName(iday, imonth, iyear)), "", JOptionPane.INFORMATION_MESSAGE);
                        clearField();
                    } else {
                        JOptionPane.showMessageDialog(null, "invalid day", "Warrning", JOptionPane.ERROR_MESSAGE);
                        nameday.requestFocusInWindow();
                    }
                } else if (imonth == 4 || imonth == 6 || imonth == 9 || imonth == 11) {
                    if (iday > 0 && iday < 31) {
                        JOptionPane.showMessageDialog(null, showG(getGName(iday, imonth, iyear)), "", JOptionPane.INFORMATION_MESSAGE);
                        clearField();
                    } else {
                        JOptionPane.showMessageDialog(null, "invalid day", "Warrning", JOptionPane.ERROR_MESSAGE);
                        nameday.requestFocusInWindow();
                    }
                } else if (imonth == 2 && (iyear % 400 == 0 || (iyear % 4 == 0 && iyear % 100 != 0))) {
                    if (iday > 0 && iday < 30) {
                        JOptionPane.showMessageDialog(null, showG(getGName(iday, imonth, iyear)), "", JOptionPane.INFORMATION_MESSAGE);
                        clearField();
                    } else {
                        JOptionPane.showMessageDialog(null, "invalid day", "Warrning", JOptionPane.ERROR_MESSAGE);
                        nameday.requestFocusInWindow();
                    }
                } else if (imonth == 2) {
                    if (iday > 0 && iday < 29) {
                        JOptionPane.showMessageDialog(null, showG(getGName(iday, imonth, iyear)), "", JOptionPane.INFORMATION_MESSAGE);
                        clearField();
                    } else {
                        JOptionPane.showMessageDialog(null, "invalid day", "Warrning", JOptionPane.ERROR_MESSAGE);
                        nameday.requestFocusInWindow();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "invalid invalid month", "Warrning", JOptionPane.ERROR_MESSAGE);

                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "invalid input", "Warrning", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static int getGName(int d, int m, int y) {
        int a1[] = {d, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int a2[] = {d, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int sum = 0;

        if (y % 400 == 0 || (y % 4 == 0 && y % 100 != 0)) {
            for (int i = 0; i < m; i++) {
                sum += a1[i];
            }
        } else {
            for (int i = 0; i < m; i++) {
                sum += a2[i];
            }
        }
        y--;
        y += sum + (y / 4);
        return (y % 7);
    }

    public static String showG(int c) {
        String dayname = "";
        switch (c) {
            case 2:
                dayname = "MONDAY";
                break;
            case 3:
                dayname = "TUESDAY";
                break;
            case 4:
                dayname = "WEDNESDAY";
                break;
            case 5:
                dayname = "THURSDAY";
                break;
            case 6:
                dayname = "FRIDAY";
                break;
            case 0:
                dayname = "SATURDAY";
                break;
            case 1:
                dayname = "SUNDAY";
                break;
        }

        return dayname;
    }

    public static int indexOfMonth(String s) {
        int monthindex = 0;
        switch (s) {
            case "JANUARY":
                monthindex = 1;
                break;
            case "FEBRUARY":
                monthindex = 2;
                break;
            case "MARCH":
                monthindex = 3;
                break;
            case "APRIL":
                monthindex = 4;
                break;
            case "MAY":
                monthindex = 5;
                break;
            case "JUNE":
                monthindex = 6;
                break;
            case "JULY":
                monthindex = 7;
                break;
            case "AUGEST":
                monthindex = 8;
                break;
            case "CEPTEMBER":
                monthindex = 9;
                break;
            case "OCTOBER":
                monthindex = 10;
                break;
            case "NOVEMBER":
                monthindex = 11;
                break;
            case "DECEMBER":
                monthindex = 12;
                break;
        }
        return monthindex;
    }

    public static int getNumberOfDay(int m, int y) {
        int days = 0;

        if (m < 1) {
            m = 12;
            y--;
        }
        if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
            days = 31;
        } else if (m == 4 || m == 6 || m == 9 || m == 11) {
            days = 30;
        } else if (((y % 100 != 0 && y % 4 == 0) || (y % 400 == 0)) && m == 2) {
            days = 29;
        } else {
            days = 28;
        }
        return days;
    }

    public static void nextMonth() {
        month++;
        day = 1;
        if (month > 12) {
            month = 1;
            year++;
        }
        clearButton();
        getCalendar(1, month, year);
    }

    public static void previousMonth() {
        month--;
        day = 1;
        if (month < 1) {
            month = 12;
            year--;
        }
        if (year < 1) {
            year = 1;
        }
        clearButton();
        getCalendar(day, month, year);
    }

    public static void clearButton() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                daysbutton[i][j].setText("");
                daysbutton[i][j].setBackground(Color.WHITE);
                daysbutton[i][j].setVisible(false);
            }
        }
    }

    public static void clearField() {
        nameday.setText("");
        namemonth.setText("");
        nameyear.setText("");
        box.setSelectedIndex(0);

    }

}
