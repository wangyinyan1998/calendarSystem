package DisplayWindows;

import DateRelated.*;
import Event.Event;
import Event.*;
import Event.AnniversaryEvent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import Event.CourseEvent;

import Event.GeneralEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import SavingOperation.*;

/*
 * You need to implement Calendar GUI here!
 * show the calendar of month of today.
 * jump to last/next month's calendar
 * jump to last/next year's calendar
 *
 * jump to one specific day's calendar
 * */
public class Display {
    /*这是主面板*/
    JFrame frame = new JFrame("日历");
    private static CalendarDate clickedDate = DateUtil.getToday();
    /*这两个值表示的是能够查询到的最大的年份跟最小的年份*/
    private static final int MAX_YEAR = 2300;
    private static final int MIN_YEAR = 1800;
    private DisplayTimePanel displayTimePanel;
    private LocalTime beginTime;
    private LocalTime endTime;
    private ArrayList<Event> events;

    public Display() {
        SerializedSaving.readAfterOpen();
        this.events = Event.searchEventByDate(DateUtil.getToday());
        displayTimePanel = new DisplayTimePanel(new LocalTime(DateUtil.getToday(), 0, 0), new LocalTime(DateUtil.getToday(), 23, 59), true);
        this.beginTime = new LocalTime(DateUtil.getToday(), 0, 0);
        this.endTime = new LocalTime(DateUtil.getToday(), 23, 59);
        init();
    }

    private void setClickedDate(CalendarDate clickedDate) {
        this.clickedDate = clickedDate;
    }

