package DisplayWindows;

import DateRelated.BuildObjectException;
import DateRelated.CalendarDate;
import DateRelated.DateUtil;
import DateRelated.LocalTime;
import Event.CourseEvent;
import Event.Event;


import javax.swing.*;
import java.awt.*;

public class DisplayAddCourseEvent extends DisplayAddEvent {
    JTextField placeTF = DisplayUtil.getTextField();
    JTextField courseContentTF = DisplayUtil.getTextField();
    JTextField courseNameTF = DisplayUtil.getTextField();
    JTextField teacherTF = DisplayUtil.getTextField();
    JComboBox lastNameCB = DisplayUtil.getSearchComboBox();

    public DisplayAddCourseEvent(CalendarDate date, Event parentEvent, Display display,DisplayEventDetails detailsDisplay) {
        super(date, parentEvent, display,detailsDisplay);
        placeTF.setText("未知");
        courseContentTF.setText("未知");
        teacherTF.setText("未知");
        courseNameTF.setText("未知");
    }


    private String getPlace() {
        return placeTF.getText();
    }

    private String getCourseContent() {
        return courseContentTF.getText();
    }

    private String getCourseName() {
        return courseNameTF.getText();
    }

    private String getTeacher() {
        return teacherTF.getText();
    }

    private int getLastTime() {
        return lastNameCB.getSelectedIndex() + 1;
    }

    private JPanel getCourseInfoPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        jPanel.setLayout(new GridLayout(2, 1));
        JLabel placeLabel = DisplayUtil.getLabel("地点：", true);
        JLabel courseContentLabel = DisplayUtil.getLabel("课程内容：", true);
        JLabel courseNameLabel = DisplayUtil.getLabel("课程名称：", true);
        JLabel teacherLabel = DisplayUtil.getLabel("老师：", true);
        JLabel lastTimeLabel = DisplayUtil.getLabel("持续周数：", true);
        for (int i = 1; i <= 50; i++) {
            lastNameCB.addItem(i);
        }
        lastNameCB.setMaximumSize(new Dimension(100, 40));
        JButton saveEvent = DisplayUtil.getFormalBt("保存");
        saveEvent.addActionListener(saveActionListener());
        JPanel xjpanel1 = new JPanel();
        xjpanel1.setOpaque(false);
        xjpanel1.setLayout(new BoxLayout(xjpanel1, BoxLayout.X_AXIS));
        JPanel xjpanel2 = new JPanel();
        xjpanel2.setOpaque(false);
        xjpanel2.setLayout(new BoxLayout(xjpanel2, BoxLayout.X_AXIS));
        xjpanel1.add(placeLabel);
        xjpanel1.add(placeTF);
        xjpanel1.add(courseNameLabel);
        xjpanel1.add(courseNameTF);
        xjpanel1.add(courseContentLabel);
        xjpanel1.add(courseContentTF);
        xjpanel2.add(teacherLabel);
        xjpanel2.add(teacherTF);
        xjpanel2.add(lastTimeLabel);
        xjpanel2.add(lastNameCB);
        xjpanel2.add(new JLabel("     "));
        xjpanel2.add(saveEvent);
        jPanel.add(xjpanel1);
        jPanel.add(xjpanel2);
        return jPanel;
    }

    @Override
    protected JPanel getEventPanel() {
        String eventString = "添加课程备注";
        eventTextarea = new JTextArea(eventString, 6, 20);
        eventTextarea.setFont(new Font("Serif", 0, 20));
        eventTextarea.setLineWrap(true);
        eventTextarea.setWrapStyleWord(true);

        JPanel panelHeader = new JPanel();
        panelHeader.setOpaque(false);
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.X_AXIS));
        panelHeader.add(getCourseInfoPanel());
        panelHeader.setSize(500, 80);

        JPanel jPanel = new NewPanel("cat.jpg");
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(panelHeader);
        JPanel getTimeJPanel = timePanel.getTimePanel();
        getTimeJPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        getTimeJPanel.setOpaque(false);
        jPanel.add(getTimeJPanel);
        jPanel.add(getEmImJPanel());
        eventTextarea.setMaximumSize(new Dimension(700,250));
        jPanel.add(eventTextarea);
        jPanel.setOpaque(false);
        return jPanel;
    }


    @Override
    protected CourseEvent getEvent() throws BuildObjectException {
        String eventString = getEventString();
        String place = getPlace();
        String courseContent = getCourseContent();
        String courseName = getCourseName();
        String teacher = getTeacher();
        int lastTime = getLastTime();

        if (place.length() == 0 || courseContent.length() == 0 || courseName.length() == 0
                || teacher.length() == 0) {
            JOptionPane.showMessageDialog(null,
                    " 请检查你的课程详情信息是否有空缺 ", " 日历", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        CourseEvent event;
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
        event = new CourseEvent(localTimes[0], courseName, place, lastTime, teacher, courseContent, eventString, localTimes[0], localTimes[1], getIfEmergency(), getIfImportant());
        return event;
    }

}
