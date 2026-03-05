package com.airtribe.meditrack.iface;

import com.airtribe.meditrack.entity.Appointment;

/**
 * Observer interface for appointment notifications.
 * Demonstrates Observer design pattern for loosely coupled notifications.
 */
public interface AppointmentListener {
    /**
     * Called when appointment is booked.
     * @param appointment the booked appointment
     */
    void onAppointmentBooked(Appointment appointment);

    /**
     * Called when appointment is cancelled.
     * @param appointment the cancelled appointment
     */
    void onAppointmentCancelled(Appointment appointment);

    /**
     * Called when appointment is completed.
     * @param appointment the completed appointment
     */
    void onAppointmentCompleted(Appointment appointment);

    /**
     * Called as appointment reminder.
     * @param appointment the upcoming appointment
     */
    void onAppointmentReminder(Appointment appointment);
}
