/****************************************************************************

	This is a web application developed for the ACCENTS club from the
	Ecole Centrale de Nantes aiming to facilitate contact between travelling
	students.
	
    Copyright (C) 2013  Malik Olivier Boussejra

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/.

******************************************************************************/

package util;

/**
 * Some method used in scala templates
 * @author malik
 *
 */
public class Util {
	
	/**
	 * Adds escape character to strings used in Javascripts
	 * @param str
	 * @return string in argument with escape characters added
	 */
	public static String addEscChar(String str){
		return str.replace("'", "\'");
	}
	
	/**
	 * No space in IDs. Used in translations
	 * @param str
	 * @return
	 */
	public static String removeUnwantedChar(String str){
		return str.replace(":", "_");
	}
}
