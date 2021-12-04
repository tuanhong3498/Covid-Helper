package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = SOPContent.class,
        parentColumns = "phaseType",
        childColumns = "phaseType"),
        indices = {@Index(value = "livingState", unique = true), @Index(value = "phaseType")})
public class SOP {
    @PrimaryKey(autoGenerate = true)
    public int sopID;

    public String livingState;
    public String phaseType;

    public SOP(String livingState, String phaseType) {
        this.livingState = livingState;
        this.phaseType = phaseType;
    }
}
