package net.minecraft.client.me.sleepyfish.smok.utils.animation.normal;

import net.minecraft.client.me.sleepyfish.smok.utils.Timer;

// Class from SMok Client by SleepyFish
public abstract class Animation {

    public Timer.Better timer = new Timer.Better();
    
    protected int duration;

    protected double endPoint;

    protected Direction direction;

    public Animation(int ms, double endPoint) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.direction = Direction.FORWARDS;
    }

    public Animation(int ms, double endPoint, Direction direction) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.direction = direction;
    }

    public boolean isDone(Direction direction) {
        return isDone() && this.direction.equals(direction);
    }

    public double getLinearOutput() {
        return 1 - ((timer.getTime() / (double) duration) * endPoint);
    }

    public void reset() {
    	timer.reset();
    }

    public boolean isDone() {
        return timer.delay(duration);
    }

    public void changeDirection() {
        setDirection(direction.opposite());
    }

    public void setDirection(Direction direction) {
        if (this.direction != direction) {
            this.direction = direction;
            timer.setTime(System.currentTimeMillis() - (duration - Math.min(duration, timer.getTime())));
        }
    }

    protected boolean correctOutput() {
        return false;
    }

    public double getValue() {
        if (direction == Direction.FORWARDS) {
            if (isDone())
                return endPoint;
            return (getEquation(timer.getTime()) * endPoint);
        } else {
            if (isDone())
                return 0;

            if (correctOutput())
                return getEquation(Math.min(duration, Math.max(0, duration - timer.getTime()))) * endPoint;
            else
                return (1 - getEquation(timer.getTime())) * endPoint;
        }
    }

    protected abstract double getEquation(double x);

	public double getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(double endPoint) {
		this.endPoint = endPoint;
	}

	public int getDuration() {
		return duration;
	}

	public Direction getDirection() {
		return direction;
	}
}