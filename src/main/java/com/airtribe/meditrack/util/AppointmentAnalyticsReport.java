package com.airtribe.meditrack.util;

/**
 * Concrete report that demonstrates the Template Method pattern.
 */
public class AppointmentAnalyticsReport extends AbstractReportTemplate {
    private final int totalAppointments;
    private final int confirmedAppointments;
    private final int completedAppointments;
    private final int cancelledAppointments;

    public AppointmentAnalyticsReport(int totalAppointments, int confirmedAppointments,
                                      int completedAppointments, int cancelledAppointments) {
        this.totalAppointments = totalAppointments;
        this.confirmedAppointments = confirmedAppointments;
        this.completedAppointments = completedAppointments;
        this.cancelledAppointments = cancelledAppointments;
    }

    @Override
    protected String buildTitle() {
        return "MediTrack Appointment Analytics";
    }

    @Override
    protected String buildBody() {
        return "Total Appointments: " + totalAppointments + "\n"
                + "Confirmed Appointments: " + confirmedAppointments + "\n"
                + "Completed Appointments: " + completedAppointments + "\n"
                + "Cancelled Appointments: " + cancelledAppointments;
    }

    @Override
    protected String buildSummary() {
        return "Template Method: title, body, and summary are assembled in a fixed order.";
    }
}
