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
        EmployeeLeave employeeLeave = database.findByEmployeeId(employeeId);

        Result decision = employeeLeave.requestDaysOff(days);

        if (decision == Result.Approved) {
            database.save(employeeLeave);
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

    EmployeeLeave findByEmployeeId(Long employeeId) {
        return null;
    }

    void save(EmployeeLeave employeeLeave) {

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