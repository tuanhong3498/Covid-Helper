package com.example.covidhelper.model;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.example.covidhelper.R;

import java.util.Locale;

public enum SOPStatus
{
    ALLOWED(R.drawable.ic_allowed, "allowed"),
    REQUIRE_VACCINATION(R.drawable.ic_syringe_double, "require vaccination"),
    NOT_ALLOWED(R.drawable.ic_not_allowed, "not allowed");

    @DrawableRes
    private final int drawableID;
    private final String title;

    SOPStatus(@DrawableRes int drawableID, String title)
    {
        this.drawableID = drawableID;
        this.title = title;
    }

    private static final SOPStatus[] sopStatus = values();
    public static SOPStatus fromString(String text)
    {
        for(SOPStatus status: sopStatus)
        {
            if(status.title.toString().toLowerCase(Locale.US).equals(text.toLowerCase(Locale.US)))
                return status;
        }
        // if no enum cannot be found
        throw new IllegalArgumentException("No constant with text " + text + " found in Enum SOPStatus");
    }

    public int getDrawableID()
    {
        return drawableID;
    }
}
