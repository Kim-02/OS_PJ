package com.cpu.cpucontroller;

import com.cpu.Processor.P_Processor;
import com.cpu.process.Process;
import com.cpu.Processor.ProcessorController;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class CpuSystem_FCFS extends CpuSystem {

    @Override
    public void setComparatorBasedOnCpu(){
        WaitingProcessQueue = new PriorityQueue<>(new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });
    }
    @Override
    public void runOneClock() {
        // 도착 시간에 맞는 프로세스가 대기 큐에 추가되는 부분
        if (ProcessMap.containsKey(ProcessingTime) && ProcessMap.get(ProcessingTime) != null) {
            while (!ProcessMap.get(ProcessingTime).isEmpty()) {
                WaitingProcessQueue.add(ProcessMap.get(ProcessingTime).poll());
            }
        }

        // 각 프로세서를 확인하고, 종료된 프로세스는 종료 큐에 추가
        for (ProcessorController processor : ProcessorList) {
            if (processor.getUsingProcess() != null && processor.getUsingProcess().getRemainTime() <= 0) {
                Process terminatedProcess = processor.RemoveTerminatedProcess(ProcessingTime);
                TerminateProcessQueue.add(terminatedProcess);
            }
        }

        // 대기 큐에서 빈 프로세서에 프로세스를 할당
        while (!WaitingProcessQueue.isEmpty() && findEmptyProcessor() != null) {
            ProcessorController availableProcessor = findEmptyProcessor();
            availableProcessor.setProcess(WaitingProcessQueue.poll());
        }

        // 프로세서가 사용 중이지 않으면 비활성화
        for (ProcessorController processor : ProcessorList) {
            processor.setProcessorStatusNonRunning();
        }

        // 프로세서가 작업을 처리하고 전력을 소모하며, 버스트 타임을 감소시킴
        for (ProcessorController processor : ProcessorList) {
            processor.DecreaseUsingProcessBT();
            processor.IncreasePowerConsumption();
        }
        printProcessorStatus();
    }
}
