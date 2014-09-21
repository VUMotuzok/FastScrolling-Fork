package de.santiv.fastscrolling.configuration;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.ComboBox;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import de.santiv.fastscrolling.enums.Hotkey;
import de.santiv.fastscrolling.enums.Strings;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Configuration implements Configurable {
    public static final String STEP_DEFAULT_VALUE = "1000";
    public static final String HOTKEY_DEFAULT_VALUE = Hotkey.CTRL.name();

    private JTextField step;
    private JComboBox<Hotkey> hotkey;

    private boolean modified = false;

    @Nls
    @Override
    public String getDisplayName() {
        return Strings.CONF__DISPLAY_NAME.getDescription();
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

        PanelBuilder builder = new PanelBuilder(new FormLayout("pref, 10dlu, 50dlu, 50dlu", "p, 5dlu, p, 5dlu, p, 5dlu"));

        CellConstraints cc = new CellConstraints();
        builder.addLabel(Strings.CONF__STEP.getDescription(), cc.rcw(1, 1, 2));
        builder.add(step, cc.rc(1, 3));
        builder.addLabel(Strings.CONF__HOTKEY.getDescription(), cc.rc(3, 1));
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
        hotkey = new ComboBox(Hotkey.values());
        hotkey.setEditable(false);
    }


    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        saveStepValue(step.getText());
        saveHotkey((Hotkey) hotkey.getSelectedItem());

        modified = false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }


    public static int loadStepValue() {
        return Integer.valueOf(PropertiesComponent.getInstance().getValue(Strings.getPropertyPath(Strings.CONF__STEP), STEP_DEFAULT_VALUE));
    }

    private void saveStepValue(String value) {
        PropertiesComponent.getInstance().setValue(Strings.getPropertyPath(Strings.CONF__STEP), value);
    }

    public static Hotkey loadHotkey() {
        return Hotkey.valueOf(PropertiesComponent.getInstance().getValue(Strings.getPropertyPath(Strings.CONF__HOTKEY), HOTKEY_DEFAULT_VALUE));
    }

    public void saveHotkey(Hotkey hotkey) {
        PropertiesComponent.getInstance().setValue(Strings.getPropertyPath(Strings.CONF__HOTKEY), hotkey.name());
    }
}
