package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    SawToothGenerator(int period){
        this.period = period;
        this.state = 0;
    }

    @Override
    public double next(){
        state++;
        return (double)(state % period) / (period / 2) - 1;
    }
}
