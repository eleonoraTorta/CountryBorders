package it.polito.tdp.country.model;

public class CountryPair {
	private Country c1;
	private Country c2;

	public CountryPair(Country c1, Country c2) {
		super();
		this.c1 = c1;
		this.c2 = c2;
	}

	/**
	 * @return the c1
	 */
	public Country getC1() {
		return c1;
	}

	/**
	 * @return the c2
	 */
	public Country getC2() {
		return c2;
	}

	/**
	 * @param c1
	 *            the c1 to set
	 */
	public void setC1(Country c1) {
		this.c1 = c1;
	}

	/**
	 * @param c2
	 *            the c2 to set
	 */
	public void setC2(Country c2) {
		this.c2 = c2;
	}

}