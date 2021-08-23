//: interfaces/filters/LowPass.java
package com.wby.thread.manythread.character9接口.node3完全解耦.filterPakage;

public class LowPass extends Filter {
  double cutoff;
  public LowPass(double cutoff) { this.cutoff = cutoff; }
  public Waveform process(Waveform input) {
    return input; // Dummy processing
  }
} ///:~
