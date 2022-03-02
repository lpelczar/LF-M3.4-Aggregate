package legacyfighter.leave;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeLeaveTest {

    static final long ONE = 1L;

    @Test
    void shouldFailWhenInvalidInput() {
        //given
        EmployeeLeave employeeLeave = aEmployeeLeave("PERFORMER", 10);

        //expect
        assertThrows(IllegalArgumentException.class, () -> employeeLeave.requestDaysOff(-1));
        assertThrows(IllegalArgumentException.class, () -> employeeLeave.requestDaysOff(0));
    }

    @Test
    void shouldApproveForPerformer() {
        //given
        EmployeeLeave employeeLeave = aEmployeeLeave("PERFORMER", 25);

        ///when
        Result result = employeeLeave.requestDaysOff(1);

        //expect
        assertEquals(Result.Approved, result);
    }

    @Test
    void shouldApproveForRegular() {
        //given
        EmployeeLeave employeeLeave = aEmployeeLeave("REGULAR", 25);

        ///when
        Result result = employeeLeave.requestDaysOff(1);

        //expect
        assertEquals(Result.Approved, result);
    }

    @Test
    void shouldDenyForRegular() {
        //given
        EmployeeLeave employeeLeave = aEmployeeLeave("REGULAR", 25);

        //and
        employeeLeave.requestDaysOff(1);
        //when
        Result result = employeeLeave.requestDaysOff(1);

        //expect
        assertEquals(Result.Denied, result);
    }

    @Test
    void shouldReturnManualForPerformer() {
        //given
        EmployeeLeave employeeLeave = aEmployeeLeave("PERFORMER", 26);

        ///when
        Result result = employeeLeave.requestDaysOff(1);

        //expect
        assertEquals(Result.Manual, result);
    }

    @Test
    void shouldDenyForSlacker() {
        //given
        EmployeeLeave employeeLeave = aEmployeeLeave("SLACKER", 0);

        ///when
        Result result = employeeLeave.requestDaysOff(1);

        //expect
        assertEquals(Result.Denied, result);
    }

    private EmployeeLeave aEmployeeLeave(String status, int daysSoFar) {
        return new EmployeeLeave(ONE, status, daysSoFar);
    }

}