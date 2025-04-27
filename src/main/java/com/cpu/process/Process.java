package com.cpu.process;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Process {
    private Integer ArrivalTime;
    private Integer RemainTime;
    private Integer TerminateTime; //프로세스가 몇 초에 끝났는지
}