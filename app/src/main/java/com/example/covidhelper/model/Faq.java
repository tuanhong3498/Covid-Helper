package com.example.covidhelper.model;

public class Faq
{
    private final String question;
    private final String answer;

    public Faq(String question, String answer)
    {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion()
    {
        return question;
    }

    public String getAnswer()
    {
        return answer;
    }
}
