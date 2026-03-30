package com.example.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationReport {
    private final List<String> issues = new ArrayList<>();

    public void addIssue(String issue) {
        if (issue != null && !issue.isBlank()) {
            issues.add(issue);
        }
    }

    public boolean isValid() {
        return issues.isEmpty();
    }

    public List<String> getIssues() {
        return Collections.unmodifiableList(issues);
    }
}
