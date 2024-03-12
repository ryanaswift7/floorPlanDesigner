package Utils;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MouseAdapterHandler {
    private static List<MouseListener> savedMouseListeners = new ArrayList<>();
    private static List<MouseMotionListener> savedMouseMotionListeners = new ArrayList<>();
    private static JPanel canvasPanel;

    public static void setCanvasPanel(JPanel newCanvasPanel) {
        canvasPanel = newCanvasPanel;
    }

    public static void saveActiveMouseAdapters() {
        savedMouseListeners.clear();
        savedMouseMotionListeners.clear();

        // Save active MouseListeners
        MouseListener[] mouseListeners = canvasPanel.getMouseListeners();
        savedMouseListeners.addAll(Arrays.asList(mouseListeners));

        // Save active MouseMotionListeners
        MouseMotionListener[] motionListeners = canvasPanel.getMouseMotionListeners();
        savedMouseMotionListeners.addAll(Arrays.asList(motionListeners));
    }

    public static void removeAllMouseAdapters() {
        // Remove all MouseListeners
        for (MouseListener listener : savedMouseListeners) {
            canvasPanel.removeMouseListener(listener);
        }
        //savedMouseListeners.clear();

        // Remove all MouseMotionListeners
        for (MouseMotionListener listener : savedMouseMotionListeners) {
            canvasPanel.removeMouseMotionListener(listener);
        }
        //savedMouseMotionListeners.clear();
    }

    public static void restoreSavedMouseAdapters() {
        // Restore saved MouseListeners
        for (MouseListener listener : savedMouseListeners) {
            canvasPanel.addMouseListener(listener);
        }
        //savedMouseListeners.clear();

        // Restore saved MouseMotionListeners
        for (MouseMotionListener listener : savedMouseMotionListeners) {
            canvasPanel.addMouseMotionListener(listener);
        }
        //savedMouseMotionListeners.clear();
    }
}
