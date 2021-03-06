package de.santiv.fastscrolling.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.ComboBox;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import de.santiv.fastscrolling.enums.Config;
import de.santiv.fastscrolling.enums.Hotkey;
import de.santiv.fastscrolling.enums.Strings;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class Configuration implements Configurable {
    private JTextField step;
    private JComboBox<Hotkey> hotkey;

    private JLabel reopenInfoLabel;

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
        loadAndSetValues();
        initListeners();

        // @formatter:off
        PanelBuilder builder = new PanelBuilder(new FormLayout("pref, 10dlu, 50dlu, 5dlu, pref",
                                                               "p, 5dlu, " +
                                                               "p, 5dlu"));

        CellConstraints cc = new CellConstraints();
        builder.addLabel(Strings.CONF__STEP.getDescription(),       cc.rc(1, 1));
        builder.add(step,                                           cc.rc(1, 3));
        builder.addLabel(Strings.CONF__HOTKEY.getDescription(),     cc.rc(3, 1));
        builder.add(hotkey,                                         cc.rc(3, 3));
        builder.add(reopenInfoLabel,                                cc.rc(3, 5));
        // @formatter:on

        return builder.getPanel();
    }

    private void loadAndSetValues() {
        step.setText(Config.loadValue(Config.STEP));
        hotkey.setSelectedItem(Config.loadEnumValue(Config.HOTKEY, Hotkey.class));
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
                showReopenInfo();
            }
        });
    }

    private void showReopenInfo() {
        if (hotkey.getSelectedItem().toString().equals(Config.loadValue(Config.HOTKEY))) {
            reopenInfoLabel.setText("");
        } else {
            reopenInfoLabel.setText(Strings.CONF__REOPEN_INFO.getDescription());
            reopenInfoLabel.setForeground(Color.red);
        }
    }

    private void setDirty() {
        modified = true;
    }

    private void initComponents() {
        step = new JTextField();
        hotkey = new ComboBox(Hotkey.values());
        hotkey.setEditable(false);

        reopenInfoLabel = new JLabel();
    }


    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        Config.saveValue(Config.STEP, step.getText());
        Config.saveValue(Config.HOTKEY, ((Hotkey) hotkey.getSelectedItem()).name());

        modified = false;
    }

    @Override
    public void reset() {
        loadAndSetValues();
        modified = false;
    }

    @Override
    public void disposeUIResources() {
    }
}
