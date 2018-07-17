package DisplayWindows;

import java.awt.event.ItemEvent;

import DateRelated.LocalTime;
import Event.Event;
import Event.Alarm;
import Event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Calendar;

import DateRelated.*;

/**
 * Created by hp on 2018/5/20.
 */
public class DisplayAlarm {
    private JFrame alarmFrame = new JFrame("事件提醒设置");
    private JTextField wayNum;
    private JRadioButton once;
    private JRadioButton tenMin;
    private JRadioButton hour;
    private JRadioButton day;
    private JComboBox yearComboBox;
    private JComboBox monthComboBox;
    private JComboBox dayComboBox;
    private JComboBox<Integer> hourCom;
    private JComboBox<Integer> minuteCom;
    private Display display;
    private DisplayEventDetails detailsDisplay;

    public DisplayAlarm(Display display, DisplayEventDetails detailsDisplay) {
        this.display = display;
        this.detailsDisplay = detailsDisplay;
    }

    public JFrame getAlarmPanel(Event alarmEvent) {
        alarmFrame.setSize(800, 500);
        alarmFrame.setVisible(true);
        JPanel jPanel = new NewPanel("cat.jpg");
        jPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.GRAY));
        jPanel.setLayout(new BorderLayout());

        jPanel.add(paintJPanelHeader(alarmEvent), BorderLayout.NORTH);
        jPanel.add(getAlarmContext(alarmEvent), BorderLayout.CENTER);

        alarmFrame.add(jPanel);
        return alarmFrame;
    }

    private JPanel paintJPanelHeader(Event alarmEvent) {
        CalendarDate clickedDate = alarmEvent.getTimeBegin().getDate();
        JPanel panelHeader = new JPanel();
        panelHeader.setOpaque(false);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelHeader.setLayout(new BorderLayout());
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        jPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel yearLabel = DisplayUtil.getLabel("年份：", true);
        // yearLabel.setFont(new Font("Serif", 0, 20));
        yearComboBox = getComboBox();
        yearComboBox.setFont(new Font("Serif", 0, 20));
        for (int i = 1800; i <= 2300; i++) {
            yearComboBox.addItem("" + i);
        }
        int yearSelectIndex = clickedDate.getYear() - 1800;
        yearComboBox.setSelectedIndex(yearSelectIndex);
       /* yearComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    alarmEvent.getAlarm().getEarlyTime().getDate().setYear(Integer.parseInt(yearComboBox.getSelectedItem().toString()));
                }
            }
        });*/
        jPanel.add(yearLabel);
        jPanel.add(yearComboBox);

        /*月份的下拉框*/
        JLabel monthLabel = DisplayUtil.getLabel("月份：", true);
        //  monthLabel.setFont(new Font("Serif", 0, 20));
        monthComboBox = getComboBox();
        monthComboBox.setFont(new Font("Serif", 0, 20));
        for (int i = 1; i <= 12; i++)
            monthComboBox.addItem("" + i);
        int monthSelectIndex = DateUtil.getToday().getMonth() - 1;//得到该日期的月份位置
        monthComboBox.setSelectedIndex(monthSelectIndex);
       /* monthComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    alarmEvent.getAlarm().getEarlyTime().getDate().setMonth(Integer.parseInt(monthComboBox.getSelectedItem().toString()));
                }
            }
        });*/
        jPanel.add(monthLabel);
        jPanel.add(monthComboBox);

        JLabel dayLabel = DisplayUtil.getLabel("天数：", true);
        //dayLabel.setFont(new Font("Serif", 0, 20));
        dayComboBox = getComboBox();
        dayComboBox.setFont(new Font("Serif", 0, 20));
        for (int i = 1; i <= DateUtil.getDayOfMonth(DateUtil.getToday().getMonth(), DateUtil.getToday().getYear()); i++) {
            dayComboBox.addItem("" + i);
        }
        dayComboBox.setSelectedIndex(DateUtil.getToday().getDay() - 1);

        jPanel.add(dayLabel);
        jPanel.add(dayComboBox);

        alarmEvent.getAlarm().getEarlyTime().setDate(new CalendarDate(Integer.parseInt(yearComboBox.getSelectedItem().toString()),
                Integer.parseInt(monthComboBox.getSelectedItem().toString()), Integer.parseInt(dayComboBox.getSelectedItem().toString())));

        panelHeader.add(jPanel, BorderLayout.NORTH);
        panelHeader.add(paintBody(alarmEvent), BorderLayout.CENTER);
        return panelHeader;
    }

    private JPanel paintBody(Event alarmEvent) {
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        jPanel.setLayout(new BorderLayout());
        jPanel.add(getAlarmClock(alarmEvent), BorderLayout.NORTH);
        jPanel.add(getStrategyPanel(alarmEvent), BorderLayout.CENTER);
        return jPanel;
    }

    private JPanel getAlarmContext(Event alarmEvent) {
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        String context = "!!提醒事项：\n" + alarmEvent.getEventString();
        JLabel jLabel = DisplayUtil.getLabel(context, true);
        jPanel.add(jLabel);
        return jPanel;
    }

    private JPanel getAlarmClock(Event alarmEvent) {
        JPanel jPanel = new JPanel();
        JPanel jPanel1 = new JPanel();
        jPanel.setOpaque(false);
        jPanel1.setOpaque(false);
        hourCom = new JComboBox<>(); //设置小时
        for (int i = 0; i < 24; i++) {
            hourCom.addItem(i);
        }
        hourCom.setPreferredSize(new Dimension(100, 30));
        hourCom.setFont(new Font("Serif", 0, 20));
        hourCom.setSelectedIndex(-1);
        hourCom.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    alarmEvent.getAlarm().getEarlyTime().setHour(Integer.parseInt(hourCom.getSelectedItem().toString()));
                }
            }
        });
        minuteCom = new JComboBox<>();//设置分钟
        for (int i = 0; i < 60; i++) {
            minuteCom.addItem(i);
        }
        minuteCom.setFont(new Font("Serif", 0, 20));
        minuteCom.setSelectedIndex(-1);
        minuteCom.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    alarmEvent.getAlarm().getEarlyTime().setMinute(Integer.parseInt(minuteCom.getSelectedItem().toString()));
                }
            }
        });
        minuteCom.setPreferredSize(new Dimension(100, 30));
        JLabel textLabel = DisplayUtil.getLabel("设置小时 ：分钟： ", true);
        jPanel.add(textLabel);
        jPanel1.add(hourCom);
        jPanel1.add(new JLabel(" : "));
        jPanel1.add(minuteCom);
        jPanel.add(jPanel1);
        jPanel.add(getValid(alarmEvent));
        return jPanel;
    }


    private JPanel getStrategyPanel(Event alarmEvent) {
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        jPanel.add(getWayPanel(alarmEvent));
        JPanel jPanel1 = new JPanel();
        jPanel1.setOpaque(false);
        once = new JRadioButton("单次");
        tenMin = new JRadioButton("每十分钟");
        hour = new JRadioButton("每小时");
        day = new JRadioButton("每天");
        once.setFont(new Font("Serif", 0, 20));
        tenMin.setFont(new Font("Serif", 0, 20));
        hour.setFont(new Font("Serif", 0, 20));
        day.setFont(new Font("Serif", 0, 20));
        once.setOpaque(false);
        tenMin.setOpaque(false);
        hour.setOpaque(false);
        day.setOpaque(false);
        once.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                alarmEvent.getAlarm().setTypeOfStrategy(Alarm.strategyType.ONCE.ordinal());
                tenMin.setSelected(false);
                hour.setSelected(false);
                day.setSelected(false);
            }
        });

        tenMin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                alarmEvent.getAlarm().setTypeOfStrategy(Alarm.strategyType.PER_TEN_MINUTES.ordinal());
                once.setSelected(false);
                hour.setSelected(false);
                day.setSelected(false);
            }
        });
        hour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                alarmEvent.getAlarm().setTypeOfStrategy(Alarm.strategyType.PER_ONE_HOUR.ordinal());
                once.setSelected(false);
                tenMin.setSelected(false);
                day.setSelected(false);
            }
        });
        day.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                alarmEvent.getAlarm().setTypeOfStrategy(Alarm.strategyType.PER_ONE_DAY.ordinal());
                once.setSelected(false);
                hour.setSelected(false);
                tenMin.setSelected(false);
            }
        });
        jPanel1.add(once);
        jPanel1.add(tenMin);
        jPanel1.add(hour);
        jPanel1.add(day);
        jPanel.add(jPanel1);
        return jPanel;
    }

    private JPanel getValid(Event alarmEvent) {
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        JButton jButton = new JButton("立即生效");
        jButton.setBackground(new Color(100, 150, 100));
        jButton.setFont(new Font("Serif", 0, 16));
        jButton.setPreferredSize(new Dimension(100, 40));
        Calendar calendar = Calendar.getInstance();
        LocalTime nowTime = new LocalTime(DateUtil.getToday(), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (hourCom.getSelectedIndex() == -1 || minuteCom.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null,
                            "请设置小时 ：分钟", " 提醒方式", JOptionPane.ERROR_MESSAGE);
                } else {
                    CalendarDate timeB = new CalendarDate(Integer.parseInt(yearComboBox.getSelectedItem().toString()),
                            Integer.parseInt(monthComboBox.getSelectedItem().toString()), Integer.parseInt(dayComboBox.getSelectedItem().toString()));
                    LocalTime localTimeB = new LocalTime(timeB, Integer.parseInt(hourCom.getSelectedItem().toString()), Integer.parseInt(minuteCom.getSelectedItem().toString()));

                    if (wayNum.getText().isEmpty() || ((!once.isSelected()) && (!tenMin.isSelected()) && (!hour.isSelected()) && (!day.isSelected()))) {
                        JOptionPane.showMessageDialog(null,
                                "提醒方式或策略不能为空", " 提醒方式", JOptionPane.ERROR_MESSAGE);
                    } else if (localTimeB.compareTo(nowTime) > 0) {//loB-noT
                        JOptionPane.showMessageDialog(null,
                                "时间不能设置小于当前时间", " 提醒方式", JOptionPane.ERROR_MESSAGE);
                    } else if ((!(alarmEvent instanceof GeneralEvent && ((GeneralEvent) alarmEvent).isWithoutSpecificTimeSetting())) & localTimeB.compareTo(alarmEvent.getTimeEnd()) < 0) {
                        JOptionPane.showMessageDialog(null,
                                "时间不能设置大于事件的终止时间", " 提醒方式", JOptionPane.ERROR_MESSAGE);
                    } else {
                        alarmEvent.getAlarm().setIsAlarm(true);
                        alarmEvent.getAlarm().setEarlyTime(localTimeB);
                        alarmEvent.getAlarm().setFirstSetTime(new LocalTime(localTimeB.getDate(), localTimeB.getHour(), localTimeB.getMinute()));
                        alarmEvent.getAlarm().setAlarmCount(0);
                        alarmFrame.setVisible(false);
                        display.refresh();
                        detailsDisplay.refresh();
                    }
                }
            }
        });
        jPanel.add(jButton);
        return jPanel;
    }

    protected JTextField getTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Serif", 0, 20));
        textField.setPreferredSize(new Dimension(150, 30));
        return textField;
    }

    protected JComboBox getComboBox() {
        JComboBox jComboBox = new JComboBox();
        jComboBox.setPreferredSize(new Dimension(100, 30));
        return jComboBox;
    }

    /*新加flx的代码*/
    private JPanel getWayPanel(Event alarmEvent) {
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        JMenuBar alarmBar = new JMenuBar();
        alarmBar.setOpaque(false);
        JLabel wayLabel = DisplayUtil.getLabel("提醒方式:", true);
        wayLabel.setSize(new Dimension(300, 300));
        wayNum = getTextField();
        wayNum.setEditable(false);
        JMenu wayBar = new JMenu("方式");
        wayBar.setOpaque(false);
        wayBar.setSize(new Dimension(300, 300));
        wayBar.setFont(new Font("Serif", 0, 20));
        JMenuItem way1 = new JMenuItem("界面区域提醒");
        JMenuItem way2 = new JMenuItem("对话框提醒");
        JMenuItem way4 = new JMenuItem("两者方式都显示");
        way1.setFont(new Font("Serif", 0, 18));
        way2.setFont(new Font("Serif", 0, 18));
        way4.setFont(new Font("Serif", 0, 18));
        wayBar.add(way1);
        wayBar.add(way2);
        wayBar.add(way4);
        way1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmEvent.getAlarm().setContext(alarmEvent.getEventString());
                alarmEvent.getAlarm().setTypeOfWay(Alarm.wayType.INTERFACE.ordinal());
                //alarmEvent.getAlarm().setOnlyFace(true);
                wayNum.setText("界面区域提醒");
            }
        });
        way2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmEvent.getAlarm().setContext(alarmEvent.getEventString());
                alarmEvent.getAlarm().setTypeOfWay(Alarm.wayType.DIALOGUE.ordinal());
                wayNum.setText("对话框提醒");
            }
        });
        way4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmEvent.getAlarm().setContext(alarmEvent.getEventString());
                alarmEvent.getAlarm().setTypeOfWay(Alarm.wayType.BOTH.ordinal());
                //alarmEvent.getAlarm().setOnlyFace(true);
                wayNum.setText("两种提醒");
            }
        });
        alarmBar.add(wayBar);
        jPanel.add(wayLabel);
        jPanel.add(wayNum);
        jPanel.add(alarmBar);
        return jPanel;
    }
}