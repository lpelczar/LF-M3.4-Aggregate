package legacyfighter.leave;


public class LeaveService {

    final LeaveDatabase database;
    final MessageBus messageBus;
    final EmailSender emailSender;
    final EscalationManager escalationManager;

    LeaveService(LeaveDatabase database, MessageBus messageBus, EmailSender emailSender, EscalationManager escalationManager) {
        this.database = database;
        this.messageBus = messageBus;
        this.emailSender = emailSender;
        this.escalationManager = escalationManager;
    }

    public Result requestPaidDaysOff(int days, Long employeeId) {
        Object[] employeeData = database.findByEmployeeId(employeeId);
        String employeeStatus = (String) employeeData[0];
        int daysSoFar = (Integer) employeeData[1];

        LeaveResolver leaveResolver = new LeaveResolver(employeeId, employeeStatus, daysSoFar);

        Result decision = leaveResolver.resolve(days);

        if (decision == Result.Approved) {
            employeeData[1] = daysSoFar + days;
            database.save(employeeData);
            messageBus.sendEvent("request approved");
        } else if (decision == Result.Manual) {
            escalationManager.notifyNewPendingRequest(employeeId);
        } else {
            emailSender.send("next time");
        }

        return decision;
    }
}

class LeaveDatabase {

    Object[] findByEmployeeId(Long employeeId) {
        return new Object[0];
    }

    void save(Object[] employeeData) {

    }
}

class MessageBus {

    void sendEvent(String msg) {
    }
}

class EmailSender {

    void send(String msg) {
    }
}

class EscalationManager {

    void notifyNewPendingRequest(Long employeeId) {
    }
}

class Configuration {

    int getMaxDaysForPerformers() {
        return 45;
    }

    int getMaxDays() {
        return 26;
    }

}