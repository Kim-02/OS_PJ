package com.cpu.cpucontroller;

import com.cpu.Processor.E_Processor;
import com.cpu.Processor.P_Processor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import com.cpu.process.Process;
import com.cpu.Processor.ProcessorController;
@RequiredArgsConstructor
@Getter
public class CpuSystem {
    private Queue<Process> TerminateProcessQueue = new LinkedList<>();
    private Queue<Process> WaitingProcessQueue = new LinkedList<>();
    private Integer ProcessingTime =0;
    private ProcessorController[] ProcessorList = new ProcessorController[4];
    private void IncreaseProcessingTime(){
        ProcessingTime+=1;
    }
//    public void SetProcessorLIst(ProcessorController Processor){
//        ProcessorList.add(Processor);
//    }

//    public void Start(){ //4개의 프로세서 구성
//        SetProcessorLIst(new P_Processor());
//        SetProcessorLIst(new P_Processor());
//        SetProcessorLIst(new E_Processor());
//        SetProcessorLIst(new E_Processor());
//    }
}
