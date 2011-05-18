/*
    Bedrock Bukkit plugin for Minecraft
    Copyright (C) 2011 Shannon Wynter (http://fremnet.net/)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package au.net.fremnet.bukkit.Bedrock;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Material;

public class WeightedMaterialPicker {
	private Random		random		= new Random(System.nanoTime());
	private Integer		count		= 0;
	private double		total		= 0.0;
	private double[]	totals		= new double[1];
	private Material[]	materials	= new Material[1];

	public WeightedMaterialPicker(Integer max) {
		totals = new double[max];
		materials = new Material[max];

	}

	public void add(Material material, double weight) {
		total += weight;
		totals[count] = total;
		materials[count++] = material;
	}

	public Material get() {
		double rnd = random.nextDouble() * total;
		int index = Arrays.binarySearch(totals, rnd);
		return materials[index < 0 ? Math.abs(index) - 1 : index];
	}
}
