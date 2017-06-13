package model.type;

public class Time {
    
    private int hour;
    private int min;
    private int sec;
    
    private boolean isTimeUp;
    
    public Time() {
        this.hour = 0;
        this.min = 0;
        this.sec = 0;
    }

    public Time(int hour, int min, int sec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }
    
    public void decrease() {
        --sec;
        if (sec < 0) {
            --min;
            sec = 59;
        }
        if (min < 0) {
            min = 59;
            if (hour == 0) {
                isTimeUp = true;
                sec = 0;
                min = 0;
            } else {
                --hour;
            }
        }
    }
    
    public boolean isTimeUp() {
        return isTimeUp;
    }
    
    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }
    
}
