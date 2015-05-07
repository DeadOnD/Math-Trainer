/* This file is part of the PlusMinusTimesDivide product, copyright Eric Lavarde <android@lavar.de>.
 * Trademarks (the product name, in English and translated, artwork like icons, and the domains
 * lavar.de and lavarde.eu - also reversed as package name) are properties of the copyright owner
 * and shall not be used by anyone else without explicit authorisation.

The code itself is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PlusMinusTimesDivide is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PlusMinusTimesDivide.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.thilo.android.pmtd;

/**
 * @author Eric Lavarde
 *
 */
public abstract class APrefs implements IPrefs {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf;
		if (isSmallNumbersMax()) {
			buf = new StringBuffer("max: ");
		} else {
			buf = new StringBuffer("MAX: ");
		}
		buf.append(getMaxValue());
		buf.append('.'); // TODO use local decimal separator DecimalFormatSymbols.getInstance(Locale!!!).getDecimalSeparator()
		buf.append(getDecimalPlaces());

		switch (getOperation()) {
		case INumberProvider.PLUS:
		case INumberProvider.TIMES:
			int train = getTableTraining();
			if (train != 0) { // training specific multiplication table
				buf.append('{');
				buf.append(train);
				buf.append('}');
			}
			break;
		}

		buf.append('[');
		switch (getOperation()) {
		case INumberProvider.PLUS:
			if (isPlusCarryAllowed()) {
				buf.append('\u2190'); // U+2190 (8592) 	â†� 	LEFTWARDS ARROW
			} else {
				buf.append('\u219a'); // U+219A (8602) 	â†š 	LEFTWARDS ARROW WITH STROKE
			}
			break;
		case INumberProvider.MINUS:
			if (isMinusNegativeAllowed()) {
				buf.append('\u00b1'); // &#177; 	&plusmn; 	Plus-minus sign
			} else {
				buf.append('+'); // +	PLUS SIGN
			}

			if (isMinusBorrowAllowed()) {
				buf.append('\u2192'); // U+2192 (8594) 	â†’ 	RIGHTWARDS ARROW
			} else {
				buf.append('\u219b'); // U+219B (8603) 	â†› 	RIGHTWARDS ARROW WITH STROKE
			}
			break;
		case INumberProvider.DIVIDE:
			if (isDivideRestAllowed()) {
				buf.append('\u2260'); // NOT EQUAL
			} else {
				buf.append('='); // EQUAL
			}
			if (isDivideIntegers()) {
				buf.append('\u2208'); // U+2208 (8712) 	âˆˆ 	ELEMENT OF
			} else {
				buf.append('\u2209'); // U+2209 (8713) 	âˆ‰ 	NOT AN ELEMENT OF
			}
			break;
		}
		buf.append(']');

		return buf.toString();
	}

}
