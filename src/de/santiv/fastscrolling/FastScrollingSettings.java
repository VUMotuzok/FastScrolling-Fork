package de.santiv.fastscrolling;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.ComboBox;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class FastScrollingSettings implements Configurable {
    private JTextField step;
    private JComboBox<FastScrollingHotkey> hotkey;

    private boolean modified = false;

    @Nls
    @Override
    public String getDisplayName() {
        return "Fast Scrolling";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        initComponents();
        loadValues();
        initListeners();

        PanelBuilder builder = new PanelBuilder(new FormLayout("pref, 10dlu, 50dlu, 50dlu", "p, 5dlu, p"));

        CellConstraints cc = new CellConstraints();
        builder.addLabel("Step", cc.rcw(1, 1, 2));
        builder.add(step, cc.rc(1, 3));
        builder.addLabel("Hotkey", cc.rc(3, 1));
        builder.add(hotkey, cc.rc(3, 3));

        return builder.getPanel();
    }

    private void loadValues() {
        int stepValue = loadStepValue();
        step.setText(String.valueOf(stepValue));

        hotkey.setSelectedItem(loadHotkey());
    }

    private void initListeners() {
        step.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setDirty();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setDirty();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setDirty();
            }
        });

        hotkey.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setDirty();
            }
        });
    }

    private void setDirty() {
        modified = true;
    }

    private void initComponents() {
        step = new JTextField();
        hotkey = new ComboBox(FastScrollingHotkey.values());
        hotkey.setEditable(false);
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        saveStepValue(step.getText());
        saveHotkey((FastScrollingHotkey) hotkey.getSelectedItem());

        modified = false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }

    public static int loadStepValue() {
        return Integer.valueOf(PropertiesComponent.getInstance().getValue("FastScrolling.step", "1000"));
    }

    private void saveStepValue(String value) {
        PropertiesComponent.getInstance().setValue("FastScrolling.step", value);
    }

    public static FastScrollingHotkey loadHotkey() {
        return FastScrollingHotkey.valueOf(PropertiesComponent.getInstance().getValue("FastScrolling.hotkey", "CTRL"));
    }

    public void saveHotkey(FastScrollingHotkey hotkey) {
        PropertiesComponent.getInstance().setValue("FastScrolling.hotkey", hotkey.name());
    }
}
