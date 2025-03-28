/*
* Copyright (C) 2015 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package be.nabu.libs.artifacts;

import java.util.function.Function;

public enum NamingConvention {
	UPPER_CAMEL_CASE(new Camelifier(true, false)),
	LOWER_CAMEL_CASE(new Camelifier(false, false)),
	UNDERSCORE(new Decamelifier("_")),
	DASH(new Decamelifier("-")),
	LOWER_CASE(new Decamelifier(null)),
	UPPER_TEXT(new Camelifier(true, true)),
	LOWER_TEXT(new Camelifier(false, true))
	;
	
	private Function<String, String> transformer;
	
	private NamingConvention(Function<String, String> transformer) {
		this.transformer = transformer;
	}
	
	public String apply(String originalString) {
		return apply(originalString, NamingConvention.LOWER_CAMEL_CASE);
	}
	
	public String apply(String originalString, NamingConvention originalConvention) {
		if (this == UPPER_TEXT || this == LOWER_TEXT) {
			// if we have to go from camel cased to text, round trip to dashed
			if (originalConvention == LOWER_CAMEL_CASE || originalConvention == UPPER_CAMEL_CASE) {
				originalString = DASH.transformer.apply(originalString);
			}
		}
		// if we are moving from one camelcase to another, roundtrip to dashed
		if ((originalConvention == LOWER_CAMEL_CASE || originalConvention == UPPER_CAMEL_CASE)
				&& (this == LOWER_CAMEL_CASE || this == UPPER_CAMEL_CASE)) {
			originalString = DASH.apply(originalString);
		}
		// otherwise we always roundtrip to camelcase
		else if (originalConvention != LOWER_CAMEL_CASE && originalConvention != UPPER_CAMEL_CASE) {
			originalString = LOWER_CAMEL_CASE.transformer.apply(originalString);
		}
		return this.transformer.apply(originalString);
	}
	
	public static class Decamelifier implements Function<String, String> {
		private String sequence;
		public Decamelifier(String sequence) {
			this.sequence = sequence;
		}
		@Override
		public String apply(String string) {
			StringBuilder builder = new StringBuilder();
			boolean previousUpper = false;
			for (int i = 0; i < string.length(); i++) {
				String substring = string.substring(i, i + 1);
				if (substring.equals(substring.toLowerCase()) || i == 0) {
					previousUpper = !substring.equals(substring.toLowerCase());
					builder.append(substring.toLowerCase());
				}
				else {
					// if it is not preceded by the sequence or another capitilized
					if (sequence != null && !builder.toString().endsWith(sequence) && !previousUpper) {
						builder.append(sequence);
					}
					previousUpper = true;
					builder.append(substring.toLowerCase());
				}
			}
			return builder.toString();
		}
	}
	
	public static class Camelifier implements Function<String, String> {
		private boolean upperCase, useWords;
		
		public Camelifier(boolean upperCase, boolean useWords) {
			this.upperCase = upperCase;
			this.useWords = useWords;
		}
		
		@Override
		public String apply(String t) {
			StringBuilder builder = new StringBuilder();
			String[] split = t.split("[^a-zA-Z0-9]+");
			for (int i = 0; i < split.length; i++) {
				if (!split[i].isEmpty()) {
					if (i > 0 && useWords) {
						builder.append(" ");
					}
					if ((i > 0 && !useWords) || upperCase) {
						builder.append(split[i].substring(0, 1).toUpperCase() + split[i].substring(1));
					}
					else {
						builder.append(split[i]);
					}
				}
			}
			String string = builder.toString();
			if (!upperCase && !string.isEmpty()) {
				string = string.substring(0, 1).toLowerCase() + string.substring(1);
			}
			return string;
		}
	};
}
