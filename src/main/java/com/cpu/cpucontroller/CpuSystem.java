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
    private Queue<Process> TerminateProcessQueue = new LinkedList<>();
    private Queue<Process> WaitingProcessQueue = new LinkedList<>();
    private Integer ProcessingTime =0;
    protected ProcessorController[] ProcessorList = new ProcessorController[4];
    private int ProcessorCount = 0;
    private Map<Integer,ArrayList<Process>> ProcessMap = new HashMap<>();

    //test용도
    protected List<String[]> ClockHistory = new ArrayList<>();

    protected void IncreaseProcessingTime(){
        ProcessingTime+=1;
    }

    public void setProcessor(ProcessorController p){ //프로세서를 지정해서 0부터 3까지 4개를 배정한다.
        if (ProcessorCount >= 4) {
            throw new IllegalStateException("프로세서는 최대 4개까지만 등록할 수 있습니다.");
        }
        ProcessorList[ProcessorCount] = p;
        ProcessorCount++;
    }

    public void setProcess(Integer AT,Integer BT){

        Process P = Process.builder()
                .ArrivalTime(AT)
                .RemainTime(BT)
                .build();
        int totalProcesses = ProcessMap.values().stream()
                .mapToInt(ArrayList::size)
                .sum();

        if (totalProcesses >= 15) {
            throw new IllegalStateException("전체 프로세스 수가 15개를 초과했습니다.");
        }

        if (ProcessMap.containsKey(AT)) {
            ProcessMap.get(AT).add(P);
        } else {
            ArrayList<Process> list = new ArrayList<>();
            list.add(P);
            ProcessMap.put(AT, list);
        }
    }

    public boolean isAllProcessTerminated() {
        // 1. Processor에 남아있는 프로세스 체크
        boolean allProcessorsIdle = true;
        for (ProcessorController processor : ProcessorList) {
            if (processor != null && processor.getUsingProcess() != null) {
                allProcessorsIdle = false;
                break;
            }
        }

        // 2. 대기 큐에 프로세스 남아있는지 체크
        boolean waitingQueueEmpty = WaitingProcessQueue.isEmpty();

        // 3. 앞으로 도착할 프로세스가 남아있는지 체크
        boolean futureProcessExist = ProcessMap.entrySet().stream()
                .anyMatch(entry -> entry.getKey() >= ProcessingTime);

        // 4. 세 가지 다 만족해야 종료
        return allProcessorsIdle && waitingQueueEmpty && !futureProcessExist;
    }

    public void runOneClock() {
        // 기본은 아무 것도 안 함
    }
}
