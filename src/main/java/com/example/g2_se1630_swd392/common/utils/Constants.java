package com.example.g2_se1630_swd392.common.utils;

public class Constants {
    public static class Login {
        public static final String LOGIN_GOOGLE = "LOGIN_GOOGLE";
        public static final String LOGIN_USERNAME_PASSWORD = "LOGIN_USERNAME_PASSWORD";
    }

    public static class Role {
        public static final Integer STUDENT = 1;
        public static final Integer TEACHER = 2;
        public static final Integer MANAGER = 3;
        public static final Integer ADMIN = 4;
    }

    public static class SystemConfig {
        public static final String EMAIL = "Email";
        public static final String SEMESTER = "Semester";
        public static final String ROLE = "Role";
        public static final String SUBJECT = "Subject";
    }

    public static class Permission {
        public static final String ISSUE_MANAGEMENT = "ISSUE_MANAGEMENT";
        public static final String MANAGE_PROJECTS = "MANAGE_PROJECTS";
        public static final String MANAGE_CLASSES = "MANAGE_CLASSES";
        public static final String MANAGE_SUBJECTS = "MANAGE_SUBJECTS";
        public static final String MANAGE_USERS = "MANAGE_USERS";
        public static final String MANAGE_SYSTEM_SETTINGS = "MANAGE_SYSTEM_SETTINGS";
    }

    public static class ClassStudentStatus {
        public static final String NO_PROJECT = "NO_PROJECT";
        public static final String ACTIVE = "ACTIVE";
        public static final String DEACTIVATE = "DEACTIVATE";
    }

    public static class Gitlab {
        public static final String PRE_GITLAB = "https://gitlab.com/api/v4";
    }

    public static class SendMail {
        public static final String FROM_ADDRESS = "trungab2305@gmail.com";
        public static final String SENDER_NAME = "IMS Company";
        public static final String SUBJECT = "Send your code";
        public static final String DEAR = "Dear [[name]],<br>";
        public static final String MAIN_CONTENT = "Please click the link below to verify your registration:<br>";
        public static final String CODE = "<p style=\"font-weight: bold;\">[[code]]</p>";
        public static final String THANK_YOU = "Thank you,<br>";
        public static final String COMPANY = "Group 2 Issue Management System";
    }


}
