package DisplayWindows;

import DateRelated.BuildObjectException;
import DateRelated.CalendarDate;
import Event.AnniversaryEvent;
import Event.Event;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/**
 * Created by Think on 2018/5/19.
 */
public class DisplayAddAnniversaryEvent extends DisplayAddEvent {
    private JTextField anniversaryNameField = DisplayUtil.getTextField();
    private JComboBox<String> anniversaryTypeBox = DisplayUtil.getSearchComboBox();

    public DisplayAddAnniversaryEvent(CalendarDate date, Event superEvent, Display display,DisplayEventDetails detailsDisplay) {
        super(date, superEvent, display,detailsDisplay);
    }

    private String getAnniversaryNameField() {
        return this.anniversaryNameField.getText();
    }

    private String getAnniversaryTypeField() {
        return this.anniversaryTypeBox.getSelectedItem().toString();
    }


    //王佳惠新添的方法，用来产生地点和会议主题
    private JPanel getNameAndType() {
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        JLabel nameLabel = DisplayUtil.getLabel("纪念日名称：", true);
        JLabel typeLabel = DisplayUtil.getLabel("纪念日类型：", true);
        jPanel.add(nameLabel);
        jPanel.add(anniversaryNameField);
        anniversaryTypeBox.addItem("结婚纪念日");
        anniversaryTypeBox.addItem("生日");
        anniversaryTypeBox.addItem("节日");
        anniversaryTypeBox.setFont(new Font("Serif", 0, 20));
        jPanel.add(typeLabel);
        jPanel.add(anniversaryTypeBox);
        return jPanel;
    }

    @Override
    protected JPanel getEventPanel() {
        String eventString = "请输入纪念日的详细信息";
        eventTextarea = new JTextArea(eventString, 6, 20);
        eventTextarea.setFont(new Font("Serif", 0, 20));
        eventTextarea.setLineWrap(true);
        eventTextarea.setWrapStyleWord(true);
        JButton saveEvent = DisplayUtil.getFormalBt("保存");
        saveEvent.addActionListener(saveActionListener());
        JPanel panelHeader = new JPanel();
        panelHeader.setOpaque(false);
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.X_AXIS));

        //新添加
        JPanel meetingCom = getNameAndType();
        anniversaryTypeBox.setSelectedIndex(1);
        panelHeader.add(meetingCom);
        panelHeader.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelHeader.add(saveEvent);
        panelHeader.setSize(500, 80);

        JPanel jPanel = new NewPanel("cat.jpg");
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(panelHeader);
        JPanel getTimeJPanel = timePanel.getTimePanel();
        //add by ZZY
        timePanel.setIsWholeDay(true);
        timePanel.setWholeDayButtonInvisible();
        // add by ZZY
        getTimeJPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        jPanel.add(getTimeJPanel);

        jPanel.add(getEmImJPanel());
        eventTextarea.setMaximumSize(new Dimension(700,250));
        jPanel.add(eventTextarea);
        return jPanel;
    }


    @Override
    protected AnniversaryEvent getEvent() throws BuildObjectException {
        String eventString = getEventString();
        if (eventString == null || eventString.length() == 0) {
            JOptionPane.showMessageDialog(null,
                    " 请检查你的事件描述是否非空 ", " 日历", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String anniversaryNameString = getAnniversaryNameField();
        String anniversaryTypeString = getAnniversaryTypeField();
        if (anniversaryNameString.length() == 0 || anniversaryTypeString.length() == 0) {
            JOptionPane.showMessageDialog(null,
                    " 请检查你的纪念日名称和类型是否为空 ", " 日历", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        AnniversaryEvent event;
        event = new AnniversaryEvent(eventString, timePanel.getBeginDate(), anniversaryNameString, anniversaryTypeString, getIfEmergency(), getIfImportant());
        return event;
    }
}
