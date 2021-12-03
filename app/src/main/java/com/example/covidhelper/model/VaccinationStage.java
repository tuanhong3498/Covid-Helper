package com.example.covidhelper.model;

import androidx.annotation.DrawableRes;

import com.example.covidhelper.R;

import java.util.Locale;

public enum VaccinationStage
{
    REGISTER(R.drawable.ic_vaccine_register, "Registration"),
    DOSE_1(R.drawable.ic_syringe, "Dose 1"),
    DOSE_2(R.drawable.ic_syringe, "Dose 2"),
    WAIT_14_DAYS(R.drawable.ic_vaccine_wait, "Wait 14 Days"),
    COMPLETED(R.drawable.ic_vaccine_cert, "Fully Vaccinated");

    private final int drawableID;
    private final String title;

    // get a list of all stages to reduce copying
    private static final VaccinationStage[] vaccinationStages = values();

    VaccinationStage(@DrawableRes int drawableID, String title)
    {
        this.drawableID = drawableID;
        this.title = title;
    }

    public static VaccinationStage fromString(String text)
    {
        for(VaccinationStage stage : vaccinationStages)
        {
            if(stage.getTitle().toLowerCase(Locale.US).equals(text.toLowerCase(Locale.US)))
                return stage;
        }
        // if no enum cannot be found
        throw new IllegalArgumentException("No constant with text " + text + " found in Enum VaccinationStage");
    }

    public @DrawableRes int getDrawableID()
    {
        return drawableID;
    }

    public String getTitle()
    {
        return title;
    }

    public VaccinationStage next()
    {
        // if it is the last stage -> return null
        if (this.ordinal() == vaccinationStages.length-1)
            return this;
        // otherwise return the next stage
        return vaccinationStages[this.ordinal() + 1];
    }

    public VaccinationStage previous()
    {
        // if it is the first stage -> return null
        if (this.ordinal() == 0)
            return this;
        // otherwise return the previous stage
        return vaccinationStages[this.ordinal() - 1];
    }
}
