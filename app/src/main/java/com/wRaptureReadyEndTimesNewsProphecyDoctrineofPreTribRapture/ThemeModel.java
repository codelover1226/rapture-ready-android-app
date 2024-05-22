package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture;

import android.graphics.Color;

import java.util.HashMap;

public class ThemeModel {

    public static final String COLOR_KEY = "COLOR_KEY";

    private static final HashMap<Integer, String[]> themeMap;
    static {
        themeMap = new HashMap<>(0);
        themeMap.put(0, new String[]{"#263238", "#37474F", "#607DBB"});
        themeMap.put(1, new String[]{"#212121", "#424242", "#9E9E9E"});
        themeMap.put(2, new String[]{"#3E2723", "#4E342E", "#A1887F"});
        themeMap.put(3, new String[]{"#BF360C", "#DD2C00", "#DB4315"});
        themeMap.put(4, new String[]{"#E65100", "#EF6C00", "#F57C00"});
        themeMap.put(5, new String[]{"#FF6D00", "#FF9100", "#FFAB40"});
        themeMap.put(6, new String[]{"#33691E", "#558B2F", "#689F38"});
        themeMap.put(7, new String[]{"#1B5E20", "#2E7D32", "#38BE3C"});
        themeMap.put(8, new String[]{"#004D40", "#00695C", "#00B97B"});
        themeMap.put(9, new String[]{"#006064", "#00838F", "#0097A7"});
        themeMap.put(10, new String[]{"#01579B", "#0277BD", "#0288D1"});
        themeMap.put(11, new String[]{"#0D47A1", "#1565C0", "#1E88E5"});
        themeMap.put(12, new String[]{"#2962FF", "#2979FF", "#448AFF"});
        themeMap.put(13, new String[]{"#1A237E", "#283593", "#7986CB"});
        themeMap.put(14, new String[]{"#311B92", "#4527A0", "#9575CD"});
        themeMap.put(15, new String[]{"#880E4F", "#AD1457", "#E91E63"});
        themeMap.put(16, new String[]{"#B71C1C", "#C62828", "#E53935"});
    }

    public static ThemeModel getDefault(){
        String[] color = themeMap.get(0);
        assert color != null;

        return new ThemeModel(Color.parseColor(color[0]), Color.parseColor(color[1]), Color.parseColor(color[2]), 0);
    }

    public static ThemeModel get(Integer position){
        String[] color = themeMap.get(position);
        assert color != null;

        return new ThemeModel(Color.parseColor(color[0]), Color.parseColor(color[1]), Color.parseColor(color[2]), position);
    }

    public static int getSize(){
        return themeMap.size();
    }

    public final int STATUS_BAR_COLOR;
    public final int TOOLBAR_COLOR;
    public final int TAB_LAYOUT_BACKGROUND;
    public Integer mapPosition;

    public ThemeModel(int STATUS_BAR_COLOR, int TOOLBAR_COLOR, int TAB_LAYOUT_BACKGROUND, Integer mapPosition){
        this.STATUS_BAR_COLOR = STATUS_BAR_COLOR;
        this.TOOLBAR_COLOR = TOOLBAR_COLOR;
        this.TAB_LAYOUT_BACKGROUND = TAB_LAYOUT_BACKGROUND;
        this.mapPosition = mapPosition;
    }
}
