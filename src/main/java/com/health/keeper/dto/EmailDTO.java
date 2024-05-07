package com.health.keeper.dto;


import lombok.Data;

@Data //(@Getter + @Setter + @ToString + @NoArgsConstructor + @AllArgsConstructor)
public class EmailDTO {

    private String emailType;
    private String emailCode;
    private String emailNum;


}
