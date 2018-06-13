package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    int period;
    double multiplier;
    int state;

    AcceleratingSawToothGenerator(int period, double multiplier){
        this.period = period;
        this.multiplier = multiplier;
        this.state = 0;
    }

    @Override
    public double next(){
        state++;
        double result = (double) state / (period / 2) - 1;
        if (state == period){
            state = 1;
            period *= multiplier;
        }
        return result;
    }
}
