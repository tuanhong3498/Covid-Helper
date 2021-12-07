package com.example.covidhelper.model;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

import com.example.covidhelper.R;

import java.util.Locale;

public enum SopPhase
{
    PHASE_1("Phase 1", R.color.red_dark),
    PHASE_2("Phase 2", R.color.yellow_medium),
    PHASE_3("Phase 3", R.color.green_medium),
    PHASE_4("Phase 4", R.color.blue_medium);

    @ColorRes
    private final int colorID;
    private final String title;

    SopPhase(String title, @ColorRes int colorID)
    {
        this.colorID = colorID;
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    @ColorRes
    public int getColorID()
    {
        return colorID;
    }

    private static final SopPhase[] sopPhases = values();
    public static SopPhase fromString(String text)
    {
        for(SopPhase phase: sopPhases)
        {
            if(phase.title.toString().toLowerCase(Locale.US).equals(text.toLowerCase(Locale.US)))
                return phase;
        }
        throw new IllegalArgumentException("No constant with text " + text + " found in Enum SopPhase");
    }
}
