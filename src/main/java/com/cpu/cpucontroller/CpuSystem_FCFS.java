package com.cpu.cpucontroller;

import com.cpu.process.Process;
import com.cpu.Processor.ProcessorController;
import java.util.*;

public class CpuSystem_FCFS extends CpuSystem {

    @Override
    public void runOneClock() {
        Integer currentTime = getProcessingTime();
        System.out.println("클럭: " + currentTime);

        // 1. 현재 시간에 도착한 프로세스 처리
        if (getProcessMap().containsKey(currentTime)) {
            List<Process> arrivingProcesses = getProcessMap().get(currentTime);
            getWaitingProcessQueue().addAll(arrivingProcesses);
        }

        // 2. 프로세서에 프로세스 할당
        for (ProcessorController processor : getProcessorList()) {
            if (processor != null) {
                if (processor.getUsingProcess() == null && !getWaitingProcessQueue().isEmpty()) {
                    processor.setProcess(getWaitingProcessQueue().poll());
                }
            }
        }

        // 3. 각 프로세서 실행
        for (ProcessorController processor : getProcessorList()) {
            if (processor != null) {
                processor.IncreasePowerConsumption();
                Object finishedProcess = processor.DecreaseUsingProcessBT(currentTime);
                if (finishedProcess != null) {
                    getTerminateProcessQueue().add((Process) finishedProcess);
                }
            }
        }

        // 4. 현재 상태 출력
        printCurrentStatus();

        // 5. 마지막에 시간 증가
        recordClockState();
        IncreaseProcessingTime();
    }

    private void printCurrentStatus() {
        System.out.println("=== 현재 상태 ===");
        for (ProcessorController processor : getProcessorList()) {
            if (processor != null) {
                if (processor.getUsingProcess() != null) {
                    System.out.println(processor.getClass().getSimpleName() + ": PID=" + processor.getUsingProcess().getArrivalTime() + ", 남은시간=" + processor.getUsingProcess().getRemainTime());
                } else {
                    System.out.println(processor.getClass().getSimpleName() + ": 대기 중");
                }
            }
        }
        System.out.println("=================");
    }

    private void recordClockState() {
        String[] state = new String[4];
        for (int i = 0; i < ProcessorList.length; i++) {
            ProcessorController processor = ProcessorList[i];
            if (processor != null && processor.getUsingProcess() != null) {
                state[i] = String.valueOf(processor.getUsingProcess().getArrivalTime()); // 프로세스 PID (도착시간으로 구분)
            } else {
                state[i] = "-"; // 비어있으면 -
            }
        }
        ClockHistory.add(state);
    }
}
