package mcjty.rftoolscontrol.config;

import mcjty.rftoolscontrol.RFToolsControl;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

import java.io.File;

public class ConfigSetup {
    public static final String CATEGORY_GENERAL = "general";

    public static int processorMaxenergy = 100000;
    public static int processorReceivepertick = 1000;

    public static int processorMaxloglines = 100;

    public static int coreSpeed[] = new int[] { 1, 4, 16 };
    public static int coreRFPerTick[] = new int[] { 4, 14, 50 };

    public static int VARIABLEMODULE_RFPERTICK = 1;
    public static int INTERACTMODULE_RFPERTICK = 2;
    public static int CONSOLEMODULE_RFPERTICK = 2;
    public static int VECTORARTMODULE_RFPERTICK = 2;

    public static boolean doubleClickToChangeConnector = true;
    public static int tooltipVerbosityLevel = 2;

    public static int maxGraphicsOpcodes = 30;
    public static int maxEventQueueSize = 100;
    public static int maxCraftRequests = 200;
    public static int maxStackSize = 100;
    public static Configuration mainConfig;

    public static void init() {
        mainConfig = new Configuration(new File(RFToolsControl.setup.getModConfigDir().getPath() + File.separator + "rftools", "control.cfg"));
        Configuration cfg = mainConfig;
        try {
            cfg.load();
            cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General settings");

            processorMaxenergy = cfg.get(CATEGORY_GENERAL, "processorMaxRF", processorMaxenergy,
                    "Maximum RF storage that the processor can hold").getInt();
            processorReceivepertick = cfg.get(CATEGORY_GENERAL, "processorRFPerTick", processorReceivepertick,
                    "RF per tick that the processor can receive").getInt();
            processorMaxloglines = cfg.get(CATEGORY_GENERAL, "processorMaxLogLines", processorMaxloglines,
                    "Maximum number of lines to keep in the log").getInt();
            maxStackSize = cfg.get(CATEGORY_GENERAL, "maxStackSize", maxStackSize,
                    "Maximum stack size for a program (used by 'call' opcode)").getInt();
            maxGraphicsOpcodes = cfg.get(CATEGORY_GENERAL, "maxGraphicsOpcodes", maxGraphicsOpcodes,
                    "Maximum amount of graphics opcodes that a graphics card supports").getInt();
            maxEventQueueSize = cfg.get(CATEGORY_GENERAL, "maxEventQueueSize", maxEventQueueSize,
                    "Maximum amount of event queue entries supported by a processor. More events will be ignored").getInt();
            maxCraftRequests = cfg.get(CATEGORY_GENERAL, "maxCraftRequests", maxCraftRequests,
                    "Maximum amount of craft requests supported by the crafting station. More requests will be ignored").getInt();
            doubleClickToChangeConnector = cfg.get(CATEGORY_GENERAL, "doubleClickToChangeConnector", doubleClickToChangeConnector,
                    "If true double click is needed in programmer to change connector. If false single click is sufficient").getBoolean();
            tooltipVerbosityLevel = cfg.get(CATEGORY_GENERAL, "tooltipVerbosityLevel", tooltipVerbosityLevel,
                    "If 2 tooltips in the programmer gui are verbose and give a lot of info. With 1 the information is decreased. 0 means no tooltips").getInt();
            coreSpeed[0] = cfg.get(CATEGORY_GENERAL, "speedB500", coreSpeed[0],
                    "Amount of instructions per tick for the CPU Core B500").getInt();
            coreSpeed[1] = cfg.get(CATEGORY_GENERAL, "speedS1000", coreSpeed[1],
                    "Amount of instructions per tick for the CPU Core S1000").getInt();
            coreSpeed[2] = cfg.get(CATEGORY_GENERAL, "speedEX2000", coreSpeed[2],
                    "Amount of instructions per tick for the CPU Core EX2000").getInt();
            coreRFPerTick[0] = cfg.get(CATEGORY_GENERAL, "rfB500", coreRFPerTick[0],
                    "RF per tick for the CPU Core B500").getInt();
            coreRFPerTick[1] = cfg.get(CATEGORY_GENERAL, "rfS1000", coreRFPerTick[1],
                    "RF per tick for the CPU Core S1000").getInt();
            coreRFPerTick[2] = cfg.get(CATEGORY_GENERAL, "rfEX2000", coreRFPerTick[2],
                    "RF per tick for the CPU Core EX2000").getInt();
            VARIABLEMODULE_RFPERTICK = cfg.get(CATEGORY_GENERAL, "variableModuleRFPerTick", VARIABLEMODULE_RFPERTICK,
                    "RF per tick/per block for the variable screen module").getInt();
            INTERACTMODULE_RFPERTICK = cfg.get(CATEGORY_GENERAL, "interactionModuleRFPerTick", INTERACTMODULE_RFPERTICK,
                    "RF per tick/per block for the interaction screen module").getInt();
            CONSOLEMODULE_RFPERTICK = cfg.get(CATEGORY_GENERAL, "consoleModuleRFPerTick", CONSOLEMODULE_RFPERTICK,
                    "RF per tick/per block for the console screen module").getInt();
            VECTORARTMODULE_RFPERTICK = cfg.get(CATEGORY_GENERAL, "vectorArtModuleRFPerTick", VECTORARTMODULE_RFPERTICK,
                    "RF per tick/per block for the vector art screen module").getInt();
        } catch (Exception e1) {
            FMLLog.log(Level.ERROR, e1, "Problem loading config file!");
        }
    }

    public static void postInit() {
        if (mainConfig.hasChanged()) {
            mainConfig.save();
        }
    }
}
