package DisplayWindows;

import DateRelated.BuildObjectException;
import DateRelated.CalendarDate;
import DateRelated.DateUtil;
import DateRelated.LocalTime;
import Event.InterviewEvent;
import Event.Event;
import Event.EventUtil;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Think on 2018/5/19.
 */
public class DisplayAddInterviewEvent extends DisplayAddEvent {
    private JTextField interviewTimeFiled = DisplayUtil.getTextField();
    private JTextField companyField = DisplayUtil.getTextField();
    private JTextField jobField = DisplayUtil.getTextField();
    private JTextField placeFiled = DisplayUtil.getTextField();

    public DisplayAddInterviewEvent(CalendarDate date, Event parentEvent, Display display,DisplayEventDetails detailsDisplay) {
        super(date, parentEvent, display,detailsDisplay);
        jobField.setText("未知");
        interviewTimeFiled.setText("未知");
        companyField.setText("未知");
        placeFiled.setText("未知");
    }

    private String getInterviewTime() {
        return this.interviewTimeFiled.getText();
    }

    private String getCompanyField() {
        return this.companyField.getText();
    }

    private String getJobField() {
        return this.jobField.getText();
    }

    private String getPlace() {
        return this.placeFiled.getText();
    }


    //王佳惠新添的方法，用来产生地点和会议主题
    private JPanel getInformation() {
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        JLabel placeLabel = DisplayUtil.getLabel("地点：", true);
        JLabel companyLabel = DisplayUtil.getLabel("公司：", true);
        JLabel jobLabel = DisplayUtil.getLabel("职位：", true);
        JLabel interviewTimeLabel = DisplayUtil.getLabel("面试时间：", true);
        jPanel.add(placeLabel);
        jPanel.add(placeFiled);
        jPanel.add(companyLabel);
        jPanel.add(companyField);
        jPanel.add(jobLabel);
        jPanel.add(jobField);
        jPanel.add(interviewTimeLabel);
        jPanel.add(interviewTimeFiled);
        return jPanel;
    }

    @Override
    protected JPanel getEventPanel() {
        //String eventString = oldEvent==null ? "请输入面试的详细信息":oldEvent.getEventString();
        String eventString = "请输入面试的详细信息";
        eventTextarea = new JTextArea(eventString, 6, 20);
        eventTextarea.setFont(new Font("Serif", 0, 20));
        eventTextarea.setLineWrap(true);
        eventTextarea.setWrapStyleWord(true);
        JButton saveEvent = DisplayUtil.getFormalBt("保存");
        saveEvent.addActionListener(saveActionListener());
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.X_AXIS));

        //新添加
        JPanel meetingCom = getInformation();
        panelHeader.add(meetingCom);
        panelHeader.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelHeader.add(saveEvent);
        panelHeader.setSize(500, 80);
        panelHeader.setOpaque(false);
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


    @Override
    protected InterviewEvent getEvent() throws BuildObjectException {
        String eventString = getEventString();
        if (eventString == null || eventString.length() == 0) {
            JOptionPane.showMessageDialog(null,
                    " 请检查你的事件描述是否非空 ", " 日历", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String placeString = getPlace();
        String companyString = getCompanyField();
        String jobString = getJobField();
        String interviewTimeString = getInterviewTime();
        if (placeString.length() == 0 || companyString.length() == 0 || jobString.length() == 0 || interviewTimeString.length() == 0) {
            JOptionPane.showMessageDialog(null,
                    " 请检查你的地点、公司、工作和面试时间是否为空 ", " 日历", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        InterviewEvent event;
        if (timePanel.getIsWholeDay())
            event = new InterviewEvent(interviewTimeString, placeString, companyString, jobString, eventString, timePanel.getBeginDate(), getIfEmergency(), getIfImportant());
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
            event = new InterviewEvent(interviewTimeString, placeString, companyString, jobString, eventString, localTimes[0], localTimes[1], getIfEmergency(), getIfImportant());
        }
        return event;
    }

}
