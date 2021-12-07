package com.example.covidhelper.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FAQ
{
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String question;
    public String answer;

    public FAQ(String question, String answer)
    {
        this.question = question;
        this.answer = answer;
    }
}
