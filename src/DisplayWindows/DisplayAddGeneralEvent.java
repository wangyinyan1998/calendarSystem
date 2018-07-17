package DisplayWindows;

import DateRelated.BuildObjectException;
import DateRelated.CalendarDate;
import DateRelated.DateUtil;
import DateRelated.LocalTime;
import Event.GeneralEvent;
import Event.Event;
import Event.EventUtil;
import DisplayWindows.DisplayTimePanel;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayAddGeneralEvent extends DisplayAddEvent {
    JCheckBox withoutSpecificTime = new JCheckBox("<html>不确定<br>结束时间</html>");//ZZY

    public DisplayAddGeneralEvent(CalendarDate date, Event parentEvent, Display display,DisplayEventDetails detailsDisplay) {
        super(date, parentEvent, display,detailsDisplay);
    }
    /**
     * 以下为添加事件的面板设计
     */
    protected JPanel getEventPanel() {
        eventTextarea.setFont(new Font("Serif", 0, 20));
        eventTextarea.setLineWrap(true);
        eventTextarea.setWrapStyleWord(true);
        JButton saveEvent = DisplayUtil.getFormalBt("保存");
        saveEvent.addActionListener(saveActionListener());

        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.X_AXIS));
        panelHeader.add(saveEvent);
        panelHeader.setOpaque(false);
        JPanel jPanel = new NewPanel("cat.jpg");
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(panelHeader);
        JPanel getTimeJPanel = timePanel.getTimePanel();
        if(parentEvent != null){
            withoutSpecificTime.setVisible(false);
        }
        withoutSpecificTime.setOpaque(false);
        withoutSpecificTime.setFont(new Font("Serif", 0, 14));
        withoutSpecificTime.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(withoutSpecificTime.isSelected()){
                    timePanel.setIsWholeDay(true,false);
                }
                else {
                    timePanel.setIsWholeDay(false,true);
                }
            }
        });
        getTimeJPanel.add(withoutSpecificTime);//ZZY
        getTimeJPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        jPanel.add(getTimeJPanel);
        jPanel.add(getEmImJPanel());
        eventTextarea.setMaximumSize(new Dimension(700,250));
        jPanel.add(eventTextarea);
        return jPanel;
    }

    //获取用户输入的事件信息
    protected String getEventString() {
        if (eventTextarea == null)
            return null;
        return eventTextarea.getText();
    }

    //用户点击保存时获取用户在添加事件页面上输入的事件信息
    protected GeneralEvent getEvent() throws BuildObjectException {
        String eventString = getEventString();
        if (eventString == null || eventString.length() == 0) {
            System.out.print(eventString);
            JOptionPane.showMessageDialog(null,
                    " 请检查你的事件描述是否非空 ", " 日历", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        GeneralEvent event;
        if (timePanel.getIsWholeDay())
            event = new GeneralEvent(eventString, timePanel.getBeginDate(), getIfEmergency(), getIfImportant());
        else {

            LocalTime[] localTimes = timePanel.getLocalTimeFromJPanel();
            if (localTimes == null) {
                JOptionPane.showMessageDialog(null,
                        " 请检查你的时间段是否合法！ ", " 日历", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            if (localTimes[1].compareToDate(DateUtil.getToday()) > 0) {
                JOptionPane.showMessageDialog(null,
                        "过去的时间已经过去了，把要做的事情放在当下或未来吧！", " 日历", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            event = new GeneralEvent(eventString, localTimes[0], localTimes[1], getIfEmergency(), getIfImportant());

        }
        event.setWithoutSpecificTimeSetting(withoutSpecificTime.isSelected());//ZZY
        return event;
    }

    protected void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    protected void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}

