package it.polito.tdp.country.model;

public class Country {

	private int cCode; // country code
	private String stateAbb; // state abbreviation (3 letters)
	private String stateName; // state name (full name)

	/**
	 * Create new Country object. Object identifier: cCode.
	 * 
	 * @param cCode
	 * @param stateAbb
	 * @param stateName
	 */
	public Country(int cCode, String stateAbb, String stateName) {
		super();
		this.cCode = cCode;
		this.stateAbb = stateAbb;
		this.stateName = stateName;
	}

	/**
	 * @return the cCode
	 */
	public int getcCode() {
		return cCode;
	}

	/**
	 * @return the stateAbb
	 */
	public String getStateAbb() {
		return stateAbb;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param cCode
	 *            the cCode to set
	 */
	public void setcCode(int cCode) {
		this.cCode = cCode;
	}

	/**
	 * @param stateAbb
	 *            the stateAbb to set
	 */
	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}

	/**
	 * @param stateName
	 *            the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * Hash function, based on the cCode field, only.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cCode;
		return result;
	}

	/**
	 * Equality comparison based on the cCode field, only.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (cCode != other.cCode)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s (%d %s)", stateName, cCode, stateAbb );
	}

}
