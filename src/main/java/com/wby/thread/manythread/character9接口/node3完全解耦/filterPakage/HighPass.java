//: interfaces/filters/HighPass.java
package com.wby.thread.manythread.character9接口.node3完全解耦.filterPakage;

public class HighPass extends Filter {
  double cutoff;
  public HighPass(double cutoff) { this.cutoff = cutoff; }
  public Waveform process(Waveform input) { return input; }
} ///:~
