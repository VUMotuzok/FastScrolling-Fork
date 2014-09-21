package de.santiv.fastscrolling.enums;


import com.google.common.collect.Lists;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public enum Hotkey {
    CTRL("Ctrl", KeyEvent.VK_CONTROL, ActionEvent.CTRL_MASK),
    ALT("Alt", KeyEvent.VK_ALT, ActionEvent.ALT_MASK),
    CTRL_ALT("Ctrl + Alt", CTRL, ALT),;

    private String name;
    private int keyEvent;
    private int actionEvent;

    private List<Hotkey> combinations;


    Hotkey(String name, int keyEvent, int actionEvent) {
        this.name = name;
        this.keyEvent = keyEvent;
        this.actionEvent = actionEvent;
    }

    Hotkey(String name, Hotkey... hotkeys) {
        this.name = name;
        combinations = Lists.newArrayList(hotkeys);
    }

    public String getName() {
        return name;
    }

    public int getKeyEvent() {
        return keyEvent;
    }

    public int getActionEvent() {
        return actionEvent;
    }

    public List<Hotkey> getCombinations() {
        return combinations;
    }

    public boolean isCombination() {
        return combinations != null && combinations.size() > 0 ? true : false;
    }

    public int getModifiers() {
        if (isCombination()) {
            int sum = 0;

            for (Hotkey k : combinations) {
                sum += k.getActionEvent();
            }

            return sum;
        }

        return getActionEvent();
    }

    @Override
    public String toString() {
        return "[" + getName() + "]";
    }
}
