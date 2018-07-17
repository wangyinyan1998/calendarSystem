package DisplayWindows;

import DateRelated.BuildObjectException;
import DateRelated.CalendarDate;
import DateRelated.DateUtil;
import DateRelated.LocalTime;
import Event.Event;
import Event.DateEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Think on 2018/5/6.
 */
public class DisplayAddDateEvent extends DisplayAddEvent {
    private JTextField placeFiled = DisplayUtil.getTextField();
    private JTextField attendPeopleField = DisplayUtil.getTextField();

    public DisplayAddDateEvent(CalendarDate date, Event oldEvent, Display display,DisplayEventDetails detailsDisplay) {
        super(date, oldEvent, display,detailsDisplay);
        placeFiled.setText("未知");
        attendPeopleField.setText("未知");
    }

    private String getPlace() {
        return this.placeFiled.getText();
    }

    private String getTheme() {
        return this.attendPeopleField.getText();
    }


    //王佳惠新添的方法，用来产生地点和会议主题
    private JPanel getPlaceAndTheme() {
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        JLabel placeLabel = DisplayUtil.getLabel("地点：", true);
        JLabel themeLabel = DisplayUtil.getLabel("参与人员：", true);
        jPanel.add(placeLabel);
        jPanel.add(placeFiled);
        jPanel.add(themeLabel);
        jPanel.add(attendPeopleField);
        return jPanel;
    }

    @Override
    protected JPanel getEventPanel() {
        String eventString = "请输入约会的详细信息：";
        eventTextarea = new JTextArea(eventString, 6, 20);
        eventTextarea.setFont(new Font("Serif", 0, 20));
        eventTextarea.setLineWrap(true);
        eventTextarea.setWrapStyleWord(true);
        JButton saveEvent = DisplayUtil.getFormalBt("保存");
        saveEvent.addActionListener(saveActionListener());
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.X_AXIS));

        //新添加
        JPanel meetingCom = getPlaceAndTheme();
        panelHeader.add(meetingCom);
        panelHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelHeader.setOpaque(false);
        panelHeader.add(saveEvent);
        panelHeader.setSize(500, 80);

        JPanel jPanel = new NewPanel("cat.jpg");
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(panelHeader);
        JPanel getTimeJPanel = timePanel.getTimePanel();
        getTimeJPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        jPanel.add(getTimeJPanel);
        jPanel.add(getEmImJPanel());
        eventTextarea.setMaximumSize(new Dimension(700,250));
        jPanel.add(eventTextarea);
        jPanel.setOpaque(false);
        return jPanel;
    }


    @Override
    protected DateEvent getEvent() throws BuildObjectException {
        String eventString = getEventString();
        if (eventString == null || eventString.length() == 0) {
            JOptionPane.showMessageDialog(null,
                    " 请检查你的事件描述是否非空 ", " 日历", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String placeString = getPlace();
        String attendPeopleString = getTheme();
        if (placeString.length() == 0 || attendPeopleString.length() == 0) {
            JOptionPane.showMessageDialog(null,
                    " 请检查你的地点和参与人员是否为空 ", " 日历", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        DateEvent event;
        if (timePanel.getIsWholeDay())
            event = new DateEvent(eventString, timePanel.getBeginDate(), placeString, attendPeopleString, getIfEmergency(), getIfImportant());
        else {
            LocalTime[] localTimes = timePanel.getLocalTimeFromJPanel();
            if (localTimes == null || localTimes[1].compareTo(localTimes[0]) > 0) {
                JOptionPane.showMessageDialog(null,
                        " 请检查你的时间段是否合法！ ", " 日历", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            if (localTimes[1].compareToDate(DateUtil.getToday()) > 0) {
                JOptionPane.showMessageDialog(null,
                        "过去的时间已经过去了，把要做的事情放在当下或未来吧！", " 日历", JOptionPane.ERROR_MESSAGE);
                return null;

            }
            event = new DateEvent(eventString, localTimes[0], localTimes[1], placeString, attendPeopleString, getIfEmergency(), getIfImportant());
        }
        return event;
    }

}
