package com.cpu.Processor;


import com.cpu.process.Process;

public interface ProcessorController {
    void IncreasePowerConsumption();
    Object DecreaseUsingProcessBT(Integer currentTime);
    Integer getPowerConsumption();
    void setProcess(Process usingProcess);
}
