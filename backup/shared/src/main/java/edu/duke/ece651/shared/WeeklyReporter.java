package edu.duke.ece651.shared;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * This class is used to send weekly report based on system time.
 * @author Can Pei
 * @version 1.0
 */
public class WeeklyReporter implements Runnable {
    /**
     * List of all courses
     */
    private final List<Course> courses;

    /**
     * Constructor
     */
    public WeeklyReporter(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Check if it's weekend. If so, send weekly report of
     * sessions in the past week to professors and students.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // 获取当前时间
                LocalDateTime now = LocalDateTime.now();
                // 获取本周的最后一刻时间
                LocalDateTime endOfWeek = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
                        .withHour(23).withMinute(59).withSecond(59).withNano(999999999);

                // 如果当前时间已经过了本周末
                if (now.compareTo(endOfWeek) > 0) {
                    // 调用需要执行的函数
                    sendWeeklyReport();

                    // 等待下一个周末
                    long waitTime = Duration.between(now, endOfWeek.plusWeeks(1)).toMillis();
                    Thread.sleep(waitTime);
                } else {
                    // 等待直到本周末
                    long waitTime = Duration.between(now, endOfWeek).toMillis();
                    Thread.sleep(waitTime);
                }
            }
        } catch (InterruptedException | GeneralSecurityException | IOException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Send weekly report of sessions in the past week.
     */
    private void sendWeeklyReport() throws GeneralSecurityException, IOException {
        // 在这里实现每当自然周结束时需要调用的方法
        for (Course c: courses){
            for (Session s: c.getSessions()){
                LocalDateTime creationTime = getTimeAsLocalDateTime(s);
                LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);

                if (creationTime.isAfter(sevenDaysAgo)) {
                    for (Professor p: c.getProfessors()){
                        sendProfReport(s, p);
                    }
                    sendStuReport(s);
                }
            }
        }
    }

    /**
     * Refract session time syntax.
     * @param s is the session to change.
     */
    public LocalDateTime getTimeAsLocalDateTime(Session s) {
        return s.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Email target email address.
     * @param target is the email address to send to.
     * @param sub is the subject of the email.
     * @param body is the body of the email.
     */
    public void sendNotification(Email target, String sub, String body) throws GeneralSecurityException, IOException {
        Email eF = new Email("jzsun00@gmail.com");
        EmailNotification sender = new EmailNotification(eF, target);
        sender.sendEmail(sub, body);
    }

    /**
     * Email a professor the attendance report of a session.
     * @param s is the session of report.
     * @param p is the professor to send to.
     */
    private void sendProfReport(Session s, Professor p) throws GeneralSecurityException, IOException {
        String sub = "Course Attendance Weekly report";
        StringBuilder body = new StringBuilder("To ");
        body.append(p.getName());
        body.append(": \n");
        body.append("\n");
        body.append("Below is the attendance record of your course: ");
        body.append(s.getCourseid());
        body.append(" at date: ");
        body.append(s.getTime());
        body.append(": \n");
        body.append("\n");
        body.append(s.getCourseid());
        body.append(": \n\n");

        for (AttendanceRecord r: s.getRecords()) {
            body.append(r.getStudent().getLegalName());
            body.append("  ");
            body.append(r.getStudent().getPersonalID());
            body.append("  ");
            body.append(r.getStatus().getStatus());
            body.append("\n");
        }
        body.append("\nIf there is any problem, please contact us.\n");
        body.append("\n");
        body.append("ECE 651 team 6");
        sendNotification(p.getEmail(), sub, body.toString());
    }

    /**
     * Email all students the attendance report of a session.
     * @param s is the session of report.
     */
    private void sendStuReport(Session s) throws GeneralSecurityException, IOException {
        for (AttendanceRecord r: s.getRecords()) {
            String sub = "Course Attendance Weekly report";
            StringBuilder body = new StringBuilder("To ");
            body.append(r.getStudent().getLegalName());
            body.append(": \n");
            body.append("\n");
            body.append("Below is the attendance record of course: ");
            body.append(s.getCourseid());
            body.append(" at date: ");
            body.append(s.getTime());
            body.append(": \n");
            body.append("\n");
            body.append(s.getCourseid());
            body.append(": \n");

            body.append(r.getStudent().getLegalName());
            body.append("  ");
            body.append(r.getStudent().getPersonalID());
            body.append("  ");
            body.append(r.getStatus().getStatus());
            body.append("\n");
            body.append("\nIf there is any problem, please contact us.\n");
            body.append("\n");
            body.append("ECE 651 team 6");
            sendNotification(r.getStudent().getEmailAddr(), sub, body.toString());
        }
    }
}

