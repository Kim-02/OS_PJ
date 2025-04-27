package com.cpu;


import com.cpu.cpucontroller.*;
import com.cpu.Processor.*;
import com.cpu.process.*;
import com.cpu.process.Process;

public class Main {
    public static void main(String[] args) {
        // 1. CpuSystem 객체 생성 (FCFS 스케줄러 사용)
        CpuSystem_FCFS cpuSystem = new CpuSystem_FCFS();

        // 2. 프로세서 등록 (원하는 대로 P코어, E코어 조합)
        cpuSystem.setProcessor(new P_Processor()); // 0번 프로세서
        cpuSystem.setProcessor(new P_Processor()); // 1번 프로세서
        cpuSystem.setProcessor(new E_Processor()); // 2번 프로세서
        cpuSystem.setProcessor(new E_Processor()); // 3번 프로세서

        // 3. 프로세스 등록 (도착시간, 실행시간)
        cpuSystem.setProcess(0, 5); // 0초 도착, 실행시간 5
        cpuSystem.setProcess(2, 8); // 2초 도착, 실행시간 8
        cpuSystem.setProcess(4, 3); // 4초 도착, 실행시간 3
        cpuSystem.setProcess(6, 7); // 6초 도착, 실행시간 7
        // 필요하면 15개까지 추가 가능

        // 4. 메인 루프 (모든 프로세스가 종료될 때까지)
        while (!cpuSystem.isAllProcessTerminated()) {
            cpuSystem.runOneClock(); // 1클럭 단위로 실행
            try {
                Thread.sleep(500); // 보기 편하게 0.5초 멈춤 (optional)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 5. 종료 후 출력
        System.out.println("\n======== 프로세스 종료 정보 ========");

        int idx = 1;
        for (Process p : cpuSystem.getTerminateProcessQueue()) {
            System.out.println(idx++ + "번째 종료 | 도착시간: " + p.getArrivalTime() + ", 종료시간: " + p.getTerminateTime());
        }
        System.out.println("\n======== 프로세서별 타임라인 ========");

        System.out.print("P1 (성능): ");
        for (String[] clock : cpuSystem.getClockHistory()) {
            System.out.print(clock[0] + " ");
        }
        System.out.println();

        System.out.print("P2 (효율): ");
        for (String[] clock : cpuSystem.getClockHistory()) {
            System.out.print(clock[1] + " ");
        }
        System.out.println();

        System.out.print("P3 (효율): ");
        for (String[] clock : cpuSystem.getClockHistory()) {
            System.out.print(clock[2] + " ");
        }
        System.out.println();

        System.out.print("P4 (효율): ");
        for (String[] clock : cpuSystem.getClockHistory()) {
            System.out.print(clock[3] + " ");
        }
        System.out.println();

    }
}
