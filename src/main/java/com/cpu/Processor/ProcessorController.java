package com.cpu.Processor;


import com.cpu.process.Process;

public interface ProcessorController {
    void IncreasePowerConsumption();
    Object DecreaseUsingProcessBT(Integer currentTime);
    void setProcess(Process usingProcess);
    Process getUsingProcess();
}
