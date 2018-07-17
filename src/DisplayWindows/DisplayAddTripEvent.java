package DisplayWindows;

import DateRelated.BuildObjectException;
import DateRelated.CalendarDate;
import DateRelated.DateUtil;
import DateRelated.LocalTime;
import Event.TripEvent;
import Event.Event;
import Event.EventUtil;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ZZY on 2018/5/19.
 */
public class DisplayAddTripEvent extends DisplayAddEvent {
    private JTextField transportationField = DisplayUtil.getTextField();
    private JTextField transNumberField = DisplayUtil.getTextField();
    private JTextField destinationField = DisplayUtil.getTextField();

    public DisplayAddTripEvent(CalendarDate date, Event superEvent, Display display,DisplayEventDetails detailsDisplay) {
        super(date, superEvent, display,detailsDisplay);
        transNumberField.setText("未知");
        destinationField.setText("未知");
    }

    private String getTransportation() {
        return this.transportationField.getText();
    }

    private String getTransNumber() {
        return this.transNumberField.getText();
    }

    private String getDestination() {
        return this.destinationField.getText();
    }

    private JPanel getAdditionalAttributes() {
        JLabel transportationLabel = DisplayUtil.getLabel("交通方式：", true);
        JLabel transNumberLabel = DisplayUtil.getLabel("车次/航班号：", true);
        JLabel destinationLabel = DisplayUtil.getLabel("地点：", true);
        JPanel transportationPanel = new JPanel();
        transportationPanel.setOpaque(false);
        transportationField.setEditable(false);
        JMenu wayBar = new JMenu("方式👇");
        wayBar.setOpaque(false);
        wayBar.setSize(new Dimension(300,300));
        wayBar.setFont(new Font("Serif", 0, 20));
        JMenuItem way1 = new JMenuItem("飞机");
        JMenuItem way2 = new JMenuItem("火车");
        JMenuItem way3 = new JMenuItem("大巴");
        way1.setFont(new Font("Serif", 0, 18));
        way2.setFont(new Font("Serif", 0, 18));
        way3.setFont(new Font("Serif", 0, 18));
        wayBar.add(way1);
        wayBar.add(way2);
        wayBar.add(way3);
        way1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transportationField.setText("飞机");
            }
        });
        way2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transportationField.setText("火车");
            }
        });
        way3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transportationField.setText("大巴");
            }
        });
        JMenuBar transportationBar = new JMenuBar();
        transportationBar.setOpaque(false);
        transportationBar.add(wayBar);
        transportationPanel.add(transportationLabel);
        transportationPanel.add(transportationField);
        transportationPanel.add(transportationBar);
        transportationPanel.add(transNumberLabel);
        transportationPanel.add(transNumberField);
        JPanel destinationPanel = new JPanel();
        destinationPanel.setOpaque(false);
        destinationPanel.add(destinationLabel);
        destinationPanel.add(destinationField);

        JButton saveEvent = DisplayUtil.getFormalBt("保存");
        saveEvent.addActionListener(saveActionListener());
        destinationPanel.add(saveEvent);
        JPanel jPanel = new JPanel();
        jPanel.add(transportationPanel);
        jPanel.setOpaque(false);
        jPanel.add(destinationPanel);
        jPanel.setLayout(new GridLayout(2,2));
        return jPanel;
    }

    protected JPanel getEventPanel() {
        //String eventString = oldEvent==null ? "请输入旅程事项的备注":oldEvent.getEventString();
        String eventString = "请输入旅程的事项备注";
        eventTextarea = new JTextArea(eventString, 6, 20);
        eventTextarea.setFont(new Font("Serif", 0, 20));
        eventTextarea.setLineWrap(true);
        eventTextarea.setWrapStyleWord(true);


        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.X_AXIS));

        //新添加
        JPanel trip = getAdditionalAttributes();
        panelHeader.add(trip);
        panelHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelHeader.setOpaque(false);
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
        return jPanel;
    }

    protected TripEvent getEvent() throws BuildObjectException {
        String eventString = getEventString();
        if (eventString == null || eventString.length() == 0) {
            JOptionPane.showMessageDialog(null,
                    " 请检查你的事件描述是否非空 ", " 日历", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String destinationString = getDestination();
        String transportationString = getTransportation();
        String transNumberString = getTransNumber();
        if (destinationString.length() == 0 || transportationString.length() == 0 || transNumberString.length() == 0) {
            JOptionPane.showMessageDialog(null,
                    " 请检查你的地点，班次/航班号，交通方式是否为空 ", " 日历", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        TripEvent event;
        if (timePanel.getIsWholeDay())
            event = new TripEvent(eventString, timePanel.getBeginDate(), transportationString, destinationString, transNumberString, getIfEmergency(), getIfImportant());
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
            event = new TripEvent(eventString, localTimes[0], localTimes[1], transportationString, destinationString, transNumberString, getIfEmergency(), getIfImportant());
        }
        return event;
    }

}
