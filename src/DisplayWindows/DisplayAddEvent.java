package DisplayWindows;

import DateRelated.BuildObjectException;
import DateRelated.CalendarDate;
import DateRelated.DateUtil;
import DateRelated.LocalTime;
import Event.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Event.EventUtil;

public abstract class DisplayAddEvent {
    protected DisplayTimePanel timePanel;
    protected JTextArea eventTextarea;
    protected LocalTime beginTime;
    protected LocalTime endTime;
    protected Event parentEvent;
    protected Display display;
    protected DisplayEventDetails detailsDisplay;
    protected JRadioButton emergency = new JRadioButton("紧急");// 创建复选按钮
    protected JRadioButton important = new JRadioButton("重要");// 创建复选按钮
    JFrame jFrame = new JFrame("事件详情：");

    public DisplayAddEvent(CalendarDate date, Event parentEvent, Display display, DisplayEventDetails detailsDisplay) {
        this.beginTime = parentEvent == null ? new LocalTime(date, 0, 0) : parentEvent.getTimeBegin();
        this.endTime = parentEvent == null ? new LocalTime(date, 23, 59) : parentEvent.getTimeEnd();
        this.parentEvent = parentEvent;
        boolean isWholeDay = parentEvent == null ? true : parentEvent.isWholeDay();
        this.display = display;
        this.detailsDisplay = detailsDisplay;
        timePanel = new DisplayTimePanel(beginTime, endTime, isWholeDay);
        String eventString = "请输入时间信息";
        eventTextarea = new JTextArea(eventString, 6, 20);
    }

    protected void displayAddEvent() {
        JPanel eventPanel = getEventPanel();
        jFrame.setSize(800, 600);
        jFrame.setContentPane(eventPanel);
        jFrame.setVisible(true);
    }

    protected abstract JPanel getEventPanel();

    /**
     * 以下为添加事件的面板设计
     */

    //获取用户输入的事件信息
    protected String getEventString() {
        if (eventTextarea == null)
            return null;
        return eventTextarea.getText();
    }

    protected abstract Event getEvent() throws BuildObjectException;

    protected void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    protected void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    protected JPanel getEmImJPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        emergency.setOpaque(false);
        important.setOpaque(false);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        emergency.setMaximumSize(new Dimension(100, 100));
        important.setMaximumSize(new Dimension(100, 100));
        emergency.setFont(new Font("Serif", 0, 24));
        important.setFont(new Font("Serif", 0, 25));
        jPanel.add(emergency);
        jPanel.add(important);
        return jPanel;
    }

    protected boolean getIfEmergency() {
        return emergency.isSelected();
    }

    protected boolean getIfImportant() {
        return important.isSelected();
    }

    protected ActionListener saveActionListener() {
        ActionListener saveEventHander =
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            //**add by wangjiahui
                            Event event = getEvent();
                            if (parentEvent != null) {
                                //ZZY
                               // System.out.println("ha");
                                /**if(EventUtil.haveConflict(event))
                                    return;**/
                                String conflictWithSubEvent = EventUtil.conflictWithOtherSubEvent(event, parentEvent.getSubEvent());
                                if (conflictWithSubEvent!=null) {
                                    JOptionPane.showMessageDialog(null,
                                            " Oops! "+conflictWithSubEvent, " 日历", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                if (!EventUtil.fatherEventCompletelyContainsSubEvent(parentEvent, event)) {
                                    JOptionPane.showMessageDialog(null,
                                            " Oops! 您想添加的子事件超出了父事件时间范围 ", " 日历", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                //ZZY
                                if (parentEvent.addSubEvent(event)) {
                                    Event.setEventState(event);
                                    detailsDisplay.refresh();
                                    jFrame.dispose();
                                }
                            } else {
                                /*过期时间无法添加事件*/
                                if(event.getTimeEnd().compareTo(DateUtil.getCurrentTime())>0) {
                                    JOptionPane.showMessageDialog(null,
                                            " 过去的时间已经过去了，就不要添加事件啦！ ", " 日历", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                if (Event.rememberEvents(event)) {
                                    Event.setEventState(event);
                                    display.refresh();
                                    jFrame.dispose();
                                }
                                //原来的
                            }
                        } catch (BuildObjectException ex) {
                            JOptionPane.showMessageDialog(null,
                                    " 你要查询的日期有误，请输入正确的日期,例如2018-3-28 ", " 日历", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };
        return saveEventHander;
    }

}

