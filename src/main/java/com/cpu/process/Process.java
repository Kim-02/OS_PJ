package com.cpu.process;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Process {
    private Integer ArrivalTime;
    private Integer RemainTime;
    private Integer TerminateTime; //프로세스가 몇 초에 끝났는지
}