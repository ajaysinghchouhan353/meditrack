package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.interfaces.AppointmentListener;
import com.airtribe.meditrack.util.DateUtil;

/**
 * Console notification listener implementation.
 * Demonstrates Observer pattern - concrete listener for console notifications.
 */
public class ConsoleNotificationListener implements AppointmentListener {

    @Override
    public void onAppointmentBooked(Appointment appointment) {
        System.out.println("📅 [NOTIFICATION] Appointment Booked!");
        System.out.println("   Appointment ID: " + appointment.getId());
        System.out.println("   Date & Time: " + DateUtil.formatDateTime(appointment.getAppointmentDateTime()));
        System.out.println("   Reason: " + appointment.getReason());
    }

    @Override
    public void onAppointmentCancelled(Appointment appointment) {
        System.out.println("❌ [NOTIFICATION] Appointment Cancelled!");
        System.out.println("   Appointment ID: " + appointment.getId());
        System.out.println("   Previous Date: " + DateUtil.formatDateTime(appointment.getAppointmentDateTime()));
    }

    @Override
    public void onAppointmentCompleted(Appointment appointment) {
        System.out.println("✅ [NOTIFICATION] Appointment Completed!");
        System.out.println("   Appointment ID: " + appointment.getId());
        System.out.println("   Completed at: " + DateUtil.formatDateTime(appointment.getAppointmentDateTime()));
    }

    @Override
    public void onAppointmentReminder(Appointment appointment) {
        System.out.println("🔔 [REMINDER] Upcoming Appointment!");
        System.out.println("   Appointment ID: " + appointment.getId());
        System.out.println("   Scheduled for: " + DateUtil.formatDateTime(appointment.getAppointmentDateTime()));
        System.out.println("   Reason: " + appointment.getReason());
    }
}
