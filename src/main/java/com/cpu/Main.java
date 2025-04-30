package com.cpu;


import com.cpu.cpucontroller.*;
import com.cpu.Processor.*;
import com.cpu.process.*;
import com.cpu.process.Process;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {


        // 1. CpuSystem 객체 생성 (FCFS 스케줄러 사용)
        CpuSystem_SRTN cpuSystem = new CpuSystem_SRTN();

        // 2. 프로세서 등록 (원하는 대로 P코어, E코어 조합)
        cpuSystem.setProcessor(new P_Processor()); // 0번 프로세서
        cpuSystem.setProcessor(new P_Processor()); // 1번 프로세서
        cpuSystem.setProcessor(new E_Processor()); // 2번 프로세서
        cpuSystem.setProcessor(new E_Processor()); // 3번 프로세서

        // 3. 프로세스 등록 (도착시간, 실행시간)
        cpuSystem.setProcess("PID1", 0, 10);  // 도착시간: 0, 실행시간: 10
        cpuSystem.setProcess("PID2", 3, 5);   // 도착시간: 2, 실행시간: 5
        cpuSystem.setProcess("PID3", 1, 1);   // 도착시간: 4, 실행시간: 1
        cpuSystem.setProcess("PID4", 8, 3);   // 도착시간: 6, 실행시간: 3
        cpuSystem.setProcess("PID5", 10, 7);   // 도착시간: 8, 실행시간: 7

        // 필요하면 15개까지 추가 가능
        // 4. 메인 루프 (모든 프로세스가 종료될 때까지)


        int ProcessCount = 5; // 프로세스 갯수만큼 해야함. 이부분은 나중에 수정 예정



        while (cpuSystem.getTerminateProcessQueue().size() != ProcessCount) {
            cpuSystem.runOneClock(); // 1클럭 단위로 실행
//            try {
//                Thread.sleep(500); // 보기 편하게 0.5초 멈춤 (optional)
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            cpuSystem.IncreaseProcessingTime();
        }

        // 5. 종료 후 출력
        System.out.println("\n======== 프로세스 종료 정보 ========");

        int idx = 1;
        for (Process p : cpuSystem.getTerminateProcessQueue()) {
            System.out.println(p.getProcessName()+" "+idx++ + "번째 종료 | 도착시간: " + p.getArrivalTime() + ", 종료시간: " + p.getTerminateTime());
        }
        Double AllPowerConsumption = 0.0;
        for(ProcessorController processor : cpuSystem.getProcessorList()){
            AllPowerConsumption += processor.getPowerConsumption();
        }
        System.out.println("전력 사용 총량: "+AllPowerConsumption);
    }
}
