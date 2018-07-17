package DisplayWindows;

import DateRelated.*;
import DisplayWindows.DisplayAddGeneralEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;

import Event.Event;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * 这是王银燕新添加的类，目的为了在display页面和displayAddEvent页面实现对搜索时间框的复用，这个可以返回时间框面板，同时
 * 获得面板上的用户输入的时间信息
 */
public class DisplayTimePanel {
    private JTextField getBeginDate;
    private JTextField getEndDate;
    private JComboBox beginHour;
    private JComboBox beginMinute;
    private JComboBox endHour;
    private JComboBox endMinute;
    private JCheckBox wholeDay;
    private LocalTime beginTime;
    private LocalTime endTime;
    private boolean isWholeDay;
    private JPanel timePanel;

    public DisplayTimePanel(LocalTime beginTime, LocalTime endTime, boolean isWholeDay) {
        getBeginDate = DisplayUtil.getTextField();
        getEndDate = DisplayUtil.getTextField();
        beginHour = DisplayUtil.getSearchComboBox();
        beginMinute = DisplayUtil.getSearchComboBox();
        endHour = DisplayUtil.getSearchComboBox();
        endMinute = DisplayUtil.getSearchComboBox();
        this.beginTime = beginTime;
        this.endTime = endTime;
        wholeDay = new JCheckBox("全天");
        setIsWholeDay(isWholeDay);
        this.timePanel = init();
    }

    protected JPanel getTimePanel() {
        return timePanel;
    }

    protected void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
        setEditable(!isWholeDay);
    }

    protected void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        setEditable(!isWholeDay);
    }

    protected boolean getIsWholeDay() {
        return isWholeDay;
    }

    protected void setIsWholeDay(boolean isWholeDay, boolean isEnable) {
        wholeDay.setSelected(isWholeDay);
        wholeDay.setEnabled(isEnable);
    }

    protected void setIsWholeDay(boolean isWholeDay) {
        this.isWholeDay = isWholeDay;
        wholeDay.setSelected(isWholeDay);
        setEditable(!isWholeDay);
    }

    //获取用户输入的开始日期信息
    protected CalendarDate getBeginDate() throws BuildObjectException {
        String dateBeginString = getBeginDate.getText();
        CalendarDate date = new CalendarDate(dateBeginString);
        return date;
    }

    //获取用户输入的时间段信息
    protected LocalTime[] getLocalTimeFromJPanel() throws BuildObjectException {
        String dateBeginString = getBeginDate.getText();
        CalendarDate beginDate = new CalendarDate(dateBeginString);
        String dateEndString;
        CalendarDate endDate;
        // setEditable(!this.isWholeDay);
        if (!this.isWholeDay) {
            dateEndString = getEndDate.getText();
            endDate = new CalendarDate(dateEndString);
            this.beginTime = new LocalTime(beginDate, beginHour.getSelectedIndex(), beginMinute.getSelectedIndex());
            this.endTime = new LocalTime(endDate, endHour.getSelectedIndex(), endMinute.getSelectedIndex());
        } else {
            endDate = beginDate;
            this.beginTime = new LocalTime(beginDate, 0, 0);
            this.endTime = new LocalTime(endDate, 23, 59);
        }
        if (!DateUtil.isValid(beginDate) || !DateUtil.isValid(endDate)) {
            return null;
        }

        if (endDate.compareTo(beginDate) > 0) {
            return null;
        }
        LocalTime[] returnLocalTime = {beginTime, endTime};
        return returnLocalTime;
    }

    /*这是获取时间的面板，在这个面板上用户输入时间信息，并通过上面的getBeginDate()（如果是选择全天的话，则使用这个函数获取用户输入的日期），
    和geiLocalTime()（如果不是全天的话，则用这个获取用户输入的时间段信息）来获取用户输入的时间信息。
     */
    private JPanel init() {

        JLabel labelHour1 = DisplayUtil.getLabel("  小时:", true);
        JLabel labelMinute1 = DisplayUtil.getLabel("  分钟:", true);
        JLabel labelHour2 = DisplayUtil.getLabel("  小时:", true);
        JLabel labelMinute2 = DisplayUtil.getLabel("  分钟:", true);
        JLabel label1 = DisplayUtil.getLabel("开始时间:", true);
        JLabel label2 = DisplayUtil.getLabel("结束时间:", true);


        wholeDay.setFont(new Font("Serif", 0, 20));
        wholeDay.setOpaque(false);
        // getBeginDate.setText(beginTime.getDate().toString());
        wholeDay.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean selected = wholeDay.isSelected();
                setEditable(!selected);
            }
        });
        //  wholeDay.setSelected(true);
        JPanel panelTime = new JPanel();
        panelTime.setOpaque(false);
        panelTime.setMaximumSize(new Dimension(800, 100));
        panelTime.setLayout(new GridLayout(2, 7, 5, 5));
        panelTime.add(label1);
        panelTime.add(getBeginDate);
        panelTime.add(labelHour1);
        panelTime.add(beginHour);
        panelTime.add(labelMinute1);
        panelTime.add(beginMinute);
        panelTime.add(wholeDay);
        panelTime.add(label2);
        panelTime.add(getEndDate);
        panelTime.add(labelHour2);
        panelTime.add(endHour);
        panelTime.add(labelMinute2);
        panelTime.add(endMinute);
        return panelTime;
    }

    //设置小时 时间的选择框是否是可用的，如果不可用，则此时一定是选择了“全天”的选择项，则将时间段自动设置为一天
    protected void setEditable(boolean isEditable) {
        this.isWholeDay = !isEditable;
        getBeginDate.setText(beginTime.getDate().toString());
        if (!isEditable) {
            getEndDate.setText("");
            endHour.removeAllItems();
            beginHour.removeAllItems();
            endMinute.removeAllItems();
            beginMinute.removeAllItems();
            endHour.addItem(23);
            beginHour.addItem(0);
            beginMinute.addItem(0);
            endMinute.addItem(59);
            getEndDate.setEditable(false);
            endTime = beginTime;
        } else {
            endHour.removeAllItems();
            beginHour.removeAllItems();
            endMinute.removeAllItems();
            beginMinute.removeAllItems();
            for (int i = 0; i < 24; i++) {
                beginHour.addItem(i);
                endHour.addItem(i);
            }
            for (int i = 0; i < 60; i++) {
                beginMinute.addItem(i);
                endMinute.addItem(i);
            }
            endHour.setSelectedIndex(endTime.getHour());
            endMinute.setSelectedIndex(endTime.getMinute());
            beginHour.setSelectedIndex(beginTime.getHour());
            beginMinute.setSelectedIndex(beginTime.getMinute());
            beginHour.setEditable(isEditable);
            beginMinute.setEditable(isEditable);
            endHour.setEditable(isEditable);
            endMinute.setEditable(isEditable);
            getEndDate.setEditable(isEditable);
            getEndDate.setText(endTime.getDate().toString());
        }
    }

    //add by ZZY
    protected void setWholeDayButtonInvisible() {
        wholeDay.setVisible(false);
    }
}
