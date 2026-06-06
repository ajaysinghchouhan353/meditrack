package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.interfaces.AppointmentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Notification service implementing Observer design pattern.
 * Manages appointment listeners and sends notifications.
 * Demonstrates concurrency with TimerTask.
 */
public class NotificationService {
    private final List<AppointmentListener> listeners = new ArrayList<>();
    private final Timer reminderTimer = new Timer(true); // Daemon thread

    /**
     * Register an appointment listener.
     * @param listener the listener to register
     */
    public void registerListener(AppointmentListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Unregister an appointment listener.
     * @param listener the listener to unregister
     */
    public void unregisterListener(AppointmentListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify all listeners that appointment is booked.
     * @param appointment the booked appointment
     */
    public void notifyAppointmentBooked(Appointment appointment) {
        for (AppointmentListener listener : listeners) {
            listener.onAppointmentBooked(appointment);
        }
    }

    /**
     * Notify all listeners that appointment is cancelled.
     * @param appointment the cancelled appointment
     */
    public void notifyAppointmentCancelled(Appointment appointment) {
        for (AppointmentListener listener : listeners) {
            listener.onAppointmentCancelled(appointment);
        }
    }

    /**
     * Notify all listeners that appointment is completed.
     * @param appointment the completed appointment
     */
    public void notifyAppointmentCompleted(Appointment appointment) {
        for (AppointmentListener listener : listeners) {
            listener.onAppointmentCompleted(appointment);
        }
    }

    /**
     * Schedule appointment reminder.
     * @param appointment the appointment
     * @param minutesBefore minutes before appointment to send reminder
     */
    public void scheduleReminder(Appointment appointment, int minutesBefore) {
        long delayMs = minutesBefore * 60 * 1000L;
        
        reminderTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                notifyAppointmentReminder(appointment);
            }
        }, delayMs);
    }

    /**
     * Notify all listeners of appointment reminder.
     * @param appointment the appointment
     */
    public void notifyAppointmentReminder(Appointment appointment) {
        for (AppointmentListener listener : listeners) {
            listener.onAppointmentReminder(appointment);
        }
    }

    /**
     * Get the number of registered listeners.
     * @return listener count
     */
    public int getListenerCount() {
        return listeners.size();
    }

    /**
     * Shutdown the notification service.
     */
    public void shutdown() {
        reminderTimer.cancel();
    }
}
