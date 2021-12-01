package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//@Entity(foreignKeys = @ForeignKey(entity = SOP.class,
//        parentColumns = "phaseType",
//        childColumns = "phaseType"))
@Entity(indices = @Index(value = "phaseType", unique = true))
public class SOPContent {
    @PrimaryKey(autoGenerate = true)
    public int sopContentID;

    public String phaseType;
    public String dineIn;
    public String openSpaceSports;
    public String closeSpaceSports;
    public String withinStateTravel;
    public String interStateTravel;
    public String examClass;
    public String nonExamClass;
    public String socialActivity;

    public SOPContent(String phaseType, String dineIn, String openSpaceSports, String closeSpaceSports, String withinStateTravel, String interStateTravel, String examClass, String nonExamClass, String socialActivity) {
        this.phaseType = phaseType;
        this.dineIn = dineIn;
        this.openSpaceSports = openSpaceSports;
        this.closeSpaceSports = closeSpaceSports;
        this.withinStateTravel = withinStateTravel;
        this.interStateTravel = interStateTravel;
        this.examClass = examClass;
        this.nonExamClass = nonExamClass;
        this.socialActivity = socialActivity;
    }
}
