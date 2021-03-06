package legacyfighter.leave;


public class EmployeeLeave {

    private Long employeeId;

    private String employeeStatus;
    private int daysSoFar;

    EmployeeLeave(Long employeeId, String employeeStatus, int daysSoFar) {
        this.employeeId = employeeId;
        this.employeeStatus = employeeStatus;
        this.daysSoFar = daysSoFar;
    }

    Result requestDaysOff(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException();
        }

        if (daysSoFar + days > 26) {
            if (employeeStatus.equals("PERFORMER") && daysSoFar + days < 45) {
                return Result.Manual;
            } else {
                return Result.Denied;
            }
        } else {
            if (employeeStatus.equals("SLACKER")) {
                return Result.Denied;
            } else {
                daysSoFar = daysSoFar + days;
                return Result.Approved;
            }
        }
    }

}
