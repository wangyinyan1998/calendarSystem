package DisplayWindows;

import DateRelated.CalendarDate;
import DateRelated.DateUtil;
import HolidayRelated.Holidays;

import javax.swing.*;
import java.awt.*;

/**
 * 以下为界面组件的统一风格
 * 在lab4中对它进行了大部分更改
 */
public class DisplayUtil {
    private static final int DAY_TYPE = 1;
    private static final int HAVE_EVENT_TYPE = 2;
    private static final int NOT_THIS_MONTH_TYPE = 3;
    private static final int PAST_EVENT_TYPE = 4;
    private static final int TODAY_RELATED = 5;
    private static final int CLICK_RELATED = 6;
    static Holidays holidays = new Holidays();

    protected static JButton getFormalBt(String btText) {
        JButton jbutton = new JButton(btText);
        jbutton.setForeground(Color.black);
        jbutton.setOpaque(false);
        // jbutton.setBackground(Color.gray);
        jbutton.setBackground(new Color(1, 1, 1, 100));
        jbutton.setMargin(new Insets(2, 2, 2, 2));
        jbutton.setBorder(BorderFactory.createEtchedBorder());
        jbutton.setPreferredSize(new Dimension(120, 40));
        jbutton.setFont(new Font("Serif", 0, 20));
        jbutton.setMaximumSize(new Dimension(100, 40));
        return jbutton;
    }

    /*对界面上的button画样式*/
    protected static JButton getButton(CalendarDate date) {
        int type = getBtDayType(date);
        JButton dayButton = getDateFormBt(date);
        switch (type) {
            case HAVE_EVENT_TYPE:
                dayButton.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(200, 10, 20)));
                break;
            case PAST_EVENT_TYPE:
                dayButton.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(80, 5, 20)));
                break;
            case TODAY_RELATED:
                return getTodayRelatedBt(date);
            case CLICK_RELATED:
                return getClickRelatedBt(date);
            case NOT_THIS_MONTH_TYPE:
                dayButton.setForeground(Color.darkGray);
                break;

        }
        return dayButton;
    }

    //获取日期类型的方法
    private static int getBtDayType(CalendarDate dateTmp) {
        if (dateTmp.getMonth() != Display.getClickedDate().getMonth())
            return NOT_THIS_MONTH_TYPE;
        boolean haveEvent = dateTmp.ifHaveEvent();
        if (dateTmp.equals(DateUtil.getToday()))
            return TODAY_RELATED;
        if (dateTmp.equals(Display.getClickedDate()))
            return DisplayUtil.CLICK_RELATED;
        if (dateTmp.compareTo(DateUtil.getToday()) > 0 && haveEvent)
            return DisplayUtil.PAST_EVENT_TYPE;
        if (haveEvent)
            return DisplayUtil.HAVE_EVENT_TYPE;
        return DisplayUtil.DAY_TYPE;
    }

    //返回与点击日期相关的button
    private static JButton getClickRelatedBt(CalendarDate date) {
        JButton dayButton = getDateFormBt(date);
        dayButton.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(10, 160, 100)));
        return dayButton;
    }

    //返回与今天日期相关的button
    private static JButton getTodayRelatedBt(CalendarDate date) {
        JButton dayButton = getDateFormBt(date);
        dayButton.setContentAreaFilled(true);
        dayButton.setBackground(Color.BLUE);
        dayButton.setForeground(Color.BLUE);
        dayButton.setFont(new Font("Algerian", 0, 25));
        if (date.ifHaveEvent()) {
            dayButton.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(200, 10, 20)));
        }
        return dayButton;
    }

    //返回日期button的初始化类型，并设置日期button上的日期信息
    private static JButton getDateFormBt(CalendarDate date) {
        boolean isDuringHoliday = holidays.isDateDuringHoliday(date);
        boolean isHolidayTime = holidays.isDateHolidayTime(date);
        JButton dayButton;
        dayButton = getFormalBt(" " + date.getDay() + " ");
        if (holidays.isWorkday(date)) {
            dayButton = getFormalBt("<html><span style=\"font-size:80%\">工作<br>&nbsp;" + date.getDay() + "</span></html>");
        }
        if (isDuringHoliday)
            dayButton = getFormalBt("<html><span style=\"font-size:80%\">&nbsp;休<br>&nbsp;" + date.getDay() + "</span></html>");
        if (isHolidayTime)
            dayButton = getFormalBt("<html><span style=\"font-size:70%\">&nbsp;&nbsp;休<br>&nbsp;&nbsp;" + date.getDay() +
                    "<br>" + holidays.getHoliday(date).getHolidayZHName() + "</span></html>");
        dayButton.setPreferredSize(new Dimension(60, 70));
        dayButton.setFont(new Font("Algerian", 0, 20));

        return dayButton;
    }

    /*画面板上的下拉框组件，并设置样式*/
    protected static JComboBox getSearchComboBox() {
        JComboBox comboBox = new JComboBox();
        comboBox.setOpaque(false);
        comboBox.setFont(new Font("Serif", 0, 20));
        comboBox.setMaximumSize(new Dimension(90, 50));
        return comboBox;
    }

    /*画面板上的TextField组件，并设置样式*/
    protected static JTextField getTextField() {
        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(200, 50));
        textField.setFont(new Font("Serif", 0, 20));
        textField.setPreferredSize(new Dimension(90, 30));
        return textField;
    }

    /*画面板上的Label组件，并设置样式*/
    protected static JLabel getLabel(String labelText, boolean isBlack) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.white);
        if (isBlack)
            label.setForeground(Color.black);
        label.setFont(new Font("Serif", 0, 20));
        return label;
    }

    //这是用于显示事件详情的textarea，放在DisplayUtil中作为工具方法
    protected static JTextArea getTextarea(String text) {
        JTextArea jTextArea = new JTextArea(text);
        jTextArea.setLineWrap(true);
        jTextArea.setMaximumSize(new Dimension(660, 150));
        jTextArea.setFont(new Font("Serif", 0, 20));
        jTextArea.setEditable(false);
        return jTextArea;
    }
}
