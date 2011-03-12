package au.net.fremnet.bukkit.Bedrock;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Material;

public class WeightedMaterialPicker {
	private Random random = new Random(System.nanoTime());
	private Integer count = 0;
	private double total = 0.0;
	private double[] totals = new double[10];
	private Material[] materials = new Material[10];
	
	public WeightedMaterialPicker (Integer max) {
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
