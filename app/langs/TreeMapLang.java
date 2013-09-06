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

package langs;

import java.util.Map;
import java.util.TreeMap;

/**
 * Used to find a language from its string symbol in a TreeMap
 * @author malik
 *
 * @param <String>
 * @param <Lang>
 */
@SuppressWarnings({ "hiding", "serial" })
class TreeMapLang<String,Lang> extends TreeMap<String,Lang>{
	/**
	 * Checks whether the TreeMap contains the value.
	 */
	@Override
	public boolean containsValue(Object o){
		for(Map.Entry<String,Lang> entry : this.entrySet()){
			if(entry.getValue().toString().equals(o))	return true;
		}
		return false;
	}

}
