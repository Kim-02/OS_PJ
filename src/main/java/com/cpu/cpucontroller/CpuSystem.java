package com.cpu.cpucontroller;

import com.cpu.Processor.E_Processor;
import com.cpu.Processor.P_Processor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

import com.cpu.process.Process;
import com.cpu.Processor.ProcessorController;
@RequiredArgsConstructor
@Getter
public class CpuSystem {
    protected Queue<Process> TerminateProcessQueue = new LinkedList<>();
    protected Queue<Process> WaitingProcessQueue = new LinkedList<>();
    protected Integer ProcessingTime =0;
    protected ProcessorController[] ProcessorList = new ProcessorController[4];
    protected int ProcessorCount = 0;
    protected Map<Integer,Queue<Process>> ProcessMap = new HashMap<>();

    //test용도
    protected List<String[]> ClockHistory = new ArrayList<>();

    public void IncreaseProcessingTime(){
        ProcessingTime+=1;
    }

    public void setProcessor(ProcessorController p){ //프로세서를 지정해서 0부터 3까지 4개를 배정한다.
        if (ProcessorCount >= 4) {
            throw new IllegalStateException("프로세서는 최대 4개까지만 등록할 수 있습니다.");
        }
        ProcessorList[ProcessorCount] = p;
        ProcessorCount++;
    }

    public void setProcess(String ProcessName,Integer AT,Integer BT){

        Process P = Process.builder()
                .ProcessName(ProcessName)
                .ArrivalTime(AT)
                .RemainTime(BT)
                .build();
        int totalProcesses = ProcessMap.values().stream()
                .mapToInt(Queue::size)
                .sum();

        if (totalProcesses >= 15) {
            throw new IllegalStateException("전체 프로세스 수가 15개를 초과했습니다.");
        }

        if (ProcessMap.containsKey(AT)) {
            ProcessMap.get(AT).add(P);
        } else {
            Queue<Process> list = new LinkedList<>();
            list.add(P);
            ProcessMap.put(AT, list);
        }
    }

    public ProcessorController findEmptyProcessor(){
        for (ProcessorController p : ProcessorList) {
            if(p.getUsingProcess() == null){
                return p;
            }
        }
        return null;
    }



    public void runOneClock() {
        // 기본은 아무 것도 안 함
    }
}
