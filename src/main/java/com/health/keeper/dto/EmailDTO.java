package com.health.keeper.dto;


import lombok.Data;

@Data //(@Getter + @Setter + @ToString + @NoArgsConstructor + @AllArgsConstructor)
public class EmailDTO {

    private String emailType;
    private String emailCode; //네이버는 256, 구글은 51..
    private String emailNum;


}
