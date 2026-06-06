package com.airtribe.meditrack.util;

/**
 * Template Method base class for generating simple text reports.
 */
public abstract class AbstractReportTemplate {

    public final String generate() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildTitle());
        builder.append('\n');
        builder.append(buildBody());
        builder.append('\n');
        builder.append(buildSummary());
        return builder.toString();
    }

    protected abstract String buildTitle();

    protected abstract String buildBody();

    protected String buildSummary() {
        return "Report generated successfully.";
    }
}
