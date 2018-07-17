package DisplayWindows;

import DateRelated.DateUtil;

import Event.*;
import Event.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DisplayEventDetails {
    private Event event;
    private Display display;
    private JFrame jFrame = new JFrame("事件详情");

    public DisplayEventDetails(Event event,Display display) {
        this.event = event;
        this.display = display;
    }

    protected void init() {
        jFrame.setSize(800, 800);
        jFrame.setContentPane(getEventDetialsPanel());
        jFrame.setVisible(true);
        Timer timeAction = new Timer(60000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        timeAction.start();
    }

    private JPanel getEventDetialsPanel() {
        JPanel headerJPanel = getHeader();
        JPanel bodyJPanel = getBody();
        JPanel jPanel = new NewPanel("cat.jpg");
        jPanel.setLayout(new BorderLayout());
        jPanel.add(headerJPanel, BorderLayout.NORTH);
        jPanel.add(bodyJPanel, BorderLayout.CENTER);

        return jPanel;
    }

    private JPanel getHeader() {
        JPanel header2 = new JPanel();
        JPanel header = new JPanel();
        JPanel header1 = new JPanel();
        JPanel sideBar = new JPanel();
        header.setOpaque(false);
        header1.setOpaque(false);
        header2.setOpaque(false);
        sideBar.setOpaque(false);
        header2.setLayout(new BorderLayout());
        header.setLayout(new BoxLayout(header,BoxLayout.X_AXIS));
        header1.setLayout(new BorderLayout());
        sideBar.setLayout(new BoxLayout(sideBar,BoxLayout.Y_AXIS));
        String showEventType = getEventType() + "\n" + event.getTimeBegin().toString() + " - " + event.getTimeEnd().toString();
        JLabel jLabel = DisplayUtil.getLabel(showEventType, true);
        jLabel.setBorder(BorderFactory.createEmptyBorder(10,200,10,10));
        header2.add(jLabel,BorderLayout.NORTH);

        JRadioButton checkComplete = new JRadioButton("完成");
        checkComplete.setFont(new Font("Serif", 0, 25));
        checkComplete.setMargin(new Insets(10,10,10,10));
        checkComplete.setOpaque(false);
        ArrayList<Event> subEvents = event.getSubEvent();
        if (subEvents.size() != 0) {
            boolean parentComplete = true;
            for (Event subEvent : subEvents) {
                if (!subEvent.getComplete()) {
                    parentComplete = false;
                }
            }
            event.setComplete(parentComplete);
            Event.setEventState(event);
            checkComplete.setSelected(parentComplete);
        } else {
            checkComplete.setSelected(event.getComplete());
        }
        checkComplete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (subEvents.size() == 0) {
                    if (checkComplete.isSelected() && !event.getState().equals("未开始") && !event.getState().equals("过期")) {
                        event.setComplete(true);
                        Event.setEventState(event);
                        refresh();
                    } else {
                        checkComplete.setSelected(true);
                        event.setComplete(false);
                        Event.setEventState(event);
                        refresh();
                    }
                } else {
                    boolean parentComplete = true;
                    for (Event subEvent : subEvents) {
                        if (!subEvent.getComplete()) {
                            parentComplete = false;
                        }
                    }
                    event.setComplete(parentComplete);
                    checkComplete.setSelected(parentComplete);
                    Event.setEventState(event);
                    refresh();
                }
            }
        });
        sideBar.add(checkComplete);

       // if (!(event instanceof GeneralEvent && ((GeneralEvent) event).isWithoutSpecificTimeSetting())) {
            if (event.getAlarm().getIsAlarm()) {
                sideBar.add(alarmCom(event));
            } else
                sideBar.add(alarmButton(event)); // 设置提醒 by fulixue
       // }

        JMenuBar bar =  new JMenuBar();
        switch (event.getType()) {//纪念日=0，课程=1, 约会=2，会议=3，面试=4，旅行=5，自定义=6
            //目前互斥集合为（会议，课程，约会，面试）
            case 0:
                //(自定义、纪念日，旅行）
            case 5:
            case 6:
                bar = display.subMenuBar(DisplayEventDetails.this,event);
                break;
            case 1:
                bar = display.subMenuBar(DisplayEventDetails.this,event);
                break;
            case 2:
                bar = display.subMenuBar(DisplayEventDetails.this,event);
                break;
            case 3:
                bar = display.subMenuBar(DisplayEventDetails.this,event);
                break;
            case 4:
                bar = display.subMenuBar(DisplayEventDetails.this,event);
                break;
            default:
                break;
        }

        if(!event.getState().equals("过期")){
            bar.setMaximumSize(new Dimension(300, 50));
            sideBar.add(bar);
        }

        sideBar.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        JTextArea eventTextArea = DisplayUtil.getTextarea(event.showEvent());
        header1.add(eventTextArea);
        header.add(header1);
        header.add(sideBar);

        header2.add(header,BorderLayout.CENTER);
        JLabel jLabel1 = DisplayUtil.getLabel("子类待办事项：",true);
        jLabel1.setBorder(BorderFactory.createEmptyBorder(10,300,10,10));
        header2.add(jLabel1,BorderLayout.SOUTH);



        return header2;
    }
    private JButton alarmButton(Event alarmEvent) {
        JButton alarmBt = DisplayUtil.getFormalBt("设置提醒");
        alarmBt.setFont(new Font("Serif", 0, 25));
        alarmBt.setMaximumSize(new Dimension(125,50));

        alarmBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!alarmEvent.getComplete() && !alarmEvent.getState().equals("过期")) {
                    new DisplayAlarm(display,DisplayEventDetails.this).getAlarmPanel(alarmEvent);
                }else if(alarmEvent.getState().equals("过期")){
                    JOptionPane.showMessageDialog(null,
                            "事件已过期，不能设置提醒" , " 事项提醒 ", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null,
                            "事件已完成，不能设置提醒" , " 事项提醒 ", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        return alarmBt;
    }

    private JMenuBar alarmCom(Event alarmEvent) {
        JMenuBar alarmCom = new JMenuBar();
        alarmCom.setMaximumSize(new Dimension(300,50));
        alarmCom.setOpaque(false);
        JMenu bar = new JMenu("设置提醒");
        bar.setOpaque(false);
        JMenuItem item1 = new JMenuItem("重设提醒");
        JMenuItem item2 = new JMenuItem("取消提醒");
        bar.add(item1);
        bar.add(item2);
        alarmCom.add(bar);
        bar.setFont(new Font("Serif", 0, 25));
        item1.setFont(new Font("Serif", 0, 25));
        item2.setFont(new Font("Serif", 0, 25));
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!alarmEvent.getComplete() && !alarmEvent.getState().equals("过期")) {
                    new DisplayAlarm(display,DisplayEventDetails.this).getAlarmPanel(alarmEvent);
                }else if(alarmEvent.getState().equals("过期")){
                    JOptionPane.showMessageDialog(null,
                            "事件已过期，不能设置提醒" , " 事项提醒 ", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null,
                            "事件已完成，不能设置提醒" , " 事项提醒 ", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!alarmEvent.getComplete() && !alarmEvent.getState().equals("过期")) {
                    alarmEvent.getAlarm().setIsAlarm(false);
                    refresh();
                }else if(alarmEvent.getState().equals("过期")){
                    JOptionPane.showMessageDialog(null,
                            "事件已过期，不能设置提醒" , " 事项提醒 ", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null,
                            "事件已完成，不能设置提醒" , " 事项提醒 ", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        alarmCom.setPreferredSize(new Dimension(100, 44));

        return alarmCom;
    }




        private String getEventType() {
        if (event instanceof GeneralEvent) {
            return "自定义待办事项  ";
        }
        if (event instanceof AnniversaryEvent)
            return "有纪念日哦  ";
        if (event instanceof InterviewEvent)
            return "有面试哦  ";
        if (event instanceof CourseEvent)
            return "有课程哦  ";
        if (event instanceof MeetingEvent)
            return "有会议哦  ";
        if (event instanceof DateEvent)
            return "有约会哦  ";
        else
            return "有行程哦  ";

    }

    private JPanel getBody() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setOpaque(false);
        bodyPanel.setLayout(new BorderLayout());
        ArrayList<Event> subEvents = event.getSubEvent();
        JPanel subEventsPanel = new JPanel();
        subEventsPanel.setOpaque(false);
        JScrollPane subEventsScrollPanel = new JScrollPane();
        subEventsScrollPanel.setOpaque(false);
        subEventsScrollPanel.getViewport().setOpaque(false);
        //subEventsScrollPanel.setBackground(new Color(1,1,1,50));
        //subEventsScrollPanel.getViewport().setBackground(new Color(1,1,1,50));
        subEventsPanel.setLayout(new BoxLayout(subEventsPanel,BoxLayout.Y_AXIS));
        for (Event subEvent : subEvents) {
            JPanel btPanel = new JPanel();
            btPanel.setOpaque(false);
            JTextArea jTextArea = DisplayUtil.getTextarea(subEvent.showEvent());
            jTextArea.setEditable(false);
            JRadioButton checkComplete = new JRadioButton("完成");
            checkComplete.setBackground(new Color(100,150,100));
            checkComplete.setFont(new Font("serif",0,24));
            checkComplete.setSelected(subEvent.getComplete());
            Event.setEventState(subEvent);
            checkComplete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (checkComplete.isSelected() && !subEvent.getState().equals("未开始") && !subEvent.getState().equals("过期")) {
                        subEvent.setComplete(true);
                        Event.setEventState(subEvent);
                        refresh();
                    }else{
                        checkComplete.setSelected(true);
                    }
                }
            });
            if (subEvent.getAlarm().getIsAlarm()) {
                btPanel.add(alarmCom(subEvent));
            } else
                btPanel.add(alarmButton(subEvent));
            JButton deleteBt = DisplayUtil.getFormalBt("删除");

            btPanel.add(checkComplete);
            btPanel.add(deleteBt);
            deleteBt.setOpaque(true);
            deleteBt.setBackground(new Color(100,150,100));
            deleteBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    event.deleteSubEvent(subEvent);
                    refresh();
                }
            });
            subEventsPanel.add(jTextArea);
            subEventsPanel.add(btPanel);
        }
        subEventsPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        subEventsScrollPanel.setViewportView(subEventsPanel);
        bodyPanel.add(subEventsScrollPanel,BorderLayout.CENTER);
        return bodyPanel;
    }
    public void refresh(){
        jFrame.setContentPane(getEventDetialsPanel());
    }
}
