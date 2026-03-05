package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.DateUtil;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;
import com.airtribe.meditrack.constants.AppointmentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing appointments with Observer pattern integration.
 * Demonstrates notification of appointment events through NotificationService.
 */
public class AppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    private final DataStore<Appointment> appointmentStore;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final NotificationService notificationService;

    public AppointmentService(DataStore<Appointment> appointmentStore, 
                             DoctorService doctorService, PatientService patientService,
                             NotificationService notificationService) {
        this.appointmentStore = appointmentStore;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.notificationService = notificationService;
    }

    /**
     * Book a new appointment.
     * @param doctorId the doctor's ID
     * @param patientId the patient's ID
     * @param appointmentDateTime the appointment date and time
     * @param reason the reason for appointment
     * @return the booked appointment
     */
    public Appointment bookAppointment(String doctorId, String patientId, 
                                       LocalDateTime appointmentDateTime, String reason)
            throws InvalidDataException, AppointmentNotFoundException {
        logger.debug("Booking appointment: doctorId={}, patientId={}, dateTime={}", doctorId, patientId, appointmentDateTime);
        // Validate data
        Validator.validateOrThrow(Validator.isNotEmpty(doctorId), "Doctor ID cannot be empty");
        Validator.validateOrThrow(Validator.isNotEmpty(patientId), "Patient ID cannot be empty");
        Validator.validateOrThrow(appointmentDateTime != null, "Appointment date-time cannot be null");
        Validator.validateOrThrow(Validator.isNotEmpty(reason), "Reason cannot be empty");

        // Verify doctor and patient exist
        if (!doctorService.getDoctorById(doctorId).isPresent()) {
            logger.error("Doctor not found for appointment: id={}", doctorId);
            throw new AppointmentNotFoundException("Doctor not found with ID: " + doctorId);
        }
        if (!patientService.getPatientById(patientId).isPresent()) {
            logger.error("Patient not found for appointment: id={}", patientId);
            throw new AppointmentNotFoundException("Patient not found with ID: " + patientId);
        }

        // Validate appointment timing
        Validator.validateOrThrow(DateUtil.isFutureDateTime(appointmentDateTime),
                "Appointment must be in the future");

        String appointmentId = IdGenerator.generateAppointmentId();
        Appointment appointment = new Appointment(appointmentId, doctorId, patientId,
                appointmentDateTime, reason);
        appointmentStore.add(appointment);
        
        // Notify listeners of appointment booking (Observer Pattern)
        notificationService.notifyAppointmentBooked(appointment);
        logger.info("Appointment booked successfully: id={}, doctor={}, patient={}", appointmentId, doctorId, patientId);
        
        return appointment;
    }

    /**
     * Get an appointment by ID.
     * @param appointmentId the appointment's ID
     * @return Optional containing the appointment or empty
     */
    public Optional<Appointment> getAppointmentById(String appointmentId) {
        return appointmentStore.findById(appointmentId);
    }

    /**
     * Get all appointments.
     * @return list of all appointments
     */
    public List<Appointment> getAllAppointments() {
        return appointmentStore.getAll();
    }

    /**
     * Get appointments by doctor ID.
     * @param doctorId the doctor's ID
     * @return list of appointments for the doctor
     */
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointmentStore.filter(apt -> apt.getDoctorId().equals(doctorId));
    }

    /**
     * Get appointments by patient ID.
     * @param patientId the patient's ID
     * @return list of appointments for the patient
     */
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointmentStore.filter(apt -> apt.getPatientId().equals(patientId));
    }

    /**
     * Get appointments by status.
     * @param status the status enum
     * @return list of appointments with the given status
     */
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentStore.filter(apt -> apt.getStatus() == status);
    }

    /**
     * Cancel an appointment.
     * @param appointmentId the appointment's ID
     * @return true if cancelled, false otherwise
     */
    public boolean cancelAppointment(String appointmentId) throws AppointmentNotFoundException {
        logger.debug("Attempting to cancel appointment: id={}", appointmentId);
        Optional<Appointment> optionalAppointment = appointmentStore.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setStatus(AppointmentStatus.CANCELLED);
            boolean updated = appointmentStore.update(appointmentId, appointment);
            
            // Notify listeners of cancellation (Observer Pattern)
            if (updated) {
                notificationService.notifyAppointmentCancelled(appointment);
                logger.info("Appointment cancelled successfully: id={}", appointmentId);
            }
            return updated;
        }
        logger.error("Appointment not found for cancellation: id={}", appointmentId);
        throw new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId);
    }

    /**
     * Mark appointment as completed.
     * @param appointmentId the appointment's ID
     * @return true if marked completed, false otherwise
     */
    public boolean markAppointmentCompleted(String appointmentId) throws AppointmentNotFoundException {
        logger.debug("Attempting to mark appointment as completed: id={}", appointmentId);
        Optional<Appointment> optionalAppointment = appointmentStore.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setStatus(AppointmentStatus.COMPLETED);
            boolean updated = appointmentStore.update(appointmentId, appointment);
            
            // Notify listeners of completion (Observer Pattern)
            if (updated) {
                notificationService.notifyAppointmentCompleted(appointment);
                logger.info("Appointment marked as completed: id={}", appointmentId);
            }
            return updated;
        }
        logger.error("Appointment not found for completion: id={}", appointmentId);
        throw new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId);
    }

    /**
     * Add notes to an appointment.
     * @param appointmentId the appointment's ID
     * @param notes the notes to add
     * @return true if updated, false otherwise
     */
    public boolean addAppointmentNotes(String appointmentId, String notes) 
            throws AppointmentNotFoundException {
        Optional<Appointment> optionalAppointment = appointmentStore.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setNotes(notes);
            return appointmentStore.update(appointmentId, appointment);
        }
        throw new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId);
    }

    /**
     * Get the total number of appointments.
     * @return total number of appointments
     */
    public int getTotalAppointments() {
        return appointmentStore.size();
    }

    /**
     * Get the total number of booked appointments.
     * @return total booked appointments
     */
    public int getTotalBookedAppointments() {
        return appointmentStore.filter(apt -> apt.getStatus() == AppointmentStatus.CONFIRMED).size();
    }
}
