package cst438hw2.domain;

public class TempAndTime {

	public double temp;
	public long time;
	public int timezone;
	
	public TempAndTime(double temp, long time, int timezone){
		this.temp = temp;
		this.time = time;
		this.timezone = timezone;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TempAndTime other = (TempAndTime) obj;
		if (Double.doubleToLongBits(temp) != Double.doubleToLongBits(other.temp))
			return false;
		if (time != other.time)
			return false;
		if (timezone != other.timezone)
			return false;
		return true;
	}
 }
