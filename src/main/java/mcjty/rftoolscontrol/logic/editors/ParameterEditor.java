package mcjty.rftoolscontrol.logic.editors;

import mcjty.lib.gui.widgets.Panel;
import mcjty.rftoolscontrol.logic.registry.ParameterValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public interface ParameterEditor {

    void build(Minecraft mc, Gui gui, Panel panel, ParameterEditorCallback callback);

    ParameterValue readValue();

    void writeValue(ParameterValue value);

    default int getHeight() { return 20; }

    // Call this to set the editor in 'constant only' mode
    void constantOnly();
}
