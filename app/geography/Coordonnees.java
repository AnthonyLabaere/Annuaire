/****************************************************************************

Copyright 2014 Anthony Labaere

Contributeurs : 
François Neber francois.neber@centraliens-nantes.net
Malik Olivier Boussejra malik.boussejra@centraliens-nantes.net
Anthony Labaere anthony.labaere@centraliens-nantes.net

Ce logiciel est un programme informatique ayant pour but de faciliter 
les contacts entre étudiants et diplômés de l'École Centrale Nantes 
à l'étranger comme en France.

Ce logiciel est régi par la licence CeCILL soumise au droit français et
respectant les principes de diffusion des logiciels libres. Vous pouvez
utiliser, modifier et/ou redistribuer ce programme sous les conditions
de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA 
sur le site "http://www.cecill.info".

En contrepartie de l'accessibilité au code source et des droits de copie,
de modification et de redistribution accordés par cette licence, il n'est
offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
seule une responsabilité restreinte pèse sur l'auteur du programme,  le
titulaire des droits patrimoniaux et les concédants successifs.

A cet égard  l'attention de l'utilisateur est attirée sur les risques
associés au chargement,  à l'utilisation,  à la modification et/ou au
développement et à la reproduction du logiciel par l'utilisateur étant 
donné sa spécificité de logiciel libre, qui peut le rendre complexe à 
manipuler et qui le réserve donc à des développeurs et des professionnels
avertis possédant  des  connaissances  informatiques approfondies.  Les
utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
logiciel à leurs besoins dans des conditions permettant d'assurer la
sécurité de leurs systèmes et ou de leurs données et, plus généralement, 
à l'utiliser et l'exploiter dans les mêmes conditions de sécurité. 

Le fait que vous puissiez accéder à cet en-tête signifie que vous avez 
pris connaissance de la licence CeCILL et que vous en avez accepté les
termes.

******************************************************************************/

package geography;

/**
 * Cette classe permet de manipuler des coordonnees GPS
 * 
 * @author Anthony
 *
 */
public class Coordonnees {

	/** Longitude GPS */
	private double longitude;

	/** Latitude GPS */
	private double latitude;

	public Coordonnees() {

	}

	public Coordonnees(double longitudeParam, double latitudeParam) {
		this.longitude = longitudeParam;
		this.latitude = latitudeParam;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

}