    private void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    //设置页面上的开始时间段，为页面的时间段显示提供信息
    private void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
        displayTimePanel.setBeginTime(beginTime);
    }

    //设置页面上的终止时间段，为页面的时间段显示提供信息
    private void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        displayTimePanel.setEndTime(endTime);
    }

    protected static CalendarDate getClickedDate() {
        return clickedDate;
    }

    /**
     * Init the UI Windows here. For example, the frame, some panels and buttons.
     */
    private void init() {
        frame.setSize(1440, 740);
        frame.setLocationRelativeTo(null);
        this.clickedDate = DateUtil.getToday();
        paintDays(DateUtil.getToday());
        Timer timeAction = new Timer(60000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        timeAction.start();
    }

    /**
     * paint the days of whole current month on the frame with the given CalendarDate
     *
     * @param clickedDate a valid CalendarDate param.
     */
    /*将日历的整个面板拼凑起来，并显示在窗口上，传入的clickedDate是页面上被鼠标点击过，或者经由查询后，显示在页面上的日期，默认该日期与当天的日期相同*/
    private void paintDays(CalendarDate clickedDate) {
        if (!DateUtil.isValid(clickedDate)) {
            JOptionPane.showMessageDialog(null,
                    " 你要查询的日期有误，请输入正确的日期,\n例如2018-3-28 ", " 日历", JOptionPane.ERROR_MESSAGE);
        } else if (clickedDate.getYear() > MAX_YEAR || clickedDate.getYear() < MIN_YEAR) {
            JOptionPane.showMessageDialog(null,
                    " 你要查询的日期年份超过了本日历的范围\n，请输入1800-2300年之间的日期 ", " 日历", JOptionPane.ERROR_MESSAGE);
        } else {
            setClickedDate(clickedDate);
            JPanel showDatePanel = new JPanel();
            showDatePanel.setLayout(new BoxLayout(showDatePanel, BoxLayout.Y_AXIS));
            showDatePanel.add(paintJPanelHeader());
            showDatePanel.add(paintJPanelBody());
            showDatePanel.add(paintJPanelEnd());
            showDatePanel.setOpaque(false);
            JPanel displayPanel = new NewPanel("cat.jpg");

            displayPanel.setLayout(new GridLayout(1, 2));
            displayPanel.add(paintAddEvent());
            displayPanel.add(showDatePanel);
            frame.setContentPane(displayPanel);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    SerializedSaving.saveBeforeClose(Event.getEvents());
                    System.exit(0);
                }
            });
            frame.setVisible(true);
        }
    }

    /*panelBody*/
    private JPanel paintJPanelBody() {
        String[] week = {" 日 ", " 一 ", " 二 ", " 三 ", " 四 ", " 五 ", " 六 "};
        JPanel panelBody = new JPanel();
        //panelBody.setBackground(new Color(1, 1, 1, 180));
        //panelBody.setBackground(null);
        panelBody.setOpaque(false);
        panelBody.setSize(600, 600);
        panelBody.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        ;
        panelBody.setLayout(new GridLayout(7, 7, 5, 5));
        for (int i = 0; i < 7; i++) {
            panelBody.add(DisplayUtil.getFormalBt(week[i]));
        }
        List<CalendarDate> dateList = DateUtil.getDaysInMonth(clickedDate);
        int listSize = dateList.size();
        int firstDayOfWeekIndex = dateList.get(0).getDayOfWeek();
        if (firstDayOfWeekIndex == 7)
            firstDayOfWeekIndex = 0;
        int formYear = clickedDate.getMonth() == 1 ? clickedDate.getYear() - 1 : clickedDate.getYear();
        int formMonth = clickedDate.getMonth() == 1 ? 12 : clickedDate.getMonth() - 1;
        int formMonthNumber = DateUtil.getDaysInMonth(new CalendarDate(formYear, formMonth, DateUtil.getDayOfMonth(formMonth, formYear))).size();
        for (int i = firstDayOfWeekIndex - 1; i >= 0; i--) {
            panelBody.add(DisplayUtil.getButton(new CalendarDate(formYear, formMonth, (formMonthNumber - i))));
        }
        for (CalendarDate dateTmp : dateList) {
            JButton btDay = DisplayUtil.getButton(dateTmp);
            btDay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    refreshTime(dateTmp);
                }
            });
            panelBody.add(btDay);
        }
        int laterYear = clickedDate.getMonth() == 12 ? clickedDate.getYear() + 1 : clickedDate.getYear();
        int laterMonth = clickedDate.getMonth() == 12 ? 1 : clickedDate.getMonth() + 1;
        for (int i = listSize + firstDayOfWeekIndex, j = 1; i < 42; i++) {
            panelBody.add(DisplayUtil.getButton(new CalendarDate(laterYear, laterMonth, (j++))));
        }
        return panelBody;
    }

    /*画面板的header*/
    private JPanel paintJPanelHeader() {
        /*年份的下拉框*/
        JPanel panelHeader = new JPanel();
        panelHeader.setOpaque(false);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelHeader.add(DisplayUtil.getLabel("年份：", true));
        final JComboBox yearComboBox = DisplayUtil.getSearchComboBox();
        yearComboBox.setPreferredSize(new Dimension(100, 30));
        for (int i = MIN_YEAR; i <= MAX_YEAR; i++) {
            yearComboBox.addItem("" + i);
        }
        int yearSelectIndex = clickedDate.getYear() - 1800;
        yearComboBox.setSelectedIndex(yearSelectIndex);
        panelHeader.add(yearComboBox);

        /*月份的下拉框*/
        panelHeader.add(DisplayUtil.getLabel("月份：", true));
        final JComboBox monthComboBox = DisplayUtil.getSearchComboBox();
        for (int i = 1; i <= 12; i++)
            monthComboBox.addItem("" + i);
        int monthSelectIndex = clickedDate.getMonth() - 1;//得到该日期的月份位置
        monthComboBox.setSelectedIndex(monthSelectIndex);
        panelHeader.add(monthComboBox);

        JButton checkMonth = DisplayUtil.getFormalBt("查看");
        /*给查看button添加一个事件处理器*/
        checkMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int monthCheckIndex = monthComboBox.getSelectedIndex();
                int yearCheckIndex = yearComboBox.getSelectedIndex();
                CalendarDate date = new CalendarDate(yearCheckIndex + MIN_YEAR, monthCheckIndex + 1, 1);
                refreshTime(date);
            }
        });
        /*给今天button添加一个事件处理器*/
        JButton today = DisplayUtil.getFormalBt("今天");
        today.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTime(DateUtil.getToday());
            }
        });
        panelHeader.add(checkMonth);
        panelHeader.add(today);
        return panelHeader;
    }

    /*画面板的末尾查询的部分*/
    private JPanel paintJPanelEnd() {
        JPanel panelEnd = new JPanel();
        panelEnd.setOpaque(false);
        panelEnd.setSize(600, 60);
        final JTextField inputDay = DisplayUtil.getTextField();
        inputDay.setColumns(11);
        JButton checkBt = DisplayUtil.getFormalBt("查询日期");
        checkBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checkDate = inputDay.getText();
                try {
                    CalendarDate date = new CalendarDate(checkDate);
                    refreshTime(date);
                } catch (BuildObjectException buildObjectionEx) {
                    JOptionPane.showMessageDialog(null,
                            " 你要查询的日期有误，请输入正确的日期,例如2018-3-28 ", " 日历", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        panelEnd.add(inputDay);
        panelEnd.add(checkBt);
        return panelEnd;
    }

    /*画显示待办事项的面板 */
    private JPanel paintAddEvent() {

        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        jPanel.setLayout(new BorderLayout());
        jPanel.add(paintEventHeader(), BorderLayout.NORTH);
        jPanel.add(paintEventSearch(), BorderLayout.SOUTH);

        JScrollPane showEventsScrollPane = new JScrollPane();
        JPanel showEvents = new JPanel();
        showEvents.setOpaque(false);
        showEvents.setLayout(new BoxLayout(showEvents, BoxLayout.Y_AXIS));
        JLabel showEventLabel = DisplayUtil.getLabel("  您的待办事项如下：   ",true);
        showEventLabel.setFont(new Font("楷体",0,30));
        JPanel labelPanel1 = new JPanel();
        labelPanel1.setOpaque(false);
        labelPanel1.add(showEventLabel);
        labelPanel1.setSize(200,50);
        showEvents.add(labelPanel1);
        if (events != null) {
            for (Event event : events) {
                JTextArea showEvent = new JTextArea(event.getObtainInformation(false)); //btEvent表示的是事件显示
                showEvent.setLineWrap(true);
                showEvent.setSize(new Dimension(340, 100));
                showEvent.setFont(new Font("Serif", 0, 20));
                showEvent.setEditable(false);
                JPanel showEventJPanel = new JPanel();
                showEventJPanel.setOpaque(false);
                showEventJPanel.add(showEvent);
                showEventJPanel.setSize(new Dimension(350, 100));

                JButton deleteEvent = DisplayUtil.getFormalBt("删除");
                deleteEvent.setOpaque(true);
                deleteEvent.setBackground(new Color(100,150,100));
                deleteEvent.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (event instanceof AnniversaryEvent) {
                            for (Event event1 : events) {
                                if (((AnniversaryEvent) event).equalsAnniversary(event1)) {
                                    Event.deleteEvents(event1);
                                }
                            }
                        }
                        //add by ZZY
                        else if (event instanceof CourseEvent) {
                            for (Event event1 : events) {
                                if (((CourseEvent) event).isSameCourse(event1)) {
                                    Event.deleteEvents(event1);
                                }
                            }
                        }
                        //ZZY
                        else {
                            Event.deleteEvents(event);
                        }
                        //删除事件之后刷新展示事件的页面和日历页面
                        refresh();
                    }
                });
                JPanel panel = new JPanel();
                panel.setOpaque(false);
                panel.setSize(200, 80);
                JButton btShow = DisplayUtil.getFormalBt("详情");
                btShow.setOpaque(true);
                btShow.setBackground(new Color(100,150,100));
                panel.add(deleteEvent);
                panel.add(btShow);
                btShow.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DisplayEventDetails displayEventDetails = new DisplayEventDetails(event, Display.this);
                        displayEventDetails.init();
                    }
                });
                showEvents.add(showEventJPanel);
                showEvents.add(panel);
            }
        }
        showEvents.setOpaque(false);
        showEventsScrollPane.setOpaque(false);
        showEventsScrollPane.setMaximumSize(new Dimension(200,500));
        showEventsScrollPane.setBackground(new Color(1,1,1,100));
        //将显示事件的面板设置成可以滚动的面板，并添加到总的显示面板中
        showEvents.setBorder(BorderFactory.createEmptyBorder(0,0,2,0));
        showEventsScrollPane.setViewportView(showEvents);
        showEventsScrollPane.getViewport().setBackground(new Color(1,1,1,100));

        JPanel showAlarmEvent = new JPanel();
        showAlarmEvent.setMaximumSize(new Dimension(300,500));
        showAlarmEvent.setOpaque(false);
        showAlarmEvent.setLayout(new BoxLayout(showAlarmEvent,BoxLayout.Y_AXIS));
        JScrollPane showAlarmScrollPane = new JScrollPane();
        showAlarmScrollPane.setMaximumSize(new Dimension(300,500));
        showAlarmScrollPane.setOpaque(false);
        JLabel alarmLabel = DisplayUtil.getLabel("   您的提醒事件如下： ",true);
        alarmLabel.setFont(new Font("楷体",0,30));
        JPanel labelPanel2 = new JPanel();
        labelPanel2.setOpaque(false);
        labelPanel2.add(alarmLabel);
        labelPanel2.setSize(200,50);
        showAlarmEvent.add(labelPanel2);

        ArrayList<Event> eventsHaveAlarm = EventUtil.getAlarmEvents();
        for(Event eventHaveAlarm:eventsHaveAlarm){
            String alarmString = AlarmEvent.showAlarm(eventHaveAlarm,eventHaveAlarm.getTimeBegin());
            if(alarmString!=null){
                JTextArea alarmTextArea = new JTextArea(alarmString);
                JPanel showAlarmPanel = new JPanel();
                alarmTextArea.setLineWrap(true);
                alarmTextArea.setSize(new Dimension(340, 200));
                alarmTextArea.setFont(new Font("Serif", 0, 20));
                JButton confirmEvent = DisplayUtil.getFormalBt("知道了");
                confirmEvent.setOpaque(true);
                confirmEvent.setBackground(new Color(100,150,100));
                confirmEvent.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AlarmMusic.musicStop();
                        //eventHaveAlarm.getAlarm().setOnlyFace(false);
                        int strategy = eventHaveAlarm.getAlarm().getTypeOfStrategy();
                        if(strategy == 0){
                            eventHaveAlarm.getAlarm().setIsAlarm(false);
                        }else if(strategy == 1){
                            AlarmEvent.strategyTen(eventHaveAlarm.getAlarm(),"");
                        }else if(strategy == 2){
                            AlarmEvent.strategyHour(eventHaveAlarm.getAlarm(),"");
                        }else if(strategy == 3){
                            AlarmEvent.strategyDay(eventHaveAlarm.getAlarm(),"");
                        }
                        refresh();
                    }
                });
                alarmTextArea.setEditable(false);
                showAlarmPanel.add(alarmTextArea);
                showAlarmPanel.setOpaque(false);
                showAlarmEvent.add(showAlarmPanel);
                showAlarmEvent.add(confirmEvent);
            }
        }


        ArrayList<Event> totalEvent = Event.getEvents();
        for(Event event: totalEvent){
            ArrayList<Event> subEventsHaveAlarm = EventUtil.getSubAlarmEvents(event);
            for(Event subEventHaveAlarm: subEventsHaveAlarm){
                String alarmString = AlarmEvent.showAlarm(subEventHaveAlarm,subEventHaveAlarm.getTimeBegin());
                if(alarmString!=null){
                    JTextArea alarmTextArea = new JTextArea(alarmString);
                    JPanel showAlarmPanel = new JPanel();
                    alarmTextArea.setLineWrap(true);
                    alarmTextArea.setSize(new Dimension(340, 200));
                    alarmTextArea.setFont(new Font("Serif", 0, 20));
                    JButton confirmEvent = DisplayUtil.getFormalBt("知道了");
                    confirmEvent.setOpaque(true);
                    confirmEvent.setBackground(new Color(100,150,100));
                    confirmEvent.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //subEventHaveAlarm.getAlarm().setOnlyFace(false);
                            int strategy = subEventHaveAlarm.getAlarm().getTypeOfStrategy();
                            if(strategy == 0){
                                subEventHaveAlarm.getAlarm().setIsAlarm(false);
                            }else if(strategy == 1){
                                AlarmEvent.strategyTen(subEventHaveAlarm.getAlarm(),"");
                            }else if(strategy == 2){
                                AlarmEvent.strategyHour(subEventHaveAlarm.getAlarm(),"");
                            }else if(strategy == 3){
                                AlarmEvent.strategyDay(subEventHaveAlarm.getAlarm(),"");
                            }
                            refresh();
                        }
                    });
                    alarmTextArea.setEditable(false);
                    showAlarmPanel.add(alarmTextArea);
                    showAlarmPanel.setOpaque(false);
                    showAlarmEvent.add(showAlarmPanel);
                    showAlarmEvent.add(confirmEvent);
                }
            }
        }
       // haveAlarmBorderJPanel.add(showAlarmEvent,BorderLayout.CENTER);
        showAlarmScrollPane.setViewportView(showAlarmEvent);
        showAlarmScrollPane.getViewport().setBackground(new Color(1,1,1,100));

        jPanel.add(showEventsScrollPane, BorderLayout.EAST);
        jPanel.add(showAlarmScrollPane,BorderLayout.WEST);
        return jPanel;
    }
    //画页面的头部，主要是显示这些待办事项的时间段信息，和添加事项的按钮
    private JPanel paintEventHeader() {
        JPanel jPaneLabel = new JPanel();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        jPanel.setOpaque(false);
        jPaneLabel.setOpaque(false);
        jPanel2.setOpaque(false);
        jPanel1.setOpaque(false);

        jPaneLabel.add(DisplayUtil.getLabel(beginTime.toString() + " - " + endTime.toString() + " 您的待办事项", true));
        JMenuBar ber = generatorSubJMenuBar(null,"新建事件", null, "会议", "约会", "纪念日", "课程", "面试", "旅行", "自定义");

        jPanel1.add(ber);
        jPanel2.add(jPaneLabel);
        jPanel.add(jPanel2);
        jPanel.add(jPanel1);

        return jPanel;
    }

    //画待办事项的搜索页面，这个页面使用了DisplayAddEvent类中的时间面板，并且使用了该类中获取时间的方法
    private JPanel paintEventSearch() {
        JPanel searchEvents = new JPanel();
        searchEvents.setOpaque(false);
        JPanel searchTime = displayTimePanel.getTimePanel();
        JButton btSearch = DisplayUtil.getFormalBt("搜索");
        btSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LocalTime[] localTimes = displayTimePanel.getLocalTimeFromJPanel();
                    if (localTimes != null) {
                        setBeginTime(localTimes[0]);
                        setEndTime(localTimes[1]);
                        refresh();
                    } else
                        JOptionPane.showMessageDialog(null,
                                " 你的查询时间段不合法 ，请检查后再搜索！ ", " 日历", JOptionPane.ERROR_MESSAGE);
                } catch (BuildObjectException ex) {
                    JOptionPane.showMessageDialog(null,
                            " 你要查询的日期有误，请输入正确的日期,例如2018-3-28 ", " 日历", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JPanel btSearchPanel = new JPanel();
        btSearchPanel.add(btSearch);
        btSearchPanel.setOpaque(false);
        searchEvents.setLayout(new BoxLayout(searchEvents, BoxLayout.Y_AXIS));
        searchEvents.add(searchTime);
        searchEvents.add(btSearchPanel);
        searchEvents.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        return searchEvents;
    }

    //刷新日期并重新画日历面板
    private void refreshTime(CalendarDate date) {
        setBeginTime(new LocalTime(date, 0, 0));
        setEndTime(new LocalTime(date, 23, 59));

        //add by wangjiahui
        ArrayList<Event> returnEvent = Event.searchEventByTime(beginTime, endTime);
        if (returnEvent != null) {
            for (Event event : returnEvent) {
                Event.setEventState(event);
            }
        }
        setEvents(returnEvent);
        displayTimePanel.setIsWholeDay(false);
        paintDays(date);
    }


    public JMenuBar generatorSubJMenuBar(DisplayEventDetails detailsDisplay,String barName, Event eventArg, String... args) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(false);
        JMenu bar = new JMenu(barName);
        menuBar.add(bar);
        bar.setFont(new Font("Serif", 0, 25));
        for (String s : args) {
            JMenuItem item = new JMenuItem(s);
            bar.add(item);
            item.setFont(new Font("Serif", 0, 25));
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean isCompleteEvent = false;
                    if(eventArg != null){
                        isCompleteEvent = eventArg.getComplete();
                    }
                    if (clickedDate.compareTo(DateUtil.getToday()) <= 0 && !isCompleteEvent) {
                        if (s.equals("纪念日")) {
                            DisplayAddAnniversaryEvent displayAddAnniversaryEvent = new DisplayAddAnniversaryEvent(clickedDate, eventArg, Display.this,detailsDisplay);
                            displayAddAnniversaryEvent.displayAddEvent();
                        } else if (s.equals("课程")) {
                            DisplayAddCourseEvent displayAddCourseEvent = new DisplayAddCourseEvent(clickedDate, eventArg, Display.this,detailsDisplay);
                            displayAddCourseEvent.displayAddEvent();
                        } else if (s.equals("约会")) {
                            DisplayAddDateEvent displayAddDateEvent = new DisplayAddDateEvent(clickedDate, eventArg, Display.this,detailsDisplay);
                            displayAddDateEvent.displayAddEvent();
                        } else if (s.equals("自定义")) {
                            DisplayAddGeneralEvent displayAddGeneralEvent = new DisplayAddGeneralEvent(clickedDate, eventArg, Display.this,detailsDisplay);
                            displayAddGeneralEvent.displayAddEvent();
                        } else if (s.equals("面试")) {
                            DisplayAddInterviewEvent displayAddInterviewEvent = new DisplayAddInterviewEvent(clickedDate, eventArg, Display.this,detailsDisplay);
                            displayAddInterviewEvent.displayAddEvent();
                        } else if (s.equals("会议")) {
                            DisplayAddMeetingEvent displayAddMeetingEvent = new DisplayAddMeetingEvent(clickedDate, eventArg, Display.this,detailsDisplay);
                            displayAddMeetingEvent.displayAddEvent();
                        } else if (s.equals("旅行")) {
                            DisplayAddTripEvent displayAddTripEvent = new DisplayAddTripEvent(clickedDate, eventArg, Display.this,detailsDisplay);
                            displayAddTripEvent.displayAddEvent();
                        }
                    } else if(clickedDate.compareTo(DateUtil.getToday()) > 0) {
                        JOptionPane.showMessageDialog(null,
                                "过去的时间已经过去了，把要做的事情放在当下或未来吧！", " 日历", JOptionPane.ERROR_MESSAGE);
                    }else if(isCompleteEvent){
                        JOptionPane.showMessageDialog(null,
                                "该事件已完成，不能添加子事件！", " 日历", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
        return menuBar;
    }


    public JMenuBar subMenuBar(DisplayEventDetails detailsDisplay,Event event) {
        JMenuBar menuBar = new JMenuBar();
        if (event instanceof AnniversaryEvent || event instanceof TripEvent || (event instanceof GeneralEvent && !((GeneralEvent) event).isWithoutSpecificTimeSetting())) {
            menuBar = generatorSubJMenuBar(detailsDisplay,"新建子事件", event, "会议", "约会", "纪念日", "课程", "面试", "旅行", "自定义");
        } else if (event instanceof MeetingEvent) {
            menuBar = generatorSubJMenuBar(detailsDisplay,"新建子事件", event, "会议", "纪念日", "旅行", "自定义");
        } else if (event instanceof DateEvent) {
            menuBar = generatorSubJMenuBar(detailsDisplay,"新建子事件" ,event, "约会", "纪念日", "旅行", "自定义");
        } else if (event instanceof CourseEvent) {
            menuBar = generatorSubJMenuBar(detailsDisplay,"新建子事件", event, "课程", "纪念日", "旅行", "自定义");
        } else if (event instanceof InterviewEvent) {
            menuBar = generatorSubJMenuBar(detailsDisplay,"新建子事件", event, "面试", "纪念日", "旅行", "自定义");
        }
        return menuBar;
    }

    //刷新面板
    public void refresh() {
        Event.resetTimeOfGeneralEventWithoutSpecificTime();//ZZY
        ArrayList<Event> returnEvent = Event.searchEventByTime(beginTime, endTime);
        if (returnEvent != null) {
            for (Event event : returnEvent) {
                Event.setEventState(event);
            }
        }
        setEvents(returnEvent);
        paintDays(clickedDate);
    }
}
