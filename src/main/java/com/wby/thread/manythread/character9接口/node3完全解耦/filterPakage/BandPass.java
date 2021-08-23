//: interfaces/filters/BandPass.java
package com.wby.thread.manythread.character9接口.node3完全解耦.filterPakage;

public class BandPass extends Filter {
  double lowCutoff, highCutoff;
  public BandPass(double lowCut, double highCut) {
    lowCutoff = lowCut;
    highCutoff = highCut;
  }
  public Waveform process(Waveform input) { return input; }
} ///:~
