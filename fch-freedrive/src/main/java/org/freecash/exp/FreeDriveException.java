package org.freecash.exp;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreeDriveException extends Exception{
    private int code;
    private String message;
}
