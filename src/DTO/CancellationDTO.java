package DTO;

import java.time.LocalDateTime;

public class CancellationDTO {
    private int cancellationID;
    private LocalDateTime cancellationDay ;
    private int employeeID ;

    public CancellationDTO(int cancellationID, LocalDateTime cancellationDay, int employeeID) {
        this.cancellationID = cancellationID;
        this.cancellationDay = cancellationDay;
        this.employeeID = employeeID;
    }

    public int getCancellationID() {
        return cancellationID;
    }

    public void setCancellationID(int cancellationID) {
        this.cancellationID = cancellationID;
    }

    public LocalDateTime getCancellationDay() {
        return cancellationDay;
    }

    public void setCancellationDay(LocalDateTime cancellationDay) {
        this.cancellationDay = cancellationDay;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this. employeeID =  employeeID;
    }
}

