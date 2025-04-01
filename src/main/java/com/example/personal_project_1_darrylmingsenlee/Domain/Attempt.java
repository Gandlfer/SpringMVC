package com.example.personal_project_1_darrylmingsenlee.Domain;

import java.time.LocalDateTime;
import java.util.Map;

public class Attempt {
    public int id;
    public int profile_id;
    public LocalDateTime timestamp;

    public Map<Question,Choice> attempt_questions;
    public Attempt(int id, int profile_id, LocalDateTime timestamp, Map<Question,Choice> attempt_questions) {
        this.id = id;
        this.profile_id = profile_id;
        this.timestamp = timestamp;
        this.attempt_questions = attempt_questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<Question,Choice> getAttempt_questions() {
        return attempt_questions;
    }

    public void setAttempt_questions(Map<Question,Choice> attempt_questions) {
        this.attempt_questions = attempt_questions;
    }

}
